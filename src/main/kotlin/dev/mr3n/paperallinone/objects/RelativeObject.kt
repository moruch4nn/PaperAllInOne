package dev.mr3n.paperallinone.objects

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

@Suppress("unused")
open class RelativeObject(location: Location) {

    var location = location.clone()

    private val displayEntities = mutableMapOf<Entity, DisplayEntityInfo>()
    private val children = mutableListOf<Pair<RelativeObject,DisplayEntityInfo>>()
    private val rotation = Vector()

    fun add(entity: Entity, vector: Vector, rotateX: Boolean = true, rotateY: Boolean = true, rotateZ: Boolean = true): RelativeObject {
        this.displayEntities[entity] = DisplayEntityInfo(vector.clone(), rotateX, rotateY, rotateZ)
        return this
    }

    fun add(entity: RelativeObject, x: Double, y: Double, z: Double, rotateX: Boolean = true, rotateY: Boolean = true, rotateZ: Boolean = true): RelativeObject = this.add(entity, Vector(x,y,z), rotateX, rotateY, rotateZ)

    fun add(relativeObject: RelativeObject, vector: Vector, rotateX: Boolean = true, rotateY: Boolean = true, rotateZ: Boolean = true): RelativeObject {
        this.children.add(relativeObject to DisplayEntityInfo(vector.clone(), rotateX, rotateY, rotateZ))
        return this
    }

    fun add(entity: Entity, x: Double, y: Double, z: Double, rotateX: Boolean = true, rotateY: Boolean = true, rotateZ: Boolean = true): RelativeObject = this.add(entity, Vector(x,y,z), rotateX, rotateY, rotateZ)

    fun <T: Entity> add(type: Class<T>, x: Double, y: Double, z: Double, rotateX: Boolean = true, rotateY: Boolean = true, rotateZ: Boolean = true): T {
        val world = this.location.world!!
        val entity = world.spawn(this.location,type)
        this.add(entity, x, y, z, rotateX, rotateY, rotateZ)
        return entity
    }

    fun rotate(x: Double? = null,y: Double? = null,z: Double? = null) {
        if(x != null) {
            this.rotation.x = x
        }
        if(y != null) {
            this.rotation.y = y
        }
        if(z != null) {
            this.rotation.z = z
        }
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
        this.children.forEach { (relativeObject, entityInfo) ->
            val loc = this.location.clone()
            var vector = entityInfo.vector.clone()

            if(entityInfo.rotateX) {
                vector = vector.rotateAroundX(this.rotation.x)
            } else {
                loc.pitch = relativeObject.location.pitch
            }
            if(entityInfo.rotateY) {
                vector = vector.rotateAroundY(this.rotation.y)
            } else {
                loc.yaw = relativeObject.location.yaw
            }
            if(entityInfo.rotateZ) {
                vector = vector.rotateAroundZ(this.rotation.z)
            }
            loc.add(vector)
            relativeObject.teleport(loc,true)
        }
        this.displayEntities.forEach { (entity, entityInfo) ->
            val loc = this.location.clone()
            var vector = entityInfo.vector.clone()

            if(entityInfo.rotateX) {
                vector = vector.rotateAroundX(this.rotation.x)
            } else {
                loc.pitch = entity.location.pitch
            }
            if(entityInfo.rotateY) {
                vector = vector.rotateAroundY(this.rotation.y)
            } else {
                loc.yaw = entity.location.yaw
            }
            if(entityInfo.rotateZ) {
                vector = vector.rotateAroundZ(this.rotation.z)
            }
            loc.add(vector)
            entity.teleport(loc)
        }
    }

    fun direction(yaw: Float, pitch: Float) {
        this.rotation.y = -Math.toRadians(location.yaw.toDouble())
        this.rotation.x = Math.toRadians(location.pitch.toDouble())
        this.update()
    }

    fun yaw(yaw: Float) {
        this.rotation.y = -Math.toRadians(yaw.toDouble())
        this.location.yaw = yaw
        this.update()
    }

    fun pitch(pitch: Float) {
        this.rotation.x = Math.toRadians(pitch.toDouble())
        this.location.pitch = pitch
        this.update()
    }

    fun teleport(location: Location, applyRot: Boolean) {
        if(applyRot) {
            this.rotation.y = -Math.toRadians(location.yaw.toDouble())
            this.rotation.x = Math.toRadians(location.pitch.toDouble())
        } else {
            location.direction = this.location.direction
        }
        this.location = location.clone()

        this.update()
    }
}