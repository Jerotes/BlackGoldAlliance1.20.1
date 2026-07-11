package com.jerotes.blackgoldalliance.effect;

import com.jerotes.jerotes.effect.BaseMobEffect;
import com.jerotes.jerotes.effect.BaseMobEffectTick;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PiglinDeterrentMobEffect extends BaseMobEffect {
	public PiglinDeterrentMobEffect() {
		super(MobEffectCategory.HARMFUL, 0x6e1f16);
	}

	@Override
	public String getDescriptionId() {
		return "effect.blackgoldalliance.piglin_deterrent";
	}
}
