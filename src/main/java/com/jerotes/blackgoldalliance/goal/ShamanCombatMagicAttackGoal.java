package com.jerotes.blackgoldalliance.goal;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BowItem;

import java.util.EnumSet;

public class ShamanCombatMagicAttackGoal<T extends PathfinderMob> extends Goal {
    private final T mob;
    private final double speedModifier;
    private int attackIntervalMin;
    private final float attackRadiusSqr;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public ShamanCombatMagicAttackGoal(T t, double d, int n, float f) {
        this.mob = t;
        this.speedModifier = d;
        this.attackIntervalMin = n;
        this.attackRadiusSqr = f * f;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return this.mob.getTarget() != null;
    }

    public boolean canContinueToUse() {
        return (this.canUse() || !this.mob.getNavigation().isDone()) && super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.seeTime = 0;
        this.attackTime = -1;
    }


    @Override
    public void stop() {
        super.stop();
        this.mob.setTarget(null);
        this.mob.setAggressive(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null) {
            if (!(this.mob.getUseItem().getItem() instanceof BowItem)) {
                this.mob.stopUsingItem();
            }
            double d0 = this.mob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean flag = this.mob.getSensing().hasLineOfSight(livingEntity);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
                this.seeTime = 0;
            }

            if (flag) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 20) {
                this.mob.getNavigation().stop();
                ++this.strafingTime;
            } else {
                this.mob.getNavigation().moveTo(livingEntity, this.speedModifier);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if ((double)this.mob.getRandom().nextFloat() < 0.3D) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if ((double)this.mob.getRandom().nextFloat() < 0.3D) {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (d0 > (double)(this.attackRadiusSqr * 0.75F)) {
                    this.strafingBackwards = false;
                } else if (d0 < (double)(this.attackRadiusSqr * 0.25F)) {
                    this.strafingBackwards = true;
                }

                this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                Entity entity = this.mob.getControlledVehicle();
                if (entity instanceof Mob mob) {
                    mob.lookAt(livingEntity, 30.0F, 30.0F);
                }

                this.mob.lookAt(livingEntity, 30.0F, 30.0F);
            } else {
                this.mob.getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);
            }

            ((Mob)this.mob).getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);
            if (livingEntity instanceof Mob mobs && mobs.isWithinMeleeAttackRange(this.mob) || this.mob.distanceTo(livingEntity) < 6) {
                float f1 = this.mob.getYRot();
                float f2 = this.mob.getXRot();
                float f3 = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
                float f4 = -Mth.sin(f2 * 0.017453292f);
                float f5 = Mth.cos(f1 * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
                float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
                float f7 = -0.03f;
                if (this.mob.getDeltaMovement().x <= 0.10 && this.mob.getDeltaMovement().z <= 0.10)
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(f3 *= f7 / f6, 0, f5 *= f7 / f6));
            }
        }

    }
}

