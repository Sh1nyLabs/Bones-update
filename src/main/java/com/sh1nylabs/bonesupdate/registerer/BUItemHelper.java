package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.BU_ITEMS;

public class BUItemHelper {
    private final RegistryObject<Item> item;

    public BUItemHelper(String name, Function<Item.Properties, Item> function, Item.Properties itemProperties) {
        item = BU_ITEMS.register(name, () -> function.apply(itemProperties.setId(ResourceKey.create(BU_ITEMS.getRegistryKey(), BUModIdentifier.fromModNamespace(name)))));
    }

    public Item item() {
        return item.get();
    }
}
