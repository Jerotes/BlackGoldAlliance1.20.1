package com.jerotes.blackgoldalliance.entity.Other;

import com.jerotes.blackgoldalliance.entity.Animal.AnimalHoglinEntity;
import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldStepperEntity;
import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import com.jerotes.blackgoldalliance.entity.Interface.PortalPointChangeEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderEntity;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import com.jerotes.blackgoldalliance.init.BGAParticleTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.ParticlesUse;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class PortalPointEntity extends Entity implements OwnableEntity {
	private static final EntityDataAccessor<String> ENTITY_TYPE = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Float> SELF_X = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SELF_Y = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SELF_Z = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_ADD_X = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_ADD_Y = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_ADD_Z = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> ENTITY_NEED_DISCARD = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> BLACK_GOLD_ALLIANCE = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ENTITY_NEED_DISCARD_TICK = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> SELF_PORTAL = SynchedEntityData.defineId(PortalPointEntity.class, EntityDataSerializers.OPTIONAL_UUID);

	public PortalPointEntity(EntityType<? extends PortalPointEntity> entityType, Level level) {
		super(entityType, level);
		this.noCulling = true;
		this.setSelfX((float) this.getX());
		this.setSelfY((float) this.getY());
		this.setSelfZ((float) this.getZ());
	}

	public boolean displayFireAnimation() {
		return false;
	}
	public boolean isInWall() {
		return false;
	}
	protected final RandomSource random = RandomSource.create();
	public RandomSource getRandom() {
		return this.random;
	}
	@Override
	public boolean canChangeDimensions() {
		return false;
	}
	public void lookAt(Vec3 vec3, float f, float f2) {
		double d0 = vec3.x() - this.getX();
		double d2 = vec3.z() - this.getZ();
		double d1 = vec3.y() - this.getY() + 0.5f;
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		float f3 = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
		float f4 = (float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
		this.setXRot(this.rotlerp(this.getXRot(), f4, f2));
		this.setYRot(this.rotlerp(this.getYRot(), f3, f));
	}
	private float rotlerp(float f, float f2, float f3) {
		float f4 = Mth.wrapDegrees(f2 - f);
		if (f4 > f3) {
			f4 = f3;
		}
		if (f4 < -f3) {
			f4 = -f3;
		}
		return f + f4;
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
	public void setOwner(@Nullable LivingEntity livingEntity) {
		this.owner = livingEntity;
		this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
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
	public Team getTeam() {
		LivingEntity livingEntity;
		if (this.getOwner() != null && (livingEntity = this.getOwner()) != null) {
			return livingEntity.getTeam();
		}
		return super.getTeam();
	}
	public void setEntityType(String string){
		this.getEntityData().set(ENTITY_TYPE, string);
	}
	public String getEntityType() {return this.getEntityData().get(ENTITY_TYPE);}
	public void setSelfX(float f){
		this.getEntityData().set(SELF_X, f);
	}
	public float getSelfX() {return this.getEntityData().get(SELF_X);}
	public void setSelfY(float f){
		this.getEntityData().set(SELF_Y, f);
	}
	public float getSelfY() {
		return this.getEntityData().get(SELF_Y);
	}
	public void setSelfZ(float f){
		this.getEntityData().set(SELF_Z, f);
	}
	public float getSelfZ() {
		return this.getEntityData().get(SELF_Z);
	}
	public void setTargetAddX(float f){
		this.getEntityData().set(TARGET_ADD_X, f);
	}
	public float getTargetAddX() {return this.getEntityData().get(TARGET_ADD_X);}
	public void setTargetAddY(float f){
		this.getEntityData().set(TARGET_ADD_Y, f);
	}
	public float getTargetAddY() {
		return this.getEntityData().get(TARGET_ADD_Y);
	}
	public void setTargetAddZ(float f){
		this.getEntityData().set(TARGET_ADD_Z, f);
	}
	public float getTargetAddZ() {
		return this.getEntityData().get(TARGET_ADD_Z);
	}
	public void setEntityNeedDiscard(boolean bl){
		this.getEntityData().set(ENTITY_NEED_DISCARD, bl);
	}
	public boolean isEntityNeedDiscard() {
		return this.getEntityData().get(ENTITY_NEED_DISCARD);
	}
	public void setBlackGoldAlliance(boolean bl){
		this.getEntityData().set(BLACK_GOLD_ALLIANCE, bl);
	}
	public boolean isBlackGoldAlliance() {
		return this.getEntityData().get(BLACK_GOLD_ALLIANCE);
	}
	public void setEntityNeedDiscardTick(int n){
		this.getEntityData().set(ENTITY_NEED_DISCARD_TICK, n);
	}
	public int getEntityNeedDiscardTick() {
		return this.getEntityData().get(ENTITY_NEED_DISCARD_TICK);
	}
	public UUID getSelfPortal() {
		Optional<UUID> optionalUUID = this.getEntityData().get(SELF_PORTAL);
		return optionalUUID.orElseGet(this::getUUID);
	}
	public void setSelfPortal(UUID uuid) {
		this.getEntityData().set(SELF_PORTAL, Optional.of(uuid));
	}
	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {
		if (this.ownerUUID != null) {
			compoundTag.putUUID("Owner", this.ownerUUID);
		}
		compoundTag.putInt("TickCount", tickCount);
		compoundTag.putString("EntityType", this.getEntityType());
		compoundTag.putFloat("SelfX", this.getSelfX());
		compoundTag.putFloat("SelfY", this.getSelfY());
		compoundTag.putFloat("SelfZ", this.getSelfZ());
		compoundTag.putFloat("TargetAddX", this.getTargetAddX());
		compoundTag.putFloat("TargetAddY", this.getTargetAddY());
		compoundTag.putFloat("TargetAddZ", this.getTargetAddZ());
		compoundTag.putBoolean("EntityNeedDiscard", this.isEntityNeedDiscard());
		compoundTag.putBoolean("BlackGoldAlliance", this.isBlackGoldAlliance());
		compoundTag.putInt("EntityNeedDiscardTick", this.getEntityNeedDiscardTick());
		compoundTag.putUUID("SelfPortal", this.getSelfPortal());
	}
	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {
		if (compoundTag.hasUUID("Owner")) {
			this.ownerUUID = compoundTag.getUUID("Owner");
		}
		this.tickCount = compoundTag.getInt("TickCount");
		this.setEntityType(compoundTag.getString("EntityType"));
		this.setSelfX(compoundTag.getFloat("SelfX"));
		this.setSelfY(compoundTag.getFloat("SelfY"));
		this.setSelfZ(compoundTag.getFloat("SelfZ"));
		this.setTargetAddX(compoundTag.getFloat("TargetAddX"));
		this.setTargetAddY(compoundTag.getFloat("TargetAddY"));
		this.setTargetAddZ(compoundTag.getFloat("TargetAddZ"));
		this.setEntityNeedDiscard(compoundTag.getBoolean("EntityNeedDiscard"));
		this.setBlackGoldAlliance(compoundTag.getBoolean("BlackGoldAlliance"));
		this.setEntityNeedDiscardTick(compoundTag.getInt("EntityNeedDiscardTick"));
		if (compoundTag.hasUUID("SelfPortal")) {
			this.setSelfPortal(compoundTag.getUUID("SelfPortal"));
		}
	}
	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(ENTITY_TYPE, "blackgoldalliance:piglin_raider");
		this.getEntityData().define(TARGET_ADD_X, 0f);
		this.getEntityData().define(TARGET_ADD_Y, 0f);
		this.getEntityData().define(TARGET_ADD_Z, 0f);
		this.getEntityData().define(SELF_X, 0f);
		this.getEntityData().define(SELF_Y, 0f);
		this.getEntityData().define(SELF_Z, 0f);
		this.getEntityData().define(ENTITY_NEED_DISCARD, false);
		this.getEntityData().define(BLACK_GOLD_ALLIANCE, false);
		this.getEntityData().define(ENTITY_NEED_DISCARD_TICK, 0);
		this.getEntityData().define(SELF_PORTAL, Optional.of(this.getUUID()));
	}
	protected float getMoveSpeed() {
		return 1f;
	}

	@Override
	public void tick() {
		super.tick();
		this.noPhysics = true;
		this.setNoGravity(true);
		if (!this.level().isClientSide()) {
			this.setEntityNeedDiscardTick(Math.max(0, this.getEntityNeedDiscardTick() - 1));
		}
		//粒子效果
		if (this.isAlive()) {
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int n = 0; n < 3; ++n) {
					serverLevel.sendParticles(BGAParticleTypes.PORTAL_POINT.get(), this.getX(), this.getY(0.2f), this.getZ(), 0, this.getRandom().nextFloat() / 5 - 0.1f, 0.15 + this.getRandom().nextFloat() / 5, this.getRandom().nextFloat() / 5 - 0.1f, 0.05);
				}
			}
		}

		float targetX = this.getSelfX() + this.getTargetAddX();
		float targetY = this.getSelfY() + this.getTargetAddY();
		float targetZ = this.getSelfZ() + this.getTargetAddZ();
		//前往目标地点
		{
			{
				double dx = targetX - this.getX();
				double dy = targetY - this.getY();
				double dz = targetZ - this.getZ();
				double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

				if (distance > 0.01) {
					double step = getMoveSpeed() / 2.0;
					if (step > distance) step = distance;
					double moveX = dx / distance * step;
					double moveY = dy / distance * step;
					double moveZ = dz / distance * step;
					this.setPos(this.getX() + moveX, this.getY() + moveY, this.getZ() + moveZ);
				}
				this.lookAt(new Vec3(targetX, targetY, targetZ), 360f, 360f);
			}
		}
		//完成接触进行生成
		{
			if (this.distanceToSqr(new Vec3(targetX, targetY, targetZ)) <= 1f || this.tickCount > 20 * 60) {
				summonEntity();
				this.discard();
			}
		}

	}

	public void summonEntity() {
		EntityType entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(this.getEntityType()));
		if (this.level() instanceof ServerLevel serverLevel) {
			if (entityType == null) {
				return;
			}
			if (!(serverLevel.getEntity(this.getSelfPortal()) instanceof PiglinRaidNetherPortalEntity piglinRaidNetherPortalEntity &&
					piglinRaidNetherPortalEntity.isAlive() && piglinRaidNetherPortalEntity.deathTime <= 0)) {
				return;
			}
			float targetX = this.getSelfX() + this.getTargetAddX();
			float targetY = this.getSelfY() + this.getTargetAddY();
			float targetZ = this.getSelfZ() + this.getTargetAddZ();
			Entity entity = entityType.create(serverLevel);
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			if (entity != null) {
				entity.moveTo(new Vec3(targetX, targetY, targetZ));
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(entity.getStringUUID(), teams);
				}
				if (entity instanceof Mob mob) {
					//定时消失相关
					if (this.isEntityNeedDiscard() && !(entity instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null)) {
						if (entity instanceof BlackGoldPiglinEntity blackGoldPiglinEntity) {
							blackGoldPiglinEntity.setEntityNeedDiscardTick(this.getEntityNeedDiscardTick() + 65);
							blackGoldPiglinEntity.setSelfPortal(this.getSelfPortal());
							blackGoldPiglinEntity.setCanPickUpLoot(false);
						}
						else if (entity instanceof PiglinRaiderEntity piglinRaiderEntity) {
							piglinRaiderEntity.setEntityNeedDiscardTick(this.getEntityNeedDiscardTick() + 65);
							piglinRaiderEntity.setSelfPortal(this.getSelfPortal());
							piglinRaiderEntity.setCanPickUpLoot(false);
						}
						else if (entity instanceof AnimalHoglinEntity animalHoglinEntity) {
							animalHoglinEntity.setEntityNeedDiscardTick(this.getEntityNeedDiscardTick() + 65);
							animalHoglinEntity.setSelfPortal(this.getSelfPortal());
							animalHoglinEntity.setCanPickUpLoot(false);
						}
						else if (entity instanceof BlackGoldStepperEntity blackGoldStepperEntity) {
							blackGoldStepperEntity.setEntityNeedDiscardTick(this.getEntityNeedDiscardTick() + 65);
							blackGoldStepperEntity.setSelfPortal(this.getSelfPortal());
							blackGoldStepperEntity.setCanPickUpLoot(false);
						}
					}
					mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					if (!mob.isInvisible()) {
						for (int i2 = 0; i2 < 32; ++i2) {
							serverLevel.sendParticles(BGAParticleTypes.PORTAL_POINT.get(), mob.getRandomX(0.5), mob.getRandomY(), mob.getRandomZ(0.5), 0, mob.getRandom().nextGaussian(), 0.0, mob.getRandom().nextGaussian(), 0);
						}
					}
					if ((EntityFactionFind.isPiglin(mob) ||
							mob instanceof HoglinBase ||
							mob instanceof ZombifiedPiglin)) {
						if (!mob.level().isClientSide()) {
							mob.addEffect(new MobEffectInstance(BGAMobEffects.PIGLIN_BLESSING.get(), 12000, 0, false, false), this);
						}
					}
					if (entity instanceof PortalPointChangeEntity portalPointChangeEntity) {
						portalPointChangeEntity.setPortalPointTick(20);
					}
					ParticlesUse.summonParticle(serverLevel, entity, entity.getX(), entity.getY(), entity.getZ(),
							0x3f0461, isBlackGoldAlliance() ? 0xffca00 : 0x8a16cf);
				}
				serverLevel.addFreshEntity(entity);
			}
			if (!this.isSilent()) {
				this.playSound(JerotesSoundEvents.SPELL, 0.5f, 1.0f);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), GameEvent.Context.of(this));
		}
	}
}