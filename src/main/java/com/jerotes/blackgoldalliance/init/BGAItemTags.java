package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;

public final class BGAItemTags {
	public static final TagKey<Item> NETHER_SIPHON_COOLDOWN_SUPPRESSION_I = bind("nether_siphon_core_cooldown_suppression_level_i");
	public static final TagKey<Item> NETHER_SIPHON_COOLDOWN_SUPPRESSION_II = bind("nether_siphon_core_cooldown_suppression_level_ii");
	public static final TagKey<Item> NETHER_SIPHON_COOLDOWN_SUPPRESSION_III = bind("nether_siphon_core_cooldown_suppression_level_iii");

	private BGAItemTags() {
	}

	private static TagKey<Item> bind(String string) {
		return TagKey.create(Registries.ITEM, new ResourceLocation(BGA.MODID, string));
	}

	public static TagKey<Item> create(final ResourceLocation name) {
		return TagKey.create(Registries.ITEM, name);
	}
}