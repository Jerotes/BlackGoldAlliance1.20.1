package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.PiglinRaidNetherPortalClothesLayer;
import com.jerotes.blackgoldalliance.client.layer.PiglinRaidNetherPortalGlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.layer.PiglinRaidNetherPortalLayer;
import com.jerotes.blackgoldalliance.client.model.Modelpiglin_raid_nether_portal;
import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PiglinRaidNetherPortalRenderer extends MobRenderer<PiglinRaidNetherPortalEntity, Modelpiglin_raid_nether_portal<PiglinRaidNetherPortalEntity>> {
	public PiglinRaidNetherPortalRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelpiglin_raid_nether_portal<>(context.bakeLayer(Modelpiglin_raid_nether_portal.LAYER_LOCATION)), 0f);
		this.addLayer(new PiglinRaidNetherPortalClothesLayer<>(this, new Modelpiglin_raid_nether_portal(context.bakeLayer(Modelpiglin_raid_nether_portal.LAYER_LOCATION))));
		this.addLayer(new PiglinRaidNetherPortalGlowOtherBodyLayer<>(this, new Modelpiglin_raid_nether_portal(context.bakeLayer(Modelpiglin_raid_nether_portal.LAYER_LOCATION))));
		this.addLayer(new PiglinRaidNetherPortalLayer<>(this, new Modelpiglin_raid_nether_portal(context.bakeLayer(Modelpiglin_raid_nether_portal.LAYER_LOCATION))));
	}

	@Override
	protected void scale(PiglinRaidNetherPortalEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.00005F, 1.00005F, 1.00005F);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(PiglinRaidNetherPortalEntity t) {
		return new ResourceLocation(BGA.MODID, "textures/entity/piglin_raid_nether_portal/" + (t.isBlackGoldAlliance() ? "black_gold_alliance" : "piglin") + "/piglin_raid_nether_portal.png");
	}

	@Override
	protected float getFlipDegrees(PiglinRaidNetherPortalEntity entity) {
		return 0.0f;
	}
}

