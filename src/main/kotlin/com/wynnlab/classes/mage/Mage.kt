package com.wynnlab.classes.mage

import com.wynnlab.classes.BaseClass
import com.wynnlab.classes.BasePlayerSpell
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Mage : BaseClass() {
    override val id get() = "MAGE"
    override val item = ItemStack(Material.STICK)
    override val metaStats = MetaStats(2, 3, 3, 5)

    override val spells: List<(Player) -> BasePlayerSpell> = listOf(
        ::MageMain, ::Heal, ::Teleport, ::Meteor, ::IceSnake
    )
}