package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.layer.PiglinItemInHandLayer;
import com.jerotes.blackgoldalliance.client.model.Modelblack_gold_piglin;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BlackGoldPiglinRenderer extends MobRenderer<BlackGoldPiglinEntity, Modelblack_gold_piglin<BlackGoldPiglinEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_piglin.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_piglin_eye.png");

	public BlackGoldPiglinRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelblack_gold_piglin<>(context.bakeLayer(Modelblack_gold_piglin.LAYER_LOCATION)), 0.6f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelblack_gold_piglin(context.bakeLayer(Modelblack_gold_piglin.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new PiglinItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PIGLIN_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PIGLIN_OUTER_ARMOR)), context.getModelManager()));
	}
	@Override
	public ResourceLocation getTextureLocation(BlackGoldPiglinEntity entity) {
		return LOCATION;
	}

	@Override
	protected float getFlipDegrees(BlackGoldPiglinEntity entity) {
		return entity instanceof EliteEntity ? 0.0f : super.getFlipDegrees(entity);
	}
	@Override
	public void render(BlackGoldPiglinEntity blackGoldPiglinEntity, float f, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int n) {
		blackGoldPiglinEntity.thisTickRenderTime += 1;
		float attackAnim = blackGoldPiglinEntity.getAttackAnim();
		float useItemRemainingTicks = blackGoldPiglinEntity.getUseItemRemainingTicks();
		float fs = blackGoldPiglinEntity.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		blackGoldPiglinEntity.attackAnimProgress = Mth.lerp(smoothFactor, blackGoldPiglinEntity.attackAnimProgress, attackAnim);
		blackGoldPiglinEntity.useItemRemainingTicksProgress = Mth.lerp(smoothFactor, blackGoldPiglinEntity.useItemRemainingTicksProgress, useItemRemainingTicks);
		super.render(blackGoldPiglinEntity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected boolean isShaking(BlackGoldPiglinEntity entity) {
		return (entity.deathTime > 0 && entity.deathTime <= 20 && entity instanceof EliteEntity) || super.isShaking(entity) || entity.isConverting();
	}
}
