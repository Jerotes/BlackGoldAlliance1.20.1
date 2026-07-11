package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.IBlackGoldPiglin;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrute.class)
public abstract class PiglinBruteMixin extends AbstractPiglinMixin {

    protected PiglinBruteMixin(EntityType<? extends AbstractPiglinMixin> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof IBlackGoldPiglin || damageSource.getEntity() != null && damageSource.getEntity() instanceof IBlackGoldPiglin) {
            cir.setReturnValue(super.hurt(damageSource, f));
            cir.cancel();
        }
    }
}