package com.sh1nylabs.bonesupdate.registerer;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Function;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.BU_ITEMS;

public class BUItemHelper {
    private final DeferredHolder<Item, Item> item;

    public BUItemHelper(String name, Function<Item.Properties, Item> function, Item.Properties itemProperties) {
        item = BU_ITEMS.register(name, () -> function.apply(itemProperties.setId(ResourceKey.create(BU_ITEMS.getRegistryKey(), ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name)))));
    }

    public Item item() {
        return item.get();
    }
}
