package com.shiny_labs.bonesupdate.common.unclassed;

import com.shiny_labs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.shiny_labs.bonesupdate.init.BonesEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ForgeEventFactory;

/**
    An interface to define a common property between the Block Entity "Grave" and a specific spell goal of the Entity
    "Necromancer".
    They use the same function to spawn (or summon) minions, called "summonMinion".
 */
public interface CanSummonMinions {

    /**
    Check whether the class implementing "CanSummonMinions" can summon "Minion".
     */
    boolean canSummonMinion();

    /**
    Let the class add some characteristics to the minion to spawn, or compute some specific computations before
    the "Minion" spawn.
     */
    void applyLastSpawnConfigurations(Minion minion);

    /**
    Function to delay the next "Minion" summoning
     */
    void delayNextSummon(RandomSource rdmSource);

    /**
    Function to add "Minion" to the world. The number of minions to spawn is defined by the int "quantity".
     */
    default void summonMinion(ServerLevel level, RandomSource rdmSource, int quantity, BlockPos pos, MobSpawnType spawntype) {
        for (int i = 0; i < quantity; ++i) { //
            for (int j = 0; j < 5; ++j) { // 5 attempts to spawn the mob // FIX_VALUE
                double positionX = pos.getX() + (rdmSource.nextDouble() - 0.5) * 4 + 0.5;
                double positionY = pos.getY();
                double positionZ = pos.getZ() + (rdmSource.nextDouble() - 0.5) * 4 + 0.5;
                BlockPos blockpos = BlockPos.containing(positionX, positionY, positionZ);
                if (level.noCollision(BonesEntities.MINION.get().getAABB(positionX, positionY, positionZ)) && level.getBlockState(blockpos.below()).isValidSpawn(level, blockpos.below(), BonesEntities.MINION.get())) {
                    Minion minion = BonesEntities.MINION.get().create(level);
                    if (minion != null) {
                        minion.moveTo(positionX, positionY, positionZ, rdmSource.nextFloat(), 0.0F); // should put moveTo for a random rotation
                        applyLastSpawnConfigurations(minion);
                        ForgeEventFactory.onFinalizeSpawn(minion, level, level.getCurrentDifficultyAt(blockpos), spawntype, null, null);
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

