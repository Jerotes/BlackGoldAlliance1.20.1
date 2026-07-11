package com.jerotes.blackgoldalliance.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseTwoHandedHammer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class BlackGoldWarhammer extends ItemToolBaseTwoHandedHammer {
	public BlackGoldWarhammer() {
		super(new Tier() {
			public int getUses() {
				return 3000;
			}

			public float getSpeed() {
				return 9.0f;
			}

			public float getAttackDamageBonus() {
				return 20f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 15;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.IRON_INGOT));
			}
		}, -1, 0.65f - 4f, new Properties().fireResistant().rarity(Rarity.UNCOMMON));
	}
}
