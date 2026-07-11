package com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin;

import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldWarHoglinEntity;
import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;

public class BlackGoldCavalryEntity extends BlackGoldPiglinEntity implements EliteEntity, UseSpearSpecialEntity {
	public BlackGoldCavalryEntity(EntityType<? extends BlackGoldCavalryEntity> type, Level world) {
		super(type, world);
		xpReward = 50;
	}

	public float getJerotesSpearDamageMultiple() {
		return this.getVehicle() instanceof BlackGoldWarHoglinEntity ? 3.5f : 2.0f;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.38);
		builder = builder.add(Attributes.MAX_HEALTH, 65);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8f);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(BGAItems.BLACK_GOLD_CAVALRY_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(BGAItems.BLACK_GOLD_CAVALRY_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(BGAItems.BLACK_GOLD_CAVALRY_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(BGAItems.BLACK_GOLD_CAVALRY_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}


	public int rushTick;
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("RushTick", this.rushTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.rushTick = compoundTag.getInt("RushTick");
	}


	@Override
	public void aiStep() {
		super.aiStep();
		if (this.rushTick > -120) {
			this.rushTick -= 1;
		}
		if (this.rushTick > 5 && this.getTarget() != null && !Main.canSee(this.getTarget(), this)) {
			this.rushTick = 5;
		}
		if (this.rushTick > 0 && this.getTarget() != null) {
			this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
			this.lookAt(this.getTarget(), 360.0f, 360.0f);
			this.RushAttack();
		}
		if (this.getRandom().nextInt(20) == 1 && rushTick <= -60 || rushTick <= -120) {
			if (this.isAlive() && !this.isPassenger() && InventoryEntity.isSpear(this, this.getUseItem()) && this.isAggressive() && this.getTarget() != null && Main.canSee(this.getTarget(), this)) {
				this.rushTick = 30;
			}
		}
	}

	public boolean RushAttack() {
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 3; ++i) {
				Vec3 vec3 = this.getDeltaMovement();
				serverLevel.sendParticles(ParticleTypes.FALLING_OBSIDIAN_TEAR, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, vec3.x, vec3.y - 0.1, vec3.z, 0);
			}
			for (int i = 0; i < 6; ++i) {
				Vec3 vec3 = this.getDeltaMovement();
				serverLevel.sendParticles(ParticleTypes.LAVA, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, vec3.x, vec3.y - 0.1, vec3.z, 0);
			}
		}
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = this.getRandom().nextInt(3, 6) / 30f;
		this.setOnGround(false);
		this.push(f3 *= f7 / f6 * 2, f4 *= f7 / f6 * 2 + 0.01f, f5 *= f7 / f6 * 2);
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (this.getVehicle() instanceof BlackGoldWarHoglinEntity) {
			if (damageSource.is(DamageTypes.IN_WALL))
				return true;
		}
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damagesource, float amount) {
		if (isInvulnerableTo(damagesource)) {
			return super.hurt(damagesource, amount);
		}
		return super.hurt(damagesource, amount * (this.getVehicle() instanceof BlackGoldWarHoglinEntity ? 0.5f : 1.0f));
	}


	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(3);
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.COMMAND && mobSpawnType != MobSpawnType.JOCKEY) {
			BlackGoldWarHoglinEntity blackGoldWarHoglin = BGAEntityType.BLACK_GOLD_WAR_HOGLIN.get().spawn(serverLevelAccessor.getLevel(), BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			if (blackGoldWarHoglin != null) {
				blackGoldWarHoglin.setBaby(false);
				if (teams != null) {
					serverLevelAccessor.getLevel().getScoreboard().addPlayerToTeam(blackGoldWarHoglin.getStringUUID(), teams);
				}
				blackGoldWarHoglin.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0f);
				blackGoldWarHoglin.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, null, null);
				//鞍
				blackGoldWarHoglin.equipSaddle(SoundSource.NEUTRAL);
				//盔甲
				blackGoldWarHoglin.equipArmor(null, new ItemStack(JerotesItems.NETHERITE_GIANT_BEAST_ARMOR.get()));
				blackGoldWarHoglin.setEntityNeedDiscardTick(this.getEntityNeedDiscardTick());
				blackGoldWarHoglin.setSelfPortal(this.getSelfPortal());
				if (!this.level().isClientSide()) {
					blackGoldWarHoglin.setBaby(false);
					this.startRiding(blackGoldWarHoglin);
				}
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(BGAItems.BLACK_GOLD_LANCE.get());
	}

}