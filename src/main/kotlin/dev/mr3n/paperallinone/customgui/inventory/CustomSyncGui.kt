package dev.mr3n.paperallinone.customgui.inventory

import dev.mr3n.paperallinone.customgui.CustomGuiEventListener
import dev.mr3n.paperallinone.customgui.CustomGuiEvents
import dev.mr3n.paperallinone.customgui.UniqueInventoryHolder
import dev.mr3n.paperallinone.customgui.CustomGuiClickEvent.Companion.asCustomGuiClickEvent
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * setItemや、アイテムを取る、入れるなどの動作がリアルタイムで反映されます。
 * チェストやかまどなどのリアルタイムで反映が必要なGUIに使用できます。
 * 注: 一度作るとリスナーを削除できません。
 */
open class CustomSyncGui(plugin: JavaPlugin, title: String, size: Int, runnable: CustomSyncGui.() -> Unit = {}): CustomGui(plugin, title, size) {

    private val listener: CustomGuiEvents

    /**
     * リスナーを削除します。再生成するにはCustomSyncGuiを再度作成してください。
     */
    fun unregisterGuiListener() {
        CustomGuiEventListener.unregister(listener)
    }

    override fun open(player: Player) {
        player.openInventory(this.raw())
    }

    init {
        isSync = true
        runnable.invoke(this)
        listener = object: CustomGuiEvents() {
            override val uniqueInventoryHolder: UniqueInventoryHolder = this@CustomSyncGui.uniqueInventoryHolder
            override val javaPlugin = plugin
            override fun onInventoryClick(event: InventoryClickEvent) {
                this@CustomSyncGui.actionItems.filter { it.isSimilarTag(event.currentItem) }.forEach { actionItem ->
                        if (!actionItem.allowGet) {
                            event.isCancelled = true
                            (event.whoClicked as Player).playSound(event.whoClicked.location, actionItem.clickSound,1F,1F)
                        }
                        actionItem.actions().filter { it.key == event.click }.forEach {
                            it.value.invoke(event.asCustomGuiClickEvent(this@CustomSyncGui))
                        }
                    }
            }
        }
        CustomGuiEventListener.register(listener)
    }

    companion object {
        /**
         * CustomGuiをCustomSyncGuiにCastします。
         */
        fun CustomGui.asSync(runnable: CustomSyncGui.() -> Unit = {}): CustomSyncGui {
            val javaPlugin = this::class.java.getDeclaredField("plugin").also { it.isAccessible = true }.get(this) as JavaPlugin
            return CustomSyncGui(javaPlugin, this.title, this.size, runnable).also {
                for(x in 0..8) { for(y in 0..this.size) { it.set(x, y, this.get(x, y)?.clone()) } }
            }
        }

        fun JavaPlugin.createCustomSyncGui(size: Int, title: String, runnable: CustomGui.() -> Unit = {}): CustomGui {
            return CustomSyncGui(this, title, size, runnable)
        }
    }
}