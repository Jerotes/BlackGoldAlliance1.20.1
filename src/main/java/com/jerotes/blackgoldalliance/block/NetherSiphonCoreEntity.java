package com.jerotes.blackgoldalliance.block;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.config.OtherMainConfig;
import com.jerotes.blackgoldalliance.entity.Boss.BlackGoldPiglinBossBaseEntity;
import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.init.*;
import com.jerotes.blackgoldalliance.network.BGAPlayerData;
import com.jerotes.blackgoldalliance.world.inventory.NetherSiphonCoreMenu;
import com.jerotes.jerotes.event.AdvancementEvent;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class NetherSiphonCoreEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	public static final Predicate<Entity> FIND_PLAYER = (entity) -> {
		return !entity.isSpectator() && !(entity instanceof LivingEntity livingEntity && (livingEntity.hasEffect(BGAMobEffects.PIGLIN_OMEN.get()) || livingEntity.hasEffect(BGAMobEffects.BLACK_GOLD_OMEN.get())));
	};
	private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
	private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter(){
		@Override
		protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
			NetherSiphonCoreEntity.this.playSound(blockState, SoundEvents.METAL_PLACE);
		}
		@Override
		protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
			NetherSiphonCoreEntity.this.playSound(blockState, SoundEvents.METAL_PLACE);
		}
		@Override
		protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int n, int n2) {
		}
		@Override
		protected boolean isOwnContainer(Player player) {
			if (player.containerMenu instanceof NetherSiphonCoreMenu netherSiphonCoreMenu) {
				Container container = netherSiphonCoreMenu.getContainer();
				return container == NetherSiphonCoreEntity.this;
			} else {
				return false;
			}
		}
	};
	protected final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int i) {
			return switch (i) {
				case 0 -> NetherSiphonCoreEntity.this.getBlockPos().getX();
				case 1 -> NetherSiphonCoreEntity.this.getBlockPos().getY();
				case 2 -> NetherSiphonCoreEntity.this.getBlockPos().getZ();
				case 3 -> NetherSiphonCoreEntity.this.burnTime;
				case 4 -> NetherSiphonCoreEntity.this.burnDuration;
				case 5 -> NetherSiphonCoreEntity.this.getBlockState().getValue(BlockStateProperties.AGE_3);
				case 6 -> NetherSiphonCoreEntity.this.cooldownCount;
				case 7 -> NetherSiphonCoreEntity.this.isCooldown ? 1 : 0;
				default -> 0;
			};
		}

		@Override
		public void set(int n, int n2) {
			switch (n) {
				case 3:
					NetherSiphonCoreEntity.this.burnTime = n2;
					break;
				case 4:
					NetherSiphonCoreEntity.this.burnDuration = n2;
			}
		}

		public int getCount() {
			return 8;
		}
	};

	public NetherSiphonCoreEntity(BlockPos blockPos, BlockState blockState) {
		super(BGABlockEntityType.NETHER_SIPHON_CORE.get(), blockPos, blockState);
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
		return new NetherSiphonCoreMenu(id, inventory, this, dataAccess);
	}
	@Override
	public void startOpen(Player player) {
		if (!this.remove && !player.isSpectator()) {
			this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}
	@Override
	public void stopOpen(Player player) {
		if (!this.remove && !player.isSpectator()) {
			this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}
	public void recheckOpen() {
		if (!this.remove) {
			this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items)
			if (!itemstack.isEmpty())
				return false;
		return true;
	}
	@Override
	public int[] getSlotsForFace(Direction direction) {
		return IntStream.range(0, this.getContainerSize()).toArray();
	}
	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		if (index >= FUEL_SLOTS_START && index <= FUEL_SLOTS_END) {
			return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
		}
		return false;
	}
	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		if (slot >= FUEL_SLOTS_START && slot <= FUEL_SLOTS_END) {
			return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
		}
		return false;
	}
	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return true;
	}

	void playSound(BlockState blockState, SoundEvent soundEvent) {
		double d = (double)this.worldPosition.getX() + 0.5;
		double d2 = (double)this.worldPosition.getY() + 0.5;
		double d3 = (double)this.worldPosition.getZ() + 0.5;
		this.level.playSound(null, d, d2, d3, soundEvent, SoundSource.BLOCKS, 0.5f, this.level.random.nextFloat() * 0.1f + 0.9f);
	}
	public static int GetDefeatTheHighestLevelPiglinRaid(Player player) {
		return player.getCapability(BGAPlayerData.CAPABILITY, null).orElse(new BGAPlayerData.PlayerVariables()).DefeatTheHighestLevelPiglinRaid;
	}
	public static void SetDefeatTheHighestLevelPiglinRaid(Player player, int n) {
		player.getCapability(BGAPlayerData.CAPABILITY, null).ifPresent(capability -> {
			capability.setDefeatTheHighestLevelPiglinRaid(n);
		});
	}

	private static final int FUEL_SLOTS_START = 3;
	private static final int FUEL_SLOTS_END = 8;
	//六分钟
	public static final int MAX_COOLDOWN = 6 * 60 * 20 * 16;
	public int tickCount;
	public int tickCountOther;
	public int cooldownCount;
	public boolean isCooldown;
	public boolean isActive;
	public int burnTime;
	public int burnDuration;
	public int leftLevel;
	public int rightLevel;
	public int mainLevel;
	public boolean effectDone;
	public boolean alreadyRaid;
	public boolean canNotStopCooldown;
	public int portalFailTick;
	public int selfFailTick;
	public boolean isTrueActive() {
		return this.isActive && !this.isCooldown && this.burnTime > 0;
	}
	public UUID forceUUID;
	public UUID portalUUID;
	protected void saveAdditional(CompoundTag compoundTag) {
		if (this.forceUUID != null) {
			compoundTag.putUUID("ForceUUID", this.forceUUID);
		}
		if (this.portalUUID != null) {
			compoundTag.putUUID("PortalUUID", this.portalUUID);
		}
		super.saveAdditional(compoundTag);
		if (!this.trySaveLootTable(compoundTag)) {
			ContainerHelper.saveAllItems(compoundTag, this.items);
		}
		compoundTag.putBoolean("IsActive", this.isActive);
		compoundTag.putBoolean("IsCooldown", this.isCooldown);
		compoundTag.putInt("CooldownCount", this.cooldownCount);
		compoundTag.putInt("BurnTime", this.burnTime);
		compoundTag.putInt("BurnDuration", this.burnDuration);
		compoundTag.putInt("LeftLevel", this.leftLevel);
		compoundTag.putInt("RightLevel", this.rightLevel);
		compoundTag.putInt("MainLevel", this.mainLevel);
		compoundTag.putBoolean("IsEffectDone", this.effectDone);
		compoundTag.putBoolean("IsAlreadyRaid", this.alreadyRaid);
		compoundTag.putBoolean("IsCanNotStopCooldown", this.canNotStopCooldown);
	}
	public void load(CompoundTag compoundTag) {
		this.forceUUID = compoundTag.contains("ForceUUID", Tag.TAG_INT_ARRAY) ? compoundTag.getUUID("ForceUUID") : null;
		this.portalUUID = compoundTag.contains("PortalUUID", Tag.TAG_INT_ARRAY) ? compoundTag.getUUID("PortalUUID") : null;
		super.load(compoundTag);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(compoundTag)) {
			ContainerHelper.loadAllItems(compoundTag, this.items);
		}
		this.isActive = compoundTag.getBoolean("IsActive");
		this.isCooldown = compoundTag.getBoolean("IsCooldown");
		this.cooldownCount = compoundTag.getInt("CooldownCount");
		this.burnTime = compoundTag.getInt("BurnTime");
		this.burnDuration = compoundTag.getInt("BurnDuration");
		this.leftLevel = compoundTag.getInt("LeftLevel");
		this.rightLevel = compoundTag.getInt("RightLevel");
		this.mainLevel = compoundTag.getInt("MainLevel");
		this.effectDone = compoundTag.getBoolean("IsEffectDone");
		this.alreadyRaid = compoundTag.getBoolean("IsAlreadyRaid");
		this.canNotStopCooldown = compoundTag.getBoolean("IsCanNotStopCooldown");
	}

	public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, NetherSiphonCoreEntity netherSiphonCoreEntity) {
		if (netherSiphonCoreEntity.isCooldown)
			return;
		long i = level.getGameTime();
		if (i % 40L == 0L) {
			netherSiphonCoreEntity.isActive = updateShape(level, blockPos);
		}
	}
	public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, NetherSiphonCoreEntity netherSiphonCoreEntity) {
		++netherSiphonCoreEntity.tickCount;
		long i = level.getGameTime();
		if (i % 20L == 0L) {
			netherSiphonCoreEntity.leftLevel =
					netherSiphonCoreEntity.items.get(1).is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_III) ? 3 :
							netherSiphonCoreEntity.items.get(1).is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_II) ? 2 :
									netherSiphonCoreEntity.items.get(1).is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_I) ? 1 : 0;
			netherSiphonCoreEntity.rightLevel =
					netherSiphonCoreEntity.items.get(2).is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_III) ? 3 :
							netherSiphonCoreEntity.items.get(2).is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_II) ? 2 :
									netherSiphonCoreEntity.items.get(2).is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_I) ? 1 : 0;
			netherSiphonCoreEntity.mainLevel =
					netherSiphonCoreEntity.items.get(0).is(BGAItems.NETHER_SIPHON_DETERRENT_UPGRADE_SCROLL_III.get()) ? 3 :
							netherSiphonCoreEntity.items.get(0).is(BGAItems.NETHER_SIPHON_DETERRENT_UPGRADE_SCROLL_II.get()) ? 2 :
									netherSiphonCoreEntity.items.get(0).is(BGAItems.NETHER_SIPHON_DETERRENT_UPGRADE_SCROLL_I.get()) ? 1 : 0;
		}
		//冷却中
		if (netherSiphonCoreEntity.isCooldown) {
			if (!netherSiphonCoreEntity.canNotStopCooldown) {
				netherSiphonCoreEntity.cooldownCount = Math.max(0, netherSiphonCoreEntity.cooldownCount - 16);
			}
			netherSiphonCoreEntity.cooldownEvent();
		}
		//非冷却
		else if (netherSiphonCoreEntity.isActive && netherSiphonCoreEntity.burnTime > 0 && !netherSiphonCoreEntity.canNotStopCooldown) {
			int addCount = 8;
			if (netherSiphonCoreEntity.leftLevel >= 3)
				addCount -= 4;
			else if (netherSiphonCoreEntity.leftLevel == 2)
				addCount -= 3;
			else if (netherSiphonCoreEntity.leftLevel == 1)
				addCount -= 2;

			if (netherSiphonCoreEntity.rightLevel >= 3)
				addCount -= 4;
			else if (netherSiphonCoreEntity.rightLevel == 2)
				addCount -= 3;
			else if (netherSiphonCoreEntity.rightLevel == 1)
				addCount -= 2;

			if (addCount <= 0) {
				netherSiphonCoreEntity.cooldownCount = Math.max(0, netherSiphonCoreEntity.cooldownCount - 32);
			}
			else {
				netherSiphonCoreEntity.cooldownCount += addCount;
			}
			if (netherSiphonCoreEntity.forceUUID != null || netherSiphonCoreEntity.portalUUID != null || netherSiphonCoreEntity.alreadyRaid || netherSiphonCoreEntity.effectDone) {
				netherSiphonCoreEntity.alreadyRaid = false;
				netherSiphonCoreEntity.forceUUID = null;
				netherSiphonCoreEntity.portalUUID = null;
				netherSiphonCoreEntity.effectDone = false;
				netherSiphonCoreEntity.setChanged();
			}
		}
		else {
			if (netherSiphonCoreEntity.forceUUID != null || netherSiphonCoreEntity.portalUUID != null || netherSiphonCoreEntity.alreadyRaid || netherSiphonCoreEntity.effectDone) {
				netherSiphonCoreEntity.alreadyRaid = false;
				netherSiphonCoreEntity.forceUUID = null;
				netherSiphonCoreEntity.portalUUID = null;
				netherSiphonCoreEntity.effectDone = false;
				netherSiphonCoreEntity.setChanged();
			}
		}
		//超过 开始冷却
		if (netherSiphonCoreEntity.cooldownCount >= NetherSiphonCoreEntity.MAX_COOLDOWN && !netherSiphonCoreEntity.isCooldown) {
			netherSiphonCoreEntity.cooldownCount = NetherSiphonCoreEntity.MAX_COOLDOWN;
			netherSiphonCoreEntity.isCooldown = true;
			level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 3).setValue(NetherSiphonCore.OPEN, false), 3);
			netherSiphonCoreEntity.setChanged();
		}
		//小于 取消冷却
		if (netherSiphonCoreEntity.cooldownCount <= 0 && netherSiphonCoreEntity.isCooldown && !netherSiphonCoreEntity.canNotStopCooldown) {
			netherSiphonCoreEntity.cooldownCount = 0;
			netherSiphonCoreEntity.isCooldown = false;
			level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, netherSiphonCoreEntity.isActive ? 1 : 0).setValue(NetherSiphonCore.OPEN, false), 3);
			netherSiphonCoreEntity.setChanged();
		}
		//未冷却时
		if (netherSiphonCoreEntity.isCooldown)
			return;
		if (i % 40L == 0L) {
			boolean rightShape = updateShape(level, blockPos);
			if (rightShape != netherSiphonCoreEntity.isActive) {
				SoundEvent soundevent = rightShape ? BGASoundEvents.NETHER_SIPHON_CORE_START : BGASoundEvents.NETHER_SIPHON_CORE_STOP;
				level.playSound(null, blockPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
				if (blockState.getBlock() instanceof NetherSiphonCore netherSiphonCore) {
					if (netherSiphonCore.getCoreType(blockState) == 0 && rightShape) {
						level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 1).setValue(NetherSiphonCore.OPEN, false), 3);
						netherSiphonCoreEntity.setChanged();
					}
					else if (netherSiphonCore.getCoreType(blockState) == 1 && !rightShape) {
						level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 0).setValue(NetherSiphonCore.OPEN, false), 3);
						netherSiphonCoreEntity.setChanged();
					}
					else if (netherSiphonCore.getCoreType(blockState) == 2 && !rightShape) {
						level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 0).setValue(NetherSiphonCore.OPEN, false), 3);
						netherSiphonCoreEntity.setChanged();
					}
				}
			}
			netherSiphonCoreEntity.isActive = rightShape;
		}

		if (netherSiphonCoreEntity.isActive) {
			boolean fuelChanged = false;

			if (netherSiphonCoreEntity.burnTime > 0) {
				netherSiphonCoreEntity.burnTime -= 4;
			}

			if (netherSiphonCoreEntity.burnTime == 0) {
				for (int slot = FUEL_SLOTS_START; slot <= FUEL_SLOTS_END && netherSiphonCoreEntity.burnTime == 0; slot++) {
					ItemStack fuelStack = netherSiphonCoreEntity.items.get(slot);
					if (!fuelStack.isEmpty()) {
						int burnTimeForThisItem = ForgeHooks.getBurnTime(fuelStack, RecipeType.SMELTING);
						if (burnTimeForThisItem > 0) {
							netherSiphonCoreEntity.burnDuration = netherSiphonCoreEntity.burnTime = burnTimeForThisItem;
							fuelStack.shrink(1);
							fuelChanged = true;
							if (fuelStack.isEmpty()) {
								ItemStack remainingItem = fuelStack.getItem().getCraftingRemainingItem(fuelStack);
								netherSiphonCoreEntity.items.set(slot, remainingItem);
							}
							break;
						}
					}
				}
			}

			boolean isBurningNow = netherSiphonCoreEntity.burnTime > 0;
			if (blockState.getBlock() instanceof NetherSiphonCore netherSiphonCore) {
				if (netherSiphonCore.getCoreType(blockState) == 0 && isBurningNow) {
					level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 2).setValue(NetherSiphonCore.OPEN, false), 3);
					netherSiphonCoreEntity.setChanged();
				}
				else if (netherSiphonCore.getCoreType(blockState) == 1 && isBurningNow) {
					level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 2).setValue(NetherSiphonCore.OPEN, false), 3);
					netherSiphonCoreEntity.setChanged();
				}
				else if (netherSiphonCore.getCoreType(blockState) == 2 && !isBurningNow) {
					level.setBlock(blockPos, blockState.setValue(NetherSiphonCore.TYPE, 1).setValue(NetherSiphonCore.OPEN, false), 3);
					netherSiphonCoreEntity.setChanged();
				}
			}

			if (fuelChanged) {
				netherSiphonCoreEntity.setChanged();
			}
		}
		if (netherSiphonCoreEntity.isTrueActive()) {
			if (i % 40L == 0L) {
				applyEffects(level, blockPos, netherSiphonCoreEntity.mainLevel);
			}
		}
	}
	private static boolean updateShape(Level pLevel, BlockPos pCorePos) {
		Block goldBlock = Blocks.GOLD_BLOCK;
		Block blackstone = Blocks.BLACKSTONE;
		int[][][] structure = {
				{
						{-1, -1, -1, 0},
						{-1, -1, 1, 0},
						{1, -1, -1, 0},
						{1, -1, 1, 0},
						{0, -1, 0, 1},
						{-1, -1, 0, 1},
						{1, -1, 0, 1},
						{0, -1, -1, 1},
						{0, -1, 1, 1}
				},
				{
						{-1, 0, -1, 1},
						{-1, 0, 1, 1},
						{1, 0, -1, 1},
						{1, 0, 1, 1}
				},
				{
						{0, 1, 0, 1},
						{-1, 1, 0, 1},
						{1, 1, 0, 1},
						{0, 1, -1, 1},
						{0, 1, 1, 1}
				}
		};
		for (int[][] layer : structure) {
			for (int[] pos : layer) {
				int relX = pos[0];
				int relY = pos[1];
				int relZ = pos[2];
				int blockType = pos[3];
				BlockPos checkPos = pCorePos.offset(relX, relY, relZ);
				BlockState blockState = pLevel.getBlockState(checkPos);
				Block expectedBlock = (blockType == 0) ? goldBlock : blackstone;
				if (!blockState.is(expectedBlock)) {
					return false;
				}
			}
		}
		return true;
	}
	private static void applyEffects(Level level, BlockPos blockPos, int mainLevel) {
		double j = OtherMainConfig.NetherSiphonUsefulToPiglinMaxReach * (1 + mainLevel * 0.2f);
		int k = blockPos.getX();
		int l = blockPos.getY();
		int i1 = blockPos.getZ();
		AABB aabb = (new AABB(k, l, i1, k + 1, l + 1, i1 + 1)).inflate(j).expandTowards(0.0D, level.getHeight(), 0.0D);
		List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, aabb);
		list.removeIf(livingEntity -> !(
				EntityFactionFind.isPiglin(livingEntity) ||
						livingEntity instanceof HoglinBase ||
						livingEntity instanceof Strider ||
				livingEntity instanceof ZombifiedPiglin
		));
		if (!list.isEmpty()) {
			for(LivingEntity livingEntity : list) {
				if (blockPos.closerThan(livingEntity.blockPosition(), j)) {
					livingEntity.addEffect(new MobEffectInstance(BGAMobEffects.PIGLIN_DETERRENT.get(),
							240, Math.max(mainLevel, 1) - 1,
							true, true));
				}
			}
		}
	}
	//冷却中事件
	public void cooldownEvent() {
		if (this.level == null)
			return;
		int cooldownTick = (int) (cooldownCount / 16f);
		//袭击未开始相关
		if (!alreadyRaid) {
			if (!effectDone && cooldownTick > 40 && isCooldown && this.level.random.nextInt(60 * 20) == 1) {
				Player nearest = level.getNearestPlayer(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), 32, FIND_PLAYER);
				if (nearest != null) {
					int level = 1;
					if (GetDefeatTheHighestLevelPiglinRaid(nearest) == 1)
						level = 2;
					if (GetDefeatTheHighestLevelPiglinRaid(nearest) == 2)
						level = 3;
					if (GetDefeatTheHighestLevelPiglinRaid(nearest) > 2)
						level = Main.randomReach(this.level.random, 1, 3);
					nearest.addEffect(new MobEffectInstance(level >= 3 ? BGAMobEffects.BLACK_GOLD_OMEN.get() : BGAMobEffects.PIGLIN_OMEN.get(), (Math.min(5 * 20 * 60, cooldownTick + 20)), level - 1, true, true));
					Component component = Component.translatable("boss.blackgoldalliance.piglin_raid_nether_portal", nearest.getDisplayName()).withStyle(ChatFormatting.GOLD);
					nearest.sendSystemMessage(component);
					nearest.displayClientMessage(component, true);
					this.effectDone = true;
					this.setChanged();
				}
			}
			//给予冷却
			if (this.canNotStopCooldown) {
				this.canNotStopCooldown = false;
				this.setChanged();
			}
			//方块状态
			if (this.getBlockState().getBlock() instanceof NetherSiphonCore netherSiphonCore) {
				if (netherSiphonCore.isCoreOpen(this.getBlockState())) {
					level.setBlock(getBlockPos(), this.getBlockState().setValue(NetherSiphonCore.TYPE, 3).setValue(NetherSiphonCore.OPEN, false), 3);
					this.setChanged();
				}
			}
			if (this.forceUUID != null || this.portalUUID != null) {
				this.forceUUID = null;
				this.portalUUID = null;
				this.setChanged();
			}
		}
		//袭击开始相关
		else {
			if (this.level instanceof ServerLevel serverLevel) {
				Entity portal = serverLevel.getEntity(portalUUID);
				Entity force = serverLevel.getEntity(forceUUID);
				long i = level.getGameTime();
				if (i % 40L == 0L) {
					applyEffects(level, getBlockPos(), this.mainLevel);
				}
				if ((portal == null || force == null) && portalUUID != null && forceUUID != null) {
					this.tickCountOther ++;
					if (this.tickCountOther >= 100) {
						if (force instanceof NetherSiphonCoreForceEntity netherSiphonCoreForceEntitys && netherSiphonCoreForceEntitys.isAlive()) {
							//离场
							netherSiphonCoreForceEntitys.hurt(netherSiphonCoreForceEntitys.damageSources().genericKill(), 10240);
						}
						this.alreadyRaid = false;
						this.forceUUID = null;
						this.portalUUID = null;
						this.setChanged();
					}
					return;
				}
				else {
					this.tickCountOther = 0;
				}
				//胜利！！！
				if (
						(portalUUID == null
								|| !(portal instanceof PiglinRaidNetherPortalEntity) && !(portal instanceof BlackGoldPiglinBossBaseEntity)
								|| (portal instanceof PiglinRaidNetherPortalEntity piglinRaidNetherPortalEntity && (!piglinRaidNetherPortalEntity.isAlive() && !piglinRaidNetherPortalEntity.isVictory() || !piglinRaidNetherPortalEntity.isChallenge()))
								|| (portal instanceof BlackGoldPiglinBossBaseEntity blackGoldPiglinBossBaseEntity && (!blackGoldPiglinBossBaseEntity.isAlive() && !blackGoldPiglinBossBaseEntity.isVictory() || !blackGoldPiglinBossBaseEntity.isChallenge()))
						)
								&&
								(force instanceof NetherSiphonCoreForceEntity netherSiphonCoreForceEntity && netherSiphonCoreForceEntity.isAlive())
				) {
					if (this.portalFailTick < 10) {
						this.portalFailTick++;
					}
					else {
						if (force instanceof NetherSiphonCoreForceEntity netherSiphonCoreForceEntitys && netherSiphonCoreForceEntitys.isAlive()) {

							if ((portal instanceof PiglinRaidNetherPortalEntity piglinRaidNetherPortalEntity && (!piglinRaidNetherPortalEntity.isAlive() && !piglinRaidNetherPortalEntity.isVictory() || !piglinRaidNetherPortalEntity.isChallenge()))) {
								BGA.LOGGER.info("(portal instanceof PiglinRaidNetherPortalEntity piglinRaidNetherPortalEntity && (!piglinRaidNetherPortalEntity.isAlive() && !piglinRaidNetherPortalEntity.isVictory() || !piglinRaidNetherPortalEntity.isChallenge()))");
								BGA.LOGGER.info(portalUUID + "   forcePortalUUID");
							}
							//离场
							netherSiphonCoreForceEntitys.hurt(netherSiphonCoreForceEntitys.damageSources().genericKill(), 10240);
						}
						this.alreadyRaid = false;
						this.forceUUID = null;
						this.portalUUID = null;
						this.setChanged();
					}

				}
				//战斗中
				else if (
						(portal instanceof PiglinRaidNetherPortalEntity piglinRaidNetherPortalEntity
								&& piglinRaidNetherPortalEntity.isAlive()
								&& piglinRaidNetherPortalEntity.isChallenge()
								&& !piglinRaidNetherPortalEntity.isVictory())
								||
								(portal instanceof BlackGoldPiglinBossBaseEntity blackGoldPiglinBossBaseEntity
										&& blackGoldPiglinBossBaseEntity.isAlive()
										&& blackGoldPiglinBossBaseEntity.isChallenge()
										&& !blackGoldPiglinBossBaseEntity.isVictory())
										&&
										(force instanceof NetherSiphonCoreForceEntity netherSiphonCoreForceEntity && netherSiphonCoreForceEntity.isAlive())
				) {
					this.portalFailTick = 0;
				}
				//意外情况
				else if ((!(portal instanceof PiglinRaidNetherPortalEntity) && !(portal instanceof BlackGoldPiglinBossBaseEntity)
						|| (portal instanceof PiglinRaidNetherPortalEntity piglinRaidNetherPortalEntity && (!piglinRaidNetherPortalEntity.isAlive() && !piglinRaidNetherPortalEntity.isVictory() || !piglinRaidNetherPortalEntity.isChallenge()))
						|| (portal instanceof BlackGoldPiglinBossBaseEntity blackGoldPiglinBossBaseEntity && (!blackGoldPiglinBossBaseEntity.isAlive() && !blackGoldPiglinBossBaseEntity.isVictory() || !blackGoldPiglinBossBaseEntity.isChallenge())))
						&&
						!(force instanceof NetherSiphonCoreForceEntity netherSiphonCoreForceEntity && netherSiphonCoreForceEntity.isAlive())
				) {
					if (this.portalFailTick < 10) {
						this.portalFailTick++;
					}
					else {
						if (force instanceof NetherSiphonCoreForceEntity netherSiphonCoreForceEntitys && netherSiphonCoreForceEntitys.isAlive()) {
							//离场
							netherSiphonCoreForceEntitys.hurt(netherSiphonCoreForceEntitys.damageSources().genericKill(), 10240);
						}
						this.alreadyRaid = false;
						this.forceUUID = null;
						this.portalUUID = null;
						this.setChanged();
					}
				}
				//失败
				else {
					if (this.portalFailTick < 10) {
						this.portalFailTick++;
					}
					else {
						this.alreadyRaid = false;
						this.forceUUID = null;
						this.portalUUID = null;
						this.setChanged();
						if (portal instanceof PiglinRaidNetherPortalEntity piglinRaidNetherPortalEntity && piglinRaidNetherPortalEntity.isAlive()) {
							piglinRaidNetherPortalEntity.setVictory(true);
							//离场
							piglinRaidNetherPortalEntity.hurt(piglinRaidNetherPortalEntity.damageSources().genericKill(), 10240);
							level.destroyBlock(this.getBlockPos(), true, piglinRaidNetherPortalEntity);
						}
						if (portal instanceof BlackGoldPiglinBossBaseEntity blackGoldPiglinBossBaseEntity && blackGoldPiglinBossBaseEntity.isAlive()) {
							blackGoldPiglinBossBaseEntity.setVictory(true);
							level.destroyBlock(this.getBlockPos(), true, blackGoldPiglinBossBaseEntity);
						}

						if (force instanceof NetherSiphonCoreForceEntity netherSiphonCoreForceEntitys && netherSiphonCoreForceEntitys.isAlive()) {
							//离场
							netherSiphonCoreForceEntitys.hurt(netherSiphonCoreForceEntitys.damageSources().genericKill(), 10240);
						}
					}
				}
			}
			if (!this.effectDone || !this.canNotStopCooldown) {
				this.effectDone = true;
				this.canNotStopCooldown = true;
				this.setChanged();
			}
		}
	}


	public void startChallenge(int raidLevel, Player player) {
		if (level instanceof ServerLevel serverLevel) {
			BlockPos blockPos1 = this.getBlockPos();
			BlockPos blockPos2 = player.getOnPos();
			for (int ns = 1; ns < 64; ++ns) {
				blockPos2 = Main.findSpawnPositionNearFillOnBlock(player.level(), this.getBlockPos(), 48);
				if (blockPos2.getCenter().distanceTo(blockPos1.getCenter()) > 28 && blockPos2.getCenter().distanceTo(blockPos1.getCenter()) < 32) break;
			}
			if (player instanceof ServerPlayer serverPlayer) {
				AdvancementEvent.AdvancementGive(serverPlayer, raidLevel > 2 ? "blackgoldalliance:stormclouds_gather" : "blackgoldalliance:old_scars_new_wounds");
			}
			BlockPos blockPos = blockPos2;
			PiglinRaidNetherPortalEntity boss = BGAEntityType.PIGLIN_RAID_NETHER_PORTAL.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
			if (boss != null) {
				boss.setChallenge(true);
				boss.setRaidLevel(raidLevel);
				boss.setBlackGoldAlliance(raidLevel > 2);
				boss.setTargetPlayer(player);
				boss.setTarget(player);
				boss.setDeltaMovement(0, 0, 0);
				boss.setStartPos(this.getBlockPos());

				Component component = Component.translatable("boss.blackgoldalliance.piglin_raid_nether_portal_start", player.getDisplayName()).withStyle(ChatFormatting.GOLD);

				double x = boss.getX();
				double y = boss.getY();
				double z = boss.getZ();
				AABB area = new AABB(x - 64, y - 64, z - 64, x + 64, y + 64, z + 64);
				List<ServerPlayer> nearPlayers = level.getEntitiesOfClass(ServerPlayer.class, area);
				nearPlayers.removeIf(serverPlayer -> serverPlayer.isSpectator());
				for (Player players : nearPlayers) {
					players.sendSystemMessage(component);
					players.displayClientMessage(component, true);
				}
				this.portalUUID = boss.getUUID();
			}
			NetherSiphonCoreForceEntity force = BGAEntityType.NETHER_SIPHON_CORE_FORCE.get().spawn(serverLevel, this.getBlockPos(), MobSpawnType.EVENT);
			if (force != null) {
				force.setPos(this.getBlockPos().getCenter().add(0,-1.5,0));
				force.setStartPos(this.getBlockPos());
				this.forceUUID = force.getUUID();
				this.alreadyRaid = true;
				this.canNotStopCooldown = true;
				this.effectDone = true;
				this.setChanged();
			}
		}
	}
}
