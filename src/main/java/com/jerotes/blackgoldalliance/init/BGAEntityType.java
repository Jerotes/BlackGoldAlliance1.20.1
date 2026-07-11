package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.entity.Animal.*;
import com.jerotes.blackgoldalliance.entity.Boss.*;
import com.jerotes.blackgoldalliance.entity.MagicSummoned.ZombiePigman.*;
import com.jerotes.blackgoldalliance.entity.Other.*;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.*;
import com.jerotes.blackgoldalliance.entity.Piglin.*;
import com.jerotes.blackgoldalliance.entity.Shoot.Arrow.*;
import com.jerotes.blackgoldalliance.entity.Shoot.Magic.Breath.*;
import com.jerotes.blackgoldalliance.entity.Shoot.Magic.Ray.*;
import com.jerotes.blackgoldalliance.entity.WitherSkeleton.*;
import com.jerotes.blackgoldalliance.util.SpawnRules;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BGAEntityType {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BGA.MODID);

	public static final RegistryObject<EntityType<PiglinRaidNetherPortalEntity>> PIGLIN_RAID_NETHER_PORTAL = register("piglin_raid_nether_portal",
			EntityType.Builder.of(PiglinRaidNetherPortalEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune().immuneTo(Blocks.WITHER_ROSE).immuneTo(Blocks.POWDER_SNOW)
					.sized(3f, 6f));
	public static final RegistryObject<EntityType<NetherSiphonCoreForceEntity>> NETHER_SIPHON_CORE_FORCE = register("nether_siphon_core_force",
			EntityType.Builder.of(NetherSiphonCoreForceEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune().immuneTo(Blocks.WITHER_ROSE).immuneTo(Blocks.POWDER_SNOW)
					.sized(4f, 4f));
	public static final RegistryObject<EntityType<PortalPointEntity>> PORTAL_POINT = register("portal_point",
			EntityType.Builder.<PortalPointEntity>of(PortalPointEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<PiglinRaiderEntity>> PIGLIN_RAIDER = register("piglin_raider",
			EntityType.Builder.of(PiglinRaiderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<PiglinRaiderWarriorEntity>> PIGLIN_RAIDER_WARRIOR = register("piglin_raider_warrior",
			EntityType.Builder.of(PiglinRaiderWarriorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<PiglinRaiderHunterEntity>> PIGLIN_RAIDER_HUNTER = register("piglin_raider_hunter",
			EntityType.Builder.of(PiglinRaiderHunterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<PiglinRaiderBruteEntity>> PIGLIN_RAIDER_BRUTE = register("piglin_raider_brute",
			EntityType.Builder.of(PiglinRaiderBruteEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<PiglinRaiderHoglinEntity>> PIGLIN_RAIDER_HOGLIN = register("piglin_raider_hoglin",
			EntityType.Builder.of(PiglinRaiderHoglinEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(1.3964844F, 1.4F));
	public static final RegistryObject<EntityType<ZombiePigmanEntity>> SHAMANIC_ZOMBIE_PIGMAN = register("shamanic_zombie_pigman",
			EntityType.Builder.of(ZombiePigmanEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).fireImmune()
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BlackGoldPiglinEntity>> BLACK_GOLD_PIGLIN = register("black_gold_piglin",
			EntityType.Builder.of(BlackGoldPiglinEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BlackGoldRuntEntity>> BLACK_GOLD_RUNT = register("black_gold_runt",
			EntityType.Builder.of(BlackGoldRuntEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.45f, 1.25f));
	public static final RegistryObject<EntityType<BlackGoldRecruitEntity>> BLACK_GOLD_RECRUIT = register("black_gold_recruit",
			EntityType.Builder.of(BlackGoldRecruitEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.45f, 1.25f));
	public static final RegistryObject<EntityType<BlackGoldThumperEntity>> BLACK_GOLD_THUMPER = register("black_gold_thumper",
			EntityType.Builder.of(BlackGoldThumperEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.45f, 1.25f));
	public static final RegistryObject<EntityType<BlackGoldWarriorEntity>> BLACK_GOLD_WARRIOR = register("black_gold_warrior",
			EntityType.Builder.of(BlackGoldWarriorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BlackGoldMaulerEntity>> BLACK_GOLD_MAULER = register("black_gold_mauler",
			EntityType.Builder.of(BlackGoldMaulerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BlackGoldHunterEntity>> BLACK_GOLD_HUNTER = register("black_gold_hunter",
			EntityType.Builder.of(BlackGoldHunterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BlackGoldCavalryEntity>> BLACK_GOLD_CAVALRY = register("black_gold_cavalry",
			EntityType.Builder.of(BlackGoldCavalryEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.6f, 1.95f));
	public static final RegistryObject<EntityType<BlackGoldWarHoglinEntity>> BLACK_GOLD_WAR_HOGLIN = register("black_gold_war_hoglin",
			EntityType.Builder.of(BlackGoldWarHoglinEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(2.1f, 2.1f));
	public static final RegistryObject<EntityType<BlackGoldBruiserEntity>> BLACK_GOLD_BRUISER = register("black_gold_bruiser",
			EntityType.Builder.of(BlackGoldBruiserEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(2.0f, 3.65f));
	public static final RegistryObject<EntityType<BlackGoldButcherEntity>> BLACK_GOLD_BUTCHER = register("black_gold_butcher",
			EntityType.Builder.of(BlackGoldButcherEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.8f, 2.4f));
	public static final RegistryObject<EntityType<BlackGoldShamanEntity>> BLACK_GOLD_SHAMAN = register("black_gold_shaman",
			EntityType.Builder.of(BlackGoldShamanEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.75f, 1.8f));
	public static final RegistryObject<EntityType<BlackGoldStepperEntity>> BLACK_GOLD_STEPPER = register("black_gold_stepper",
			EntityType.Builder.of(BlackGoldStepperEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(1.2f, 5.8f));
	public static final RegistryObject<EntityType<BlackGoldStepperSquadEntity>> BLACK_GOLD_STEPPER_SQUAD = register("black_gold_stepper_squad",
			EntityType.Builder.of(BlackGoldStepperSquadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune()
					.sized(1.2f, 5.8f));
	public static final RegistryObject<EntityType<TheBlackGoldMarshalEntity>> THE_BLACK_GOLD_MARSHAL = register("the_black_gold_marshal",
			EntityType.Builder.of(TheBlackGoldMarshalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32)
					.sized(0.9f, 2.9f));

	public static final RegistryObject<EntityType<OathWitherSkeletonEntity>> OATH_WITHER_SKELETON = register("oath_wither_skeleton",
			EntityType.Builder.of(OathWitherSkeletonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune().immuneTo(Blocks.WITHER_ROSE)
					.sized(0.7F, 2.4F));
	public static final RegistryObject<EntityType<OathWitherSquireEntity>> OATH_WITHER_SQUIRE = register("oath_wither_squire",
			EntityType.Builder.of(OathWitherSquireEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune().immuneTo(Blocks.WITHER_ROSE)
					.sized(0.7F, 2.4F));
	public static final RegistryObject<EntityType<OathConstructSpiderEntity>> OATH_CONSTRUCT_SPIDER = register("oath_construct_spider",
			EntityType.Builder.of(OathConstructSpiderEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune().immuneTo(Blocks.WITHER_ROSE)
					.sized(2.4f, 1.5f));
	public static final RegistryObject<EntityType<OathConstructSpiderJockeyEntity>> OATH_CONSTRUCT_SPIDER_JOCKEY = register("oath_construct_spider_jockey",
			EntityType.Builder.of(OathConstructSpiderJockeyEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(32).fireImmune().immuneTo(Blocks.WITHER_ROSE)
					.sized(0.7F, 2.4F));

	public static final RegistryObject<EntityType<BlackGoldSpectralArrowEntity>> BLACK_GOLD_SPECTRAL_ARROW = register("projectile_black_gold_spectral_arrow",
			EntityType.Builder.<BlackGoldSpectralArrowEntity>of(BlackGoldSpectralArrowEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.sized(0.5f, 0.5f));


	public static final RegistryObject<EntityType<WarpedBreathEntity>> WARPED_BREATH = register("warped_breath",
			EntityType.Builder.<WarpedBreathEntity>of(WarpedBreathEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(1.6f, 1.6f));
	public static final RegistryObject<EntityType<RayOfWarpedEntity>> RAY_OF_WARPED = register("ray_of_warped",
			EntityType.Builder.<RayOfWarpedEntity>of(RayOfWarpedEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<WarpedBombEntity>> WARPED_BOMB = register("warped_bomb",
			EntityType.Builder.<WarpedBombEntity>of(WarpedBombEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(4)
					.sized(0.6f, 0.6f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			SpawnPlacements.register(BGAEntityType.PIGLIN_RAID_NETHER_PORTAL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.NETHER_SIPHON_CORE_FORCE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.PIGLIN_RAIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.PIGLIN_RAIDER_WARRIOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.PIGLIN_RAIDER_HUNTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.PIGLIN_RAIDER_BRUTE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.PIGLIN_RAIDER_HOGLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.SHAMANIC_ZOMBIE_PIGMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_PIGLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_RUNT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_RECRUIT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_THUMPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_WARRIOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_MAULER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_HUNTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_CAVALRY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_WAR_HOGLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_BRUISER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_BUTCHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_SHAMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_STEPPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.BLACK_GOLD_STEPPER_SQUAD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(1, 128, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.THE_BLACK_GOLD_MARSHAL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(1, 256, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.OATH_WITHER_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.OATH_WITHER_SQUIRE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.OATH_CONSTRUCT_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
			SpawnPlacements.register(BGAEntityType.OATH_CONSTRUCT_SPIDER_JOCKEY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (arg_0, arg_1, arg_2, arg_3, arg_4) ->
					SpawnRules.NeutralSpawn(8, 64, arg_0, arg_1, arg_2, arg_3, arg_4));
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(PIGLIN_RAID_NETHER_PORTAL.get(), PiglinRaidNetherPortalEntity.createAttributes().build());
		event.put(NETHER_SIPHON_CORE_FORCE.get(), NetherSiphonCoreForceEntity.createAttributes().build());
		event.put(PIGLIN_RAIDER.get(), PiglinRaiderEntity.createAttributes().build());
		event.put(PIGLIN_RAIDER_WARRIOR.get(), PiglinRaiderWarriorEntity.createAttributes().build());
		event.put(PIGLIN_RAIDER_HUNTER.get(), PiglinRaiderHunterEntity.createAttributes().build());
		event.put(PIGLIN_RAIDER_BRUTE.get(), PiglinRaiderBruteEntity.createAttributes().build());
		event.put(PIGLIN_RAIDER_HOGLIN.get(), PiglinRaiderHoglinEntity.createAttributes().build());
		event.put(SHAMANIC_ZOMBIE_PIGMAN.get(), ZombiePigmanEntity.createAttributes().build());
		event.put(BLACK_GOLD_PIGLIN.get(), BlackGoldPiglinEntity.createAttributes().build());
		event.put(BLACK_GOLD_RUNT.get(), BlackGoldRuntEntity.createAttributes().build());
		event.put(BLACK_GOLD_RECRUIT.get(), BlackGoldRecruitEntity.createAttributes().build());
		event.put(BLACK_GOLD_THUMPER.get(), BlackGoldThumperEntity.createAttributes().build());
		event.put(BLACK_GOLD_WARRIOR.get(), BlackGoldWarriorEntity.createAttributes().build());
		event.put(BLACK_GOLD_MAULER.get(), BlackGoldMaulerEntity.createAttributes().build());
		event.put(BLACK_GOLD_HUNTER.get(), BlackGoldHunterEntity.createAttributes().build());
		event.put(BLACK_GOLD_CAVALRY.get(), BlackGoldCavalryEntity.createAttributes().build());
		event.put(BLACK_GOLD_WAR_HOGLIN.get(), BlackGoldWarHoglinEntity.createAttributes().build());
		event.put(BLACK_GOLD_BRUISER.get(), BlackGoldBruiserEntity.createAttributes().build());
		event.put(BLACK_GOLD_BUTCHER.get(), BlackGoldButcherEntity.createAttributes().build());
		event.put(BLACK_GOLD_SHAMAN.get(), BlackGoldShamanEntity.createAttributes().build());
		event.put(BLACK_GOLD_STEPPER.get(), BlackGoldStepperEntity.createAttributes().build());
		event.put(BLACK_GOLD_STEPPER_SQUAD.get(), BlackGoldStepperSquadEntity.createAttributes().build());
		event.put(THE_BLACK_GOLD_MARSHAL.get(), TheBlackGoldMarshalEntity.createAttributes().build());
		event.put(OATH_WITHER_SKELETON.get(), OathWitherSkeletonEntity.createAttributes().build());
		event.put(OATH_WITHER_SQUIRE.get(), OathWitherSquireEntity.createAttributes().build());
		event.put(OATH_CONSTRUCT_SPIDER.get(), OathConstructSpiderEntity.createAttributes().build());
		event.put(OATH_CONSTRUCT_SPIDER_JOCKEY.get(), OathConstructSpiderJockeyEntity.createAttributes().build());
	}
}