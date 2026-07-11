package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.layer.PiglinItemInHandLayer;
import com.jerotes.blackgoldalliance.client.model.Modeloath_wither_skeleton;
import com.jerotes.blackgoldalliance.entity.WitherSkeleton.OathWitherSkeletonEntity;
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

public class OathWitherSkeletonRenderer extends MobRenderer<OathWitherSkeletonEntity, Modeloath_wither_skeleton<OathWitherSkeletonEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_wither_skeleton.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/wither_skeleton/oath_wither_skeleton_glow.png");

	public OathWitherSkeletonRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeloath_wither_skeleton<>(context.bakeLayer(Modeloath_wither_skeleton.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modeloath_wither_skeleton(context.bakeLayer(Modeloath_wither_skeleton.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new PiglinItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PIGLIN_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PIGLIN_OUTER_ARMOR)), context.getModelManager()));
	}
	@Override
	public ResourceLocation getTextureLocation(OathWitherSkeletonEntity entity) {
		return LOCATION;
	}

	@Override
	protected float getFlipDegrees(OathWitherSkeletonEntity entity) {
		return entity instanceof EliteEntity ? 0.0f : super.getFlipDegrees(entity);
	}
	@Override
	public void render(OathWitherSkeletonEntity blackGoldPiglinEntity, float f, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int n) {
		blackGoldPiglinEntity.thisTickRenderTime += 1;
		float attackAnim = blackGoldPiglinEntity.getAttackAnim();
		float closeAnim = blackGoldPiglinEntity.getCloseAnim();
		float useItemRemainingTicks = blackGoldPiglinEntity.getUseItemRemainingTicks();
		float fs = blackGoldPiglinEntity.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		blackGoldPiglinEntity.attackAnimProgress = Mth.lerp(smoothFactor, blackGoldPiglinEntity.attackAnimProgress, attackAnim);
		blackGoldPiglinEntity.closeAnimProgress = Mth.lerp(smoothFactor, blackGoldPiglinEntity.closeAnimProgress, closeAnim);
		blackGoldPiglinEntity.useItemRemainingTicksProgress = Mth.lerp(smoothFactor, blackGoldPiglinEntity.useItemRemainingTicksProgress, useItemRemainingTicks);
		super.render(blackGoldPiglinEntity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected void scale(OathWitherSkeletonEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2F, 1.2F, 1.2F);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected boolean isShaking(OathWitherSkeletonEntity entity) {
		return (entity.deathTime > 0 && entity.deathTime <= 20 && entity instanceof EliteEntity) || super.isShaking(entity);
	}
}
