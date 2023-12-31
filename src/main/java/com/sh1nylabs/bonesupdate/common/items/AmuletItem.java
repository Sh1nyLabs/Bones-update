package com.sh1nylabs.bonesupdate.common.items;

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BonesBrokenSkeletons;
import com.sh1nylabs.bonesupdate.common.unclassed.CanPacifyGraves;
import com.sh1nylabs.bonesupdate.init.BonesEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AmuletItem extends Item implements CanPacifyGraves {
    public AmuletItem(Properties properties) {
        super(properties);
    }

    /**
     * Kill the bones skeleton if the skeleton is broken, otherwise it will do nothing.
     *
     * @param stack : the stack in the used hand
     * @param player : The player which interacts with the entity
     * @param entity : The targeted living entity
     * @param hand : The hand used to interact with the entity
     * @return InteractionResult : SUCCESS / PASS
     */
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (entity instanceof BonesBrokenSkeletons brokenSkeleton) {
            if (!player.level.isClientSide() && brokenSkeleton.isAlive() && brokenSkeleton.isBroken()) {
                brokenSkeleton.setHealth(-1.0F);
                brokenSkeleton.die(player.level.damageSources().playerAttack(player));

                stack.hurtAndBreak(1, player, player1 -> {player1.broadcastBreakEvent(hand);});
                player.getCooldowns().addCooldown(this, 80); // FIX_VALUE
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /**
     * useOn will apply the function 'pacifyGrave' if the item contains the enchantment 'Serenity'.
     *
     * @param context : The context when it is used
     * @return InteractionResult: SUCCESS / PASS
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        return tryToPacifyGrave(context, context.getItemInHand(), context.getPlayer());
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
        return enchantment.category == EnchantmentCategory.BREAKABLE
                || enchantment.category == BonesEnchantments.SKELETON_QUEST;
    }
}
