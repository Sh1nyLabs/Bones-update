package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.BU_ITEMS;

public class BUItemHelper {
    private final RegistryObject<Item> item;

    public BUItemHelper(String name, Function<Item.Properties, Item> function, Item.Properties itemProperties) {
        item = BU_ITEMS.register(name, () -> function.apply(itemProperties.setId(BU_ITEMS.key(name))));
    }

    public Item item() {
        return item.get();
    }
}
