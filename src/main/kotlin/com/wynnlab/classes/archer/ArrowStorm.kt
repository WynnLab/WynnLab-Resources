package com.wynnlab.classes.archer

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import com.wynnlab.plugin
import com.wynnlab.util.register
import com.wynnlab.util.unregister
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.*
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.inventory.ItemStack

class ArrowStorm(player: Player) : BasePlayerSpell(player, 20, 6), Listener {
    private val arrows = mutableSetOf<Projectile>()

    override fun onCast() {
        particle(player.location.clone().add(.0, 1.0, .0), if (clone) Particle.VILLAGER_HAPPY else Particle.CRIT, 5, .2, 2.0, .2, .5)

        register<ProjectileHitEvent>(plugin, "onHit", EventPriority.HIGH)
    }

    override fun onTick() {
        sound(Sound.ENTITY_ARROW_SHOOT, 1f, 1f)
        if (clone) sound(Sound.BLOCK_GLASS_BREAK, .05f, .6f)

        for (arrow in arrows) {
            particle(arrow.location, Particle.CRIT, 1, .0, .0, .0, .01)
            if (clone) particle(arrow.location, Particle.CRIT_MAGIC, 1, .0, .0, .0, .01)
        }

        val vi = player.direction * 3
        val shots = arrayOf(
            player.launchProjectile(if (clone) Snowball::class.java else Arrow::class.java, vi),
            player.launchProjectile(if (clone) Snowball::class.java else Arrow::class.java, vi),
            player.launchProjectile(if (clone) Snowball::class.java else Arrow::class.java, vi),
        )

        for (shot in shots) {
            shot.shooter = player
            if (clone) (shot as Snowball).item = ItemStack(Material.FLINT)
            arrows.add(shot)
        }
    }

    fun onHit(e: ProjectileHitEvent) {
        if (e.entity !in arrows)
            return
        unregister<ProjectileHitEvent>()

        val hit = e.hitEntity ?: return
        if (hit is Mob || hit is Player && player.canDamage(hit))
            damage(hit as LivingEntity, false, .1, .6, .0, .25, .0, .15, .0)
    }
}