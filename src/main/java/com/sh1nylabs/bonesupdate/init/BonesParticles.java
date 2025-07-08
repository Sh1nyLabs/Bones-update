package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BonesParticles {

    public static final DeferredRegister<ParticleType<?>> BU_PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BonesUpdate.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PURPLE_BAR = BU_PARTICLES.register("purple_bar", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PURPLE_SOUL = BU_PARTICLES.register("purple_soul", () -> new SimpleParticleType(true));

}
