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
object LongRangeSerializer: KSerializer<LongRange> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("LongRange") {
        element<Long>("minimum")
        element<Long>("maximum")
    }

    override fun serialize(encoder: Encoder, value: LongRange) {
        encoder.encodeStructure(descriptor) {
            encodeLongElement(descriptor, 0, value.first)
            encodeLongElement(descriptor, 0, value.last)
        }
    }

    override fun deserialize(decoder: Decoder): LongRange {
        var first: Long = 0
        var last: Long = 0
        decoder.decodeStructure(descriptor) {
            while(true) {
                when(decodeElementIndex(descriptor)) {
                    0 -> first = decodeLongElement(descriptor, 0)
                    1 -> last = decodeLongElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
        }
        return first..last
    }
}