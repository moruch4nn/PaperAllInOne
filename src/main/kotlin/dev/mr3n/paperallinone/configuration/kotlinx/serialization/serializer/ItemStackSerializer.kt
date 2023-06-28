package dev.mr3n.paperallinone.configuration.kotlinx.serialization.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.inventory.ItemStack
import java.util.*

@Serializer(forClass = ItemStack::class)
@OptIn(ExperimentalSerializationApi::class)
object ItemStackSerializer: KSerializer<ItemStack> {
    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ItemStack", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ItemStack) {
        encoder.encodeString(this.encoder.encodeToString(value.serializeAsBytes()))
    }

    override fun deserialize(decoder: Decoder): ItemStack {
        return ItemStack.deserializeBytes(this.decoder.decode(decoder.decodeString()))
    }
}