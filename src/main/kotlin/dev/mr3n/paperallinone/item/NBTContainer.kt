@file:Suppress("unused")

package dev.mr3n.paperallinone.item

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*


class NBTContainer(private val itemMeta: ItemMeta) {
    fun longArray(key: NamespacedKey, value: LongArray): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.LONG_ARRAY, value)
        return this
    }

    fun longArray(key: NamespacedKey): LongArray? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.LONG_ARRAY)
    }

    fun integerArray(key: NamespacedKey, value: IntArray): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.INTEGER_ARRAY, value)
        return this
    }

    fun integerArray(key: NamespacedKey): IntArray? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.INTEGER_ARRAY)
    }

    fun byteArray(key: NamespacedKey, value: ByteArray): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.BYTE_ARRAY, value)
        return this
    }

    fun byteArray(key: NamespacedKey): ByteArray? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.BYTE_ARRAY)
    }

    fun double(key: NamespacedKey, value: Double): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.DOUBLE, value)
        return this
    }

    fun double(key: NamespacedKey): Double? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.DOUBLE)
    }

    fun float(key: NamespacedKey, value: Float): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.FLOAT, value)
        return this
    }

    fun float(key: NamespacedKey): Float? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.FLOAT)
    }

    fun long(key: NamespacedKey, value: Long): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.LONG, value)
        return this
    }

    fun long(key: NamespacedKey): Long? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.LONG)
    }

    fun integer(key: NamespacedKey, value: Int): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.INTEGER, value)
        return this
    }

    fun integer(key: NamespacedKey): Int? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.INTEGER)
    }

    fun short(key: NamespacedKey, value: Short): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.SHORT, value)
        return this
    }

    fun short(key: NamespacedKey): Short? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.SHORT)
    }

    fun byte(key: NamespacedKey, value: Byte): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.BYTE, value)
        return this
    }

    fun byte(key: NamespacedKey): Byte? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.BYTE)
    }

    fun string(key: NamespacedKey, value: String): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.STRING, value)
        return this
    }

    fun string(key: NamespacedKey): String? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.STRING)
    }

    fun uniqueId(key: NamespacedKey, value: UUID): NBTContainer {
        return this.string(key, "$value")
    }

    fun uniqueId(key: NamespacedKey): UUID? {
        return UUID.fromString(this.string(key))
    }

    fun boolean(key: NamespacedKey, value: Boolean): NBTContainer {
        itemMeta.persistentDataContainer.set(key, PersistentDataType.BOOLEAN, value)
        return this
    }

    fun boolean(key: NamespacedKey): Boolean? {
        return itemMeta.persistentDataContainer.get(key, PersistentDataType.BOOLEAN)
    }
}

fun ItemMeta.nbt(): NBTContainer = NBTContainer(this)

fun <T> ItemStack.nbt(block: (NBTContainer)->T): T {
    val itemMeta = this.itemMeta
    val value = block.invoke(itemMeta.nbt())
    this.itemMeta = itemMeta
    return value
}