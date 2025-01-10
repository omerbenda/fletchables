package com.fletchables.screens;

import com.fletchables.FletchablesMod;
import com.fletchables.screenhandlers.FletchingScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FletchingScreen extends HandledScreen<FletchingScreenHandler> {
  private static final Identifier TEXTURE =
      Identifier.of(FletchablesMod.MOD_ID, "textures/gui/container/fletching_table.png");

  public FletchingScreen(FletchingScreenHandler handler, PlayerInventory inventory, Text title) {
    super(handler, inventory, title);
  }

  @Override
  protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    int yStart = (this.height - this.backgroundHeight) / 2;
    context.drawTexture(TEXTURE, this.x, yStart, 0, 0, this.backgroundWidth, this.backgroundHeight);
  }
}
