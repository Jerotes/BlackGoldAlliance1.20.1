package com.jerotes.blackgoldalliance.client.layer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.model.Modeloath_construct_spider;
import com.jerotes.blackgoldalliance.entity.Animal.OathConstructSpiderEntity;
import com.jerotes.jerotes.item.Interface.ItemBaseWarBeastArmor;
import com.jerotes.jerotes.item.ItemWarBeastArmor;
import com.jerotes.jerotes.util.Color;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;

public class OathConstructSpiderArmorLayer extends RenderLayer<OathConstructSpiderEntity, Modeloath_construct_spider<OathConstructSpiderEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_construct_spider/oath_construct_spider_armor/oath_construct_spider_armor.png");
    private final Modeloath_construct_spider<OathConstructSpiderEntity> model;

    public OathConstructSpiderArmorLayer(RenderLayerParent<OathConstructSpiderEntity, Modeloath_construct_spider<OathConstructSpiderEntity>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new Modeloath_construct_spider(entityModelSet.bakeLayer(Modeloath_construct_spider.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, OathConstructSpiderEntity oathWitherSpider, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(oathWitherSpider)) {
            return;
        }
        float f7;
        float f8;
        float f9;
        ItemStack itemStack = oathWitherSpider.getItemBySlot(EquipmentSlot.CHEST);
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(oathWitherSpider, f, f2, f3);
        this.model.setupAnim(oathWitherSpider, f, f2, f4, f5, f6);
        this.model.setupAnimArmor(oathWitherSpider, f, f2, f4, f5, f6);

        if (itemStack.getItem() instanceof ItemBaseWarBeastArmor itemBaseWarBeastArmor) {
            if (itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation(BGA.MODID, "oath_construct_spider_special_armor")))) {
                ResourceLocation LOCATIONS = new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_construct_spider/oath_construct_spider_armor/" + itemBaseWarBeastArmor.getTextureString() + ".png");
                if (itemBaseWarBeastArmor instanceof DyeableLeatherItem dyeableLeatherItem) {
                    int n2 = dyeableLeatherItem.getColor(itemStack);
                    f9 = (float)(n2 >> 16 & 0xFF) / 255.0f;
                    f7 = (float)(n2 >> 8 & 0xFF) / 255.0f;
                    f8 = (float)(n2 & 0xFF) / 255.0f;
                    VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATIONS));
                    this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
                }
                else {
                    f9 = 1.0f;
                    f7 = 1.0f;
                    f8 = 1.0f;
                    VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATIONS));
                    this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
                }
            }
            else if (itemBaseWarBeastArmor instanceof DyeableLeatherItem dyeableLeatherItem) {
                int n2 = dyeableLeatherItem.getColor(itemStack);
                f9 = (float)(n2 >> 16 & 0xFF) / 255.0f;
                f7 = (float)(n2 >> 8 & 0xFF) / 255.0f;
                f8 = (float)(n2 & 0xFF) / 255.0f;
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATION));
                this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
            }
            else if (itemBaseWarBeastArmor instanceof ItemWarBeastArmor itemWarBeastArmor) {
                int n2 = itemWarBeastArmor.getColor();
                for (int ns = 1; ns < 7; ++ns) {
                    int[] arrf = Color.colorIntToRGBInt(n2);
                    if (arrf == null)
                        return;
                    //-1 0 1 2
                    int colorLightOrDark = ns - 4;
                    colorLightOrDark = colorLightOrDark < 0 ? (int) (colorLightOrDark * 1.1f) : colorLightOrDark > 0 ? (int) (colorLightOrDark * 1.1f) : colorLightOrDark;
                    int addColor = 30 * colorLightOrDark;

                    int rAddColor = (int) (addColor * 1.1f);
                    float r = Mth.clamp((arrf[0] - rAddColor), 0, 255) / 255f;

                    int gAddColor = (int) (addColor * 0.9f);
                    float g = Mth.clamp((arrf[1] - gAddColor), 0, 255) / 255f;

                    int bAddColor = (int) (addColor * 1f);
                    float b = Mth.clamp((arrf[2] - bAddColor), 0, 255) / 255f;

                    VertexConsumer vertexConsumer1 = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_construct_spider/oath_construct_spider_armor/oath_construct_spider_armor_"+ ns +".png")));
                    this.model.renderToBuffer(poseStack, vertexConsumer1, n, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
                }
            }
            else {
                f9 = 1.0f;
                f7 = 1.0f;
                f8 = 1.0f;
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATION));
                this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
            }
        }
        else if (itemStack.getItem() instanceof HorseArmorItem horseArmorItem) {
            String path = BuiltInRegistries.ITEM.getKey(horseArmorItem).getPath();
            int n2 = 0Xffffff;
            if (path.equals("iron_horse_armor"))
                n2 = 9211020;
            if (path.equals("golden_horse_armor"))
                n2 = 16767037;
            if (path.equals("diamond_horse_armor"))
                n2 = 2740416;
            if (path.equals("copper_horse_armor"))
                n2 = 14052680;
            if (path.equals("netherite_horse_armor"))
                n2 = 3945010;
            for (int ns = 1; ns < 7; ++ns) {
                int[] arrf = Color.colorIntToRGBInt(n2);
                if (arrf == null)
                    return;
                //-1 0 1 2
                int colorLightOrDark = ns - 4;
                colorLightOrDark = colorLightOrDark < 0 ? (int) (colorLightOrDark * 1.1f) : colorLightOrDark > 0 ? (int) (colorLightOrDark * 1.1f) : colorLightOrDark;
                int addColor = 30 * colorLightOrDark;

                int rAddColor = (int) (addColor * 1.1f);
                float r = Mth.clamp((arrf[0] - rAddColor), 0, 255) / 255f;

                int gAddColor = (int) (addColor * 0.9f);
                float g = Mth.clamp((arrf[1] - gAddColor), 0, 255) / 255f;

                int bAddColor = (int) (addColor * 1f);
                float b = Mth.clamp((arrf[2] - bAddColor), 0, 255) / 255f;

                VertexConsumer vertexConsumer1 = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_construct_spider/oath_construct_spider_armor/oath_construct_spider_armor_"+ ns +".png")));
                this.model.renderToBuffer(poseStack, vertexConsumer1, n, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
            }
        }
    }
}

