package com.sh1nylabs.bonesupdate.common.particle;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BonesParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BonesUpdate.MODID);

    public static final RegistryObject<SimpleParticleType> SOUL_PARTICLE = PARTICLE_TYPE.register("soul_particle", () -> new SimpleParticleType(true));

}
