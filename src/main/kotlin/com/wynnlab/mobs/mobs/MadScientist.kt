package com.wynnlab.mobs.mobs

import com.wynnlab.mobs.BaseMob
import com.wynnlab.mobs.spells.BaseMobSpell
import com.wynnlab.mobs.spells.damage.ChemicalAura
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound as BukkitSound
import org.bukkit.entity.*
import org.bukkit.inventory.ItemStack

object MadScientist : BaseMob() {
    override val name = Component.text("Mad Scientist", EVIL_NAME_COLOR)
    override val mobType = EntityType.EVOKER // TODO: -> Player
    override val ai = AI(AI.CastSpells, AI.RangedAttack)
    override val level = 150
    override val health = 500_000
    override val regen = 1_000
    override val damage = 500..1_000
    override val attackSpeed = 1.0
    override val projectile = Projectile(Snowball::class, ItemStack(Material.DRAGON_BREATH))
    override val speed = 1.5
    override val vision = 20.0
    override val defense = 0.6
    override val elementalDamage = Elemental(600..800, 200..400, 0..0, 0..0, 0..0)
    override val elementalDefense = Elemental(20, 50, 0, -50, -40)
    override val ambientSound = Sound(BukkitSound.ENTITY_WITCH_AMBIENT, 1f, .5f)
    override val hurtSound = Sound(BukkitSound.ENTITY_EVOKER_HURT, 1f, 1.3f)
    override val deathSound = Sound(BukkitSound.ENTITY_WITCH_DEATH, 1.2f, .6f)
    override val kbResistance = .0
    override val equipment = Equipment(mainHand = ItemStack(Material.DRAGON_BREATH), offHand = ItemStack(Material.EXPERIENCE_BOTTLE))

    override val spells: List<(Entity, Player) -> BaseMobSpell> = listOf(
        ::ChemicalAura
    )
}