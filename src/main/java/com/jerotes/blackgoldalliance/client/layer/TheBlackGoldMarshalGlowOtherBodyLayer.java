package com.jerotes.blackgoldalliance.client.layer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.entity.Boss.TheBlackGoldMarshalEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class TheBlackGoldMarshalGlowOtherBodyLayer<T extends TheBlackGoldMarshalEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/the_black_gold_marshal_glow.png");
    private final EntityModel<T> model;

    public TheBlackGoldMarshalGlowOtherBodyLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
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
        String string = ChatFormatting.stripFormatting(t.getName().getString());
        ResourceLocation glowLocation = GLOW_LOCATION;
        if ("White".equals(string)) {
            glowLocation = new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/white_armor_glow.png");
        }
        if ("Diamond".equals(string)) {
            glowLocation = new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/diamond_armor_glow.png");
        }
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.energySwirl(glowLocation, 0, 0));
        this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
   }
}

