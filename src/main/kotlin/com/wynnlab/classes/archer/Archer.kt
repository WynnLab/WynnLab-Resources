package com.wynnlab.classes.archer

import com.wynnlab.classes.BaseClass
import com.wynnlab.classes.BasePlayerSpell
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.entity.Snowball
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.inventory.ItemStack

object Archer : BaseClass() {
    override val id = "ARCHER"
    override val item = ItemStack(Material.BOW)
    override val metaStats = MetaStats(4, 1, 5, 3)

    override val spells: List<(Player) -> BasePlayerSpell> = listOf(
        ::ArcherMain, ::ArrowStorm, ::Escape, ::BombArrow, ::ArrowShield
    )

    internal fun projectile(clone: Boolean) = if (clone) Snowball::class.java else Arrow::class.java

    internal fun flint(clone: Boolean, proj: Projectile) {
        if (clone) (proj as Snowball).item = ItemStack(Material.FLINT)
    }

    internal fun removeArrow(e: ProjectileHitEvent) {
        (e.entity as? Arrow)?.damage = .0
        e.entity.remove()
        e.isCancelled = true
    }
}