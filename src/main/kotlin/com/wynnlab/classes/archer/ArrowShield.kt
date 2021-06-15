package com.wynnlab.classes.archer

import com.wynnlab.DEG2RAD
import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class ArrowShield(player: Player) : BasePlayerSpell(player, 600, 10) {
    private lateinit var stands: Array<ArmorStand>
    private var remaining = 3
    private var cooldown = 0

    override fun onCast() {
        if (!player.hasScoreboardTag("arrow_shield")) {
            player.addScoreboardTag("arrow_shield")
            player.data.set(wlKey("arrow_shield"), PersistentDataType.INTEGER, 3)
        } else {
            val arrows = player.data.get(wlKey("arrow_shield"), PersistentDataType.INTEGER)!!
            player.data.set(wlKey("arrow_shield"), PersistentDataType.INTEGER, 3)
            if (arrows <= 0)
                player.removeScoreboardTag("arrow_shield")
            return
        }

        sound(if (clone) Sound.ITEM_ARMOR_EQUIP_NETHERITE else Sound.ENTITY_EVOKER_PREPARE_SUMMON, .5f, .9f)
        sound(Sound.ENTITY_ARROW_SHOOT, 1f, .8f)
        sound(Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f)

        stands = arrayOf(
            player.world.spawn(player.location.clone().add(1.3, if (clone) .2 else .7, .75), ArmorStand::class.java),
            player.world.spawn(player.location.clone().add(-1.3, if (clone) .2 else .7, .75), ArmorStand::class.java),
            player.world.spawn(player.location.clone().add(.0, if (clone) .2 else .7, 1.5), ArmorStand::class.java),
        )

        for (stand in stands) {
            stand.isVisible = false
            stand.isSmall = false
            stand.isInvulnerable = true
            stand.isMarker = true
            stand.addScoreboardTag("shield_arrow")
            stand.persistentDataContainer.set(wlKey("owner"), PersistentDataType.STRING, player.name)
            stand.equipment!!.helmet = ItemStack(if (clone) Material.SHEARS else Material.ARROW)
            stand.headPose = EulerAngle(.0, .0, PI * (if (clone) -.5 else -.75))
        }
    }

    override fun onTick() {
        if (t in 1..599) {
            stands[0].teleport(player.location.clone().add(sin((10 * t + 60) * -DEG2RAD) * 1.5, if (clone) .2 else .7, cos((10 * t + 60) * DEG2RAD) * 1.5))
            stands[1].teleport(player.location.clone().add(sin((10 * t - 60) * -DEG2RAD) * 1.5, if (clone) .2 else .7, cos((10 * t - 60) * DEG2RAD) * 1.5))
            stands[2].teleport(player.location.clone().add(sin((10 * t + 180) * -DEG2RAD) * 1.5, if (clone) .2 else .7, cos((10 * t + 180) * DEG2RAD) * 1.5))

            stands[0].setRotation(10 * t + 60f, 0f)
            stands[1].setRotation(10 * t - 60f, 0f)
            stands[2].setRotation(10 * t + 180f, 0f)

            for (stand in stands) {
                particle(stand.location.clone().add(.0, if (clone) .5 else .2, .0), if (clone) Particle.VILLAGER_HAPPY else Particle.CRIT, 1, .0, .0, .0, .0)
                particle(stand.location.clone().add(.0, if (clone) .5 else -.2, .0), if (clone) Particle.FIREWORKS_SPARK else Particle.CRIT_MAGIC, 1, .0, .0, .0, .0)
            }
        } else if (t >= 600) {
            sound(Sound.ENTITY_WITHER_BREAK_BLOCK, .1f, 1.9f)
            sound(Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1f, 1f)

            cancel()
        }

        if (cooldown <= 0) {
            for (e in targets(3.0, 3.0, 3.0)) {
                if (e.hasScoreboardTag("shield_arrow"))
                    continue

                activate(e)

                if (remaining > 1) {
                    cooldown = 10
                    --remaining
                } else {
                    arrowRain()
                    cancel()
                }
            }
        } else
            --cooldown
    }

    override fun onCancel() {
        deactivate()
    }

    private fun activate(e: LivingEntity) {
        damage(e, false, 1.0, .7, .0, .0, .0, .0, .3)

        e.velocity = (e.location.toVector() - player.location.toVector()).setY(0).normalize() + Vector(0, 1, 0)

        sound(Sound.ENTITY_ARROW_HIT, 1f, .9f)
        sound(Sound.ENTITY_WITHER_BREAK_BLOCK, .1f, 1.9f)
        sound(Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1f, 1f)
    }

    private fun deactivate() {
        player.removeScoreboardTag("arrow_shield")

        stands.forEach(ArmorStand::remove)
    }

    private fun arrowRain() {
        ArrowRain(player).schedule()
    }
}

