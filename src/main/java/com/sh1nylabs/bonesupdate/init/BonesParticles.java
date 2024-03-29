package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BonesParticles {

    public static final DeferredRegister<ParticleType<?>> BU_PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BonesUpdate.MODID);

    public static final RegistryObject<SimpleParticleType> PURPLE_BAR = BU_PARTICLES.register("purple_bar", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PURPLE_SOUL = BU_PARTICLES.register("purple_soul", () -> new SimpleParticleType(true));

}
