package com.jerotes.blackgoldalliance.entity.Animal;

import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldCavalryEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldHunterEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldMaulerEntity;
import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;

public class BlackGoldStepperSquadEntity extends BlackGoldStepperEntity implements EliteEntity {
	public StepperSimpleContainer inventory() {
		return inventory;
	}
	public BlackGoldStepperSquadEntity(EntityType<? extends BlackGoldStepperSquadEntity> type, Level world) {
		super(type, world);
		this.xpReward = 80;
	}

	@Override
	public int getExperienceReward() {
		return 80;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 220);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 15);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		return builder;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.COMMAND) {
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			//骑手
			BlackGoldHunterEntity blackGoldHunter = BGAEntityType.BLACK_GOLD_HUNTER.get().spawn(serverLevelAccessor.getLevel(), BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.JOCKEY);
			if (blackGoldHunter != null) {
				if (teams != null) {
					serverLevelAccessor.getLevel().getScoreboard().addPlayerToTeam(blackGoldHunter.getStringUUID(), teams);
				}
				blackGoldHunter.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0f);
				blackGoldHunter.finalizeSpawn(serverLevelAccessor, difficultyInstance, MobSpawnType.JOCKEY, null, null);
				if (!this.level().isClientSide()) {
					blackGoldHunter.setBaby(false);
					blackGoldHunter.startRiding(this);
					blackGoldHunter.setCanPickUpLoot(false);
					blackGoldHunter.setEntityNeedDiscardTick(this.getEntityNeedDiscardTick());
					blackGoldHunter.setSelfPortal(this.getSelfPortal());
				}
				this.equipSaddle(blackGoldHunter.getSoundSource());
				this.level().gameEvent(this, GameEvent.EQUIP, this.position());
			}
			//骑士
			for (int n = 0; n < 2; ++n) {
				BlackGoldCavalryEntity blackGold = BGAEntityType.BLACK_GOLD_CAVALRY.get().spawn(serverLevelAccessor.getLevel(), BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.JOCKEY);
				if (blackGold != null) {
					if (teams != null) {
						serverLevelAccessor.getLevel().getScoreboard().addPlayerToTeam(blackGold.getStringUUID(), teams);
					}
					blackGold.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0f);
					blackGold.finalizeSpawn(serverLevelAccessor, difficultyInstance, MobSpawnType.JOCKEY, null, null);
					blackGold.setItemInHand(InteractionHand.MAIN_HAND, BGAItems.BLACK_GOLD_PIKE.get().getDefaultInstance());
					if (!this.level().isClientSide()) {
						blackGold.setBaby(false);
						blackGold.setLeftHanded(n == 0);
						blackGold.startRiding(this);
						blackGold.setCanPickUpLoot(false);
						blackGold.setEntityNeedDiscardTick(this.getEntityNeedDiscardTick());
						blackGold.setSelfPortal(this.getSelfPortal());
					}
				}
			}
			//重兵
			for (int n = 0; n < 2; ++n) {
				BlackGoldMaulerEntity blackGold = BGAEntityType.BLACK_GOLD_MAULER.get().spawn(serverLevelAccessor.getLevel(), BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.JOCKEY);
				if (blackGold != null) {
					if (teams != null) {
						serverLevelAccessor.getLevel().getScoreboard().addPlayerToTeam(blackGold.getStringUUID(), teams);
					}
					blackGold.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0f);
					blackGold.finalizeSpawn(serverLevelAccessor, difficultyInstance, MobSpawnType.JOCKEY, null, null);
					blackGold.setItemInHand(InteractionHand.MAIN_HAND, BGAItems.BLACK_GOLD_SCUTUM.get().getDefaultInstance());
					if (!this.level().isClientSide()) {
						blackGold.setBaby(false);
						blackGold.setLeftHanded(n == 0);
						blackGold.startRiding(this);
						blackGold.setCanPickUpLoot(false);
						blackGold.setEntityNeedDiscardTick(this.getEntityNeedDiscardTick());
						blackGold.setSelfPortal(this.getSelfPortal());
					}
				}
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}
