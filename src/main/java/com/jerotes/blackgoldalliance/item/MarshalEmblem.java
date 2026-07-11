package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.config.OtherMainConfig;
import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import com.jerotes.jerotes.util.ParticlesUse;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MarshalEmblem extends Item {
	public MarshalEmblem() {
		super(new Properties().stacksTo(1).durability(1).fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.SOUL_SAND) || itemStack2.is(Items.SOUL_SOIL) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (player instanceof ServerPlayer serverPlayer) {

			List<PiglinRaidNetherPortalEntity> list = serverPlayer.level().getEntitiesOfClass(PiglinRaidNetherPortalEntity.class, serverPlayer.getBoundingBox().inflate(64.0, 32.0, 64.0));
			list.removeIf(entity -> entity.getRaidLevel() <= 3 || entity.getBossRaid() != 0);
			PiglinRaidNetherPortalEntity piglinRaidNetherPortalEntity = null;
			for (PiglinRaidNetherPortalEntity entity : list) {
				int maxTick = (int) ((OtherMainConfig.PiglinRaidNetherPortalRoundTime * 20) + (20 * OtherMainConfig.PiglinRaidNetherPortalLevelAddRoundTime * (entity.getRaidLevel() - 1)));
				if (Mth.clamp(1 - (entity.getRaidTick() / (maxTick)), 0.0f, 1.0f) < 0.5f) continue;
				if (piglinRaidNetherPortalEntity == null || piglinRaidNetherPortalEntity.distanceTo(player) > entity.distanceTo(player))
					piglinRaidNetherPortalEntity = entity;
			}
			if (piglinRaidNetherPortalEntity != null) {
				piglinRaidNetherPortalEntity.setBossRaid(1);
				player.getCooldowns().addCooldown(itemStack.getItem(), 20 * 3);
				level.playSound(null, player.getOnPos().above().above(), SoundEvents.PIGLIN_ANGRY, SoundSource.NEUTRAL, 10.0F, 1.0F);
				if (!player.getAbilities().instabuild) {
					itemStack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(player.getUsedItemHand()));
				}
				Component component = Component.translatable("item.blackgoldalliance.marshal_emblem.desc_change").withStyle(ChatFormatting.GOLD);
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
