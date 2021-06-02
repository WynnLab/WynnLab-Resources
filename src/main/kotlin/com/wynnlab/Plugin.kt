package com.wynnlab

import org.bukkit.plugin.java.JavaPlugin
import kotlin.math.PI

class Plugin : JavaPlugin() {
    override fun onLoad() {
        instance = this
    }
}

private lateinit var instance: Plugin
val plugin get() = instance

val random = java.util.Random()

const val DEG2RAD = PI / 180.0
const val RAD2DEG = 180.0 / PI