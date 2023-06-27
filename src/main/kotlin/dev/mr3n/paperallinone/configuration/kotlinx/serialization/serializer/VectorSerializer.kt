package dev.mr3n.paperallinone.configuration.kotlinx.serialization.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.util.Vector

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Vector::class)
object VectorSerializer: KSerializer<Vector> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Vector") {
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
    }

    override fun serialize(encoder: Encoder, value: Vector) {
        encoder.encodeStructure(descriptor) {
            encodeDoubleElement(descriptor, 0, value.x)
            encodeDoubleElement(descriptor, 1, value.y)
            encodeDoubleElement(descriptor, 2, value.z)
        }
    }

    override fun deserialize(decoder: Decoder): Vector {
        var x = 0.0
        var y = 0.0
        var z = 0.0
        decoder.decodeStructure(descriptor) {
            while(true) {
                when(decodeElementIndex(descriptor)) {
                    0 -> x = decodeDoubleElement(descriptor, 0)
                    1 -> y = decodeDoubleElement(descriptor, 1)
                    2 -> z = decodeDoubleElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
        }
        return Vector(x,y,z)
    }
}