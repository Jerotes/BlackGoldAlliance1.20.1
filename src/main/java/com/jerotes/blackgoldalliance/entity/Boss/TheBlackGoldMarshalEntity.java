package com.jerotes.blackgoldalliance.entity.Boss;

import com.jerotes.blackgoldalliance.block.NetherSiphonCoreEntity;
import com.jerotes.blackgoldalliance.config.OtherMainConfig;
import com.jerotes.blackgoldalliance.entity.Animal.AnimalHoglinEntity;
import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldStepperEntity;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderEntity;
import com.jerotes.blackgoldalliance.event.BossBarEvent;
import com.jerotes.blackgoldalliance.goal.ForceNearestAttackableTargetGoal;
import com.jerotes.blackgoldalliance.goal.SwitchTargetToAllyTargetGoal;
import com.jerotes.blackgoldalliance.goal.TheBlackGoldMarshalMeleeAttackGoal;
import com.jerotes.blackgoldalliance.init.*;
import com.jerotes.blackgoldalliance.spell.OtherSpellFind;
import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Interface.ItemSpecialEffect;
import com.jerotes.jerotes.item.Interface.ItemTwoHanded;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TheBlackGoldMarshalEntity extends BlackGoldPiglinBossBaseEntity implements BossEntity, SpellUseEntity, BreakShieldEntity {
	public AnimationState normalAttack1AnimationState = new AnimationState();
	public AnimationState normalAttack2AnimationState = new AnimationState();
	public AnimationState normalAttack3AnimationState = new AnimationState();
	public AnimationState normalAttack4AnimationState = new AnimationState();
	public AnimationState normalAttack5AnimationState = new AnimationState();
	public AnimationState normalAttack6AnimationState = new AnimationState();
	public AnimationState specialAttack1AnimationState = new AnimationState();
	public AnimationState specialAttack2AnimationState = new AnimationState();
	public AnimationState specialAttack3AnimationState = new AnimationState();
	public AnimationState specialAttack4AnimationState = new AnimationState();
	public AnimationState specialAttack5AnimationState = new AnimationState();
	public AnimationState specialAttack6AnimationState = new AnimationState();
	public AnimationState block1AnimationState = new AnimationState();
	public AnimationState block2AnimationState = new AnimationState();
	public AnimationState block3AnimationState = new AnimationState();
	public AnimationState shootAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.YELLOW, false);
	public static final EntityDataAccessor<Integer> ATTACKING_ANIM = SynchedEntityData.defineId(TheBlackGoldMarshalEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> SHADOW_ANIM = SynchedEntityData.defineId(TheBlackGoldMarshalEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> SPECIAL_ATTACK_USE = SynchedEntityData.defineId(TheBlackGoldMarshalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(TheBlackGoldMarshalEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> BLOCK_COOLDOWN = SynchedEntityData.defineId(TheBlackGoldMarshalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPECIAL_BLOCK_TICK = SynchedEntityData.defineId(TheBlackGoldMarshalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPECIAL_HURT_TICK = SynchedEntityData.defineId(TheBlackGoldMarshalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_COOLDOWN = SynchedEntityData.defineId(TheBlackGoldMarshalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SUMMON_COOLDOWN = SynchedEntityData.defineId(TheBlackGoldMarshalEntity.class, EntityDataSerializers.INT);
	public static final int MAX_SHADOW_HISTORY = 12;
	public static final int SHADOW_COOLDOWN = 10;

	public TheBlackGoldMarshalEntity(EntityType<? extends TheBlackGoldMarshalEntity> type, Level level) {
		super(type, level);
		xpReward = 50;
		this.setCanPickUpLoot(false);
		this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = (float) OtherMainConfig.TheBlackGoldMarshalDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = (float) OtherMainConfig.TheBlackGoldMarshalDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = (float) OtherMainConfig.TheBlackGoldMarshalDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.FEET.getIndex()] = (float) OtherMainConfig.TheBlackGoldMarshalDropEquipmentChance;
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = (float) OtherMainConfig.TheBlackGoldMarshalDropEquipmentChance;
		this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = (float) OtherMainConfig.TheBlackGoldMarshalDropEquipmentChance;
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(OtherMainConfig.TheBlackGoldMarshalMaxHealth);
			this.setHealth(this.getMaxHealth());
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(OtherMainConfig.TheBlackGoldMarshalMeleeDamage);
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(OtherMainConfig.TheBlackGoldMarshalArmor);
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(OtherMainConfig.TheBlackGoldMarshalMovementSpeed);
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_KNOCKBACK)).setBaseValue(OtherMainConfig.TheBlackGoldMarshalAttackKnockback);
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).setBaseValue(OtherMainConfig.TheBlackGoldMarshalKnockbackResistance);
		}
		if (level.isClientSide) {
			BossBarEvent.BOSSES.add(this);
		}
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
		for (int i = 0; i < 20; i++) {
			listRenderer1.add(0f);
			listRenderer2.add(0f);
			listRenderer3.add(0);
			listX.add(0d);
			listY.add(0d);
			listZ.add(0d);
			listXRot.add(0f);
			listYRot.add(0f);
			listXRotO.add(0f);
			listYRotO.add(0f);
			listAttackTime.add(0f);
			listYBodyRot.add(0f);
			listYHeadRot.add(0f);
			listYBodyRotO.add(0f);
			listYHeadRotO.add(0f);
			listWalkSpeed.add(0f);
			listWalkPosition.add(0f);
			listBob.add(0f);
			listRiding.add(false);
			listRidingLivingEntity.add(false);
			listAgeInTicks.add(0f);
		}
	}
	//百分比
	public boolean hasPercentageDamage() {
		return MainConfig.HasPercentageDamage.contains(this.getEncodeId())
				|| OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
	}
	public float PercentageDamage(DamageSource damageSource) {
		if (EntityAndItemFind.MagicResistance(damageSource)) {
			return(float) OtherMainConfig.TheBlackGoldMarshalMagicAttackPercentage;
		}
		return (float) OtherMainConfig.TheBlackGoldMarshalAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.TheBlackGoldMarshalDamageCap;
	}
	//间隔
	public boolean hasDamageCooldownTick() {
		return MainConfig.HasDamageCooldownTick.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCooldownTick.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCooldownTick(DamageSource damageSource, Entity entity) {
		float base = 1.0F;
		if (damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
			base *= 0.5F;
		}
		return (float)OtherMainConfig.TheBlackGoldMarshalDamageCooldownTick * base;
	}
	@Override
	public boolean wantsToPickUp(ItemStack itemStack) {
		if (itemStack.canEquip(EquipmentSlot.HEAD,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.CHEST,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.LEGS,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.FEET,this)) {
			return false;
		}
		return super.wantsToPickUp(itemStack);
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		if (newItem.getItem() instanceof BowItem) {
			return false;
		}
		if (newItem.getItem() instanceof CrossbowItem) {
			return false;
		}
		if (newItem.getItem() instanceof ShieldItem) {
			return false;
		}
		return super.canReplaceCurrentItem(newItem, oldItem);
	}


	@Override
	public void startSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(BGAItems.THE_BLACK_GOLD_MARSHAL_HELMET.get()));
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(BGAItems.THE_BLACK_GOLD_MARSHAL_CHESTPLATE.get()));
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(BGAItems.THE_BLACK_GOLD_MARSHAL_LEGGINGS.get()));
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(BGAItems.THE_BLACK_GOLD_MARSHAL_BOOTS.get()));
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.40);
		builder = builder.add(Attributes.MAX_HEALTH, 320);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12f);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5f);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(2, new TheBlackGoldMarshalMeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.6));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, AbstractPiglin.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 6.0f));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WitherSkeleton.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WitherBoss.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(4, new ForceNearestAttackableTargetGoal<>(this, NetherSiphonCoreForceEntity.class, false, false));
		this.targetSelector.addGoal(1, new SwitchTargetToAllyTargetGoal(this, 32.0));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<BlackGoldPiglinEntity>(this, true));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}
	@Override
	protected @NotNull SoundEvent getDeathSound() {
		return BGASoundEvents.THE_BLACK_GOLD_MARSHAL_DEATH;
	}
	@Override
	protected @NotNull SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.isDamageSourceBlocked(damageSource) && !(this.getOffhandItem().getItem() instanceof ShieldItem)) {
			return BGASoundEvents.THE_BLACK_GOLD_MARSHAL_BLOCKING;
		}
		if (this.isDamageSourceBlocked(damageSource)) {
			return SoundEvents.SHIELD_BLOCK;
		}
		return BGASoundEvents.THE_BLACK_GOLD_MARSHAL_HURT;
	}
	protected float nextStep() {
		return this.moveDist + 1.5f;
	}
	@Override
	protected void playStepSound(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		if (this.getAttackTick() <= 0)
			this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_WALK, 1.0f, 1.0f);
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
	protected void blockedByShield(@NotNull LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			player.disableShield(true);
		}
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	protected boolean isImmuneToZombification() {
		return true;
	}
	@Override
	public boolean isBaby() {
		return false;
	}
	protected float getStandingEyeHeight(Pose p_259213_, EntityDimensions p_259279_) {
		return 2.6f;
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
	public void knockback(double d, double d2, double d3) {
		super.knockback(0, 0, 0);
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
	}
	@Override
	public void makeStuckInBlock(@NotNull BlockState blockState, @NotNull Vec3 vec3) {
		this.resetFallDistance();
	}
	@Override
	public boolean canUseBow() {
		return false;
	}
	public float getVoicePitch() {
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 0.75f;
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
		AABB aabb1 = aabb.inflate(Math.sqrt(2.04F) - (double)0.6F, 0.0D, Math.sqrt(2.04F) - (double)0.6F);
		return aabb1.inflate(2.25d, 2.25d, 2.25d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	public boolean isOnFire() {
		return false;
	}
	@Override
	public boolean selfChangeAttackAnim() {
		return true;
	}
	public boolean specialAction() {
		return this.getSpecialAttackUse() == 4 && this.getAttackTick() > 3 ||
				this.getSpecialAttackUse() == 5 && this.getAttackTick() > 3 ||
				this.getSpecialBlockTick() > 0;
	}
	@Override
	public boolean selfAttackAbout() {
		return true;
	}
	@Override
	public boolean selfUseAnim() {
		return true;
	}
	@Override
	public boolean isBlocking() {
		if (this.getSpecialBlockTick() > 3) {
			return true;
		}
		return super.isBlocking();
	}
	@Override
	public boolean canSpawnSprintParticle() {
		return this.getDeltaMovement().horizontalDistanceSqr() > 2.5 && this.getRandom().nextInt(5) == 1 && this.getAttackTick() > 0;
	}
	@Override
	public SoundEvent getBossMusic() {
		return SoundEvents.MUSIC_DISC_PIGSTEP;
	}
	public void travel(@NotNull Vec3 vec3) {
		if (this.specialAction() || ((this.getAttackTick() > 5 && this.getAttackTick() < 25 && this.getAttackUse() != 6 && this.getSpecialAttackUse() == 0) ||
				this.getSpecialAttackUse() != 0 && stopMoveSpecialAttack())) {
			this.setDeltaMovement(0d, this.getDeltaMovement().y, 0d);
			super.travel(Vec3.ZERO);
		}
		else if (this.isEffectiveAi() && (this.isInFluidType())) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.7));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
			}
		}
		else {
			super.travel(vec3);
		}
	}




	public int spellLevel = OtherMainConfig.TheBlackGoldMarshalSpellNormalLevel;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public float attackingAnimProgress = 0.0f;
	public float shadowAnimProgress = 0.0f;
	public int renderTick = 0;
	private double blockDestroyTick;
	public List<Float> listRenderer1 = new ArrayList<>();
	public List<Float> listRenderer2 = new ArrayList<>();
	public List<Integer> listRenderer3 = new ArrayList<>();
	public List<Double> listX = new ArrayList<>();
	public List<Double> listY = new ArrayList<>();
	public List<Double> listZ = new ArrayList<>();
	public List<Float> listXRot = new ArrayList<>();
	public List<Float> listXRotO = new ArrayList<>();
	public List<Float> listYRot = new ArrayList<>();
	public List<Float> listYRotO = new ArrayList<>();
	public List<Float> listAttackTime = new ArrayList<>();
	public List<Float> listYBodyRot = new ArrayList<>();
	public List<Float> listYHeadRot = new ArrayList<>();
	public List<Float> listYBodyRotO = new ArrayList<>();
	public List<Float> listYHeadRotO = new ArrayList<>();
	public List<Float> listWalkSpeed = new ArrayList<>();
	public List<Float> listWalkPosition = new ArrayList<>();
	public List<Float> listBob = new ArrayList<>();
	public List<Boolean> listRiding = new ArrayList<>();
	public List<Boolean> listRidingLivingEntity = new ArrayList<>();
	public List<Float> listAgeInTicks = new ArrayList<>();
	public int getAttackingAnim() {
		return this.getEntityData().get(ATTACKING_ANIM);
	}
	public void setAttackingAnim(int n) {
		this.getEntityData().set(ATTACKING_ANIM, n);
	}
	public int getShadowAnim() {
		return this.getEntityData().get(ATTACKING_ANIM);
	}
	public void setShadowAnim(int n) {
		this.getEntityData().set(ATTACKING_ANIM, n);
	}
	public int getSpecialAttackUse() {
		return this.getEntityData().get(SPECIAL_ATTACK_USE);
	}
	public void setSpecialAttackUse(int n) {
		this.getEntityData().set(SPECIAL_ATTACK_USE, n);
	}
	public int getBlockCooldown() {
		return this.getEntityData().get(BLOCK_COOLDOWN);
	}
	public void setBlockCooldown(int n) {
		this.getEntityData().set(BLOCK_COOLDOWN, n);
	}
	public void setSpecialBlockTick(int n){
		this.getEntityData().set(SPECIAL_BLOCK_TICK, n);
	}
	public int getSpecialBlockTick(){
		return this.getEntityData().get(SPECIAL_BLOCK_TICK);
	}
	public void setSpecialHurtTick(int n){
		this.getEntityData().set(SPECIAL_HURT_TICK, n);
	}
	public int getSpecialHurtTick(){
		return this.getEntityData().get(SPECIAL_HURT_TICK);
	}
	public void setShootCooldown(int n){
		this.getEntityData().set(SHOOT_COOLDOWN, n);
	}
	public int getShootCooldown(){
		return this.getEntityData().get(SHOOT_COOLDOWN);
	}
	public void setSummonCooldown(int n){
		this.getEntityData().set(SUMMON_COOLDOWN, n);
	}
	public int getSummonCooldown(){
		return this.getEntityData().get(SUMMON_COOLDOWN);
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "dead")){
			return 1;
		}
		else if (Objects.equals(animation, "dance")){
			return 2;
		}
		else if (Objects.equals(animation, "normalAttack1")){
			return 3;
		}
		else if (Objects.equals(animation, "normalAttack2")){
			return 4;
		}
		else if (Objects.equals(animation, "normalAttack3")){
			return 5;
		}
		else if (Objects.equals(animation, "normalAttack4")){
			return 6;
		}
		else if (Objects.equals(animation, "normalAttack5")){
			return 7;
		}
		else if (Objects.equals(animation, "normalAttack6")){
			return 8;
		}
		else if (Objects.equals(animation, "specialAttack1")){
			return 9;
		}
		else if (Objects.equals(animation, "specialAttack2")){
			return 10;
		}
		else if (Objects.equals(animation, "specialAttack3")){
			return 11;
		}
		else if (Objects.equals(animation, "specialAttack4")){
			return 12;
		}
		else if (Objects.equals(animation, "specialAttack5")){
			return 13;
		}
		else if (Objects.equals(animation, "specialAttack6")){
				return 14;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.deadAnimationState);
		list.add(this.danceAnimationState);
		list.add(this.normalAttack1AnimationState);
		list.add(this.normalAttack2AnimationState);
		list.add(this.normalAttack3AnimationState);
		list.add(this.normalAttack4AnimationState);
		list.add(this.normalAttack5AnimationState);
		list.add(this.normalAttack6AnimationState);
		list.add(this.specialAttack1AnimationState);
		list.add(this.specialAttack2AnimationState);
		list.add(this.specialAttack3AnimationState);
		list.add(this.specialAttack4AnimationState);
		list.add(this.specialAttack5AnimationState);
		list.add(this.specialAttack6AnimationState);
		return list;
	}
	//是否传奇
	public boolean isLegend() {
		return this.getEntityData().get(IS_LEGEND);
	}
	public void setLegend(boolean bl) {
		this.getEntityData().set(IS_LEGEND, bl);
	}
	public boolean isLegendary() {
		return this.isLegend() || hasPercentageDamage() && hasDamageCap() && hasDamageCooldownTick();
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
		compoundTag.putInt("AttackingAnim", this.getAttackingAnim());
		compoundTag.putInt("SpecialAttackUse", this.getSpecialAttackUse());
		compoundTag.putInt("BlockCooldown", this.getBlockCooldown());
		compoundTag.putInt("SpecialBlockTick", this.getSpecialBlockTick());
		compoundTag.putInt("SpecialHurtTick", this.getSpecialHurtTick());
		compoundTag.putInt("ShootCooldown", this.getShootCooldown());
		compoundTag.putInt("SummonCooldown", this.getSummonCooldown());
		compoundTag.putInt("ShadowAnim", this.getShadowAnim());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.setAttackingAnim(compoundTag.getInt("AttackingAnim"));
		this.setSpecialAttackUse(compoundTag.getInt("SpecialAttackUse"));
		this.setBlockCooldown(compoundTag.getInt("BlockCooldown"));
		this.setSpecialBlockTick(compoundTag.getInt("SpecialBlockTick"));
		this.setSpecialHurtTick(compoundTag.getInt("SpecialHurtTick"));
		this.setShootCooldown(compoundTag.getInt("ShootCooldown"));
		this.setSummonCooldown(compoundTag.getInt("SummonCooldown"));
		this.setShadowAnim(compoundTag.getInt("ShadowAnim"));
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IS_LEGEND, false);
		this.getEntityData().define(ATTACKING_ANIM, 0);
		this.getEntityData().define(SHADOW_ANIM, 0);
		this.getEntityData().define(SPECIAL_ATTACK_USE, 0);
		this.getEntityData().define(BLOCK_COOLDOWN, 180);
		this.getEntityData().define(SPECIAL_BLOCK_TICK, 0);
		this.getEntityData().define(SPECIAL_HURT_TICK, 0);
		this.getEntityData().define(SHOOT_COOLDOWN, 120);
		this.getEntityData().define(SUMMON_COOLDOWN, 30 * 20);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
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
						this.normalAttack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.normalAttack1AnimationState);
						break;
					case 4:
						this.normalAttack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.normalAttack2AnimationState);
						break;
					case 5:
						this.normalAttack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.normalAttack3AnimationState);
						break;
					case 6:
						this.normalAttack4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.normalAttack4AnimationState);
						break;
					case 7:
						this.normalAttack5AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.normalAttack5AnimationState);
						break;
					case 8:
						this.normalAttack6AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.normalAttack6AnimationState);
						break;
					case 9:
						this.specialAttack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.specialAttack1AnimationState);
						break;
					case 10:
						this.specialAttack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.specialAttack2AnimationState);
						break;
					case 11:
						this.specialAttack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.specialAttack3AnimationState);
						break;
					case 12:
						this.specialAttack4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.specialAttack4AnimationState);
						break;
					case 13:
						this.specialAttack5AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.specialAttack5AnimationState);
						break;
					case 14:
						this.specialAttack6AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.specialAttack6AnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide() && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
	}
	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		this.clearFire();

		if (this.specialAction() || ((this.getAttackTick() > 5 && this.getAttackTick() < 25 && this.getAttackUse() != 6 && this.getSpecialAttackUse() == 0) ||
				this.getSpecialAttackUse() != 0 && stopMoveSpecialAttack())) {
			this.getNavigation().stop();
			this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0);
		}

		if (this.hasEffect(MobEffects.DAMAGE_BOOST)) {
			for (int i = 0; i < Math.min(3, Objects.requireNonNull(this.getEffect(MobEffects.DAMAGE_BOOST)).getAmplifier() + 1); ++i) {
				if (this.level() instanceof ServerLevel serverLevel) {
					if (!this.isInvisible()) {
						serverLevel.sendParticles(BGAParticleTypes.MARSHAL_SHOCK.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, 0.0, 0.0, 0.0, 0);
					}
				}
			}
		}
		//破坏方块
		{
			if (this.blockDestroyTick > 0) {
				this.blockDestroyTick -= 1;
			}
			if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
				boolean blockDestroy = Main.BlockDestroy(this, 10f);
				if (blockDestroy) {
					this.blockDestroyTick = OtherMainConfig.TheBlackGoldMarshalBreakBlockCooldown * 20;
				}
				if (!this.level().isClientSide) {
					if (!canBlockDestroy && this.onGround()) {
						this.jumpFromGround();
					}
				}
			}
		}
		//近战攻击
		{
			if (!this.level().isClientSide()) {
				this.setAttackTick(Math.max(-30, this.getAttackTick() - 1));
				this.setSpecialBlockTick(Math.max(0, this.getSpecialBlockTick() - 1));
				this.setSpecialHurtTick(Math.max(0, this.getSpecialHurtTick() - 1));
			}
			if (this.isAlive()) {
				if (this.getAttackTick() > 0 && this.getSpecialAttackUse() != 0) {
					specialAttackChangeAttackUse();
				}
				else {
					if (this.getAttackTick() == 20) {
						playSwingSound();
					}
					else if (this.getAttackTick() == 15) {
						this.trueHurt();
					}
				}
			}
		}
		//动画
		{
			//未攻击且无目标且存活且无技能才抬起武器
			if (this.getTarget() == null && this.getAttackTick() <= -30 && this.isAlive() && !this.specialAction()) {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
				}
				if (this.getAttackAnim() == 8) {
					if (!this.isSilent()) {
						this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_VOICE, 10.0f, 1.0f);
					}
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.max(this.getAttackAnim() - 1, 0));
				}
			}
			//基础站立移动姿态
			if (this.deathTime > 0 || this.getSpecialBlockTick() > 0) {
				if (!this.level().isClientSide()) {
					this.setAttackingAnim(Math.min(this.getAttackingAnim() + 2, 10));
				}
			}
			else if (this.getAttackTick() > 0) {
				if (!this.level().isClientSide()) {
					this.setAttackingAnim(Math.min(this.getAttackingAnim() + 1, 10));
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setAttackingAnim(Math.max(this.getAttackingAnim() - 1, 0));
				}
			}
			//
			if (this.isAlive() && (
					(this.getSpecialAttackUse() == 3 && ((this.getAttackTick() < 65 && this.getAttackTick() > 50) || (this.getAttackTick() < 45 && this.getAttackTick() > 35) || (this.getAttackTick() < 30 && this.getAttackTick() > 20)))
					|| (this.getSpecialAttackUse() == 4 && ((this.getAttackTick() < 25 && this.getAttackTick() > 15)))
							|| (this.getAttackTick() >= 10 && this.getAttackTick() <= 15 && (this.getAttackUse() == 5 || this.getAttackUse() == 6))
			)) {
				if (!this.level().isClientSide()) {
					this.setShadowAnim(Math.min(this.getShadowAnim() + 1, 10));
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setShadowAnim(Math.max(this.getShadowAnim() - 2, 0));
				}
			}
		}
		//技能
		if (!this.isNoAi() && this.isAlive()){
			int spellLevel = this.getSpellLevel() - 1;
			if (this.level().getDifficulty() == Difficulty.NORMAL) {
				spellLevel = this.getSpellLevel();
			} else if (this.level().getDifficulty() == Difficulty.HARD) {
				spellLevel = this.getSpellLevel() + 1;
			}
			//技能-黑金地裂
			{
				//开始
				if (this.getBlockCooldown() <= 0 && this.spellNeed(0, 16, 2)) {
					if (!this.level().isClientSide()) {
						this.setAttackTick(51);
						this.setSpecialAttackUse(4);
						this.setAttackUse(0);
					}
				}
				if (this.getSpecialAttackUse() == 4) {
					if (this.getAttackTick() == 50) {
						if (!this.level().isClientSide()) {
							this.setBlockCooldown((int) (OtherMainConfig.TheBlackGoldMarshalCrackCooldown * 20));
							this.setAnimTick(52);
							this.setAnimationState("specialAttack4");
							this.yBodyRot = this.getYRot();
							this.yHeadRot = this.getYRot();
							this.yBodyRotO = this.yBodyRot;
							this.yHeadRotO = this.yHeadRot;
						}
						this.setDeltaMovement(this.getDeltaMovement().multiply(0,1,0));
						this.getNavigation().stop();
					}
					else if (this.getAttackTick() == 35) {
						if (!this.isSilent()) {
							this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_BLOCK, 10.0f, 1.0f);
						}
						if (!this.level().isClientSide()) {
							this.setAttackUse(4);
						}
						trueHurt();
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(BGAParticleTypes.BLACK_GOLD_GROUND_CRACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
							float renderYawOffset = this.yBodyRot;
							float angleYaw = (180.0f) * ((float) Math.PI / 180.0F);
							Vec3 rightPos = new Vec3(this.xo + 1.5f * Mth.cos((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)), this.yo, this.zo + 1.5f * Mth.sin((float) (renderYawOffset * (Math.PI / 180.0F) + angleYaw)));
							Main.spawnUnevenBlockByPos(serverLevel,  BlockPos.containing(rightPos).below(), 2);
						}
					}
					else if (this.getAttackTick() == 25) {
						if (!this.isSilent()) {
							this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_SWING, 10.0f, 1.0f);
						}
					}
					else {
						if (this.getAttackTick() == 20) {
							OtherSpellFind.BlackGoldGroundCrack(this, this.getTarget() == null ? this : this.getTarget(), spellLevel, 10, 2, true, 0);
							trueHurt();
						}
						else if (this.getAttackTick() <= 19 && this.getAttackTick() >= 10) {
							OtherSpellFind.BlackGoldGroundCrack(this, this.getTarget() == null ? this : this.getTarget(),
									spellLevel, 10, 2, true, 10 - (this.getAttackTick() - 10));
						}
					}
				}
			}
			//黑金连弩
			{
				//开始
				if (this.getShootCooldown() <= 0 && this.spellNeed(6, 24, 1)) {
					if (!this.level().isClientSide()) {
						this.setAttackTick(71);
						this.setSpecialAttackUse(5);
						this.setAttackUse(0);
					}
				}
				if (this.getSpecialAttackUse() == 5) {
					if (this.getAttackTick() == 70) {
						if (!this.level().isClientSide()) {
							this.setShootCooldown((int) (OtherMainConfig.TheBlackGoldMarshalShootCooldown * 20));
							this.setAnimTick(72);
							this.setAnimationState("specialAttack5");
						}
						this.setDeltaMovement(this.getDeltaMovement().multiply(0,1,0));
						this.getNavigation().stop();
					}
					else if (this.getAttackTick() == 55) {
						if (!this.isSilent()) {
							this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_SHOOT, 10.0f, 1.0f);
						}
						if (!this.level().isClientSide()) {
							this.setAttackUse(4);
						}
					}
					if (this.getAttackTick() == 65) {
						//寻找最好的弩装备
						int findNumOffHandItem = -1;
						ItemStack offHandItem = this.getOffhandItem();
						ItemStack findNestOffHandItem = offHandItem;
						for (int n = 0; n < inventoryCount(); ++n) {
							ItemStack itemStack = this.mobInventory().getItem(n);
							if (canReplaceCrossbowSelf(this, itemStack, findNestOffHandItem) || findNestOffHandItem.isEmpty()) {
								findNestOffHandItem = itemStack;
								findNumOffHandItem = n;
							}
						}
						if (findNumOffHandItem != -1 && offHandItem.getEnchantmentLevel(Enchantments.BINDING_CURSE) <= 0) {
							this.setItemSlot(EquipmentSlot.OFFHAND, this.mobInventory().getItem(findNumOffHandItem));
							this.mobInventory().setItem(findNumOffHandItem, offHandItem);
						}
						if (this.isUsingItem()) {
							this.stopUsingItem();
						}
					}
					if (this.getOffhandItem().getItem() instanceof CrossbowItem crossbowItem) {
						//提前装填
						if (this.getAttackTick() <= 60 && this.getAttackTick() >= 30) {
							this.startUsingItem(InteractionHand.OFF_HAND);
						}
						if (this.getAttackTick() > 30 && this.getTicksUsingItem() >= CrossbowItem.getChargeDuration(this.getOffhandItem())) {
							this.releaseUsingItem();
						}
						//准备好
						if (this.getAttackTick() == 30 && !CrossbowItem.isCharged(this.getOffhandItem())) {
							crossbowItem.releaseUsing(this.getOffhandItem(), this.level(), this, 9999);
						}
						//发射
						if (this.getOffhandItem().getItem() instanceof CrossbowItem && CrossbowItem.isCharged(this.getOffhandItem()) &&
								this.getAttackTick() < 30 && this.getAttackTick() > 10) {
							ItemStack itemStack = this.getOffhandItem();
							int shootTimes = 1;
							if (crossbowItem instanceof ItemToolBaseCrossbow itemToolBaseCrossbow && itemToolBaseCrossbow.maxBullet() > 0) {
								shootTimes = itemToolBaseCrossbow.maxBullet();
							}
							int finds = 20 / (shootTimes + 1);
							boolean bl = false;
							for (int n = 0; n <= shootTimes; ++n) {
								if (this.getAttackTick() == finds * n + 10) {
									bl = true;
								}
							}
							if (bl) {
								if (InventoryEntity.isCrossbow(itemStack)) {
									useCrossbowShoot(this, 3.15F);
									if (!this.level().isClientSide()) {
										this.level().broadcastEntityEvent(this, (byte) 102);
									}
								}

								if (ItemToolBaseCrossbow.getBullet(itemStack) > 1) {
									ItemToolBaseCrossbow.setBullet(itemStack, ItemToolBaseCrossbow.getBullet(itemStack) - 1);
									this.lookAt(this.getTarget() != null ? this.getTarget() : this, 360.0f, 360.0f);
									this.getLookControl().setLookAt(this.getTarget() != null ? this.getTarget() : this, 360.0f, 360.0f);
								}
								else {
									CrossbowItem.setCharged(itemStack, false);
								}
							}
						}
						//收起
						if (this.getOffhandItem().getItem() instanceof CrossbowItem && this.getAttackTick() == 5) {
							//寻找最好的弩装备
							int findNumOffHandItem = -1;
							ItemStack offHandItem = this.getOffhandItem();
							ItemStack findNestOffHandItem = offHandItem;
							for (int n = 0; n < inventoryCount(); ++n) {
								ItemStack itemStack = this.mobInventory().getItem(n);
								if (!findNestOffHandItem.isEmpty() && itemStack.isEmpty()) {
									findNestOffHandItem = itemStack;
									findNumOffHandItem = n;
								}
							}
							if (findNumOffHandItem != -1 && offHandItem.getEnchantmentLevel(Enchantments.BINDING_CURSE) <= 0) {
								this.setItemSlot(EquipmentSlot.OFFHAND, this.mobInventory().getItem(findNumOffHandItem));
								this.mobInventory().setItem(findNumOffHandItem, offHandItem);
							}
						}
					}
				}
			}
			//技能-黑金号令
			{
				//开始
				if (this.getSummonCooldown() <= 0 && this.spellNeed(0, 24, 2)) {
					List<TheBlackGoldMarshalEntity> listLord = this.level().getEntitiesOfClass(TheBlackGoldMarshalEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
					listLord.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
					List<BlackGoldPiglinEntity> listPiglin = this.level().getEntitiesOfClass(BlackGoldPiglinEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
					listPiglin.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
					if (listPiglin.size() < listLord.size() * 8) {
						if (!this.level().isClientSide()) {
							this.setAttackTick(31);
							this.setSpecialAttackUse(6);
							this.setAttackUse(0);
						}
					}
					else {
						if (!this.level().isClientSide()) {
							this.setSummonCooldown((int) (OtherMainConfig.TheBlackGoldMarshalLeadCooldown * 20));
						}
					}
				}
				if (this.getSpecialAttackUse() == 6) {
					if (this.getAttackTick() == 30) {
						if (!this.level().isClientSide()) {
							this.setSummonCooldown((int) (OtherMainConfig.TheBlackGoldMarshalLeadCooldown * 20));
							this.setAnimTick(32);
							this.setAnimationState("specialAttack6");
							this.yBodyRot = this.getYRot();
							this.yHeadRot = this.getYRot();
							this.yBodyRotO = this.yBodyRot;
							this.yHeadRotO = this.yHeadRot;
						}
						this.setDeltaMovement(this.getDeltaMovement().multiply(0,1,0));
						this.getNavigation().stop();
					}
					else if (this.getAttackTick() == 18) {
						if (!this.isSilent()) {
							this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_SUMMON, 10.0f, 1.0f);
						}
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
							this.lookAt(this.getTarget(), 360.0f, 360.0f);
						}
						OtherSpellFind.BlackGoldOrder(this, this.getTarget(), 6, 6, 16, 32);
					}
				}
			}
			//计算
			{
				if (!this.level().isClientSide()) {
					this.setBlockCooldown(Math.max(this.getBlockCooldown() - 1, 0));
					this.setShootCooldown(Math.max(this.getShootCooldown() - 1, 0));
					this.setSummonCooldown(Math.max(this.getSummonCooldown() - 1, 0));
				}
			}
		}
	}
	public boolean spellNeed(float min, float max, int time) {
		return this.getAttackTick() <= -3 && !this.specialAction() && !this.isNoAi() && this.isAlive() && this.getAttackTick() <= 0 && this.getTarget() != null && this.getTarget().isAlive()
				&& this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
				&& this.getRandom().nextInt(time * 20) == 1;
	}
	public static boolean canReplaceCrossbowSelf(InventoryEntity inventory, ItemStack newItem, ItemStack oldItem) {
		if (!InventoryEntity.isCrossbow(inventory, newItem) && !InventoryEntity.isCrossbow(inventory, oldItem)) {
			return false;
		} else if (oldItem.getEnchantmentLevel(Enchantments.BINDING_CURSE) <= 0) {
			if (InventoryEntity.isCrossbow(inventory, newItem) && InventoryEntity.isCrossbow(inventory, oldItem)) {
				if (newItem.getMaxDamage() > oldItem.getMaxDamage()) {
					return true;
				}

				if (newItem.getMaxDamage() == oldItem.getMaxDamage() && EntityAndItemFind.isEnchantLevelBetter(newItem, oldItem)) {
					return true;
				}

				if (newItem.getMaxDamage() == oldItem.getMaxDamage() && newItem.getDamageValue() < oldItem.getDamageValue()) {
					return true;
				}
			}

			return InventoryEntity.isCrossbow(inventory, newItem) && (!InventoryEntity.isMeleeWeapon(inventory, oldItem) || !inventory.canUseMeleeWeapon()) && (!InventoryEntity.isCrossbow(inventory, oldItem) || !inventory.canUseCrossbow()) && (!InventoryEntity.isMagicItem(inventory, oldItem) || !inventory.canUseMagicItem()) && (!InventoryEntity.isThrow(inventory, oldItem) || !inventory.canUseThrow()) && (!InventoryEntity.isBow(inventory, oldItem) || !inventory.canUseBow()) && !InventoryEntity.isOtherRange(inventory, oldItem);
		} else {
			return false;
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > -3) {
			return false;
		}
		if (this.specialAction()) {
			return false;
		}
		//普通攻击
		int attackRandom = this.getRandom().nextInt(120);
		if (this.getRandom().nextFloat() > 0.4f) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(32);
				this.setSpecialAttackUse(0);
				if (attackRandom > 100) {
					this.setAttackTick(30);
					this.setAnimationState("normalAttack1");
					this.setAttackUse(1);
				} else if (attackRandom > 80) {
					this.setAttackTick(30);
					this.setAnimationState("normalAttack2");
					this.setAttackUse(2);
				} else if (attackRandom > 60) {
					this.setAttackTick(30);
					this.setAnimationState("normalAttack3");
					this.setAttackUse(3);
				} else if (attackRandom > 40) {
					this.setAttackTick(30);
					this.setAnimationState("normalAttack4");
					this.setAttackUse(4);
				} else if (attackRandom > 20) {
					this.setAttackTick(30);
					this.setAnimationState("normalAttack5");
					this.setAttackUse(5);
				} else {
					this.setAttackTick(30);
					this.setAnimationState("normalAttack6");
					this.setAttackUse(6);
				}
			}
		}
		//连击
		else {
			if (attackRandom > 80) {
				this.setAnimTick(72);
				this.setAttackTick(70);
				this.setAnimationState("specialAttack1");
				this.setSpecialAttackUse(1);
				this.setAttackUse(0);
			}
			else if (attackRandom > 40) {
				this.setAnimTick(72);
				this.setAttackTick(70);
				this.setAnimationState("specialAttack2");
				this.setSpecialAttackUse(2);
				this.setAttackUse(0);
			}
			else {
				this.setAnimTick(72);
				this.setAttackTick(70);
				this.setAnimationState("specialAttack3");
				this.setSpecialAttackUse(3);
				this.setAttackUse(0);
			}
		}
		this.setDeltaMovement(this.getDeltaMovement().multiply(0,1,0));
		return true;
	}

	public void specialAttackChangeAttackUse() {
		//连击招式一
		if (this.getSpecialAttackUse() == 1) {
			//攻击第一式
			if (this.getAttackTick() == 60) {
				playSwingSound();
				this.setAttackUse(1);
			}
			else if (this.getAttackTick() == 55) {
				trueHurt();
			}
			//攻击第二式
			else if (this.getAttackTick() == 40) {
				playSwingSound();
				this.setAttackUse(3);
			}
			else if (this.getAttackTick() == 35) {
				trueHurt();
			}
			//攻击第三式
			else if (this.getAttackTick() == 18) {
				playSwingSound();
				this.setAttackUse(5);
			}
			else if (this.getAttackTick() == 13) {
				trueHurt();
				this.RushAttack();
			}
		}
		//连击招式二
		else if (this.getSpecialAttackUse() == 2) {
			//攻击第一式
			if (this.getAttackTick() == 60) {
				playSwingSound();
				this.setAttackUse(2);
			}
			else if (this.getAttackTick() == 55) {
				trueHurt();
			}
			//攻击第二式
			else if (this.getAttackTick() == 40) {
				playSwingSound();
				this.setAttackUse(4);
			}
			else if (this.getAttackTick() == 35) {
				trueHurt();
			}
			//攻击第三式
			else if (this.getAttackTick() == 20) {
				playSwingSound();
				this.setAttackUse(6);
			}
			else if (this.getAttackTick() == 15) {
				trueHurt();
				this.RushAttack();
			}
		}
		//绞剑式
		else if (this.getSpecialAttackUse() == 3) {
			//攻击第一式
			if (this.getAttackTick() == 60) {
				playSwingSound();
				this.setAttackUse(2);
			}
			else if (this.getAttackTick() == 55) {
				trueHurt();
				this.RushAttack();
			}
			//攻击第二式
			else if (this.getAttackTick() == 45) {
				playSwingSound();
				this.setAttackUse(2);
			}
			else if (this.getAttackTick() == 40) {
				trueHurt();
				this.RushAttack();
			}
			//攻击第三式
			else if (this.getAttackTick() == 30) {
				playSwingSound();
				this.setAttackUse(2);
			}
			else if (this.getAttackTick() == 25) {
				trueHurt();
				this.RushAttack();
			}
		}
	}
	public boolean stopMoveSpecialAttack() {
		if (this.getAttackTick() <= 0)
			return false;
		if (this.getSpecialAttackUse() == 1) {
			return !(this.getAttackTick() >= 65 || this.getAttackTick() <= 5 || (this.getAttackTick() >= 45 && this.getAttackTick() <= 55) || (this.getAttackTick() >= 10 && this.getAttackTick() <= 27));
		}
		else if (this.getSpecialAttackUse() == 2) {
			return !(this.getAttackTick() >= 65 || this.getAttackTick() <= 5 || (this.getAttackTick() >= 45 && this.getAttackTick() <= 55) || (this.getAttackTick() >= 10 && this.getAttackTick() <= 35));
		}
		else if (this.getSpecialAttackUse() == 3) {
			return !(this.getAttackTick() >= 65 || this.getAttackTick() <= 5 || (this.getAttackTick() >= 55 && this.getAttackTick() <= 60) || (this.getAttackTick() >= 20 && this.getAttackTick() <= 45));
		}
		else if (this.getSpecialAttackUse() == 4) {
			return this.getAttackTick() < 40 && this.getAttackTick() > 10;
		}
		else if (this.getSpecialAttackUse() == 5) {
			return this.getAttackTick() < 40 && this.getAttackTick() > 10;
		}
		else if (this.getSpecialAttackUse() == 6) {
			return this.getAttackTick() < 20 && this.getAttackTick() > 10;
		}
		return false;
	}
	public void playSwingSound() {
		if (!this.isSilent()) {
			this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_SWING, 10.0f, 1.0f);
		}
	}

	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_HIT, 10.0f, 1.0f);
		}
		float damageMulti = 1.25f;
		float knockbackMulti = 1.0f;
		float reach = 0.5f;
		if (this.getAttackUse() == 1) {
			reach = 1.0f;
		}
		else if (this.getAttackUse() == 2) {
			damageMulti = 1.5f;
		}
		else if (this.getAttackUse() == 3) {
			knockbackMulti = 1.25f;
		}
		else if (this.getAttackUse() == 5) {
			damageMulti = 1.5f;
			knockbackMulti = 1.25f;
			reach = 1.0f;
		}
		else if (this.getAttackUse() == 6) {
			damageMulti = 1.5f;
			knockbackMulti = 1.5f;
			reach = 1.25f;
		}
		int hurtAbout = 0;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		List<LivingEntity> listAround = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > 64) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!listAround.contains(hurt)) {
				if (!Main.canSee(hurt, this) && (this.getAttackUse() == 3 || this.getAttackUse() == 4)) continue;
				if (!Main.canSeeAngle(this, hurt.getEyePosition(), 60) && (this.getAttackUse() == 1 || this.getAttackUse() == 2)) continue;
				if (!Main.canSeeAngle(this, hurt.getEyePosition(), 30) && (this.getAttackUse() == 5 || this.getAttackUse() == 6)) continue;
			}
			AttackFind.attackBegin(this, hurt);

			float healthOld = hurt.getHealth();
			boolean bl;
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_ARMOR_AND_SHIELD_MELEE, this);
			if (this.hasEffect(MobEffects.DAMAGE_BOOST) && Objects.requireNonNull(this.getEffect(MobEffects.DAMAGE_BOOST)).getAmplifier() >= 2) {
				bl = AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti * 1.25f, knockbackMulti, false, 0f);
				if (!this.level().isClientSide()) {
					this.getActiveEffects().removeIf(mobEffectInstance -> !mobEffectInstance.getEffect().isBeneficial());
				}
			}
			else {
				bl = AttackFind.attackAfter(this, hurt, damageMulti * 1.25f, knockbackMulti, false, 0f);
			}
			if (bl) {
				if (healthOld - hurt.getHealth() > 0) {
					this.heal((healthOld - hurt.getHealth()) / 20);
				}
				hurtAbout += 1;
				if (this.getAttackUse() == 1 || this.getAttackUse() == 2) {
					if (hurtAbout < 10) {
						if (this.level() instanceof ServerLevel serverLevel) {
							Main.spawnUnevenBlockByPos(serverLevel, BlockPos.containing(hurt.position()).below(), Math.max(1, (int) hurt.getBbWidth()));
						}
					}
				}
				if (this.getAttackUse() == 4) {
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
							&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						double d = 0.0;
						if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
							d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
						}
						double d3 = Math.max(0, 1 - d) + 0.5;
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 0.8 * d3, -(this.getY() - hurt.getY()) * 0.3 * d3, -(this.getZ() - hurt.getZ()) * 0.8 * d3));
					}
				}
			}
			else {
				//技能-大巧不工
				if (!(hurt.isBlocking() && (hurt instanceof Player || hurt instanceof JerotesPlayerBaseEntity jerotesPlayerBaseEntity && jerotesPlayerBaseEntity.beTargetAsPlayer()))) {
					if (!(this.hasEffect(MobEffects.DAMAGE_BOOST))) {
						if (!this.level().isClientSide()) {
							this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12 * 20, 0), hurt);
						}
					}
					else {
						int level = Objects.requireNonNull(this.getEffect(MobEffects.DAMAGE_BOOST)).getAmplifier();
						int time = Objects.requireNonNull(this.getEffect(MobEffects.DAMAGE_BOOST)).getDuration();
						if (!this.level().isClientSide()) {
							this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Math.max(time, 12 * 20), Math.min(2, level + 1)), hurt);
						}
					}
				}
			}
		}
		if (!this.isSilent() && hurtAbout > 0) {
			this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_HIT, 10.0f, 1.0f);
		}
		if (this.getAttackUse() == 6) {
			this.RushAttack();
		}
		//横扫效果
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
			ItemStack hand = this.getMainHandItem();
			hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	public void RushAttack() {
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4  + f5 * f5);
		float f7 = this.getRandom().nextInt(3, 6) / 30f;
		this.setOnGround(false);
		this.push(f3 * (f7 / f6 * 2), f4 * (f7 / f6 * 2 + 0.01f), f5 * (f7 / f6 * 2));
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypeTags.IS_FIRE)
				|| damageSource.is(DamageTypes.WITHER)
				|| damageSource.is(DamageTypes.WITHER_SKULL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!this.isNoAi() && this.getTarget() != null && this.isAlive())  {
			if (!this.specialAction() && this.getAttackTick() <= 5 && this.getSpecialBlockTick() <= 0)  {
				if (isDamageSourceBlocks(damageSource) && !this.getMainHandItem().isEmpty() && !(this.getOffhandItem().getItem() instanceof ShieldItem)) {
					if (this.getOffhandItem().isEmpty() &&
							this.getMainHandItem().getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock()) {
						if (this.getMainHandItem().getItem() instanceof ItemSpecialEffect specialEffect) {
							specialEffect.blockUse(this, damageSource.getEntity(), damageSource);
						}
					}
					int attackDamage = (int) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 10;
					if (attackDamage < 2) {
						attackDamage = 2;
					}
					//伤害越低 此值越高 越不容易格挡
					float chanceLine = 1 - Mth.clamp((amount / (attackDamage / (this.getHealth() > this.getMaxHealth() / 2 ? 5f: 10f))) , 0f, 1f);
					if (this.getRandom().nextFloat() > 0.9f * chanceLine) {
						if (!this.level().isClientSide()) {
							this.level().broadcastEntityEvent(this, (byte) 101);
						}
						if (this.getRandom().nextInt(1, attackDamage) > amount * 2) {
							if (!this.level().isClientSide()) {
								this.setSpecialBlockTick(8);
							}
							if (!this.isSilent()) {
								this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
										BGASoundEvents.THE_BLACK_GOLD_MARSHAL_BLOCKING,
										this.getSoundSource(), 10.0f, 1.0f);
							}
							if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
								ItemStack hand = this.getMainHandItem();
								hand.hurtAndBreak((int) amount / 3, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
							}
							return false;
						}
					}
				}
			}
		}

		//黑金锻身
		if (EntityAndItemFind.MagicResistance(damageSource))
			amount /= 5;
		else if (damageSource.is(DamageTypeTags.BYPASSES_ARMOR) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			amount /= (this.getHealth() > this.getMaxHealth() / 2 ? 2f: 3f);
		}
		else {
			float afterArmorAbsorbDamage = getDamageAfterArmorAbsorb(damageSource, amount);
			if (afterArmorAbsorbDamage > amount / (this.getHealth() > this.getMaxHealth() / 2 ? 3f: 4f)) {
				amount /= (this.getHealth() > this.getMaxHealth() / 2 ? 3f: 4f);
			}
		}
		//
		if (isDamageSourceBlocks(damageSource) && this.getSpecialBlockTick() > 0) {
			amount /= (this.getHealth() > this.getMaxHealth() / 2 ? 2f: 3f);
		}


		if (this.getHealth() <= this.getMaxHealth() / 2 && getSpecialHurtTick() > 0) {
			amount /= 10;
		}
		else {
			if (!this.level().isClientSide()) {
				this.setSpecialHurtTick(20);
			}
		}
        return super.hurt(damageSource, amount);
	}
	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WITHER) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 67) {
			BossMusicPlayer.playBossMusic(this);
		}
		else if (by == 68) {
			BossMusicPlayer.stopBossMusic(this);
		}
		else if (by == 101) {
			int blockRandom = this.getRandom().nextInt(30);
			if (blockRandom > 20) {
				this.block1AnimationState.start(this.tickCount);
			} else if (blockRandom > 10) {
				this.block2AnimationState.start(this.tickCount);
			} else {
				this.block3AnimationState.start(this.tickCount);
			}
		}
		else if (by == 102) {
			this.shootAnimationState.start(this.tickCount);
		}
		else {
			super.handleEntityEvent(by);
		}
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(40);
				this.setAnimationState("dead");
			}

			//公示
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			AABB area = new AABB(x - 64, y - 64, z - 64, x + 64, y + 64, z + 64);
			List<ServerPlayer> nearPlayers = level().getEntitiesOfClass(ServerPlayer.class, area);
			nearPlayers.removeIf(serverPlayer -> serverPlayer.isSpectator());
			for (Player player : nearPlayers) {
				NetherSiphonCoreEntity.SetDefeatTheHighestLevelPiglinRaid(player, Math.max(this.getRaidLevel(), NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(player)));
			}
			if (this.getTargetPlayer() != null && this.getTargetPlayer() instanceof ServerPlayer serverPlayer) {
				if (nearPlayers.contains(serverPlayer)) {
					Component component;
					if (this.isVictory()) {
						component = Component.translatable("message.blackgoldalliance.piglin_win").withStyle(ChatFormatting.GOLD);
					}
					else {
						component = Component.translatable("message.blackgoldalliance.player_win", NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(serverPlayer)).withStyle(ChatFormatting.GOLD);
					}
					serverPlayer.sendSystemMessage(component);
					serverPlayer.displayClientMessage(component, true);
					nearPlayers.remove(serverPlayer);
				}
			}
			Component component;
			for (ServerPlayer player : nearPlayers) {
				if (this.isVictory()) {
					component = Component.translatable("message.blackgoldalliance.piglin_win").withStyle(ChatFormatting.GOLD);
				}
				else {
					component = Component.translatable("message.blackgoldalliance.player_win", NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(player)).withStyle(ChatFormatting.GOLD);
				}
				player.sendSystemMessage(component);
				player.displayClientMessage(component, true);
			}
			//结算清除时间
			if (this.isChallenge()) {
				List<LivingEntity> nearestPiglin = level().getEntitiesOfClass(LivingEntity.class, area);
				nearestPiglin.removeIf(livingEntity -> !(livingEntity instanceof PiglinRaiderEntity || livingEntity instanceof BlackGoldPiglinEntity || livingEntity instanceof BlackGoldStepperEntity || livingEntity instanceof AnimalHoglinEntity));
				for (LivingEntity livingEntity : nearestPiglin) {
					if (livingEntity == null) continue;
					if ((this.distanceTo(livingEntity)) > 128) continue;
					if (!AttackFind.SameFactionAvoidDamage(this, livingEntity, false)) continue;
					if (livingEntity instanceof BlackGoldPiglinEntity blackGoldPiglinEntity) {
						if (blackGoldPiglinEntity.getSelfPortal() != this.getUUID()) continue;
						blackGoldPiglinEntity.setEntityNeedDiscardTick(65);
					}
					else if (livingEntity instanceof PiglinRaiderEntity piglinRaiderEntity) {
						if (piglinRaiderEntity.getSelfPortal() != this.getUUID()) continue;
						piglinRaiderEntity.setEntityNeedDiscardTick(65);
					}
					else if (livingEntity instanceof AnimalHoglinEntity animalHoglinEntity) {
						if (animalHoglinEntity.getSelfPortal() != this.getUUID()) continue;
						animalHoglinEntity.setEntityNeedDiscardTick(65);
					}
					else if (livingEntity instanceof BlackGoldStepperEntity blackGoldStepperEntity) {
						if (blackGoldStepperEntity.getSelfPortal() != this.getUUID()) continue;
						blackGoldStepperEntity.setEntityNeedDiscardTick(65);
					}
				}
			}
		}
		++this.deathTime;
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.CHANGED_DIMENSION);
			if (!this.isSilent()) {
				this.playSound(JerotesSoundEvents.TELEPORT, 5.0f, 1.0f);
			}
		}
		if (this.deathTime == 32) {
			if (!this.isSilent()) {
				this.playSound(BGASoundEvents.THE_BLACK_GOLD_MARSHAL_VOICE, 5.0f, 1.0f);
			}
		}
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i2 = 0; i2 < Math.min(30, this.deathTime + 20); ++i2) {
				serverLevel.sendParticles(ParticleTypes.PORTAL, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, this.getRandom().nextGaussian(), 0.0, this.getRandom().nextGaussian(), 0.5f);
			}
		}
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(5);
		this.setBowLevel(20);
		this.mobInventory().addItem(BGAItems.BLACK_GOLD_COG_CROSSBOW.get().getDefaultInstance());
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(BGAItems.THE_BLACK_GOLD_MARSHAL_GREATSWORD.get());
	}
}