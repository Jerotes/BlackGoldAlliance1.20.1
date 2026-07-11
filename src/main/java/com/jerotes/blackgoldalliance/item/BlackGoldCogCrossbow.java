package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.entity.Shoot.Arrow.BlackGoldSpectralArrowEntity;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlackGoldCogCrossbow extends ItemToolBaseCrossbow {

	public BlackGoldCogCrossbow() {
		super(new Properties().stacksTo(1).durability(3000).fireResistant().rarity(Rarity.UNCOMMON));
	}

	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		return new BlackGoldSpectralArrowEntity(livingEntity.level(), livingEntity, itemStack);
	}
	public int getEnchantmentValue() {
		return 15;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.NETHERITE_INGOT) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public float getShootingPower(ItemStack itemStack) {
		return containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 2.5F : 4.5F;
	}

	@Override
	public int getDefaultProjectileRange() {
		return 14;
	}

	@Override
	public int mobUseCooldownTick(ItemStack itemStack) {
		return 10;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return BlackGoldCogCrossbow.getChargeDuration(itemStack) + 3;
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		int n2 = this.getUseDuration(itemStack) - n;
		float f = BlackGoldCogCrossbow.getPowerForTime(n2, itemStack);
		if (f >= 1.0f && !BlackGoldCogCrossbow.isCharged(itemStack) && BlackGoldCogCrossbow.tryLoadProjectiles(livingEntity, itemStack)) {
			BlackGoldCogCrossbow.setCharged(itemStack, true);
			SoundSource soundsource = livingEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
			level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundsource, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
		}
	}

	private static float getPowerForTime(int p_40854_, ItemStack p_40855_) {
		float f = (float)p_40854_ / (float)getChargeDuration(p_40855_);
		if (f > 1.0F) {
			f = 1.0F;
		}
		return f;
	}
	private static boolean tryLoadProjectiles(LivingEntity p_40860_, ItemStack p_40861_) {
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, p_40861_);
		int j = i == 0 ? 1 : 3;
		boolean flag = p_40860_ instanceof Player && ((Player)p_40860_).getAbilities().instabuild;
		ItemStack itemstack = p_40860_.getProjectile(p_40861_);
		ItemStack itemstack1 = itemstack.copy();

		for(int k = 0; k < j; ++k) {
			if (k > 0) {
				itemstack = itemstack1.copy();
			}

			if (itemstack.isEmpty() && flag) {
				itemstack = new ItemStack(Items.ARROW);
				itemstack1 = itemstack.copy();
			}

			if (!loadProjectile(p_40860_, p_40861_, itemstack, k > 0, flag)) {
				return false;
			}
		}

		return true;
	}
	public int maxBullet() {
		return 3;
	}
	private static boolean loadProjectile(LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack2, boolean bl, boolean bl2) {
		ItemStack itemStack3;
		boolean bl3;
		if (itemStack2.isEmpty()) {
			return false;
		}
		int count = itemStack.getItem() instanceof ItemToolBaseCrossbow itemToolBaseCrossbow ? itemToolBaseCrossbow.maxBullet() : 1;
		if (livingEntity instanceof Player player && !player.getAbilities().instabuild || livingEntity instanceof UseCrossbowEntity && !MainConfig.MobUseCrossbowShrinkArrow && !itemStack2.is(Items.ARROW)) {
			count = Math.min(itemStack.getItem() instanceof ItemToolBaseCrossbow itemToolBaseCrossbow ? itemToolBaseCrossbow.maxBullet() : 1, itemStack2.getCount());
		}
		boolean bl4 = bl3 = bl2 && itemStack2.getItem() instanceof ArrowItem;
		if (!(bl3 || bl2 || bl)) {
			if (livingEntity instanceof UseCrossbowEntity && !MainConfig.MobUseCrossbowShrinkArrow) {
				int i = Math.min(1, itemStack2.getCount());
				itemStack3 = itemStack2.copyWithCount(i);
			}
			else {
				int i = Math.min(count, itemStack2.getCount());
				ItemStack itemstack = itemStack2.copyWithCount(i);
				itemStack2.shrink(i);
				itemStack3 = itemstack;
			}
			if (itemStack2.isEmpty() && livingEntity instanceof Player) {
				((Player)livingEntity).getInventory().removeItem(itemStack2);
			}
		} else {
			itemStack3 = itemStack2.copy();
		}
		BlackGoldCogCrossbow.setBullet(itemStack, count);
		BlackGoldCogCrossbow.addChargedProjectile(itemStack, itemStack3);
		return true;
	}
	private static void addChargedProjectile(ItemStack p_40929_, ItemStack p_40930_) {
		CompoundTag compoundtag = p_40929_.getOrCreateTag();
		ListTag listtag;
		if (compoundtag.contains("ChargedProjectiles", 9)) {
			listtag = compoundtag.getList("ChargedProjectiles", 10);
		} else {
			listtag = new ListTag();
		}
		CompoundTag compoundtag1 = new CompoundTag();
		p_40930_.save(compoundtag1);
		listtag.add(compoundtag1);
		compoundtag.put("ChargedProjectiles", listtag);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		if (getBullet(itemStack) != 0) {
			list.add(Component.translatable(String.valueOf(getBullet(itemStack))));
		}
	}
}

