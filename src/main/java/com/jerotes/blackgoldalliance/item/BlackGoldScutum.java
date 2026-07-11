package com.jerotes.blackgoldalliance.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseParryShield;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;

public class BlackGoldScutum extends ItemToolBaseParryShield {
	public BlackGoldScutum() {
		super(new Item.Properties().stacksTo(1).durability(3000 * 3).fireResistant().rarity(Rarity.UNCOMMON), 14f, 0.55f, 15, Ingredient.of(Items.NETHERITE_INGOT)
				, 40, 4, 1.2f, 6, 1.2f);
	}
}
