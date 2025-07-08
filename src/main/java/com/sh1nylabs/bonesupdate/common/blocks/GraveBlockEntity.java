package com.sh1nylabs.bonesupdate.common.blocks;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.unclassed.CanSummonMinions;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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

    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        readyToSpawn=compoundTag.getBoolean("ReadyToSpawn");
        necromancerDelay=compoundTag.getInt("NecromancerDelay");
    }
    /**
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        readyToSpawn=compoundTag.getBoolean("ReadyToSpawn");
        necromancerDelay=compoundTag.getInt("NecromancerDelay");
    } */
    
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider holderProvider) {
        super.saveAdditional(compoundTag, holderProvider);
        compoundTag.putBoolean("ReadyToSpawn", readyToSpawn);
        compoundTag.putInt("NecromancerDelay", necromancerDelay);
    }

    public void blockEntityTicker(Level level, BlockPos pos, BlockState blockState) {
        RandomSource rdmSequence = level.getRandom();

        if (level.isClientSide()) {
            if (blockState.getValue(HAUNTED) && level.random.nextDouble()<0.07) {
                level.addParticle(ParticleTypes.SMOKE, pos.getX()+level.random.nextDouble(), pos.getY() + 0.2D, pos.getZ()+level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        } else {
            if (blockState.getValue(HAUNTED) && rdmSequence.nextInt(SPAWN_PROBABILITY) == 0) {
                readyToSpawn = true;
            }

            if (necromancerDelay >= 0) {
                necromancerDelay--;
            }
            if (readyToSummon() & ( necromancerDelay == 0
                    || ((level.getBrightness(LightLayer.SKY,pos)-level.getSkyDarken())<(5+rdmSequence.nextInt(4))))) {
                this.summonMinion((ServerLevel)level, rdmSequence,
                        1+rdmSequence.nextInt(SPAWN_COUNT-1),
                        getBlockPos(), MobSpawnType.SPAWNER, new Minion.MinionData(this));
            }
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
