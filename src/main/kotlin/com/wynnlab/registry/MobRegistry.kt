package com.wynnlab.registry

import com.wynnlab.mobs.BaseMob
import com.wynnlab.mobs.mobs.MadScientist

object MobRegistry : Registry<BaseMob>() {
    override val entries = listOf(MadScientist)
}