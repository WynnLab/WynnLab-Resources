package com.wynnlab.events

import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class DamageEvent(
    val source: LivingEntity,
    val target: LivingEntity,
    val melee: Boolean,
    val multiplier: Double,
    val conversion: DoubleArray
) : Event() {
    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}