package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.entity.Interface.PortalPointChangeEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements PortalPointChangeEntity {
    @Unique
    private static final EntityDataAccessor<Integer> POWER_POINT_CHANGE_TICK = SynchedEntityData.defineId(LivingEntityMixin.class, EntityDataSerializers.INT);
    protected LivingEntityMixin(EntityType<? extends Entity> livingEntity, Level level) {
        super(livingEntity, level);
    }

    public int getPortalPointTick() {
        return this.getEntityData().get(POWER_POINT_CHANGE_TICK);
    }
    public void setPortalPointTick(int n) {
        this.getEntityData().set(POWER_POINT_CHANGE_TICK, n);
    }
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putInt("PurpleSandPotionTick", this.getPortalPointTick());
    }
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        this.setPortalPointTick(compoundTag.getInt("PurpleSandPotionTick"));
    }
    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(CallbackInfo ci) {
        this.getEntityData().define(POWER_POINT_CHANGE_TICK, 0);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    protected void tick(CallbackInfo ci) {
        if (getPortalPointTick() > 0) {
            if (!level().isClientSide()) {
                setPortalPointTick(Math.max(0, getPortalPointTick() - 1));
            }
        }
    }
}