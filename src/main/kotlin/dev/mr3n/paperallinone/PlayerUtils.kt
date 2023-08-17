package dev.mr3n.paperallinone

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun Player.giveItemOrDrop(itemStack: ItemStack) {
    this.inventory.addItem(itemStack).forEach { (_, dropItem) ->
        val item = this.world.dropItem(this.location,dropItem)
        item.owner = this.uniqueId
        item.pickupDelay = 0
    }
}