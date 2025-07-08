package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.*;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

public class BonesEntities {
    public static final DeferredRegister<EntityType<?>> BU_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BonesUpdate.MODID);

    public static final RegistryObject<EntityType<BrokenSkeleton>> BROKEN_SKELETON = BU_ENTITIES.register("broken_skeleton",
            () -> EntityType.Builder.of(BrokenSkeleton::new,MobCategory.MONSTER).sized(0.6F,0.5F).build(BonesUpdate.MODID+":"+"broken_skeleton"));

    public static final RegistryObject<EntityType<HaunterSkeleton>> HAUNTER_SKELETON = registerMonster("haunter_skeleton", HaunterSkeleton::new, 0.6F, 1.99F);
    public static final RegistryObject<EntityType<KnightSkeleton>> KNIGHT_SKELETON = registerMonster("knight_skeleton", KnightSkeleton::new, 0.6F, 1.99F);
    public static final RegistryObject<EntityType<Grabber>> GRABBER = registerMonster("grabber", Grabber::new,0.7F ,1.6F, 1.5F);
    public static final RegistryObject<EntityType<Minion>> MINION = registerMonster("minion", Minion::new,0.35F, 0.85F, 0.85F * 0.85F);
    public static final RegistryObject<EntityType<Necromancer>> NECROMANCER = BU_ENTITIES.register("necromancer", () -> EntityType.Builder.of( Necromancer::new,MobCategory.MONSTER).build(BonesUpdate.MODID+":"+"necromancer"));
    public static final RegistryObject<EntityType<Reaper>> REAPER = registerMonster("reaper", Reaper::new, 0.6F, 1.4F);

    public static void registerWaveMembers() {
        //TODO: reactivate necromancer in raids
        //Raid.RaiderType.create("NECROMANCER", NECROMANCER.get(), new int[]{0, 0, 1, 0, 1, 1, 2, 1}); //FIX_VALUES
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerMonster(String name, EntityType.EntityFactory<T> buildClass, float length, float height, float eyeHeight) {
        return BU_ENTITIES.register(name,
                () -> EntityType.Builder.of(buildClass,MobCategory.MONSTER).sized(length, height).eyeHeight(eyeHeight).build(BonesUpdate.MODID+":"+name));
    }
    private static <T extends Entity> RegistryObject<EntityType<T>> registerMonster(String name, EntityType.EntityFactory<T> buildClass, float length, float height) {
        return BU_ENTITIES.register(name,
                () -> EntityType.Builder.of(buildClass,MobCategory.MONSTER).sized(length, height).build(BonesUpdate.MODID+":"+name));
    }
    private static <T extends Entity> RegistryObject<EntityType<T>> registerMonster(String name, EntityType.EntityFactory<T> buildClass) {
        return BU_ENTITIES.register(name,
                () -> EntityType.Builder.of(buildClass,MobCategory.MONSTER).build(BonesUpdate.MODID+":"+name));
    }

    public static class BonesEntityTagsProvider extends EntityTypeTagsProvider {

        public BonesEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, provider);
        }

        public void addTags(HolderLookup.Provider provider) {
            this.tag(EntityTypeTags.ILLAGER).add(BonesEntities.NECROMANCER.get());
            this.tag(EntityTypeTags.SKELETONS)
                    .add(BonesEntities.REAPER.get())
                    .add(BonesEntities.MINION.get())
                    .add(BonesEntities.GRABBER.get())
                    .add(BonesEntities.HAUNTER_SKELETON.get())
                    .add(BonesEntities.KNIGHT_SKELETON.get());
        }
    }

}
