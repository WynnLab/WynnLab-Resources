package com.wynnlab.classes.archer

import com.wynnlab.classes.BasePlayerSpell
import com.wynnlab.extensions.*
import com.wynnlab.plugin
import com.wynnlab.util.register
import com.wynnlab.util.unregister
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.inventory.ItemStack

class ArcherMain(player: Player) : BasePlayerSpell(player, 0), Listener {
    private lateinit var arrow: Projectile

    override fun onCast() {
        sound(Sound.ENTITY_ARROW_SHOOT, 1f, 1f)

        arrow = player.launchProjectile(if (clone) Snowball::class.java else Arrow::class.java, player.direction * 3)
        if (clone) (arrow as Snowball).item = ItemStack(Material.FLINT)

        register<ProjectileHitEvent>(plugin, "onHit", EventPriority.HIGH)
    }

    fun onHit(e: ProjectileHitEvent) {
        if (e.entity != arrow)
            return
        unregister<ProjectileHitEvent>()

        val hit = e.hitEntity ?: return
        if (hit is Mob || hit is Player && player.canDamage(hit))
            damage(hit as LivingEntity, true)
    }
}