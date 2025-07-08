package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.blocks.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BonesBlocks {
    public static final DeferredRegister<Block> BU_BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, BonesUpdate.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BU_BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BonesUpdate.MODID);

    public static final DeferredHolder<Block, Block> GRAVE_BLOCK = BU_BLOCKS.register("grave", () -> new GraveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0F)));
    public static final Supplier<BlockEntityType<GraveBlockEntity>> GRAVE = BU_BLOCK_ENTITIES.register("grave", () -> BlockEntityType.Builder.of(GraveBlockEntity::new, GRAVE_BLOCK.get()).build(null));
    public static final DeferredHolder<Block, Block> CURSED_LANTERN = BU_BLOCKS.register("cursed_lantern", () -> new CursedLanternBlock((BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).noOcclusion().lightLevel((light) -> {return 10;}))));
    public static final DeferredHolder<Block, Block> WEEPING_WILLOW_SMALL_VINES = BU_BLOCKS.register("weeping_willow_small_vines",() -> new WeepingWillowSmallVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).randomTicks().noCollission().instabreak()));
    public static final DeferredHolder<Block, Block> WEEPING_WILLOW_VINES = BU_BLOCKS.register("weeping_willow_vines",() -> new WeepingWillowVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).noCollission().instabreak()));
    public static final DeferredHolder<Block, Block> WEEPING_WILLOW_LEAVES = BU_BLOCKS.register("weeping_willow_leaves",() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).randomTicks().noOcclusion()));

    public static final DeferredHolder<Block, Block> ERODED_FOSSIL = BU_BLOCKS.register("eroded_fossil", () -> new ErodedFossilBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK)));
    public static final DeferredHolder<Block, Block> GUARDIAN_FOSSIL = BU_BLOCKS.register("guardian_fossil", () -> new GuardianFossilBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).noOcclusion()));

}
