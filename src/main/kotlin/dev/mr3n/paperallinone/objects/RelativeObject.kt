package dev.mr3n.paperallinone.objects

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

@Suppress("unused")
class RelativeObject(var location: Location) {

    private val entities = mutableMapOf<Entity, Vector>()
    private val rotation = Vector()

    fun add(entity: Entity, vector: Vector): RelativeObject {
        this.entities[entity] = vector
        return this
    }

    fun add(entity: Entity, x: Double, y: Double, z: Double): RelativeObject = this.add(entity, Vector(x,y,z))

    fun <T: Entity> add(type: Class<T>, x: Double, y: Double, z: Double): T {
        val world = this.location.world!!
        val entity = world.spawn(this.location,type)
        this.add(entity, x, y, z)
        return entity
    }

    fun rotate(x: Double,y: Double,z: Double) {
        this.rotation.x = x
        this.rotation.y = y
        this.rotation.z = z
        this.update()
    }

    var rotateX: Double
        set(value) {
            this.rotation.x = value
            this.update()
        }
        get() = this.rotation.x


    var rotateY: Double
        set(value) {
            this.rotation.y = value
            this.update()
        }
        get() = this.rotation.y

    var rotateZ: Double
        set(value) {
            this.rotation.z = value
            this.update()
        }
        get() = this.rotation.z

    fun update() {
        this.entities.forEach { (entity, vector) ->
            val loc = this.location.clone()
            loc.add(vector.rotateAroundX(this.rotation.x).rotateAroundY(this.rotation.y).rotateAroundZ(this.rotation.z))
            entity.teleport(loc)
        }
    }

    fun teleport(location: Location, applyRot: Boolean) {
        this.location = location
        if(applyRot) {
            this.rotation.y = -Math.toRadians(location.yaw.toDouble())
            this.rotation.x = Math.toRadians(location.pitch.toDouble())
        }
        this.update()
    }
}