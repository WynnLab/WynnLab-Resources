package com.wynnlab.extensions

import com.wynnlab.classes.BasePlayerSpell
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

fun BasePlayerSpell.sound(sound: Sound, volume: Float, pitch: Float) = sound(player, sound, volume, pitch)
fun BasePlayerSpell.sound(player: Player, sound: Sound, volume: Float, pitch: Float) {}

fun BasePlayerSpell.particle(l: Location, particle: Particle, count: Int, sx: Double, sy: Double, sz: Double, speed: Double) {}

fun BasePlayerSpell.targets(l: Location, x: Double, y: Double, z: Double): Collection<Entity> { TODO() }

fun BasePlayerSpell.damage(e: Entity, melee: Boolean = false, multiplier: Double = 1.0) {}