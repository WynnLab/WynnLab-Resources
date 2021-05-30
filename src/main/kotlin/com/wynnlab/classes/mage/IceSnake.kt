package com.wynnlab.classes.mage

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import com.wynnlab.util.normalizeOnXZ
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class IceSnake(player: Player) : BasePlayerSpell(player, 20, 6) {
    private val hit = mutableSetOf<Entity>()
    private val iceLoc = player.location.clone().add(.0, 1.0, .0)

    override fun tick() {
        iceLoc + player.direction.normalizeOnXZ()

        val iceBlock = player.world.spawnFallingBlock(iceLoc, if (clone) Material.OBSIDIAN.createBlockData() else Material.PACKED_ICE.createBlockData())
        iceBlock.dropItem = false
        iceBlock.setHurtEntities(false)

        particle(iceLoc, Particle.FIREWORKS_SPARK, 1, 1.0, 1.0, 1.0, .1)
        particle(iceLoc, Particle.BLOCK_CRACK, 9, 1.0, 1.0, 1.0, 1.0, if (clone) Material.OBSIDIAN.createBlockData() else Material.ICE.createBlockData())
        sound(iceLoc, if (clone) Sound.BLOCK_STONE_PLACE else Sound.BLOCK_GLASS_BREAK, if (clone) 2f else 1f, .75f)
        sound(iceLoc, if (clone) Sound.ENTITY_WITHER_BREAK_BLOCK else Sound.BLOCK_FIRE_EXTINGUISH, if (clone) .25f else .5f, if (clone) 1.5f else 1f)

        for (e in targets(iceLoc, 1.0, 1.0, 1.0)) {
            if (!hit.add(e)) continue

            e.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 300, 4))
            damage(e, false, .7, .5, .0, .0, .5, .0, .0)
            //PySpell.knockback(e, VectorUP, .5)
        }
    }
}