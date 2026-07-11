package com.jerotes.blackgoldalliance.entity.Other;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.block.NetherSiphonCoreEntity;
import com.jerotes.blackgoldalliance.entity.Part.NetherSiphonCoreForcePart;
import com.jerotes.jerotes.control.NoRotationControl;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NetherSiphonCoreForceEntity extends Mob implements JerotesEntity, TraceableEntity, OwnableEntity {
	private static final EntityDataAccessor<Integer> START_TICK = SynchedEntityData.defineId(NetherSiphonCoreForceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(NetherSiphonCoreForceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(NetherSiphonCoreForceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> START_X = SynchedEntityData.defineId(NetherSiphonCoreForceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> START_Y = SynchedEntityData.defineId(NetherSiphonCoreForceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> START_Z = SynchedEntityData.defineId(NetherSiphonCoreForceEntity.class, EntityDataSerializers.INT);
	public AnimationState startAnimationState = new AnimationState();
	public AnimationState stopAnimationState = new AnimationState();
	public final NetherSiphonCoreForcePart main;
	public final NetherSiphonCoreForcePart[] allParts;

	public NetherSiphonCoreForceEntity(EntityType<? extends NetherSiphonCoreForceEntity> type, Level world) {
		super(type, world);
		this.noCulling = true;
		this.startAnimationState.start(this.tickCount);
		main = new NetherSiphonCoreForcePart(this, "main", 7.5f, 7.5f);
		allParts = new NetherSiphonCoreForcePart[]{main};
	}
	@Override
	protected void registerGoals() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 200);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	@Override
	public void remove(@NotNull RemovalReason reason) {
		super.remove(reason);
		if (allParts != null) {
			for (NetherSiphonCoreForcePart part : allParts) {
				part.remove(RemovalReason.KILLED);
			}
		}
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GLASS_BREAK;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.GLASS_BREAK;
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public boolean isOnFire() {
		return false;
	}
	@Override
	public boolean canFreeze() {
		return false;
	}
	@Override
	public boolean isFreezing() {
		return false;
	}
	@Override
	public boolean canCollideWith(Entity entity) {
		if (entity instanceof NetherSiphonCoreForcePart) {
			return false;
		}
		return entity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(entity) && this.isAlive();
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
	}
	@Override
	public void setDeltaMovement(Vec3 vec3) {
		super.setDeltaMovement(new Vec3(0, 0, 0));
	}
	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		return new NoRotationControl(this);
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerUUID;

	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	@Nullable
	@Override
	public LivingEntity getOwner() {
		Entity entity;
		if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
			this.owner = (LivingEntity)entity;
		}
		return this.owner;
	}
	public void setOwner(@Nullable LivingEntity livingEntity) {
		this.owner = livingEntity;
		this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (this.getOwner() != null) {
			LivingEntity livingEntity = this.getOwner();
			if (entity == livingEntity) {
				return true;
			}
			if (livingEntity != null) {
				return livingEntity.isAlliedTo(entity);
			}
		}
		return super.isAlliedTo(entity);
	}
	@Override
	//1.20.4↑//
	//public PlayerTeam getTeam() {
	//1.20.1//
	public Team getTeam() {
		LivingEntity livingEntity;
		if (this.getOwner() != null && (livingEntity = this.getOwner()) != null) {
			return livingEntity.getTeam();
		}
		return super.getTeam();
	}
	//动画
	public void setAnimTick(int n){
		this.getEntityData().set(ANIM_TICK, n);
	}
	public int getAnimTick(){
		return this.getEntityData().get(ANIM_TICK);
	}
	public void setAnimationState(String input) {
		this.setAnimationState(this.getAnimationState(input));
	}
	public void setAnimationState(int id) {
		this.entityData.set(ANIM_STATE, id);
	}
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "start")){
			return 1;
		}
		else if (Objects.equals(animation, "stop")){
			return 2;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.startAnimationState);
		list.add(this.stopAnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception && state != startAnimationState) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation(){
		for (AnimationState state : this.getAllAnimations()){
			state.stop();
		}
	}
	//
	public void setStartTick(int n){
		this.getEntityData().set(START_TICK, n);
	}
	public int getStartTick(){
		return this.getEntityData().get(START_TICK);
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
		compoundTag.putInt("StartTick", this.getStartTick());
		if (this.ownerUUID != null) {
			compoundTag.putUUID("Owner", this.ownerUUID);
		}
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("XStart", getStartX());
		compoundTag.putInt("YStart", getStartY());
		compoundTag.putInt("ZStart", getStartZ());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setStartTick(compoundTag.getInt("StartTick"));
		if (compoundTag.hasUUID("Owner")) {
			this.ownerUUID = compoundTag.getUUID("Owner");
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setStartPos(new BlockPos((int) compoundTag.getInt("XStart"), (int) compoundTag.getInt("YStart"), (int) compoundTag.getInt("ZStart")));
		float facing = 0f;
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		this.yBodyRotO = facing;
		this.yBodyRot = facing;
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(START_TICK, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(START_X, 0);
		this.getEntityData().define(START_Y, -9999);
		this.getEntityData().define(START_Z, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.startAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.startAnimationState);
						break;
					case 2:
						this.stopAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.stopAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public InteractionResult mobInteract(Player p_21472_, InteractionHand p_21473_) {
		return InteractionResult.PASS;
	}

	@Override
	public boolean isMultipartEntity() {
		return true;
	}
	@Override
	public PartEntity<?>[] getParts() {
		return allParts;
	}
	public NetherSiphonCoreForcePart[] getSubEntities() {
		return this.allParts;
	}
	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
		super.recreateFromPacket(clientboundAddEntityPacket);
		NetherSiphonCoreForcePart[] NetherSiphonCoreForceParts = this.getSubEntities();
		for (int i = 0; i < NetherSiphonCoreForceParts.length; ++i) {
			NetherSiphonCoreForceParts[i].setId(i + 1 + clientboundAddEntityPacket.getId());
		}
	}
	private void tickMultipart() {
		Vec3[] avector3d = new Vec3[this.allParts.length];
		for (int j = 0; j < this.allParts.length; ++j) {
			avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
		}
		Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
		this.main.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0f, 0), 0, this.yBodyRot).add(center));
		for (int l = 0; l < this.allParts.length; ++l) {
			this.allParts[l].xo = avector3d[l].x;
			this.allParts[l].yo = avector3d[l].y;
			this.allParts[l].zo = avector3d[l].z;
			this.allParts[l].xOld = avector3d[l].x;
			this.allParts[l].yOld = avector3d[l].y;
			this.allParts[l].zOld = avector3d[l].z;
		}
	}
	private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
		return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
	}
	@Override
	public void tick() {
		tickMultipart();
		super.tick();
		this.setJumping(false);
		if (!this.level().isClientSide) {
			this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
			this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
			this.goalSelector.setControlFlag(Goal.Flag.TARGET, false);
			this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
		}

		if (getStartY() != -9999 && this.tickCount > 20) {
			if (this.level() instanceof ServerLevel serverLevel) {
				if (serverLevel.getBlockEntity(new BlockPos((int) getStartX(), (int) getStartY(), (int) getStartZ())) instanceof NetherSiphonCoreEntity netherSiphonCoreEntity) {
					if (!netherSiphonCoreEntity.alreadyRaid || !netherSiphonCoreEntity.isCooldown) {
						this.hurt(this.damageSources().genericKill(), 10240);
						BGA.LOGGER.info("NetherSiphonCoreForceEntity Destroy Because Self Other");
					}
				}
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.clearFire();
		this.setNoGravity(true);
		if (!this.level().isClientSide()) {
			this.setStartTick(this.getStartTick() + 1);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		//清除冻结
		if (this.getTicksFrozen() > 0) {
			this.setTicksFrozen(0);
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.CRAMMING)
				|| damageSource.is(DamageTypeTags.IS_DROWNING)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| damageSource.is(DamageTypeTags.IS_FIRE)
				|| damageSource.is(DamageTypeTags.IS_LIGHTNING)
				|| damageSource.is(DamageTypeTags.IS_FREEZING)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| damageSource.is(DamageTypes.WITHER)
				|| damageSource.is(DamageTypes.WITHER_SKULL)
				|| damageSource.is(DamageTypes.FALLING_BLOCK)
				|| damageSource.is(DamageTypes.FALLING_ANVIL)
				|| damageSource.is(DamageTypes.FALLING_STALACTITE))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (amount < 2 && !(damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY))) {
			return false;
		}
		if (damageSource.getEntity() != null) {
			amount /= Mth.clamp(((damageSource.getEntity().distanceTo(this) + 5) / 5), 1, 5);
		}
		return super.hurt(damageSource, amount / (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) ? 1 : 10F));
	}
	public boolean hurtByPart(NetherSiphonCoreForcePart nethersiphoncoreforcepart, DamageSource damageSource, float f) {
		return this.hurt(damageSource, f);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.WITHER) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.DARKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void stopRiding() {
		super.stopRiding();
		float facing = 0f;
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		this.yBodyRotO = facing;
		this.yBodyRot = facing;
	}
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		float facing = 0f;
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		this.yBodyRotO = facing;
		this.yBodyRot = facing;
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0){
			this.setAnimTick(20);
			this.setAnimationState("stop");
		}
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.DISCARDED);
		}
	}
}
