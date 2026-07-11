package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class BGATabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BGA.MODID);
	public static final RegistryObject<CreativeModeTab> BLACK_GOLD_ALLIANCE_TAB = REGISTRY.register("black_gold_alliance_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.blackgoldalliance.black_gold_alliance_tab").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).icon(() -> new ItemStack(BGAItems.BLACK_GOLD_CUTLASS.get())).displayItems((parameters, tabData) -> {
						tabData.accept(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get());
						tabData.accept(BGAItems.NETHER_GOLDEN_VEINED_CODEX.get());
						tabData.accept(BGAItems.NETHER_SIPHON_CORE.get());
						tabData.accept(BGAItems.NETHER_SIPHON_LINK_STATION.get());
						tabData.accept(BGAItems.NETHER_SIPHON_DETERRENT_UPGRADE_SCROLL_I.get());
						tabData.accept(BGAItems.NETHER_SIPHON_DETERRENT_UPGRADE_SCROLL_II.get());
						tabData.accept(BGAItems.NETHER_SIPHON_DETERRENT_UPGRADE_SCROLL_III.get());
						tabData.accept(BGAItems.DETERRENT_EXPANSION_TABLET.get());
						tabData.accept(BGAItems.DETERRENT_INTENSIFICATION_TABLET.get());
						tabData.accept(BGAItems.MARSHAL_EMBLEM.get());
						tabData.accept(Banner());
						tabData.accept(BGAItems.PIGLIN_RAID_NETHER_PORTAL_I_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAID_NETHER_PORTAL_II_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAID_NETHER_PORTAL_III_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAID_NETHER_PORTAL_IV_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAID_NETHER_PORTAL_V_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAID_NETHER_PORTAL_VI_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAIDER_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAIDER_WARRIOR_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAIDER_HUNTER_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAIDER_BRUTE_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAIDER_HOGLIN_SPAWN_EGG.get());
						tabData.accept(BGAItems.SHAMANIC_ZOMBIE_PIGMAN_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_PIGLIN_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_RUNT_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_RECRUIT_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_THUMPER_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_WARRIOR_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_MAULER_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_HUNTER_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_CAVALRY_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_WAR_HOGLIN_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_BRUISER_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_BUTCHER_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_SHAMAN_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_STEPPER_SPAWN_EGG.get());
						tabData.accept(BGAItems.BLACK_GOLD_STEPPER_SQUAD_SPAWN_EGG.get());
						tabData.accept(BGAItems.THE_BLACK_GOLD_MARSHAL_SPAWN_EGG.get());
						tabData.accept(BGAItems.PIGLIN_RAIDER_HOGLIN_BABY.get());
						tabData.accept(BGAItems.BLACK_GOLD_WAR_HOGLIN_BABY.get());
						tabData.accept(BGAItems.BLACK_GOLD_STEPPER_BABY.get());
						tabData.accept(BGAItems.BLACK_GOLD_GLADIUS.get());
						tabData.accept(BGAItems.BLACK_GOLD_MACE.get());
						tabData.accept(BGAItems.BLACK_GOLD_CUTLASS.get());
						tabData.accept(BGAItems.BLACK_GOLD_BATTLEAXE.get());
						tabData.accept(BGAItems.BLACK_GOLD_WARHAMMER.get());
						tabData.accept(BGAItems.BLACK_GOLD_COG_CROSSBOW.get());
						tabData.accept(BGAItems.BLACK_GOLD_SPECTRAL_ARROW.get());
						tabData.accept(BGAItems.BLACK_GOLD_LANCE.get());
						tabData.accept(BGAItems.BLACK_GOLD_PIKE.get());
						tabData.accept(BGAItems.BLACK_GOLD_SCUTUM.get());
						tabData.accept(BGAItems.BLACK_GOLD_CHAINSAW.get());
						tabData.accept(BGAItems.BLACK_GOLD_SHAMAN_STAFF.get());
						tabData.accept(BGAItems.BLACK_GOLD_RUNT_HELMET.get());
						tabData.accept(BGAItems.BLACK_GOLD_RUNT_CHESTPLATE.get());
						tabData.accept(BGAItems.BLACK_GOLD_RUNT_LEGGINGS.get());
						tabData.accept(BGAItems.BLACK_GOLD_RUNT_BOOTS.get());
						tabData.accept(BGAItems.BLACK_GOLD_WARRIOR_HELMET.get());
						tabData.accept(BGAItems.BLACK_GOLD_WARRIOR_CHESTPLATE.get());
						tabData.accept(BGAItems.BLACK_GOLD_WARRIOR_LEGGINGS.get());
						tabData.accept(BGAItems.BLACK_GOLD_WARRIOR_BOOTS.get());
						tabData.accept(BGAItems.BLACK_GOLD_MAULER_HELMET.get());
						tabData.accept(BGAItems.BLACK_GOLD_MAULER_CHESTPLATE.get());
						tabData.accept(BGAItems.BLACK_GOLD_MAULER_LEGGINGS.get());
						tabData.accept(BGAItems.BLACK_GOLD_MAULER_BOOTS.get());
						tabData.accept(BGAItems.BLACK_GOLD_HUNTER_HELMET.get());
						tabData.accept(BGAItems.BLACK_GOLD_HUNTER_CHESTPLATE.get());
						tabData.accept(BGAItems.BLACK_GOLD_HUNTER_LEGGINGS.get());
						tabData.accept(BGAItems.BLACK_GOLD_HUNTER_BOOTS.get());
						tabData.accept(BGAItems.BLACK_GOLD_CAVALRY_HELMET.get());
						tabData.accept(BGAItems.BLACK_GOLD_CAVALRY_CHESTPLATE.get());
						tabData.accept(BGAItems.BLACK_GOLD_CAVALRY_LEGGINGS.get());
						tabData.accept(BGAItems.BLACK_GOLD_CAVALRY_BOOTS.get());
						tabData.accept(BGAItems.THE_BLACK_GOLD_MARSHAL_GREATSWORD.get());
						tabData.accept(BGAItems.THE_BLACK_GOLD_MARSHAL_HELMET.get());
						tabData.accept(BGAItems.THE_BLACK_GOLD_MARSHAL_CHESTPLATE.get());
						tabData.accept(BGAItems.THE_BLACK_GOLD_MARSHAL_LEGGINGS.get());
						tabData.accept(BGAItems.THE_BLACK_GOLD_MARSHAL_BOOTS.get());

						tabData.accept(BGAItems.OATH_WITHER_SKELETON_SPAWN_EGG.get());
						tabData.accept(BGAItems.OATH_WITHER_SQUIRE_SPAWN_EGG.get());
						tabData.accept(BGAItems.OATH_CONSTRUCT_SPIDER_SPAWN_EGG.get());
						tabData.accept(BGAItems.OATH_CONSTRUCT_SPIDER_JOCKEY_SPAWN_EGG.get());
						tabData.accept(BGAItems.OATH_STONE_SWORD.get());
						tabData.accept(BGAItems.OATH_WITHER_SQUIRE_HELMET.get());
						tabData.accept(BGAItems.OATH_WITHER_SQUIRE_CHESTPLATE.get());
						tabData.accept(BGAItems.OATH_WITHER_SQUIRE_LEGGINGS.get());
						tabData.accept(BGAItems.OATH_WITHER_SQUIRE_BOOTS.get());
			}).
					//withSearchBar().
					withBackgroundLocation(new ResourceLocation(BGA.MODID, "textures/gui/container/creative_inventory/tab_item_search.png")).
					withTabsImage(new ResourceLocation(BGA.MODID, "textures/gui/container/creative_inventory/tabs.png")).
					build());


	public static ItemStack Banner() {
		ItemStack itemStack = new ItemStack(Items.BLACK_BANNER);
		CompoundTag compoundTag = new CompoundTag();
		ListTag listTag = new BannerPattern.Builder()
				.addPattern(BannerPatterns.STRIPE_BOTTOM, DyeColor.RED)
				.addPattern(BannerPatterns.STRIPE_TOP, DyeColor.RED)
				.addPattern(BannerPatterns.RHOMBUS_MIDDLE, DyeColor.BLACK)

				.addPattern(BannerPatterns.CURLY_BORDER, DyeColor.YELLOW)
				.addPattern(BannerPatterns.BORDER, DyeColor. BLACK)
				.addPattern(BannerPatterns.PIGLIN, DyeColor.YELLOW)
				.toListTag();
		compoundTag.put("Patterns", listTag);
		BlockItem.setBlockEntityData(itemStack, BlockEntityType.BANNER, compoundTag);
		itemStack.hideTooltipPart(ItemStack.TooltipPart.ADDITIONAL);
		itemStack.setHoverName(Component.translatable("block.blackgoldalliance.black_gold_alliance_banner").withStyle(ChatFormatting.GOLD));
		return itemStack;
	}
}
