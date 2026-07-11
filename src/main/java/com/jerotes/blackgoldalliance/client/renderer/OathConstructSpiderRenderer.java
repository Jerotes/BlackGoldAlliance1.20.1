package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.layer.OathConstructSpiderArmorLayer;
import com.jerotes.blackgoldalliance.client.model.Modeloath_construct_spider;
import com.jerotes.blackgoldalliance.entity.Animal.OathConstructSpiderEntity;
import com.jerotes.jerotes.client.layer.TameLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class OathConstructSpiderRenderer extends MobRenderer<OathConstructSpiderEntity, Modeloath_construct_spider<OathConstructSpiderEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_construct_spider/oath_construct_spider.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_construct_spider/oath_construct_spider_glow.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_construct_spider/oath_construct_spider_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_construct_spider/oath_construct_spider_saddle.png");
	public OathConstructSpiderRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeloath_construct_spider(context.bakeLayer(Modeloath_construct_spider.LAYER_LOCATION)), 1.25f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modeloath_construct_spider(context.bakeLayer(Modeloath_construct_spider.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new TameLayer(this, new Modeloath_construct_spider(context.bakeLayer(Modeloath_construct_spider.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modeloath_construct_spider(context.bakeLayer(Modeloath_construct_spider.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new OathConstructSpiderArmorLayer(this, context.getModelSet()));
	}

	@Override
	public void render(OathConstructSpiderEntity oathConstructSpiderEntity, float f, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int n) {
		oathConstructSpiderEntity.thisTickRenderTime += 1;
		float attackAnim = oathConstructSpiderEntity.getClimbAnim();
		float fs = oathConstructSpiderEntity.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		oathConstructSpiderEntity.climbAnimProgress = Mth.lerp(smoothFactor, oathConstructSpiderEntity.climbAnimProgress, attackAnim);
		super.render(oathConstructSpiderEntity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected void scale(OathConstructSpiderEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2F, 1.2F, 1.2F);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(OathConstructSpiderEntity entity) {
		return 180f;
	}
	@Override
	public ResourceLocation getTextureLocation(OathConstructSpiderEntity oathConstructSpiderEntity) {
		return LOCATION;
	}
}

