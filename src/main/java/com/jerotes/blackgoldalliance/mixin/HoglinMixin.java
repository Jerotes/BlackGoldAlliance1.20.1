package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.entity.Interface.ChangeHoglin;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.IBlackGoldPiglin;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Hoglin.class)
public abstract class HoglinMixin extends Animal implements ChangeHoglin {
    @Shadow @Final private static EntityDataAccessor<Boolean> DATA_IMMUNE_TO_ZOMBIFICATION;
    @Shadow
    private int timeInOverworld;

    protected HoglinMixin(EntityType<? extends Animal> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }


    @Inject(method = "isConverting", at = @At("HEAD"), cancellable = true)
    public void isConverting(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(BGAMobEffects.PIGLIN_BLESSING.get())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}