package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoglinRenderer.class)
public abstract class HoglinRendererMixin extends MobRenderer<Hoglin, HoglinModel<Hoglin>> {
    public HoglinRendererMixin(EntityRendererProvider.Context context, HoglinModel<Hoglin> hoglinHoglinModel, float f) {
        super(context, hoglinHoglinModel, f);
    }

    @Inject(method = "isShaking(Lnet/minecraft/world/entity/monster/hoglin/Hoglin;)Z", at = @At("HEAD"), cancellable = true)
    private void isShaking(Hoglin mob, CallbackInfoReturnable<Boolean> cir) {
        if (mob.hasEffect(BGAMobEffects.PIGLIN_BLESSING.get())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}