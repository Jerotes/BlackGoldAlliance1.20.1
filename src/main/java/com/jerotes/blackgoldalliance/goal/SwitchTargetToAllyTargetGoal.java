package com.jerotes.blackgoldalliance.goal;

import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderEntity;
import com.jerotes.jerotes.util.AttackFind;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class SwitchTargetToAllyTargetGoal extends Goal {
    private final Mob mob;
    private final double searchRange;
    private LivingEntity newTarget;

    public SwitchTargetToAllyTargetGoal(Mob mob, double searchRange) {
        this.mob = mob;
        this.searchRange = searchRange;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        LivingEntity currentTarget = this.mob.getTarget();
        if (!(currentTarget instanceof NetherSiphonCoreForceEntity)) {
            return false;
        }
        if (this.mob.getLastHurtByMob() != null && this.mob.canAttack(this.mob.getLastHurtByMob())) {
            this.newTarget = this.mob.getLastHurtByMob();
            return true;
        }
        AABB searchBox = this.mob.getBoundingBox().inflate(searchRange);
        List<Mob> allies = this.mob.level().getEntitiesOfClass(Mob.class, searchBox, ally -> {
            if (ally == this.mob) return false;
            if (!ally.isAlive()) return false;
            return AttackFind.SameFactionAvoidDamage(this.mob, ally, false);
        });

        for (Mob ally : allies) {
            LivingEntity allyTarget = ally.getTarget();
            if (allyTarget != null && allyTarget.isAlive() && !(allyTarget instanceof NetherSiphonCoreForceEntity)) {
                this.newTarget = allyTarget;
                return true;
            }
        }
        for (Mob ally : allies) {
            LivingEntity allyTarget = ally.getLastHurtByMob();
            if (allyTarget != null && allyTarget.isAlive() && !(allyTarget instanceof NetherSiphonCoreForceEntity)) {
                this.newTarget = allyTarget;
                return true;
            }
        }

        return false;
    }

    @Override
    public void start() {
        if (this.newTarget != null) {
            this.mob.setTarget(this.newTarget);
            this.newTarget = null;
        }
    }

    @Override
    public void stop() {
        this.newTarget = null;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}