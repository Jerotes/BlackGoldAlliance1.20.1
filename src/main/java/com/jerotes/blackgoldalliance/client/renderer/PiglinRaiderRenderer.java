package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.layer.PiglinItemInHandLayer;
import com.jerotes.blackgoldalliance.client.model.Modelpiglin_raider;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderBruteEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderHunterEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderWarriorEntity;
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

public class PiglinRaiderRenderer extends MobRenderer<PiglinRaiderEntity, Modelpiglin_raider<PiglinRaiderEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/piglin_raider/piglin_raider.png");
	private static final ResourceLocation WARRIOR_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/piglin_raider/piglin_raider_warrior.png");
	private static final ResourceLocation HUNTER_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/piglin_raider/piglin_raider_hunter.png");
	private static final ResourceLocation BRUTE_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/piglin_raider/piglin_raider_brute.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/piglin_raider/piglin_raider_eye.png");

	public PiglinRaiderRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelpiglin_raider<>(context.bakeLayer(Modelpiglin_raider.LAYER_LOCATION)), 0.6f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelpiglin_raider(context.bakeLayer(Modelpiglin_raider.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new PiglinItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PIGLIN_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PIGLIN_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(PiglinRaiderEntity entity) {
		if (entity instanceof PiglinRaiderWarriorEntity)
			return WARRIOR_LOCATION;
		if (entity instanceof PiglinRaiderHunterEntity)
			return HUNTER_LOCATION;
		if (entity instanceof PiglinRaiderBruteEntity)
			return BRUTE_LOCATION;
		return LOCATION;
	}

	@Override
	protected float getFlipDegrees(PiglinRaiderEntity entity) {
		return entity instanceof EliteEntity ? 0.0f : super.getFlipDegrees(entity);
	}
	@Override
	public void render(PiglinRaiderEntity piglinRaiderEntity, float f, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int n) {
		piglinRaiderEntity.thisTickRenderTime += 1;
		float attackAnim = piglinRaiderEntity.getAttackAnim();
		float useItemRemainingTicks = piglinRaiderEntity.getUseItemRemainingTicks();
		float fs = piglinRaiderEntity.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		piglinRaiderEntity.attackAnimProgress = Mth.lerp(smoothFactor, piglinRaiderEntity.attackAnimProgress, attackAnim);
		piglinRaiderEntity.useItemRemainingTicksProgress = Mth.lerp(smoothFactor, piglinRaiderEntity.useItemRemainingTicksProgress, useItemRemainingTicks);
		super.render(piglinRaiderEntity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected boolean isShaking(PiglinRaiderEntity entity) {
		return (entity.deathTime > 0 && entity.deathTime <= 20 && entity instanceof EliteEntity) || super.isShaking(entity) || entity.isConverting();
	}
}
