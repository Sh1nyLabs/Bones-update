package com.sh1nylabs.bonesupdate.common.entities.custom_skeletons;

import com.sh1nylabs.bonesupdate.common.items.AmuletItem;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class BrokenSkeleton extends AbstractSkeleton {
    private int timeBeforeSkeletonRevives;
    private static final int SKELETON_REVIVES_MIN_TIME = 905;
    private static final int SKELETON_REVIVES_MAX_TIME = SKELETON_REVIVES_MIN_TIME + 200;
    private LivingEntity inheritedKillCredit;
    private boolean friendly = false;
    protected static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(BrokenSkeleton.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> DATA_BOGGED_SHEARED = SynchedEntityData.defineId(BrokenSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final List<EntityType<? extends AbstractSkeleton>> OVERWORLD_DEFAULT_VARIANTS =
            List.of(EntityType.SKELETON, BonesRegistry.KNIGHT_SKELETON.type(), BonesRegistry.HAUNTER_SKELETON.type());
    private static int SIZE_OVERWORLD_DEFAULT_VARIANTS = OVERWORLD_DEFAULT_VARIANTS.size();

    public BrokenSkeleton(EntityType<? extends AbstractSkeleton> type, Level level) {
        super(type, level);
    }

    protected void defineSynchedData(SynchedEntityData.Builder syncBuilder) {
        super.defineSynchedData(syncBuilder);
        syncBuilder.define(DATA_ID_TYPE_VARIANT, 1);
        syncBuilder.define(DATA_BOGGED_SHEARED, false);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putInt("Variant", this.entityData.get(DATA_ID_TYPE_VARIANT));
        valueOutput.putBoolean("sheared", this.boggedIsSheared());
        valueOutput.putInt("TimeToRevive", this.timeBeforeSkeletonRevives);
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.entityData.set(DATA_ID_TYPE_VARIANT, valueInput.getIntOr("Variant", 1));
        this.entityData.set(DATA_BOGGED_SHEARED, valueInput.getBooleanOr("sheared", false));
        this.timeBeforeSkeletonRevives = valueInput.getIntOr("TimeToRevive", SKELETON_REVIVES_MAX_TIME);
    }

    public static AttributeSupplier.Builder getCustomAttributes() {
        return (Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 55.0D));
    }

    public boolean boggedIsSheared() {
        return this.entityData.get(DATA_BOGGED_SHEARED);
    }

    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
        boolean playerGotAmulet = (damageSource.getEntity() instanceof Player player &&
                (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AmuletItem ||
                        player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof AmuletItem));
        if (damageSource.getEntity() instanceof Creeper || playerGotAmulet) {
            super.dropAllDeathLoot(level, damageSource);
        }
    }

    /* COPIED from Minecraft's LivingEntity Source Code */
    @Override
    protected void dropFromLootTable(ServerLevel level, DamageSource damageSource, boolean playerKill) {
        Optional<ResourceKey<LootTable>> optional = this.getSkeletonType().getDefaultLootTable();
        if (!optional.isEmpty()) {
            LootTable loottable = level.getServer().reloadableRegistries().getLootTable(optional.get());
            LootParams.Builder lootparams$builder = new LootParams.Builder(level)
                    .withParameter(LootContextParams.THIS_ENTITY, this)
                    .withParameter(LootContextParams.ORIGIN, this.position())
                    .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                    .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity())
                    .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, damageSource.getDirectEntity());
            Player player = this.getLastHurtByPlayer();
            if (playerKill && player != null) {
                lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player).withLuck(player.getLuck());
            }

            LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
            loottable.getRandomItems(lootparams, this.getLootTableSeed(), p_375574_ -> this.spawnAtLocation(level, p_375574_));
        }
    }

    /**
     * Function overriden in order to drop skulls even when broken
     * @param damageSource
     * @param hurtByPlayer
     */
    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean hurtByPlayer) {
        super.dropCustomDeathLoot(level, damageSource, hurtByPlayer);

        boolean playerGotAmulet = (damageSource.getEntity() instanceof Player player &&
                (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AmuletItem ||
                 player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof AmuletItem));
        if (getSkeletonType() == EntityType.SKELETON ||
            getSkeletonType() == BonesRegistry.KNIGHT_SKELETON.type() ||
            getSkeletonType() == BonesRegistry.HAUNTER_SKELETON.type())
        {
            this.spawnAtLocation(level, Items.SKELETON_SKULL);
        }
        if (playerGotAmulet) {
            this.spawnAtLocation(level, BonesRegistry.SKELETON_SOUL.item());
        }
    }

    public void playRevivingSound() {
        SoundEvent soundevent = BonesRegistry.BROKEN_SKELETON_REVIVES.get();
        this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide()) {
            if (timeBeforeSkeletonRevives <= 0 && this.isAlive()) {
                ServerLevel svrLevel = (ServerLevel) this.level();
                AbstractSkeleton skeleton = getSkeletonType().create(svrLevel, EntitySpawnReason.CONVERSION);
                if (skeleton!=null) {
                    skeleton.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                    for(MobEffectInstance mobeffectinstance : this.getActiveEffectsMap().values()) {
                        skeleton.getActiveEffectsMap().put(mobeffectinstance.getEffect(), mobeffectinstance);
                    }
                    if (skeleton instanceof FriendlySkeleton friendlySk) {
                        friendlySk.setFriendly(friendly);
                    }
                    if (skeleton instanceof Bogged bogged) {
                        bogged.setSheared(boggedIsSheared());
                    }
                    net.neoforged.neoforge.event.EventHooks.finalizeMobSpawn(skeleton, svrLevel, svrLevel.getCurrentDifficultyAt(this.blockPosition()), EntitySpawnReason.CONVERSION, null);
                    if (this.getMainHandItem() != ItemStack.EMPTY) {
                        skeleton.setItemInHand(InteractionHand.MAIN_HAND,this.getMainHandItem());
                    }
                    if (this.getOffhandItem() != ItemStack.EMPTY) {
                        skeleton.setItemInHand(InteractionHand.OFF_HAND,this.getOffhandItem());
                    }

                    skeleton.setRemainingFireTicks(this.getRemainingFireTicks());

                    net.neoforged.neoforge.event.EventHooks.onLivingConvert(this, skeleton);
                    svrLevel.addFreshEntityWithPassengers(skeleton);
                    svrLevel.gameEvent(skeleton, GameEvent.ENTITY_PLACE, this.blockPosition());
                    this.discard();
                    svrLevel.sendParticles(BonesRegistry.PURPLE_SOUL.particle(),
                            skeleton.getX(), skeleton.getY() + 0.5D, skeleton.getZ(),
                            50, 0.0D, 0.1D, 0.0D, 0.20D);
                    this.playRevivingSound();

                }
            } else {
                timeBeforeSkeletonRevives--;}
        }
        super.tick();
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!this.isRemoved() && !this.dead && this.level() instanceof ServerLevel) {
            if (damageSource.getEntity() instanceof Player player) {
                player.awardStat(Stats.ENTITY_KILLED.get(this.getSkeletonType()));
            }
        }
        super.die(damageSource);
    }

    @Override
    public LivingEntity getKillCredit() {
        return (inheritedKillCredit != null ? inheritedKillCredit : super.getKillCredit());
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return false;
    }

    @Override
    public boolean isImmobile() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, DamageSource damageSource) {
        return (!damageSource.is(DamageTypeTags.IS_FIRE) &&
                !damageSource.is(DamageTypeTags.IS_EXPLOSION))
                || super.isInvulnerableTo(level, damageSource);
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    public EntityType<? extends AbstractSkeleton> getSkeletonType() {
        return switch (this.entityData.get(DATA_ID_TYPE_VARIANT)) { /* case 1 is SKELETON */
            case 2 -> EntityType.STRAY;
            case 3 -> EntityType.WITHER_SKELETON;
            case 4 -> BonesRegistry.HAUNTER_SKELETON.type();
            case 5 -> BonesRegistry.KNIGHT_SKELETON.type();
            case 6 -> EntityType.BOGGED;
            case 7 -> EntityType.PARCHED;
            default -> EntityType.SKELETON;
        };
    }

    public final void setSkeletonType(EntityType<? extends AbstractSkeleton> skeletonType) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, switch (skeletonType.toString()) {
            case "entity.minecraft.stray" -> 2;
            case "entity.minecraft.wither_skeleton" -> 3;
            case "entity.bonesupdate.haunter_skeleton" -> 4;
            case "entity.bonesupdate.knight_skeleton" -> 5;
            case "entity.minecraft.bogged" -> 6;
            case "entity.minecraft.parched" -> 7;
            default -> 1;
        });
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnData) {
        timeBeforeSkeletonRevives = random.nextInt(SKELETON_REVIVES_MIN_TIME, SKELETON_REVIVES_MAX_TIME);
        if (spawnData instanceof BrokenSkeletonSpawnData skeletonData) { /* Defining which skeleton to create after revival */
            setSkeletonType(skeletonData.skeletonType);
            this.friendly = skeletonData.friendly;
            this.entityData.set(DATA_BOGGED_SHEARED, skeletonData.bogged_sheared);
            this.inheritedKillCredit = skeletonData.inheritedKillCredit;
            this.setItemInHand(InteractionHand.MAIN_HAND,skeletonData.mainHandItem);
            this.setItemInHand(InteractionHand.OFF_HAND,skeletonData.offHandItem);

            this.setRemainingFireTicks(skeletonData.remainingFireTicks);
            for(Holder<MobEffect> effect : skeletonData.activeEffects.keySet()) {
                MobEffectInstance mobeffectinstance = new MobEffectInstance(effect);
                this.getActiveEffectsMap().put(mobeffectinstance.getEffect(), mobeffectinstance);
            }
        } else {
            this.setSkeletonType(OVERWORLD_DEFAULT_VARIANTS.get(random.nextInt(SIZE_OVERWORLD_DEFAULT_VARIANTS)));
            if (getSkeletonType() == BonesRegistry.HAUNTER_SKELETON.type()) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BonesRegistry.HAUNTER_SPEAR.item()));
            }
        }
        return spawnData;
    }

    public static class BrokenSkeletonSpawnData implements SpawnGroupData {
        public EntityType<? extends AbstractSkeleton> skeletonType;
        public java.util.Map<Holder<MobEffect>, MobEffectInstance> activeEffects;
        public LivingEntity inheritedKillCredit;
        public ItemStack mainHandItem;
        public ItemStack offHandItem;
        public int remainingFireTicks;
        public boolean friendly;
        public boolean bogged_sheared = false;

        public BrokenSkeletonSpawnData(AbstractSkeleton entity) {
            this.skeletonType = (EntityType<? extends AbstractSkeleton>) entity.getType();
            this.activeEffects = entity.getActiveEffectsMap();
            this.remainingFireTicks = entity.getRemainingFireTicks();
            this.inheritedKillCredit = entity.getKillCredit();
            this.mainHandItem = entity.getMainHandItem();
            this.offHandItem = entity.getOffhandItem();
            this.friendly = (entity instanceof FriendlySkeleton friendlySk && friendlySk.isFriendly());
            this.bogged_sheared = (entity instanceof Bogged bogged && bogged.isSheared());
        }
    }
}
