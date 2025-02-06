package com.fletchables.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;

public class BaseArrowRenderer<T extends PersistentProjectileEntity>
    extends ProjectileEntityRenderer<T, ArrowEntityRenderState> {
  public final Identifier texture;

  private BaseArrowRenderer(EntityRendererFactory.Context context, Identifier texture) {
    super(context);

    this.texture = texture;
  }

  public static <T extends PersistentProjectileEntity> EntityRendererFactory<T> getWithTexture(
      Identifier texture) {
    return (context) -> new BaseArrowRenderer<>(context, texture);
  }

  @Override
  public Identifier getTexture(ArrowEntityRenderState state) {
    return texture;
  }

  @Override
  public ArrowEntityRenderState createRenderState() {
    return new ArrowEntityRenderState();
  }
}
