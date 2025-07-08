package com.sh1nylabs.bonesupdate.common.enchantments;

/* Java class written by sh1nylabs' team. All rights reserved. */

import net.minecraft.world.item.enchantment.Enchantment;

/**
 * This enchantment allows the player to tame some monsters.
 * When used on the necromancer scepter, it no more summons minions.

public class SubalternEnchantment extends Enchantment {
    public SubalternEnchantment(Enchantment.EnchantmentDefinition definition) {
        super(definition);
    }

    /** The 'Subordinate' enchantment is incompatible with the 'Serenity' or 'Leader' enchantments
    public boolean checkCompatibility(Enchantment enchantement) {
        return !(enchantement instanceof SerenityEnchantment) && !(enchantement instanceof LeaderEnchantment) && super.checkCompatibility(enchantement);
    }
}*/
