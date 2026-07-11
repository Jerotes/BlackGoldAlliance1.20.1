package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.entity.Other.PortalPointEntity;
import com.jerotes.jerotes.JerotesWarehouse;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PortalPointRenderer<T extends PortalPointEntity> extends EntityRenderer<T> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JerotesWarehouse.MODID, "textures/item/magic_missile.png");

    public PortalPointRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        super.render(t, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(PortalPointEntity tex) {
        return TEXTURE_LOCATION;
    }
}
