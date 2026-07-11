package com.jerotes.blackgoldalliance.client.layer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.model.Modelthe_black_gold_marshal;
import com.jerotes.blackgoldalliance.entity.Boss.TheBlackGoldMarshalEntity;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class TheBlackGoldMarshalSlashLayer<T extends TheBlackGoldMarshalEntity, M extends Modelthe_black_gold_marshal<T>> extends RenderLayer<T, M> {
    private final Modelthe_black_gold_marshal<T> model;

    public TheBlackGoldMarshalSlashLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
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
        if (t.getAttackTick() <= 0) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        boolean start = false;
        ResourceLocation resourceLocation;
        int ticks = 0;
        //连招
        if (t.getAttackTick() > 0 && t.getSpecialAttackUse() != 0) {
            //招式一
            if (t.getSpecialAttackUse() == 1) {
                if (t.getAttackTick() <= 59 && t.getAttackTick() >= 52) {
                    ticks = (7 + (52 - t.getAttackTick()));
                    start = true;
                }
                else if (t.getAttackTick() <= 38 && t.getAttackTick() >= 31) {
                    ticks = (7 + (31 - t.getAttackTick()));
                    start = true;
                }
                else if (t.getAttackTick() <= 17 && t.getAttackTick() >= 10) {
                    ticks = 3;
                    start = true;
                }
            }
            //招式二
            else if (t.getSpecialAttackUse() == 2) {
                if (t.getAttackTick() <= 58 && t.getAttackTick() >= 51) {
                    ticks = (7 + (51 - t.getAttackTick()));
                    start = true;
                }
                else if (t.getAttackTick() <= 38 && t.getAttackTick() >= 31) {
                    ticks = (7 + (31 - t.getAttackTick()));
                    start = true;
                }
                else if (t.getAttackTick() <= 19 && t.getAttackTick() >= 12) {
                    ticks = 3;
                    start = true;
                }
            }
            //绞剑式
            else if (t.getSpecialAttackUse() == 3) {
                if (t.getAttackTick() <= 56 && t.getAttackTick() >= 49) {
                    ticks = (7 + (49 - t.getAttackTick()));
                    start = true;
                }
                else if (t.getAttackTick() <= 42 && t.getAttackTick() >= 35) {
                    ticks = (7 + (35 - t.getAttackTick()));
                    start = true;
                }
                else if (t.getAttackTick() <= 26 && t.getAttackTick() >= 19) {
                    ticks = (7 + (19 - t.getAttackTick()));
                    start = true;
                }
            }
        }
        //常规
        else {
            int minTick = 12;
            int maxTick = 19;
            if (t.getAttackUse() == 2 || t.getAttackUse() == 3 || t.getAttackUse() == 4) {
                minTick = 11;
                maxTick = 18;
            }
            ticks = (7 + (minTick - t.getAttackTick()));
            if (t.getAttackUse() == 5) {
                ticks = 3;
            }
            if (t.getAttackTick() <= maxTick && t.getAttackTick() >= minTick) {
                start = true;
            }
        }
        if (!start) {
            return;
        }
        resourceLocation = new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/the_black_gold_marshal_slash_" + ticks + ".png");
        String string = ChatFormatting.stripFormatting(t.getName().getString());
        if ("White".equals(string) || "Diamond".equals(string)) {
            resourceLocation = new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/white_slash_" + ticks + ".png");
        }
        VertexConsumer vertexConsumerSlash = multiBufferSource.getBuffer(JerotesRenderType.entityTranslucent(resourceLocation));
        this.model.renderToBuffer(poseStack, vertexConsumerSlash, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        vertexConsumerSlash = multiBufferSource.getBuffer(JerotesRenderType.glowDoubleSidedTranslucent(resourceLocation));
        this.model.renderToBuffer(poseStack, vertexConsumerSlash, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}