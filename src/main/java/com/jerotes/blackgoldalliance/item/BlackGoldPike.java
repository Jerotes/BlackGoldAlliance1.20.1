package com.jerotes.blackgoldalliance.item;

import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBasePike;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class BlackGoldPike extends ItemToolBasePike {
	public BlackGoldPike() {
		super(new Tier() {
				  public int getUses() {
					  return 3000;
				  }

				  public float getSpeed() {
					  return 9.0f;
				  }

				  public float getAttackDamageBonus() {
					  return 15f;
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
			  }, new Properties().stacksTo(1).rarity(Rarity.COMMON), -1f, (1.0f / 1.2f) - 4f, 1.2f, 0.2f,
				0.9f, 1.1f, 4f,
				JerotesSoundEvents.SPEAR_ATTACK,
				JerotesSoundEvents.SPEAR_HIT,
				JerotesSoundEvents.SPEAR_HIT,
				3.5f, 7.5f, 3.5f, 9.5f, 0.25f, 0.85f);
	}
}
