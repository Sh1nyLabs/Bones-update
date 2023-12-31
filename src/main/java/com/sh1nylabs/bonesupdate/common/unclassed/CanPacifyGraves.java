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

    /** Function used to damage the item after an usage.
     *
     * @param stack
     * @param player
     * @param hand
     */
    //void useItemStack(ItemStack stack, Player player, InteractionHand hand);

    /** function used by some items to pacify graves (especially AmuletItem and NecroScepteritem)
     * If the item contains the enchantment 'SERENITY', it will change the grave from state
     * 'HAUNTED' = false to 'HAUNTED' = true.
     *
     * @param context : context of the use
     * @param stack : ItemStack used
     * @param player : Player using the itemStack
     * @return InteractionResult : SUCCESS / PASS
     */
    default InteractionResult tryToPacifyGrave(UseOnContext context, ItemStack stack, Player player) {
        if (stack.getAllEnchantments().containsKey(BonesEnchantments.SERENITY.get())) {
            Level level = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.getBlock() instanceof GraveBlock graveBlock && blockstate.getValue(HAUNTED)) {
                BlockState blockstate1 = graveBlock.setHaunted(blockstate, false);
                level.setBlockAndUpdate(blockpos, blockstate1);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate1));

                if (player != null) {
                    stack.hurtAndBreak(1, player, player1 -> {
                        player1.broadcastBreakEvent( context.getHand());
                    });
                    player.getCooldowns().addCooldown(stack.getItem(), 80);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
