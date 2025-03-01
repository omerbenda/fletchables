package com.fletchables.renderers;

import com.fletchables.init.ModModelLayers;
import com.fletchables.models.BucketArrowModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class BucketArrowRenderer<T extends PersistentProjectileEntity>
    extends EntityRenderer<T, ProjectileEntityRenderState> {
  private final Identifier texture;
  private final BucketArrowModel model;

  public BucketArrowRenderer(EntityRendererFactory.Context context, Identifier texture) {
    super(context);
    this.texture = texture;
    this.model = new BucketArrowModel(context.getPart(ModModelLayers.BUCKET_ARROW_MODEL_LAYER));
  }

  @Override
  public void render(
      ProjectileEntityRenderState state,
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light) {
    matrices.push();
    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw - 90.0F));
    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(state.pitch));
    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(texture));
    this.model.setAngles(state);
    this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
    matrices.pop();
    super.render(state, matrices, vertexConsumers, light);
  }

  @Override
  public ProjectileEntityRenderState createRenderState() {
    return new ProjectileEntityRenderState();
  }

  @Override
  public void updateRenderState(T entity, ProjectileEntityRenderState state, float f) {
    super.updateRenderState(entity, state, f);
    state.pitch = entity.getLerpedPitch(f);
    state.yaw = entity.getLerpedYaw(f);
    state.shake = (float) entity.shake - f;
  }
}
