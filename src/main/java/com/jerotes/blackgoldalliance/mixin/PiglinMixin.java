package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.IBlackGoldPiglin;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Piglin.class)
public abstract class PiglinMixin extends AbstractPiglin {
    protected PiglinMixin(EntityType<? extends AbstractPiglin> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof IBlackGoldPiglin || damageSource.getEntity() != null && damageSource.getEntity() instanceof IBlackGoldPiglin) {
            cir.setReturnValue(super.hurt(damageSource, f));
            cir.cancel();
        }
    }

    @Inject(method = "shouldDespawnInPeaceful", at = @At("HEAD"), cancellable = true)
    private void shouldDespawnInPeaceful(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(BGAMobEffects.PIGLIN_BLESSING.get())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}