@file:Suppress("unused")

package dev.mr3n.paperallinone.text

import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

var ItemStack.displayName: Component?
    get() = this.itemMeta?.displayName()
    set(value) {
        val itemMeta = this.itemMeta
        itemMeta?.displayName(value)
        this.itemMeta = itemMeta
    }

var ItemStack.lore: List<Component>?
    get() = this.itemMeta?.lore()
    set(value) {
        val itemMeta = this.itemMeta
        itemMeta?.lore(value)
        this.itemMeta = itemMeta
    }