package com.sh1nylabs.bonesupdate.common.items;

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BonesBrokenSkeletons;
import com.sh1nylabs.bonesupdate.common.unclassed.CanSummonMinions;
import com.sh1nylabs.bonesupdate.init.BonesEnchantments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;


public class NecroScepterItem extends GravePacifierItem implements CanSummonMinions {
    public static final int MAX_MINIONS_SUMMONED=3; //FIX_VALUE

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
        if (stack.getAllEnchantments().containsKey(BonesEnchantments.SUBORDINATE.get()) && entity.isAlive() && entity instanceof BonesBrokenSkeletons friendlySkeleton && !friendlySkeleton.isFriendly()) {
            friendlySkeleton.becomesFriendly(player.level);
            useItemStack(stack, player, hand);
            return InteractionResult.sidedSuccess(player.level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    /** When summoned, a Minion is always friendly. */
    public void applyLastSpawnConfigurations(Minion minion) {
        minion.setOwner(null);
        minion.setFriendly(true);
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
        if (!level.isClientSide() && !stack.getAllEnchantments().containsKey(BonesEnchantments.SUBORDINATE.get())){
            this.summonMinion((ServerLevel) level, level.getRandom(),
                    MAX_MINIONS_SUMMONED + (stack.getAllEnchantments().containsKey(BonesEnchantments.LEADER.get())? 4 : 0),
                    player.blockPosition(), MobSpawnType.MOB_SUMMONED);

            this.useItemStack(stack, player,hand);
            return InteractionResultHolder.consume(player.getItemInHand(hand));
        } else {
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
    }

    /**
     * Enchantments applicable on the Necromancer scepter:
     * Category "NECROMANCY": "Leader", "Subordinate"
     * Categories inherited from the GravePacifier item.
     *
     * For enchantment compatibility, check enchantments classes.
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)  {
        return enchantment.category == BonesEnchantments.NECROMANCY || super.canApplyAtEnchantingTable(stack,enchantment);
    }

}
