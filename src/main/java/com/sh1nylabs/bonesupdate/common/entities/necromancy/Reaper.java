package com.sh1nylabs.bonesupdate.common.entities.necromancy;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class Reaper extends Monster {
    public Reaper(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,25.0D) // TODO: FIX_VALUE
                .add(Attributes.MOVEMENT_SPEED, 0.2F); // FIX_VALUE
    }
}
