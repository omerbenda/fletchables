package com.fletchables;

import com.fletchables.init.ModRenderers;
import net.fabricmc.api.ClientModInitializer;

public class FletchablesClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ModRenderers.initialize();
  }
}
