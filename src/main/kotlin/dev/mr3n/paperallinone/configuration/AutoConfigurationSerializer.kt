package dev.mr3n.paperallinone.configuration

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.objenesis.ObjenesisStd
import java.lang.reflect.Modifier
import java.util.UUID

@Suppress("unused")
abstract class AutoConfigurationSerializer: ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> {
        val fields = this::class.java.declaredFields
            .filterNot { Modifier.isTransient(it.modifiers) }
        val result = mutableMapOf<String, Any>()
        fields.forEach { field ->
            if(field.type.isEnum) {
                result[field.name.asSnakeCase()] = field.get(this).toString()
            } else if(field.type == UUID::class.java) {
                result[field.name.asSnakeCase()] = field.get(this).toString()
            } else {
                result[field.name.asSnakeCase()] = field.get(this)
            }
        }
        return result
    }

    companion object {
        private val objenesis = ObjenesisStd()
        private val regex = Regex("([a-z])([A-Z]+)")

        @JvmStatic
        fun deserialize(args: Map<String, Any>): Any {
            val clazz = Class.forName(args["=="].toString())
            val fields = this::class.java.declaredFields
                .filterNot { Modifier.isTransient(it.modifiers) }
            val instance = objenesis.newInstance(clazz)
            fields.forEach { field ->
                if(field.type.isEnum) {
                    val enumClazz = field.type.enumConstants
                    val enum = enumClazz.find { it.toString().lowercase() == args[field.name.asSnakeCase()].toString().lowercase() }
                    field.set(instance, enum)
                } else if(field.type == UUID::class.java) {
                    field.set(instance, UUID.fromString(args[field.name.asSnakeCase()].toString()))
                } else {
                    field.set(instance, args[field.name.asSnakeCase()])
                }
            }
            return instance
        }

        private fun String.asSnakeCase() = this.replace(regex, "$1_$2")
    }
}