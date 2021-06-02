package com.wynnlab.classes.mage

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import com.wynnlab.util.Locations
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class Teleport(player: Player) : BasePlayerSpell(player, 0, 4) {
    private val hit = mutableSetOf<Entity>()

    override fun onCast() {
        val ray = player.rayTraceBlocks(14.0)
        val target = player.location.clone() + (if (ray?.hitBlock == null) player.direction.clone().multiply(14) else ray.hitPosition)

        /*while (!target.block.isPassable) {
            target + (player.direction.clone() * -1)
            target.direction = player.direction
        }*/

        val particleStart = player.eyeLocation
        player.teleport(target)

        for (l in Locations(particleStart, target, .5))
            particle(if (clone) l.clone().subtract(.0, 1.0, .0) else l, if (clone) Particle.DRIP_LAVA else Particle.FLAME, 1, .0, .0, .0, .0)

        for (l in Locations(particleStart, target, 1.0)) {
            particle(l, if (clone) Particle.VILLAGER_ANGRY else Particle.LAVA, 1, .0, .0, .0, .0)

            for (e in targets(l, .5, 2.0, .5)) {
                if (!hit.add(e)) continue

                damage(e, false, 1.0, .6, .0, .4, .0, .0, .0)
            }
        }

        sound(if (clone) Sound.ENTITY_ENDERMAN_TELEPORT else Sound.ENTITY_SHULKER_TELEPORT, 1f, 1f)
        sound(target, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1f, 1f)
    }
}