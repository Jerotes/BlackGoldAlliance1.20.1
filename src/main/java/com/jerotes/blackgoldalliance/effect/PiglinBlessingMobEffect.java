package com.jerotes.blackgoldalliance.effect;

import com.jerotes.blackgoldalliance.entity.Interface.ChangeAbstractPiglin;
import com.jerotes.blackgoldalliance.entity.Interface.ChangeHoglin;
import com.jerotes.jerotes.effect.BaseMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PiglinBlessingMobEffect extends BaseMobEffect {
	public PiglinBlessingMobEffect() {
		super(MobEffectCategory.BENEFICIAL, 0x167e86);
	}

	@Override
	public String getDescriptionId() {
		return "effect.blackgoldalliance.piglin_blessing";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		if (livingEntity instanceof ChangeAbstractPiglin abstractPiglin) {
			abstractPiglin.blackGoldAlliance1_20_1$piglinBlessing();
		}
		if (livingEntity instanceof ChangeHoglin hoglin) {
			hoglin.blackGoldAlliance1_20_1$piglinBlessing();
		}
	}
}