package com.sh1nylabs.bonesupdate.common.events;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BUConfig;
import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.*;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.List;
import java.util.Optional;

public class BonesModEvent {

    @EventBusSubscriber(modid = BonesUpdate.MODID)
    public static class BonesForgeEvents {

        @SubscribeEvent
        public static void SpawnSkeletonSquadEvent(FinalizeSpawnEvent event) {
            if (event.getLevel() instanceof ServerLevel serverLevel && (event.getEntity() instanceof Skeleton skeleton) && event.getDifficulty().isHarderThan(BUConfig.squadDifficultyMin) && event.getLevel().getRandom().nextInt(BUConfig.squadSpawnChance) == 0) {
                BlockPos blockpos;
                for (EntityType<? extends AbstractSkeleton> entityType : BonesUpdate.SKELETONS_PER_SQUAD.keySet()) {
                    for (int i = 0; i < BonesUpdate.SKELETONS_PER_SQUAD.get(entityType); i++) {
                        blockpos = BonesUpdate.randomValidPosForSpawn(serverLevel, skeleton.getOnPos().above(), 4, 3, 4, 0.5, entityType, 20);
                        if (blockpos != null) {
                            AbstractSkeleton new_skeleton = entityType.create(serverLevel);
                            if (new_skeleton != null) {
                                new_skeleton.moveTo(blockpos, serverLevel.getRandom().nextFloat() * 3.0F, 0.0F);
                                EventHooks.finalizeMobSpawn(new_skeleton, serverLevel, serverLevel.getCurrentDifficultyAt(blockpos), MobSpawnType.EVENT, null);
                                serverLevel.tryAddFreshEntityWithPassengers(new_skeleton);
                                serverLevel.gameEvent(new_skeleton, GameEvent.ENTITY_PLACE, blockpos);
                            }
                        }
                    }
                }
            }
        }

        /**
         * Catch a skeleton which is dying in order to replace it by a broken form.
         * Cancels the event and replace the entity with a broken skeleton.
         * When the broken skeleton revives, it is removed from world and
         *     replaced by the original skeleton.
         */
        @SubscribeEvent
        public static void SkeletonDiesEvent(LivingDeathEvent event) {
            if ((!event.getEntity().level().isClientSide()) && (event.getEntity() instanceof AbstractSkeleton skeleton) && !(skeleton instanceof BrokenSkeleton)  && !(skeleton instanceof Minion) && !(skeleton instanceof Grabber)) {
                event.setCanceled(true);
                ServerLevel svrLevel = (ServerLevel) event.getEntity().level();
                BrokenSkeleton broken = BonesRegistry.BROKEN_SKELETON.type().create(svrLevel);
                if (broken != null) {
                    broken.moveTo(skeleton.getX(), skeleton.getY(), skeleton.getZ(), skeleton.getYRot(), skeleton.getXRot());
                    EventHooks.finalizeMobSpawn(broken, svrLevel, svrLevel.getCurrentDifficultyAt(broken.blockPosition()), MobSpawnType.CONVERSION, new BrokenSkeleton.BrokenSkeletonSpawnData(skeleton));

                    EventHooks.onLivingConvert(skeleton, broken);
                    svrLevel.addFreshEntityWithPassengers(broken);
                    svrLevel.gameEvent(broken, GameEvent.ENTITY_PLACE, broken.blockPosition());
                    skeleton.discard();
                }
            }
        }
        /**
         * When a pillager or a villager dies near a necromancer, it increases the necromancer's
         * minion spawn capacity.
         * @param event the death event
         */
        @SubscribeEvent
        public static void illagerDieEvent(LivingDeathEvent event) {
            LivingEntity illager = event.getEntity();
            if (illager instanceof AbstractIllager || illager instanceof AbstractVillager) {
                List<? extends LivingEntity> list = illager.level().getNearbyEntities(Necromancer.class, TargetingConditions.forNonCombat(),illager,illager.getBoundingBox().inflate(10.0D, 8.0D, 10.0D));
                for (LivingEntity necromancer:list) {
                    ((Necromancer) necromancer).addMinionToStock(2);
                }
            }
        }

        @SubscribeEvent
        public static void grabberAbortTotemUse(LivingUseTotemEvent event) {
            if (event.getEntity() instanceof Grabber) {
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void KnightSkeletonDamageIncreased(LivingDamageEvent.Pre event) {
            if (event.getSource().getEntity() instanceof KnightSkeleton knight && knight.isDashing()) {
                event.setNewDamage(event.getNewDamage() + KnightSkeleton.DASH_BONUS_DAMAGE);
            }
        }

        @SubscribeEvent
        public static void addVillagerTradesEvent(VillagerTradesEvent event){
            if (event.getType() == VillagerProfession.CLERIC){
                event.getTrades().get(2).add(new VillagerTrades.ItemListing() {
                    @Override
                    public MerchantOffer getOffer(Entity entity, RandomSource rdmSource) {
                        return new MerchantOffer(new ItemCost(BonesRegistry.SKELETON_SOUL.item(), 2), new ItemStack(Items.EMERALD), 12, 12, 0.2F);
                    }
                });
                event.getTrades().get(5).add(new VillagerTrades.ItemListing() {
                    @Override
                    public MerchantOffer getOffer(Entity entity, RandomSource rdmSource) {
                        return new MerchantOffer(new ItemCost(BonesRegistry.SKELETON_SOUL.item(), 4), Optional.of(new ItemCost(BonesRegistry.BLADE.item(),1)), new ItemStack(BonesRegistry.HAUNTER_BLADE.item()), 12, 12, 0.2F);
                    }
                });
            }
        }
    }

    @EventBusSubscriber(modid = BonesUpdate.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class BonesCommonEvents {

        @SubscribeEvent
        public static void gatherBonesData(GatherDataEvent event) {
            event.getGenerator().addProvider(
                    event.includeServer(),
                    (DataProvider.Factory<BonesRegistry.BonesEntityTagsProvider>) output -> new BonesRegistry.BonesEntityTagsProvider(output, event.getLookupProvider())
            );
        }

        @SubscribeEvent
        public static void entityAttributes(EntityAttributeCreationEvent event) {
            event.put(BonesRegistry.GRABBER.type(), Grabber.getCustomAttributes().build());
            event.put(BonesRegistry.MINION.type(), Minion.getCustomAttributes().build());
            event.put(BonesRegistry.NECROMANCER.type(), Necromancer.getCustomAttributes().build());
            event.put(BonesRegistry.REAPER.type(), Reaper.getCustomAttributes().build());
            event.put(BonesRegistry.KNIGHT_SKELETON.type(), KnightSkeleton.getCustomAttributes().build());
            event.put(BonesRegistry.HAUNTER_SKELETON.type(), HaunterSkeleton.getCustomAttributes().build());
            event.put(BonesRegistry.BROKEN_SKELETON.type(), BrokenSkeleton.getCustomAttributes().build());
        }

        @SubscribeEvent
        public static void entitySpawnRestriction(RegisterSpawnPlacementsEvent event) {
            event.register(BonesRegistry.KNIGHT_SKELETON.type(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(BonesRegistry.BROKEN_SKELETON.type(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(BonesRegistry.GRABBER.type(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(BonesRegistry.HAUNTER_SKELETON.type(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(BonesRegistry.NECROMANCER.type(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }

    }
}
