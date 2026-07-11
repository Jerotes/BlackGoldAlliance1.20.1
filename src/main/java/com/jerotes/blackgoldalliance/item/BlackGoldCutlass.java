package com.jerotes.blackgoldalliance.item;

import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public class BlackGoldCutlass extends ItemToolBaseSword {
	public BlackGoldCutlass() {
		super(new Tier() {
			public int getUses() {
				return 3000;
			}

			public float getSpeed() {
				return 9.0f;
			}

			public float getAttackDamageBonus() {
				return 9.5f;
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
		}, -1, 1.5f - 4f, new Properties().fireResistant().rarity(Rarity.UNCOMMON));
	}
}
