package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.model.Modelzombie_pigman;
import com.jerotes.blackgoldalliance.entity.MagicSummoned.ZombiePigman.ZombiePigmanEntity;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class ZombiePigmanRenderer extends HumanoidMobRenderer<ZombiePigmanEntity, Modelzombie_pigman<ZombiePigmanEntity>> {
	private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/zombie/zombie_pigman.png");
	public ZombiePigmanRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelzombie_pigman(context.bakeLayer(Modelzombie_pigman.LAYER_LOCATION)), 0.5F);
			this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(ZombiePigmanEntity p_114482_) {
		return ZOMBIE_LOCATION;
	}
	public boolean isShaking(ZombiePigmanEntity p_113773_) {
		return super.isShaking(p_113773_) || p_113773_.isUnderWaterConverting();
	}
}