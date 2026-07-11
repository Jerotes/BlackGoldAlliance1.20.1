package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.BGA;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

public class NetherGoldenVeinedCodex extends Item {
	public NetherGoldenVeinedCodex() {
		super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (!ModList.get().isLoaded("patchouli")) {
			level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
			if (player instanceof ServerPlayer serverPlayer) {
				if (level instanceof ServerLevel serverLevel) {
					serverPlayer.sendSystemMessage(Component.translatable("item.blackgoldalliance.nether_golden_veined_codex.desc", player.getDisplayName()).withStyle(ChatFormatting.GREEN));
//					serverLevel.getServer().getCommands().performPrefixedCommand(
//							new CommandSourceStack(
//									CommandSource.NULL, new Vec3(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ()), Vec2.ZERO, serverLevel, 4, "", Component.literal(""), serverLevel.getServer(), null).
//									withSuppressedOutput(), "tellraw @p [{\"text\":\"https://www.mcmod.cn/class/14086.html\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.mcmod.cn/class/14086.html\"}}]"
//					);
				}
			}
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		}
		else {
			ItemStack itemStacks = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation("patchouli:guide_book")));
			if (itemStacks.isEmpty()) {
				BGA.LOGGER.error("未能找到物品: patchouli:guide_book");
				return super.use(level, player, interactionHand);
			} else {
				itemStack = itemStacks;
				CompoundTag compoundtag = new CompoundTag();
				compoundtag.putString("patchouli:book", "blackgoldalliance:nether_golden_veined_codex");
				itemStack.setTag(compoundtag);
			}
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		}
	}
}
