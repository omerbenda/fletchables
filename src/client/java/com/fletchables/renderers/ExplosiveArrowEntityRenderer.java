package com.fletchables.renderers;

import com.fletchables.FletchablesMod;
import com.fletchables.entity.ExplosiveArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ExplosiveArrowEntityRenderer extends ProjectileEntityRenderer<ExplosiveArrowEntity> {
  public static final Identifier TEXTURE =
      Identifier.of(FletchablesMod.MOD_ID, "textures/entity/projectiles/explosive_arrow.png");

  public ExplosiveArrowEntityRenderer(EntityRendererFactory.Context context) {
    super(context);
  }

  @Override
  public Identifier getTexture(ExplosiveArrowEntity entity) {
    return TEXTURE;
  }
}
