package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.layer.PiglinItemInHandLayer;
import com.jerotes.blackgoldalliance.client.model.Modelblack_gold_shaman;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldShamanEntity;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class BlackGoldShamanRenderer extends MobRenderer<BlackGoldShamanEntity, Modelblack_gold_shaman<BlackGoldShamanEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_shaman.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_shaman_glow.png");

	public BlackGoldShamanRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelblack_gold_shaman<>(context.bakeLayer(Modelblack_gold_shaman.LAYER_LOCATION)), 0.6f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelblack_gold_shaman(context.bakeLayer(Modelblack_gold_shaman.LAYER_LOCATION)), GLOW_LOCATION));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new PiglinItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}
	@Override
	public ResourceLocation getTextureLocation(BlackGoldShamanEntity entity) {
		return LOCATION;
	}

	@Override
	protected float getFlipDegrees(BlackGoldShamanEntity entity) {
		return 0.0f;
	}

	@Override
	protected boolean isShaking(BlackGoldShamanEntity entity) {
		return super.isShaking(entity) || entity.isConverting();
	}
}
