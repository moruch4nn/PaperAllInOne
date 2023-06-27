package dev.mr3n.paperallinone.configuration.kotlinx.serialization.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.inventory.ItemStack

@Serializer(forClass = ItemStack::class)
@OptIn(ExperimentalSerializationApi::class)
object IntRangeSerializer: KSerializer<IntRange> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("IntRange") {
        element<Int>("minimum")
        element<Int>("maximum")
    }

    override fun serialize(encoder: Encoder, value: IntRange) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.first)
            encodeIntElement(descriptor, 0, value.last)
        }
    }

    override fun deserialize(decoder: Decoder): IntRange {
        var first = 0
        var last = 0
        decoder.decodeStructure(descriptor) {
            while(true) {
                when(decodeElementIndex(descriptor)) {
                    0 -> first = decodeIntElement(descriptor, 0)
                    1 -> last = decodeIntElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
        }
        return first..last
    }
}