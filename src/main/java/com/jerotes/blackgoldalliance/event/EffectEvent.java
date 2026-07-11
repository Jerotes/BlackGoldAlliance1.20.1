package com.jerotes.blackgoldalliance.event;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import com.jerotes.jerotes.util.EntityFactionFind;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = BGA.MODID)
public class EffectEvent {
	@SubscribeEvent
	public static void PiglinDeterrent(LivingHurtEvent event) {
		DamageSource damagesource = event.getSource();
		LivingEntity entity = event.getEntity();
		if (damagesource == null || entity == null)
			return;
		if (event.getAmount() > 1000000)
			return;
		if (!entity.level().isClientSide()) {
			//受伤
			if (entity.hasEffect(BGAMobEffects.PIGLIN_DETERRENT.get()) &&
					(EntityFactionFind.isPiglin(entity) ||
							entity instanceof HoglinBase ||
							entity instanceof Strider ||
							entity instanceof ZombifiedPiglin)) {
				int effectLevel = Objects.requireNonNull(entity.getEffect(BGAMobEffects.PIGLIN_DETERRENT.get())).getAmplifier() + 1;
				float newAmount = event.getAmount() * (1 + effectLevel);
				event.setAmount(newAmount);
			}
			//攻击
			//受伤
			if (damagesource.getEntity() instanceof LivingEntity attacker &&
					attacker.hasEffect(BGAMobEffects.PIGLIN_DETERRENT.get()) &&
					(EntityFactionFind.isPiglin(attacker) || attacker instanceof HoglinBase || attacker instanceof Strider || attacker instanceof ZombifiedPiglin)) {
				int effectLevel = Objects.requireNonNull(attacker.getEffect(BGAMobEffects.PIGLIN_DETERRENT.get())).getAmplifier() + 1;
				float newAmount = event.getAmount() / (1 + effectLevel);
				event.setAmount(newAmount);
			}
		}
	}
}

