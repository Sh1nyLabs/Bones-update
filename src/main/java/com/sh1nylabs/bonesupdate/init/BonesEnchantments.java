package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.enchantments.LeaderEnchantment;
import com.sh1nylabs.bonesupdate.common.enchantments.SerenityEnchantment;
import com.sh1nylabs.bonesupdate.common.enchantments.SubalternEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BonesEnchantments {
    public static final DeferredRegister<Enchantment> BU_ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BonesUpdate.MODID);
    public static final RegistryObject<Enchantment> SERENITY = BU_ENCHANTMENTS.register("serenity",
            () -> new SerenityEnchantment(Enchantment.definition(BonesItems.SKELETON_QUEST_ENCHANTABLE, 4, 1, Enchantment.dynamicCost(12, 7), Enchantment.constantCost(20), 2, EquipmentSlot.MAINHAND)));
    public static final RegistryObject<Enchantment> SUBALTERN = BU_ENCHANTMENTS.register("subaltern",
            () -> new SubalternEnchantment(Enchantment.definition(BonesItems.NECROMANCY_ENCHANTABLE, 4, 1, Enchantment.dynamicCost(12, 7), Enchantment.constantCost(20), 2, EquipmentSlot.MAINHAND)));
    public static final RegistryObject<Enchantment> LEADER = BU_ENCHANTMENTS.register("leader",
            () -> new LeaderEnchantment(Enchantment.definition(BonesItems.NECROMANCY_ENCHANTABLE, 4, 1, Enchantment.dynamicCost(12, 7), Enchantment.constantCost(20), 2, EquipmentSlot.MAINHAND)));
    //public static final EnchantmentCategory NECROMANCY = EnchantmentCategory.create("necromancy", item -> item instanceof NecroScepterItem);
    //public static final EnchantmentCategory SKELETON_QUEST = EnchantmentCategory.create("skeleton_quest", item -> item instanceof NecroScepterItem || item instanceof AmuletItem);

}
