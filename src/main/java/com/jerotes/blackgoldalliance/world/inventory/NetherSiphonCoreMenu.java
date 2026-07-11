package com.jerotes.blackgoldalliance.world.inventory;

import com.jerotes.blackgoldalliance.block.NetherSiphonCoreEntity;
import com.jerotes.blackgoldalliance.init.BGAMenus;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class NetherSiphonCoreMenu extends AbstractContainerMenu {
   private final Container container;
   public final ContainerData data;

   public NetherSiphonCoreMenu(int p_39225_, Inventory p_39226_) {
      this(BGAMenus.NETHER_SIPHON_CORE.get(), p_39225_, p_39226_, new SimpleContainer(9), new SimpleContainerData(8));
   }
   public NetherSiphonCoreMenu(int p_39225_, Inventory p_39226_, Container container, ContainerData containerData) {
      this(BGAMenus.NETHER_SIPHON_CORE.get(), p_39225_, p_39226_, container, containerData);
   }

   public NetherSiphonCoreMenu(MenuType<?> p_39229_, int p_39230_, Inventory p_39231_, Container container, ContainerData containerData) {
      super(p_39229_, p_39230_);
      checkContainerSize(container, 9);
      checkContainerDataCount(containerData, 8);
      this.container = container;
      this.data = containerData;
      container.startOpen(p_39231_.player);
      int i = (1 - 4) * 18;

      this.addSlot(new Slot(container, 0, 80, 6));
      this.addSlot(new Slot(container, 1, 8, 28));
      this.addSlot(new Slot(container, 2, 152, 28));
      this.addSlot(new Slot(container, 3, 26, 55));
      this.addSlot(new Slot(container, 4, 44, 55));
      this.addSlot(new Slot(container, 5, 62, 55));
      this.addSlot(new Slot(container, 6, 98, 55));
      this.addSlot(new Slot(container, 7, 116, 55));
      this.addSlot(new Slot(container, 8, 134, 55));

      for(int l = 0; l < 3; ++l) {
         for(int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(p_39231_, j1 + l * 9 + 9, 8 + j1 * 18, 103 + 35 + l * 18 + i));
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         this.addSlot(new Slot(p_39231_, i1, 8 + i1 * 18, 161 + 35 + i));
      }

      this.addDataSlots(containerData);
   }

   public boolean stillValid(Player p_39242_) {
      return this.container.stillValid(p_39242_);
   }

   public ItemStack quickMoveStack(Player p_39253_, int p_39254_) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(p_39254_);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (p_39254_ < 9) {
            if (!this.moveItemStackTo(itemstack1, 9, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
      }

      return itemstack;
   }

   public void removed(Player p_39251_) {
      super.removed(p_39251_);
      this.container.stopOpen(p_39251_);
   }

   public Container getContainer() {
      return this.container;
   }

   public int getBlockType() {
      return this.data.get(5);
   }

   public int getLitProgress() {
      int i = this.data.get(4);
      if (i == 0) {
         i = 200;
      }
     // BGA.LOGGER.info("getLitProgress" + this.data.get(3) * 15 / i);
      return this.data.get(3) * 15 / i;
   }
   public boolean isLit() {
      return this.data.get(3) > 0;
   }

   public int getCooldownProgress() {
      return Mth.clamp(this.data.get(6) * 25 / NetherSiphonCoreEntity.MAX_COOLDOWN, 0, 25);
   }
   public boolean notCooldownProgress() {
      return this.data.get(6) <= 0;
   }
   public boolean isCooldown() {
      return this.data.get(7) == 1;
   }
}