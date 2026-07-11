package com.jerotes.blackgoldalliance.goal;

import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class PiglinAvoidSoulFireGoal extends Goal {
    protected final PathfinderMob mob;
    protected final double speedModifier;
    protected final int searchRange;
    protected final int verticalRange;
    protected final double minDistance;

    protected BlockPos nearestRepellentPos;
    protected Path repelPath;
    protected int pathUpdateCooldown;

    public PiglinAvoidSoulFireGoal(PathfinderMob mob, double speedModifier, int searchRange, int verticalRange, double minDistance) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.searchRange = searchRange;
        this.verticalRange = verticalRange;
        this.minDistance = minDistance;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        this.nearestRepellentPos = this.findNearestRepellent(this.mob.level(), this.mob.blockPosition(), this.searchRange, this.verticalRange);
        return this.nearestRepellentPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.nearestRepellentPos == null) {
            return false;
        }
        double distanceSq = this.mob.distanceToSqr(Vec3.atCenterOf(this.nearestRepellentPos));
        return distanceSq < this.minDistance * this.minDistance;
    }

    @Override
    public void start() {
        this.updatePath();
    }

    @Override
    public void tick() {
        if (--this.pathUpdateCooldown <= 0) {
            this.updatePath();
            this.pathUpdateCooldown = 5;
        }
        if (this.repelPath != null) {
            this.mob.getNavigation().moveTo(this.repelPath, this.speedModifier);
        }
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        this.nearestRepellentPos = null;
        this.repelPath = null;
    }

    protected void updatePath() {
        if (this.nearestRepellentPos == null) {
            return;
        }
        Vec3 fromRepellent = this.mob.position().subtract(Vec3.atCenterOf(this.nearestRepellentPos)).normalize();
        Vec3 targetPos = Vec3.atCenterOf(this.nearestRepellentPos).add(fromRepellent.scale(this.minDistance));
        BlockPos targetBlock = new BlockPos((int) targetPos.x, (int) targetPos.y, (int) targetPos.z);
        this.repelPath = this.mob.getNavigation().createPath(targetBlock, 0);
        if (this.repelPath == null || !this.repelPath.canReach()) {
            Vec3 randomDir = fromRepellent.add(this.mob.getRandom().nextGaussian() * 0.5, 0, this.mob.getRandom().nextGaussian() * 0.5).normalize();
            targetPos = Vec3.atCenterOf(this.nearestRepellentPos).add(randomDir.scale(this.minDistance));
            targetBlock = new BlockPos((int) targetPos.x, (int) targetPos.y, (int) targetPos.z);
            this.repelPath = this.mob.getNavigation().createPath(targetBlock, 0);
        }
    }

    protected BlockPos findNearestRepellent(Level level, BlockPos pos, int rangeXZ, int rangeY) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        BlockPos closest = null;
        double closestDistSq = Double.MAX_VALUE;

        int startX = pos.getX() - rangeXZ;
        int startY = pos.getY() - rangeY;
        int startZ = pos.getZ() - rangeXZ;
        int endX = pos.getX() + rangeXZ;
        int endY = pos.getY() + rangeY;
        int endZ = pos.getZ() + rangeXZ;

        for (int x = startX; x <= endX; ++x) {
            for (int y = startY; y <= endY; ++y) {
                for (int z = startZ; z <= endZ; ++z) {
                    mutable.set(x, y, z);
                    if (this.isValidRepellent(level, mutable)) {
                        double distSq = mutable.distSqr(pos);
                        if (distSq < closestDistSq) {
                            closestDistSq = distSq;
                            closest = mutable.immutable();
                        }
                    }
                }
            }
        }
        return closest;
    }

    protected boolean isValidRepellent(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        boolean isRepellent = state.is(net.minecraft.tags.BlockTags.PIGLIN_REPELLENTS);
        if (isRepellent && state.is(Blocks.SOUL_CAMPFIRE)) {
            return CampfireBlock.isLitCampfire(state);
        }
        return isRepellent;
    }
}