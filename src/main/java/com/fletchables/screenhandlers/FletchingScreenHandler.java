package com.fletchables.screenhandlers;

import com.fletchables.init.ModScreenHandlers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class FletchingScreenHandler extends ScreenHandler {
  private final Inventory inventory;
  private final ScreenHandlerContext context;

  public FletchingScreenHandler(int syncId, PlayerInventory inventory) {
    this(syncId, inventory, ScreenHandlerContext.EMPTY);
  }

  public FletchingScreenHandler(
      int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
    super(ModScreenHandlers.FLETCHING, syncId);

    this.inventory = inventory;
    this.context = context;
  }

  @Override
  public ItemStack quickMove(PlayerEntity player, int slot) {
    return null;
  }

  @Override
  public boolean canUse(PlayerEntity player) {
    return ScreenHandler.canUse(this.context, player, Blocks.FLETCHING_TABLE);
  }
}
