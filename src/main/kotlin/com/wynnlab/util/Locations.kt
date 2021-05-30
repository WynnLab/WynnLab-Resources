package com.wynnlab.util

import com.wynnlab.extensions.minus
import com.wynnlab.extensions.plus
import com.wynnlab.extensions.times
import org.bukkit.Location

class Locations(
    val start: Location,
    val end: Location,
    val step: Double
) : Iterable<Location> {
    private val d = (end.clone() - start).toVector().normalize() * step

    override operator fun iterator(): Iterator<Location> =
        object : Iterator<Location> {
            private var l = start.clone()

            private var iStep = 0.0
            private val dist: Double = end.distance(start)

            override operator fun hasNext(): Boolean {
                return iStep <= dist
            }

            override operator fun next(): Location {
                val r = l
                l + d
                iStep += step
                return r
            }
        }
}