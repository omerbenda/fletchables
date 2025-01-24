package com.fletchables.init;

import com.fletchables.screens.FletchingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModScreens {
  public static void initialize() {
    HandledScreens.register(ModScreenHandlers.FLETCHING, FletchingScreen::new);
  }
}
