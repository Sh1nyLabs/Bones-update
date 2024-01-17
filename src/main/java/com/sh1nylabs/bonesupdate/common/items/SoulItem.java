package com.sh1nylabs.bonesupdate.common.items;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.blocks.GraveBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import static com.sh1nylabs.bonesupdate.common.blocks.GraveBlock.HAUNTED;

/**
 * A soul-like item.
 * - Use conditions:
 *      The item is used on a Grave block, which is in blockstate "HAUNTED = False".
 * - Effect:
 *      The Grave (which was formerly in a state "HAUNTED = False") becomes haunted ("HAUNTED = true"),
 *      which let the grave be able to spawn minions (under specific conditions) or be used by a Necromancer
 *      (under specific conditions).
 *      The soul item is consumed.
 */
public class SoulItem extends Item {
    public SoulItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {

        if (context.getLevel() instanceof ServerLevel level) {
            BlockPos blockpos = context.getClickedPos();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.getBlock() instanceof GraveBlock graveBlock && !blockstate.getValue(HAUNTED)) {
                BlockState blockstate1 = graveBlock.setHaunted(blockstate,true);
                level.setBlockAndUpdate(blockpos, blockstate1);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(context.getPlayer(), blockstate1));

                if (context.getPlayer() != null) {
                    context.getItemInHand().shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
