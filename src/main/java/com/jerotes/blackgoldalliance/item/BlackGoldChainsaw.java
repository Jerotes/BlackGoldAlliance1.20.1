package com.jerotes.blackgoldalliance.item;

import com.jerotes.jerotes.item.Tool.*;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.util.EntityFactionFind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class BlackGoldChainsaw extends ItemToolBaseChainsaw {
	public BlackGoldChainsaw() {
		super(new Tier() {
			public int getUses() {
				return 3000;
			}

			public float getSpeed() {
				return 9.0f;
			}

			public float getAttackDamageBonus() {
				return 14f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 15;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.NETHERITE_INGOT));
			}
		}, -1, 1.0f - 4f, new Properties().rarity(Rarity.UNCOMMON));
	}

	public void afterUseAttack(ItemStack itemStack, Level level, LivingEntity self, LivingEntity hurt, List<LivingEntity> list) {
		if (!hurt.level().isClientSide) {
			hurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), self);
		}
		if (level instanceof ServerLevel serverLevel) {
			if (hurt.getMobType() != MobType.UNDEAD && !EntityFactionFind.isOoze(hurt) && !EntityFactionFind.isPlant(hurt) && !EntityFactionFind.isConstruct(hurt)) {
				for (int i = 0; i < 30f / list.size(); i++) {
					double angle = (Math.PI * 2 * i) / (30f / list.size());
					double radius = hurt.getBbWidth()/2f;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;
					serverLevel.sendParticles(JerotesParticleTypes.BLOOD.get(), hurt.getX() + offsetX, hurt.getY(0.75 + hurt.getRandom().nextFloat() * 0.1f), hurt.getZ() + offsetZ, 0, offsetX * 0.05, -0.1 - hurt.getRandom().nextFloat() * 0.1f, offsetZ * 0.05, 1.0);
				}
			}
			serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, hurt.getX(), hurt.getY(0.5), hurt.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
		}
	}
}
