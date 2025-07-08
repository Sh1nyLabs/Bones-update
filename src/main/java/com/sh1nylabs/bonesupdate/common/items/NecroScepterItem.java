package com.sh1nylabs.bonesupdate.common.items;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.FriendlySkeleton;
import com.sh1nylabs.bonesupdate.common.unclassed.CanPacifyGraves;
import com.sh1nylabs.bonesupdate.common.unclassed.CanSummonMinions;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;


public class NecroScepterItem extends Item implements CanSummonMinions, CanPacifyGraves {
    public static final int MAX_MINIONS_SUMMONED = 3; //FIXED_VALUE

    public NecroScepterItem(Properties properties) {
        super(properties);
    }

    /**
     * When the player uses the tool to interact with an entity, the necromancer scepter converts it
     * into a friendly mob, given some conditions:
     *     - the item used contains the "Subordinate" enchantment
     *     - the targeted entity if a "BonesFriendlySkeleton".
     *
     * @param stack : the itemStack used
     * @param player : the player which uses the tool
     * @param entity : the targeted entity
     * @param hand : the hand where is the tool
     * @return InteractionResultHolder : SUCCESS / PASS
     */
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (EnchantmentHelper.getItemEnchantmentLevel(BonesRegistry.SUBALTERN.toHolder(player.registryAccess()), stack) != 0 && entity.isAlive() && entity instanceof FriendlySkeleton friendlySkeleton && !friendlySkeleton.isFriendly()) {
            Level level = player.level();
            if (!level.isClientSide()) {
                friendlySkeleton.setFriendly(true);
            } else {
                level.addParticle(ParticleTypes.HEART, friendlySkeleton.getRandomX(1.0D), friendlySkeleton.getRandomY() + 0.1D, friendlySkeleton.getRandomZ(1.0D), friendlySkeleton.getRandom().nextGaussian() * 0.02D, friendlySkeleton.getRandom().nextGaussian() * 0.02D, friendlySkeleton.getRandom().nextGaussian() * 0.02D);
                level.addParticle(ParticleTypes.HEART, friendlySkeleton.getRandomX(1.0D), friendlySkeleton.getRandomY() + 0.1D, friendlySkeleton.getRandomZ(1.0D), friendlySkeleton.getRandom().nextGaussian() * 0.02D, friendlySkeleton.getRandom().nextGaussian() * 0.02D, friendlySkeleton.getRandom().nextGaussian() * 0.02D);
                level.addParticle(ParticleTypes.HEART, friendlySkeleton.getRandomX(1.0D), friendlySkeleton.getRandomY() + 0.1D, friendlySkeleton.getRandomZ(1.0D), friendlySkeleton.getRandom().nextGaussian() * 0.02D, friendlySkeleton.getRandom().nextGaussian() * 0.02D, friendlySkeleton.getRandom().nextGaussian() * 0.02D);
            }


            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            player.getCooldowns().addCooldown(this, 120);

            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    /** This function (inherited from CanSummonMinion interface) is not used in this Item class. */
    public void delayNextSummon(RandomSource rdmSource) {}

    /**
     * When the player uses the tool, the necromancer scepter summons minions.
     * It does not work when the scepter is enchanted with "Subordinate".
     *
     * @param level
     * @param player : the player which uses the tool
     * @param hand : the hand where is the tool
     * @return InteractionResultHolder : SUCCESS / PASS
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean hasLeader = (EnchantmentHelper.getItemEnchantmentLevel(BonesRegistry.LEADER.toHolder(player.registryAccess()), stack) >= 0);
        if (!level.isClientSide() && EnchantmentHelper.getItemEnchantmentLevel(BonesRegistry.SUBALTERN.toHolder(player.registryAccess()), stack) == 0){
            this.summonMinion((ServerLevel) level, level.getRandom(),
                    MAX_MINIONS_SUMMONED + (hasLeader? 4 : 0),
                    player.blockPosition(), MobSpawnType.MOB_SUMMONED, new Minion.MinionData(this));

            stack.hurtAndBreak((hasLeader ? 2 : 1), player, LivingEntity.getSlotForHand(hand));
            player.getCooldowns().addCooldown(this, (hasLeader ? 140 : 100));

            return InteractionResultHolder.consume(player.getItemInHand(hand));
        } else {
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
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
}
