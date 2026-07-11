package com.jerotes.blackgoldalliance.client.layer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PiglinRaidNetherPortalLayer<T extends PiglinRaidNetherPortalEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityModel<T> model;

    public PiglinRaidNetherPortalLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl = minecraft.shouldEntityAppearGlowing(t) && t.isInvisible();
        if (t.isInvisible() && !bl) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        int ns = t.tickCount % 30 / 2;
        ResourceLocation resourceLocation = new ResourceLocation(BGA.MODID, "textures/entity/piglin_raid_nether_portal/" + (t.isBlackGoldAlliance() ? "black_gold_alliance" : "piglin") + "/piglin_raid_nether_portal_" + ns + ".png");
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(resourceLocation));
        float fs = t.isAlive() ? (1f - ((t.getStartTick() + 30) / 40f))/2f : (0.5f - (t.deathTime / 40f));
        this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, Mth.clamp(fs, 0f, 0.5f));
    }
}

