package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.layer.RuntItemInHandLayer;
import com.jerotes.blackgoldalliance.client.model.Modelblack_gold_runt;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldRuntEntity;
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

public class BlackGoldRuntRenderer extends MobRenderer<BlackGoldRuntEntity, Modelblack_gold_runt<BlackGoldRuntEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_runt.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_runt_eye.png");

	public BlackGoldRuntRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelblack_gold_runt<>(context.bakeLayer(Modelblack_gold_runt.LAYER_LOCATION)), 0.8f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelblack_gold_runt(context.bakeLayer(Modelblack_gold_runt.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new RuntItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}
	@Override
	public void render(BlackGoldRuntEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		entity.thisTickRenderTime += 1;
		float attackAnim = entity.getAttackAnim();
		float fs = entity.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		entity.attackAnimProgress = Mth.lerp(smoothFactor, entity.attackAnimProgress, attackAnim);
		super.render(entity, f, f2, poseStack, multiBufferSource, n);
	}
	@Override
	public ResourceLocation getTextureLocation(BlackGoldRuntEntity entity) {
		return LOCATION;
	}

	@Override
	protected float getFlipDegrees(BlackGoldRuntEntity entity) {
		return entity instanceof EliteEntity ? 0.0f : super.getFlipDegrees(entity);
	}

	@Override
	protected boolean isShaking(BlackGoldRuntEntity entity) {
		return (entity.deathTime > 0 && entity.deathTime <= 20 && entity instanceof EliteEntity) || super.isShaking(entity) || entity.isConverting();
	}
}
