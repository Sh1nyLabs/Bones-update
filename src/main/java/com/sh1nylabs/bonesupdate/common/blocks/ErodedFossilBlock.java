package com.sh1nylabs.bonesupdate.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ErodedFossilBlock extends Block {
    private static final VoxelShape AABB = Shapes.or(Block.box(3.0D, 0.0D, 1.0D, 8.0D, 4.0D, 11.0D));

    public ErodedFossilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return AABB;
    }
}
