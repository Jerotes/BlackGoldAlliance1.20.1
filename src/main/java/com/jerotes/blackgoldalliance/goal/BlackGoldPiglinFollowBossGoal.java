package com.jerotes.blackgoldalliance.goal;

import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.util.AttackFind;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class BlackGoldPiglinFollowBossGoal extends Goal {
    private final BlackGoldPiglinEntity follower;
    @Nullable
    private BlackGoldPiglinEntity boss;
    private final double speedModifier;
    private int timeToRecalcPath;

    public BlackGoldPiglinFollowBossGoal(BlackGoldPiglinEntity follower, double d) {
        this.follower = follower;
        this.speedModifier = d;
    }

    @Override
    public boolean canUse() {
        if (follower instanceof BossEntity) {
            return false;
        }
        if (follower.isAggressive()) {
            return false;
        }
        List<BlackGoldPiglinEntity> list = this.follower.level().getEntitiesOfClass(BlackGoldPiglinEntity.class, this.follower.getBoundingBox().inflate(64.0, 32.0, 64.0));
        list.removeIf(entity -> !(entity instanceof BossEntity));
        BlackGoldPiglinEntity bossFind = null;
        double d = Double.MAX_VALUE;
        for (BlackGoldPiglinEntity bosses : list) {
            double d2;
            if ((d2 = this.follower.distanceToSqr(bosses)) > d) continue;
            if (!AttackFind.SameFactionAvoidDamage(follower, bosses, false)) continue;
            d = d2;
            bossFind = bosses;
        }
        if (bossFind == null) {
            return false;
        }
        if (d < 64.0) {
            return false;
        }
        this.boss = bossFind;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.boss == null || !this.boss.isAlive()) {
            return false;
        }
        if (this.follower.getTarget() != null) {
            return false;
        }
        double d = this.follower.distanceToSqr(this.boss);
        return !(d < 18.0) && !(d > 1024.0);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.boss = null;
    }

    @Override
    public void tick() {
        if (this.boss != null) {
            if (--this.timeToRecalcPath > 0) {
                return;
            }
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.follower.getNavigation().moveTo(this.boss, this.speedModifier);
        }
    }
}

