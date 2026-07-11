package com.jerotes.blackgoldalliance.client.layer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.model.Modelthe_black_gold_marshal;
import com.jerotes.blackgoldalliance.entity.Boss.TheBlackGoldMarshalEntity;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TheBlackGoldMarshalClothesLayer<T extends TheBlackGoldMarshalEntity, M extends Modelthe_black_gold_marshal<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation ARMOR_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/the_black_gold_marshal_armor.png");
    private static final ResourceLocation BASE_ARMOR_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/the_black_gold_marshal_base_armor.png");
    private final M model;
    private final int type;
    public int alphaAbout;

    public TheBlackGoldMarshalClothesLayer(RenderLayerParent<T, M> renderLayerParent, M m, int type, int alphaAbout) {
        super(renderLayerParent);
        this.model = m;
        this.type = type;
        this.alphaAbout = alphaAbout;
    }
    public TheBlackGoldMarshalClothesLayer(RenderLayerParent<T, M> renderLayerParent, M m, int type) {
        this(renderLayerParent, m, type, 0);
    }

    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!EntityAndItemFind.isTrueInvisible(t)) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            String string = ChatFormatting.stripFormatting(t.getName().getString());
            ResourceLocation armorLocation = type != 1 ? ARMOR_LOCATION : BASE_ARMOR_LOCATION;
            if ("White".equals(string) && type != 1) {
                armorLocation = new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/white_armor.png");
            }
            if ("Diamond".equals(string) && type != 1) {
                armorLocation = new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/diamond_armor.png");
            }
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(armorLocation));
            if (t.deathTime > 30) {
                vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(armorLocation));
            }
            float alpha = Mth.clamp(1 - (t.deathTime - 30) / 10f, 0, 1);
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);


            float fHurt = 1;
            if (t.hurtTime > t.hurtDuration/2f) {
                fHurt = 2 - (t.hurtTime / (t.hurtDuration/2f));
            }
            else if (t.hurtTime < t.hurtDuration/2f) {
                fHurt = t.hurtTime / (t.hurtDuration/2f);
            }
            if (t.hurtTime > 0 && t.isAlive() && type != 2) {
                vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.entityTranslucent(armorLocation));
                boolean bl = t.getHealth() > t.getMaxHealth()/2;
                this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, bl ? 0.35F : 0.05F, bl ? 0.2F : 0.05F, bl ? 0.0F : 0.05F, fHurt);
            }
            if (t.getHealth() <= t.getMaxHealth()/2 && t.getSpecialHurtTick() > 0 && t.deathTime <= 30 && type != 1 && t.isAlive()) {
                float f7 = (float)t.tickCount + f3;
                this.model.stopSlash(t, f, f2, f4, f5, f6);
                vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.energySwirl(
                new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/marshal_armor_glint.png"),this.xOffset(f7) % 1.0f, 0.0f));
                this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1f);
            }
        }
    }

    private float xOffset(float f) {
        return f * 0.008f;
    }
}
