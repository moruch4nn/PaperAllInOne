package dev.mr3n.paperallinone.location

import org.bukkit.Location

fun Location.lookAt(target: Location): Location {
    return this.setDirection(target.toVector().subtract(this.toVector()))
}

fun Location.moveFront(length: Double): Location {
    return this.add(this.direction.normalize().multiply(length))
}

fun Location.lineTo(to: Location, roughness: Int, task: (Location)->Unit) {
    val from = this.clone()
    val now = from.clone()
    val direction = to.toVector().subtract(from.toVector()).normalize().multiply(roughness)
    val distance = to.distance(from)
    while(true) {
        val loc = now.add(direction)
        if(from.distance(now) >= distance) { break }
        task.invoke(loc)
    }
}