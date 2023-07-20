package dev.mr3n.paperallinone.objects

import org.bukkit.Location
import org.bukkit.entity.Display
import org.bukkit.util.Vector

@Suppress("unused")
class RelativeObject(location: Location) {

    var location = location.clone().setDirection(Vector(0,0,0))

    private val displayEntities = mutableMapOf<Display, DisplayEntityInfo>()
    private val rotation = Vector()

    fun add(entity: Display, vector: Vector, rotateX: Boolean = true, rotateY: Boolean = true, rotateZ: Boolean = true): RelativeObject {
        this.displayEntities[entity] = DisplayEntityInfo(vector, rotateX, rotateY, rotateZ)
        entity.boundingBox
        return this
    }

    fun add(entity: Display, x: Double, y: Double, z: Double, rotateX: Boolean = true, rotateY: Boolean = true, rotateZ: Boolean = true): RelativeObject = this.add(entity, Vector(x,y,z), rotateX, rotateY, rotateZ)

    fun <T: Display> add(type: Class<T>, x: Double, y: Double, z: Double, rotateX: Boolean = true, rotateY: Boolean = true, rotateZ: Boolean = true): T {
        val world = this.location.world!!
        val entity = world.spawn(this.location,type)
        this.add(entity, x, y, z, rotateX, rotateY, rotateZ)
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
        this.displayEntities.forEach { (entity, entityInfo) ->
            val loc = this.location.clone().setDirection(Vector(0,0,0))
            loc.add(entityInfo.vector.clone().rotateAroundX(this.rotation.x).rotateAroundY(this.rotation.y).rotateAroundZ(this.rotation.z))
            if(entityInfo.rotateY) {
                loc.yaw = -Math.toRadians(loc.yaw.toDouble()).toFloat()
            }
            if(entityInfo.rotateX) {
                loc.pitch = Math.toRadians(loc.pitch.toDouble()).toFloat()
            }
            entity.teleport(loc)
        }
    }

    fun teleport(location: Location, applyRot: Boolean) {
        this.location = location.clone()
        if(applyRot) {
            this.rotation.y = -Math.toRadians(location.yaw.toDouble())
            this.rotation.x = Math.toRadians(location.pitch.toDouble())
        }
        this.update()
    }
}