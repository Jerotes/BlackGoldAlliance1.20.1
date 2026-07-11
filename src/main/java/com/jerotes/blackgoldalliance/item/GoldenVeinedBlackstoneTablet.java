package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.block.NetherSiphonLinkStationEntity;
import com.jerotes.blackgoldalliance.config.OtherMainConfig;
import com.jerotes.blackgoldalliance.init.BGABlocks;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class GoldenVeinedBlackstoneTablet extends Item implements Vanishable {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final String TAG_VEINED_POS = "GoldenVeinedBlackstoneTabletPos";
	public static final String TAG_VEINED_DIMENSION = "GoldenVeinedBlackstoneTabletDimension";
	public static final String TAG_VEINED_TRACKED = "GoldenVeinedBlackstoneTabletTracked";
	public static final String TAG_VEINED_TYPE = "GoldenVeinedBlackstoneTabletType";

	public GoldenVeinedBlackstoneTablet() {
		super(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
	}

	public static boolean isTabletContacter(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.getTag();
		return compoundtag != null && (compoundtag.contains(TAG_VEINED_DIMENSION) && compoundtag.contains(TAG_VEINED_POS));
	}

	private static Optional<ResourceKey<Level>> getTabletDimension(CompoundTag compoundTag) {
		return Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, compoundTag.get(TAG_VEINED_DIMENSION)).result();
	}

	public boolean isFoil(ItemStack itemStack) {
		return isTabletContacter(itemStack) || super.isFoil(itemStack);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return super.use(level, player, hand);
	}
	public InteractionResult useOn(UseOnContext useOnContext) {
		BlockPos blockPosThis = useOnContext.getClickedPos();
		Level level = useOnContext.getLevel();
		ItemStack itemStack = useOnContext.getItemInHand();
		Player player = useOnContext.getPlayer();

		if (player == null) {
			return super.useOn(useOnContext);
		}
		//其他方块
		if (!level.getBlockState(blockPosThis).is(BGABlocks.NETHER_SIPHON_CORE.get()) &&
				!level.getBlockState(blockPosThis).is(BGABlocks.NETHER_SIPHON_LINK_STATION.get())) {
			if (!isTabletContacter(itemStack)) {
				return super.useOn(useOnContext);
			}
			ItemStack itemstack1 = new ItemStack(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get(), itemStack.getCount());
			itemStack.shrink(itemStack.getCount());
			if (!player.getInventory().add(itemstack1)) {
				player.drop(itemstack1, false);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		//如果是已绑定物品，则读取其NBT
		if (isTabletContacter(itemStack)) {
			CompoundTag compoundTag = itemStack.getTag();
			Optional<ResourceKey<Level>> optional = getTabletDimension(compoundTag);
			BlockPos blockPosLast = NbtUtils.readBlockPos(compoundTag.getCompound(TAG_VEINED_POS));

			//绑定过后未找到方块
			if (!level.getBlockState(blockPosLast).is(BGABlocks.NETHER_SIPHON_CORE.get()) &&
					!level.getBlockState(blockPosLast).is(BGABlocks.NETHER_SIPHON_LINK_STATION.get())) {
				level.playSound(null, player, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
				ItemStack itemstack1 = new ItemStack(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get(), itemStack.getCount());
				itemStack.shrink(itemStack.getCount());
				if (!player.getInventory().add(itemstack1)) {
					player.drop(itemstack1, false);
				}
				if (level.isClientSide()) {
					return super.useOn(useOnContext);
				}
				player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_not_find").withStyle(ChatFormatting.GOLD));
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
			//绑定过后不同维度
			else if (optional.isPresent() &&
					(optional.get() != level.dimension() || optional.get() != player.level().dimension())) {
				level.playSound(null, player, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
				ItemStack itemstack1 = new ItemStack(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get(), itemStack.getCount());
				itemStack.shrink(itemStack.getCount());
				if (!player.getInventory().add(itemstack1)) {
					player.drop(itemstack1, false);
				}
				if (level.isClientSide()) {
					return super.useOn(useOnContext);
				}
				player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_dimension_not_find").withStyle(ChatFormatting.GOLD));
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
			//二次连接（已绑定物品且点击了核心或连接站）
			else {
				double reach = OtherMainConfig.NetherSiphonLinkStationLinkMaxReach;

				//如果维度错误
				if (optional.isPresent() && optional.get() != level.dimension()) {
					level.playSound(null, player, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
					ItemStack itemstack1 = new ItemStack(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get(), itemStack.getCount());
					itemStack.shrink(itemStack.getCount());
					if (!player.getInventory().add(itemstack1)) {
						player.drop(itemstack1, false);
					}
					if (level.isClientSide()) {
						return super.useOn(useOnContext);
					}
					player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_dimension_not_find").withStyle(ChatFormatting.GOLD));
					return InteractionResult.sidedSuccess(level.isClientSide);
				}
				//如果维度正确
				if (optional.isPresent()) {
					//如果距离错误
					if (blockPosLast.getCenter().distanceTo(blockPosThis.getCenter()) > reach) {
						level.playSound(null, player, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
						ItemStack itemstack1 = new ItemStack(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get(), itemStack.getCount());
						itemStack.shrink(itemStack.getCount());
						if (!player.getInventory().add(itemstack1)) {
							player.drop(itemstack1, false);
						}
						if (level.isClientSide()) {
							return super.useOn(useOnContext);
						}
						player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_far").withStyle(ChatFormatting.GOLD));
						return InteractionResult.sidedSuccess(level.isClientSide);
					}
					//如果距离正确
					else {
						//如果绑定对应方块错误
						if (level.getBlockState(blockPosThis).is(BGABlocks.NETHER_SIPHON_CORE.get()) && compoundTag.getInt(TAG_VEINED_TYPE) == 1) {
							level.playSound(null, blockPosThis, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
							this.addTabletTags(level.dimension(), blockPosThis, itemStack.getOrCreateTag(), level.getBlockState(blockPosThis).is(BGABlocks.NETHER_SIPHON_CORE.get()) ? 1: 0);
							return super.useOn(useOnContext);
						}
						else if (level.getBlockState(blockPosThis).is(BGABlocks.NETHER_SIPHON_LINK_STATION.get()) && compoundTag.getInt(TAG_VEINED_TYPE) == 0) {
							level.playSound(null, blockPosThis, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
							this.addTabletTags(level.dimension(), blockPosThis, itemStack.getOrCreateTag(), level.getBlockState(blockPosThis).is(BGABlocks.NETHER_SIPHON_CORE.get()) ? 1: 0);
							return super.useOn(useOnContext);
						}
						//如果绑定对应方块正确
						//对象是不是已有绑定 如果没有进行绑定  如果有 对应取消 非对应转移

						//此次为连接站
						else if (level.getBlockState(blockPosThis).is(BGABlocks.NETHER_SIPHON_LINK_STATION.get()) && compoundTag.getInt(TAG_VEINED_TYPE) == 1) {
							BlockEntity link = level.getBlockEntity(blockPosThis);
							if (link instanceof NetherSiphonLinkStationEntity netherSiphonLinkStationEntity) {
								level.playSound(null, blockPosThis, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
								ItemStack itemstack1 = new ItemStack(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get(), itemStack.getCount());
								itemStack.shrink(itemStack.getCount());
								if (!player.getInventory().add(itemstack1)) {
									player.drop(itemstack1, false);
								}
								//未连接
								if (!netherSiphonLinkStationEntity.line) {
									netherSiphonLinkStationEntity.setLine(true, level);
									netherSiphonLinkStationEntity.lineX = blockPosLast.getX();
									netherSiphonLinkStationEntity.lineY = blockPosLast.getY();
									netherSiphonLinkStationEntity.lineZ = blockPosLast.getZ();
									netherSiphonLinkStationEntity.setChanged();
									if (level.isClientSide()) {
										return super.useOn(useOnContext);
									}
									player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_yes").withStyle(ChatFormatting.GOLD));
									return InteractionResult.sidedSuccess(level.isClientSide);
								}
								else {
									//取消
									if (blockPosLast.equals(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ))) {
										netherSiphonLinkStationEntity.setLine(false, level);
										netherSiphonLinkStationEntity.setChanged();
										if (level.isClientSide()) {
											return super.useOn(useOnContext);
										}
										player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_no").withStyle(ChatFormatting.GOLD));
										return InteractionResult.sidedSuccess(level.isClientSide);
									}
									//更改
									else {
										netherSiphonLinkStationEntity.setLine(true, level);
										netherSiphonLinkStationEntity.lineX = blockPosLast.getX();
										netherSiphonLinkStationEntity.lineY = blockPosLast.getY();
										netherSiphonLinkStationEntity.lineZ = blockPosLast.getZ();
										netherSiphonLinkStationEntity.setChanged();
										if (level.isClientSide()) {
											return super.useOn(useOnContext);
										}
										player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_yes").withStyle(ChatFormatting.GOLD));
										return InteractionResult.sidedSuccess(level.isClientSide);
									}
								}
							}
						}
						//上次为连接站
						else if (level.getBlockState(blockPosThis).is(BGABlocks.NETHER_SIPHON_CORE.get()) && compoundTag.getInt(TAG_VEINED_TYPE) == 0) {
							BlockEntity link = level.getBlockEntity(blockPosLast);
							if (link instanceof NetherSiphonLinkStationEntity netherSiphonLinkStationEntity) {

								level.playSound(null, blockPosThis, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
								ItemStack itemstack1 = new ItemStack(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get(), itemStack.getCount());
								itemStack.shrink(itemStack.getCount());
								if (!player.getInventory().add(itemstack1)) {
									player.drop(itemstack1, false);
								}
								//未连接
								if (!netherSiphonLinkStationEntity.line) {
									netherSiphonLinkStationEntity.setLine(true, level);
									netherSiphonLinkStationEntity.lineX = blockPosThis.getX();
									netherSiphonLinkStationEntity.lineY = blockPosThis.getY();
									netherSiphonLinkStationEntity.lineZ = blockPosThis.getZ();
									netherSiphonLinkStationEntity.setChanged();
									if (level.isClientSide()) {
										return super.useOn(useOnContext);
									}
									player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_yes").withStyle(ChatFormatting.GOLD));
									return InteractionResult.sidedSuccess(level.isClientSide);
								}
								else {
									//取消
									if (blockPosThis.equals(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ))) {
										netherSiphonLinkStationEntity.setLine(false, level);
										netherSiphonLinkStationEntity.setChanged();
										if (level.isClientSide()) {
											return super.useOn(useOnContext);
										}
										player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_no").withStyle(ChatFormatting.GOLD));
										return InteractionResult.sidedSuccess(level.isClientSide);
									}
									//更改
									else {
										netherSiphonLinkStationEntity.setLine(true, level);
										netherSiphonLinkStationEntity.lineX = blockPosThis.getX();
										netherSiphonLinkStationEntity.lineY = blockPosThis.getY();
										netherSiphonLinkStationEntity.lineZ = blockPosThis.getZ();
										netherSiphonLinkStationEntity.setChanged();
										if (level.isClientSide()) {
											return super.useOn(useOnContext);
										}
										player.sendSystemMessage(Component.translatable("message.blackgoldalliance.golden_veined_blackstone_tablet_yes").withStyle(ChatFormatting.GOLD));
										return InteractionResult.sidedSuccess(level.isClientSide);
									}
								}
							}
						}
						return InteractionResult.sidedSuccess(level.isClientSide);
					}
				}
			}
		}
		//未绑定初连接
		else {
			level.playSound(null, blockPosThis, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
			this.addTabletTags(level.dimension(), blockPosThis, itemStack.getOrCreateTag(), level.getBlockState(blockPosThis).is(BGABlocks.NETHER_SIPHON_CORE.get()) ? 1: 0);
			//1核心 0连接站
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	private void addTabletTags(ResourceKey<Level> resourceKey, BlockPos blockPos, CompoundTag compoundTag, int type) {
		compoundTag.put(TAG_VEINED_POS, NbtUtils.writeBlockPos(blockPos));
		Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, resourceKey).resultOrPartial(LOGGER::error).ifPresent((p_) -> {
			compoundTag.put(TAG_VEINED_DIMENSION, p_);
		});
		compoundTag.putBoolean(TAG_VEINED_TRACKED, true);
		compoundTag.putInt(TAG_VEINED_TYPE, type);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(Component.translatable(this.getDescriptionId() + ".desc_0").withStyle(ChatFormatting.GRAY));
		list.add(Component.translatable(this.getDescriptionId() + ".desc_1").withStyle(ChatFormatting.GRAY));

		CompoundTag compoundtag = itemStack.getTag();
		if (compoundtag != null && (compoundtag.contains(TAG_VEINED_TYPE) && compoundtag.getInt(TAG_VEINED_TYPE) == 1)) {
			list.add(Component.translatable(this.getDescriptionId() + ".desc_2").withStyle(ChatFormatting.GOLD));
		}
		if (compoundtag != null && (compoundtag.contains(TAG_VEINED_TYPE) && compoundtag.getInt(TAG_VEINED_TYPE) == 0)) {
			list.add(Component.translatable(this.getDescriptionId() + ".desc_3").withStyle(ChatFormatting.GOLD));
		}
		if (compoundtag != null && (compoundtag.contains(TAG_VEINED_DIMENSION))) {
			list.add(Component.translatable(this.getDescriptionId() + ".desc_4", compoundtag.getString(TAG_VEINED_DIMENSION)).withStyle(ChatFormatting.GOLD));
		}
		if (compoundtag != null && (compoundtag.contains(TAG_VEINED_POS))) {
			BlockPos blockPos = NbtUtils.readBlockPos(compoundtag.getCompound(TAG_VEINED_POS));
			list.add(Component.translatable(this.getDescriptionId() + ".desc_5", blockPos.getX(), blockPos.getY(), blockPos.getZ()).withStyle(ChatFormatting.GOLD));
		}
	}
}