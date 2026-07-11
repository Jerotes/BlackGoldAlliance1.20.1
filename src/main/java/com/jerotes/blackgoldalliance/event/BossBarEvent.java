package com.jerotes.blackgoldalliance.event;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.config.OtherMainConfig;
import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import com.jerotes.blackgoldalliance.entity.Boss.TheBlackGoldMarshalEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BGA.MODID, value = Dist.CLIENT)
public class BossBarEvent {
	public static final Set<Mob> BOSSES = Collections.newSetFromMap(new WeakHashMap<>());
	private static final ResourceLocation BASE_LOCATION = new ResourceLocation(BGA.MODID,"textures/gui/new_bars.png");
	private static final ResourceLocation BASE_LOCATION_OLD = new ResourceLocation(BGA.MODID,"textures/gui/bars.png");

	@SubscribeEvent
	public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
		ResourceLocation barLocation;
		LerpingBossEvent bossEvent = event.getBossEvent();
		Mob boss = null;
		int color = 16777215;
		int colorBar = 1;
		if (!BOSSES.isEmpty()) {
			for (Mob mob : BOSSES) {
				if (mob.getUUID().equals(bossEvent.getId())) {
					boss = mob;
					break;
				}
			}
			if (boss != null) {
				Component bossDesc = null;
				if (boss instanceof JerotesEntity jerotesEntity && jerotesEntity.isExalted()) {
					bossDesc = Component.translatable("boss.jerotes.exalted");
				}
				else if (boss instanceof JerotesEntity jerotesEntity && jerotesEntity.isLegendary()) {
					bossDesc = Component.translatable("boss.jerotes.legendary");
				}
				if (boss instanceof PiglinRaidNetherPortalEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(BGA.MODID,"textures/gui/sprites/boss_bar/piglin_raid_nether_portal_progress.png");
					color = OtherMainConfig.PiglinRaidNetherPortalBossBarNameColor;
					colorBar = OtherMainConfig.PiglinRaidNetherPortalBossBarColor;
				}
				else if (boss instanceof TheBlackGoldMarshalEntity) {
					event.setCanceled(true);
					barLocation = new ResourceLocation(BGA.MODID,"textures/gui/sprites/boss_bar/the_black_gold_marshal_progress.png");
					color = OtherMainConfig.TheBlackGoldMarshalBossBarNameColor;
					colorBar = OtherMainConfig.TheBlackGoldMarshalBossBarColor;
				}
				else {
					return;
				}
				event.setIncrement(event.getIncrement() + 8);
				RenderSystem.setShaderTexture(0, BASE_LOCATION_OLD);
				drawBar(event.getGuiGraphics(), event.getX(), event.getY(), bossEvent, barLocation, colorBar);
				MutableComponent component = bossEvent.getName().copy().withStyle(ChatFormatting.BOLD);
				int textWidth = Minecraft.getInstance().font.width(component);
				int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
				int textY = event.getY() - 9;
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, component, textX, textY, color);
				if (bossDesc != null) {
					int descWidth = Minecraft.getInstance().font.width(bossDesc);
					int descX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - descWidth / 2;
					int descY = event.getY() + 10;
					event.getGuiGraphics().drawString(Minecraft.getInstance().font, bossDesc, descX, descY, color);
				}
			}
		}
	}

	public static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event, ResourceLocation barLocation, int color) {
		drawBar(guiGraphics, x, y + 2, event, 182, 0, color);
		int i = (int)(event.getProgress() * 183.0f);
		if (i > 0) {
			drawBar(guiGraphics, x, y + 2, event, i, 5, color);
		}

		RenderSystem.setShaderTexture(0, barLocation);
		guiGraphics.blit(barLocation, x - 3, y - 6, 0.0F, 0.0F, 188, 20, 188, 20);
	}

	private static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event, int width, int i, int color) {
		guiGraphics.blit(BASE_LOCATION_OLD, x, y, 0, color * 5 * 2 + i, width, 5);

		if (event.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
			RenderSystem.enableBlend();
			guiGraphics.blit(BASE_LOCATION_OLD, x, y, 0, 80 + (event.getOverlay().ordinal() - 1) * 5 * 2 + i, width, 5);
			RenderSystem.disableBlend();
		}
	}
}