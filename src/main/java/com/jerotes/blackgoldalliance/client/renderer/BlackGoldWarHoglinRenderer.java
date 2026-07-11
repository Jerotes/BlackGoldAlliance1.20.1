package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.BlackGoldWarHoglinArmorLayer;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.model.Modelblack_gold_war_hoglin;
import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldWarHoglinEntity;
import com.jerotes.jerotes.client.layer.TameLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class BlackGoldWarHoglinRenderer extends MobRenderer<BlackGoldWarHoglinEntity, Modelblack_gold_war_hoglin<BlackGoldWarHoglinEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/hoglin/black_gold_war_hoglin.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/hoglin/black_gold_war_hoglin_eye.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/hoglin/black_gold_war_hoglin_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/hoglin/black_gold_war_hoglin_saddle.png");
	public BlackGoldWarHoglinRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelblack_gold_war_hoglin(context.bakeLayer(Modelblack_gold_war_hoglin.LAYER_LOCATION)), 1.25f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelblack_gold_war_hoglin(context.bakeLayer(Modelblack_gold_war_hoglin.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new TameLayer(this, new Modelblack_gold_war_hoglin(context.bakeLayer(Modelblack_gold_war_hoglin.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelblack_gold_war_hoglin(context.bakeLayer(Modelblack_gold_war_hoglin.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new BlackGoldWarHoglinArmorLayer(this, context.getModelSet()));
	}

	protected boolean isShaking(BlackGoldWarHoglinEntity blackGoldWarHoglin) {
		return super.isShaking(blackGoldWarHoglin) || blackGoldWarHoglin.isConverting();
	}

	@Override
	public ResourceLocation getTextureLocation(BlackGoldWarHoglinEntity entity) {
		return LOCATION;
	}
}

