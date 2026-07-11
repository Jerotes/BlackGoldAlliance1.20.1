package com.jerotes.blackgoldalliance.client.layer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PiglinRaidNetherPortalClothesLayer<T extends PiglinRaidNetherPortalEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityModel<T> model;

    public PiglinRaidNetherPortalClothesLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!EntityAndItemFind.isTrueInvisible(t)) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(BGA.MODID, "textures/entity/piglin_raid_nether_portal/" + (t.isBlackGoldAlliance() ? "black_gold_alliance" : "piglin") + "/piglin_raid_nether_portal.png")));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}

