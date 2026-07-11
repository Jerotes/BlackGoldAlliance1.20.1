package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.entity.WitherSkeleton.OathWitherSkeletonEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitherSkeleton.class)
public abstract class WitherSkeletonMixin extends AbstractSkeleton {
    protected WitherSkeletonMixin(EntityType<? extends AbstractSkeleton> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }
    @Inject(method = "finalizeSpawn", at = @At("HEAD"), cancellable = true)
    private void finalizeSpawn(ServerLevelAccessor p_34178_, DifficultyInstance p_34179_, MobSpawnType p_34180_, SpawnGroupData p_34181_, CompoundTag p_34182_, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (((WitherSkeleton)(Object)this) instanceof OathWitherSkeletonEntity) {
            cir.setReturnValue(super.finalizeSpawn(p_34178_, p_34179_, p_34180_, p_34181_, p_34182_));
            cir.cancel();
        }
    }
}