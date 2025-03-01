package com.fletchables;

import com.fletchables.init.ModModelLayers;
import com.fletchables.init.ModRenderers;
import com.fletchables.init.ModScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FletchablesClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ModModelLayers.initialize();
    ModRenderers.initialize();
    ModScreens.initialize();
  }
}
