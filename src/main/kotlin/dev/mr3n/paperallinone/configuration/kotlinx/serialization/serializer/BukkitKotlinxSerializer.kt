package dev.mr3n.paperallinone.configuration.kotlinx.serialization.serializer

import kotlinx.serialization.modules.SerializersModule
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import java.util.UUID

object BukkitKotlinxSerializer {
    val module = SerializersModule {
        contextual(ItemStack::class, ItemStackSerializer)
        contextual(Location::class, LocationSerializer)
        contextual(UUID::class, UUIDSerializer)
        contextual(Vector::class, VectorSerializer)
        contextual(World::class, WorldSerializer)
        contextual(IntRange::class, IntRangeSerializer)
        contextual(LongRange::class, LongRangeSerializer)
    }
}