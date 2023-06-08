package dev.mr3n.paperallinone.customgui

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.util.*

class UniqueInventoryHolder: InventoryHolder {
    val uniqueId = UUID.randomUUID()
    override fun getInventory(): Inventory = throw Exception()
}