package com.fletchables.renderers;

import com.fletchables.FletchablesMod;
import com.fletchables.entity.EnderArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EnderArrowEntityRenderer extends ProjectileEntityRenderer<EnderArrowEntity> {
  public static final Identifier TEXTURE =
      Identifier.of(FletchablesMod.MOD_ID, "textures/entity/projectiles/ender_arrow.png");

  public EnderArrowEntityRenderer(EntityRendererFactory.Context context) {
    super(context);
  }

  @Override
  public Identifier getTexture(EnderArrowEntity entity) {
    return TEXTURE;
  }
}
