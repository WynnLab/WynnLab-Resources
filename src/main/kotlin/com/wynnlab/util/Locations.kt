package com.wynnlab.util

import com.wynnlab.extensions.minus
import com.wynnlab.extensions.times
import org.bukkit.Location

class Locations(private val l1: Location, private val l2: Location, private val step: Double = 1.0) : Iterable<Location>, Iterator<Location> {
    override fun iterator(): Iterator<Location> = this

    private val distance = l1.distance(l2)
    init {
        (l2 - l1).toVector().normalize() * step
    }

    private var current = .0

    override fun hasNext(): Boolean = current + step < distance

    override fun next(): Location {
        current += step
        return l1.add(l2)
    }
}