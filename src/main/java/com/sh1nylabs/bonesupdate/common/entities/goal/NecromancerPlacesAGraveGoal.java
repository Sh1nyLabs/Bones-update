package com.sh1nylabs.bonesupdate.common.entities.goal;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


public class NecromancerPlacesAGraveGoal extends MoveToBlockGoal {

    public NecromancerPlacesAGraveGoal(Necromancer necromancer, double speedModif, int searchRange, int vertSearchRange) {
        super(necromancer, speedModif, searchRange, vertSearchRange);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && ((Necromancer) this.mob).graveStock > 0
                && mob.getRandom().nextInt(((Raider)mob).hasActiveRaid()? 3:10)==0;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos blockpos) {
        Necromancer necromancer=(Necromancer) this.mob;
        BlockState blockstate=level.getBlockState(blockpos.below());
        BlockState blockstate1=level.getBlockState(blockpos);
        Level entityGetter=necromancer.level();
        boolean flag=level.getBlockStates(new AABB(blockpos).inflate(4.0D,3.0D,4.0D)).filter((blockstate2)->{return blockstate2.is(BonesRegistry.GRAVE_BLOCK.block());}).toList().isEmpty();
        boolean flag1=blockpos.distSqr(necromancer.blockPosition())>1;
        return(flag && flag1 && (!blockstate.isAir()) && blockstate1.isAir() && blockstate.isCollisionShapeFullBlock(level,blockpos.below()) && entityGetter.getEntities(necromancer, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(blockpos))).isEmpty());
    }

    @Override
    public double acceptedDistance() {
        return 3.0D;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isReachedTarget()) {
            mob.getNavigation().stop();
            BlockState blockstate = BonesRegistry.GRAVE_BLOCK.block().defaultBlockState();
            mob.level().setBlock(this.blockPos,blockstate,3);
            ((Necromancer)mob).graveStock-=1;
        }
    }
}
