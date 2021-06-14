package com.wynnlab.classes.archer

import com.wynnlab.classes.BaseClass
import com.wynnlab.classes.BasePlayerSpell
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Archer : BaseClass() {
    override val id = "ARCHER"
    override val item = ItemStack(Material.BOW)
    override val metaStats = MetaStats(4, 1, 5, 3)

    override val spells: List<(Player) -> BasePlayerSpell> = listOf(
        ::ArcherMain
    )
}