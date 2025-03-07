package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.renderers.BaseArrowRenderer;
import com.fletchables.renderers.BucketArrowRenderer;
import com.fletchables.renderers.JumpTntRenderer;
import com.fletchables.renderers.WebArrowRenderer;
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
    EntityRendererRegistry.register(
        ModEntityTypes.WATER_ARROW_ENTITY,
        (context) ->
            new BucketArrowRenderer<>(
                context,
                Identifier.of(
                    FletchablesMod.MOD_ID, "textures/entity/projectiles/water_arrow.png")));
    EntityRendererRegistry.register(
        ModEntityTypes.LAVA_ARROW_ENTITY,
        (context) ->
            new BucketArrowRenderer<>(
                context,
                Identifier.of(
                    FletchablesMod.MOD_ID, "textures/entity/projectiles/lava_arrow.png")));
    EntityRendererRegistry.register(
        ModEntityTypes.WEB_ARROW_ENTITY,
        (context) ->
            new WebArrowRenderer<>(
                context,
                Identifier.of(FletchablesMod.MOD_ID, "textures/entity/projectiles/web_arrow.png")));
    EntityRendererRegistry.register(ModEntityTypes.JUMP_TNT_ENTITY, JumpTntRenderer::new);
  }
}
