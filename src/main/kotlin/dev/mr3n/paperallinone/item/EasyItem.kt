package dev.mr3n.paperallinone.item

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

open class EasyItem(
    material: Material,
    displayName: Component? = null,
    lore: List<Component> = listOf(),
    itemFlags: Set<ItemFlag> = setOf(),
    enchantments: Map<Enchantment, Int> = mapOf(),
    loreColor: TextColor? = null,
    customModelData: Int = 0,
    vararg dataContainer: Pair<NamespacedKey, Any>
) : ItemStack(material) {
    init {
        this.itemMeta = this.itemMeta?.also { itemMeta ->
            displayName?.also(itemMeta::displayName)
            itemMeta.setCustomModelData(customModelData)
            itemMeta.lore(lore.map { if(loreColor == null) it else it.color(loreColor) })
            itemFlags.forEach(itemMeta::addItemFlags)
            enchantments.forEach { itemMeta.addEnchant(it.key, it.value, true) }
            dataContainer.forEach { (key, any) ->
                when(any) {
                    is String -> {
                        itemMeta.persistentDataContainer.set(key, PersistentDataType.STRING, any)
                    }
                    is Double -> {
                        itemMeta.persistentDataContainer.set(key, PersistentDataType.DOUBLE, any)
                    }
                    is Int -> {
                        itemMeta.persistentDataContainer.set(key, PersistentDataType.INTEGER, any)
                    }
                    is Float -> {
                        itemMeta.persistentDataContainer.set(key, PersistentDataType.FLOAT, any)
                    }
                }
            }
        }
    }
}