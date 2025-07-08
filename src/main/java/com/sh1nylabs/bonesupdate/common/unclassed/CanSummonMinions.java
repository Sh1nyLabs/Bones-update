package com.sh1nylabs.bonesupdate.common.unclassed;

/* Java interface written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
    An interface to define a common property between the Block Entity "Grave", the "Necromancer Scepter" item
    and a specific spell goal of the Entity "Necromancer".
    They use the same function to spawn (or summon) minions, called "summonMinion". This interface provides a
    generic code to summon Minions.
 */
public interface CanSummonMinions {

    /**
    Let the class add some characteristics to the minion to spawn, or compute some specific computations before
    the "Minion" spawn.
     */

    /**
    Function to delay the next "Minion" summoning
     */
    void delayNextSummon(RandomSource rdmSource);

    /**
    Function to add "Minion" to the world. The number of minions to spawn is defined by the int "quantity".
     */
    default void summonMinion(ServerLevel level, RandomSource rdmSource, int quantity, BlockPos pos, MobSpawnType spawntype, Minion.MinionData minionData) {
        for (int i = 0; i < quantity; ++i) {
            BlockPos blockpos = BonesUpdate.randomValidPosForSpawn(level, pos, 2, 2, 2, 0.35D, BonesRegistry.MINION.type(), 8);
            if (blockpos != null) {
                Minion minion = BonesRegistry.MINION.type().create(level);
                if (minion != null) {
                    minion.moveTo(blockpos, rdmSource.nextFloat() * 3.0F, 0.0F);
                    net.minecraftforge.event.ForgeEventFactory.onFinalizeSpawn(minion, level, level.getCurrentDifficultyAt(blockpos), spawntype, minionData);
                    level.tryAddFreshEntityWithPassengers(minion);
                    level.gameEvent(minion, GameEvent.ENTITY_PLACE, blockpos);
                    minion.spawnAnim();
                }
            }
        }
        this.delayNextSummon(rdmSource);
    }
}

