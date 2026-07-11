package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.model.Modelwarped_bomb;
import com.jerotes.blackgoldalliance.entity.Other.WarpedBombEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class WarpedBombRenderer extends EntityRenderer<WarpedBombEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/projectiles/warped_bomb.png");
    private final Modelwarped_bomb<WarpedBombEntity> model;

    public WarpedBombRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new Modelwarped_bomb(context.bakeLayer(Modelwarped_bomb.LAYER_LOCATION));
    }

    @Override
    protected int getBlockLightLevel(WarpedBombEntity entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(WarpedBombEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        float f3 = entity.getAnimationProgress(f2);
        if (f3 == 0.0f) {
            return;
        }
        float fss = f3/0.6f + 0.15f;
        float fs = Math.min(f3/0.6f + 0.15f, 0.8f);
        poseStack.pushPose();
        if (fss != fs) {
            poseStack.scale(fs/0.8f + (fss - 0.8f), fs/0.8f + (fss - 0.8f), fs/0.8f + (fss - 0.8f));
        }
        poseStack.translate(0.0, 1 + 6f/16f * fs, 0.0);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0f - entity.getYRot()));
        poseStack.scale(-1.0f, -1.0f, 1.0f);
        float f5 = 0.03125f;
        this.model.setupAnim(entity, f3, 0.0f, 0.0f, entity.getYRot(), entity.getXRot());
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
        super.render(entity, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(WarpedBombEntity entity) {
        return TEXTURE_LOCATION;
    }
}

