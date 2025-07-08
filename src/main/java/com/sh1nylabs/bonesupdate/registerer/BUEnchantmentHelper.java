package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.BU_ENCHANTMENTS;

public class BUEnchantmentHelper {
    private final DeferredHolder<Enchantment, Enchantment> enchantment;

    public BUEnchantmentHelper(String name, Supplier<Enchantment> supplier) {
        enchantment = BU_ENCHANTMENTS.register(name, supplier);
    }

    public Enchantment enchantment() {
        return enchantment.get();
    }
    public ItemStack enchantedBook() {return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment.get(), enchantment.get().getMaxLevel()));}
}
