package dev.mr3n.paperallinone.location

import org.bukkit.Location

fun Location.lookAt(target: Location): Location {
    return this.setDirection(target.toVector().subtract(this.toVector()))
}

fun Location.moveFront(length: Double): Location {
    return this.add(this.direction.normalize().multiply(length))
}

fun Location.lineTo(to: Location, roughness: Double, task: (Location)->Unit) {
    val from = this.clone()
    val now = from.clone()
    val direction = to.toVector().subtract(from.toVector()).normalize().multiply(roughness)
    if(from.distance(to) <= 0.0) { return }
    val distance = to.distance(from)
    while(true) {
        val loc = now.add(direction)
        if(from.distance(now) >= distance) { break }
        task.invoke(loc)
    }
}

fun Location.fill(to: Location, roughness: Double, task: (Location)->Unit) {
    val from = this
    val world = from.world
    var x = minOf(from.x,to.x)
    val maxX = maxOf(from.x,to.x)
    var y = minOf(from.y,to.y)
    val maxY = maxOf(from.y,to.y)
    var z = minOf(from.z,to.z)
    val maxZ = maxOf(from.z,to.z)
    while(true) {
        while(true) {
            while(true) {
                task.invoke(Location(world, x, y, z))
                z+=roughness
                if(z > maxZ) { break }
            }
            z = minOf(from.z,to.z)
            x+=roughness
            if(x > maxX) { break }
        }
        x = minOf(from.x,to.x)
        y+=roughness
        if(y > maxY) { break }
    }
}

fun Location.wall(to: Location, roughness: Double, task: (Location) -> Unit) {
    val minX = minOf(this.x,to.x)
    val maxX = maxOf(this.x,to.x)
    val minY = minOf(this.y,to.y)
    val maxY = maxOf(this.y,to.y)
    val minZ = minOf(this.z,to.z)
    val maxZ = maxOf(this.z,to.z)
    val world = this.world
    Location(world, minX, minY, minZ).fill(Location(world, minX, maxY, maxZ), roughness, task)
    Location(world, minX, minY, maxZ).fill(Location(world, maxX, maxY, maxZ), roughness, task)
    Location(world, minX, minY, minZ).fill(Location(world, minX, maxY, maxZ), roughness, task)
    Location(world, minX, minY, minZ).fill(Location(world, maxX, maxY, minZ), roughness, task)
}

fun Location.box(to: Location, roughness: Double, task: (Location) -> Unit) {
    val minX = minOf(this.x,to.x)
    val maxX = maxOf(this.x,to.x)
    val minY = minOf(this.y,to.y)
    val maxY = maxOf(this.y,to.y)
    val minZ = minOf(this.z,to.z)
    val maxZ = minOf(this.z,to.z)
    val world = this.world
    Location(world, minX, minY, minZ).fill(Location(world, minX, maxY, maxZ), roughness, task)
    Location(world, minX, minY, maxZ).fill(Location(world, maxX, maxY, maxZ), roughness, task)
    Location(world, minX, minY, minZ).fill(Location(world, minX, maxY, maxZ), roughness, task)
    Location(world, minX, minY, minZ).fill(Location(world, maxX, maxY, minZ), roughness, task)
    Location(world, minX, minY, minZ).fill(Location(world, maxX, minY, maxZ), roughness, task)
    Location(world, minX, maxY, minZ).fill(Location(world, maxX, maxY, maxZ), roughness, task)
}