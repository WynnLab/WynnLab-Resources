package com.wynnlab

import com.wynnlab.classes.archer.Archer
import com.wynnlab.classes.mage.Mage
import com.wynnlab.mobs.mobs.MadScientist
import com.wynnlab.registry.ClassRegistry
import com.wynnlab.registry.MobRegistry
import org.bukkit.plugin.java.JavaPlugin
import kotlin.math.PI

class Plugin : JavaPlugin() {
    override fun onLoad() {
        instance = this

        ClassRegistry.register(Mage)
        ClassRegistry.register(Archer)

        MobRegistry.register(MadScientist)
    }
}

private lateinit var instance: Plugin
val plugin get() = instance

val random = java.util.Random()

const val DEG2RAD = PI / 180.0
const val RAD2DEG = 180.0 / PI