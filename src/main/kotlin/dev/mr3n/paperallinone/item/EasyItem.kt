package dev.mr3n.paperallinone.item

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

open class EasyItem(
    material: Material,
    displayName: Component? = null,
    lore: List<String> = listOf(),
    itemFlags: Set<ItemFlag> = setOf(),
    enchantments: Map<Enchantment, Int> = mapOf(),
    lorePrefix: TextColor? = TextColor.color(0xAAAAAA),
    customModelData: Int = 0,
) : ItemStack(material) {
    init {
        this.itemMeta = this.itemMeta?.also { itemMeta ->
            displayName?.also(itemMeta::displayName)
            itemMeta.setCustomModelData(customModelData)
            itemMeta.lore(lore.map { Component.text(it, Style.style(lorePrefix)) })
            itemFlags.forEach(itemMeta::addItemFlags)
            enchantments.forEach { itemMeta.addEnchant(it.key, it.value, true) }
        }
    }
}