package com.wynnlab.registry

import com.wynnlab.classes.BaseClass
import com.wynnlab.classes.archer.Archer
import com.wynnlab.classes.mage.Mage

object ClassRegistry : Registry<BaseClass>() {
    override val entries = listOf(Mage, Archer)
}