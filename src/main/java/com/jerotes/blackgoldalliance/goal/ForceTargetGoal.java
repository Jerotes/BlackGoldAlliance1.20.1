package com.jerotes.blackgoldalliance.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;

public abstract class ForceTargetGoal extends Goal {
   private static final int EMPTY_REACH_CACHE = 0;
   private static final int CAN_REACH_CACHE = 1;
   private static final int CANT_REACH_CACHE = 2;
   protected final Mob mob;
   private int reachCache;
   private int reachCacheTime;
   private int unseenTicks;
   @Nullable
   protected LivingEntity targetMob;
   protected int unseenMemoryTicks = 60;

   public ForceTargetGoal(Mob p_26143_) {
      this.mob = p_26143_;
   }

   public boolean canContinueToUse() {
      LivingEntity livingentity = this.mob.getTarget();
      if (livingentity == null) {
         livingentity = this.targetMob;
      }

      if (livingentity == null) {
         return false;
      } else if (!this.mob.canAttack(livingentity)) {
         return false;
      } else {
         Team team = this.mob.getTeam();
         Team team1 = livingentity.getTeam();
         if (team != null && team1 == team) {
            return false;
         } else {
            double d0 = this.getFollowDistance();
            if (this.mob.distanceToSqr(livingentity) > d0 * d0) {
               return false;
            } else {
               this.mob.setTarget(livingentity);
               return true;
            }
         }
      }
   }

   protected double getFollowDistance() {
      return this.mob.getAttributeValue(Attributes.FOLLOW_RANGE);
   }

   public void start() {
      this.reachCache = 0;
      this.reachCacheTime = 0;
      this.unseenTicks = 0;
   }

   public void stop() {
      this.mob.setTarget((LivingEntity)null);
      this.targetMob = null;
   }

   protected boolean canAttack(@Nullable LivingEntity p_26151_, TargetingConditions p_26152_) {
      if (p_26151_ == null) {
         return false;
      } else if (!p_26152_.test(this.mob, p_26151_)) {
         return false;
      } else if (!this.mob.isWithinRestriction(p_26151_.blockPosition())) {
         return false;
      } else {
         return true;
      }
   }

   private boolean canReach(LivingEntity p_26149_) {
      this.reachCacheTime = reducedTickDelay(10 + this.mob.getRandom().nextInt(5));
      Path path = this.mob.getNavigation().createPath(p_26149_, 0);
      if (path == null) {
         return false;
      } else {
         Node node = path.getEndNode();
         if (node == null) {
            return false;
         } else {
            int i = node.x - p_26149_.getBlockX();
            int j = node.z - p_26149_.getBlockZ();
            return (double)(i * i + j * j) <= 2.25D;
         }
      }
   }

   public ForceTargetGoal setUnseenMemoryTicks(int p_26147_) {
      this.unseenMemoryTicks = p_26147_;
      return this;
   }
}