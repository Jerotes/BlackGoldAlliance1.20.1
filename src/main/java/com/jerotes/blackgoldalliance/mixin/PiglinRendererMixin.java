package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinRenderer.class)
public abstract class PiglinRendererMixin extends HumanoidMobRenderer<Mob, PiglinModel<Mob>> {
    public PiglinRendererMixin(EntityRendererProvider.Context context, PiglinModel<Mob> piglinModel, float f) {
        super(context, piglinModel, f);
    }

    @Inject(method = "isShaking(Lnet/minecraft/world/entity/Mob;)Z", at = @At("HEAD"), cancellable = true)
    private void isShaking(Mob mob, CallbackInfoReturnable<Boolean> cir) {
        if (mob.hasEffect(BGAMobEffects.PIGLIN_BLESSING.get())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}