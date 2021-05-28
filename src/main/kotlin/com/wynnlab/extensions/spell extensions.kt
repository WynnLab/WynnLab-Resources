package com.wynnlab.extensions

import com.wynnlab.classes.BasePlayerSpell
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity

fun BasePlayerSpell.sound(sound: Sound, volume: Float, pitch: Float) = sound(player.location, sound, volume, pitch)
fun BasePlayerSpell.sound(l: Location, sound: Sound, volume: Float, pitch: Float) {}

fun BasePlayerSpell.particle(l: Location, particle: Particle, count: Int, sx: Double, sy: Double, sz: Double, speed: Double) {}
fun <T> BasePlayerSpell.particle(l: Location, particle: Particle, count: Int, sx: Double, sy: Double, sz: Double, speed: Double, data: T) {}

fun BasePlayerSpell.targets(l: Location, x: Double, y: Double, z: Double): Collection<LivingEntity> { TODO() }

fun BasePlayerSpell.damage(e: Entity, melee: Boolean = false, multiplier: Double = 1.0, vararg conversion: Double) {}

fun BasePlayerSpell.heal(e: LivingEntity, amount: Double) {}

fun BasePlayerSpell.healable(x: Double, y: Double, z: Double): Collection<LivingEntity> = healable(player.location, x, y, z)
fun BasePlayerSpell.healable(l: Location, x: Double, y: Double, z: Double): Collection<LivingEntity> { TODO() }