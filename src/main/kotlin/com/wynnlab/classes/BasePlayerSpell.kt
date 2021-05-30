package com.wynnlab.classes

import com.wynnlab.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player

abstract class BasePlayerSpell(val player: Player, private val maxTick: Int, val cost: Int = 0) : Runnable {
    var t = -1
    val clone = "clone" in player.scoreboardTags

    private var taskId = -1

    abstract fun tick()

    override fun run() {
        ++t

        tick()

        if (t >= maxTick)
            cancel()
    }

    protected fun delay() {
        --t
    }

    protected fun cancel() {
        Bukkit.getScheduler().cancelTask(taskId)
    }

    fun schedule(delay: Long = 0L, period: Long = 1L) {
        taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, delay, period).taskId
    }
}