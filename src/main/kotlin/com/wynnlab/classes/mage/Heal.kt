package com.wynnlab.classes.mage

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityRegainHealthEvent

class Heal(player: Player) : BasePlayerSpell(player, 40, 6) {
    override fun tick() {
        if (t % 20 > 0) return

        particle(player.location.clone().add(.0, .5, .0), Particle.PORTAL, 144, 4.0, .0, 4.0, .1)
        particle(player.location.clone().add(.0, .3, .0), Particle.CRIT_MAGIC, 144, 4.0, .0, 4.0, .1)
        particle(player.location.clone().add(.0, 1.0, .0), Particle.FIREWORKS_SPARK, 16, .3, 1.0, .3, .05)
        sound(Sound.ENTITY_EVOKER_CAST_SPELL, .5f, 1.5f)
        sound(Sound.BLOCK_LAVA_EXTINGUISH, 1f, 1f)

        val amount = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
        heal(player, amount)
        player.sendMessage("§4[§c+${amount.toInt()}❤§4]")

        for (e in healable(4.0, 4.0, 4.0)) {
            heal(e, amount)
            e.sendMessage("§4[§c+${amount.toInt()}❤§4] §7(${player.name})")
            Bukkit.getPluginManager().callEvent(EntityRegainHealthEvent(e, amount, EntityRegainHealthEvent.RegainReason.CUSTOM))

            particle(e.location.clone().add(.0, 1.0, .0), Particle.FIREWORKS_SPARK, 16, .3, 1.0, .3, .05)
        }
    }
}