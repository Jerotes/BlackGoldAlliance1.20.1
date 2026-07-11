package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.entity.Shoot.Arrow.BlackGoldSpectralArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BlackGoldSpectralArrowRenderer extends ArrowRenderer<BlackGoldSpectralArrowEntity> {
    public static final ResourceLocation ARROW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/projectiles/black_gold_spectral_arrow.png");

    public BlackGoldSpectralArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackGoldSpectralArrowEntity spectralArrow) {
        return ARROW_LOCATION;
    }
}

