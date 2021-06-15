package com.wynnlab.classes.archer

import com.wynnlab.DEG2RAD
import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.canDamage
import com.wynnlab.extensions.damage
import com.wynnlab.extensions.particle
import com.wynnlab.extensions.sound
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
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

class ArrowRain(player: Player) : BasePlayerSpell(player, 10), Listener {
    private val ids = IntArray(36)
    private var shot = 0
    private var arrived = 36

    override fun onCast() {
        register<ProjectileHitEvent>(plugin, "onHit", EventPriority.HIGH)
    }

    override fun onTick() {
        if (t < 10) {
            for (i in 0..2) {
                val l = player.location.clone().add(sin((10 * t + 60 * (i - 1)) * DEG2RAD) * (1.5 - .15 * t), t.toDouble(), cos((10 * t + 60 * (i - 1)) * DEG2RAD) * (1.5 - .15 * t))
                particle(l, if (clone) Particle.FIREWORKS_SPARK else Particle.CRIT, 5, .0 ,.0, .0, if (clone) .1 else .3)
                particle(l, Particle.SQUID_INK, 3, .0, .0, .0, .0)
                sound(l, Sound.ENTITY_ARROW_SHOOT, 1f, t / 20f + 1f)
            }
            return
        }

        val l = player.location.clone().add(.0, 10.0, .0)

        sound(l, Sound.ENTITY_ARROW_SHOOT, 1f, .8f)
        sound(l, if (clone) Sound.ITEM_ARMOR_EQUIP_GOLD else Sound.ENTITY_GENERIC_EXPLODE, .8f, if (clone) 1f else 1.8f)
        if (clone) sound(l, Sound.ITEM_TOTEM_USE, .8f, .8f)

        for (i in 0 until 360 step 30) {
            for (j in 0..2) {
                var arrow: Projectile = player.world.spawnArrow(l, Vector(sin(i * DEG2RAD), .5 * (j - 1), cos(i * DEG2RAD)), 3f, 1f)
                if (clone) {
                    val snowball = player.world.spawnEntity(arrow.location, EntityType.SNOWBALL) as Snowball
                    snowball.velocity = arrow.velocity
                    snowball.item = ItemStack(Material.FLINT)
                    arrow.remove()
                    arrow = snowball
                }
                ids[shot++] = arrow.entityId
            }
        }
    }

    @Suppress("unused")
    fun onHit(e: ProjectileHitEvent) {
        if (e.entity.entityId !in ids)
            return
        if (--arrived <= 0)
            unregister<ProjectileHitEvent>()

        Archer.removeArrow(e)

        val hit = e.hitEntity ?: return
        if (hit is Mob || hit is Player && player.canDamage(hit))
            damage(hit as LivingEntity, false, 2.0, .7, .0, .0, .0, .0, .3)
    }
}