package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.block.NetherSiphonCoreEntity;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import com.jerotes.jerotes.util.ParticlesUse;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class DeterrentIntensificationTablet extends Item {
	public DeterrentIntensificationTablet() {
		super(new Properties().stacksTo(1).durability(16).fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.SOUL_SAND) || itemStack2.is(Items.SOUL_SOIL) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (player instanceof ServerPlayer serverPlayer) {
			if (player.hasEffect(BGAMobEffects.PIGLIN_OMEN.get()) || player.hasEffect(BGAMobEffects.BLACK_GOLD_OMEN.get())) {
				int effectLevel = Math.max(
						player.getEffect(BGAMobEffects.PIGLIN_OMEN.get()) != null && player.hasEffect(BGAMobEffects.PIGLIN_OMEN.get()) ? Objects.requireNonNull(player.getEffect(BGAMobEffects.PIGLIN_OMEN.get())).getAmplifier() : 0,
						player.getEffect(BGAMobEffects.BLACK_GOLD_OMEN.get()) != null && player.hasEffect(BGAMobEffects.BLACK_GOLD_OMEN.get()) ? Objects.requireNonNull(player.getEffect(BGAMobEffects.BLACK_GOLD_OMEN.get())).getAmplifier() : 0
				) + 1;
				int time = Math.max(
						player.getEffect(BGAMobEffects.PIGLIN_OMEN.get()) != null && player.hasEffect(BGAMobEffects.PIGLIN_OMEN.get()) ? Objects.requireNonNull(player.getEffect(BGAMobEffects.PIGLIN_OMEN.get())).getDuration() : 0,
						player.getEffect(BGAMobEffects.BLACK_GOLD_OMEN.get()) != null && player.hasEffect(BGAMobEffects.BLACK_GOLD_OMEN.get()) ? Objects.requireNonNull(player.getEffect(BGAMobEffects.BLACK_GOLD_OMEN.get())).getDuration() : 0
				);
				//1→2 2→3
				if (effectLevel <= 2) {
					effectLevel += 1;
				}
				else if (effectLevel > NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(player)) {
					effectLevel = 1;
				}
				else {
					effectLevel += 1;
				}
				player.addEffect(new MobEffectInstance(effectLevel >= 3 ? BGAMobEffects.BLACK_GOLD_OMEN.get() : BGAMobEffects.PIGLIN_OMEN.get(),
						time, effectLevel - 1, true, true));
				player.removeEffect(effectLevel < 3 ? BGAMobEffects.BLACK_GOLD_OMEN.get() : BGAMobEffects.PIGLIN_OMEN.get());
				player.getCooldowns().addCooldown(itemStack.getItem(), 20 * 3);
				level.playSound(null, player.getOnPos().above().above(), SoundEvents.PIGLIN_ANGRY, SoundSource.NEUTRAL, 10.0F, 1.0F);
				if (!player.getAbilities().instabuild) {
					itemStack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(player.getUsedItemHand()));
				}
				Component component = Component.translatable("message.blackgoldalliance.change_piglin_omen_level", effectLevel).withStyle(ChatFormatting.GOLD);
				serverPlayer.sendSystemMessage(component);
				serverPlayer.displayClientMessage(component, true);
				ParticlesUse.sendBallParticles(player, ParticleTypes.SOUL, true, 3.0f, 0.25f);
				ParticlesUse.sendBallParticles(player, ParticleTypes.SOUL, true, 3.0f, 0.25f);
				ParticlesUse.sendBallParticles(player, ParticleTypes.SOUL, true, 3.0f, 0.25f);
				ParticlesUse.sendBallParticles(player, ParticleTypes.LAVA, true, 3.0f, 0.25f);
				return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
			}
		}

		return super.use(level, player, hand);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
	}
}
