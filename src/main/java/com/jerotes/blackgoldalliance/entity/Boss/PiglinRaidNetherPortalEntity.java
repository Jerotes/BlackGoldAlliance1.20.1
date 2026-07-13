package com.jerotes.blackgoldalliance.entity.Boss;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.block.NetherSiphonCoreEntity;
import com.jerotes.blackgoldalliance.config.OtherMainConfig;
import com.jerotes.blackgoldalliance.entity.Animal.AnimalHoglinEntity;
import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldStepperEntity;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.entity.Other.PortalPointEntity;
import com.jerotes.blackgoldalliance.entity.Part.PiglinRaidNetherPortalPart;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderEntity;
import com.jerotes.blackgoldalliance.event.BossBarEvent;
import com.jerotes.blackgoldalliance.goal.ForceNearestAttackableTargetGoal;
import com.jerotes.blackgoldalliance.goal.SwitchTargetToAllyTargetGoal;
import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.blackgoldalliance.init.BGAParticleTypes;
import com.jerotes.blackgoldalliance.util.OtherParticlesUse;
import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.control.NoRotationControl;
import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.event.AdvancementEvent;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.util.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.BossEvent;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class PiglinRaidNetherPortalEntity extends PathfinderMob implements NeutralMob, BossEntity, JerotesEntity, FactionEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState startAnimationState = new AnimationState();
	public AnimationState roundAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Boolean> IS_BLACK_GOLD_ALLIANCE = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> START_TICK = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RAID_LEVEL = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> RAID_TICK = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> BOSS_SUMMONED_RAID_TICK = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> FACING = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> START_X = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> START_Y = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> START_Z = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.INT);
	//胜利
	private static final EntityDataAccessor<Boolean> VICTORY = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.BOOLEAN);
	//是否挑战
	private static final EntityDataAccessor<Boolean> CHALLENGE = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.BOOLEAN);
	//选择玩家
	private static final EntityDataAccessor<Optional<UUID>> TARGET_PLAYER = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	//boss袭击
	private static final EntityDataAccessor<Integer> BOSS_RAID = SynchedEntityData.defineId(PiglinRaidNetherPortalEntity.class, EntityDataSerializers.INT);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.PURPLE, false);
	private final PiglinRaidNetherPortalPart part_1;
	private final PiglinRaidNetherPortalPart part_2;
	private final PiglinRaidNetherPortalPart part_3;
	private final PiglinRaidNetherPortalPart part_4;
	private final PiglinRaidNetherPortalPart part_5;
	public final PiglinRaidNetherPortalPart[] allParts;

	public PiglinRaidNetherPortalEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(0f);
		xpReward = 300;
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(OtherMainConfig.PiglinRaidNetherPortalMaxHealth);
			this.setHealth(this.getMaxHealth());
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(OtherMainConfig.PiglinRaidNetherPortalMeleeDamage);
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(OtherMainConfig.PiglinRaidNetherPortalArmor);
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(OtherMainConfig.PiglinRaidNetherPortalMovementSpeed);
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_KNOCKBACK)).setBaseValue(OtherMainConfig.PiglinRaidNetherPortalAttackKnockback);
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).setBaseValue(OtherMainConfig.PiglinRaidNetherPortalKnockbackResistance);
		}
		if (level.isClientSide) {
			BossBarEvent.BOSSES.add(this);
		}
		part_1 = new PiglinRaidNetherPortalPart(this, "part_1", 1f, 6f);
		part_2 = new PiglinRaidNetherPortalPart(this, "part_2", 1f, 6f);
		part_3 = new PiglinRaidNetherPortalPart(this, "part_3", 1f, 6f);
		part_4 = new PiglinRaidNetherPortalPart(this, "part_4", 1f, 6f);
		part_5 = new PiglinRaidNetherPortalPart(this, "part_5", 1f, 6f);
		allParts = new PiglinRaidNetherPortalPart[]{part_1, part_2, part_3, part_4, part_5};
	}

	@Override
	public int getExperienceReward() {
		if (this.isVictory())
			return 0;
		return 250 + this.getRaidLevel() * 50;
	}

	@Override
	public String getFirstFactionTypeName() {
		if (this.isBlackGoldAlliance())
			return "black_gold_alliance";
		return "piglin_raider";
	}
	@Override
	public List<String> getFactionTypeUntilTame() {
		List<String> list = new ArrayList<>();
		list.add(getFirstFactionTypeName());
		list.add("piglin");
		return list;
	}

	//百分比
	public boolean hasPercentageDamage() {
		return MainConfig.HasPercentageDamage.contains(this.getEncodeId())
				|| OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
	}
	public float PercentageDamage(DamageSource damageSource) {
		if (EntityAndItemFind.MagicResistance(damageSource)) {
			return(float) OtherMainConfig.PiglinRaidNetherPortalMagicAttackPercentage;
		}
		return (float) OtherMainConfig.PiglinRaidNetherPortalAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.PiglinRaidNetherPortalDamageCap;
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
		return (float)OtherMainConfig.PiglinRaidNetherPortalDamageCooldownTick * base;
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
		if (this.getStartTick() > -30) {
			this.bossEvent.setProgress(Mth.clamp((140f - this.getStartTick())/ 140f, 0.0f, 1.0f));
		}
		else {
			int maxTick = (int) ((OtherMainConfig.PiglinRaidNetherPortalRoundTime * 20) + (20 * OtherMainConfig.PiglinRaidNetherPortalLevelAddRoundTime * (getRaidLevel() - 1)));
			this.bossEvent.setProgress(Mth.clamp(1 - (getRaidTick() / (maxTick)), 0.0f, 1.0f));
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 300);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	public void remove(@NotNull RemovalReason reason) {
		super.remove(reason);
		if (allParts != null) {
			for (PiglinRaidNetherPortalPart part : allParts) {
				part.remove(RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ForceNearestAttackableTargetGoal<>(this, NetherSiphonCoreForceEntity.class, false, false));
		this.targetSelector.addGoal(1, new SwitchTargetToAllyTargetGoal(this, 32.0));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<PiglinRaidNetherPortalEntity>(this, false));
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.PORTAL_AMBIENT;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 600;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GLASS_BREAK;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.STONE_BREAK;
	}
	@Override
	public SoundEvent getBossMusic() {
		return SoundEvents.MUSIC_DISC_PIGSTEP;
	}
	@Override
	public boolean isBaby() {
		return false;
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	protected float getStandingEyeHeight(Pose p_259213_, EntityDimensions p_259279_) {
		return 3f;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	protected void doPush(Entity entity) {
	}
	@Override
	protected void pushEntities() {
	}
	@Override
	public boolean isPushable() {
		return false;
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
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
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
	@Override
	public boolean canBeSeenAsEnemy() {
		return false;
	}
	@Override
	public boolean canChangeDimensions() {
		return false;
	}
	@Override
	public ItemStack getPickResult() {
		if (this.getRaidLevel() == 1)
			return new ItemStack(BGAItems.PIGLIN_RAID_NETHER_PORTAL_I_SPAWN_EGG.get());
		if (this.getRaidLevel() == 2)
			return new ItemStack(BGAItems.PIGLIN_RAID_NETHER_PORTAL_II_SPAWN_EGG.get());
		if (this.getRaidLevel() == 3)
			return new ItemStack(BGAItems.PIGLIN_RAID_NETHER_PORTAL_III_SPAWN_EGG.get());
		if (this.getRaidLevel() == 4)
			return new ItemStack(BGAItems.PIGLIN_RAID_NETHER_PORTAL_IV_SPAWN_EGG.get());
		if (this.getRaidLevel() == 5)
			return new ItemStack(BGAItems.PIGLIN_RAID_NETHER_PORTAL_V_SPAWN_EGG.get());
		if (this.getRaidLevel() == 6)
			return new ItemStack(BGAItems.PIGLIN_RAID_NETHER_PORTAL_VI_SPAWN_EGG.get());
		if (this.getRaidLevel() > 6)
			return new ItemStack(BGAItems.PIGLIN_RAID_NETHER_PORTAL_VI_SPAWN_EGG.get());
		return new ItemStack(BGAItems.PIGLIN_RAID_NETHER_PORTAL_I_SPAWN_EGG.get());
	}

	public int summonMaxTick = 0;
	public boolean isSummonedEliteOrBoss = false;
	//剩余时间
	public float getRaidTick() {
		return this.getEntityData().get(RAID_TICK);
	}
	public void setRaidTick(float f) {
		this.getEntityData().set(RAID_TICK, f);
	}
	public float getBossSummonedRaidTick() {
		return this.getEntityData().get(BOSS_SUMMONED_RAID_TICK);
	}
	public void setBossSummonedRaidTick(float f) {
		this.getEntityData().set(BOSS_SUMMONED_RAID_TICK, f);
	}
	//等级
	public int getRaidLevel() {
		return this.getEntityData().get(RAID_LEVEL);
	}
	public void setRaidLevel(int n) {
		this.getEntityData().set(RAID_LEVEL, n);
	}
	public boolean isBlackGoldAlliance() {
		return this.getEntityData().get(IS_BLACK_GOLD_ALLIANCE);
	}
	public void setBlackGoldAlliance(boolean bl) {
		this.getEntityData().set(IS_BLACK_GOLD_ALLIANCE, bl);
	}
	public float getFacing() {
		return this.getEntityData().get(FACING);
	}
	public void setFacing(float f) {
		this.getEntityData().set(FACING, f);
	}
	//是不是挑战（是刷怪蛋的还是事件的）
	//是不是都给指定玩家播放消息（不是挑战也能提高附近生存模式玩家击败传送门等级）
	//是挑战才有召唤物随着传送门死亡消失的限制
	//打碎下界汲取器就直接赢而消失
	//没下界汲取器也直接赢消失
	//而且失败（对于玩家是胜利）有特殊奖励掉落物
	//胜利（对于玩家是失败）猪灵会跳舞
	//boss到来后会接管这些判定
	// ——————Jerotes_
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
		if (player == null) {
			this.getEntityData().set(TARGET_PLAYER, Optional.empty());
			return;
		}
		this.getEntityData().set(TARGET_PLAYER, Optional.of(player.getUUID()));
	}
	public void setTargetPlayerUUID(UUID player) {
		this.getEntityData().set(TARGET_PLAYER, Optional.of(player));
	}
	public int getBossRaid() {
		return this.getEntityData().get(BOSS_RAID);
	}
	public void setBossRaid(int n) {
		this.getEntityData().set(BOSS_RAID, n);
	}
	//
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	//动画
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "round")){
			return 1;
		}
		else if (Objects.equals(animation, "dead")){
			return 2;
		}
		else if (Objects.equals(animation, "start")){
			return 3;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.roundAnimationState);
		list.add(this.deadAnimationState);
		list.add(this.startAnimationState);
		return list;
	}
	//
	public int getStartTick() {
		return this.getEntityData().get(START_TICK);
	}
	public void setStartTick(int n) {
		this.getEntityData().set(START_TICK, n);
	}
	//是否传奇
	public boolean isLegend() {
		return this.getEntityData().get(IS_LEGEND);
	}
	public void setLegend(boolean bl) {
		this.getEntityData().set(IS_LEGEND, bl);
		this.refreshDimensions();
	}
	public boolean isLegendary() {
		return this.isLegend() || hasPercentageDamage() && hasDamageCap() && hasDamageCooldownTick();
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
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception) {
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
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SummonMaxTick", this.summonMaxTick);
		compoundTag.putInt("StartTick", this.getStartTick());
		compoundTag.putInt("RaidLevel", this.getRaidLevel());
		compoundTag.putBoolean("IsBlackGoldAlliance", this.isBlackGoldAlliance());
		compoundTag.putFloat("RaidTick", this.getRaidTick());
		compoundTag.putFloat("BossSummonedRaidTick", this.getBossSummonedRaidTick());
		compoundTag.putFloat("Facing", this.getFacing());
		compoundTag.putBoolean("IsSummonedEliteOrBoss", this.isSummonedEliteOrBoss);
		compoundTag.putBoolean("IsVictory", this.isVictory());
		compoundTag.putBoolean("IsChallenge", this.isChallenge());
		compoundTag.putUUID("TargetPlayerUUID", this.getTargetPlayerUUID());
		compoundTag.putInt("BossRaid", this.getBossRaid());
		compoundTag.putInt("XStart", getStartX());
		compoundTag.putInt("YStart", getStartY());
		compoundTag.putInt("ZStart", getStartZ());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.summonMaxTick = (compoundTag.getInt("SummonMaxTick"));
		this.setStartTick(compoundTag.getInt("StartTick"));
		this.setRaidLevel(compoundTag.getInt("RaidLevel"));
		if (compoundTag.contains("IsBlackGoldAlliance")) {
			this.setBlackGoldAlliance(compoundTag.getBoolean("IsBlackGoldAlliance"));
		}
		this.setRaidTick(compoundTag.getFloat("RaidTick"));
		this.setBossSummonedRaidTick(compoundTag.getFloat("BossSummonedRaidTick"));
		this.setFacing(compoundTag.getFloat("Facing"));
		this.isSummonedEliteOrBoss = compoundTag.getBoolean("IsSummonedEliteOrBoss");
		this.setVictory(compoundTag.getBoolean("IsVictory"));
		this.setChallenge(compoundTag.getBoolean("IsChallenge"));
		if (compoundTag.hasUUID("TargetPlayerUUID")) {
			this.setTargetPlayerUUID(compoundTag.getUUID("TargetPlayerUUID"));
		}
		this.setBossRaid(compoundTag.getInt("BossRaid"));
		this.setStartPos(new BlockPos((int) compoundTag.getInt("XStart"), (int) compoundTag.getInt("YStart"), (int) compoundTag.getInt("ZStart")));
		this.bossEvent.setId(this.getUUID());
		this.readPersistentAngerSaveData(this.level(), compoundTag);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IS_BLACK_GOLD_ALLIANCE, false);
		this.getEntityData().define(IS_LEGEND, false);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(START_TICK, 0);
		this.getEntityData().define(RAID_LEVEL, 1);
		this.getEntityData().define(RAID_TICK, 0f);
		this.getEntityData().define(BOSS_SUMMONED_RAID_TICK, 0f);
		this.getEntityData().define(FACING, 0f);
		this.getEntityData().define(VICTORY, false);
		this.getEntityData().define(CHALLENGE, false);
		this.getEntityData().define(TARGET_PLAYER, Optional.of(this.getUUID()));
		this.getEntityData().define(BOSS_RAID, 0);
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
						this.roundAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.roundAnimationState);
						break;
					case 2:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
					case 3:
						this.startAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.startAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}
	@Override
	public boolean isMultipartEntity() {
		return true;
	}
	@Override
	public PartEntity<?>[] getParts() {
		return allParts;
	}
	public PiglinRaidNetherPortalPart[] getSubEntities() {
		return this.allParts;
	}
	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
		super.recreateFromPacket(clientboundAddEntityPacket);
		PiglinRaidNetherPortalPart[] PiglinRaidNetherPortalParts = this.getSubEntities();
		for (int i = 0; i < PiglinRaidNetherPortalParts.length; ++i) {
			PiglinRaidNetherPortalParts[i].setId(i + 1 + clientboundAddEntityPacket.getId());
		}
	}
	private void tickMultipart() {
		Vec3[] avector3d = new Vec3[this.allParts.length];
		for (int j = 0; j < this.allParts.length; ++j) {
			avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
		}
		Vec3 center = this.position().add(0, 0, 0);
		this.part_1.setPosCenteredY(this.rotateOffsetVec(new Vec3(1, 3, 0), 0, this.yBodyRot).add(center));
		this.part_2.setPosCenteredY(this.rotateOffsetVec(new Vec3(2, 3, 0), 0, this.yBodyRot).add(center));
		this.part_3.setPosCenteredY(this.rotateOffsetVec(new Vec3(-1, 3, 0), 0, this.yBodyRot).add(center));
		this.part_4.setPosCenteredY(this.rotateOffsetVec(new Vec3(-2, 3, 0), 0, this.yBodyRot).add(center));
		this.part_5.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 3, 0), 0, this.yBodyRot).add(center));
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
		//胜利条件-NetherSiphonCoreEntity
		//生成机制
		{
			int maxTick = (int) ((OtherMainConfig.PiglinRaidNetherPortalRoundTime * 20) + (20 * OtherMainConfig.PiglinRaidNetherPortalLevelAddRoundTime * (getRaidLevel() - 1)));
			if (!this.isBlackGoldAlliance()) {
				this.bossEvent.setName(Component.translatable("entity.blackgoldalliance.piglin_raid")
						.append(Component.literal(" ")).append(Component.translatable("entity.blackgoldalliance.piglin_raid_level", String.valueOf(this.getRaidLevel()))).append(Component.literal(" "))
						.append(Component.translatable("entity.blackgoldalliance.piglin_raid_time",(int)(((maxTick) - getRaidTick())/20f))).append(Component.literal(" ")));
			}
			else {
				String normal = "entity.blackgoldalliance.piglin_raid_black_gold";
				if (this.getBossRaid() == 1) {
					normal = "entity.blackgoldalliance.piglin_raid_black_gold_marshal";
				}
				this.bossEvent.setName(Component.translatable(normal)
						.append(Component.literal(" ")).append(Component.translatable("entity.blackgoldalliance.piglin_raid_level", String.valueOf(this.getRaidLevel()))).append(Component.literal(" "))
						.append(Component.translatable("entity.blackgoldalliance.piglin_raid_time", (int)(((maxTick) - getRaidTick())/20f))).append(Component.literal(" ")));
			}
			//死亡
			if ((getRaidTick() / maxTick) > 1) {
				this.hurt(this.damageSources().genericKill(), 1024);
				if (this.getKillCredit() == this || this.getKillCredit() == null) {
					double x = this.getX();
					double y = this.getY();
					double z = this.getZ();
					AABB area = new AABB(x - 64, y - 64, z - 64, x + 64, y + 64, z + 64);
					List<ServerPlayer> nearPlayers = level().getEntitiesOfClass(ServerPlayer.class, area);
					if (deathTime <= 0) {
						for (ServerPlayer serverPlayer : nearPlayers) {
							serverPlayer.awardStat(Stats.ENTITY_KILLED.get(this.getType()));
						}
					}
				}
			}
			//开始袭击
			if (this.getStartTick() <= -30) {
				if (!this.level().isClientSide()) {
					this.setRaidTick(this.getRaidTick() + 1);
				}
			}
			float level = getRaidTick() / maxTick;
			//生成生物
			spawnEntityTick(level);
			//特殊精英与boss逻辑
			{
				if (getRaidTick() >= maxTick * 7/8f && !isSummonedEliteOrBoss) {
					String strings = "boss";
					if (this.getBossRaid() == 0) {
						EntityType<?> randomEntity = entityTypeFind(strings);
						summonEntity(randomEntity);
						this.isSummonedEliteOrBoss = true;
					}
					else {
						this.isSummonedEliteOrBoss = true;
					}
				}
				if (this.isSummonedEliteOrBoss && this.getBossRaid() != 0) {
					bossRaidTick(this.getBossRaid());
				}
			}
		}
		{
			this.setNoGravity(true);
			this.setJumping(false);
			if (!this.level().isClientSide) {
				this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
				this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
				this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
			}
			if (!level().isClientSide && getBossMusic() != null) {
				if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
					this.level().broadcastEntityEvent(this, (byte) 67);
				} else {
					this.level().broadcastEntityEvent(this, (byte) 68);
				}
			}
		}
	}
	public void spawnEntityTick(float level) {
		if (this.getStartTick() > -30) {
			return;
		}
		if (level > 1) {
			return;
		}
		if (this.isSummonedEliteOrBoss && this.getBossRaid() != 1)
			return;
		if (getRaidTick() == 20 || getRaidTick() == 25 || getRaidTick() == 30 || getRaidTick() == 35 || getRaidTick() == 40) {
			EntityType<?> randomEntity = entityTypeFind("i");
			summonEntity(randomEntity);
		}
		if (getRaidTick() > 40) {
			this.summonMaxTick ++;
			String strings;
			float randomAbout;
			if (level <= 0.2f) {
				strings = "i";
				randomAbout = 10f;
			}
			else if (level <= 0.4f) {
				strings = "ii";
				randomAbout = 9f;
			}
			else if (level <= 0.6f) {
				strings = "iii";
				randomAbout = 8f;
			}
			else if (level <= 0.8f) {
				strings = "iv";
				randomAbout = 7.5f;
			}
			else {
				strings = "v";
				randomAbout = 5;
			}
			if (this.getRandom().nextInt((int) (randomAbout * 20)) == 1 || this.summonMaxTick >= (int) (randomAbout * 20)) {
				this.summonMaxTick = 0;
				EntityType<?> randomEntity = entityTypeFind(strings);
				summonEntity(randomEntity);
			}
		}
	}
	public void bossRaidTick(int type) {
		if (!this.level().isClientSide()) {
			this.setBossSummonedRaidTick(this.getBossSummonedRaidTick() + 1);
		}
		//黑金军主
		if (type==1) {
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			//boss生成
			if (this.getBossSummonedRaidTick() == 1) {
				if (this.level() instanceof ServerLevel serverLevel) {
					TheBlackGoldMarshalEntity theBlackGoldMarshalEntity = BGAEntityType.THE_BLACK_GOLD_MARSHAL.get().spawn(serverLevel, this.getOnPos().above().above(), MobSpawnType.MOB_SUMMONED);
					if (theBlackGoldMarshalEntity != null) {
						if (serverLevel.getBlockEntity(new BlockPos((int) this.getStartX(), (int) this.getStartY(), (int) this.getStartZ())) instanceof NetherSiphonCoreEntity netherSiphonCoreEntity) {
							netherSiphonCoreEntity.portalUUID = theBlackGoldMarshalEntity.getUUID();
							BGA.LOGGER.info(theBlackGoldMarshalEntity.getUUID() + "   theBlackGoldMarshalEntityUUID");
							BGA.LOGGER.info(netherSiphonCoreEntity.portalUUID + "   forcePortalUUID");
							BGA.LOGGER.info(this.getUUID() + "   portalUUID");
							netherSiphonCoreEntity.setChanged();
						}
						theBlackGoldMarshalEntity.setChallenge(this.isChallenge());
						this.setChallenge(false);
						theBlackGoldMarshalEntity.setRaidLevel(this.getRaidLevel());
						Player targetPlayer = this.getTargetPlayer();
						if (targetPlayer == null && this.getTarget() instanceof Player playerTarget) {
							targetPlayer = playerTarget;
						}
						if (targetPlayer != null) {
							theBlackGoldMarshalEntity.setTargetPlayer(targetPlayer);
						}
						theBlackGoldMarshalEntity.setTarget(this.getTarget());
						theBlackGoldMarshalEntity.setStartPos(new BlockPos((int) this.getStartX(), (int) this.getStartY(), (int) this.getStartZ()));
						if (teams != null) {
							serverLevel.getScoreboard().addPlayerToTeam(theBlackGoldMarshalEntity.getStringUUID(), teams);
						}
						for (int n = 0; n < 20; ++n) {
							serverLevel.sendParticles(BGAParticleTypes.PORTAL_POINT.get(), theBlackGoldMarshalEntity.getRandomX(0.5), theBlackGoldMarshalEntity.getRandomY(), theBlackGoldMarshalEntity.getRandomZ(0.5), 0, 0, 0, 0, 0.125f);
						}
						ParticlesUse.summonParticle(serverLevel, theBlackGoldMarshalEntity, theBlackGoldMarshalEntity.getX(), theBlackGoldMarshalEntity.getY(), theBlackGoldMarshalEntity.getZ(),
								0x3f0461, 0xffca00);

						double x = theBlackGoldMarshalEntity.getX();
						double y = theBlackGoldMarshalEntity.getY();
						double z = theBlackGoldMarshalEntity.getZ();
						AABB area = new AABB(x - 64, y - 64, z - 64, x + 64, y + 64, z + 64);
						List<ServerPlayer> nearPlayers = serverLevel.getEntitiesOfClass(ServerPlayer.class, area);
						nearPlayers.removeIf(ServerPlayer::isSpectator);
						for (Player players : nearPlayers) {
							Component component = Component.translatable("boss.blackgoldalliance.the_black_gold_marshal", players.getDisplayName()).withStyle(ChatFormatting.GOLD);
							players.sendSystemMessage(component);
							players.displayClientMessage(component, true);
						}
					}
				}
			}
			if (this.getBossSummonedRaidTick() >= 40) {
				this.hurt(this.damageSources().genericKill(), 1024);
			}
		}
	}
	//生成类型
	public EntityType<?> entityTypeFind(String string) {
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
		TagKey<EntityType<?>> tagKey = TagKey.create(
				ForgeRegistries.ENTITY_TYPES.getRegistryKey(),
				new ResourceLocation(BGA.MODID, "piglin_raid/" + strings + "/" + string)
		);

		List<EntityType<?>> entityTypes = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags())
				.getTag(tagKey)
				.stream()
				.toList();

		if (!entityTypes.isEmpty()) {
			return entityTypes.get(level().random.nextInt(entityTypes.size()));
		}
		return EntityType.ARROW;
	}
	public static EntityType<?> entityTypeFind(Level level, String string, String string2) {
		TagKey<EntityType<?>> tagKey = TagKey.create(
				ForgeRegistries.ENTITY_TYPES.getRegistryKey(),
				new ResourceLocation(BGA.MODID, "piglin_raid/" + string + "/" + string2)
		);

		List<EntityType<?>> entityTypes = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags())
				.getTag(tagKey)
				.stream()
				.toList();

		if (!entityTypes.isEmpty()) {
			return entityTypes.get(level.random.nextInt(entityTypes.size()));
		}
		return EntityType.ARROW;
	}
	public void summonEntity(EntityType<?> entityType) {
		if (!this.isAlive()) {
			return;
		}
		if (this.level() instanceof ServerLevel serverLevel) {
			if (entityType == null) {
				return;
			}
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			PortalPointEntity portalPointEntity = BGAEntityType.PORTAL_POINT.get().spawn(serverLevel, this.getOnPos().above().above(), MobSpawnType.MOB_SUMMONED);
			if (portalPointEntity != null) {
				portalPointEntity.setSelfX((float) this.getX());
				portalPointEntity.setSelfY((float) this.getY());
				portalPointEntity.setSelfZ((float) this.getZ());
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(this, 20);
				float targetPosX = summonPos.getX();
				float targetPosY = summonPos.getY();
				float targetPosZ = summonPos.getZ();

				portalPointEntity.setTargetAddX(targetPosX - (float) this.getX());
				portalPointEntity.setTargetAddY(targetPosY - (float) this.getY());
				portalPointEntity.setTargetAddZ(targetPosZ - (float) this.getZ());
				portalPointEntity.setRaidLevel(this.getRaidLevel());
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(portalPointEntity.getStringUUID(), teams);
				}

				String string = entityType.toString();
				String cleanedEntityType = string.replaceFirst("^entity\\.", "").replaceFirst("\\.", ":");
				portalPointEntity.setEntityType(cleanedEntityType);
				portalPointEntity.setEntityNeedDiscard(getBossRaid() == 0 && this.isChallenge());
				int maxTick = (int) ((OtherMainConfig.PiglinRaidNetherPortalRoundTime * 20) + (20 * OtherMainConfig.PiglinRaidNetherPortalLevelAddRoundTime * (getRaidLevel() - 1)));

				portalPointEntity.setEntityNeedDiscardTick((int) (maxTick - getRaidTick()));
				portalPointEntity.setBlackGoldAlliance(this.isBlackGoldAlliance());
				portalPointEntity.setSelfPortal(this.getUUID());
			}
		}
		if (!this.isSilent()) {
			this.playSound(SoundEvents.AMETHYST_BLOCK_HIT, 1.0f, 1.0f);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		//站立动画
		this.idleAnimationState.startIfStopped(this.tickCount);
		if (!this.level().isClientSide()) {
			this.setStartTick(Math.max(-30, this.getStartTick() - 1));
		}
		if (this.getStartTick() <= (int) (140 - 6.5f * 20)) {
			if (this.level().isClientSide()) {
				for(int i = 0; i < 8; ++i) {
					this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.333d), this.getRandomY() - 0.25D, this.getRandomZ(0.333d), (this.random.nextDouble() - 0.5D), -this.random.nextDouble(), (this.random.nextDouble() - 0.5D));
					this.level().addParticle(ParticleTypes.REVERSE_PORTAL, this.getRandomX(0.333d), this.getRandomY() - 0.25D, this.getRandomZ(0.333d), (this.random.nextDouble() - 0.5D), -this.random.nextDouble(), (this.random.nextDouble() - 0.5D));
				}
			}
		}

		if (this.getStartTick() == (int) (140 - 0.5f * 20) ||
				this.getStartTick() == (int) (140 - 1f * 20) ||
				this.getStartTick() == (int) (140 - 1.42f * 20) ||
				this.getStartTick() == (int) (140 - 1.83f * 20) ||
				this.getStartTick() == (int) (140 - 2.21f * 20) ||
				this.getStartTick() == (int) (140 - 2.54f * 20) ||
				this.getStartTick() == (int) (140 - 2.88f * 20) ||
				this.getStartTick() == (int) (140 - 3.21f * 20) ||
				this.getStartTick() == (int) (140 - 3.54f * 20) ||
				this.getStartTick() == (int) (140 - 3.83f * 20) ||
				this.getStartTick() == (int) (140 - 4.13f * 20) ||
				this.getStartTick() == (int) (140 - 4.38f * 20) ||
				this.getStartTick() == (int) (140 - 4.63f * 20) ||
				this.getStartTick() == (int) (140 - 4.88f * 20) ||
				this.getStartTick() == (int) (140 - 5.13f * 20) ||
				this.getStartTick() == (int) (140 - 5.33f * 20) ||
				this.getStartTick() == (int) (140 - 5.58f * 20) ||
				this.getStartTick() == (int) (140 - 5.83f * 20)) {
			if (!this.isSilent()) {
				this.playSound(SoundEvents.STONE_PLACE, 2.0f, 1.0f);
			}
		}

		if (this.getStartTick() > 0) {
			OtherParticlesUse.PiglinRaidNetherPortalBuild(this);
		}
		if (this.getStartTick() == (int) (140 - 6.5f * 20)) {
			if (!this.isSilent()) {
				this.playSound(SoundEvents.PORTAL_TRIGGER, 5.0f, 1.0f);
				this.playSound(SoundEvents.GLASS_PLACE, 5.0f, 1.0f);
				this.playSound(SoundEvents.FIRECHARGE_USE, 5.0f, 1.0f);
			}
		}

		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		this.clearFire();
		//
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		if (!this.level().isClientSide()) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		return super.mobInteract(player, interactionHand);
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
		int maxTick = (int) ((OtherMainConfig.PiglinRaidNetherPortalRoundTime * 20) + (20 * OtherMainConfig.PiglinRaidNetherPortalLevelAddRoundTime * (getRaidLevel() - 1)));
		if (isInvulnerableTo(damageSource) || (getRaidTick() / (maxTick)) > 1) {
			return super.hurt(damageSource, amount);
		}
		if (amount < 4000 && !(damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY))) {
			return false;
		}
		return super.hurt(damageSource, amount);
	}
	public boolean hurtByPart(PiglinRaidNetherPortalPart piglinRaidNetherPortalPart, DamageSource damageSource, float f) {
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
	public boolean isAlliedTo(Entity entity) {
		if (entity == null) {
			return false;
		}
		if (entity == this) {
			return true;
		}
		if (super.isAlliedTo(entity)) {
			return true;
		}
		if (entity instanceof LivingEntity livingEntity && EntityFactionFind.isFaction(this, livingEntity)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		return false;
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
				EntityFactionFind.isFaction(this, livingEntity)
		) {
			return false;
		}
		return super.canAttack(livingEntity);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		float faces = serverLevelAccessor.getRandom().nextFloat();
		float facing = 0f;
		if (faces > 0.75f) {
			facing = 90f;
		}
		else if (faces > 0.5f) {
			facing = 180f;
		}
		else if (faces > 0.25f) {
			facing = 270f;
		}
		this.setYRot(facing);
		this.setYHeadRot(facing);
		this.setYBodyRot(facing);
		if (!this.level().isClientSide()) {
			this.setFacing(facing);
			this.setAnimTick(140);
			this.setStartTick(140);
			this.setAnimationState("start");
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 67) {
			BossMusicPlayer.playBossMusic(this);
		}
		else if (by == 68) {
			BossMusicPlayer.stopBossMusic(this);
		}
		else {
			super.handleEntityEvent(by);
		}
	}

	public ResourceLocation getDefaultLootTable() {
		if (!this.isChallenge() || this.isVictory()) {
			return this.getType().getDefaultLootTable();
		}
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


	@Override
	public void tickDeath() {
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(40);
				this.setStartTick(0);
				this.setAnimationState("dead");
			}
			//公示
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			AABB area = new AABB(x - 64, y - 64, z - 64, x + 64, y + 64, z + 64);
			List<ServerPlayer> nearPlayers = level().getEntitiesOfClass(ServerPlayer.class, area);
			nearPlayers.removeIf(serverPlayer -> serverPlayer.isSpectator());
			if (this.isChallenge()) {
				for (Player player : nearPlayers) {
					NetherSiphonCoreEntity.SetDefeatTheHighestLevelPiglinRaid(player, Math.max(this.getRaidLevel(), NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(player)));
				}
			}
			if (this.getTargetPlayer() != null && this.getTargetPlayer() instanceof ServerPlayer serverPlayer) {
				if (nearPlayers.contains(serverPlayer)) {
					AdvancementEvent.AdvancementGive(serverPlayer, isBlackGoldAlliance() ? "blackgoldalliance:mortal_enmity" : "blackgoldalliance:an_example_to_all");
					if (this.isChallenge()) {
						Component component;
						if (this.isVictory()) {
							component = Component.translatable("message.blackgoldalliance.piglin_win").withStyle(ChatFormatting.GOLD);
						} else {
							component = Component.translatable("message.blackgoldalliance.player_win", NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(serverPlayer)).withStyle(ChatFormatting.GOLD);
						}
						serverPlayer.sendSystemMessage(component);
						serverPlayer.displayClientMessage(component, true);
					}
					nearPlayers.remove(serverPlayer);
				}
			}
			Component component;
			for (ServerPlayer player : nearPlayers) {
				if (this.isChallenge()) {
					if (this.isVictory()) {
						component = Component.translatable("message.blackgoldalliance.piglin_win").withStyle(ChatFormatting.GOLD);
					} else {
						component = Component.translatable("message.blackgoldalliance.player_win", NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(player)).withStyle(ChatFormatting.GOLD);
					}
					player.sendSystemMessage(component);
					player.displayClientMessage(component, true);
				}
				AdvancementEvent.AdvancementGive(player, isBlackGoldAlliance() ? "blackgoldalliance:mortal_enmity" : "blackgoldalliance:an_example_to_all");
			}
			//结算清除时间
			if (getBossRaid() == 0 && this.isChallenge()) {
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

			//胜利之舞
			if (this.isVictory() && this.isChallenge()) {
				List<AbstractPiglin> list = this.level().getEntitiesOfClass(AbstractPiglin.class,
						this.getBoundingBox().inflate(64, 64, 64));
				for (AbstractPiglin find : list) {
					if (find == null) continue;
					if ((this.distanceTo(find)) > 64) continue;
					if (!AttackFind.SameFactionAvoidDamage(this, find, false)) continue;
					if (find instanceof PiglinRaiderEntity piglinRaiderEntity) {
						if (!this.level().isClientSide()) {
							piglinRaiderEntity.setAnimTick(60);
							piglinRaiderEntity.setAnimationState("dance");
						}
					}
					else if (find instanceof BlackGoldPiglinEntity blackGoldPiglinEntity) {
						if (!this.level().isClientSide()) {
							blackGoldPiglinEntity.setAnimTick(60);
							blackGoldPiglinEntity.setAnimationState("dance");
						}
					}
					else if (find instanceof Piglin piglin) {
						if (!this.level().isClientSide()) {
							piglin.getBrain().setMemory(MemoryModuleType.DANCING, true);
						}
					}
				}
			}
		}
		++this.deathTime;

		if (this.deathTime == 20 && !this.level().isClientSide() && !this.isRemoved()) {
			OtherParticlesUse.PiglinRaidNetherPortalDestroy(this);
			for (int i = 0; i < 18; ++i) {
				if (!this.isSilent()) {
					this.playSound(SoundEvents.STONE_BREAK, 2.0f, 1.0f);
				}
			}
		}

		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	private UUID persistentAngerTarget;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}


	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uUID) {
		this.persistentAngerTarget = uUID;
	}
}
