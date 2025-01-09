package com.fletchables;

import com.fletchables.init.ModItems;
import net.fabricmc.api.ModInitializer;

public class FletchablesMod implements ModInitializer {
  public static final String MOD_ID = "fletchables";

  @Override
  public void onInitialize() {
    ModItems.initialize();
  }
}
