package com.sh1nylabs.bonesupdate.common.events;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BonesBrokenSkeletons;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.HaunterSkeleton;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import com.sh1nylabs.bonesupdate.init.BonesEntities;
import com.sh1nylabs.bonesupdate.init.BonesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BonesModEvent {

    @Mod.EventBusSubscriber(modid = BonesUpdate.MODID)
    public static class BonesForgeEvents {

        /**
         * When a broken skeleton is hurt, he might switch to a broken state instead of dying.
         * @param event the damage event
         */
        @SubscribeEvent
        public static void SkeletonBrokeEvent(LivingDamageEvent event) {
            if (event.getEntity() instanceof BonesBrokenSkeletons customSkeleton && !customSkeleton.getLevel().isClientSide()) {
                float finalDamage = customSkeleton.updateDamageIfBecomesBroken(event.getAmount(),event.getSource());
                event.setAmount(finalDamage);
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
                List<? extends LivingEntity> list = illager.getLevel().getNearbyEntities(Necromancer.class, TargetingConditions.forNonCombat(),illager,illager.getBoundingBox().inflate(10.0D, 8.0D, 10.0D));
                for (LivingEntity necromancer:list) {
                    ((Necromancer) necromancer).addMinionToStock(2);
                }
            }
        }

        @SubscribeEvent
        public static void addVillagerTradesEvent(VillagerTradesEvent event){
            if (event.getType() == VillagerProfession.CLERIC){
                event.getTrades().get(2).add(new VillagerTrades.ItemListing() {
                    @Override
                    public MerchantOffer getOffer(Entity entity, RandomSource rdmSource) {
                        return new MerchantOffer(new ItemStack(BonesItems.SKELETON_SOUL.get(), 2), new ItemStack(Items.EMERALD), 12, 12, 0.2F);
                    }
                });
                event.getTrades().get(5).add(new VillagerTrades.ItemListing() {
                    @Override
                    public MerchantOffer getOffer(Entity entity, RandomSource rdmSource) {
                        return new MerchantOffer(new ItemStack(BonesItems.SKELETON_SOUL.get(), 4), new ItemStack(BonesItems.BLADE.get()), new ItemStack(BonesItems.HAUNTER_BLADE.get()), 12, 12, 0.2F);
                    }
                });
            }
        }
    }

    @Mod.EventBusSubscriber(modid = BonesUpdate.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class BonesCommonEvents {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                BonesEntities.registerWaveMembers();
            });
        }

        @SubscribeEvent
        public static void entityAttributes(EntityAttributeCreationEvent event) {
            event.put(BonesEntities.MINION.get(), Minion.getCustomAttributes().build());
            event.put(BonesEntities.NECROMANCER.get(), Necromancer.getCustomAttributes().build());
            event.put(BonesEntities.REAPER.get(), Reaper.getCustomAttributes().build());
            event.put(BonesEntities.KNIGHT_SKELETON.get(), KnightSkeleton.getCustomAttributes().build());
            event.put(BonesEntities.HAUNTER_SKELETON.get(), HaunterSkeleton.getCustomAttributes().build());
        }

        @SubscribeEvent
        public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
            event.register(BonesEntities.KNIGHT_SKELETON.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
            event.register(BonesEntities.HAUNTER_SKELETON.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
            event.register(BonesEntities.NECROMANCER.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        }
    }
}
