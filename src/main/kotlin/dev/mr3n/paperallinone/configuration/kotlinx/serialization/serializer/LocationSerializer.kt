package dev.mr3n.paperallinone.configuration.kotlinx.serialization.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.Location
import org.bukkit.World

@Serializer(forClass = Location::class)
@OptIn(ExperimentalSerializationApi::class)
object LocationSerializer: KSerializer<Location> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Location") {
        element<World?>("world", isOptional = true)
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
        element<Float>("yaw")
        element<Float>("pitch")
    }

    override fun serialize(encoder: Encoder, value: Location) {
        encoder.encodeStructure(descriptor) {
            encodeNullableSerializableElement(descriptor, 0, WorldSerializer, value.world)
            encodeDoubleElement(descriptor, 1, value.x)
            encodeDoubleElement(descriptor, 2, value.y)
            encodeDoubleElement(descriptor, 3, value.z)
            encodeFloatElement(descriptor, 4, value.yaw)
            encodeFloatElement(descriptor, 5, value.pitch)
        }
    }

    override fun deserialize(decoder: Decoder): Location {
        var world: World? = null
        var x = 0.0
        var y = 0.0
        var z = 0.0
        var yaw = 0f
        var pitch = 0f
        decoder.decodeStructure(descriptor) {
            while(true) {
                when(decodeElementIndex(descriptor)) {
                    0 -> world = decodeNullableSerializableElement(descriptor, 0, WorldSerializer)
                    1 -> x = decodeDoubleElement(descriptor, 1)
                    2 -> y = decodeDoubleElement(descriptor, 2)
                    3 -> z = decodeDoubleElement(descriptor, 3)
                    4 -> yaw = decodeFloatElement(descriptor, 4)
                    5 -> pitch = decodeFloatElement(descriptor, 5)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
        }
        return Location(world, x, y, z, yaw, pitch)
    }
}