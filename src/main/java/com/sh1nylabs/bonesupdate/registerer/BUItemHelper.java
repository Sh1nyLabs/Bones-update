package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.BU_ITEMS;

public class BUItemHelper {
    private final RegistryObject<Item> item;

    public BUItemHelper(String name, Supplier<Item> supplier) {
        item = BU_ITEMS.register(name, supplier);
    }

    public Item item() {
        return item.get();
    }
}
