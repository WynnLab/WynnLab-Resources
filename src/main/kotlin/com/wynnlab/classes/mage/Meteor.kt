package com.wynnlab.classes.mage

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import com.wynnlab.random
import com.wynnlab.util.Locations
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class Meteor(player: Player) : BasePlayerSpell(player, 60, 8) {
    private lateinit var target: Location
    private lateinit var origin: Location
    private lateinit var direction: Vector

    override fun onCast() {
        val ray = player.rayTraceBlocks(21.0)
        var rayLoc = if (ray == null || ray.hitBlock == null) player.eyeLocation.clone() + player.direction * 21 else ray.hitBlock!!.location
        if (ray != null && ray.hitEntity != null)
            rayLoc = ray.hitEntity!!.location

        for (e in targets(rayLoc, 7.0, 7.0, 7.0))
            if (!::target.isInitialized || rayLoc.distance(target) > e.location.distance(target))
                target = e.location

        if (!::target.isInitialized)
            target = rayLoc
        while (target.block.isPassable)
            target.subtract(.0, 1.0, .0)

        origin = target.clone().add(random.nextDouble() * 5 - 2.5, 21.0, random.nextDouble() * 5 - 2.5)
        direction = (origin.clone() - target).toVector().normalize()

        for (l in Locations(target, origin, .5))
            particle(l, if (clone) Particle.SOUL_FIRE_FLAME else Particle.FLAME, 1, .0, .0, .0, .0)

        direction * -1

        if (clone)
            sound(origin, Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1f, .5f)
    }

    override fun onTick() {
        val particleCount = when {
            t <= 5 -> 4
            t <= 10 -> 3
            t <= 15 -> 2
            t <= 20 -> 1
            else -> 0
        }

        if (t <= 20) {
            val pLoc = origin.clone() + (direction.clone() * t)

            particle(pLoc, Particle.EXPLOSION_LARGE, particleCount, .0, .0, .0, .1)
            particle(pLoc, if (clone) Particle.SQUID_INK else Particle.CLOUD, particleCount * 5, .0, .0, .0, .25)
            particle(pLoc, if (clone) Particle.SPELL_WITCH else Particle.LAVA, particleCount, .0, .0, .0, .25)
        }

        if (t == 20) {
            sound(target, Sound.ENTITY_BLAZE_SHOOT, 5f, 1f)
            sound(target, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 5f, 1f)
            sound(target, Sound.ENTITY_GENERIC_EXPLODE, 5f, if (clone) .5f else .75f)
            sound(target, Sound.ITEM_TRIDENT_THUNDER, 1f, .5f)
            sound(target, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1f, .8f)
            sound(target, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, .8f)

            for (e in targets(target, 3.0, 3.0, 3.0))
                damage(e, false, 5.0, .4, .3, .0, .0, .3, .0)
        }

        if (t >= 20 && t % 10 == 0) {
            sound(target, Sound.BLOCK_CAMPFIRE_CRACKLE, 2f, 1f)
            particle(target, if (clone) Particle.SPELL_WITCH else Particle.FLAME, 98, 7.0, 1.0, 7.0, .2)
            particle(target, Particle.SMOKE_NORMAL, 98, 7.0, 3.0, 7.0, .2)

            for (e in targets(target, 7.0, 3.0, 7.0))
                damage(e, false, 1.25)
                //PySpell.knockback(e, VectorUP, .5)
        }
    }
}