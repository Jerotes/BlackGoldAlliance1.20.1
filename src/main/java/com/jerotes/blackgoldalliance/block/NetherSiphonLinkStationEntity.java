package com.jerotes.blackgoldalliance.block;

import com.jerotes.blackgoldalliance.config.OtherMainConfig;
import com.jerotes.blackgoldalliance.init.BGABlockEntityType;
import com.jerotes.blackgoldalliance.init.BGABlocks;
import com.jerotes.blackgoldalliance.init.BGAParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class NetherSiphonLinkStationEntity extends RandomizableContainerBlockEntity {
	private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);

	public NetherSiphonLinkStationEntity(BlockPos blockPos, BlockState blockState) {
		super(BGABlockEntityType.NETHER_SIPHON_LINK_STATION.get(), blockPos, blockState);
	}

	public int tickCount;
	public int stationCount = 1;
	public boolean line;
	public int lineX;
	public int lineY;
	public int lineZ;
	public int mainLevel;
	protected void saveAdditional(CompoundTag compoundTag) {
		super.saveAdditional(compoundTag);
		if (!this.trySaveLootTable(compoundTag)) {
			ContainerHelper.saveAllItems(compoundTag, this.items);
		}
		compoundTag.putInt("StationCount", this.stationCount);
		compoundTag.putBoolean("Line", this.line);
		compoundTag.putInt("lineX", this.lineX);
		compoundTag.putInt("lineY", this.lineY);
		compoundTag.putInt("lineZ", this.lineZ);
		compoundTag.putInt("MainLevel", this.mainLevel);
	}
	public void load(CompoundTag compoundTag) {
		super.load(compoundTag);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(compoundTag)) {
			ContainerHelper.loadAllItems(compoundTag, this.items);
		}
		this.stationCount = compoundTag.getInt("StationCount");
		this.line = compoundTag.getBoolean("Line");
		this.lineX = compoundTag.getInt("lineX");
		this.lineY = compoundTag.getInt("lineY");
		this.lineZ = compoundTag.getInt("lineZ");
		this.mainLevel = compoundTag.getInt("MainLevel");
	}
	public void setLine(boolean bl, Level level) {
		line = bl;
		level.setBlock(this.getBlockPos(), BGABlocks.NETHER_SIPHON_LINK_STATION.get().defaultBlockState().setValue(NetherSiphonCore.TYPE, 0), bl ? 2 : 0);
		AtomicInteger n = new AtomicInteger();
		int radius = (int) OtherMainConfig.NetherSiphonLinkStationMutuallyInfluenceReach;
		BlockPos.betweenClosed(this.getBlockPos().offset(-radius, -radius, -radius),
				this.getBlockPos().offset(radius, radius, radius)).forEach(pos -> {
			if (level.getBlockState(pos).is(BGABlocks.NETHER_SIPHON_LINK_STATION.get())) {
				n.addAndGet(1);
			}
		});
		this.stationCount = n.get();
		this.setChanged();
	}

	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	public CompoundTag getUpdateTag() {
		return this.saveWithFullMetadata();
	}
	@Override
	public int getContainerSize() {
		return 9;
	}
	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}
	@Override
	protected void setItems(NonNullList<ItemStack> nonNullList) {
		this.items = nonNullList;
	}
	@Override
	public Component getDefaultName() {
		return Component.translatable("block.blackgoldalliance.nether_siphon_core.desc");
	}
	@Override
	public int getMaxStackSize() {
		return 64;
	}
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return ChestMenu.threeRows(id, inventory);
	}
	@Override
	public void startOpen(Player player) {
	}
	@Override
	public void stopOpen(Player player) {
	}
	public void recheckOpen() {
	}
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items)
			if (!itemstack.isEmpty())
				return false;
		return true;
	}
	public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, NetherSiphonLinkStationEntity netherSiphonLinkStationEntity) {
		if (!level.getBlockState(blockPos).is(BGABlocks.NETHER_SIPHON_LINK_STATION.get())) {
			return;
		}
		++netherSiphonLinkStationEntity.tickCount;
		long i = level.getGameTime();
		if (i % 20L == 0L && netherSiphonLinkStationEntity.tickCount > 10) {

			if (netherSiphonLinkStationEntity.line && !level.getBlockState(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ)).is(BGABlocks.NETHER_SIPHON_CORE.get())) {
				netherSiphonLinkStationEntity.line = false;
				netherSiphonLinkStationEntity.setChanged();
			}

			if (level.getBlockState(blockPos).getValue(NetherSiphonLinkStation.TYPE) == 1) {
				level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 0), 3);
			}
			if (netherSiphonLinkStationEntity.line) {
				//未连接变成连接
				if (level.getBlockState(blockPos).getValue(NetherSiphonLinkStation.TYPE) == 0) {
					level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 2), 3);
				}
				//非冷却变成冷却
				else if (level.getBlockEntity(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ)) instanceof NetherSiphonCoreEntity netherSiphonCoreEntity && netherSiphonCoreEntity.isCooldown) {
					level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 3), 3);
				}
				//冷却变成非冷却
				else if (level.getBlockEntity(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ)) instanceof NetherSiphonCoreEntity netherSiphonCoreEntity && !netherSiphonCoreEntity.isCooldown) {
					level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 2), 3);
				}
			}
			if (!netherSiphonLinkStationEntity.line) {
				level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 0), 3);
			}

		}
		if (i % 320L == 0L) {
			AtomicInteger n = new AtomicInteger();
			int radius = (int) OtherMainConfig.NetherSiphonLinkStationMutuallyInfluenceReach;
			BlockPos.betweenClosed(blockPos.offset(-radius, -radius, -radius),
					blockPos.offset(radius, radius, radius)).forEach(pos -> {
				if (level.getBlockState(pos).is(BGABlocks.NETHER_SIPHON_LINK_STATION.get())) {
					n.addAndGet(1);
				}
			});
			netherSiphonLinkStationEntity.stationCount = n.get();
		}
	}

	public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, NetherSiphonLinkStationEntity netherSiphonLinkStationEntity) {
		++netherSiphonLinkStationEntity.tickCount;
		long i = level.getGameTime();
		if (i % 20L == 0L && netherSiphonLinkStationEntity.tickCount > 10) {

			if (netherSiphonLinkStationEntity.line &&
					(!level.getBlockState(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ)).is(BGABlocks.NETHER_SIPHON_CORE.get()) ||
							level.getBlockState(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ)).getValue(NetherSiphonCore.TYPE) != 2 && level.getBlockState(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ)).getValue(NetherSiphonCore.TYPE) != 3)) {
				netherSiphonLinkStationEntity.line = false;
				netherSiphonLinkStationEntity.setChanged();
			}

			// 去除type=1的可能性（假设type=1是中间状态，直接归零）
			if (level.getBlockState(blockPos).getValue(NetherSiphonLinkStation.TYPE) == 1) {
				level.setBlock(blockPos, blockState.setValue(NetherSiphonLinkStation.TYPE, 0), 3);
			}

			// 0=未连接, 2=连接, 3=冷却
			if (netherSiphonLinkStationEntity.line) {
				// 未连接变成连接
				if (level.getBlockState(blockPos).getValue(NetherSiphonLinkStation.TYPE) == 0) {
					level.setBlock(blockPos, blockState.setValue(NetherSiphonLinkStation.TYPE, 2), 3);
				}
				// 非冷却变成冷却
				else if (level.getBlockEntity(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ)) instanceof NetherSiphonCoreEntity netherSiphonCoreEntity && netherSiphonCoreEntity.isCooldown) {
					level.setBlock(blockPos, blockState.setValue(NetherSiphonLinkStation.TYPE, 3), 3);
				}
				// 冷却变成非冷却
				else if (level.getBlockEntity(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ)) instanceof NetherSiphonCoreEntity netherSiphonCoreEntity && !netherSiphonCoreEntity.isCooldown) {
					level.setBlock(blockPos, blockState.setValue(NetherSiphonLinkStation.TYPE, 2), 3);
				}
			}
			if (!netherSiphonLinkStationEntity.line) {
				level.setBlock(blockPos, blockState.setValue(NetherSiphonLinkStation.TYPE, 0), 3);
			}
		}

		if (i % 320L == 0L) {
			if (level.getBlockState(blockPos).getValue(NetherSiphonLinkStation.TYPE) == 2) {
				AtomicInteger n = new AtomicInteger();
				int radius = (int) OtherMainConfig.NetherSiphonLinkStationMutuallyInfluenceReach;
				BlockPos.betweenClosed(blockPos.offset(-radius, -radius, -radius),
						blockPos.offset(radius, radius, radius)).forEach(pos -> {
					if (level.getBlockState(pos).is(BGABlocks.NETHER_SIPHON_LINK_STATION.get())) {
						n.addAndGet(1);
					}
				});
				netherSiphonLinkStationEntity.stationCount = n.get();
			}
		}

		if (i % 120L == 0L) {
			if (level.getBlockEntity(new BlockPos(netherSiphonLinkStationEntity.lineX, netherSiphonLinkStationEntity.lineY, netherSiphonLinkStationEntity.lineZ)) instanceof NetherSiphonCoreEntity netherSiphonCoreEntity) {
				netherSiphonLinkStationEntity.mainLevel = netherSiphonCoreEntity.mainLevel;
			}
			else {
				netherSiphonLinkStationEntity.mainLevel = 0;
			}
		}

		if (level.random.nextInt(getProductionInterval(netherSiphonLinkStationEntity.stationCount, netherSiphonLinkStationEntity.mainLevel)) == 0) {
			if (level.getBlockState(blockPos).getValue(NetherSiphonLinkStation.TYPE) == 2) {
				if (level instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(BGAParticleTypes.NETHER_SIPHON_LINK_STATION.get(), blockPos.getX() + 0.5f, blockPos.getY() + 0.1f, blockPos.getZ() + 0.5f, 0, 0, 0, 0, 0);
					BlockPos belowPos = blockPos.below();
					BlockEntity belowEntity = level.getBlockEntity(belowPos);
					Container container = belowEntity instanceof Container ? (Container) belowEntity : null;

					for (ItemStack drop : level.getServer().getLootData()
							.getLootTable(new ResourceLocation(netherSiphonLinkStationEntity.giftItem))
							.getRandomItems(new LootParams.Builder(serverLevel)
									.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos))
									.withParameter(LootContextParams.BLOCK_STATE, level.getBlockState(blockPos))
									.withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(blockPos))
									.create(LootContextParamSets.EMPTY))) {
						ItemStack remaining = drop;
						if (container != null) {

							int containerSize = container.getContainerSize();
							for (int i2 = 0; i2 < containerSize && !remaining.isEmpty(); i2++) {
								if (container.canPlaceItem(i2, remaining)) {
									ItemStack slotStack = container.getItem(i2);
									if (ItemStack.isSameItemSameTags(slotStack, remaining)) {
										int maxStack = Math.min(slotStack.getMaxStackSize(), container.getMaxStackSize());
										int space = maxStack - slotStack.getCount();
										if (space > 0) {
											int toTransfer = Math.min(space, remaining.getCount());
											slotStack.grow(toTransfer);
											remaining.shrink(toTransfer);
											container.setItem(i2, slotStack);
											container.setChanged();
										}
									}
								}
							}
							for (int i2 = 0; i2 < containerSize && !remaining.isEmpty(); i2++) {
								if (container.canPlaceItem(i2, remaining) && container.getItem(i2).isEmpty()) {
									int maxStackSize = Math.min(remaining.getMaxStackSize(), container.getMaxStackSize());
									ItemStack toPut = remaining.split(maxStackSize);
									container.setItem(i2, toPut);
									container.setChanged();
								}
							}
						}
						if (!remaining.isEmpty()) {
							netherSiphonLinkStationEntity.spawnAtLocation(remaining);
						}
					}
				}
			}
		}
	}

	public String giftItem = "blackgoldalliance:gameplay/blackgoldalliance_other/nether_siphon_link_station";
	@Nullable
	public ItemEntity spawnAtLocation(ItemStack itemStack) {
		return this.spawnAtLocation(itemStack, 0.0F);
	}
	@Nullable
	public ItemEntity spawnAtLocation(ItemStack p_19985_, float p_19986_) {
		if (p_19985_.isEmpty()) {
			return null;
		} else if (getLevel() != null && getLevel().isClientSide) {
			return null;
		} else if (getLevel() != null) {
			ItemEntity itementity = new ItemEntity(getLevel(), this.getBlockPos().getX(), this.getBlockPos().getY() + (double)p_19986_, this.getBlockPos().getZ(), p_19985_);
			itementity.setDefaultPickUpDelay();
			getLevel().addFreshEntity(itementity);
			return itementity;
		}
		return null;
	}

	private static final double BASE_INTERVAL = 8.0 * 20;
	private static final double PEAK_N = 6.0;

	public static int getProductionInterval(int n, int mainLevel) {
		n = Math.max(1, n);
		double interval = BASE_INTERVAL * Math.exp((n - 1) / PEAK_N);
		if (mainLevel > 0) {
			double scale = 1.0 + mainLevel / 3.0;
			interval /= scale;
		}
		return Math.max(1, (int) Math.round(interval));
	}
}
