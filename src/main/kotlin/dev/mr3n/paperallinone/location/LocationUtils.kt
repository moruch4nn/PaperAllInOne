package dev.mr3n.paperallinone.location

import org.bukkit.Location

fun Location.targetTo(target: Location): Location {
    return this.setDirection(target.toVector().subtract(this.toVector()))
}

fun Location.moveFront(length: Double): Location {
    return this.add(this.direction.normalize().multiply(length))
}