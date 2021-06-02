package com.wynnlab.mobs.spells.damage

import com.wynnlab.DEG2RAD
import com.wynnlab.extensions.*
import com.wynnlab.mobs.spells.BaseMobSpell
import org.bukkit.Particle
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import kotlin.math.cos
import kotlin.math.sin

class ChemicalAura(mob: Entity, target: Player) : BaseMobSpell(mob, target, 20, Type.Damage, 0) {
    val hit = mutableSetOf<Entity>()

    override fun onTick() {
        for (i in 0 until 360 step (20 - t / 2)) {
            particle(mob.location.clone().add(t / 2.0 * cos(i * DEG2RAD), 0.5, t / 2.0 * sin(i * DEG2RAD)),
                Particle.SPELL_MOB, 0, 1.0, .0, 1.0, 1.0)
        }

        for (e in targets(t / 2.0, t / 2.0, t / 2.0)) {
            if (!hit.add(e)) continue

            damage(e)
        }
    }
}