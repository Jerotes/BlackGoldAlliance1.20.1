package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.spell.OtherSpellList;
import com.jerotes.blackgoldalliance.spell.OtherSpellType;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.item.Interface.MagicItem;
import com.jerotes.jerotes.spell.MagicSpell;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.Main;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class BlackGoldShamanStaff extends Item implements Vanishable, MagicItem {
	public BlackGoldShamanStaff() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(128));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(ItemTags.PLANKS) || itemStack2.is(ItemTags.WARPED_STEMS) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 20) {
			return;
		}
		if (Main.getJerotesPersistentData(livingEntity).getDouble("jerotes_spell_cooldown") > 0) {
			return;
		}
		livingEntity.swing(InteractionHand.MAIN_HAND, true);
		//法术列表-诡异射线
		OtherSpellList.RayofWarped(3, livingEntity, null).spellUse();
		if (livingEntity instanceof Player player) {
			damageMagicItem(player, itemStack);
			if (!player.getAbilities().instabuild) {
				player.getCooldowns().addCooldown(this, 120);
			}
			player.awardStat(Stats.ITEM_USED.get(this));
		}
	}

	@Override
	public void damageMagicItem(LivingEntity livingEntity, ItemStack itemStack) {
		if (livingEntity instanceof Player player) {
			itemStack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(livingEntity.getUsedItemHand()));
		}
		else if (JerotesGameRules.JEROTES_MAGIC_CAN_BREAK != null && livingEntity.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MAGIC_CAN_BREAK)) {
			itemStack.hurtAndBreak(1, livingEntity, player2 -> player2.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}


	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		MagicSpell.MagicTooltip(list, OtherSpellList.RayofWarped(3, null, null), trueLevel(itemStack));
	}

	public int trueLevel(ItemStack itemStack) {
		return OtherSpellList.RayofWarped(3, null, null).getSpellLevel();
	}


	@Override
	public List<SpellTypeInterface> getMainSpellType(ItemStack itemStack) {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		spellList.add(OtherSpellType.BLACKGOLDALLIANCE_RAY_OF_WARPED);
		return spellList;
	}
	@Override
	public List<SpellTypeInterface> getAddSpellType(ItemStack itemStack) {
		return new ArrayList<>();
	}

	@Override
	public int getSpellLevel(ItemStack itemStack) {
		return 3;
	}

	@Override
	public boolean isMelee(ItemStack itemStack) {
		return false;
	}
	@Override
	public boolean isHelp(ItemStack itemStack) {
		return false;
	}
	@Override
	public float getSpellDistance(ItemStack itemStack) {
		return OtherSpellList.RayofWarped(3, null, null).getSpellDistance();
	}
}