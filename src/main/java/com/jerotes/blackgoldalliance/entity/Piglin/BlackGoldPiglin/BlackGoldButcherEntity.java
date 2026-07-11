package com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin;

import com.jerotes.blackgoldalliance.control.ButcherMoveControl;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.goal.*;
import com.jerotes.blackgoldalliance.init.BGABlocks;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.item.Tool.ItemToolBaseChainsaw;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class BlackGoldButcherEntity extends BlackGoldPiglinEntity implements EliteEntity {
	private static final EntityDataAccessor<Float> RUN_SPEED = SynchedEntityData.defineId(BlackGoldButcherEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> CHAINSAW_TICK = SynchedEntityData.defineId(BlackGoldButcherEntity.class, EntityDataSerializers.INT);

	public BlackGoldButcherEntity(EntityType<? extends BlackGoldButcherEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.6f);
		xpReward = 80;
		this.moveControl = new ButcherMoveControl(this);
		this.setCanPickUpLoot(false);
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 175);
		builder = builder.add(Attributes.ARMOR, 16);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 10f);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(2, new ButcherMeleeAttackGoal(this, true));
		this.goalSelector.addGoal(3, new BlackGoldPiglinFollowBossGoal(this, 1.1f));
		this.goalSelector.addGoal(3, new PiglinFollowPortalGoal(this, 1.1f));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, AbstractPiglin.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 6.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(new Class[0]));
//		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
//			@Override
//			public boolean canUse() {
//				if (BlackGoldButcherEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
//					return false;
//				}
//				if (this.target != null && PiglinAi.isWearingGold(this.target)) {
//					return false;
//				}
//				return super.canUse();
//			}
//		});
//		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, true){
//			@Override
//			public boolean canUse() {
//				if (BlackGoldButcherEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
//					return false;
//				}
//				if (this.target != null && PiglinAi.isWearingGold(this.target)) {
//					return false;
//				}
//				return super.canUse();
//			}
//		});
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
	protected float getStandingEyeHeight(Pose p_259213_, EntityDimensions p_259279_) {
		return 2.15f;
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
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 0.25f;
	}
	@Override
	public void travel(Vec3 vec3) {
		super.travel(vec3);
		if (this.getChainsawTick() > 520 && this.getMainHandItem().getItem() instanceof ItemToolBaseChainsaw) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
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
		if (!this.getMainHandItem().isEmpty())
			return aabb1.inflate(0.75d, 0.75d, 0.75d);
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	protected void doPush(Entity entity) {
		if (!this.isAggressive()) {
			super.doPush(entity);
		}
		else if (this.getAttackTick() <= 5 && !AttackFind.FindCanNotAttack(this, entity)) {
			this.doHurtTarget(entity);
			super.doPush(entity);
		}
	}
	@Override
	public boolean selfAttackAbout() {
		return true;
	}

	private double blockDestroyTick;
	public void setRunSpeed(float f){
		this.getEntityData().set(RUN_SPEED, f);
	}
	public float getRunSpeed(){
		return this.getEntityData().get(RUN_SPEED);
	}
	public void setChainsawTick(int n){
		this.getEntityData().set(CHAINSAW_TICK, n);
	}
	public int getChainsawTick(){
		return this.getEntityData().get(CHAINSAW_TICK);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putFloat("RunSpeed", this.getRunSpeed());
		compoundTag.putInt("ChainsawTick", this.getChainsawTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.setRunSpeed(compoundTag.getFloat("RunSpeed"));
		this.setChainsawTick(compoundTag.getInt("ChainsawTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(RUN_SPEED, 0f);
		this.getEntityData().define(CHAINSAW_TICK, 0);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		//破坏方块
		if (this.blockDestroyTick > 0) {
			this.blockDestroyTick -= 1;
		}
		{
			if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
				boolean blockDestroy = Main.BlockDestroy(this, 10f);
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
		//加速计时
		if (this.getTarget() != null) {
			if (!this.level().isClientSide()) {
				this.setRunSpeed(Math.min(this.getRunSpeed() + 0.003f, 1.8f));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setRunSpeed(Math.max(this.getRunSpeed() - 0.01f, 1.2f));
			}
		}
		//链锯
		if (this.getMainHandItem().getItem() instanceof ItemToolBaseChainsaw && (this.getChainsawTick() == 600 || this.getChainsawTick() == 560)) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("heavyAttack2");
			}
		}
		if (this.getMainHandItem().getItem() instanceof ItemToolBaseChainsaw && (this.getAttackTick() > 8 && this.getAttackTick() < 17 || blockDestroyTick > 0 ||
				this.getChainsawTick() > 530 && this.getChainsawTick() <= 560 || this.getChainsawTick() > 570 && this.getChainsawTick() <= 590
		)) {
			this.startUsingItem(InteractionHand.MAIN_HAND);
			double d =(-Mth.sin(this.yBodyRot * 0.017453292F));
			double d2 = Mth.cos(this.yBodyRot * 0.017453292F);
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(ParticleTypes.LAVA, this.getX() + d, this.getY(0.65), this.getZ() + d2, 0, d, 0.0, d2, 0.0);
			}
		}
		else if (this.getUseItem().getItem() instanceof ItemToolBaseChainsaw) {
			this.stopUsingItem();
		}
		if (!this.level().isClientSide()) {
			this.setChainsawTick(Math.max(this.getChainsawTick() - 1, 0));
		}
		if (this.getMainHandItem().getItem() instanceof ItemToolBaseChainsaw && this.getChainsawTick() <= 0 && this.getTarget() != null) {
			if (!this.level().isClientSide()) {
				this.setChainsawTick(600);
			}
			this.getNavigation().stop();
		}
		if (this.getMainHandItem().getItem() instanceof ItemToolBaseChainsaw && this.getChainsawTick() > 0 && this.getChainsawTick() < 590 && this.getAttackTick() <= 0 && blockDestroyTick <= 0) {
			double d = (-Mth.sin(this.yBodyRot * 0.017453292F));
			double d2 = Mth.cos(this.yBodyRot * 0.017453292F);
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(ParticleTypes.SMOKE, this.getX() + d, this.getY(0.65), this.getZ() + d2, 0, d, 0.0, d2, 0.0);
			}
		}
		if (this.getMainHandItem().getItem() instanceof ItemToolBaseChainsaw && this.getChainsawTick() > 0 && this.getChainsawTick() < 590) {
			if (!this.isSilent()) {
				this.playSound(SoundEvents.ITEM_BREAK, 1.0f, 2.0f);
			}
		}

		//近战攻击
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-30, this.getAttackTick() - 1));
		}
		if (this.getAttackTick() == 15 && this.isAlive()) {
			this.trueHurt();
		}
	}

	//冒粒子和火星 激活后武器抽动 前后摇

	@Override
	public boolean doHurtTarget(Entity entity) {
		if ((this.getChainsawTick() <= 0 || this.getChainsawTick() > 520) && this.getMainHandItem().getItem() instanceof ItemToolBaseChainsaw) {
			return false;
		}
		if (!this.isSilent() && this.getRandom().nextFloat() > 0.9f) {
			this.playSound(SoundEvents.PIGLIN_ANGRY, 5.0f, 0.2f);
		}
		if (this.getAttackTick() > 5) {
			return false;
		}
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(30);
			//其他
			if (!(this.getMainHandItem().getItem() instanceof ItemToolBaseChainsaw)) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAnimationState("heavyAttack1");
			}
			//电锯
			else {
				if (attackRandom > 20) {
					this.setAnimTick(20);
					this.setAttackTick(25);
					this.setAnimationState("longAttack1");
				}
				else if (attackRandom > 10) {
					this.setAnimTick(20);
					this.setAttackTick(25);
					this.setAnimationState("longAttack2");
				}
				else {
					this.setAnimTick(20);
					this.setAttackTick(25);
					this.setAnimationState("longAttack3");
				}
			}
		}
		this.setDeltaMovement(this.getDeltaMovement().multiply(0,1,0));
		return true;
	}

	public boolean trueHurt() {
		if (this.getMainHandItem().getItem().canDisableShield(this.getMainHandItem(), new ItemStack(Items.SHIELD), this, this)) {
			if (!this.isSilent()) {
				this.playSound(SoundEvents.PLAYER_ATTACK_STRONG, 1.0f, 1.0f);
				this.playSound(SoundEvents.ANVIL_FALL, 1.0f, 1.0f);
			}
		}
		else {
			if (!this.isSilent()) {
				this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0f, 1.0f);
			}
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > 64) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			AttackFind.attackBegin(this, hurt);
			if (this.getMainHandItem().getItem() instanceof ItemToolBaseChainsaw) {
				if (this.getRandom().nextFloat() > 0.75f) {
					AttackFind.attackAfter(this, hurt, 1.25f, 1.0f, false, 0f);
				}
				else {
					DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BLEEDING, this);
					AttackFind.attackAfterCustomDamage(this, hurt, damageSource, 0.6f, 1.0f, false, 0f);
				}
				if (hurt.getMobType() != MobType.UNDEAD && !EntityFactionFind.isOoze(hurt) && !EntityFactionFind.isPlant(hurt) && !EntityFactionFind.isConstruct(hurt)) {
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 20 / list.size(); i++) {
							double angle = (Math.PI * 2 * i) / (20f / list.size());
							double radius = hurt.getBbWidth() / 2f;
							double offsetX = Math.cos(angle) * radius;
							double offsetZ = Math.sin(angle) * radius;
							serverLevel.sendParticles(JerotesParticleTypes.BLOOD.get(), hurt.getX() + offsetX, hurt.getY(0.75 + this.getRandom().nextFloat() * 0.1f), hurt.getZ() + offsetZ, 0, offsetX * 0.05, -0.1 - this.getRandom().nextFloat() * 0.1f, offsetZ * 0.05, 1.0);
						}
					}
				}
			}
			else {
				AttackFind.attackAfter(this, hurt, 1.25f, 1.0f, false, 0f);
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
			ItemStack hand = this.getMainHandItem();
			hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(3);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(BGAItems.BLACK_GOLD_CHAINSAW.get());
	}

}