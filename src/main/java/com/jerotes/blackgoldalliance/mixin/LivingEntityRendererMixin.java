package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.entity.Interface.PortalPointChangeEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldBruiserEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldRuntEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
    @Shadow protected M model;

    public LivingEntityRendererMixin(RenderLayerParent<T, M> p_117346_) {
        super((EntityRendererProvider.Context) p_117346_);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isBaby()Z", shift = At.Shift.AFTER))
    public void render(T p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_, CallbackInfo ci) {
        if (p_115308_ instanceof BlackGoldRuntEntity) {
            this.model.young = false;
        }
    }
    @Inject(method = "getOverlayCoords", at = @At("HEAD"), cancellable = true)
    private static void getOverlayCoords(LivingEntity livingEntity, float p_115340_, CallbackInfoReturnable<Integer> cir) {
        if (livingEntity instanceof BlackGoldBruiserEntity blackGoldBruiserEntity && blackGoldBruiserEntity.isLoss()) {
            cir.setReturnValue( OverlayTexture.pack(OverlayTexture.u(p_115340_), OverlayTexture.v(livingEntity.hurtTime > 0 || (livingEntity.deathTime > 0 && livingEntity.deathTime < 15))));
        }
    }

    @Inject(method = "scale", at = @At("TAIL"), cancellable = true)
    private void scale(T t, PoseStack poseStack, float f, CallbackInfo ci) {
        if (t instanceof PortalPointChangeEntity portalPointChangeEntity && portalPointChangeEntity.getPortalPointTick() > 0) {
            poseStack.scale(1 - portalPointChangeEntity.getPortalPointTick()/ 40f, 1 - portalPointChangeEntity.getPortalPointTick()/ 40f, 1 - portalPointChangeEntity.getPortalPointTick()/ 40f);
        }
    }
    @Inject(method = "getWhiteOverlayProgress", at = @At("HEAD"), cancellable = true)
    protected void getWhiteOverlayProgress(T t, float f, CallbackInfoReturnable<Float> cir) {
        if (t instanceof PortalPointChangeEntity portalPointChangeEntity && portalPointChangeEntity.getPortalPointTick() > 0) {
            cir.setReturnValue(Mth.clamp(portalPointChangeEntity.getPortalPointTick()/ 20f, 0, 1));
            cir.cancel();
        }
    }
}