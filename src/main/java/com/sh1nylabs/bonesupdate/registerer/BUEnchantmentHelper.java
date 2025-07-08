package com.sh1nylabs.bonesupdate.registerer;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;


public class BUEnchantmentHelper {
    private final ResourceKey<Enchantment> enchantment;

    public BUEnchantmentHelper(String name) {
        enchantment = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name));
    }

    public Holder<Enchantment> toHolder(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);
    }

    public ResourceKey<Enchantment> ench() {return enchantment;}
}
