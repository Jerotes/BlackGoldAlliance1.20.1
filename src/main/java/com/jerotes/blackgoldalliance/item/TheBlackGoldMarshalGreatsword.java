package com.jerotes.blackgoldalliance.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseTwoHandedSword;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class TheBlackGoldMarshalGreatsword extends ItemToolBaseTwoHandedSword {
	public TheBlackGoldMarshalGreatsword() {
		super(new Tier() {
			public int getUses() {
				return 4000;
			}

			public float getSpeed() {
				return 9.0f;
			}

			public float getAttackDamageBonus() {
				return 17f;
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
		}, -1, 1.1f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC));
	}

	public int getBlockReduction() {
		return 75;
	}
}
