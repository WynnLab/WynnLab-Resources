package com.wynnlab.events

import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class HealEvent(
    val source: LivingEntity,
    val target: LivingEntity,
    val amount: Double
) : Event() {
    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}