package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.model.Modelblack_gold_stepper;
import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldStepperEntity;
import com.jerotes.jerotes.client.layer.TameLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class BlackGoldStepperRenderer extends MobRenderer<BlackGoldStepperEntity, Modelblack_gold_stepper<BlackGoldStepperEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/stider/black_gold_stepper.png");
	private static final ResourceLocation COLD_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/stider/black_gold_stepper_cold.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/stider/black_gold_stepper_glow.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/stider/black_gold_stepper_tame.png");
	private static final ResourceLocation ARMOR_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/stider/black_gold_stepper_armor.png");
	public BlackGoldStepperRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelblack_gold_stepper(context.bakeLayer(Modelblack_gold_stepper.LAYER_LOCATION)), 1.25f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelblack_gold_stepper(context.bakeLayer(Modelblack_gold_stepper.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new TameLayer(this, new Modelblack_gold_stepper(context.bakeLayer(Modelblack_gold_stepper.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelblack_gold_stepper(context.bakeLayer(Modelblack_gold_stepper.LAYER_LOCATION)), ARMOR_LOCATION));
	}
	@Override
	public ResourceLocation getTextureLocation(BlackGoldStepperEntity p_116064_) {
		return p_116064_.isSuffocating() ? COLD_LOCATION : LOCATION;
	}

	@Override
	protected float getFlipDegrees(BlackGoldStepperEntity entity) {
		return 0.0f;
	}

	protected boolean isShaking(BlackGoldStepperEntity p_116070_) {
		return super.isShaking(p_116070_) || p_116070_.isSuffocating() || p_116070_.deathTime > 0;
	}
}

