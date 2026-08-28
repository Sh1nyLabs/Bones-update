package com.sh1nylabs.bonesupdate.common.particle;

/* Java class written by sh1nylabs' team. All rights reserved. */

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class ParticleBar extends SingleQuadParticle {

    protected ParticleBar(ClientLevel level, double pos_x, double pos_y, double pos_z, double xd, double yd, double zd, SpriteSet sprite) {
        super(level, pos_x, pos_y, pos_z, xd, yd, zd, sprite.first());
        this.quadSize *= 0.85F;
        this.friction = 0.99F;
        this.lifetime = 12+level.random.nextInt(5);

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    protected Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public SpriteSet sprite;
        public Provider(SpriteSet sprite) {this.sprite = sprite;}
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz,
                                       RandomSource randomSource) {
            return new ParticleBar(level, x, y, z, dx, dy, dz, sprite);
        }
    }
















}
