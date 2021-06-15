package com.wynnlab.classes.archer

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import com.wynnlab.plugin
import com.wynnlab.util.register
import com.wynnlab.util.unregister
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.entity.Snowball
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.math.PI

class BombArrow(player: Player) : BasePlayerSpell(player, 0, 8), Listener {
    private lateinit var bomb: Projectile
    private var hits = 0

    override fun onCast() {
        sound(Sound.ENTITY_ARROW_SHOOT, .9f, .7f)
        sound(Sound.ENTITY_TNT_PRIMED, 1.2f, 1.3f)

        bomb = player.launchProjectile(Archer.projectile(clone))
        Archer.flint(clone, bomb)

        register<ProjectileHitEvent>(plugin, "onHit", EventPriority.HIGH)
    }

    @Suppress("unused")
    fun onHit(e: ProjectileHitEvent) {
        if (e.entity != bomb)
            return

        ++hits
        Archer.removeArrow(e)

        if (hits < 3) {
            bomb = bomb.world.spawnArrow(bomb.location.clone().add(.0, .2, .0), (bomb.velocity.clone().rotateAroundAxis(bomb.facing.direction, PI / 2.0) * 3).setY(1), .6f, .0f)
            if (clone) {
                val snowball = bomb.world.spawnEntity(bomb.location, EntityType.SNOWBALL) as Snowball
                snowball.velocity = bomb.velocity
                snowball.item = ItemStack(Material.FLINT)
                bomb.remove()
                bomb = snowball
            }
        } else
            unregister<ProjectileHitEvent>()

        for (e1 in targets(bomb.location, 4.0, 4.0, 4.0)) {
            damage(e1, false, 2.5, .6, .25, .0, .0, .15, .0)
            e1.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 60, 4, true, true, true))
        }

        sound(bomb.location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.3f)
        if (clone) sound(bomb.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, .9f, 1.1f)

        val pLoc = bomb.location.add(.0, .5, .0)
        particle(pLoc, if (clone) Particle.FIREWORKS_SPARK else Particle.FLAME, 20, .3, .3, .3, .1)
        particle(pLoc, if (clone) Particle.VILLAGER_HAPPY else Particle.SQUID_INK, 10, .3, .3, .3, .1)
        if (!clone) particle(pLoc, Particle.CLOUD, 5, .2, .2, .2, .1)
        particle(pLoc, Particle.EXPLOSION_LARGE, 1, .0, .0, .0, .0)
    }
}