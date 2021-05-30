package com.wynnlab.extensions

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.events.DamageEvent
import com.wynnlab.events.HealEvent
import org.bukkit.*
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

fun BasePlayerSpell.sound(sound: Sound, volume: Float, pitch: Float) = sound(player.location, sound, volume, pitch)
fun BasePlayerSpell.sound(l: Location, sound: Sound, volume: Float, pitch: Float) {
    l.world.playSound(l, sound, SoundCategory.MASTER, volume, pitch)
}

fun BasePlayerSpell.particle(l: Location, particle: Particle, count: Int, sx: Double, sy: Double, sz: Double, speed: Double) {
    for (p in l.world.getNearbyPlayers(l, 25.0)) {
        p.spawnParticle(particle, l,
            count * (p.data.get(wlKey(if (p == player) "particles" else "other_particles"), PersistentDataType.INTEGER) ?: 2) / 2,
            sx, sy, sz, speed)
    }
}
fun <T> BasePlayerSpell.particle(l: Location, particle: Particle, count: Int, sx: Double, sy: Double, sz: Double, speed: Double, data: T) {
    for (p in l.world.getNearbyPlayers(l, 25.0)) {
        p.spawnParticle(particle, l,
            count * (p.data.get(wlKey(if (p == player) "particles" else "other_particles"), PersistentDataType.INTEGER) ?: 2) / 2,
            sx, sy, sz, speed, data)
    }
}

fun BasePlayerSpell.targets(l: Location, x: Double, y: Double, z: Double): Collection<LivingEntity> =
    l.world.getNearbyLivingEntities(l, x, y, z) { it != player && (it !is Player || player.canDamage(it)) }

fun BasePlayerSpell.damage(e: LivingEntity, melee: Boolean = false, multiplier: Double = 1.0, vararg conversion: Double) =
    Bukkit.getPluginManager().callEvent(DamageEvent(player, e, melee, multiplier, conversion))

fun BasePlayerSpell.heal(e: LivingEntity, amount: Double) =
    Bukkit.getPluginManager().callEvent(HealEvent(player, e, amount))

fun BasePlayerSpell.healable(x: Double, y: Double, z: Double): Collection<LivingEntity> = healable(player.location, x, y, z)
fun BasePlayerSpell.healable(l: Location, x: Double, y: Double, z: Double): Collection<LivingEntity> =
    l.world.getNearbyLivingEntities(l, x, y, z) { it != player && it is Player && player.canHeal(it) }