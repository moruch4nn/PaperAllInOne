package dev.mr3n.paperallinone.nms

import org.bukkit.Bukkit
import java.lang.reflect.Field
import java.lang.reflect.Method

@Suppress("unused")
object NmsUtils {
    private val version: String by lazy { Bukkit.getServer()::class.java.`package`.name.let { it.substring(it.lastIndexOf('.') + 1) } }

    fun nmsClass(clazzName: String): Class<*> = Class.forName("net.minecraft.server.${version}.${clazzName}")

    fun craftBukkitClass(clazzName: String): Class<*> = Class.forName("org.bukkit.craftbukkit.${version}.${clazzName}")

    fun Field.accessible() = this.also { it.isAccessible = true }

    fun Method.accessible() = this.also { it.isAccessible = true }
}
