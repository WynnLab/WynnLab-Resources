package com.wynnlab.classes.archer

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import com.wynnlab.plugin
import com.wynnlab.util.register
import com.wynnlab.util.unregister
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

class ArcherMain(player: Player) : BasePlayerSpell(player, 0), Listener {
    private lateinit var arrow: Projectile

    override fun onCast() {
        sound(Sound.ENTITY_ARROW_SHOOT, 1f, 1f)

        arrow = player.launchProjectile(Archer.projectile(clone), player.direction * 3)
        Archer.flint(clone, arrow)

        register<ProjectileHitEvent>(plugin, "onHit", EventPriority.HIGH)
    }

    @Suppress("unused")
    fun onHit(e: ProjectileHitEvent) {
        if (e.entity != arrow)
            return
        unregister<ProjectileHitEvent>()

        Archer.removeArrow(e)

        val hit = e.hitEntity ?: return
        if (hit is Mob || hit is Player && player.canDamage(hit))
            damage(hit as LivingEntity, true)
    }
}