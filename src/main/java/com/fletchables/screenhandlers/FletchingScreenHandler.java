package com.fletchables.screenhandlers;

import com.fletchables.init.ModScreenHandlers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class FletchingScreenHandler extends ScreenHandler {
  private final Inventory inventory;
  private final ScreenHandlerContext context;
  private final RecipeInputInventory input;

  public FletchingScreenHandler(int syncId, PlayerInventory inventory) {
    this(syncId, inventory, ScreenHandlerContext.EMPTY);
  }

  public FletchingScreenHandler(
      int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
    super(ModScreenHandlers.FLETCHING, syncId);

    this.inventory = inventory;
    this.context = context;
    this.input = new CraftingInventory(this, 4, 1);

    this.addInventorySlots();
    this.addCraftingSlots();
  }

  @Override
  public ItemStack quickMove(PlayerEntity player, int slot) {
    return null;
  }

  @Override
  public boolean canUse(PlayerEntity player) {
    return ScreenHandler.canUse(this.context, player, Blocks.FLETCHING_TABLE);
  }

  private void addInventorySlots() {
    for (int x = 0; x < 9; x++) {
      this.addSlot(new Slot(this.inventory, x, 8 + x * 18, 142));
    }

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 9; x++) {
        this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
      }
    }
  }

  private void addCraftingSlots() {
    this.addSlot(new Slot(this.input, 0, 9, 53));
    this.addSlot(new Slot(this.input, 1, 27, 35));
    this.addSlot(new Slot(this.input, 2, 45, 17));
    this.addSlot(new Slot(this.input, 3, 94, 35));
  }
}
