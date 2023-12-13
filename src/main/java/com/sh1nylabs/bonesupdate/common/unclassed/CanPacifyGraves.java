package com.sh1nylabs.bonesupdate.common.unclassed;

import com.sh1nylabs.bonesupdate.common.blocks.GraveBlock;
import com.sh1nylabs.bonesupdate.init.BonesEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import static com.sh1nylabs.bonesupdate.common.blocks.GraveBlock.HAUNTED;

public interface CanPacifyGraves {

    void useItemStack(ItemStack stack, Player player, InteractionHand hand);
    default InteractionResult tryToPacifyGrave(UseOnContext context, ItemStack stack, Player player) {

        if (stack.getAllEnchantments().containsKey(BonesEnchantments.SERENITY.get())) {
            Level level = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.getBlock() instanceof GraveBlock graveBlock && blockstate.getValue(HAUNTED)) {
                BlockState blockstate1 = graveBlock.setHaunted(blockstate, false);
                level.setBlockAndUpdate(blockpos, blockstate1);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate1));

                useItemStack(stack, player, context.getHand());

                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
