package com.jerotes.blackgoldalliance.client.layer;

import com.jerotes.blackgoldalliance.client.model.Modelblack_gold_war_hoglin;
import com.jerotes.blackgoldalliance.client.model.Modelblack_gold_war_hoglin_armor;
import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldWarHoglinEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.*;
import com.jerotes.jerotes.item.Interface.ItemBaseGiantBeastArmor;
import com.jerotes.jerotes.item.Interface.ItemBeastArmor;
import com.jerotes.jerotes.item.Tool.*;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class BlackGoldWarHoglinArmorLayer extends RenderLayer<BlackGoldWarHoglinEntity, Modelblack_gold_war_hoglin<BlackGoldWarHoglinEntity>> {
    private final Modelblack_gold_war_hoglin<BlackGoldWarHoglinEntity> model;

    public BlackGoldWarHoglinArmorLayer(RenderLayerParent<BlackGoldWarHoglinEntity, Modelblack_gold_war_hoglin<BlackGoldWarHoglinEntity>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new Modelblack_gold_war_hoglin_armor<>(entityModelSet.bakeLayer(Modelblack_gold_war_hoglin_armor.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, BlackGoldWarHoglinEntity t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        float f7;
        float f8;
        float f9;
        ItemStack itemStack = t.getItemBySlot(EquipmentSlot.CHEST);
        if (!(itemStack.getItem() instanceof ItemBaseGiantBeastArmor itemGiantBeastArmor)) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        if (itemGiantBeastArmor instanceof DyeableLeatherItem) {
            int n2 = ((DyeableLeatherItem)itemGiantBeastArmor).getColor(itemStack);
            f9 = (float)(n2 >> 16 & 0xFF) / 255.0f;
            f7 = (float)(n2 >> 8 & 0xFF) / 255.0f;
            f8 = (float)(n2 & 0xFF) / 255.0f;
        }
        else {
            f9 = 1.0f;
            f7 = 1.0f;
            f8 = 1.0f;
        }
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(itemGiantBeastArmor.getTexture()));
        this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
        if (itemStack.getItem() instanceof ItemBeastArmor itemBeastArmor && itemBeastArmor.hasOverlay()) {
            VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(itemGiantBeastArmor.getTextureOverlay()));
            this.model.renderToBuffer(poseStack, vertexConsumer2, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}

