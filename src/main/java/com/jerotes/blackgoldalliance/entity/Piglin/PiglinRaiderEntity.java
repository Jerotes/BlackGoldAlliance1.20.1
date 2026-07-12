package com.jerotes.blackgoldalliance.entity.Piglin;

import com.google.common.collect.ImmutableList;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.IBlackGoldPiglin;
import com.jerotes.blackgoldalliance.goal.ForceNearestAttackableTargetGoal;
import com.jerotes.blackgoldalliance.goal.PiglinAvoidSoulFireGoal;
import com.jerotes.blackgoldalliance.goal.PiglinFollowPortalGoal;
import com.jerotes.blackgoldalliance.goal.SwitchTargetToAllyTargetGoal;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class PiglinRaiderEntity extends AbstractPiglin implements CrossbowAttackMob, IBlackGoldPiglin, FactionEntity, InventoryCarrier, SpellUseEntity, UseDaggerEntity, ShiftKeyDownEntity, NeutralMob, WizardEntity, JerotesPiglinEntity, UseThrowEntity, UseThrownJavelinEntity, InventoryEntity, UseBowEntity, UseCrossbowEntity, UseShieldEntity, JerotesEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState longAttack1AnimationState = new AnimationState();
	public AnimationState longAttack2AnimationState = new AnimationState();
	public AnimationState longAttack3AnimationState = new AnimationState();
	public AnimationState heavyAttack1AnimationState = new AnimationState();
	public AnimationState heavyAttack2AnimationState = new AnimationState();
	public AnimationState heavyAttack3AnimationState = new AnimationState();
	public AnimationState throw1AnimationState = new AnimationState();
	public AnimationState throw2AnimationState = new AnimationState();
	public AnimationState shoot1AnimationState = new AnimationState();
	public AnimationState shoot2AnimationState = new AnimationState();
	public AnimationState spear1AnimationState = new AnimationState();
	public AnimationState spear2AnimationState = new AnimationState();
	public AnimationState danceAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();

	private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final UUID SPEED_MODIFIER_BABY_UUID = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
	private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_UUID, "Baby speed boost", 0.20000000298023224, AttributeModifier.Operation.MULTIPLY_BASE);
	private static final EntityDataAccessor<Integer> COMBAT_STYLE = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> USE_SELF_NOT_SPELL_LIST = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BOW_LEVEL = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHIELD_LEVEL = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CAN_CHANGE_INVENTORY = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CAN_CHANGE_MELEE_OR_RANGE = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_WEAPON = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NO_COMBAT_EMPTY_SHIELD = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> CHANGE_INVENTORY_COOLDOWN_TICK = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> ATTACK_ANIM = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ENTITY_NEED_DISCARD_TICK = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> SELF_PORTAL = SynchedEntityData.defineId(PiglinRaiderEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	protected static final ImmutableList<SensorType<? extends Sensor<? super PiglinRaiderEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY, SensorType.PIGLIN_BRUTE_SPECIFIC_SENSOR);
	protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEARBY_ADULT_PIGLINS, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.ANGRY_AT, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.HOME);

	protected Brain.Provider<PiglinRaiderEntity> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
	}
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return PiglinRaiderAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}
	public Brain<PiglinRaiderEntity> getBrain() {
		return (Brain<PiglinRaiderEntity>)super.getBrain();
	}
	public final SimpleContainer inventory = new SimpleContainer(inventoryCount());
	private LazyOptional<?> itemHandler = null;

	public PiglinRaiderEntity(EntityType<? extends PiglinRaiderEntity> type, Level world) {
		super(type, world);
		xpReward = 25;
		setMaxUpStep(0.6f);
		setPersistenceRequired();
		this.applyOpenDoorsAbility();
		this.setCanPickUpLoot(true);
		this.setPathfindingMalus(BlockPathTypes.WATER, 16.0F);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.itemHandler = LazyOptional.of(this::createCombinedHandler);
	}
	private IItemHandler createCombinedHandler() {
		IItemHandlerModifiable handsHandler = new EntityHandsInvWrapper(this);
		IItemHandlerModifiable armorHandler = new EntityArmorInvWrapper(this);
		IItemHandlerModifiable customHandler = new InvWrapper(this.inventory);
		return new CombinedInvWrapper(
				armorHandler,
				handsHandler,
				customHandler
		);
	}

	@Override
	public String getFirstFactionTypeName() {
		return "piglin_raider";
	}
	@Override
	public List<String> getFactionTypeUntilTame() {
		List<String> list = new ArrayList<>();
		list.add(getFirstFactionTypeName());
		list.add("piglin");
		return list;
	}
	@VisibleForDebug
	@Override
	public SimpleContainer getInventory() {
		return this.inventory;
	}

	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		return capability == ForgeCapabilities.ITEM_HANDLER && itemHandler != null &&
				this.isAlive() ? itemHandler.cast() : super.getCapability(capability, facing);
	}
	public void invalidateCaps() {
		super.invalidateCaps();
		if (this.itemHandler != null) {
			LazyOptional<?> oldHandler = this.itemHandler;
			this.itemHandler = null;
			oldHandler.invalidate();
		}
	}

	protected ItemStack addToInventory(ItemStack itemStack) {
		return this.inventory.addItem(itemStack);
	}

	protected boolean canAddToInventory(ItemStack itemStack) {
		return this.inventory.canAddItem(itemStack);
	}

	@Override
	public ItemStack equipItemIfPossible(ItemStack itemStack) {
		EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
		ItemStack itemStack2 = this.getItemBySlot(equipmentSlot);
		boolean bl = this.canReplaceCurrentItem(itemStack, itemStack2);
		if (equipmentSlot.isArmor() && !bl) {
			equipmentSlot = EquipmentSlot.MAINHAND;
			itemStack2 = this.getItemBySlot(equipmentSlot);
			bl = itemStack2.isEmpty();
		}
		if (bl && this.canHoldItem(itemStack)) {
			double d = this.getEquipmentDropChance(equipmentSlot);
			if (this.canAddToInventory(itemStack2)) {
				this.addToInventory(itemStack2);
			}
			else {
				this.spawnAtLocation(itemStack2);
			}

			if (equipmentSlot.isArmor() && itemStack.getCount() > 1) {
				ItemStack itemStack3 = itemStack.copyWithCount(1);
				this.setItemSlotAndDropWhenKilled(equipmentSlot, itemStack3);
				return itemStack3;
			}
			this.setItemSlotAndDropWhenKilled(equipmentSlot, itemStack);
			return itemStack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		return InventoryEntity.canReplaceCurrentItem(this, newItem, oldItem);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 16);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource damageSource, int n, boolean bl) {
		super.dropCustomDeathLoot(damageSource, n, bl);
		this.inventory.removeAllItems().forEach(this::spawnAtLocation);
	}

	private void applyOpenDoorsAbility() {
		if (GoalUtils.hasGroundPathNavigation(this)) {
			((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
		}
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundevent = this.getAmbientSound();
		if (soundevent != null) {
			this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
		}
	}

	@Override
	protected void registerGoals() {
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(1, new JerotesShiftKeyDownGoal(this));
		this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 180, 240, 0.5f));
		this.goalSelector.addGoal(1, new JerotesCombatIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIIMagicAttackGoal<>(this, 0.2, true, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIIIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesCombatIVMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesRangedBowAttackGoal<PiglinRaiderEntity>(this, 0.5, 20, 15.0f));
		this.goalSelector.addGoal(1, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
		this.goalSelector.addGoal(1, new JerotesRangedJavelinAttackGoal<>(this, 1.0, 60, 12.0F, this.meleeOrRangeDistance()));
		this.goalSelector.addGoal(1, new JerotesRangedCrossbowAttackGoal<>(this, 1.0, 15.0f));
		this.goalSelector.addGoal(1, new JerotesSpearUseGoal<>(this,  1.0, 1.0, 10.0f, 2.0f));
		this.goalSelector.addGoal(1, new JerotesPikeUseGoal(this,  1.2f, true));
		this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(this, 1.1, true));
		this.goalSelector.addGoal(3, new PiglinFollowPortalGoal(this, 1.1f));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<ZombifiedPiglin>(this, ZombifiedPiglin.class, 12.0f, 1.2, 1.2, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<Zoglin>(this, Zoglin.class, 12.0f, 1.2, 1.2, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<WitherSkeleton>(this, WitherSkeleton.class, 12.0f, 1.4, 1.4, EntitySelector.NO_SPECTATORS::test){
			@Override
			public boolean canUse() {
				if (PiglinRaiderEntity.this.isAdult()) {
					return false;
				}
				return super.canUse();
			}
		});
		this.goalSelector.addGoal(3, new AvoidEntityGoal<WitherBoss>(this, WitherBoss.class, 12.0f, 1.4, 1.4, EntitySelector.NO_SPECTATORS::test){
			@Override
			public boolean canUse() {
				if (PiglinRaiderEntity.this.isAdult()) {
					return false;
				}
				return super.canUse();
			}
		});
		this.goalSelector.addGoal(3, new PiglinAvoidSoulFireGoal(this, 1.0, 8, 4, 8.0));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.6));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, AbstractPiglin.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 6.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
			@Override
			public boolean canUse() {
				if (PiglinRaiderEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
					return false;
				}
				if (this.target != null && PiglinAi.isWearingGold(this.target)) {
					return false;
				}
				return super.canUse();
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, true){
			@Override
			public boolean canUse() {
				if (PiglinRaiderEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
					return false;
				}
				if (this.target != null && PiglinAi.isWearingGold(this.target)) {
					return false;
				}
				return super.canUse();
			}
		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<WitherSkeleton>(this, WitherSkeleton.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<WitherBoss>(this, WitherBoss.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
		this.targetSelector.addGoal(4, new ForceNearestAttackableTargetGoal<>(this, NetherSiphonCoreForceEntity.class, false, false));
		this.targetSelector.addGoal(1, new SwitchTargetToAllyTargetGoal(this, 32.0));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<PiglinRaiderEntity>(this, true));
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		ItemStack handItem = this.getMainHandItem();
		if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
			handItem = this.getOffhandItem();
		}
		if (InventoryEntity.isRangeJavelin(handItem)) {
			useTridentShoot(this, livingEntity);
			if (!this.level().isClientSide()) {
				this.setAnimTick(10);
				if (handItem == this.getMainHandItem()) {
					this.setAnimationState("throw1");
				} else {
					this.setAnimationState("throw2");
				}
			}
		}
		if (InventoryEntity.isBow(handItem)) {
			useBowShoot(this, livingEntity, f, getBowLevel(), 20);
		}
		if (InventoryEntity.isThrow(handItem)) {
			useThrowShoot(this, livingEntity);
			if (!this.level().isClientSide()) {
				this.setAnimTick(10);
				if (handItem == this.getMainHandItem()) {
					this.setAnimationState("throw1");
				} else {
					this.setAnimationState("throw2");
				}
			}
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.ITEM_THROW, this.getSoundSource(), 0.5f, 0.4f / (this.level().getRandom().nextFloat() * 0.4f + 0.8f));
			}
		}
		if (InventoryEntity.isCrossbow(handItem)) {
			useCrossbowShoot(this, 3.15F);
			if (!this.level().isClientSide()) {
				this.setAnimTick(10);
				this.setAttackAnim(0);
				if (handItem == this.getMainHandItem()) {
					this.setAnimationState("shoot1");
				} else {
					this.setAnimationState("shoot2");
				}
			}
		}
		if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
			if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
				handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
			}
		}
	}

	@Override
	public ItemStack getProjectile(ItemStack itemStack) {
		if (itemStack.getItem() instanceof ProjectileWeaponItem projectileWeaponItem) {
			Predicate<ItemStack> predicate = projectileWeaponItem.getSupportedHeldProjectiles();
			ItemStack itemStack2 = ProjectileWeaponItem.getHeldProjectile(this, predicate);
			if (predicate.test(this.getMainHandItem())) {
				itemStack2 = this.getMainHandItem();
			}
			else if (predicate.test(this.getOffhandItem())) {
				itemStack2 = this.getOffhandItem();
			}
			else {
				for (int n = 0; n < this.inventoryCount(); ++n) {
					ItemStack finds = this.mobInventory().getItem(n);
					if (predicate.test(finds)) {
						itemStack2 = finds;
						break;
					}
				}
			}
			return itemStack2.isEmpty() ? new ItemStack(Items.ARROW) : itemStack2;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isChargingCrossbow() {
		return this.entityData.get(IS_CHARGING_CROSSBOW);
	}

	@Override
	public void setChargingCrossbow(boolean bl) {
		this.entityData.set(IS_CHARGING_CROSSBOW, bl);
	}

	@Override
	public void shootCrossbowProjectile(LivingEntity livingEntity, ItemStack itemStack, Projectile projectile, float f) {
		this.shootCrossbowProjectile(this, livingEntity, projectile, f, itemStack.getItem() instanceof ItemToolBaseCrossbow itemToolBaseCrossbow ? itemToolBaseCrossbow.getShootingPower(itemStack) : 3.15f);
	}
	@Override
	public void shootCrossbowProjectile(LivingEntity livingEntity, LivingEntity livingEntity2, Projectile projectile, float f, float f2) {
		double d0 = livingEntity2.getX() - livingEntity.getX();
		double d1 = livingEntity2.getZ() - livingEntity.getZ();
		double d2 = Math.sqrt(d0 * d0 + d1 * d1);
		double d3 = livingEntity2.getY(0.3333333333333333) - projectile.getY() + d2 * 0.1;
		Vector3f vector3f = this.getProjectileShotVector(livingEntity, new Vec3(d0, d3, d1), f);
		projectile.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), f2, Math.max(0, 20 - getBowLevel()) / 5f);
		livingEntity.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / (livingEntity.getRandom().nextFloat() * 0.4F + 0.8F));
	}

	@Override
	public void onCrossbowAttackPerformed() {
		this.noActionTime = 0;
	}
	@Override
	public AbstractArrow getCustomArrow(ItemStack itemStack, float f) {
		return ProjectileUtil.getMobArrow(this, itemStack, f);
	}

	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem instanceof BowItem || projectileWeaponItem instanceof CrossbowItem || super.canFireProjectileWeapon(projectileWeaponItem);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.isAggressive() ? SoundEvents.PIGLIN_ANGRY : SoundEvents.PIGLIN_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PIGLIN_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (this.isDamageSourceBlocked(damageSource)) {
			return SoundEvents.SHIELD_BLOCK;
		}
		return SoundEvents.PIGLIN_HURT;
	}
	@Override
	protected boolean isImmuneToZombification() {
		return super.isImmuneToZombification() || !this.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
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
	protected void finishConversion(ServerLevel serverLevel) {
		SimpleContainer simpleContainer = this.mobInventory();
		for (int i = 0; i < simpleContainer.getMaxStackSize(); ++i) {
			this.spawnAtLocation(simpleContainer.getItem(i));
		}
		super.finishConversion(serverLevel);
	}

	@Override
	public PiglinArmPose getArmPose() {
		return this.isAggressive() && this.isHoldingMeleeWeapon() ? PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON : PiglinArmPose.DEFAULT;
	}

	@Override
	protected void playConvertedSound() {
		this.playSound(SoundEvents.PIGLIN_CONVERTED_TO_ZOMBIFIED, 1.0F, this.getVoicePitch());
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
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	public boolean hasLineOfSight(Entity target) {
		if (target instanceof NetherSiphonCoreForceEntity netherSiphonCoreForceEntity) {
			return super.hasLineOfSight(target) || Main.canSee(netherSiphonCoreForceEntity,this);
		}
		return super.hasLineOfSight(target);
	}
	public double getPassengersRidingOffset() {
		return (double)this.getBbHeight() * 0.92;
	}
	protected float getStandingEyeHeight(Pose p_34740_, EntityDimensions p_34741_) {
		float f = super.getStandingEyeHeight(p_34740_, p_34741_);
		return this.isBaby() ? f - 0.82F : f;
	}
	@Override
	public boolean canHunt() {
		return false;
	}
	public boolean selfChangeAttackAnim() {
		return false;
	}
	public boolean selfUseAnim() {
		return false;
	}
	public boolean selfAttackAbout() {
		return false;
	}

	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float attackAnimProgress = 0.0f;
	public float useItemRemainingTicksProgress = 0.0f;
	public int shieldCoolDown;
	public int shieldCanUse = 1;
	//    @Override
//    public boolean isAdult() {
//        return true;
//    }
//    @Override
//    public boolean isBaby() {
//        return false;
//    }
	@Override
	public boolean shieldCanUse() {
		return this.shieldCanUse == 1 && this.shieldCoolDown <= 0;
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
	}
	@Override
	public SimpleContainer mobInventory() {
		return inventory;
	}
	@Override
	public int inventoryCount() {
		return 8;
	}
	@Override
	public boolean isCanChangeInventory() {
		return this.getEntityData().get(CAN_CHANGE_INVENTORY);
	}
	@Override
	public void setCanChangeInventory(boolean bl) {
		this.getEntityData().set(CAN_CHANGE_INVENTORY, bl);
	}
	@Override
	public boolean isCanChangeMeleeOrRange() {
		return this.getEntityData().get(CAN_CHANGE_MELEE_OR_RANGE);
	}
	@Override
	public void setCanChangeMeleeOrRange(boolean bl) {
		this.getEntityData().set(CAN_CHANGE_MELEE_OR_RANGE, bl);
	}
	@Override
	public boolean NonCombatEmptyWeapon() {
		return this.isNoCombatEmptyWeapon();
	}
	@Override
	public boolean NonCombatEmptyShield() {
		return this.isNoCombatEmptyShield();
	}
	@Override
	public int changeInventoryCooldownTick() {
		return this.getChangeInventoryCooldownTick();
	}
	public boolean isNoCombatEmptyWeapon() {
		return this.getEntityData().get(NO_COMBAT_EMPTY_WEAPON);
	}
	public void setNoCombatEmptyWeapon(boolean bl) {
		this.getEntityData().set(NO_COMBAT_EMPTY_WEAPON, bl);
	}
	public boolean isNoCombatEmptyShield() {
		return this.getEntityData().get(NO_COMBAT_EMPTY_SHIELD);
	}
	public void setNoCombatEmptyShield(boolean bl) {
		this.getEntityData().set(NO_COMBAT_EMPTY_SHIELD, bl);
	}
	public int getChangeInventoryCooldownTick() {
		return this.getEntityData().get(CHANGE_INVENTORY_COOLDOWN_TICK);
	}
	public void setChangeInventoryCooldownTick(int n) {
		this.getEntityData().set(CHANGE_INVENTORY_COOLDOWN_TICK, n);
	}
	@Override
	public void SpellUseAfterAttack(String string, MagicType magicType, MagicType magicType2) {
		if (!this.level().isClientSide()) {
			this.setSpellTick(10);
		}
	}
	public void setBaby(boolean bl) {
		this.getEntityData().set(DATA_BABY_ID, bl);
		if (!this.level().isClientSide()) {
			AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
			attributeinstance.removeModifier(SPEED_MODIFIER_BABY);
			if (bl) {
				attributeinstance.addTransientModifier(SPEED_MODIFIER_BABY);
			}
		}
	}
	public boolean isBaby() {
		return (Boolean)this.getEntityData().get(DATA_BABY_ID);
	}
	@Override
	public List<SpellTypeInterface> SelfMainSpellList() {
		return new ArrayList<>();
	}
	@Override
	public List<SpellTypeInterface> SelfAddSpellList() {
		return new ArrayList<>();
	}
	public int spellLevel = 1;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public void setSpellTick(int n){
		this.getEntityData().set(SPELL_TICK, n);
	}
	public int getSpellTick(){
		return this.getEntityData().get(SPELL_TICK);
	}
	public void setBowLevel(int n){
		this.getEntityData().set(BOW_LEVEL, n);
	}
	public int getBowLevel(){
		return this.getEntityData().get(BOW_LEVEL);
	}
	public void setShieldLevel(int n){
		this.getEntityData().set(SHIELD_LEVEL, n);
	}
	public int getShieldLevel(){
		return this.getEntityData().get(SHIELD_LEVEL);
	}
	public int getCombatStyle() {
		return this.getEntityData().get(COMBAT_STYLE);
	}
	public void setCombatStyle(int n) {
		this.getEntityData().set(COMBAT_STYLE, n);
	}
	public boolean isUseSelfNotStringSpellList() {
		return this.getEntityData().get(USE_SELF_NOT_SPELL_LIST);
	}
	public void setUseSelfNotStringSpellList(boolean bl) {
		this.getEntityData().set(USE_SELF_NOT_SPELL_LIST, bl);
	}
	public boolean isMagicUseStyle() {
		return this.getSpellTick() > 0;
	}
	@Override
	public boolean isSpellHumanoid() {
		return true;
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
		if (Objects.equals(animation, "attack")){
			return 1;
		}
		else if (Objects.equals(animation, "longAttack1")){
			return 2;
		}
		else if (Objects.equals(animation, "longAttack2")){
			return 3;
		}
		else if (Objects.equals(animation, "longAttack3")){
			return 4;
		}
		else if (Objects.equals(animation, "heavyAttack1")){
			return 5;
		}
		else if (Objects.equals(animation, "heavyAttack2")){
			return 6;
		}
		else if (Objects.equals(animation, "heavyAttack3")){
			return 7;
		}
		else if (Objects.equals(animation, "throw1")){
			return 8;
		}
		else if (Objects.equals(animation, "throw2")){
			return 9;
		}
		else if (Objects.equals(animation, "shoot1")){
			return 10;
		}
		else if (Objects.equals(animation, "shoot2")){
			return 11;
		}
		else if (Objects.equals(animation, "spear1")){
			return 12;
		}
		else if (Objects.equals(animation, "spear2")){
			return 13;
		}
		else if (Objects.equals(animation, "dance")){
			return 14;
		}
		else if (Objects.equals(animation, "dead")){
			return 15;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attackAnimationState);
		list.add(this.longAttack1AnimationState);
		list.add(this.longAttack2AnimationState);
		list.add(this.longAttack3AnimationState);
		list.add(this.heavyAttack1AnimationState);
		list.add(this.heavyAttack2AnimationState);
		list.add(this.heavyAttack3AnimationState);
		list.add(this.throw1AnimationState);
		list.add(this.throw2AnimationState);
		list.add(this.shoot1AnimationState);
		list.add(this.shoot2AnimationState);
		list.add(this.spear1AnimationState);
		list.add(this.spear2AnimationState);
		list.add(this.danceAnimationState);
		list.add(this.deadAnimationState);
		return list;
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
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
	}
	public int getAttackAnim() {
		return this.getEntityData().get(ATTACK_ANIM);
	}
	public void setAttackAnim(int n) {
		this.getEntityData().set(ATTACK_ANIM, n);
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
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		if (this.isBaby()) {
			compoundTag.putBoolean("IsBaby", true);
		}
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("AttackAnim", this.getAttackAnim());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("SpellTick", this.getSpellTick());
		compoundTag.putInt("BowLevel", this.getBowLevel());
		compoundTag.putInt("ShieldLevel", this.getShieldLevel());
		compoundTag.putInt("CombatStyle", this.getCombatStyle());
		compoundTag.putBoolean("UseSelfNotStringSpellList", this.isUseSelfNotStringSpellList());
		compoundTag.putBoolean("IsNoCombatEmptyWeapon", this.isNoCombatEmptyWeapon());
		compoundTag.putBoolean("IsNoCombatEmptyShield", this.isNoCombatEmptyShield());
		compoundTag.putInt("ChangeInventoryCooldownTick", this.getChangeInventoryCooldownTick());
		compoundTag.putBoolean("IsCanChangeInventory", this.isCanChangeInventory());
		compoundTag.putBoolean("IsCanChangeMeleeOrRange", this.isCanChangeMeleeOrRange());
		compoundTag.putInt("ShieldCoolDown", this.shieldCoolDown);
		compoundTag.putInt("ShieldCanUse", this.shieldCanUse);
		compoundTag.putInt("EntityNeedDiscardTick", this.getEntityNeedDiscardTick());;
		compoundTag.putUUID("SelfPortal", this.getSelfPortal());
		this.addPersistentAngerSaveData(compoundTag);
		this.writeInventoryToTag(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setBaby(compoundTag.getBoolean("IsBaby"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setAttackAnim(compoundTag.getInt("AttackAnim"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setSpellTick(compoundTag.getInt("SpellTick"));
		this.setBowLevel(compoundTag.getInt("BowLevel"));
		this.setShieldLevel(compoundTag.getInt("ShieldLevel"));
		this.setCombatStyle(compoundTag.getInt("CombatStyle"));
		this.setUseSelfNotStringSpellList(compoundTag.getBoolean("UseSelfNotStringSpellList"));
		this.setNoCombatEmptyWeapon(compoundTag.getBoolean("IsNoCombatEmptyWeapon"));
		this.setNoCombatEmptyShield(compoundTag.getBoolean("IsNoCombatEmptyShield"));
		this.setChangeInventoryCooldownTick(compoundTag.getInt("ChangeInventoryCooldownTick"));
		this.setCanChangeInventory(compoundTag.getBoolean("IsCanChangeInventory"));
		this.setCanChangeMeleeOrRange(compoundTag.getBoolean("IsCanChangeMeleeOrRange"));
		this.shieldCoolDown = compoundTag.getInt("ShieldCoolDown");
		this.shieldCanUse = compoundTag.getInt("ShieldCanUse");
		this.setEntityNeedDiscardTick(compoundTag.getInt("EntityNeedDiscardTick"));
		if (compoundTag.hasUUID("SelfPortal")) {
			this.setSelfPortal(compoundTag.getUUID("SelfPortal"));
		}
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.readInventoryFromTag(compoundTag);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_BABY_ID, false);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ATTACK_ANIM, 10);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(SPELL_TICK, 0);
		this.getEntityData().define(BOW_LEVEL, 15);
		this.getEntityData().define(SHIELD_LEVEL, 1);
		this.getEntityData().define(USE_SELF_NOT_SPELL_LIST, true);
		this.getEntityData().define(COMBAT_STYLE, 1);
		this.getEntityData().define(CAN_CHANGE_INVENTORY, false);
		this.getEntityData().define(CAN_CHANGE_MELEE_OR_RANGE, false);
		this.getEntityData().define(NO_COMBAT_EMPTY_WEAPON, false);
		this.getEntityData().define(NO_COMBAT_EMPTY_SHIELD, false);
		this.getEntityData().define(CHANGE_INVENTORY_COOLDOWN_TICK, 50);
		this.getEntityData().define(IS_CHARGING_CROSSBOW, false);
		this.getEntityData().define(ENTITY_NEED_DISCARD_TICK, -1);
		this.getEntityData().define(SELF_PORTAL, Optional.of(this.getUUID()));
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (DATA_BABY_ID.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (!this.selfUseAnim()) {
			if (ANIM_STATE.equals(entityDataAccessor)) {
				if (this.level().isClientSide()) {
					switch (this.entityData.get(ANIM_STATE)) {
						case 0:
							this.stopAllAnimation();
							break;
						case 1:
							this.attackAnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.attackAnimationState);
							break;
						case 2:
							this.longAttack1AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.longAttack1AnimationState);
							break;
						case 3:
							this.longAttack2AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.longAttack2AnimationState);
							break;
						case 4:
							this.longAttack3AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.longAttack3AnimationState);
							break;
						case 5:
							this.heavyAttack1AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.heavyAttack1AnimationState);
							break;
						case 6:
							this.heavyAttack2AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.heavyAttack2AnimationState);
							break;
						case 7:
							this.heavyAttack3AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.heavyAttack3AnimationState);
							break;
						case 8:
							this.throw1AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.throw1AnimationState);
							break;
						case 9:
							this.throw2AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.throw2AnimationState);
							break;
						case 10:
							this.shoot1AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.shoot1AnimationState);
							break;
						case 11:
							this.shoot2AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.shoot2AnimationState);
							break;
						case 12:
							this.spear1AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.spear1AnimationState);
							break;
						case 13:
							this.spear2AnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.spear2AnimationState);
							break;
						case 14:
							this.danceAnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.danceAnimationState);
							break;
						case 15:
							this.deadAnimationState.startIfStopped(this.tickCount);
							this.stopMostAnimation(this.deadAnimationState);
							break;
					}
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	public int useCrossbow = 0;
	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
		}
		if (level().isClientSide()) {
			if (this.isChargingCrossbow()) {
				useCrossbow = 1;
			} else if (useCrossbow >= 0) {
				useCrossbow--;
			}
		}
		//消失
		if (!this.level().isClientSide()) {
			this.setEntityNeedDiscardTick(Math.max(-1, this.getEntityNeedDiscardTick() - 1));
		}
		if (this.getEntityNeedDiscardTick() == 5) {
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.TELEPORT, this.getSoundSource(), 5.0f, 1.0F);
			}
			if (this.level() instanceof ServerLevel serverLevel) {
				ParticlesUse.summonParticle(serverLevel, this, this.getX(), this.getY(), this.getZ(),
						0x3f0461, 0x8a16cf);
			}
			this.remove(Entity.RemovalReason.CHANGED_DIMENSION);
		}
	}
	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt	(900) == 1 && this.isAlive()) {
			this.heal(3.0f);
		}
		this.updateSwingTime();
		this.updateNoActionTime();
		if (!this.level().isClientSide()) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}

		//骑乘紧盯
		if (this.getTarget() != null && this.isPassenger() && this.getVehicle() != null && this.getControlledVehicle() == null) {
			if (InventoryEntity.isPike(this.getMainHandItem())) {
				this.lookAt(this.getTarget(), 360, 360);
				this.getLookControl().setLookAt(this.getTarget(), 360, 360);
			}
			else if (InventoryEntity.isShield(this.getUseItem())) {
				this.setYRot(this.getVehicle().getYRot());
				this.yRotO = this.getYRot();
				this.setXRot(this.getVehicle().getXRot() * 0.5F);
				this.setRot(this.getYRot(), this.getXRot());
				this.yBodyRot = this.getVehicle().getYRot();
				this.yHeadRot = this.getVehicle().getYRot();
			}
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//战斗
		if (!this.selfChangeAttackAnim()){
			//0-10 高为非战斗
			//高情况
			//非弩矛未战斗
			if (!(this.getMainHandItem().getItem() instanceof CrossbowItem) &&
					!(InventoryEntity.isSpear(this, this.getMainHandItem())) &&
					!(InventoryEntity.isPike(this, this.getMainHandItem())) &&
					(this.getTarget() == null)) {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
				}
			}
			//弩战斗
			else if (this.getMainHandItem().getItem() instanceof CrossbowItem && !this.isAggressive()) {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
				}
			}
			//矛战斗
			else if (InventoryEntity.isSpear(this, this.getMainHandItem()) && !this.isAggressive()) {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
				}
			}
			//矛战斗
			else if (InventoryEntity.isSpear(this, this.getOffhandItem()) && !this.isAggressive()) {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
				}
			}
			//长枪战斗
			else if (InventoryEntity.isPike(this, this.getMainHandItem()) && !this.isAggressive()) {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
				}
			}
			//长枪战斗
			else if (InventoryEntity.isPike(this, this.getOffhandItem()) && !this.isAggressive()) {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setAttackAnim(Math.max(this.getAttackAnim() - 2, 0));
				}
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
		//
		//法术
		if (!this.level().isClientSide()) {
			this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
		}
		//
		if (this.shieldCoolDown > 0){
			this.shieldCoolDown -= 1;
			this.shieldCanUse = 0;
		}
		if (this.shieldCoolDown <= 0){
			this.shieldCanUse = 1;
		}
		//摧毁骑乘物
		Main.destroyRides(this);
		//使用盾牌和双手武器
		useBlockingItem(this);
		//替换自己的物品
		changeInventory(this);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.isSilent()) {
			this.playSound(SoundEvents.PIGLIN_ANGRY, this instanceof EliteEntity ? 2.0f : 1.0f, 1.0f);
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(15);
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		return bl;
	}

	@Override
	public boolean hurt(DamageSource damagesource, float amount) {
		if (isInvulnerableTo(damagesource)) {
			return super.hurt(damagesource, amount);
		}
		return super.hurt(damagesource, amount);
	}


	@Override
	protected void blockUsingShield(LivingEntity entityIn) {
		super.blockUsingShield(entityIn);
		if (entityIn instanceof BreakShieldEntity breakShield) this.disableShieldBreak(breakShield.getShieldBreakStrength());
		if (entityIn.getMainHandItem().canDisableShield(this.useItem, this, entityIn) || entityIn.canDisableShield()) this.disableShield();
		this.disableShieldTry();
	}

	@Override
	public boolean isDamageSourceBlocks(DamageSource damageSource) {
		Vec3 object;
		Entity entity = damageSource.getDirectEntity();
		boolean bl = entity instanceof AbstractArrow && ((AbstractArrow) (AbstractArrow) entity).getPierceLevel() > 0;
		if (!damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && !bl && (object = damageSource.getSourcePosition()) != null) {
			Vec3 vec3 = this.calculateViewVector(0.0f, this.getYHeadRot());
			Vec3 vec32 = object.vectorTo(this.position());
			vec32 = new Vec3(vec32.x, 0.0, vec32.z).normalize();
			return vec32.dot(vec3) < 0.0;
		}
		return false;
	}

	@Override
	protected void hurtCurrentlyUsedShield(float f) {
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_SHIELD_CAN_BREAK)) {
			if (this.useItem.canPerformAction(ToolActions.SHIELD_BLOCK)) {
				if (f >= 3.0f) {
					int n = 1 + Mth.floor(f);
					InteractionHand interactionHand = this.getUsedItemHand();
					this.useItem.hurtAndBreak(n, this, player -> player.broadcastBreakEvent(interactionHand));
					if (this.useItem.isEmpty()) {
						if (interactionHand == InteractionHand.MAIN_HAND) {
							this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
						} else {
							this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
						}
						this.useItem = ItemStack.EMPTY;
						this.playSound(SoundEvents.SHIELD_BREAK, 0.8f, 0.8f + this.level().random.nextFloat() * 0.4f);
					}
				}
			}
		}
	}

	@Override
	public void disableShield() {
		if (this.getRandom().nextFloat() <= (1 - (this.getShieldLevel() - 1) * 0.2f)) {
			if (this.shieldCoolDown < 100) {
				this.shieldCoolDown = 100;
			}
			this.shieldCanUse = 0;
			if (this.getUseItem().getItem() instanceof ShieldItem) {
				this.stopUsingItem();
			}
		}
	}
	@Override
	public void disableShieldTry() {
		if (this.getRandom().nextFloat() <= (0.15 - (this.getShieldLevel() - 1) * 0.05f)) {
			if (this.shieldCoolDown < 100) {
				this.shieldCoolDown = 100;
			}
			this.shieldCanUse = 0;
			if (this.getUseItem().getItem() instanceof ShieldItem) {
				this.stopUsingItem();
			}
		}
	}
	@Override
	public void disableShieldBreak(int tick) {
		if (tick == 0) {
			return;
		}
		if (this.shieldCoolDown < tick) {
			this.shieldCoolDown = tick;
		}
		this.shieldCanUse = 0;
		if (this.getUseItem().getItem() instanceof ShieldItem) {
			this.stopUsingItem();
		}
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

	@Override
	public void tickDeath() {
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(20);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}


	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		SpawnGroupData spawnGroupData2 = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		RandomSource randomSource = serverLevelAccessor.getRandom();
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
			float weaponRandom = this.random.nextFloat();
			float offhandRandom = this.random.nextFloat();
			this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon(weaponRandom));
			this.setItemSlot(EquipmentSlot.OFFHAND, this.createSpawnOffhand(offhandRandom));
			this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
		}
		return spawnGroupData2;
	}

	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(Items.AIR);
	}

	public ItemStack createSpawnOffhand(float offhandRandom) {
		return new ItemStack(Items.AIR);
	}



	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	private UUID persistentAngerTarget;

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}


	protected void playAngrySound() {
		this.playSound(SoundEvents.PIGLIN_ANGRY, 1.0F, this.getVoicePitch());
	}
	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setPersistentAngerTarget(UUID uUID) {
		this.persistentAngerTarget = uUID;
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}


}