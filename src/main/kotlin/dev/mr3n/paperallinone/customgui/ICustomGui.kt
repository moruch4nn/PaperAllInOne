package dev.mr3n.paperallinone.customgui

import dev.moru3.minepie.customgui.ActionItem
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

interface ICustomGui {

    var title: Component
    val size: Int

    val uniqueInventoryHolder: UniqueInventoryHolder

    val isSync: Boolean

    val uniqueTagKey: NamespacedKey

    /**
     * setItemで追加したアイテムを削除します。
     * @param tempItemStack 削除する対象のアイテム。
     * @param ignoreAmount tempItemStackのamountを無視して削除します。
     * @param limit 削除するアイテムのリミットを設定します。
     * @param sortType アイテムをソートしてから削除します。
     */
    fun remove(itemStack: ItemStack)

    fun closeListener(process: (InventoryCloseEvent)->Unit)

    /**
     * アイテムを削除します。
     */
    fun remove(x: Int, y: Int)

    /**
     * 指定した場所にアイテムを配置します。
     */
    fun set(x: Int, y: Int, itemStack: ItemStack?, runnable: ActionItem.() -> Unit = {})

    /**
     * 指定した場所にアクションアイテムを配置します。
     */
    fun set(x: Int, y: Int, actionItem: ActionItem?, runnable: ActionItem.() -> Unit = {})

    /**
     * 指定された位置のActionItemを返します。
     */
    fun get(x: Int, y: Int, runnable: ActionItem.() -> Unit = {}): ActionItem?

    /**
     * 今までに設定したCustomGuiをCloneしてorg.bukkit.inventory.Inventoryで返します。
     */
    fun inventory(): Inventory

    /**
     * 今までに設定したCustomGuiをCloneせずにorg.bukkit.inventory.Inventoryで返します。
     */
    fun raw(): Inventory

    /**
     * インベントリを開きます。
     */
    fun open(player: Player)

    /**
     * CustomInventoryをClone(non-deep)します。
     */
    fun clone(): ICustomGui

    fun onClick(task: (InventoryClickEvent)->Unit)

    /**
     * GUIの内容を別のCustomGuiに置き換えます。
     */
    fun replace(iCustomGui: ICustomGui)

    fun onClose(event: InventoryCloseEvent)

    enum class SortType {
        AMOUNT,
        DISPLAY_NAME,
        DATE,
    }
}