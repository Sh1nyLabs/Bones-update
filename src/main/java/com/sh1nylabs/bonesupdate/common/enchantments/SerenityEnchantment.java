package com.sh1nylabs.bonesupdate.common.enchantments;

/* Java class written by sh1nylabs' team. All rights reserved. */

import net.minecraft.world.item.enchantment.Enchantment;

/**
 * This enchantment is used to pacify graves, ie forbid graves to summon minions
 * (or being used by necromancers).

public class SerenityEnchantment extends Enchantment {
    public SerenityEnchantment(Enchantment.EnchantmentDefinition definition) {
        super(definition);
    }

    /** The 'Serenity' enchantment is incompatible with the 'Subordinate' enchantment
    public boolean checkCompatibility(Enchantment enchantement) {
        return !( enchantement instanceof SubalternEnchantment) && super.checkCompatibility(enchantement);
    }
} */
