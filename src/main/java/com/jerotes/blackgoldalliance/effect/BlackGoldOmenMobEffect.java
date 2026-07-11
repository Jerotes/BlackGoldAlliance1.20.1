package com.jerotes.blackgoldalliance.effect;

import com.jerotes.jerotes.effect.BaseMobEffectAllTick;
import com.jerotes.jerotes.util.Main;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BlackGoldOmenMobEffect extends BaseMobEffectAllTick {
	public BlackGoldOmenMobEffect() {
		super(MobEffectCategory.HARMFUL, 0xff6b42);
	}

	@Override
	public String getDescriptionId() {
		return "effect.blackgoldalliance.black_gold_omen";
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int n) {
		super.applyEffectTick(livingEntity, n);
		Main.getJerotesPersistentData(livingEntity).putDouble("blackgoldalliance_piglin_omen", 10);
		Main.getJerotesPersistentData(livingEntity).putDouble("blackgoldalliance_piglin_omen_level", Math.max(n, Main.getJerotesPersistentData(livingEntity).getDouble("blackgoldalliance_piglin_omen_level")));
	}
}
