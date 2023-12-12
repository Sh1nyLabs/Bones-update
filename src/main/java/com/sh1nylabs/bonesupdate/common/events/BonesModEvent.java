package com.sh1nylabs.bonesupdate.common.events;


import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BonesBrokenSkeletons;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.init.BonesEntities;
import com.mojang.logging.LogUtils;
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
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public class BonesModEvent {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Mod.EventBusSubscriber(modid = BonesUpdate.MODID)
    public static class BonesForgeEvents {
    @SubscribeEvent
    public static void catchLiveEvent(MobSpawnEvent.FinalizeSpawn event) { //TODO: delete this after mod final test
        if (event.getEntity() instanceof BonesBrokenSkeletons || event.getEntity() instanceof Necromancer) {
            LOGGER.info("-------Detecting a spawn: {} -----------", event.getEntity().getName());
        }
    }

    @SubscribeEvent
    public static void SkeletonBrokeEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof BonesBrokenSkeletons customSkeleton && !customSkeleton.getLevel().isClientSide()) {
            float finalDamage = customSkeleton.updateDamageIfBecomesBroken(event.getAmount(),event.getSource());
            event.setAmount(finalDamage);
        }
    }

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
        public static void addCartographerTradesEvent(VillagerTradesEvent event){
            if (event.getType() == VillagerProfession.CARTOGRAPHER){
                event.getTrades().get(1).add(new VillagerTrades.ItemListing() { //TODO: change 1 to 4
                    @Nullable
                    @Override
                    public MerchantOffer getOffer(Entity entity, RandomSource rdmSource) {
                        if (!(entity.level instanceof ServerLevel)) {
                            return null;
                        } else {
                            ServerLevel serverlevel = (ServerLevel)entity.level;
                            BlockPos blockpos = serverlevel.findNearestMapStructure(StructureTags.OCEAN_RUIN, entity.blockPosition(), 100, true);
                            if (blockpos != null) {
                                ItemStack itemstack = MapItem.create(serverlevel, blockpos.getX(), blockpos.getZ(), (byte)2, true, true);
                                MapItem.renderBiomePreviewMap(serverlevel, itemstack);
                                MapItemSavedData.addTargetDecoration(itemstack, blockpos, "+", MapDecoration.Type.MANSION);
                                itemstack.setHoverName(Component.translatable("filled_map.necromancer_crypte"));
                                return new MerchantOffer(new ItemStack(Items.EMERALD, 16), new ItemStack(Items.COMPASS), itemstack, 12, 12, 0.2F);
                            } else {
                                return null;
                            }
                        }
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
            event.put(BonesEntities.KNIGHT_SKELETON.get(), KnightSkeleton.getCustomAttributes().build());
        }


        @SubscribeEvent
        public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
            event.register(BonesEntities.KNIGHT_SKELETON.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
            event.register(BonesEntities.NECROMANCER.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        }
    }
}
