package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.PiglinItemInHandLayer;
import com.jerotes.blackgoldalliance.client.model.Modelblack_gold_butcher;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldButcherEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class BlackGoldButcherRenderer extends MobRenderer<BlackGoldButcherEntity, Modelblack_gold_butcher<BlackGoldButcherEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_butcher.png");

	public BlackGoldButcherRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelblack_gold_butcher<>(context.bakeLayer(Modelblack_gold_butcher.LAYER_LOCATION)), 0.72f);
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new PiglinItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}
	@Override
	protected void scale(BlackGoldButcherEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2F, 1.2F, 1.2F);
		super.scale(entity, poseStack, f);
	}
	@Override
	public ResourceLocation getTextureLocation(BlackGoldButcherEntity entity) {
		return LOCATION;
	}

	@Override
	protected float getFlipDegrees(BlackGoldButcherEntity entity) {
		return 0.0f;
	}

	@Override
	protected boolean isShaking(BlackGoldButcherEntity entity) {
		return super.isShaking(entity) || entity.isConverting();
	}
}
