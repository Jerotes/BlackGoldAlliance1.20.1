package com.jerotes.blackgoldalliance.event;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.init.BGAItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BGA.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootEvent {
	@SubscribeEvent
	public static void onLootTableLoad(LootTableLoadEvent event) {
		if (event.getName().equals(new ResourceLocation("minecraft", "gameplay/piglin_bartering"))) {
			LootPool SnipePool = LootPool.lootPool()
					.name("SnipePool")
					.setRolls(ConstantValue.exactly(1))
					.add(LootItem.lootTableItem(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get())
							.when(LootItemRandomChanceCondition.randomChance(0.025F))
							.apply(SetNbtFunction.setTag(new CompoundTag())))
					.build();
			event.getTable().addPool(SnipePool);
		}
		if (event.getName().equals(new ResourceLocation("minecraft", "entities/piglin")) || event.getName().equals(new ResourceLocation("minecraft", "entities/piglin_brute"))) {
			LootPool SnipePool = LootPool.lootPool()
					.name("SnipePool")
					.setRolls(ConstantValue.exactly(1))
					.add(LootItem.lootTableItem(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get())
							.when(LootItemRandomChanceCondition.randomChance(0.025F))
							.apply(SetNbtFunction.setTag(new CompoundTag())))
					.build();
			event.getTable().addPool(SnipePool);
		}
	}
}