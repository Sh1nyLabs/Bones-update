package com.sh1nylabs.bonesupdate.common.blocks;

import com.mojang.serialization.MapCodec;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WeepingWillowVinesBlock extends GrowingPlantBodyBlock {
    private static final MapCodec<WeepingWillowVinesBlock> CODEC = simpleCodec(WeepingWillowVinesBlock::new);
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public WeepingWillowVinesBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
    }

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return CODEC;
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BonesRegistry.WEEPING_WILLOW_SMALL_VINES.block();
    }

    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock()) || blockstate.is(BonesRegistry.WEEPING_WILLOW_LEAVES.block())  || blockstate.is(Blocks.OAK_LOG);
        }
    }
}
