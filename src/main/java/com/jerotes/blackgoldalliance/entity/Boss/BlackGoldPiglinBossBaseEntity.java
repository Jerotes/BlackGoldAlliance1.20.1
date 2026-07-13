package com.jerotes.blackgoldalliance.entity.Boss;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Optional;
import java.util.UUID;

public abstract class BlackGoldPiglinBossBaseEntity extends BlackGoldPiglinEntity {
	//胜利
	private static final EntityDataAccessor<Boolean> VICTORY = SynchedEntityData.defineId(BlackGoldPiglinBossBaseEntity.class, EntityDataSerializers.BOOLEAN);
	//是否挑战
	private static final EntityDataAccessor<Boolean> CHALLENGE = SynchedEntityData.defineId(BlackGoldPiglinBossBaseEntity.class, EntityDataSerializers.BOOLEAN);
	//选择玩家
	private static final EntityDataAccessor<Optional<UUID>> TARGET_PLAYER = SynchedEntityData.defineId(BlackGoldPiglinBossBaseEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Integer> RAID_LEVEL = SynchedEntityData.defineId(BlackGoldPiglinBossBaseEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> START_X = SynchedEntityData.defineId(BlackGoldPiglinBossBaseEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> START_Y = SynchedEntityData.defineId(BlackGoldPiglinBossBaseEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> START_Z = SynchedEntityData.defineId(BlackGoldPiglinBossBaseEntity.class, EntityDataSerializers.INT);

	public BlackGoldPiglinBossBaseEntity(EntityType<? extends BlackGoldPiglinEntity> type, Level world) {
		super(type, world);
	}

	//等级
	public int getRaidLevel() {
		return this.getEntityData().get(RAID_LEVEL);
	}
	public void setRaidLevel(int n) {
		this.getEntityData().set(RAID_LEVEL, n);
	}
	public boolean isVictory() {
		return this.getEntityData().get(VICTORY);
	}
	public void setVictory(boolean bl) {
		this.getEntityData().set(VICTORY, bl);
	}
	public boolean isChallenge() {
		return this.getEntityData().get(CHALLENGE);
	}
	public void setChallenge(boolean bl) {
		this.getEntityData().set(CHALLENGE, bl);
	}
	public Player getTargetPlayer() {
		return this.level().getPlayerByUUID(getTargetPlayerUUID());
	}
	public UUID getTargetPlayerUUID() {
		Optional<UUID> optionalUUID = this.getEntityData().get(TARGET_PLAYER);
		return optionalUUID.orElseGet(this::getUUID);
	}
	public void setTargetPlayer(Player player) {
		this.getEntityData().set(TARGET_PLAYER, Optional.of(player.getUUID()));
	}
	public void setTargetPlayerUUID(UUID player) {
		this.getEntityData().set(TARGET_PLAYER, Optional.of(player));
	}
	//
	public void setStartPos(BlockPos blockPos) {
		this.entityData.set(START_X, blockPos.getX());
		this.entityData.set(START_Y, blockPos.getY());
		this.entityData.set(START_Z, blockPos.getZ());
	}
	public int getStartX() { return this.entityData.get(START_X); }
	public int getStartY() { return this.entityData.get(START_Y); }
	public int getStartZ() { return this.entityData.get(START_Z); }
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("RaidLevel", this.getRaidLevel());
		compoundTag.putBoolean("IsVictory", this.isVictory());
		compoundTag.putBoolean("IsChallenge", this.isChallenge());
		compoundTag.putUUID("TargetPlayerUUID", this.getTargetPlayerUUID());
		compoundTag.putInt("XStart", getStartX());
		compoundTag.putInt("YStart", getStartY());
		compoundTag.putInt("ZStart", getStartZ());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setRaidLevel(compoundTag.getInt("RaidLevel"));
		this.setVictory(compoundTag.getBoolean("IsVictory"));
		this.setChallenge(compoundTag.getBoolean("IsChallenge"));
		if (compoundTag.hasUUID("TargetPlayerUUID")) {
			this.setTargetPlayerUUID(compoundTag.getUUID("TargetPlayerUUID"));
		}
		this.setStartPos(new BlockPos((int) compoundTag.getInt("XStart"), (int) compoundTag.getInt("YStart"), (int) compoundTag.getInt("ZStart")));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(RAID_LEVEL, 1);
		this.getEntityData().define(VICTORY, false);
		this.getEntityData().define(CHALLENGE, false);
		this.getEntityData().define(TARGET_PLAYER, Optional.of(this.getUUID()));
		this.getEntityData().define(START_X, 0);
		this.getEntityData().define(START_Y, -9999);
		this.getEntityData().define(START_Z, 0);
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource damageSource, int n, boolean bl) {
		super.dropCustomDeathLoot(damageSource, n, bl);
		this.dropFromLootTableAdd(damageSource, bl);
	}
	public ResourceLocation getDefaultLootTableAdd() {
		String strings = switch (this.getRaidLevel()) {
			case 2 -> "level_ii";
			case 3 -> "level_iii";
			case 4 -> "level_iv";
			case 5 -> "level_v";
			default -> {
				if (this.getRaidLevel() > 5) yield "level_vi";
				else yield "level_i";
			}
		};
		return new ResourceLocation(BGA.MODID, "entities/piglin_raid_nether_portal_player_virtory/" + strings);
	}
	protected void dropFromLootTableAdd(DamageSource damageSource, boolean bl) {
		ResourceLocation resourcelocation = this.getDefaultLootTableAdd();
		LootTable loottable = this.level().getServer().getLootData().getLootTable(resourcelocation);
		LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel)this.level())).withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.DAMAGE_SOURCE, damageSource).withOptionalParameter(LootContextParams.KILLER_ENTITY, damageSource.getEntity()).withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, damageSource.getDirectEntity());
		if (bl && this.lastHurtByPlayer != null) {
			lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
		}
		LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
		loottable.getRandomItems(lootparams, this.getLootTableSeed(), this::spawnAtLocation);
	}
}