package com.fletchables.screens;

import com.fletchables.screenhandlers.FletchingScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class FletchingScreen extends HandledScreen<FletchingScreenHandler> {
  public FletchingScreen(FletchingScreenHandler handler, PlayerInventory inventory, Text title) {
    super(handler, inventory, title);
  }

  @Override
  protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    context.drawItem(new ItemStack(Items.ARROW), 10, 10);
  }
}
