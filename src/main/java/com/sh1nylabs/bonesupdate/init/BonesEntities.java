package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BrokenSkeleton;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.HaunterSkeleton;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BonesEntities {
    public static final DeferredRegister<EntityType<?>> BU_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BonesUpdate.MODID);

    public static final RegistryObject<EntityType<BrokenSkeleton>> BROKEN_SKELETON = BU_ENTITIES.register("broken_skeleton",
            () -> EntityType.Builder.of(BrokenSkeleton::new,MobCategory.CREATURE).sized(1.1F,0.3F).build(BonesUpdate.MODID+":"+"broken_skeleton"));

    public static final RegistryObject<EntityType<HaunterSkeleton>> HAUNTER_SKELETON = registerMonster("haunter_skeleton", HaunterSkeleton::new, 0.6F, 1.99F);
    public static final RegistryObject<EntityType<KnightSkeleton>> KNIGHT_SKELETON = registerMonster("knight_skeleton", KnightSkeleton::new, 0.6F, 1.99F);
    public static final RegistryObject<EntityType<Minion>> MINION = registerMonster("minion", Minion::new,0.35F,0.85F);
    public static final RegistryObject<EntityType<Necromancer>> NECROMANCER = registerMonster("necromancer", Necromancer::new);
    public static final RegistryObject<EntityType<Reaper>> REAPER = registerMonster("reaper", Reaper::new, 0.6F, 1.4F);

    public static void registerWaveMembers() {
        Raid.RaiderType.create("NECROMANCER", NECROMANCER.get(), new int[]{0, 0, 1, 0, 1, 1, 2, 1}); //FIX_VALUES
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerMonster(String name, EntityType.EntityFactory<T> buildClass, float length, float height) {
        return BU_ENTITIES.register(name,
                () -> EntityType.Builder.of(buildClass,MobCategory.MONSTER).sized(length, height).build(BonesUpdate.MODID+":"+name));
    }
    private static <T extends Entity> RegistryObject<EntityType<T>> registerMonster(String name, EntityType.EntityFactory<T> buildClass) {
        return BU_ENTITIES.register(name,
                () -> EntityType.Builder.of(buildClass,MobCategory.MONSTER).build(BonesUpdate.MODID+":"+name));
    }

}
