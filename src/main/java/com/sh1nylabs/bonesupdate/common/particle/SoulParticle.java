package com.sh1nylabs.bonesupdate.common.particle;

import com.mojang.logging.LogUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class SoulParticle extends TextureSheetParticle {
    private static final Logger LOGGER = LogUtils.getLogger();

    protected SoulParticle(ClientLevel level, SpriteSet sprite, double pos_x, double pos_y, double pos_z, double xd, double yd, double zd) {
        super(level, pos_x, pos_y, pos_z, xd, yd, zd);
        this.quadSize *= 0.85F;
        this.friction = 0.8F;
        this.lifetime = 50;
        this.pickSprite(sprite);

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public SpriteSet sprite;
        public Provider(SpriteSet sprite) {this.sprite = sprite;}
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new SoulParticle(level, sprite, x, y, z, dx, dy, dz);
        }
    }
















}
