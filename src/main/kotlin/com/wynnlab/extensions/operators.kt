package com.wynnlab.extensions

import org.bukkit.Location
import org.bukkit.util.Vector

operator fun Vector.times(i: Int) = multiply(i)
operator fun Vector.times(d: Double) = multiply(d)

operator fun Location.plus(v: Vector) = add(v)

operator fun Location.minus(l: Location) = subtract(l)