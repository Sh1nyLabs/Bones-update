package com.sh1nylabs.bonesupdate.common.enchantments;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.enchantments.SubalternEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * This enchantment allows the player to summon more minions at the same time.
 */
public class LeaderEnchantment extends Enchantment {
    public LeaderEnchantment(Enchantment.EnchantmentDefinition definition) {
        super(definition);
    }

    /** The 'Leader' enchantment is incompatible with the 'Subordinate' enchantment */
    public boolean checkCompatibility(Enchantment enchantement) {
        return !( enchantement instanceof SubalternEnchantment) && super.checkCompatibility(enchantement);
    }
}
