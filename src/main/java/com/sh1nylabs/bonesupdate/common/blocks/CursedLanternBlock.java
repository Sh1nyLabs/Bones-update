package com.sh1nylabs.bonesupdate.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CursedLanternBlock extends LanternBlock {
    private static final VoxelShape newAABB = Shapes.or(Block.box(6.0D, 0.0D, 6.0D, 10.0D, 7.0D, 10.0D));
    public CursedLanternBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return newAABB;
    }
}
