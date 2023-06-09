package dev.mr3n.paperallinone.configuration.kotlinx.serialization.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.inventory.ItemStack

@Serializer(forClass = ItemStack::class)
@OptIn(ExperimentalSerializationApi::class)
object ItemStackSerializer: KSerializer<ItemStack> {
    private val serializer = ByteArraySerializer()
    override val descriptor: SerialDescriptor = SerialDescriptor("ItemStack", serializer.descriptor)

    override fun serialize(encoder: Encoder, value: ItemStack) {
        encoder.encodeSerializableValue(serializer, value.serializeAsBytes())
    }

    override fun deserialize(decoder: Decoder): ItemStack {
        return ItemStack.deserializeBytes(decoder.decodeSerializableValue(serializer))
    }
}