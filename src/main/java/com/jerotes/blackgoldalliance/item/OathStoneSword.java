package com.jerotes.blackgoldalliance.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseSword;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public class OathStoneSword extends ItemToolBaseSword {
	public OathStoneSword() {
		super(new Tier() {
			public int getUses() {
				return 350;
			}

			public float getSpeed() {
				return 6.5F;
			}

			public float getAttackDamageBonus() {
				return 7.5f;
			}

			public int getLevel() {
				return 2;
			}

			public int getEnchantmentValue() {
				return 15;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
			}
		}, -1, 1.3f - 4f, new Properties().fireResistant());
	}
}
