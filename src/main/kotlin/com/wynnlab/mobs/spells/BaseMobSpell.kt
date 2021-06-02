package com.wynnlab.mobs.spells

import com.wynnlab.base.BaseSpell
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

abstract class BaseMobSpell(
    val mob: Entity,
    val target: Player,
    maxTick: Int,
    val type: Type,
    val cooldown: Int
) : BaseSpell(maxTick) {
    enum class Type {
        Damage
    }
}