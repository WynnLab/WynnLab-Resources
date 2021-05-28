package com.wynnlab.classes

import org.bukkit.entity.Player

abstract class BaseClass {
    abstract val spells: List<Spell>

     data class Spell(
        //val maxTick: Int,
        val constructor: (Player) -> BasePlayerSpell
    )
}