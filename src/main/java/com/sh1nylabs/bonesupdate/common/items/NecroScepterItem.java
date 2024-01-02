package com.sh1nylabs.bonesupdate.common.items;

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BonesBrokenSkeletons;
import com.sh1nylabs.bonesupdate.common.unclassed.CanPacifyGraves;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
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
        if (stack.getAllEnchantments().containsKey(BonesEnchantments.SUBORDINATE.get()) && entity.isAlive() && entity instanceof BonesBrokenSkeletons friendlySkeleton && !friendlySkeleton.isFriendly()) {
            friendlySkeleton.becomesFriendly(player.level);

            stack.hurtAndBreak(1, player, player1 -> {player1.broadcastBreakEvent(hand);});
            player.getCooldowns().addCooldown(this, 120);

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
        boolean hasLeader = stack.getAllEnchantments().containsKey(BonesEnchantments.LEADER.get());
        if (!level.isClientSide() && !stack.getAllEnchantments().containsKey(BonesEnchantments.SUBORDINATE.get())){
            this.summonMinion((ServerLevel) level, level.getRandom(),
                    MAX_MINIONS_SUMMONED + (hasLeader? 4 : 0),
                    player.blockPosition(), MobSpawnType.MOB_SUMMONED);

            stack.hurtAndBreak((hasLeader ? 2 : 1), player, player1 -> {player1.broadcastBreakEvent(hand);});
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

    /**
     * Enchantments applicable on the Necromancer scepter:
     * Category "NECROMANCY": "Leader", "Subordinate"
     * Category "SKELETON_QUEST": "Serenity"
     * Category "BREAKABLE": "Mending", "Unbreaking"
     *
     * For enchantment compatibility, check enchantments classes.
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)  {
        return enchantment.category == BonesEnchantments.NECROMANCY
                || enchantment.category == EnchantmentCategory.BREAKABLE
                || enchantment.category == BonesEnchantments.SKELETON_QUEST;
    }
}
