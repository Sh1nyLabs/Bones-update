package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.BU_ENCHANTMENTS;
import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.BU_ITEMS;

public class BUItemHelper {
    private final DeferredHolder<Item, Item> item;

    public BUItemHelper(String name, Supplier<Item> supplier) {
        item = BU_ITEMS.register(name, supplier);
    }

    public Item item() {
        return item.get();
    }
}
