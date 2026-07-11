package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.entity.Shoot.Arrow.BlackGoldSpectralArrowEntity;
import com.jerotes.blackgoldalliance.item.*;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BGAItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BGA.MODID);

	public static final RegistryObject<Item> GOLDEN_VEINED_BLACKSTONE_TABLET = REGISTRY.register("golden_veined_blackstone_tablet", () -> new GoldenVeinedBlackstoneTablet());
	public static final RegistryObject<Item> NETHER_GOLDEN_VEINED_CODEX = REGISTRY.register("nether_golden_veined_codex", () -> new NetherGoldenVeinedCodex());
	public static final RegistryObject<Item> NETHER_SIPHON_CORE = fireResistanceEpicBlock(BGABlocks.NETHER_SIPHON_CORE);
	public static final RegistryObject<Item> NETHER_SIPHON_LINK_STATION = fireResistanceEpicBlock(BGABlocks.NETHER_SIPHON_LINK_STATION);
	public static final RegistryObject<Item> NETHER_SIPHON_DETERRENT_UPGRADE_SCROLL_I = REGISTRY.register("nether_siphon_deterrent_upgrade_scroll_i", () -> new NetherSiphonDeterrentUpgradeScroll(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> NETHER_SIPHON_DETERRENT_UPGRADE_SCROLL_II = REGISTRY.register("nether_siphon_deterrent_upgrade_scroll_ii", () -> new NetherSiphonDeterrentUpgradeScroll(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> NETHER_SIPHON_DETERRENT_UPGRADE_SCROLL_III = REGISTRY.register("nether_siphon_deterrent_upgrade_scroll_iii", () -> new NetherSiphonDeterrentUpgradeScroll(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> DETERRENT_EXPANSION_TABLET = REGISTRY.register("deterrent_expansion_tablet", () -> new DeterrentExpansionTablet());
	public static final RegistryObject<Item> DETERRENT_INTENSIFICATION_TABLET = REGISTRY.register("deterrent_intensification_tablet", () -> new DeterrentIntensificationTablet());
	public static final RegistryObject<Item> MARSHAL_EMBLEM = REGISTRY.register("marshal_emblem", () -> new MarshalEmblem());

	public static final RegistryObject<Item> PIGLIN_RAID_NETHER_PORTAL_I_SPAWN_EGG = REGISTRY.register("piglin_raid_nether_portal_i_spawn_egg", () -> new PiglinRaidNetherPortalSpawnEgg(1));
	public static final RegistryObject<Item> PIGLIN_RAID_NETHER_PORTAL_II_SPAWN_EGG = REGISTRY.register("piglin_raid_nether_portal_ii_spawn_egg", () -> new PiglinRaidNetherPortalSpawnEgg(2));
	public static final RegistryObject<Item> PIGLIN_RAID_NETHER_PORTAL_III_SPAWN_EGG = REGISTRY.register("piglin_raid_nether_portal_iii_spawn_egg", () -> new PiglinRaidNetherPortalSpawnEgg(3));
	public static final RegistryObject<Item> PIGLIN_RAID_NETHER_PORTAL_IV_SPAWN_EGG = REGISTRY.register("piglin_raid_nether_portal_iv_spawn_egg", () -> new PiglinRaidNetherPortalSpawnEgg(4));
	public static final RegistryObject<Item> PIGLIN_RAID_NETHER_PORTAL_V_SPAWN_EGG = REGISTRY.register("piglin_raid_nether_portal_v_spawn_egg", () -> new PiglinRaidNetherPortalSpawnEgg(5));
	public static final RegistryObject<Item> PIGLIN_RAID_NETHER_PORTAL_VI_SPAWN_EGG = REGISTRY.register("piglin_raid_nether_portal_vi_spawn_egg", () -> new PiglinRaidNetherPortalSpawnEgg(6));
	public static final RegistryObject<Item> PIGLIN_RAIDER_SPAWN_EGG = REGISTRY.register("piglin_raider_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.PIGLIN_RAIDER, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> PIGLIN_RAIDER_WARRIOR_SPAWN_EGG = REGISTRY.register("piglin_raider_warrior_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.PIGLIN_RAIDER_WARRIOR, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> PIGLIN_RAIDER_HUNTER_SPAWN_EGG = REGISTRY.register("piglin_raider_hunter_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.PIGLIN_RAIDER_HUNTER, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> PIGLIN_RAIDER_BRUTE_SPAWN_EGG = REGISTRY.register("piglin_raider_brute_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.PIGLIN_RAIDER_BRUTE, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> PIGLIN_RAIDER_HOGLIN_SPAWN_EGG = REGISTRY.register("piglin_raider_hoglin_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.PIGLIN_RAIDER_HOGLIN, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> SHAMANIC_ZOMBIE_PIGMAN_SPAWN_EGG = REGISTRY.register("shamanic_zombie_pigman_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.SHAMANIC_ZOMBIE_PIGMAN, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> BLACK_GOLD_PIGLIN_SPAWN_EGG = REGISTRY.register("black_gold_piglin_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_PIGLIN, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> BLACK_GOLD_RUNT_SPAWN_EGG = REGISTRY.register("black_gold_runt_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_RUNT, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> BLACK_GOLD_RECRUIT_SPAWN_EGG = REGISTRY.register("black_gold_recruit_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_RECRUIT, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> BLACK_GOLD_THUMPER_SPAWN_EGG = REGISTRY.register("black_gold_thumper_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_THUMPER, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> BLACK_GOLD_WARRIOR_SPAWN_EGG = REGISTRY.register("black_gold_warrior_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_WARRIOR, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> BLACK_GOLD_MAULER_SPAWN_EGG = REGISTRY.register("black_gold_mauler_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_MAULER, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> BLACK_GOLD_HUNTER_SPAWN_EGG = REGISTRY.register("black_gold_hunter_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_HUNTER, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> BLACK_GOLD_CAVALRY_SPAWN_EGG = REGISTRY.register("black_gold_cavalry_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_CAVALRY, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> BLACK_GOLD_WAR_HOGLIN_SPAWN_EGG = REGISTRY.register("black_gold_war_hoglin_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_WAR_HOGLIN, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> BLACK_GOLD_BRUISER_SPAWN_EGG = REGISTRY.register("black_gold_bruiser_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_BRUISER, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> BLACK_GOLD_BUTCHER_SPAWN_EGG = REGISTRY.register("black_gold_butcher_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_BUTCHER, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> BLACK_GOLD_SHAMAN_SPAWN_EGG = REGISTRY.register("black_gold_shaman_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_SHAMAN, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> BLACK_GOLD_STEPPER_SPAWN_EGG = REGISTRY.register("black_gold_stepper_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_STEPPER, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> BLACK_GOLD_STEPPER_SQUAD_SPAWN_EGG = REGISTRY.register("black_gold_stepper_squad_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.BLACK_GOLD_STEPPER_SQUAD, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> THE_BLACK_GOLD_MARSHAL_SPAWN_EGG = REGISTRY.register("the_black_gold_marshal_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.THE_BLACK_GOLD_MARSHAL, 0xffffff, 0xffffff, new Item.Properties().rarity(Rarity.EPIC)));

	public static final RegistryObject<Item> PIGLIN_RAIDER_HOGLIN_BABY = REGISTRY.register("piglin_raider_hoglin_baby", () -> new BabyItem(1));
	public static final RegistryObject<Item> BLACK_GOLD_WAR_HOGLIN_BABY = REGISTRY.register("black_gold_war_hoglin_baby", () -> new BabyItem(0));
	public static final RegistryObject<Item> BLACK_GOLD_STEPPER_BABY = REGISTRY.register("black_gold_stepper_baby", () -> new BabyItem(2));

	public static final RegistryObject<Item> BLACK_GOLD_GLADIUS = REGISTRY.register("black_gold_gladius", () -> new BlackGoldGladius());
	public static final RegistryObject<Item> BLACK_GOLD_MACE = REGISTRY.register("black_gold_mace", () -> new BlackGoldMace());
	public static final RegistryObject<Item> BLACK_GOLD_CUTLASS = REGISTRY.register("black_gold_cutlass", () -> new BlackGoldCutlass());
	public static final RegistryObject<Item> BLACK_GOLD_BATTLEAXE = REGISTRY.register("black_gold_battleaxe", () -> new BlackGoldBattleaxe());
	public static final RegistryObject<Item> BLACK_GOLD_WARHAMMER = REGISTRY.register("black_gold_warhammer", () -> new BlackGoldWarhammer());
	public static final RegistryObject<Item> BLACK_GOLD_COG_CROSSBOW = REGISTRY.register("black_gold_cog_crossbow", () -> new BlackGoldCogCrossbow());
	public static final RegistryObject<Item> BLACK_GOLD_SPECTRAL_ARROW = REGISTRY.register("black_gold_spectral_arrow", () -> new BlackGoldSpectralArrow());
	public static final RegistryObject<Item> BLACK_GOLD_LANCE = REGISTRY.register("black_gold_lance", () -> new BlackGoldLance());
	public static final RegistryObject<Item> BLACK_GOLD_PIKE = REGISTRY.register("black_gold_pike", () -> new BlackGoldPike());
	public static final RegistryObject<Item> BLACK_GOLD_SCUTUM = REGISTRY.register("black_gold_scutum", () -> new BlackGoldScutum());
	public static final RegistryObject<Item> BLACK_GOLD_CHAINSAW = REGISTRY.register("black_gold_chainsaw", () -> new BlackGoldChainsaw());
	public static final RegistryObject<Item> BLACK_GOLD_SHAMAN_STAFF = REGISTRY.register("black_gold_shaman_staff", () -> new BlackGoldShamanStaff());
	public static final RegistryObject<Item> BLACK_GOLD_RUNT_HELMET = REGISTRY.register("black_gold_runt_helmet", () -> new BlackGoldRuntArmor.Helmet());
	public static final RegistryObject<Item> BLACK_GOLD_RUNT_CHESTPLATE = REGISTRY.register("black_gold_runt_chestplate", () -> new BlackGoldRuntArmor.Chestplate());
	public static final RegistryObject<Item> BLACK_GOLD_RUNT_LEGGINGS = REGISTRY.register("black_gold_runt_leggings", () -> new BlackGoldRuntArmor.Leggings());
	public static final RegistryObject<Item> BLACK_GOLD_RUNT_BOOTS = REGISTRY.register("black_gold_runt_boots", () -> new BlackGoldRuntArmor.Boots());
	public static final RegistryObject<Item> BLACK_GOLD_WARRIOR_HELMET = REGISTRY.register("black_gold_warrior_helmet", () -> new BlackGoldWarriorArmor.Helmet());
	public static final RegistryObject<Item> BLACK_GOLD_WARRIOR_CHESTPLATE = REGISTRY.register("black_gold_warrior_chestplate", () -> new BlackGoldWarriorArmor.Chestplate());
	public static final RegistryObject<Item> BLACK_GOLD_WARRIOR_LEGGINGS = REGISTRY.register("black_gold_warrior_leggings", () -> new BlackGoldWarriorArmor.Leggings());
	public static final RegistryObject<Item> BLACK_GOLD_WARRIOR_BOOTS = REGISTRY.register("black_gold_warrior_boots", () -> new BlackGoldWarriorArmor.Boots());
	public static final RegistryObject<Item> BLACK_GOLD_MAULER_HELMET = REGISTRY.register("black_gold_mauler_helmet", () -> new BlackGoldMaulerArmor.Helmet());
	public static final RegistryObject<Item> BLACK_GOLD_MAULER_CHESTPLATE = REGISTRY.register("black_gold_mauler_chestplate", () -> new BlackGoldMaulerArmor.Chestplate());
	public static final RegistryObject<Item> BLACK_GOLD_MAULER_LEGGINGS = REGISTRY.register("black_gold_mauler_leggings", () -> new BlackGoldMaulerArmor.Leggings());
	public static final RegistryObject<Item> BLACK_GOLD_MAULER_BOOTS = REGISTRY.register("black_gold_mauler_boots", () -> new BlackGoldMaulerArmor.Boots());
	public static final RegistryObject<Item> BLACK_GOLD_HUNTER_HELMET = REGISTRY.register("black_gold_hunter_helmet", () -> new BlackGoldHunterArmor.Helmet());
	public static final RegistryObject<Item> BLACK_GOLD_HUNTER_CHESTPLATE = REGISTRY.register("black_gold_hunter_chestplate", () -> new BlackGoldHunterArmor.Chestplate());
	public static final RegistryObject<Item> BLACK_GOLD_HUNTER_LEGGINGS = REGISTRY.register("black_gold_hunter_leggings", () -> new BlackGoldHunterArmor.Leggings());
	public static final RegistryObject<Item> BLACK_GOLD_HUNTER_BOOTS = REGISTRY.register("black_gold_hunter_boots", () -> new BlackGoldHunterArmor.Boots());
	public static final RegistryObject<Item> BLACK_GOLD_CAVALRY_HELMET = REGISTRY.register("black_gold_cavalry_helmet", () -> new BlackGoldCavalryArmor.Helmet());
	public static final RegistryObject<Item> BLACK_GOLD_CAVALRY_CHESTPLATE = REGISTRY.register("black_gold_cavalry_chestplate", () -> new BlackGoldCavalryArmor.Chestplate());
	public static final RegistryObject<Item> BLACK_GOLD_CAVALRY_LEGGINGS = REGISTRY.register("black_gold_cavalry_leggings", () -> new BlackGoldCavalryArmor.Leggings());
	public static final RegistryObject<Item> BLACK_GOLD_CAVALRY_BOOTS = REGISTRY.register("black_gold_cavalry_boots", () -> new BlackGoldCavalryArmor.Boots());
	public static final RegistryObject<Item> THE_BLACK_GOLD_MARSHAL_GREATSWORD = REGISTRY.register("the_black_gold_marshal_greatsword", () -> new TheBlackGoldMarshalGreatsword());
	public static final RegistryObject<Item> THE_BLACK_GOLD_MARSHAL_HELMET = REGISTRY.register("the_black_gold_marshal_helmet", () -> new TheBlackGoldMarshalArmor.Helmet());
	public static final RegistryObject<Item> THE_BLACK_GOLD_MARSHAL_CHESTPLATE = REGISTRY.register("the_black_gold_marshal_chestplate", () -> new TheBlackGoldMarshalArmor.Chestplate());
	public static final RegistryObject<Item> THE_BLACK_GOLD_MARSHAL_LEGGINGS = REGISTRY.register("the_black_gold_marshal_leggings", () -> new TheBlackGoldMarshalArmor.Leggings());
	public static final RegistryObject<Item> THE_BLACK_GOLD_MARSHAL_BOOTS = REGISTRY.register("the_black_gold_marshal_boots", () -> new TheBlackGoldMarshalArmor.Boots());

	public static final RegistryObject<Item> OATH_WITHER_SKELETON_SPAWN_EGG = REGISTRY.register("oath_wither_skeleton_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.OATH_WITHER_SKELETON, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> OATH_WITHER_SQUIRE_SPAWN_EGG = REGISTRY.register("oath_wither_squire_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.OATH_WITHER_SQUIRE, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> OATH_CONSTRUCT_SPIDER_SPAWN_EGG = REGISTRY.register("oath_construct_spider_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.OATH_CONSTRUCT_SPIDER, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> OATH_CONSTRUCT_SPIDER_JOCKEY_SPAWN_EGG = REGISTRY.register("oath_construct_spider_jockey_spawn_egg", () -> new ForgeSpawnEggItem(BGAEntityType.OATH_CONSTRUCT_SPIDER_JOCKEY, 0xffffff, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item> OATH_STONE_SWORD = REGISTRY.register("oath_stone_sword", () -> new OathStoneSword());
	public static final RegistryObject<Item> OATH_WITHER_SQUIRE_HELMET = REGISTRY.register("oath_wither_squire_helmet", () -> new OathWitherSquireArmor.Helmet());
	public static final RegistryObject<Item> OATH_WITHER_SQUIRE_CHESTPLATE = REGISTRY.register("oath_wither_squire_chestplate", () -> new OathWitherSquireArmor.Chestplate());
	public static final RegistryObject<Item> OATH_WITHER_SQUIRE_LEGGINGS = REGISTRY.register("oath_wither_squire_leggings", () -> new OathWitherSquireArmor.Leggings());
	public static final RegistryObject<Item> OATH_WITHER_SQUIRE_BOOTS = REGISTRY.register("oath_wither_squire_boots", () -> new OathWitherSquireArmor.Boots());

	public static final RegistryObject<Item> WARPED_BREATH = REGISTRY.register("warped_breath", () -> new Shoot());
	public static final RegistryObject<Item> RAY_OF_WARPED = REGISTRY.register("ray_of_warped", () -> new Shoot());


	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
	private static RegistryObject<Item> fireResistanceBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant()));
	}
	private static RegistryObject<Item> fireResistanceRareBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.RARE)));
	}
	private static RegistryObject<Item> uncommonBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
	}
	private static RegistryObject<Item> rareBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.RARE)));
	}
	private static RegistryObject<Item> fireResistanceEpicBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.EPIC)));
	}
	private static RegistryObject<Item> highBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new DoubleHighBlockItem(block.get(), new Item.Properties()));
	}
	private static RegistryObject<Item> highfireResistanceEpicBlock(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new DoubleHighBlockItem(block.get(), new Item.Properties().fireResistant().rarity(Rarity.EPIC)));
	}

	public static void setup() {
		DispenserBlock.registerBehavior(BGAItems.BLACK_GOLD_SPECTRAL_ARROW.get(), new AbstractProjectileDispenseBehavior(){
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
				BlackGoldSpectralArrowEntity arrow = new BlackGoldSpectralArrowEntity(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
				arrow.pickup = AbstractArrow.Pickup.ALLOWED;
				return arrow;
			}
		});
	}

}
