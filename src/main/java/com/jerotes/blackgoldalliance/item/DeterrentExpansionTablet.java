package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.block.NetherSiphonCore;
import com.jerotes.blackgoldalliance.block.NetherSiphonCoreEntity;
import com.jerotes.blackgoldalliance.init.BGABlocks;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class DeterrentExpansionTablet extends Item {
	public DeterrentExpansionTablet() {
		super(new Item.Properties().stacksTo(1).durability(16).fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.SOUL_SAND) || itemStack2.is(Items.SOUL_SOIL) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return super.use(level, player, hand);
	}
	public InteractionResult useOn(UseOnContext useOnContext) {
		BlockPos blockPos = useOnContext.getClickedPos();
		Level level = useOnContext.getLevel();
		ItemStack itemStack = useOnContext.getItemInHand();
		Player player = useOnContext.getPlayer();
		BlockState blockState = level.getBlockState(blockPos);
		BlockEntity blockEntity = level.getBlockEntity(blockPos);
		if (player == null) {
			return super.useOn(useOnContext);
		}
		if (blockState.is(BGABlocks.NETHER_SIPHON_CORE.get()) && blockEntity instanceof NetherSiphonCoreEntity netherSiphonCoreEntity) {
			if (!netherSiphonCoreEntity.alreadyRaid && !netherSiphonCoreEntity.canNotStopCooldown) {
				if (blockState.getValue(NetherSiphonCore.TYPE) == 3 || blockState.getValue(NetherSiphonCore.TYPE) == 1 || blockState.getValue(NetherSiphonCore.TYPE) == 2) {
					if (!player.level().isClientSide()) {
						int cooldownTick = (int) (NetherSiphonCoreEntity.MAX_COOLDOWN / 16f);
						netherSiphonCoreEntity.cooldownCount = NetherSiphonCoreEntity.MAX_COOLDOWN;
						netherSiphonCoreEntity.isCooldown = true;
						netherSiphonCoreEntity.effectDone = true;
						level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 3).setValue(NetherSiphonCore.OPEN, false), 3);
						netherSiphonCoreEntity.setChanged();
						Component component = Component.translatable("boss.blackgoldalliance.piglin_raid_nether_portal", player.getDisplayName()).withStyle(ChatFormatting.GOLD);
						player.sendSystemMessage(component);
						player.displayClientMessage(component, true);
						player.addEffect(new MobEffectInstance(BGAMobEffects.PIGLIN_OMEN.get(),
								(Math.min(5 * 20 * 60, cooldownTick + 20)), 0, true, true));
						player.getCooldowns().addCooldown(itemStack.getItem(), 20 * 60);
					}
					level.playSound(null, blockPos, SoundEvents.PIGLIN_ANGRY, SoundSource.NEUTRAL, 10.0F, 1.0F);
					if (!player.getAbilities().instabuild) {
						itemStack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(player.getUsedItemHand()));
					}
				}
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.useOn(useOnContext);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
	}
}
