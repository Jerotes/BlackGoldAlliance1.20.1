package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseSpear;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class BlackGoldLance extends ItemToolBaseSpear {
	public BlackGoldLance() {
		super(new Tier() {
				  public int getUses() {
					  return 3000;
				  }

				  public float getSpeed() {
					  return 9.0f;
				  }

				  public float getAttackDamageBonus() {
					  return 5f;
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
			  },  new Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON).durability(3000),
				-1f, 0.87f - 4f, 1.15f,
				0.25f, 10, (int)(0.4f * 20.0f),
				Condition.ofAttackerSpeed((int)(4.5f * 20.0f), 10.0f),
				Condition.ofAttackerSpeed((int)(7.5f * 20.0f), 5.1f),
				Condition.ofRelativeSpeed((int)(13.75f * 20.0f), 4.6f),
				0.38f, 1.25f, JerotesSoundEvents.SPEAR_USE, JerotesSoundEvents.SPEAR_HIT, JerotesSoundEvents.SPEAR_ATTACK, JerotesSoundEvents.SPEAR_HIT,
				2.0f, 4.5f, 2.0f, 6.5f, 0.125f, 0.5f, true, false
		);
	}

	public boolean stabAttack(EquipmentSlot equipmentSlot, Entity entity, float f, boolean bl, boolean bl2, boolean bl3, LivingEntity self) {
		if (self instanceof BlackGoldPiglinEntity blackGoldPiglinEntity) {
			if (!blackGoldPiglinEntity.level().isClientSide()) {
				blackGoldPiglinEntity.setAnimTick(10);
				if (equipmentSlot != EquipmentSlot.MAINHAND) {
					blackGoldPiglinEntity.setAnimationState("spear1");
				} else {
					blackGoldPiglinEntity.setAnimationState("spear2");
				}
			}
		}
		return super.stabAttack(equipmentSlot, entity, f, bl, bl2, bl3, self);
	}

}
