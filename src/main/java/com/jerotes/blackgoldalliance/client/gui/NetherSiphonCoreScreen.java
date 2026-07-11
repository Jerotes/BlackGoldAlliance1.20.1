package com.jerotes.blackgoldalliance.client.gui;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.init.BGAItemTags;
import com.jerotes.blackgoldalliance.world.inventory.NetherSiphonCoreMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NetherSiphonCoreScreen extends AbstractContainerScreen<NetherSiphonCoreMenu> {
    private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/gui/container/nether_siphon_core.png");
    private static final ResourceLocation FIRE_LOCATION = new ResourceLocation(BGA.MODID, "textures/gui/sprites/container/nether_siphon_core/fire.png");
    private static final ResourceLocation SLOT_LOCATION = new ResourceLocation(BGA.MODID, "textures/gui/sprites/container/nether_siphon_core/fuel_slot.png");
    private static final ResourceLocation COOLDOWN_LEFT = new ResourceLocation(BGA.MODID, "textures/gui/sprites/container/nether_siphon_core/cooldown_left.png");
    private static final ResourceLocation COOLDOWN_RIGHT = new ResourceLocation(BGA.MODID, "textures/gui/sprites/container/nether_siphon_core/cooldown_right.png");
    private static final ResourceLocation COOLDOWN_LEFT_COOLDOWN = new ResourceLocation(BGA.MODID, "textures/gui/sprites/container/nether_siphon_core/cooldown_left_cooldown.png");
    private static final ResourceLocation COOLDOWN_RIGHT_COOLDOWN = new ResourceLocation(BGA.MODID, "textures/gui/sprites/container/nether_siphon_core/cooldown_right_cooldown.png");
    private static final ResourceLocation LEVEL_I = new ResourceLocation(BGA.MODID, "textures/gui/sprites/container/nether_siphon_core/level_i.png");
    private static final ResourceLocation LEVEL_II = new ResourceLocation(BGA.MODID, "textures/gui/sprites/container/nether_siphon_core/level_ii.png");
    private static final ResourceLocation LEVEL_III = new ResourceLocation(BGA.MODID, "textures/gui/sprites/container/nether_siphon_core/level_iii.png");
    private final NetherSiphonCoreMenu container;

    private static ResourceLocation blockLocation(String string) {
        return new ResourceLocation(BGA.MODID, "textures/block/nether_siphon_core" + string + "_particle.png");
    }


    public NetherSiphonCoreScreen(NetherSiphonCoreMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.container = container;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    protected void init() {
        super.init();
    }
    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        p_281635_.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x000000, false);
        p_281635_.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY + 1, 0x000000, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        //guiGraphics.blit(CAN_NOT_USE, this.leftPos + 82, this.topPos + 67, 0, 0, 11, 12, 11, 12);

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        //燃烧
        if (this.menu.isLit()) {
            int litProgress = this.menu.getLitProgress();
            guiGraphics.blit(FIRE_LOCATION,
                    this.leftPos + 80, this.topPos + 54 + (14 - litProgress),
                    0, 14 - litProgress,
                    16, litProgress + 1,
                    16, 16);
        }
        //冷却
        int cooldownProgress = this.menu.getCooldownProgress();
        if (!this.menu.notCooldownProgress()) {
            guiGraphics.blit(!this.menu.isCooldown() ? COOLDOWN_LEFT : COOLDOWN_LEFT_COOLDOWN,
                    this.leftPos + 63 + (24 - cooldownProgress), this.topPos + 27,
                    24 - cooldownProgress, 0,
                    cooldownProgress + 1, 3,
                    25, 3);
            guiGraphics.blit(!this.menu.isCooldown() ? COOLDOWN_RIGHT : COOLDOWN_RIGHT_COOLDOWN,
                    this.leftPos + 88, this.topPos + 27,
                    0, 0,
                    cooldownProgress + 1, 3,
                    25, 3);
        }

        //冷却抑制等级
        if (this.menu.getSlot(1).getItem().is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_III)) {
            guiGraphics.blit(LEVEL_III, this.leftPos + 31, this.topPos + 34, 0, 0, 9, 9, 9, 9);
        }
        else if (this.menu.getSlot(1).getItem().is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_II)) {
            guiGraphics.blit(LEVEL_II, this.leftPos + 31, this.topPos + 34, 0, 0, 9, 9, 9, 9);
        }
        else if (this.menu.getSlot(1).getItem().is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_I)) {
            guiGraphics.blit(LEVEL_I, this.leftPos + 31, this.topPos + 34, 0, 0, 9, 9, 9, 9);
        }
        if (this.menu.getSlot(2).getItem().is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_III)) {
            guiGraphics.blit(LEVEL_III, this.leftPos + 136, this.topPos + 34, 0, 0, 9, 9, 9, 9);
        }
        else if (this.menu.getSlot(2).getItem().is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_II)) {
            guiGraphics.blit(LEVEL_II, this.leftPos + 136, this.topPos + 34, 0, 0, 9, 9, 9, 9);
        }
        else if (this.menu.getSlot(2).getItem().is(BGAItemTags.NETHER_SIPHON_COOLDOWN_SUPPRESSION_I)) {
            guiGraphics.blit(LEVEL_I, this.leftPos + 136, this.topPos + 34, 0, 0, 9, 9, 9, 9);
        }
        //方块
        if (this.menu.getBlockType() == 0)
            guiGraphics.blit(blockLocation(""), this.leftPos + 80, this.topPos + 35, 0, 0, 16, 16, 16, 16);
        else if (this.menu.getBlockType() == 1)
            guiGraphics.blit(blockLocation("_can_use"), this.leftPos + 80, this.topPos + 35, 0, 0, 16, 16, 16, 16);
        else if (this.menu.getBlockType() == 2)
            guiGraphics.blit(blockLocation("_use"), this.leftPos + 80, this.topPos + 35, 0, 0, 16, 16, 16, 16);
        else if (this.menu.getBlockType() == 3)
            guiGraphics.blit(blockLocation("_close"), this.leftPos + 80, this.topPos + 35, 0, 0, 16, 16, 16, 16);

        //核心等级
        guiGraphics.blit(LEVEL_I, this.leftPos + 155, this.topPos + 7, 0, 0, 9, 9, 9, 9);

        //燃料栏位
        if (!this.menu.getSlot(3).hasItem())
            guiGraphics.blit(SLOT_LOCATION, this.leftPos + 25, this.topPos + 54, 0, 0, 18, 18, 18, 18);
        if (!this.menu.getSlot(4).hasItem())
            guiGraphics.blit(SLOT_LOCATION, this.leftPos + 43, this.topPos + 54, 0, 0, 18, 18, 18, 18);
        if (!this.menu.getSlot(5).hasItem())
            guiGraphics.blit(SLOT_LOCATION, this.leftPos + 61, this.topPos + 54, 0, 0, 18, 18, 18, 18);
        if (!this.menu.getSlot(6).hasItem())
            guiGraphics.blit(SLOT_LOCATION, this.leftPos + 97, this.topPos + 54, 0, 0, 18, 18, 18, 18);
        if (!this.menu.getSlot(7).hasItem())
            guiGraphics.blit(SLOT_LOCATION, this.leftPos + 115, this.topPos + 54, 0, 0, 18, 18, 18, 18);
        if (!this.menu.getSlot(8).hasItem())
            guiGraphics.blit(SLOT_LOCATION, this.leftPos + 133, this.topPos + 54, 0, 0, 18, 18, 18, 18);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

}
