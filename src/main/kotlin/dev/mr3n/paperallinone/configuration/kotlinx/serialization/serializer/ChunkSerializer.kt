package dev.mr3n.paperallinone.configuration.kotlinx.serialization.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.Chunk
import org.bukkit.World

@Serializer(forClass = Chunk::class)
@OptIn(ExperimentalSerializationApi::class)
object ChunkSerializer: KSerializer<Chunk> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Chunk") {
        element<World>("world")
        element<Int>("x")
        element<Int>("z")
    }

    override fun serialize(encoder: Encoder, value: Chunk) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, WorldSerializer, value.world)
            encodeIntElement(descriptor, 1, value.x)
            encodeIntElement(descriptor, 2, value.z)
        }
    }

    override fun deserialize(decoder: Decoder): Chunk {
        var world: World? = null
        var x: Int? = null
        var z: Int? = null
        decoder.decodeStructure(descriptor) {
            while(true) {
                when(decodeElementIndex(descriptor)) {
                    0 -> world = decodeSerializableElement(descriptor, 0, WorldSerializer)
                    1 -> x = decodeIntElement(descriptor, 1)
                    2 -> z = decodeIntElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
        }
        return world!!.getChunkAtAsync(x!!,z!!).get()
    }
}