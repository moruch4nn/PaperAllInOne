package dev.moru3.minepie.customgui

import dev.mr3n.paperallinone.customgui.CustomGuiClickEvent
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.*

open class ActionItem(val uniqueTagKey: NamespacedKey,val itemStack: ItemStack): ItemStack(itemStack) {

    private val actions: MutableMap<ClickType, (CustomGuiClickEvent)->Unit> = mutableMapOf()

    private val uniqueId: String = UUID.randomUUID().toString()

    fun isSimilarTag(itemStack: ItemStack?) = itemStack?.itemMeta?.persistentDataContainer?.get(uniqueTagKey, PersistentDataType.STRING) == uniqueId

    var slot: Int? = null
        private set

    var clickSound: Sound = Sound.UI_BUTTON_CLICK

    var allowGet = false

    init {
        itemStack.itemMeta = itemStack.itemMeta?.also { meta -> meta.persistentDataContainer.set(uniqueTagKey, PersistentDataType.STRING,uniqueId) }
    }

    fun action(vararg clickType: ClickType, runnable: (CustomGuiClickEvent)->Unit) {
        clickType.forEach {
            actions[it] = runnable
        }
    }

    fun actions(): Map<ClickType, (CustomGuiClickEvent)->Unit> {
        return actions.toMap()
    }

    constructor(uniqueTagKey: NamespacedKey,itemStack: ItemStack, slot: Int?): this(uniqueTagKey, itemStack) {
        this.slot = slot
    }

    constructor(uniqueTagKey: NamespacedKey, itemStack: ItemStack, slot: Int? = null, actions: MutableMap<ClickType, (CustomGuiClickEvent)->Unit>):
            this(uniqueTagKey, itemStack, slot) {
                actions.forEach(this.actions::put)
            }

    override fun clone(): ActionItem {
        val actionItem = ActionItem(uniqueTagKey = uniqueTagKey,itemStack = itemStack.clone(), slot = slot, actions = actions)
        actionItem.allowGet = allowGet
        return actionItem
    }

    fun copy(slot: Int?): ActionItem {
        val actionItem = ActionItem(uniqueTagKey = uniqueTagKey,itemStack = itemStack.clone(), slot = slot?:this.slot, actions = actions)
        actionItem.allowGet = allowGet
        return actionItem
    }
}