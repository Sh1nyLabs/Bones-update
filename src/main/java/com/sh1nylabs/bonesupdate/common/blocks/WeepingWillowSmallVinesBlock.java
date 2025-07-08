package com.sh1nylabs.bonesupdate.common.blocks;

import com.mojang.serialization.MapCodec;
import com.sh1nylabs.bonesupdate.init.BonesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WeepingWillowSmallVinesBlock extends GrowingPlantHeadBlock {
    private static final MapCodec<WeepingWillowSmallVinesBlock> CODEC = simpleCodec(WeepingWillowSmallVinesBlock::new);
    protected static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public WeepingWillowSmallVinesBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1D);
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        double d0 = 1.0D;
        int i;
        for(i = 0; randomSource.nextDouble() < d0; ++i) {
            d0 *= 0.826D;
        }
        return i;
    }

    protected Block getBodyBlock() {
        return BonesBlocks.WEEPING_WILLOW_VINES.get();
    }

    protected boolean canGrowInto(BlockState blockState) {
        return blockState.isAir();
    }

    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock()) || blockstate.is(BonesBlocks.WEEPING_WILLOW_LEAVES.get()) || blockstate.is(Blocks.OAK_LOG);
        }
    }

}
