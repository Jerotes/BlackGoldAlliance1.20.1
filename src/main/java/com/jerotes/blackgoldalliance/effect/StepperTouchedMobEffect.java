package com.jerotes.blackgoldalliance.effect;

import com.jerotes.jerotes.effect.BaseMobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class StepperTouchedMobEffect extends BaseMobEffect {
	public StepperTouchedMobEffect() {
		super(MobEffectCategory.BENEFICIAL, 0xac5c2d);
	}

	@Override
	public String getDescriptionId() {
		return "effect.blackgoldalliance.stepper_touched";
	}
}
