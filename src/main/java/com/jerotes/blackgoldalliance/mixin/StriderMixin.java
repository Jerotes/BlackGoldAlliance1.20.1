package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.init.BGAEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Strider.class)
public abstract class StriderMixin extends Animal {
    protected StriderMixin(EntityType<? extends Animal> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Inject(method = "dropEquipment", at = @At("HEAD"), cancellable = true)
    public void dropEquipment(CallbackInfo ci) {
        if (this.getType() == BGAEntityType.BLACK_GOLD_STEPPER.get()) {
            super.dropEquipment();
            ci.cancel();
        }
        else if (this.getType() == BGAEntityType.BLACK_GOLD_STEPPER_SQUAD.get()) {
            super.dropEquipment();
            ci.cancel();
        }
    }
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mobInteract(Player p_33910_, InteractionHand p_33911_, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.getType() == BGAEntityType.BLACK_GOLD_STEPPER.get()) {
            cir.setReturnValue(super.mobInteract(p_33910_, p_33911_));
            cir.cancel();
        }
        else if (this.getType() == BGAEntityType.BLACK_GOLD_STEPPER_SQUAD.get()) {
            cir.setReturnValue(super.mobInteract(p_33910_, p_33911_));
            cir.cancel();
        }
    }
    @Inject(method = "finalizeSpawn", at = @At("HEAD"), cancellable = true)
    public void finalizeSpawn(ServerLevelAccessor p_33887_, DifficultyInstance p_33888_, MobSpawnType p_33889_, SpawnGroupData p_33890_, CompoundTag p_33891_, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (this.getType() == BGAEntityType.BLACK_GOLD_STEPPER.get()) {
            cir.setReturnValue(super.finalizeSpawn(p_33887_, p_33888_, p_33889_, p_33890_, p_33891_));
            cir.cancel();
        }
       else if (this.getType() == BGAEntityType.BLACK_GOLD_STEPPER_SQUAD.get()) {
            cir.setReturnValue(super.finalizeSpawn(p_33887_, p_33888_, p_33889_, p_33890_, p_33891_));
            cir.cancel();
        }
    }
}