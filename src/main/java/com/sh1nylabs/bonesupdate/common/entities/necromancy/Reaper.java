package com.sh1nylabs.bonesupdate.common.entities.necromancy;

import com.sh1nylabs.bonesupdate.common.entities.goal.ReaperAttackGoal;
import com.sh1nylabs.bonesupdate.init.BonesItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Reaper extends FlyingMob implements Enemy {
    public Reaper(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new ReaperAttackGoal.ReaperMoveControl(this);
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,25.0D) // TODO: FIX_VALUE
                .add(Attributes.MOVEMENT_SPEED, 0.2D) // FIX_VALUE
                .add(Attributes.ATTACK_DAMAGE, 2.0D) // FIX_VALUE
                .add(Attributes.ATTACK_SPEED, 12.0F); // FIX_VALUE
    }

    protected void registerGoals() {
        //TODO: put custom attacks
        this.goalSelector.addGoal(3, new ReaperAttackGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource rdmSequence, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BonesItems.REAPER_SCYTHE.get()));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

}
