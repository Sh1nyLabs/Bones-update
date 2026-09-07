package com.sh1nylabs.bonesupdate.common.blocks;

import com.sh1nylabs.bonesupdate.common.unclassed.CanSummonMinions;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import static com.sh1nylabs.bonesupdate.common.blocks.GraveBlock.HAUNTED;

public class GraveBlockEntity extends BlockEntity implements CanSummonMinions {
    public static final int SPAWN_PROBABILITY = 700; // FIXED_VALUE
    public static final int SPAWN_COUNT = 7; // FIXED_VALUE
    private boolean readyToSpawn = false;
    private int necromancerDelay = -1;

    public GraveBlockEntity(BlockPos position, BlockState state) {super(BonesRegistry.GRAVE_BLOCK.blockEntity(), position, state);}

    public boolean readyToSummon() {return (readyToSpawn && this.getBlockState().getValue(HAUNTED));}

    @Override
    public void delayNextSummon(RandomSource rdmSource) {readyToSpawn = false;}

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        readyToSpawn=valueInput.getBooleanOr("ReadyToSpawn", false);
        necromancerDelay=valueInput.getIntOr("NecromancerDelay", -1);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putBoolean("ReadyToSpawn", readyToSpawn);
        valueOutput.putInt("NecromancerDelay", necromancerDelay);
    }

    public static void serverTick(final Level level, final BlockPos blockPos, final BlockState state, final GraveBlockEntity graveBlockEntity) {
        RandomSource rdmSequence = level.getRandom();

        if (graveBlockEntity.getBlockState().getValue(HAUNTED) && rdmSequence.nextInt(SPAWN_PROBABILITY) == 0) {
            graveBlockEntity.readyToSpawn = true;
        }

        if (graveBlockEntity.necromancerDelay >= 0) {
            graveBlockEntity.necromancerDelay--;
        }
        if (graveBlockEntity.readyToSummon() & ( graveBlockEntity.necromancerDelay == 0
                || ((level.getBrightness(LightLayer.SKY,blockPos)-level.getSkyDarken())<(5+rdmSequence.nextInt(4))))) {
            graveBlockEntity.summonMinion((ServerLevel)level, rdmSequence,
                    1+rdmSequence.nextInt(SPAWN_COUNT-1),
                    graveBlockEntity.getBlockPos(), EntitySpawnReason.SPAWNER, new Minion.MinionData(graveBlockEntity));
        }
    }

    /** Called by the Necromancer class when he tries to use a Grave. It puts a timer on the Grave
     *  to force Minions summoning after the countdown, and during this time, it executes an 'empty' "Summon" goal.
     *  Here, this goal is only used for necromancer animations.
     *
     * @param count timer to put before the grave forces its spawn.
     * @return
     */
    public boolean necromancerTriggerSummon(int count) {
        if (this.readyToSummon()) {
            this.necromancerDelay = count;
            return true;
        }
        return false;
    }
}
