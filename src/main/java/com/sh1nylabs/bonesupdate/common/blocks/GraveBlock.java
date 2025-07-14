package com.sh1nylabs.bonesupdate.common.blocks;

import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GraveBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty HAUNTED=BooleanProperty.create("haunted");

    private static final VoxelShape FACE_S = Shapes.or(Block.box(2.0D, 0.0D, 0.0D, 13.0D, 3.0D, 15.0D), Block.box(3.0D, 3.0D, 13.0D, 12.0D, 14.0D, 15.0D));
    private static final VoxelShape FACE_N = Shapes.or(Block.box(3.0D, 0.0D, 1.0D, 14.0D, 3.0D, 16.0D), Block.box(4.0D, 3.0D, 1.0D, 13.0D, 14.0D, 3.0D));
    private static final VoxelShape FACE_E = Shapes.or(Block.box(0.0D, 0.0D, 3.0D, 15.0D, 3.0D, 14.0D), Block.box(13.0D, 3.0D, 4.0D, 15.0D, 14.0D, 13.0D));
    private static final VoxelShape FACE_W = Shapes.or(Block.box(1.0D, 0.0D, 2.0D, 16.0D, 3.0D, 13.0D), Block.box(1.0D, 3.0D, 3.0D, 3.0D, 14.0D, 12.0D));

    public GraveBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAUNTED,true));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockState) {
        blockState.add(FACING,HAUNTED);
    }

    public BlockState setHaunted(BlockState blockstate,boolean isHaunted) {
        return blockstate.setValue(HAUNTED,isHaunted);
    }
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
        Direction direction = blockState.getValue(FACING);
        return direction == Direction.NORTH ? FACE_N : direction == Direction.SOUTH ? FACE_S : direction == Direction.EAST ? FACE_E : FACE_W;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
        return new GraveBlockEntity(position,state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return ( (level1,pos,blockState,blockEntity)->{
            if (blockEntity instanceof GraveBlockEntity grave) {
                grave.blockEntityTicker(level1,pos,blockState);
            }
        });
    }

    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        AABB aabb = player.getBoundingBox().inflate(10.0D,6.0D,10.0D);
        List<Villager> list = level.getNearbyEntities(Villager.class, TargetingConditions.forCombat().range(64.0D), player, aabb);
        for (Villager villager: list){
            villager.getGossips().add(player.getUUID(), GossipType.MINOR_NEGATIVE, 25); // TODO:PROBLEM?
            villager.playSound(SoundEvents.VILLAGER_NO, 1.0F, villager.getVoicePitch());
        }
        if (state.getValue(HAUNTED) && level.getRandom().nextInt(4)==0 && !level.isClientSide()) {
            Reaper reaper = BonesRegistry.REAPER.type().create(level);
            if (reaper!=null) {
                reaper.moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), level.getRandom().nextFloat(), 0.0F);
                net.minecraftforge.event.ForgeEventFactory.onFinalizeSpawn(reaper, (ServerLevel) level, level.getCurrentDifficultyAt(blockPos), MobSpawnType.SPAWNER, null);
                ((ServerLevel) level).tryAddFreshEntityWithPassengers(reaper);
                level.gameEvent(reaper, GameEvent.ENTITY_PLACE, blockPos);
            }
        }
        super.playerDestroy(level,player,blockPos,state,blockEntity,stack);
    }

}
