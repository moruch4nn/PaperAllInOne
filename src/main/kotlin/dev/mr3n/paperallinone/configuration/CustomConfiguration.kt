package dev.mr3n.paperallinone.configuration

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

@Suppress("Unused","MemberVisibilityCanBePrivate")
class CustomConfiguration(private val plugin: Plugin, private val configFile: File): YamlConfiguration() {

    constructor(plugin: Plugin, filename: String) : this(plugin, File(plugin.dataFolder, filename))

    fun reloadConfig() {
        this.load(configFile)
        val defaultConfigStream = InputStreamReader(plugin.getResource(configFile.name)!!, StandardCharsets.UTF_8)
        this.setDefaults(loadConfiguration(defaultConfigStream))
    }

    fun saveConfig() {
        try { this.save(configFile) } catch (e: IOException) { e.printStackTrace() }
    }

    init {
        if (!configFile.exists()) plugin.saveResource(configFile.name, false)
        this.reloadConfig()
    }
}