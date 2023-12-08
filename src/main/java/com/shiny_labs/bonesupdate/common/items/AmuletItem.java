package com.shiny_labs.bonesupdate.common.items;

import com.shiny_labs.bonesupdate.common.entities.custom_skeletons.BonesBrokenSkeletons;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AmuletItem extends GravePacifierItem {
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
                this.useItemStack(stack, player,hand);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
