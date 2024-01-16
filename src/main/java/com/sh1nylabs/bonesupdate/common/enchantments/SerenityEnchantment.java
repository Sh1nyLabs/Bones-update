package com.sh1nylabs.bonesupdate.common.enchantments;

import com.sh1nylabs.bonesupdate.init.BonesEnchantments;
import com.sh1nylabs.bonesupdate.init.BonesItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * This enchantment is used to pacify graves, ie forbid graves to summon minions
 * (or being used by necromancers).
 */
public class SerenityEnchantment extends Enchantment {

    public SerenityEnchantment(Rarity rarity, EquipmentSlot... slot) {
        super(rarity, BonesEnchantments.SKELETON_QUEST, slot);
    }
    @Override
    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();
        return item == BonesItems.NECRO_SCEPTER.get() || item == BonesItems.AMULET.get();
    }

    /**
     * The 'Serenity' enchantment is incompatible with the 'Subordinate' enchantment
     */
    public boolean checkCompatibility(Enchantment enchantement) {
        return !( enchantement instanceof SubalternEnchantment) && super.checkCompatibility(enchantement);
    }

}
