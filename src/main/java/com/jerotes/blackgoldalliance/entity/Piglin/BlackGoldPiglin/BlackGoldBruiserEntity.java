package com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin;

import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.goal.BlackGoldPiglinFollowBossGoal;
import com.jerotes.blackgoldalliance.goal.ForceNearestAttackableTargetGoal;
import com.jerotes.blackgoldalliance.goal.PiglinFollowPortalGoal;
import com.jerotes.blackgoldalliance.goal.SwitchTargetToAllyTargetGoal;
import com.jerotes.blackgoldalliance.init.BGAParticleTypes;
import com.jerotes.blackgoldalliance.init.BGASoundEvents;
import com.jerotes.jerotes.control.GiantLookControl;
import com.jerotes.jerotes.control.GiantMoveControl;
import com.jerotes.jerotes.entity.Interface.BreakShieldEntity;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.StopLook;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BlackGoldBruiserEntity extends BlackGoldPiglinEntity implements EliteEntity, StopLook, BreakShieldEntity {
	public AnimationState idleBaseAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	public AnimationState danceAnimationState = new AnimationState();
	public AnimationState stompAnimationState = new AnimationState();
	public AnimationState fist1AnimationState = new AnimationState();
	public AnimationState fist2AnimationState = new AnimationState();
	public AnimationState fist3AnimationState = new AnimationState();
	public AnimationState palm1AnimationState = new AnimationState();
	public AnimationState palm2AnimationState = new AnimationState();
	public AnimationState palm3AnimationState = new AnimationState();
	public AnimationState rushAnimationState = new AnimationState();
	private static final EntityDataAccessor<Boolean> LOSS = SynchedEntityData.defineId(BlackGoldBruiserEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> STOMP_TICK = SynchedEntityData.defineId(BlackGoldBruiserEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STOMP_USE_TICK = SynchedEntityData.defineId(BlackGoldBruiserEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_TICK = SynchedEntityData.defineId(BlackGoldBruiserEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RUSH_USE_TICK = SynchedEntityData.defineId(BlackGoldBruiserEntity.class, EntityDataSerializers.INT);

	public BlackGoldBruiserEntity(EntityType<? extends BlackGoldBruiserEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.6f);
		xpReward = 80;
		this.moveControl = new GiantMoveControl(this);
		this.lookControl = new GiantLookControl(this, 1);
		this.setCanPickUpLoot(false);
		this.fixupDimensions();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 220);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 22f);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0f);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(this, 1.1, true));
		this.goalSelector.addGoal(3, new BlackGoldPiglinFollowBossGoal(this, 1.1f));
		this.goalSelector.addGoal(3, new PiglinFollowPortalGoal(this, 1.1f));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, AbstractPiglin.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 6.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<WitherSkeleton>(this, WitherSkeleton.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<WitherBoss>(this, WitherBoss.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
		this.targetSelector.addGoal(4, new ForceNearestAttackableTargetGoal<>(this, NetherSiphonCoreForceEntity.class, false, false));
		this.targetSelector.addGoal(1, new SwitchTargetToAllyTargetGoal(this, 32.0));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<BlackGoldPiglinEntity>(this, true));
	}
	@Override
	protected boolean isImmuneToZombification() {
		return true;
	}
	@Override
	public boolean isBaby() {
		return false;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	@Override
	public boolean isLeftHanded() {
		return false;
	}
	@Override
	public boolean canUseCrossbow() {
		return false;
	}
	@Override
	public boolean canUseRangeJavelin() {
		return false;
	}
	@Override
	public boolean canUseBow() {
		return false;
	}
	public float getVoicePitch() {
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.75f + 0.25f;
	}
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale(1f, ((3.65f - (0.045f) * (10 - getAttackAnim())) / 3.65f));
	}
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 3.5f - (0.045f) * (10 - getAttackAnim());
	}
	protected float nextStep() {
		return (float)(this.moveDist + 2);
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		if (this.getRushUseTick() <= 0) {
			if (this.getAttackAnim() < 5) {
				this.playSound(BGASoundEvents.BLACK_GOLD_BRUISER_WALK, 1.0f, 1.0f);
			} else {
				this.playSound(SoundEvents.PIGLIN_STEP, 1.0f, 1.0f);
			}
		}
	}
	@Override
	public void refreshDimensions() {
		double d = this.getX();
		double d2 = this.getY();
		double d3 = this.getZ();
		super.refreshDimensions();
		this.setPos(d, d2, d3);
	}
	public AABB getAttackBoundingBox() {
		Entity entity = this.getVehicle();
		AABB aabb;
		if (entity != null) {
			AABB aabb1 = entity.getBoundingBox();
			AABB aabb2 = this.getBoundingBox();
			aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
		} else {
			aabb = this.getBoundingBox();
		}
		AABB aabb1 = aabb.inflate(Math.sqrt((double)2.04F) - (double)0.6F, 0.0D, Math.sqrt((double)2.04F) - (double)0.6F);
		return aabb1.inflate(1.25d, 1.25d, 1.25d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().inflate(livingEntity.onGround() ? 0f : 0.75f).intersects(livingEntity.getBoundingBox());
	}
	@Override
	public boolean hasLineOfSight(Entity target) {
		if (target instanceof NetherSiphonCoreForceEntity netherSiphonCoreForceEntity) {
			return super.hasLineOfSight(target) || Main.canSee(netherSiphonCoreForceEntity,this);
		}
		return super.hasLineOfSight(target);
	}
	@Override
	public void travel(Vec3 dir) {
		super.travel(dir);
		if (this.specialAction() && !this.onGround() || this.getAttackTick() > 0) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.005d, 1d, 0.005d));
		}
	}
	@Override
	public void pushEntities() {
		if (this.getRushUseTick() <= 0 || this.getRushUseTick() > 70) {
			super.pushEntities();
		}
	}
	@Override
	public boolean canDisableShield() {
		return true;
	}
	@Override
	public int getShieldBreakStrength() {
		return 100;
	}
	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			player.disableShield(true);
		}
	}
	@Override
	public void lookAt(Entity entity, float f, float f2) {
		if (!this.stopLookTime()) {
			super.lookAt(entity,f,f2);
		}
	}
	@Override
	public void setXRot(float f) {
		if (!this.stopLookTime()) {
			super.setXRot(f);
		}
	}
	@Override
	public void setYRot(float f) {
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if (!this.stopLookTime()) {
			super.setYRot(f);
		}
		else if ("Rusher".equals(string)) {
			super.setYRot(this.getYHeadRot());
		}
	}
	@Override
	public void setYBodyRot(float f) {
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if (!this.stopLookTime()) {
			super.setYBodyRot(f);
		}
		else if ("Rusher".equals(string)) {
			super.setYRot(this.getYHeadRot());
		}
	}
	@Override
	public boolean selfChangeAttackAnim() {
		return true;
	}
	@Override
	public boolean selfUseAnim() {
		return true;
	}
	@Override
	public boolean selfAttackAbout() {
		return true;
	}

	private double blockDestroyTick;
	public float rushFindXRot;
	public float rushFindYRot;
	public void setLoss(boolean bl){
		this.getEntityData().set(LOSS, bl);
	}
	public boolean isLoss(){
		return this.getEntityData().get(LOSS);
	}
	public void setStompTick(int n){
		this.getEntityData().set(STOMP_TICK, n);
	}
	public int getStompTick(){
		return this.getEntityData().get(STOMP_TICK);
	}
	public void setStompUseTick(int n){
		this.getEntityData().set(STOMP_USE_TICK, n);
	}
	public int getStompUseTick(){
		return this.getEntityData().get(STOMP_USE_TICK);
	}
	public void setRushTick(int n){
		this.getEntityData().set(RUSH_TICK, n);
	}
	public int getRushTick(){
		return this.getEntityData().get(RUSH_TICK);
	}
	public void setRushUseTick(int n){
		this.getEntityData().set(RUSH_USE_TICK, n);
	}
	public int getRushUseTick(){
		return this.getEntityData().get(RUSH_USE_TICK);
	}
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "dead")){
			return 1;
		}
		else if (Objects.equals(animation, "dance")){
			return 2;
		}
		else if (Objects.equals(animation, "stomp")){
			return 3;
		}
		else if (Objects.equals(animation, "fist1")){
			return 4;
		}
		else if (Objects.equals(animation, "fist2")){
			return 5;
		}
		else if (Objects.equals(animation, "fist3")){
			return 6;
		}
		else if (Objects.equals(animation, "palm1")){
			return 7;
		}
		else if (Objects.equals(animation, "palm2")){
			return 8;
		}
		else if (Objects.equals(animation, "palm3")){
			return 9;
		}
		else if (Objects.equals(animation, "rush")){
			return 10;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.deadAnimationState);
		list.add(this.danceAnimationState);
		list.add(this.stompAnimationState);
		list.add(this.fist1AnimationState);
		list.add(this.fist2AnimationState);
		list.add(this.fist3AnimationState);
		list.add(this.palm1AnimationState);
		list.add(this.palm2AnimationState);
		list.add(this.palm3AnimationState);
		list.add(this.rushAnimationState);
		return list;
	}
	public boolean specialAction() {
		return this.getStompUseTick() > 0 || this.getRushUseTick() > 0;
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putBoolean("IsLoss", this.isLoss());
		compoundTag.putInt("StompTick", this.getStompTick());
		compoundTag.putInt("StompUseTick", this.getStompUseTick());
		compoundTag.putInt("RushTick", this.getRushTick());
		compoundTag.putInt("RushUseTick", this.getRushUseTick());
		compoundTag.putDouble("RushFindXRot", this.rushFindXRot);
		compoundTag.putDouble("RushFindYRot", this.rushFindYRot);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.setLoss(compoundTag.getBoolean("IsLoss"));
		this.setStompTick(compoundTag.getInt("StompTick"));
		this.setStompUseTick(compoundTag.getInt("StompUseTick"));
		this.setRushTick(compoundTag.getInt("RushTick"));
		this.setRushUseTick(compoundTag.getInt("RushUseTick"));
		this.rushFindXRot = compoundTag.getFloat("RushFindXRot");
		this.rushFindYRot = compoundTag.getFloat("RushFindYRot");
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(LOSS, false);
		this.getEntityData().define(STOMP_TICK, 800);
		this.getEntityData().define(STOMP_USE_TICK, 0);
		this.getEntityData().define(RUSH_TICK, 450);
		this.getEntityData().define(RUSH_USE_TICK, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ATTACK_ANIM.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)) {
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
					case 2:
						this.danceAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.danceAnimationState);
						break;
					case 3:
						this.stompAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.stompAnimationState);
						break;
					case 4:
						this.fist1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.fist1AnimationState);
						break;
					case 5:
						this.fist2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.fist2AnimationState);
						break;
					case 6:
						this.fist3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.fist3AnimationState);
						break;
					case 7:
						this.palm1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.palm1AnimationState);
						break;
					case 8:
						this.palm2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.palm2AnimationState);
						break;
					case 9:
						this.palm3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.palm3AnimationState);
						break;
					case 10:
						this.rushAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.rushAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if (this.getTarget() == null && this.isAlive() && !this.specialAction()) {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
			}
		}
		else {
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 15f, 15f);
				this.lookAt(this.getTarget(), 15f, 15f);
			}
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.max(this.getAttackAnim() - 2, 0));
			}
		}
		if ("Rusher".equals(string)) {
			if (!this.stopLookTime()) {
				if (this.getTarget() != null) {
					this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
					this.lookAtRusher(this.getTarget(), 360f, 360f);
				}
			}
			this.setYBodyRot(this.getYHeadRot());
			this.setYRot(this.getYHeadRot());
		}
		//破坏方块
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}
		{
			if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
				boolean blockDestroy = Main.BlockDestroy(this, 5f);
				if (blockDestroy) {
					this.blockDestroyTick = 40;
				}
				if (!this.level().isClientSide) {
					if (!canBlockDestroy && this.onGround()) {
						this.jumpFromGround();
					}
				}
			}
		}
		this.idleBaseAnimationState.startIfStopped(this.tickCount);
		//近战攻击
		{
			if (this.isAlive()) {
				//驱邪践踏
				{
					if (this.isAggressive() && this.getAttackTick() <= 0 && !"Rusher".equals(string) && !this.specialAction() && this.getStompTick() >= 800 && this.getTarget() != null && this.getTarget() instanceof LivingEntity) {
						if (!this.level().isClientSide()) {
							this.setStompTick(0);
							this.setStompUseTick(50);
						}
					}
					if (this.getStompUseTick() > 0) {
						this.getNavigation().stop();
						if (this.getStompUseTick() == 50) {
							if (!this.level().isClientSide()) {
								this.setStompTick(0);
								this.setAnimTick(50);
								this.setAnimationState("stomp");
							}
							if (this.level() instanceof ServerLevel serverLevel) {
								if (!this.isInvisible()) {
									serverLevel.sendParticles(BGAParticleTypes.EXORCISM_STOMP_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
								}
							}
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							float renderYawOffset = this.yBodyRot;
							//左
							if (this.getStompUseTick() == 36) {
								float angleYaw = (0f) * ((float) Math.PI / 180.0F);
								Vec3 leftPos = new Vec3(this.xo + 1.5f * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)), this.yo, this.zo + 1.5f * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
								Main.spawnUnevenBlockByPos(serverLevel, BlockPos.containing(leftPos).below(), 3);
							}
							//右
							if (this.getStompUseTick() == 11) {
								float angleYaw = (180.0f) * ((float) Math.PI / 180.0F);
								Vec3 rightPos = new Vec3(this.xo + 1.5f * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)), this.yo, this.zo + 1.5f * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
								Main.spawnUnevenBlockByPos(serverLevel,  BlockPos.containing(rightPos).below(), 3);
							}
						}
						if (this.getStompUseTick() == 35 || this.getStompUseTick() == 10) {
							if (!this.level().isClientSide()) {
								this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 240, 1), this);
								this.setAttackUse(5);
								trueHurt();
								List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8.0, 8.0, 8.0));
								for (LivingEntity shake : listShake) {
									if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
										continue;
									if (!shake.level().isClientSide()) {
										shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
									}
								}
							}
						}
					}
				}
				//急速冲撞
				{
					if (this.isAggressive() && this.getAttackTick() <= 0 && this.getRushUseTick() <= -2 && (this.getRushTick() >= 900 && this.spellNeed(0, 18, 1) || "Rusher".equals(string)) && !this.specialAction() && this.getTarget() != null && this.getTarget() instanceof LivingEntity) {
						if (!this.level().isClientSide()) {
							this.setRushTick(0);
							this.setRushUseTick(80);
						}
					}
					if (this.getRushUseTick() > 0) {
						this.getNavigation().stop();
						if (this.getRushUseTick() == 80) {
							if (!this.level().isClientSide()) {
								this.setRushTick(0);
								this.setAnimTick(80);
								this.setAnimationState("rush");
							}
							if (this.level() instanceof ServerLevel serverLevel) {
								if (!this.isInvisible()) {
									serverLevel.sendParticles(BGAParticleTypes.RAPID_IMPACT_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
								}
							}
						}
						if (this.getRushUseTick() >= 65) {
							if (this.getTarget() != null) {
								this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
								this.lookAt(this.getTarget(), 360f, 360f);
							}
						}
						else if (this.getRushUseTick() > 10) {
							RushAttack();
						}
						if (this.getRushUseTick() == 68) {
							if (this.getTarget() != null) {
								this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
								this.lookAt(this.getTarget(), 360.0f, 360.0f);
							}
							this.rushFindXRot = this.getXRot();
							this.rushFindYRot = this.yHeadRot;
						}
						if (this.getRushUseTick() == 65) {
							this.rushFindXRot = this.getXRot();
							this.rushFindYRot = this.yHeadRot;
						}
						if (this.getRushUseTick() == 63 || this.getRushUseTick() == 6 || this.getRushUseTick() == 3 || this.getRushUseTick() == 1) {
							if (this.level() instanceof ServerLevel serverLevel) {
								float renderYawOffset = this.yBodyRot;
								float angleYaw = (0f) * ((float) Math.PI / 180.0F);
								Vec3 leftPos = new Vec3(this.xo + 1.5f * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)), this.yo, this.zo + 1.5f * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
								float angleYaw2 = (180.0f) * ((float) Math.PI / 180.0F);
								Vec3 rightPos = new Vec3(this.xo + 1.5f * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw2)), this.yo, this.zo + 1.5f * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw2)));
								Main.spawnUnevenBlockByPos(serverLevel, BlockPos.containing(leftPos).below(), 3);
								Main.spawnUnevenBlockByPos(serverLevel, BlockPos.containing(rightPos).below(), 3);
							}
							if (!this.level().isClientSide()) {
								this.setAttackUse(6);
								trueHurt();
								List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8.0, 8.0, 8.0));
								for (LivingEntity shake : listShake) {
									if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
										continue;
									if (!shake.level().isClientSide()) {
										shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
									}
								}
							}
						}
					}
				}
				//攻击
				{
					if (this.getAttackUse() == 1) {
						if (this.getAttackTick() == 25) {
							this.trueHurt();
						}
						if (this.getAttackTick() > 30) {
							if (this.getTarget() != null) {
								this.getLookControl().setLookAt(this.getTarget(), 30f, 30f);
								this.lookAt(this.getTarget(), 30f, 30f);
							}
						}
					}
					else if (this.getAttackUse() == 2) {
						if (this.getAttackTick() == 35 || this.getAttackTick() == 25) {
							this.trueHurt();
						}
						if (this.getAttackTick() > 40) {
							if (this.getTarget() != null) {
								this.getLookControl().setLookAt(this.getTarget(), 30f, 30f);
								this.lookAt(this.getTarget(), 30f, 30f);
							}
						}
					}
					else if (this.getAttackUse() == 3) {
						if (this.getAttackTick() == 22) {
							this.trueHurt();
						}
						if (this.getAttackTick() > 25) {
							if (this.getTarget() != null) {
								this.getLookControl().setLookAt(this.getTarget(), 30f, 30f);
								this.lookAt(this.getTarget(), 30f, 30f);
							}
						}
					}
					else if (this.getAttackUse() == 4) {
						if (this.getAttackTick() == 22) {
							this.trueHurt();
						}
						if (this.getAttackTick() > 25) {
							if (this.getTarget() != null) {
								this.getLookControl().setLookAt(this.getTarget(), 30f, 30f);
								this.lookAt(this.getTarget(), 30f, 30f);
							}
						}
					}
				}
			}
			if (!this.level().isClientSide()) {
				this.setAttackTick(Math.max(-30, this.getAttackTick() - 1));
				this.setStompTick(Math.min(800, this.getStompTick() + 1));
				this.setStompUseTick(Math.max(0, this.getStompUseTick() - 1));
				this.setRushTick(Math.min(900, this.getRushTick() + 1));
				this.setRushUseTick(Math.max(-2, this.getRushUseTick() - 1));
			}
		}
	}
	//前提
	public boolean spellNeed(float min, float max, int time) {
		return !this.specialAction() && !this.isNoAi() && this.isAlive() && this.getAttackTick() <= 0 && this.getTarget() != null && this.getTarget().isAlive()
				&& this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
				&& this.getRandom().nextInt(time * 20) == 1;
	}
	public void lookAtRusher(Entity p_21392_, float p_21393_, float p_21394_) {
		double d0 = p_21392_.getX() - this.getX();
		double d2 = p_21392_.getZ() - this.getZ();
		double d1;
		if (p_21392_ instanceof LivingEntity livingentity) {
			d1 = livingentity.getEyeY() - this.getEyeY();
		} else {
			d1 = (p_21392_.getBoundingBox().minY + p_21392_.getBoundingBox().maxY) / 2.0D - this.getEyeY();
		}

		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
		float f1 = (float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
		this.setXRot(this.rotlerpRusher(this.getXRot(), f1, p_21394_));
		this.setYRot(this.rotlerpRusher(this.getYRot(), f, p_21393_));
		this.setYHeadRot(this.rotlerpRusher(this.getYRot(), f, p_21393_));
		this.setYBodyRot(this.rotlerpRusher(this.getYRot(), f, p_21393_));
	}
	private float rotlerpRusher(float p_21377_, float p_21378_, float p_21379_) {
		float f = Mth.wrapDegrees(p_21378_ - p_21377_);
		if (f > p_21379_) {
			f = p_21379_;
		}
		if (f < -p_21379_) {
			f = -p_21379_;
		}
		return p_21377_ + f;
	}
	@Override
	public boolean stopLookTime() {
		if (this.getAttackUse() == 1 && this.getAttackTick() <= 30 && this.getAttackTick() > 10)
			return true;
		if (this.getAttackUse() == 2 && this.getAttackTick() <= 40 && this.getAttackTick() > 10)
			return true;
		if (this.getAttackUse() == 3 && this.getAttackTick() <= 25 && this.getAttackTick() > 10)
			return true;
		if (this.getAttackUse() == 4 && this.getAttackTick() <= 25 && this.getAttackTick() > 10)
			return true;
		if (this.getAttackUse() == 5 && this.getAttackTick() > 2)
			return true;
		return this.getStompUseTick() > 0 || this.getRushUseTick() > 5 && this.getRushUseTick() > 50;
	}
	//冲刺
	public boolean RushAttack() {
		if (!this.level().isClientSide()) {
			this.setXRot(this.rushFindXRot);
			this.setYRot(this.rushFindYRot);
			this.setYBodyRot(this.rushFindYRot);
			this.setYHeadRot(this.rushFindYRot);
		}
		float f = this.rushFindYRot;
		float f2 = this.rushFindXRot;
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.35f;
		if (this.getRushUseTick() <= 20) {
			f7 = 0.25f;
		}
		else if (this.getRushUseTick() > 55) {
			f7 = 0.65f;
		}
		float f8 = f4 *= f7 / f6 * 0.02f;
		if (this.getRushUseTick() > 50) {
			f8 += 0.09f;
		}
		else if (this.getRushUseTick() > 20) {
			f8 += 0.075f;
		}
		this.push(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2);
		//攻击
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if (this.tickCount % 5 == 0 || "Rusher".equals(string)) {
			if (!this.level().isClientSide()) {
				if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && ForgeEventFactory.getMobGriefingEvent(this.level(), this)) {
					boolean bl = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
					AABB aABB = this.getBoundingBox().inflate(1).move(0, 1, 0);
					for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB.minX), Mth.floor(aABB.minY), Mth.floor(aABB.minZ), Mth.floor(aABB.maxX), Mth.floor(aABB.maxY), Mth.floor(aABB.maxZ))) {
						BlockState blockState = this.level().getBlockState(blockPos);
						float block = blockState.getDestroySpeed(this.level(), blockPos);
						if (block >= 5f || block < 0f) continue;
						if (!ForgeEventFactory.onEntityDestroyBlock(this, blockPos, blockState)) continue;
						if (!ForgeHooks.canEntityDestroy(this.level(), blockPos, this)) continue;
						if ((blockState.is(BlockTags.REPLACEABLE_BY_TREES) || blockState.is(BlockTags.DIRT) || blockState.is(BlockTags.SCULK_REPLACEABLE) || blockState.is(BlockTags.STONE_ORE_REPLACEABLES)) && this.getRandom().nextFloat() > 0.05) {
							bl = this.level().destroyBlock(blockPos, false, this) || bl;
						} else {
							bl = this.level().destroyBlock(blockPos, true, this) || bl;
						}
					}
				}
			}
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.15f));
		for (LivingEntity attack : list) {
			if (!(Main.mobSizeSmall(attack) || Main.mobSizeMedium(attack) || Main.mobSizeLarge(attack)) || EntityAndItemFind.isNoSpecialKnockback(attack.getType())) continue;
			if (AttackFind.FindCanNotAttack(this, attack)) continue;
			if (!this.hasLineOfSight(attack)) continue;
			float angleYaw = 90.0F * ((float) Math.PI / 180.0F);
			double x = this.getX() + 2.5f * Mth.cos((float) (this.rushFindYRot * (Math.PI / 180.0F) + angleYaw));
			double y = this.getY() + 2;
			double z = this.getZ() + 2.5f * Mth.sin((float) (this.rushFindYRot * (Math.PI / 180.0F) + angleYaw));
			if (attack.isShiftKeyDown()) continue;
			AttackFind.attackBegin(this, attack);
			AttackFind.attackAfter(this, attack, 0.3f, 0f, false, 0);
			attack.teleportTo(x, y, z);
		}
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > 0) {
			return false;
		}
		if (this.specialAction()) {
			return false;
		}
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Rusher".equals(string)) {
			return false;
		}
		if (!this.level().isClientSide()) {
			int attackRandom1 = this.getRandom().nextInt(100);
			int attackRandom2 = this.getRandom().nextInt(100);
			if (attackRandom1 > 50) {
				if (attackRandom2 > 60) {
					this.setAnimTick(40);
					this.setAttackTick(40);
					this.setAttackUse(1);
					this.setAnimationState("fist1");
				}
				else if (attackRandom2 > 20) {
					this.setAnimTick(40);
					this.setAttackTick(40);
					this.setAttackUse(1);
					this.setAnimationState("fist2");
				}
				else {
					this.setAnimTick(50);
					this.setAttackTick(50);
					this.setAttackUse(2);
					this.setAnimationState("fist3");
				}
			}
			else if (attackRandom1 > 0) {
				if (attackRandom2 > 60) {
					this.setAnimTick(35);
					this.setAttackTick(35);
					this.setAttackUse(3);
					this.setAnimationState("palm1");
				}
				else if (attackRandom2 > 20) {
					this.setAnimTick(35);
					this.setAttackTick(35);
					this.setAttackUse(3);
					this.setAnimationState("palm2");
				}
				else {
					this.setAnimTick(35);
					this.setAttackTick(35);
					this.setAttackUse(4);
					this.setAnimationState("palm3");
				}
			}
		}
		this.getNavigation().stop();
		this.setDeltaMovement(this.getDeltaMovement().multiply(0,1,0));
		return true;
	}

	public boolean trueHurt() {
		if (!this.isSilent()) {
			//践踏
			if (this.getAttackUse() == 1 || this.getAttackUse() == 2) {
				this.playSound(BGASoundEvents.BLACK_GOLD_BRUISER_ATTACK_1, 2.0f, 1.0f);
			}
			else if (this.getAttackUse() == 3 || this.getAttackUse() == 4) {
				this.playSound(BGASoundEvents.BLACK_GOLD_BRUISER_ATTACK_2, 2.0f, 1.0f);
			}
			else if (this.getAttackUse() == 5) {
				this.playSound(BGASoundEvents.BLACK_GOLD_BRUISER_STOMP, 2.0f, 1.0f);
			}
			else if (this.getAttackUse() == 6) {
				this.playSound(BGASoundEvents.BLACK_GOLD_BRUISER_RUSH, 2.0f, 1.0f);
			}
			else {
				this.playSound(SoundEvents.PLAYER_ATTACK_STRONG, 2.0f, 1.0f);
			}
			if (this.getRandom().nextFloat() > 0.9f) {
				this.playSound(SoundEvents.PIGLIN_ANGRY, 2.0f, 0.2f);
			}
		}
		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		float reach = 0.5f;
		//拳
		if (this.getAttackUse() == 1 || this.getAttackUse() == 2) {
			damageMulti = 1.25f;
			knockbackMulti = 1.15f;
			reach = 0.75f;
		}
		//掌
		else if (this.getAttackUse() == 3) {
			damageMulti = 1.15f;
			knockbackMulti = 1.45f;
			reach = 0.75f;
		}
		else if (this.getAttackUse() == 4) {
			damageMulti = 2.0f;
			knockbackMulti = 1.85f;
			reach = 0.8f;
		}
		//驱邪踏击
		else if (this.getAttackUse() == 5) {
			damageMulti = 1.75f;
			knockbackMulti = 1.35f;
		}
		//急速冲撞
		else if (this.getAttackUse() == 6) {
			damageMulti = 1.5f;
			knockbackMulti = 0f;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > 64) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && this.getAttackUse() != 5 && this.getAttackUse() != 6) continue;
			if (hurt.isShiftKeyDown() && this.getAttackUse() == 6) continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl = AttackFind.attackAfter(this, hurt, damageMulti * (this.getAttackUse() == 5 && hurt.getMobType() == MobType.UNDEAD ? 1.2f : 1f), knockbackMulti, false, 0f);
			if (bl) {
				if (hurt.getAttribute(Attributes.ARMOR) != null && hurt.getAttributeValue(Attributes.ARMOR) > 6) {
					DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this);
					AttackFind.attackAfterCustomDamage(this, hurt, damageSource,
							(float) Math.min(3f, (hurt.getAttributeValue(Attributes.ARMOR) - 6) * 0.035f), 0, false, 0f);
				}
				if ((this.getAttackUse() == 3 || this.getAttackUse() == 4 || this.getAttackUse() == 5) && hurt.getMobType() == MobType.UNDEAD) {
					if (!this.level().isClientSide()) {
						hurt.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 240, 0), this);
						hurt.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 240, 0), this);
					}
				}
				//击飞
				if (this.getAttackUse() == 3 || this.getAttackUse() == 4) {
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
							&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						double d = 0.0;
						if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
							d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
						}
						double d3 = (Math.max(0, 1 - d) + 0.3) * knockbackMulti;
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(
								-((this.getX() - hurt.getX()) * d3 / 2) * (0.7 + knockbackMulti / 3),
								-((this.getY() - hurt.getY()) * d3 / 2) * (0.7 + knockbackMulti / 3),
								-((this.getZ() - hurt.getZ()) * d3 / 2) * (0.7 + knockbackMulti / 3)
						));
					}
				}
			}
			if (this.getAttackUse() == 5 || this.getAttackUse() == 6) {
				if (!hurt.getBlockStateOn().isAir()) {
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 100 / list.size(); i++) {
							double angle = (Math.PI * 2 * i) / (100f / list.size());
							double radius = 0.5;
							double offsetX = Math.cos(angle) * radius;
							double offsetZ = Math.sin(angle) * radius;
							serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, hurt.getBlockStateOn()), hurt.getX() + offsetX, hurt.getY() + 0.2f, hurt.getZ() + offsetZ, 0, offsetX * 0.05, -0.02, offsetZ * 0.05, 1.0);
						}
						for (int i = 0; i < 100 / list.size(); i++) {
							double angle = (Math.PI * 2 * i) / (100f / list.size());
							double radius = 0.35;
							double offsetX = Math.cos(angle) * radius;
							double offsetZ = Math.sin(angle) * radius;
							serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, hurt.getBlockStateOn()), hurt.getX() + offsetX, hurt.getY() + 0.2f, hurt.getZ() + offsetZ, 0, offsetX * 0.05, -0.02, offsetZ * 0.05, 1.0);
						}
					}
				}
			}
		}
		//横扫效果
		if (this.getAttackUse() != 5 && this.getAttackUse() != 6) {
			Main.sweepAttack(this);
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		return true;
	}

	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 10;
	}
	@Override
	public boolean hurt(DamageSource damagesource, float amount) {
		if (this.deathTime > 20) {
            for (int i = 0; i < 120; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double d2 = this.random.nextGaussian() * 0.02;
                double d3 = this.random.nextGaussian() * 0.02;
                this.level().addAlwaysVisibleParticle(ParticleTypes.CLOUD, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), d, d2, d3);
                this.level().addAlwaysVisibleParticle(ParticleTypes.CLOUD, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), d, -d2, d3);
            }
			if (!this.level().isClientSide() && !this.isRemoved()) {
				this.level().broadcastEntityEvent(this, (byte)60);
				this.remove(RemovalReason.KILLED);
			}
		}
		if (isInvulnerableTo(damagesource)) {
			return super.hurt(damagesource, amount);
		}
		if (this.getRushUseTick() > 0) {
			amount *= 0.2f;
		}
		if (damagesource.getEntity() instanceof LivingEntity livingEntity && livingEntity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
			if (EntityAndItemFind.isMeleeDamage(damagesource)) {
				AttributeInstance attributeInstance = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
				if (attributeInstance != null && attributeInstance.getModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")) != null) {
					float f = (float) Objects.requireNonNull(attributeInstance.getModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"))).getAmount();
					if (f > 0) {
						amount *= Math.max(0.4f, 1 - (f / amount));
					}
				}
			}
		}
		if (!damagesource.is(DamageTypeTags.BYPASSES_RESISTANCE)) {
			amount *= 0.8f;
		}
		return super.hurt(damagesource, amount);
	}


	@Override
	public void tickDeath() {
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(160);
				this.setAnimationState("dead");
				this.setLoss(this.getRandom().nextFloat() > 0.9f || "Rusher".equals(string));
			}
		}
		++this.deathTime;
		if (this.deathTime >= 20 && !this.isLoss() && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
		if (this.deathTime >= 160 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(3);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}