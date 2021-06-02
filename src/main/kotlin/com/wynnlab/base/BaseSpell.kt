package com.wynnlab.base

import com.wynnlab.plugin
import org.bukkit.Bukkit

abstract class BaseSpell(private val maxTick: Int) : Runnable {
    var t = -1

    private var taskId = -1

    open fun onCast() {}

    open fun onTick() {}

    open fun onCancel() {}

    override fun run() {
        ++t

        onTick()

        if (t >= maxTick)
            cancel()
    }

    protected fun delay() {
        --t
    }

    protected fun cancel() {
        onCancel()
        Bukkit.getScheduler().cancelTask(taskId)
    }

    fun schedule(delay: Long = 0L, period: Long = 1L) {
        taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, delay, period).taskId
        onCast()
    }
}