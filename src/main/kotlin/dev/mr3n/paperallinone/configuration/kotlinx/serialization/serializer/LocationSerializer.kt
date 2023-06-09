package dev.mr3n.paperallinone.configuration.kotlinx.serialization.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
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
        return decoder.decodeStructure(descriptor) {
            val world = decodeNullableSerializableElement(descriptor, 0, WorldSerializer)
            val x = decodeDoubleElement(descriptor, 1)
            val y = decodeDoubleElement(descriptor, 2)
            val z = decodeDoubleElement(descriptor, 3)
            val yaw = decodeFloatElement(descriptor, 4)
            val pitch = decodeFloatElement(descriptor, 5)
            return@decodeStructure Location(world, x, y, z, yaw, pitch)
        }
    }
}