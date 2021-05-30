package com.wynnlab

import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {
    override fun onLoad() {
        instance = this
    }
}

private lateinit var instance: Plugin
val plugin get() = instance

val random = java.util.Random()