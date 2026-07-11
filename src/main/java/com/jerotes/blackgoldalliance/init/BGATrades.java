package com.jerotes.blackgoldalliance.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BGATrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		//猪灵
		if (event.getType().name().equals("jerotesvillage:villager_piglin")) {
			//下界沙拉
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Items.GOLD_INGOT, 3),
					new ItemStack(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get(), 3), 30, 10, 0.05f));
		}
	}
}
