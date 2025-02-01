package com.fletchables.init;

import com.fletchables.events.BlockEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class ModEvents {
  public static void initialize() {
    UseBlockCallback.EVENT.register(BlockEvents::interact);
  }
}
