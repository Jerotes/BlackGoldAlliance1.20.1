package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.entity.Shoot.Arrow.BlackGoldSpectralArrowEntity;
import com.jerotes.jerotes.item.Tool.ItemToolBaseArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class BlackGoldSpectralArrow extends ItemToolBaseArrow {
	public BlackGoldSpectralArrow() {
		super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity) {
		return new BlackGoldSpectralArrowEntity(level, livingEntity, itemStack.copyWithCount(1));
	}

	@Override
	public float getBaseDamage() {
		return 3.5f;
	}
}
