package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.screenhandlers.FletchingScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
  public static final ScreenHandlerType<FletchingScreenHandler> FLETCHING =
      register("fletching", FletchingScreenHandler::new);

  public static void initialize() {}

  private static <T extends ScreenHandler> ScreenHandlerType<T> register(
      String id, ScreenHandlerType.Factory<T> factory) {
    Identifier identifier = Identifier.of(FletchablesMod.MOD_ID, id);

    return Registry.register(
        Registries.SCREEN_HANDLER,
        identifier,
        new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
  }
}
