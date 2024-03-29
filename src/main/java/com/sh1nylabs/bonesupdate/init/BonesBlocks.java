package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.blocks.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BonesBlocks {
    public static final DeferredRegister<Block> BU_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BonesUpdate.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BU_BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BonesUpdate.MODID);

    public static final RegistryObject<Block> GRAVE_BLOCK = BU_BLOCKS.register("grave", () -> new GraveBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F)));
    public static final RegistryObject<BlockEntityType<GraveBlockEntity>> GRAVE = BU_BLOCK_ENTITIES.register("grave", () -> BlockEntityType.Builder.of(GraveBlockEntity::new, GRAVE_BLOCK.get()).build(null));
    public static final RegistryObject<Block> CURSED_LANTERN = BU_BLOCKS.register("cursed_lantern", () -> new CursedLanternBlock((BlockBehaviour.Properties.of(Material.METAL).strength(3.0F).noOcclusion().lightLevel((light) -> {return 10;}).sound(SoundType.LANTERN))));
    public static final RegistryObject<Block> WEEPING_WILLOW_SMALL_VINES = BU_BLOCKS.register("weeping_willow_small_vines",() -> new WeepingWillowSmallVinesBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_BLACK).randomTicks().noCollission().instabreak().sound(SoundType.VINE)));
    public static final RegistryObject<Block> WEEPING_WILLOW_VINES = BU_BLOCKS.register("weeping_willow_vines",() -> new WeepingWillowVinesBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_BLACK).noCollission().instabreak().sound(SoundType.VINE)));
    public static final RegistryObject<Block> WEEPING_WILLOW_LEAVES = BU_BLOCKS.register("weeping_willow_leaves",() -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()));
}
