package com.jerotes.blackgoldalliance.entity.Animal;

import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.IBlackGoldPiglin;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.ParticlesUse;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class AnimalHoglinEntity extends Animal implements HoglinBase, JerotesEntity {
	private static final EntityDataAccessor<Boolean> DATA_IMMUNE_TO_ZOMBIFICATION = SynchedEntityData.defineId(AnimalHoglinEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ENTITY_NEED_DISCARD_TICK = SynchedEntityData.defineId(AnimalHoglinEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> SELF_PORTAL = SynchedEntityData.defineId(AnimalHoglinEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final float PROBABILITY_OF_SPAWNING_AS_BABY = 0.2F;
	private static final int MAX_HEALTH = 40;
	private static final float MOVEMENT_SPEED_WHEN_FIGHTING = 0.3F;
	private static final int ATTACK_KNOCKBACK = 1;
	private static final float KNOCKBACK_RESISTANCE = 0.6F;
	private static final int ATTACK_DAMAGE = 6;
	private static final float BABY_ATTACK_DAMAGE = 0.5F;
	private static final int CONVERSION_TIME = 300;
	private int attackAnimationRemainingTicks;
	private int timeInOverworld;
	private boolean cannotBeHunted;
	public AnimalHoglinEntity(EntityType<? extends AnimalHoglinEntity> p_34488_, Level p_34489_) {
		super(p_34488_, p_34489_);
		this.xpReward = 5;
	}

	public boolean canBeLeashed(Player p_34506_) {
		return !this.isLeashed();
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.KNOCKBACK_RESISTANCE, (double)0.6F).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.ATTACK_DAMAGE, 6.0D);
	}

	public boolean doHurtTarget(Entity p_34491_) {
		if (!(p_34491_ instanceof LivingEntity)) {
			return false;
		} else {
			this.attackAnimationRemainingTicks = 10;
			this.level().broadcastEntityEvent(this, (byte)4);
			this.playSound(SoundEvents.HOGLIN_ATTACK, 1.0F, this.getVoicePitch());
			return HoglinBase.hurtAndThrowTarget(this, (LivingEntity)p_34491_);
		}
	}

	protected void blockedByShield(LivingEntity p_34550_) {
		if (this.isAdult()) {
			HoglinBase.throwTarget(this, p_34550_);
		}

	}

	public boolean hurt(DamageSource p_34503_, float p_34504_) {
		boolean flag = super.hurt(p_34503_, p_34504_);
		if (this.level().isClientSide) {
			return false;
		} else {
			return flag;
		}
	}

	protected void customServerAiStep() {
		this.level().getProfiler().push("hoglinBrain");
		this.level().getProfiler().pop();
		if (this.isConverting()) {
			++this.timeInOverworld;
			if (this.timeInOverworld > 300 && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, EntityType.ZOGLIN, (timer) -> this.timeInOverworld = timer)) {
				this.playSoundEvent(SoundEvents.HOGLIN_CONVERTED_TO_ZOMBIFIED);
				this.finishConversion((ServerLevel)this.level());
			}
		} else {
			this.timeInOverworld = 0;
		}

	}
	public LivingEntity getOwner() {
		return null;
	}

	@Override
	public void tick() {
		super.tick();
		//消失
		if (!this.level().isClientSide()) {
			this.setEntityNeedDiscardTick(Math.max(-1, this.getEntityNeedDiscardTick() - 1));
		}
		if (this.getEntityNeedDiscardTick() == 5 && this.getOwner() == null) {
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.TELEPORT, this.getSoundSource(), 5.0f, 1.0F);
			}
			if (this.level() instanceof ServerLevel serverLevel) {
				ParticlesUse.summonParticle(serverLevel, this, this.getX(), this.getY(), this.getZ(),
						0x3f0461, this instanceof BlackGoldWarHoglinEntity ? 0xffca00 : 0x8a16cf);
			}
			this.remove(Entity.RemovalReason.CHANGED_DIMENSION);
		}
	}
	public void aiStep() {
		if (this.attackAnimationRemainingTicks > 0) {
			--this.attackAnimationRemainingTicks;
		}
		super.aiStep();
	}

	protected void ageBoundaryReached() {
		if (this.isBaby()) {
			this.xpReward = 3;
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.5D);
		} else {
			this.xpReward = 5;
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D);
		}

	}

	public static boolean checkHoglinSpawnRules(EntityType<AnimalHoglinEntity> p_219182_, LevelAccessor p_219183_, MobSpawnType p_219184_, BlockPos p_219185_, RandomSource p_219186_) {
		return !p_219183_.getBlockState(p_219185_.below()).is(Blocks.NETHER_WART_BLOCK);
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34508_, DifficultyInstance p_34509_, MobSpawnType p_34510_, @Nullable SpawnGroupData p_34511_, @Nullable CompoundTag p_34512_) {
		if (p_34508_.getRandom().nextFloat() < 0.2F) {
			this.setBaby(true);
		}

		return super.finalizeSpawn(p_34508_, p_34509_, p_34510_, p_34511_, p_34512_);
	}

	@Override
	public @org.jetbrains.annotations.Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return null;
	}

	public boolean removeWhenFarAway(double p_34559_) {
		return !this.isPersistenceRequired();
	}

	public double getPassengersRidingOffset() {
		return (double)this.getBbHeight() - (this.isBaby() ? 0.2D : 0.15D);
	}

	public InteractionResult mobInteract(Player p_34523_, InteractionHand p_34524_) {
		InteractionResult interactionresult = super.mobInteract(p_34523_, p_34524_);
		if (interactionresult.consumesAction()) {
			this.setPersistenceRequired();
		}

		return interactionresult;
	}

	public void handleEntityEvent(byte p_34496_) {
		if (p_34496_ == 4) {
			this.attackAnimationRemainingTicks = 10;
			this.playSound(SoundEvents.HOGLIN_ATTACK, 1.0F, this.getVoicePitch());
		} else {
			super.handleEntityEvent(p_34496_);
		}

	}

	public int getAttackAnimationRemainingTicks() {
		return this.attackAnimationRemainingTicks;
	}

	public boolean shouldDropExperience() {
		return true;
	}

	public int getExperienceReward() {
		return this.xpReward;
	}

	private void finishConversion(ServerLevel p_34532_) {
		Zoglin zoglin = this.convertTo(EntityType.ZOGLIN, true);
		if (zoglin != null) {
			zoglin.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
			net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, zoglin);
		}

	}

	public boolean isFood(ItemStack p_34562_) {
		return p_34562_.is(Items.CRIMSON_FUNGUS);
	}

	public boolean isAdult() {
		return !this.isBaby();
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_IMMUNE_TO_ZOMBIFICATION, false);
		this.getEntityData().define(ENTITY_NEED_DISCARD_TICK, -1);
		this.getEntityData().define(SELF_PORTAL, Optional.of(this.getUUID()));
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
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.isImmuneToZombification()) {
			compoundTag.putBoolean("IsImmuneToZombification", true);
		}
		compoundTag.putInt("TimeInOverworld", this.timeInOverworld);
		if (this.cannotBeHunted) {
			compoundTag.putBoolean("CannotBeHunted", true);
		}
		compoundTag.putInt("EntityNeedDiscardTick", this.getEntityNeedDiscardTick());;
		compoundTag.putUUID("SelfPortal", this.getSelfPortal());
	}

	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setImmuneToZombification(compoundTag.getBoolean("IsImmuneToZombification"));
		this.timeInOverworld = compoundTag.getInt("TimeInOverworld");
		this.setCannotBeHunted(compoundTag.getBoolean("CannotBeHunted"));
		this.setEntityNeedDiscardTick(compoundTag.getInt("EntityNeedDiscardTick"));
		this.setSelfPortal(compoundTag.getUUID("SelfPortal"));
	}

	public void setImmuneToZombification(boolean p_34565_) {
		this.getEntityData().set(DATA_IMMUNE_TO_ZOMBIFICATION, p_34565_);
	}

	public boolean isConverting() {
		return !this.level().dimensionType().piglinSafe() && !this.isImmuneToZombification() && !this.isNoAi();
	}

	private void setCannotBeHunted(boolean p_34567_) {
		this.cannotBeHunted = p_34567_;
	}

	public boolean canBeHunted() {
		return this.isAdult() && !this.cannotBeHunted;
	}

	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return this.isAggressive() ? SoundEvents.HOGLIN_ANGRY : SoundEvents.HOGLIN_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource p_34548_) {
		return SoundEvents.HOGLIN_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.HOGLIN_DEATH;
	}

	protected SoundEvent getSwimSound() {
		return SoundEvents.HOSTILE_SWIM;
	}

	protected SoundEvent getSwimSplashSound() {
		return SoundEvents.HOSTILE_SPLASH;
	}

	protected void playStepSound(BlockPos p_34526_, BlockState p_34527_) {
		this.playSound(SoundEvents.HOGLIN_STEP, 0.15F, 1.0F);
	}

	protected void playSoundEvent(SoundEvent p_219180_) {
		this.playSound(p_219180_, this.getSoundVolume(), this.getVoicePitch());
	}

	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}


	private boolean isImmuneToZombification() {
		if (this.hasEffect(BGAMobEffects.PIGLIN_BLESSING.get())) {
			return true;
		}
		if (this instanceof IBlackGoldPiglin) {
			return (this.getEntityData().get(DATA_IMMUNE_TO_ZOMBIFICATION) || !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty() || (this instanceof OwnableEntity ownable && ownable.getOwner() != null));
		}
		return this.getEntityData().get(DATA_IMMUNE_TO_ZOMBIFICATION);
	}
}
