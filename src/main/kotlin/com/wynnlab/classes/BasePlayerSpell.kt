package com.wynnlab.classes

import org.bukkit.entity.Player

abstract class BasePlayerSpell(val player: Player) {
    var t = 0
    val clone = "clone" in player.scoreboardTags

    abstract fun tick()
}