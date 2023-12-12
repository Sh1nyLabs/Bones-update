package com.sh1nylabs.bonesupdate.common.items;

import com.sh1nylabs.bonesupdate.common.blocks.GraveBlock;
import com.sh1nylabs.bonesupdate.init.BonesEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import static com.sh1nylabs.bonesupdate.common.blocks.GraveBlock.HAUNTED;

/**
 * An Item class used to group items which can pacify graves.
 */
public class GravePacifierItem extends Item {
    public static final int ITEM_COOLDOWN = 100; //FIX_VALUE

    public GravePacifierItem(Properties properties) {
        super(properties);
    }

    /**
     * useOn will apply the function 'pacifyGrave' if the item contains the enchantment 'Serenity'.
     *
     * @param context : The context when it is used
     * @return InteractionResult: SUCCESS / PASS
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        if (stack.getAllEnchantments().containsKey(BonesEnchantments.SERENITY.get())) {
            return this.pacifyGrave(context, stack);
        }
        return InteractionResult.PASS;
    }

    /**
     * pacifyGrave is a function called to, under specific conditions,
     * change the state of the grave from HAUNTED = true to HAUNTED = false
     *
     * @param context : The context when it is used
     * @param stack : The stack in the player hand
     * @return InteractionResult: SUCCESS / PASS
     */
    private InteractionResult pacifyGrave(UseOnContext context, ItemStack stack) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.getBlock() instanceof GraveBlock graveBlock && blockstate.getValue(HAUNTED)) {
            BlockState blockstate1 = graveBlock.setHaunted(blockstate,false);
            level.setBlockAndUpdate(blockpos, blockstate1);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(context.getPlayer(), blockstate1));

            useItemStack(stack, context.getPlayer(), context.getHand());

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /**
     * Enchantments applicable on the GravePacifier items:
     * Category "SKELETON_QUEST": "Serenity"
     * Category "BREAKABLE": "Mending", "Unbreaking"
     *
     * For enchantment compatibility, check enchantments classes.
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)  {
        return enchantment.category == EnchantmentCategory.BREAKABLE || enchantment.category == BonesEnchantments.SKELETON_QUEST;
    }

    public void useItemStack(ItemStack stack, Player player, InteractionHand hand) {
        if (player != null) {
            stack.hurtAndBreak(1, player, player1 -> {
                player1.broadcastBreakEvent(hand);
            });
            player.getCooldowns().addCooldown(this, ITEM_COOLDOWN);
        }
    }
}
