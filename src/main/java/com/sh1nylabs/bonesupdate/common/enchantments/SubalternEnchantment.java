package com.sh1nylabs.bonesupdate.common.enchantments;

import com.sh1nylabs.bonesupdate.init.BonesEnchantments;
import com.sh1nylabs.bonesupdate.init.BonesItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * This enchantment allows the player to tame some monsters.
 * When used on the necromancer scepter, it no more summons minions.
 */
public class SubalternEnchantment extends Enchantment {
    public SubalternEnchantment(Rarity rarity, EquipmentSlot... slot) {
        super(rarity, BonesEnchantments.NECROMANCY, slot);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() == BonesItems.NECRO_SCEPTER.get();
    }

    /**
     * The 'Subordinate' enchantment is incompatible with the 'Serenity' or 'Leader' enchantments
     */
    public boolean checkCompatibility(Enchantment enchantement) {
        return !(enchantement instanceof SerenityEnchantment) && !(enchantement instanceof LeaderEnchantment) && super.checkCompatibility(enchantement);
    }
}
