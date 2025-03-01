package com.fletchables.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.util.math.MathHelper;

public class BucketArrowModel extends EntityModel<ProjectileEntityRenderState> {
  public BucketArrowModel(ModelPart root) {
    super(root, RenderLayer::getEntityCutout);
  }

  public static TexturedModelData getTexturedModelData() {
    ModelData modelData = new ModelData();
    ModelPartData modelPartData = modelData.getRoot();

    modelPartData.addChild(
        "back",
        ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F),
        ModelTransform.of(-11.0F, 0.0F, 0.0F, 0.7853982F, 0.0F, 0.0F).withScale(0.8F));

    ModelPartBuilder modelPartBuilder =
        ModelPartBuilder.create()
            .uv(0, 0)
            .cuboid(-12.0F, -2.0F, 0.0F, 16.0F, 4.0F, 0.0F, Dilation.NONE, 1.0F, 0.8F);
    modelPartData.addChild(
        "cross_1", modelPartBuilder, ModelTransform.rotation(0.7853982F, 0.0F, 0.0F));
    modelPartData.addChild(
        "cross_2", modelPartBuilder, ModelTransform.rotation(2.3561945F, 0.0F, 0.0F));

    modelPartData.addChild(
            "front",
            ModelPartBuilder.create().uv(0, 10).cuboid(15.0F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F),
            ModelTransform.of(-11.0F, 0.0F, 0.0F, 0.7853982F, 0.0F, 0.0F).withScale(0.8F));

    return TexturedModelData.of(
        modelData.transform((modelTransform) -> modelTransform.scaled(0.9F)), 32, 32);
  }

  @Override
  public void setAngles(ProjectileEntityRenderState state) {
    super.setAngles(state);

    if (state.shake > 0.0F) {
      float f = -MathHelper.sin(state.shake * 3.0F) * state.shake;
      ModelPart root = this.root;
      root.roll += f * 0.017453292F;
    }
  }
}
