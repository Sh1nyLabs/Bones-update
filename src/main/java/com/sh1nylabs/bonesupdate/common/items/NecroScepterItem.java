package com.sh1nylabs.bonesupdate.common.items;

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BonesBrokenSkeletons;
import com.sh1nylabs.bonesupdate.init.BonesEnchantments;
import com.sh1nylabs.bonesupdate.init.BonesEntities;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ForgeEventFactory;


public class NecroScepterItem extends GravePacifierItem {
    public static final int MAX_MINIONS_SUMMONED=3; //FIX_VALUE

    public NecroScepterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (stack.getAllEnchantments().containsKey(BonesEnchantments.SUBORDINATE.get()) && entity.isAlive() && entity instanceof BonesBrokenSkeletons friendlySkeleton && !friendlySkeleton.isFriendly()) {
            friendlySkeleton.becomesFriendly(player.level);
            useItemStack(stack, player, hand);
            return InteractionResult.sidedSuccess(player.level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    /**
     * When the player uses the tool, the necromancer scepter summons minions.
     * It does not work when the scepter is enchanted with "Subordinate".
     *
     * @param level
     * @param player : the player which uses the tool.
     * @param hand : the hand where is the tool
     * @return InteractionResultHolder : SUCCESS / PASS
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && !stack.getAllEnchantments().containsKey(BonesEnchantments.SUBORDINATE.get())){
            RandomSource rdmSequence = level.getRandom();
            int entityCount = MAX_MINIONS_SUMMONED + (stack.getAllEnchantments().containsKey(BonesEnchantments.LEADER.get())? 2 : 0);
            for (int i=0;i<entityCount;++i){
                Minion minion = BonesEntities.MINION.get().create(level);
                if (minion!=null) {
                    BlockPos pos = player.blockPosition().offset(-1+rdmSequence.nextInt(3),0,-1+rdmSequence.nextInt(3));
                    minion.moveTo(pos,0.0F,0.0F);
                    minion.becomesFriendly(level);
                    ForgeEventFactory.onFinalizeSpawn(minion, (ServerLevel)level, level.getCurrentDifficultyAt(player.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                    ((ServerLevel)level).tryAddFreshEntityWithPassengers(minion);
                    level.gameEvent(minion, GameEvent.ENTITY_PLACE, player.blockPosition());
                }
            }
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
