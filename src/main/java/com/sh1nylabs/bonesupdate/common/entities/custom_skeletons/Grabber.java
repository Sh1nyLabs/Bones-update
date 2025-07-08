package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BUConfig;
import com.sh1nylabs.bonesupdate.common.entities.goal.*;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;


public class Grabber extends AbstractSkeleton {
    private ItemStack requestedItem = ItemStack.EMPTY;
    private static final EntityDataAccessor<ItemStack> ITEM_DISPLAY = SynchedEntityData.defineId(Grabber.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Boolean> IS_CELEBRATING = SynchedEntityData.defineId(Grabber.class, EntityDataSerializers.BOOLEAN);

    public Grabber(EntityType<? extends AbstractSkeleton> type, Level level) {
        super(type, level);
    }

    protected void defineSynchedData(SynchedEntityData.Builder syncBuilder) {
        super.defineSynchedData(syncBuilder);
        syncBuilder.define(IS_CELEBRATING, false);
        syncBuilder.define(ITEM_DISPLAY, ItemStack.EMPTY);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        CompoundTag compoundTag1 = compoundTag.getCompound("requestedItem");
        setPocketItem(ItemStack.parseOptional(this.registryAccess(), compoundTag1));
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("requestedItem", this.requestedItem.save(this.registryAccess()));
    }

    public boolean isCelebratingNewItem() {
        return this.entityData.get(IS_CELEBRATING);
    }

    public void setCelebratingNewItem(Boolean bool) {
        this.entityData.set(IS_CELEBRATING, bool);
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    public boolean wantsItem(Item item) {
        return !this.gotItem() && item == requestedItem.getItem();
    }
    public boolean gotItem() {return requestedItem!=null && this.requestedItem.getItem() == getOffhandItem().getItem();}

    public ItemStack getPocketItem() {return entityData.get(ITEM_DISPLAY);}

    public void setPocketItem(Item item) {
        if (item==null) {
            this.requestedItem = ItemStack.EMPTY;
            this.entityData.set(ITEM_DISPLAY, requestedItem);
        } else {
            this.requestedItem = new ItemStack(item);
            this.entityData.set(ITEM_DISPLAY, requestedItem);
        }
    }

    public void setPocketItem(ItemStack itemstack) {
        this.requestedItem = itemstack;
        this.entityData.set(ITEM_DISPLAY, requestedItem);
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,15.0D) //FIX_VALUE
                .add(Attributes.ATTACK_DAMAGE, 1.5D) //FIX_VALUE
                .add(Attributes.MOVEMENT_SPEED, 0.25F); //FIX_VALUE
    }

    public void registerGoals() {
        this.goalSelector.addGoal(1, new GrabberCelebratesNewItemGoal(this, Grabber::gotItem));
        this.goalSelector.addGoal(2, new GrabberStealsItem(this));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new GrabberMovesBehindPlayerGoal(this, 16.0F, 1.0D, 1.0D, this::playerCanSeeMe));
        this.goalSelector.addGoal(4, new SimpleMoveGoal(this, 1.2F, 0.1F));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D)); //FIX_VALUE (radius of search / walkSpeedModif / sprintspeedModif)
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestStealableTargetGoal(this));
    }

    public boolean playerCanSeeMe(LivingEntity entity) {
        if (entity instanceof Player player) {
            return player.hasLineOfSight(this) && entityInsidePlayerWindow(player.getViewVector(1.0F).normalize(), player.position(), player);
        }
        return false;
    }

    public boolean entityInsidePlayerWindow(Vec3 vecView, Vec3 position, Player player) {
        Vec3 vec31 = new Vec3(this.getX() - position.x(), this.getEyeY() - position.y(), this.getZ() - position.z());
        vec31 = vec31.normalize();

        boolean pitch_okay = Math.abs(Math.atan2(vecView.y(), vecView.distanceToSqr(0, vecView.y(), 0))) - Math.atan2(vec31.y(), vec31.distanceToSqr(0, vec31.y(), 0)) < BUConfig.grabber_ceil_tilt_angle;
        boolean heading_okay = Math.abs(Math.atan2(vecView.z(), vecView.x()) - Math.atan2(vec31.z(), vec31.x())) < BUConfig.grabber_ceil_heading_angle;

        return pitch_okay && heading_okay && vec31.length() < BUConfig.grabber_vision_max_dist;
    }

    @Override
    public void reassessWeaponGoal() {
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean hurtByPlayer) {
        ItemStack itemstack = this.getOffhandItem();
        this.spawnAtLocation(itemstack);
        setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource rdmSequence, DifficultyInstance difficulty) {

    }


    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        Optional<HolderSet.Named<Item>> grabber_steals = BuiltInRegistries.ITEM.getTag(BonesRegistry.GRABBER_STEALS);
        grabber_steals.ifPresent(holders -> this.setPocketItem(holders.stream().toList().get(random.nextInt(holders.size())).value()));
        return spawnData;
    }
}
