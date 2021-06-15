package com.wynnlab.classes.archer

import com.wynnlab.DEG2RAD
import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import kotlin.math.*

class Escape(player: Player) : BasePlayerSpell(player, 0, 3) {
    private var inAir = false

    override fun onCast() {
        if (player.isOnGround || !player.world.getBlockAt(player.location.clone().subtract(.0, 2.0, .0)).isEmpty) {
            player.velocity = player.direction.setY(min(-.4 * abs(player.direction.y), -.4)) * -4

            sound(Sound.ENTITY_BLAZE_SHOOT, 1f, 1.2f)
            particle(player.location.clone().add(.0, 1.0, .0), if (clone) Particle.VILLAGER_HAPPY else Particle.SQUID_INK, if (clone) 10 else 5, .3, 2.0, .3, .2)
            if (clone) particle(player.location.clone().add(.0, 1.0, .0), Particle.CLOUD, 5, .3, 2.0, .3, .1)
        } else
            cancel()
    }

    override fun onTick() {
        if (!inAir && player.isOnGround) {
            delay()
            return
        }
        inAir = true

        if (!player.isOnGround) {
            delay()

            if (player.isSneaking)
                player.velocity = player.velocity - Vector(0, 1, 0)
            else
                player.velocity = player.velocity.setY(max(player.velocity.y, -.5))
        } else {
            if (player.isSneaking) {
                for (i in 0 until 360 step 60) {
                    for (j in 0..8) {
                        val l = player.location.clone().add(sin(i * DEG2RAD) * j, .0, cos(i * DEG2RAD) * j)
                        particle(l, Particle.SQUID_INK, 2, .0, .0, .0, .2)
                        particle(l, Particle.CLOUD, 2, .0, .0, .0, .2)
                        particle(l, Particle.CRIT, 2, .0, .0, .0, .3)
                    }
                }

                sound(Sound.ENTITY_GENERIC_EXPLODE, .5f, 1.2f)
                sound(Sound.ENTITY_IRON_GOLEM_DEATH, 1f, 1f)

                for (e in targets(8.0, 10.0, 8.0)) {
                    damage(e, false, 1.0, .5, .0, .0, .0, .0, .5)
                }
            }

            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 3600, 2, true, false, true))
        }
    }
}