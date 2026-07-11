package com.jerotes.blackgoldalliance.entity.Piglin;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class PiglinRaiderBruteEntity extends PiglinRaiderEntity implements EliteEntity {
	public PiglinRaiderBruteEntity(EntityType<? extends PiglinRaiderBruteEntity> type, Level world) {
		super(type, world);
		xpReward = 30;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.365);
		builder = builder.add(Attributes.MAX_HEALTH, 50);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(Items.NETHERITE_HELMET), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(Items.NETHERITE_CHESTPLATE), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(Items.NETHERITE_LEGGINGS), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(Items.NETHERITE_BOOTS), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		if (randomSource.nextFloat() < 0.3f || equipmentSlot == EquipmentSlot.HEAD) {
			this.setItemSlot(equipmentSlot, itemStack);
		}
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}


	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(3);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom < 0.3f) {
			return new ItemStack(Items.NETHERITE_AXE);
		}
		return new ItemStack(Items.GOLDEN_AXE);
	}
}