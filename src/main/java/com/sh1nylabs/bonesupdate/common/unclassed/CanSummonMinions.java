package com.sh1nylabs.bonesupdate.common.unclassed;

/* Java interface written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.init.BonesEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

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
            for (int j = 0; j < 8; ++j) { /** 8 attempts to spawn the mob */ // FIX_VALUE
                double positionX = pos.getX() + (rdmSource.nextDouble() - 0.5) * 4 + 0.5;
                double positionY = pos.getY() + rdmSource.nextGaussian() * 2 + 0.5;
                double positionZ = pos.getZ() + (rdmSource.nextDouble() - 0.5) * 4 + 0.5;
                BlockPos blockpos = BlockPos.containing(positionX, positionY, positionZ);
                if (level.noCollision(AABB.unitCubeFromLowerCorner(new Vec3(positionX, positionY, positionZ)).inflate(0.35D)) && level.getBlockState(blockpos.below()).isValidSpawn(level, blockpos.below(), BonesEntities.MINION.get())) {
                    Minion minion = BonesEntities.MINION.get().create(level);
                    if (minion != null) {
                        minion.moveTo(positionX, positionY, positionZ, rdmSource.nextFloat(), 0.0F);
                        ForgeEventFactory.onFinalizeSpawn(minion, level, level.getCurrentDifficultyAt(blockpos), spawntype, minionData);
                        level.tryAddFreshEntityWithPassengers(minion);
                        level.gameEvent(minion, GameEvent.ENTITY_PLACE, blockpos);
                        minion.spawnAnim();
                    }
                    break;
                }
            }
        }
        this.delayNextSummon(rdmSource);
    }
}

