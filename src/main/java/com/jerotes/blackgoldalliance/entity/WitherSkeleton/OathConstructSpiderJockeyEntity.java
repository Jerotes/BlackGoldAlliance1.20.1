package com.jerotes.blackgoldalliance.entity.WitherSkeleton;

import com.jerotes.blackgoldalliance.entity.Animal.OathConstructSpiderEntity;
import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.blackgoldalliance.init.BGAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;

public class OathConstructSpiderJockeyEntity extends OathWitherSkeletonEntity {
	public OathConstructSpiderJockeyEntity(EntityType<? extends OathConstructSpiderJockeyEntity> type, Level world) {
		super(type, world);
		xpReward = 20;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.325);
		builder = builder.add(Attributes.MAX_HEALTH, 26);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
		return builder;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(BGAItems.OATH_WITHER_SQUIRE_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(BGAItems.OATH_WITHER_SQUIRE_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(BGAItems.OATH_WITHER_SQUIRE_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(BGAItems.OATH_WITHER_SQUIRE_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
	if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.COMMAND && mobSpawnType != MobSpawnType.JOCKEY) {
			OathConstructSpiderEntity oathConstructSpiderEntity = BGAEntityType.OATH_CONSTRUCT_SPIDER.get().spawn(serverLevelAccessor.getLevel(), BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			if (oathConstructSpiderEntity != null) {
				oathConstructSpiderEntity.setBaby(false);
				if (teams != null) {
					serverLevelAccessor.getLevel().getScoreboard().addPlayerToTeam(oathConstructSpiderEntity.getStringUUID(), teams);
				}
				oathConstructSpiderEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0f);
				oathConstructSpiderEntity.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, null, null);
				//鞍
				oathConstructSpiderEntity.equipSaddle(SoundSource.NEUTRAL);
//				//盔甲
//				oathConstructSpiderEntity.equipArmor(null, new ItemStack(JerotesItems.NETHERITE_GIANT_BEAST_ARMOR.get()));
				if (!this.level().isClientSide()) {
					oathConstructSpiderEntity.setBaby(false);
					this.startRiding(oathConstructSpiderEntity);
				}
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(BGAItems.OATH_STONE_SWORD.get());
	}
}