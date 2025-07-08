package com.sh1nylabs.bonesupdate.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BonesOrientableBlock extends Block {

    private final VoxelShape FACE_N;
    private final VoxelShape FACE_S;
    private final VoxelShape FACE_E;
    private final VoxelShape FACE_W;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BonesOrientableBlock(Properties properties, VoxelShape shape_n, VoxelShape shape_s, VoxelShape shape_e, VoxelShape shape_w) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        FACE_N = shape_n;
        FACE_S = shape_s;
        FACE_E = shape_e;
        FACE_W = shape_w;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockState) {
        blockState.add(FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        Direction direction = blockState.getValue(FACING);
        return direction == Direction.NORTH ? FACE_N : direction == Direction.SOUTH ? FACE_S : direction == Direction.EAST ? FACE_E : FACE_W;
    }
}
