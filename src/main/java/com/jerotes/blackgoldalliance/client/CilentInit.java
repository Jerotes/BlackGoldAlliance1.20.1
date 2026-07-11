package com.jerotes.blackgoldalliance.client;

import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldButcherEntity;
import com.jerotes.blackgoldalliance.init.BGAItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;

public class CilentInit
{
    public static void clientInit() {
        BGAItems.REGISTRY.getEntries().forEach(item -> {
            ItemProperties.register(item.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, n) -> {
                if (livingEntity == null) {
                    return 0.0f;
                }
                if (livingEntity.getUseItem() != itemStack) {
                    return 0.0f;
                }
                return (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f;
            });

            ItemProperties.register(item.get(), new ResourceLocation("blackgoldalliance_pull_crossbow"), (itemStack, clientLevel, livingEntity, n) -> {
                if (livingEntity == null) {
                    return 0.0f;
                }
                if (CrossbowItem.isCharged(itemStack)) {
                    return 0.0f;
                }
                return (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(itemStack);
            });
            ItemProperties.register(item.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && (!(itemStack.getItem() instanceof CrossbowItem) || !CrossbowItem.isCharged(itemStack)) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, n) -> CrossbowItem.isCharged(itemStack) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("firework"), (itemStack, clientLevel, livingEntity, n) -> CrossbowItem.isCharged(itemStack) && CrossbowItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("throwing"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("blackgoldalliance_swing"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && livingEntity.getMainHandItem() == itemStack && livingEntity.swinging ? 1.0f : 0.0f);
            ItemProperties.register(item.get(), new ResourceLocation("blackgoldalliance_tick_count_6"), (itemStack, clientLevel, livingEntity, n) -> livingEntity != null && (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack || livingEntity instanceof BlackGoldButcherEntity blackGoldButcherEntity && itemStack.is(BGAItems.BLACK_GOLD_CHAINSAW.get()) && blackGoldButcherEntity.getChainsawTick() > 0 && blackGoldButcherEntity.getChainsawTick() < 590) ?
                    (livingEntity.tickCount % 6 + 1) : 0.0f);
        });
    }
}
