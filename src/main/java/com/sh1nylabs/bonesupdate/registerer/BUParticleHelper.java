package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.*;

public class BUParticleHelper {
    private final DeferredHolder<ParticleType<?>, SimpleParticleType> particle;

    public BUParticleHelper(String name) {
        particle = BU_PARTICLES.register(name, () -> new SimpleParticleType(true));
    }

    public SimpleParticleType particle() {
        return particle.get();
    }

}
