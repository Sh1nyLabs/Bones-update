package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.enchantments.LeaderEnchantment;
import com.sh1nylabs.bonesupdate.common.enchantments.SerenityEnchantment;
import com.sh1nylabs.bonesupdate.common.enchantments.SubalternEnchantment;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BonesEnchantments {
    public static final DeferredRegister<Enchantment> BU_ENCHANTMENTS = DeferredRegister.create(BuiltInRegistries.ENCHANTMENT, BonesUpdate.MODID);
    public static final DeferredHolder<Enchantment, Enchantment> SERENITY = BU_ENCHANTMENTS.register("serenity",
            () -> new SerenityEnchantment(Enchantment.definition(BonesItems.SKELETON_QUEST_ENCHANTABLE, 4, 1, Enchantment.dynamicCost(12, 7), Enchantment.constantCost(20), 2, EquipmentSlot.MAINHAND)));
    public static final DeferredHolder<Enchantment, Enchantment> SUBALTERN = BU_ENCHANTMENTS.register("subaltern",
            () -> new SubalternEnchantment(Enchantment.definition(BonesItems.NECROMANCY_ENCHANTABLE, 4, 1, Enchantment.dynamicCost(12, 7), Enchantment.constantCost(20), 2, EquipmentSlot.MAINHAND)));
    public static final DeferredHolder<Enchantment, Enchantment> LEADER = BU_ENCHANTMENTS.register("leader",
            () -> new LeaderEnchantment(Enchantment.definition(BonesItems.NECROMANCY_ENCHANTABLE, 4, 1, Enchantment.dynamicCost(12, 7), Enchantment.constantCost(20), 2, EquipmentSlot.MAINHAND)));
    //public static final EnchantmentCategory NECROMANCY = EnchantmentCategory.create("necromancy", item -> item instanceof NecroScepterItem);
    //public static final EnchantmentCategory SKELETON_QUEST = EnchantmentCategory.create("skeleton_quest", item -> item instanceof NecroScepterItem || item instanceof AmuletItem);

}
