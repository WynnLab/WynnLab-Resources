package com.wynnlab.classes

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class BaseClass {
    abstract val id: String
    abstract val item: ItemStack
    abstract val metaStats: MetaStats
    open val invertedControls get() = false
    abstract val spells: List<(Player) -> BasePlayerSpell>

    data class MetaStats(
        val damage: Int,
        val defense: Int,
        val range: Int,
        val spells: Int
    )
}