package com.fletchables;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.init.ModEvents;
import com.fletchables.init.ModItems;
import com.fletchables.init.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

public class FletchablesMod implements ModInitializer {
  public static final String MOD_ID = "fletchables";

  @Override
  public void onInitialize() {
    ModItems.initialize();
    ModEntityTypes.initialize();
    ModScreenHandlers.initialize();
    ModEvents.initialize();
  }
}
