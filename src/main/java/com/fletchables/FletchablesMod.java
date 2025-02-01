package com.fletchables;

import com.fletchables.init.*;
import net.fabricmc.api.ModInitializer;

public class FletchablesMod implements ModInitializer {
  public static final String MOD_ID = "fletchables";

  @Override
  public void onInitialize() {
    ModItems.initialize();
    ModEntityTypes.initialize();
    ModRecipeSerializers.initialize();
    ModRecipeTypes.initialize();
    ModScreenHandlers.initialize();
    ModEvents.initialize();
  }
}
