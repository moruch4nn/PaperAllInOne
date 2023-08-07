@file:Suppress("unused")

package dev.mr3n.paperallinone.task

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

inline fun Plugin.runTask(crossinline consumer: (BukkitTask)->Unit): BukkitTask {
    var bukkitTask: BukkitTask? = null
    bukkitTask = this.server.scheduler.runTask(this, Runnable { consumer.invoke(bukkitTask!!) })
    return bukkitTask
}

inline fun Plugin.runTaskLater(delay: Long, crossinline consumer: (BukkitTask) -> Unit): BukkitTask {
    var bukkitTask: BukkitTask? = null
    bukkitTask = this.server.scheduler.runTaskLater(this, Runnable { consumer.invoke(bukkitTask!!) }, delay)
    return bukkitTask
}

inline fun Plugin.runTaskAsync(crossinline consumer: (BukkitTask) -> Unit): BukkitTask {
    var bukkitTask: BukkitTask? = null
    bukkitTask = this.server.scheduler.runTaskAsynchronously(this, Runnable { consumer.invoke(bukkitTask!!) })
    return bukkitTask
}

inline fun Plugin.runTaskLaterAsync(delay: Long, crossinline consumer: (BukkitTask) -> Unit): BukkitTask {
    var bukkitTask: BukkitTask? = null
    bukkitTask = this.server.scheduler.runTaskLaterAsynchronously(this, Runnable { consumer.invoke(bukkitTask!!) }, delay)
    return bukkitTask
}

inline fun Plugin.runTaskTimerAsync(delay: Long, period: Long, crossinline consumer: (BukkitTask, Long) -> Unit): BukkitTask {
    var bukkitTask: BukkitTask? = null
    var count = 0L
    bukkitTask = this.server.scheduler.runTaskTimer(this, Runnable { consumer.invoke(bukkitTask!!,count++) }, delay, period)
    return bukkitTask
}

inline fun Plugin.runTaskTimer(delay: Long, period: Long, crossinline consumer: (BukkitTask, Long) -> Unit): BukkitTask {
    var bukkitTask: BukkitTask? = null
    var count = 0L
    bukkitTask = this.server.scheduler.runTaskTimer(this, Runnable { consumer.invoke(bukkitTask!!,count++) }, delay, period)
    return bukkitTask
}