package com.wynnlab.classes.mage

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import com.wynnlab.util.Locations
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class MageMain(player: Player) : BasePlayerSpell(player) {
    private val hit = mutableSetOf<Entity>()

    override fun tick() {
        val l1 = player.eyeLocation.subtract(.0, .5, .0)
        val l2 = (player.eyeLocation + (player.direction * 7)).subtract(.0, .5, .0)

        sound(Sound.ITEM_TRIDENT_THROW, 1f, 1.5f)
        sound(Sound.ITEM_TRIDENT_RIPTIDE_3, .2f, 1f)
        if (clone) sound(Sound.ENTITY_SHULKER_SHOOT, .5f, 1.5f)

        for (l in Locations(l1, l2, .5)) {
            particle(l, if (clone) Particle.SQUID_INK else Particle.CLOUD, 2, .0, .0, .0, .1)
            particle(l, if (clone) Particle.SPELL_WITCH else Particle.CRIT, 1, .0, .0, .0, .0)
            particle(l, Particle.CRIT_MAGIC, 1, .0, .0, .0, .1)

            for (e in targets(l, .5, .5, .5)) {
                if (!this.hit.add(e)) continue

                damage(e, melee = true)
            }
        }
    }
}