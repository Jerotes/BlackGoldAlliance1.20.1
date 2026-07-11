package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.client.layer.NetherSiphonCoreForceClothesLayer;
import com.jerotes.blackgoldalliance.client.model.Modelnether_siphon_core_force;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.jerotes.JerotesWarehouse;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NetherSiphonCoreForceRenderer extends MobRenderer<NetherSiphonCoreForceEntity, Modelnether_siphon_core_force<NetherSiphonCoreForceEntity>> {
	public NetherSiphonCoreForceRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelnether_siphon_core_force<>(context.bakeLayer(Modelnether_siphon_core_force.LAYER_LOCATION)), 0f);
		this.addLayer(new NetherSiphonCoreForceClothesLayer<>(this, new Modelnether_siphon_core_force(context.bakeLayer(Modelnether_siphon_core_force.LAYER_LOCATION))));
	}

	@Override
	protected void scale(NetherSiphonCoreForceEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.00005F, 1.00005F, 1.00005F);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(NetherSiphonCoreForceEntity t) {
		return new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/null.png");
	}

	@Override
	protected float getFlipDegrees(NetherSiphonCoreForceEntity entity) {
		return 0.0f;
	}
}

