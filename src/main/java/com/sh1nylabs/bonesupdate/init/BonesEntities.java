package com.sh1nylabs.bonesupdate.init;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.KnightSkeleton;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BonesEntities {
    public static final DeferredRegister<EntityType<?>> BU_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BonesUpdate.MODID);

    public static final RegistryObject<EntityType<Minion>> MINION=BU_ENTITIES.register("minion",
            () -> EntityType.Builder.of(Minion::new,MobCategory.MONSTER).sized(0.35F, 0.85F).build(BonesUpdate.MODID+":minion"));
    public static final RegistryObject<EntityType<Necromancer>> NECROMANCER=BU_ENTITIES.register("necromancer",
            () -> EntityType.Builder.of(Necromancer::new,MobCategory.MONSTER).build(BonesUpdate.MODID+":necromancer"));

    public static final RegistryObject<EntityType<KnightSkeleton>> KNIGHT_SKELETON=BU_ENTITIES.register("knight_skeleton",
            () -> EntityType.Builder.of(KnightSkeleton::new,MobCategory.MONSTER).build(BonesUpdate.MODID+":knight_skeleton"));

    public static void registerWaveMembers() {
        Raid.RaiderType.create("NECROMANCER", NECROMANCER.get(), new int[]{0, 0, 0, 0, 1, 1, 2, 1}); //TODO:FIX_VALUES
    }

}
