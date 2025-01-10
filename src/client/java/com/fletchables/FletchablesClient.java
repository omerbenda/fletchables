package com.fletchables;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.renderers.EnderArrowEntityRenderer;
import com.fletchables.renderers.ExplosiveArrowEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FletchablesClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    EntityRendererRegistry.register(
        ModEntityTypes.EXPLOSIVE_ARROW_ENTITY, ExplosiveArrowEntityRenderer::new);
    EntityRendererRegistry.register(
        ModEntityTypes.ENDER_ARROW_ENTITY, EnderArrowEntityRenderer::new);
  }
}
