package com.wynnlab.classes

import com.wynnlab.base.BaseSpell
import org.bukkit.entity.Player

abstract class BasePlayerSpell(
    val player: Player,
    maxTick: Int,
    val cost: Int = 0
) : BaseSpell(maxTick) {
    val clone = "clone" in player.scoreboardTags
}