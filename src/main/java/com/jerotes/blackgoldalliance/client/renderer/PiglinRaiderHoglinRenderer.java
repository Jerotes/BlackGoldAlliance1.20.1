package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.layer.PiglinRaiderHoglinArmorLayer;
import com.jerotes.blackgoldalliance.client.model.Modelpiglin_raider_hoglin;
import com.jerotes.blackgoldalliance.entity.Animal.PiglinRaiderHoglinEntity;
import com.jerotes.jerotes.client.layer.TameLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class PiglinRaiderHoglinRenderer extends MobRenderer<PiglinRaiderHoglinEntity, Modelpiglin_raider_hoglin<PiglinRaiderHoglinEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/hoglin/piglin_raider_hoglin.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/hoglin/piglin_raider_hoglin_eye.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/hoglin/piglin_raider_hoglin_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/hoglin/piglin_raider_hoglin_saddle.png");
	public PiglinRaiderHoglinRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelpiglin_raider_hoglin(context.bakeLayer(Modelpiglin_raider_hoglin.LAYER_LOCATION)), 0.7F);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelpiglin_raider_hoglin(context.bakeLayer(Modelpiglin_raider_hoglin.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new TameLayer(this, new Modelpiglin_raider_hoglin(context.bakeLayer(Modelpiglin_raider_hoglin.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelpiglin_raider_hoglin(context.bakeLayer(Modelpiglin_raider_hoglin.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new PiglinRaiderHoglinArmorLayer(this, context.getModelSet()));
	}

	protected boolean isShaking(PiglinRaiderHoglinEntity piglinRaiderHoglin) {
		return super.isShaking(piglinRaiderHoglin) || piglinRaiderHoglin.isConverting();
	}

	@Override
	public ResourceLocation getTextureLocation(PiglinRaiderHoglinEntity entity) {
		return LOCATION;
	}
}

