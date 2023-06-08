package dev.mr3n.paperallinone.event

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

inline fun <reified E: Event> Plugin.registerEvent(p: EventPriority = EventPriority.NORMAL, ic: Boolean = false, crossinline r: (E)->Unit): RegistrationResult {
    val dummyListener = object: Listener { }
    Bukkit.getPluginManager().registerEvent(E::class.java, dummyListener, p, { _, e -> if(e is E) { r.invoke(e) } }, this, ic)
    return RegistrationResult {
        val handlers = E::class.java.getDeclaredMethod("getHandlerList").apply { isAccessible = true }.invoke(null) as HandlerList
        handlers.unregister(dummyListener)
    }
}

class RegistrationResult(private val unregister: ()->Unit) {
    fun unregister() { this.unregister.invoke() }
}