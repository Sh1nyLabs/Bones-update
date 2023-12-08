package com.shiny_labs.bonesupdate.common.enchantments;

import com.shiny_labs.bonesupdate.init.BonesEnchantments;
import com.shiny_labs.bonesupdate.init.BonesItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * This enchantment allows the player to summon more minions at the same time.
 */
public class LeaderEnchantment extends Enchantment {
    public LeaderEnchantment(Rarity rarity, EquipmentSlot... slot) {
        super(rarity, BonesEnchantments.NECROMANCY, slot);
    }
    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() == BonesItems.NECRO_SCEPTER.get();
    }

    /**
     * The 'Leader' enchantment is incompatible with the 'Subordinate' enchantment
     */
    public boolean checkCompatibility(Enchantment enchantement) {
        return !(enchantement instanceof SubordinateEnchantment) && super.checkCompatibility(enchantement);
    }
}
