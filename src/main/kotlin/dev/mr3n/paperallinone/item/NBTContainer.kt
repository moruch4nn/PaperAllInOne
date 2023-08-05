@file:Suppress("unused","MemberVisibilityCanBePrivate")

package dev.mr3n.paperallinone.item

import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType
import java.util.*

internal object KeyManager {

    private val keys = mutableMapOf<String, NamespacedKey>()

    fun key(enum: Enum<*>): NamespacedKey {
        val name = enum::class.java.name
        return keys.getOrPut(name) { NamespacedKey("paper-all-in-one", name) }
    }
}

class NBTContainer(private val holder: PersistentDataHolder) {
    fun longArray(key: NamespacedKey, value: LongArray): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.LONG_ARRAY, value)
        return this
    }

    fun longArray(key: Enum<*>, value: LongArray) = this.longArray(KeyManager.key(key), value)

    fun longArray(key: NamespacedKey): LongArray? {
        return holder.persistentDataContainer.get(key, PersistentDataType.LONG_ARRAY)
    }

    fun longArray(key: Enum<*>) = this.longArray(KeyManager.key(key))

    fun integerArray(key: NamespacedKey, value: IntArray): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.INTEGER_ARRAY, value)
        return this
    }

    fun integerArray(key: Enum<*>, value: IntArray) = this.integerArray(KeyManager.key(key), value)

    fun integerArray(key: NamespacedKey): IntArray? {
        return holder.persistentDataContainer.get(key, PersistentDataType.INTEGER_ARRAY)
    }

    fun integerArray(key: Enum<*>): IntArray? = this.integerArray(KeyManager.key(key))

    fun byteArray(key: NamespacedKey, value: ByteArray): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.BYTE_ARRAY, value)
        return this
    }

    fun byteArray(key: Enum<*>, value: ByteArray) = this.byteArray(KeyManager.key(key), value)

    fun byteArray(key: NamespacedKey): ByteArray? {
        return holder.persistentDataContainer.get(key, PersistentDataType.BYTE_ARRAY)
    }

    fun byteArray(key: Enum<*>) = this.byteArray(KeyManager.key(key))

    fun double(key: NamespacedKey, value: Double): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.DOUBLE, value)
        return this
    }

    fun double(key: Enum<*>, value: Double) = this.double(KeyManager.key(key), value)

    fun double(key: NamespacedKey): Double? {
        return holder.persistentDataContainer.get(key, PersistentDataType.DOUBLE)
    }

    fun double(key: Enum<*>) = this.double(KeyManager.key(key))

    fun float(key: NamespacedKey, value: Float): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.FLOAT, value)
        return this
    }

    fun float(key: Enum<*>, value: Float) = this.float(KeyManager.key(key), value)

    fun float(key: NamespacedKey): Float? {
        return holder.persistentDataContainer.get(key, PersistentDataType.FLOAT)
    }

    fun float(enum: Enum<*>) = this.float(KeyManager.key(enum))

    fun long(key: NamespacedKey, value: Long): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.LONG, value)
        return this
    }

    fun long(key: Enum<*>, value: Long) = this.long(KeyManager.key(key), value)

    fun long(key: NamespacedKey): Long? {
        return holder.persistentDataContainer.get(key, PersistentDataType.LONG)
    }

    fun long(key: Enum<*>) = this.long(KeyManager.key(key))

    fun integer(key: NamespacedKey, value: Int): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.INTEGER, value)
        return this
    }

    fun integer(key: Enum<*>, value: Int) = this.integer(KeyManager.key(key), value)

    fun integer(key: NamespacedKey): Int? {
        return holder.persistentDataContainer.get(key, PersistentDataType.INTEGER)
    }

    fun integer(key: Enum<*>) = this.integer(KeyManager.key(key))

    fun short(key: NamespacedKey, value: Short): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.SHORT, value)
        return this
    }

    fun short(key: Enum<*>, value: Short) = this.short(KeyManager.key(key), value)

    fun short(key: NamespacedKey): Short? {
        return holder.persistentDataContainer.get(key, PersistentDataType.SHORT)
    }

    fun short(key: Enum<*>) = this.short(KeyManager.key(key))

    fun byte(key: NamespacedKey, value: Byte): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.BYTE, value)
        return this
    }

    fun byte(key: Enum<*>, value: Byte) = this.byte(KeyManager.key(key), value)

    fun byte(key: NamespacedKey): Byte? {
        return holder.persistentDataContainer.get(key, PersistentDataType.BYTE)
    }

    fun byte(key: Enum<*>) = this.byte(KeyManager.key(key))

    fun string(key: NamespacedKey, value: String): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.STRING, value)
        return this
    }

    fun string(enum: Enum<*>, value: String) = this.string(KeyManager.key(enum), value)

    fun string(key: NamespacedKey): String? {
        return holder.persistentDataContainer.get(key, PersistentDataType.STRING)
    }

    fun string(enum: Enum<*>) = this.string(KeyManager.key(enum))

    fun uniqueId(key: NamespacedKey, value: UUID): NBTContainer {
        return this.string(key, "$value")
    }

    fun uniqueId(key: Enum<*>, value: UUID) = this.uniqueId(KeyManager.key(key), value)

    fun uniqueId(key: NamespacedKey): UUID? {
        return UUID.fromString(this.string(key))
    }

    fun uniqueId(key: Enum<*>) = this.uniqueId(KeyManager.key(key))

    fun boolean(key: NamespacedKey, value: Boolean): NBTContainer {
        holder.persistentDataContainer.set(key, PersistentDataType.BOOLEAN, value)
        return this
    }

    fun boolean(key: Enum<*>, value: Boolean) = this.boolean(KeyManager.key(key), value)

    fun boolean(key: NamespacedKey): Boolean? {
        return holder.persistentDataContainer.get(key, PersistentDataType.BOOLEAN)
    }

    fun boolean(key: Enum<*>) = this.boolean(KeyManager.key(key))

    fun remove(key: NamespacedKey): NBTContainer {
        holder.persistentDataContainer.remove(key)
        return this
    }

    fun remove(key: Enum<*>) = this.remove(KeyManager.key(key))
}

fun ItemMeta.nbt(): NBTContainer = NBTContainer(this)

fun Entity.nbt(): NBTContainer = NBTContainer(this)

fun <T> ItemStack.nbt(block: NBTContainer.()->T): T {
    val itemMeta = this.itemMeta
    val value = block.invoke(itemMeta.nbt())
    this.itemMeta = itemMeta
    return value
}

fun <T> Entity.nbt(block: NBTContainer.() -> T): T {
    return block.invoke(NBTContainer(this))
}