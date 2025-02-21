package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.renderers.BaseArrowRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.util.Identifier;

public class ModRenderers {
  public static void initialize() {
    EntityRendererRegistry.register(
        ModEntityTypes.EXPLOSIVE_ARROW_ENTITY,
        BaseArrowRenderer.getWithTexture(
            Identifier.of(
                FletchablesMod.MOD_ID, "textures/entity/projectiles/explosive_arrow.png")));
    EntityRendererRegistry.register(
        ModEntityTypes.ENDER_ARROW_ENTITY,
        BaseArrowRenderer.getWithTexture(
            Identifier.of(FletchablesMod.MOD_ID, "textures/entity/projectiles/ender_arrow.png")));
    EntityRendererRegistry.register(
        ModEntityTypes.JUMP_ARROW_ENTITY,
        BaseArrowRenderer.getWithTexture(
            Identifier.of(FletchablesMod.MOD_ID, "textures/entity/projectiles/jump_arrow.png")));
    EntityRendererRegistry.register(
        ModEntityTypes.TWISTING_ARROW_ENTITY,
        BaseArrowRenderer.getWithTexture(
            Identifier.of(
                FletchablesMod.MOD_ID, "textures/entity/projectiles/twisting_arrow.png")));
    EntityRendererRegistry.register(
        ModEntityTypes.CHORUS_ARROW_ENTITY,
        BaseArrowRenderer.getWithTexture(
            Identifier.of(FletchablesMod.MOD_ID, "textures/entity/projectiles/chorus_arrow.png")));
  }
}
