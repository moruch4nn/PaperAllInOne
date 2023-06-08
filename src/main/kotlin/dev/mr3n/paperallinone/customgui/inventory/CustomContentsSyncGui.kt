package dev.mr3n.paperallinone.customgui.inventory

import dev.moru3.minepie.customgui.*
import dev.mr3n.paperallinone.item.EasyItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
/**
 * @param plugin JavaPluginを入れてください。
 * @param size インベントリの縦の列のサイズです。
 * @param title インベントリのタイトルを設定してください。
 * @param startX addItemをした際に(ry
 * @param startY addItemをした際(ry
 * @param endX addItemをした(ry
 * @param endY addItemをし(ry
 * @param runnable 任意:処理を記述してください
 */
open class CustomContentsSyncGui(plugin: JavaPlugin, size: Int, title: String, private val startX: Int, private val startY: Int, private val endX: Int, private val endY: Int, private val runnable: CustomContentsSyncGui.() -> Unit = {}): CustomSyncGui(plugin, title, size) {
    private val contents = mutableMapOf<ActionItem,ActionItem.()->Unit>()
    private val bufferInventory = super.inventory()
    private val indexes = mutableListOf<Int>()
    private var override: Boolean = false
    val filler: ItemStack = EasyItem(Material.BLACK_STAINED_GLASS_PANE, Component.text(" "))

    var page = 1
        private set(value) { field = maxOf(1,value) }

    private var sort: (ActionItem)->Comparable<*>? = SORT_BY_DISPLAY_NAME

    open fun add(itemStack: ItemStack, update: Boolean = false, runnable: ActionItem.() -> Unit = {}): CustomContentsSyncGui {
        add(ActionItem(uniqueTagKey,itemStack),update,runnable)
        return this
    }

    fun override(bool: Boolean) { this.override = bool }

    fun override() = this.override

    fun contents(): Set<ActionItem> = contents.keys

    open fun add(actionItem: ActionItem, update: Boolean = false, runnable: ActionItem.() -> Unit = {}): CustomContentsSyncGui {
        contents[actionItem] = runnable
        if(update) { update() }
        return this
    }

    private fun update(page: Int? = null) {
        this.indexes.clear()
        for(y in startY..endY) { for(x in startX..endX) {
            if(bufferInventory.getItem(x+(y*9))==null||override) { indexes.add(x+(y*9)) }
        } }
        page?.also { this.page = minOf(maxOf(it,1),(this.contents.size/this.indexes.size)+1) }
        val contents = this.contents.keys.toMutableList().subList((this.page-1)*this.indexes.size, minOf(maxOf(0,this.contents.size),this.contents.size))
        indexes.forEachIndexed { index2, i ->
            val content = contents.getOrNull(index2)
            val action = content?.let { this.contents[content] }
            if(action==null) {
                super.set(i%9,i/9, content) { }
            } else {
                super.set(i%9,i/9, content, action)
            }
        }
    }

    fun replaceContents(old: ItemStack, new: ItemStack, runnable: ActionItem.() -> Unit = {}): CustomContentsSyncGui {
        repeat(removeContents(old)) { add(new, false, runnable::invoke) }
        return this
    }

    fun next() {
        update(page+1)
    }

    fun back() {
        update(page-1)
    }

    fun removeContents(actionItem: ActionItem, update: Boolean = false): Int {
        val contentsAmount = contents.count(actionItem::equals)
        contents.remove(actionItem)
        if(update) { update() }
        return contentsAmount
    }

    fun removeContents(itemStack: ItemStack, update: Boolean = false): Int {
        val contentsAmount: Int
        contents.keys.filter { itemStack==it.itemStack }.apply {
            contentsAmount = this.size
            forEach(contents::remove)
        }
        if(update) { update() }
        return contentsAmount
    }

    fun clearContents(): CustomContentsSyncGui {
        contents.clear()
        return this
    }

    override fun open(player: Player) {
        open(player, page, SORT_BY_DISPLAY_NAME)
    }

    fun <T> open(player: Player, page: Int, sort: (ActionItem)->Comparable<T>?) {
        this.sort = sort
        update(page)
        super.open(player)
    }

    override fun clone(): CustomContentsSyncGui {
        val customContentsGui = CustomContentsSyncGui(plugin, size, title, startX, startY, endX, endY, runnable)
        for(x in 0..8) { for(y in 0..size) {
            customContentsGui.set(x, y, this.get(x, y)?.clone()?:continue)
        } }
        contents.forEach { customContentsGui.add(it.key,false,it.value) }
        return customContentsGui
    }

    /**
     * initは最後に置いておいてね
     */
    init {
        runnable.invoke(this)
    }

    companion object {

        val SORT_BY_DISPLAY_NAME: (ActionItem)->Comparable<String>? = { it.itemStack.itemMeta?.displayName?:it.itemStack.type.toString() }
        val SORT_BY_AMOUNT: (ActionItem)->Comparable<Int>? = { it.itemStack.amount }

        fun JavaPlugin.createCustomContentsGui(size: Int, title: String, startX: Int, startY: Int, endX: Int, endY: Int, runnable: CustomContentsSyncGui.() -> Unit = {}): CustomContentsSyncGui {
            return CustomContentsSyncGui(this, size, title, startX, startY, endX, endY, runnable)
        }
    }
}