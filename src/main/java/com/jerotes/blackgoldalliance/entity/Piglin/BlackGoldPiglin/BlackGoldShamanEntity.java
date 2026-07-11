package com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin;

import com.jerotes.blackgoldalliance.entity.MagicSummoned.ZombiePigman.ZombiePigmanEntity;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.goal.*;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.blackgoldalliance.spell.OtherSpellType;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesAddSpellAttackGoal;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.goal.JerotesMainSpellAttackGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellList;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlackGoldShamanEntity extends BlackGoldPiglinEntity implements EliteEntity {
	private static final EntityDataAccessor<Integer> MAIN_SPELL_COOLDOWN = SynchedEntityData.defineId(BlackGoldShamanEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ADD_SPELL_COOLDOWN = SynchedEntityData.defineId(BlackGoldShamanEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPELL_COOLDOWN = SynchedEntityData.defineId(BlackGoldShamanEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPELL_TYPE = SynchedEntityData.defineId(BlackGoldShamanEntity.class, EntityDataSerializers.INT);

	public BlackGoldShamanEntity(EntityType<? extends BlackGoldShamanEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.6f);
		xpReward = 80;
		this.setCanPickUpLoot(false);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 125);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5f);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.4f);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 240, 240, 0.5f));
		this.goalSelector.addGoal(1, new ShamanCombatMagicAttackGoal<>(this, 0.1, 20, 15.0f));
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
//				if (BlackGoldShamanEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
//				if (BlackGoldShamanEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
		return 1.5f;
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


	public boolean firstStart = true;
	public int spellLevel = 3;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public void setSpellType(int n){
		this.getEntityData().set(SPELL_TYPE, n);
	}
	public int getSpellType(){
		return this.getEntityData().get(SPELL_TYPE);
	}
	public void setSpellCooldown(int n){
		this.getEntityData().set(SPELL_COOLDOWN, n);
	}
	public int getSpellCooldown(){
		return this.getEntityData().get(SPELL_COOLDOWN);
	}
	public void setMainSpellCooldown(int n){
		this.getEntityData().set(MAIN_SPELL_COOLDOWN, n);
	}
	public int getMainSpellCooldown(){
		return this.getEntityData().get(MAIN_SPELL_COOLDOWN);
	}
	public void setAddSpellCooldown(int n){
		this.getEntityData().set(ADD_SPELL_COOLDOWN, n);
	}
	public int getAddSpellCooldown(){
		return this.getEntityData().get(ADD_SPELL_COOLDOWN);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putBoolean("FirstStart", this.firstStart);
		compoundTag.putInt("SpellType", this.getSpellLevel());
		compoundTag.putInt("SpellCooldown", this.getSpellCooldown());
		compoundTag.putInt("MainSpellCooldown", this.getMainSpellCooldown());
		compoundTag.putInt("AddSpellCooldown", this.getAddSpellCooldown());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.firstStart = compoundTag.getBoolean("FirstStart");
		this.setSpellType(compoundTag.getInt("SpellType"));
		this.setSpellCooldown(compoundTag.getInt("SpellCooldown"));
		this.setMainSpellCooldown(compoundTag.getInt("MainSpellCooldown"));
		this.setAddSpellCooldown(compoundTag.getInt("AddSpellCooldown"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SPELL_COOLDOWN, 0);
		this.getEntityData().define(MAIN_SPELL_COOLDOWN, 0);
		this.getEntityData().define(ADD_SPELL_COOLDOWN, 0);
		this.getEntityData().define(SPELL_TYPE, 0);
	}
	@Override
	public boolean stopUseMainSpellInGoal() {
		return this.getMainSpellCooldown() > 0;
	}
	@Override
	public boolean stopUseAddSpellInGoal() {
		return this.getAddSpellCooldown() > 0;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		//战斗
		if (this.getTarget() == null) {
			this.firstStart = true;
		}
		//施法
		if (this.getTarget() != null) {
			if (!this.level().isClientSide()) {
				this.getLookControl().setLookAt(this.getTarget(), 30.0f, 30.0f);
				this.lookAt(this.getTarget(), 30.0f, 30.0f);
			}
			if (this.getMainSpellCooldown() <= 0 && this.getSpellCooldown() <= 0 && Main.getJerotesPersistentData(this).getDouble("jerotes_spell_cooldown") <= 0) {
				boolean use = false;
				if (Main.getJerotesPersistentData(this.getTarget()).getDouble("jerotes_spell_cooldown") > 0) {
					//法术列表-法术反制
					SpellList.Counterspell(this.getSpellLevel(), this, this.getTarget()).spellUse();
					use = true;
				}
				if (use) {
					if (!this.level().isClientSide()) {
						this.setAnimTick(20);
						this.setAnimationState("longAttack1");
						this.setMainSpellCooldown(60);
						this.setSpellCooldown(30);
					}
				}
			}
			if (this.getAddSpellCooldown() <= 0 && this.getSpellCooldown() <= 0 && Main.getJerotesPersistentData(this).getDouble("jerotes_spell_cooldown") <= 0) {
				boolean use = false;
				if (this.getTarget().distanceTo(this) < 3) {
					//法术列表-迷踪步
					SpellList.MistyStep(this.getSpellLevel(), this, this.getTarget()).spellUse();
					use = true;
				}
				if (use) {
					if (!this.level().isClientSide()) {
						this.setAnimTick(20);
						this.setAnimationState("longAttack2");
						this.setAddSpellCooldown(180);
						this.setSpellCooldown(30);
					}
				}
			}
		}
		//法术
		if (!this.level().isClientSide()) {
			this.setSpellCooldown(Math.max(this.getSpellCooldown() - 1, 0));
			this.setMainSpellCooldown(Math.max(this.getMainSpellCooldown() - 1, 0));
			this.setAddSpellCooldown(Math.max(this.getAddSpellCooldown() - 1, 0));
		}
	}


	@Override
	public List<SpellTypeInterface> SelfMainSpellList() {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		if (this.firstStart) {
			List<ZombiePigmanEntity> list = this.level().getEntitiesOfClass(ZombiePigmanEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
			list.removeIf(summon -> summon.getOwner() != this);
			if (list.size() <= getSpellLevel() * 3) {
				for (int i = 0; i < 6; ++i) {
					//技能列表-召唤萨满制僵尸猪人
					spellList.add(OtherSpellType.BLACKGOLDALLIANCE_CONJURE_SHAMANIC_ZOMBIE_PIGMAN);
				}
				return spellList;
			}
		}
		//技能列表-召唤萨满制僵尸猪人
		spellList.add(OtherSpellType.BLACKGOLDALLIANCE_CONJURE_SHAMANIC_ZOMBIE_PIGMAN);
		//技能列表-诡异吐息
		spellList.add(OtherSpellType.BLACKGOLDALLIANCE_WARPED_BREATH);
		//技能列表-诡异射线
		spellList.add(OtherSpellType.BLACKGOLDALLIANCE_RAY_OF_WARPED);
		//技能列表-诡异炸弹
		spellList.add(OtherSpellType.BLACKGOLDALLIANCE_WARPED_BOMB);
		//技能列表-降咒
		spellList.add(SpellType.JEROTES_BESTOW_CURSE);
		//技能列表-疗伤术
		spellList.add(SpellType.JEROTES_CURE_WOUNDS);
		return spellList;
	}
	@Override
	public List<SpellTypeInterface> SelfAddSpellList() {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		if (this.getHealth() <= getMaxHealth() && this.getTarget() != null && Main.getJerotesPersistentData(this.getTarget()).getDouble("jerotes_spell_cooldown") > 0) {
			//技能列表-法术反制
			spellList.add(SpellType.JEROTES_COUNTERSPELL);
		}
		else {
			//技能列表-迷踪步
			spellList.add(SpellType.JEROTES_MISTY_STEP);
		}
		//技能列表-迷踪步
		spellList.add(SpellType.JEROTES_MISTY_STEP);
		return spellList;
	}
	@Override
	public void SpellUseAfterAttack(String string, MagicType magicType, MagicType magicType2) {
		if (magicType2 == MagicType.MAIN) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("longAttack1");
				this.setMainSpellCooldown(60);
				this.setSpellCooldown(30);
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("longAttack2");
				this.setAddSpellCooldown(180);
				this.setSpellCooldown(30);
			}
		}
		firstStart = false;
	}

	@Override
	public boolean hurt(DamageSource damagesource, float amount) {
		if (isInvulnerableTo(damagesource)) {
			return super.hurt(damagesource, amount);
		}

		if (!this.isNoAi() && (damagesource.is(DamageTypeTags.IS_FALL) ||
				damagesource.is(DamageTypes.CACTUS) ||
				damagesource.is(DamageTypes.SWEET_BERRY_BUSH))) {
			//技能列表-迷踪步
			boolean bl = SpellList.MistyStep(getSpellLevel(), this, this).canUse();
			if (bl) {
				SpellList.MistyStep(getSpellLevel(), this, this).spellUse();
				return false;
			}
		}
		if (!this.isNoAi() && damagesource.getEntity() == null && (damagesource.is(DamageTypeTags.IS_FIRE) ||
				damagesource.is(DamageTypeTags.IS_DROWNING))) {
			if (amount < this.getHealth() * 10){
				//技能列表-迷踪步
				boolean bl = SpellList.MistyStep(getSpellLevel(), this, this).canUse();
				if (bl) {
					SpellList.MistyStep(getSpellLevel(), this, this).spellUse();
					return false;
				}
			}
		}
		if (!damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.getTarget() != null && damagesource.getEntity() instanceof LivingEntity && !this.isNoAi()) {
			if (amount < this.getHealth() * 10 &&
					this.getRandom().nextFloat() > 0.75f){
				//技能列表-迷踪步
				boolean bl = SpellList.MistyStep(getSpellLevel(), this, this).canUse();
				if (bl) {
					SpellList.MistyStep(getSpellLevel(), this, this).spellUse();
					return false;
				}
			}
		}
		if (EntityAndItemFind.MagicResistance(damagesource))
			return super.hurt(damagesource, amount / 5);
		return super.hurt(damagesource, amount);
	}
	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setCombatStyle(4);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(BGAItems.BLACK_GOLD_SHAMAN_STAFF.get());
	}

}