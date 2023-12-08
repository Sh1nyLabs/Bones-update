package com.sh1nylabs.bonesupdate.common.blocks;

import com.sh1nylabs.bonesupdate.common.unclassed.CanSummonMinions;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.init.BonesBlocks;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import static com.sh1nylabs.bonesupdate.common.blocks.GraveBlock.HAUNTED;

public class GraveBlockEntity extends BlockEntity implements CanSummonMinions {
    public static final int SPAWN_PROBABILITY = 700; // FIX_VALUE
    public static final int SPAWN_COUNT = 7;
    private boolean readyToSpawn = false;
    private static final Logger LOGGER = LogUtils.getLogger();
    private int necromancerDelay = -1;

    public GraveBlockEntity(BlockPos position, BlockState state) {super(BonesBlocks.GRAVE.get(), position, state);}

    public boolean canSummonMinion() {return (readyToSpawn && this.getBlockState().getValue(HAUNTED));}
    public void applyLastSpawnConfigurations(Minion minion) {minion.setOwner(null);}
    @Override
    public void delayNextSummon(RandomSource rdmSource) {readyToSpawn = false;}

    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        readyToSpawn=compoundTag.getBoolean("ReadyToSpawn");
    }
    
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putBoolean("ReadyToSpawn", readyToSpawn);
    }

    public void blockEntityTicker(Level level, BlockPos pos, BlockState blockState) {
        RandomSource rdmSequence=level.getRandom();

        if (level.isClientSide()) {
            if (blockState.getValue(HAUNTED) && level.random.nextDouble()<0.07) {
                level.addParticle(ParticleTypes.SMOKE, pos.getX()+level.random.nextDouble(), pos.getY() + 0.2D, pos.getZ()+level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        } else {
            if (blockState.getValue(HAUNTED) && rdmSequence.nextInt(SPAWN_PROBABILITY)==0) {
                LOGGER.info("grave ready to spawn");
                readyToSpawn = true;
            }

            if (necromancerDelay>=0) {
                necromancerDelay--;
            }
            if (canSummonMinion() & ( necromancerDelay == 0
                    || ((level.getBrightness(LightLayer.SKY,pos)-level.getSkyDarken())<(5+rdmSequence.nextInt(4))))) {
                this.summonMinion((ServerLevel)level,rdmSequence,1+rdmSequence.nextInt(SPAWN_COUNT-1),getBlockPos(),MobSpawnType.SPAWNER);
            }
        }
    }

    public boolean necromancerTriggerSummon(int count) {
        if (this.canSummonMinion()) {
            this.necromancerDelay = count;
            return true;
        }
        return false;
    }
}
