package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.models.BucketArrowModel;
import com.fletchables.models.WebArrowModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
  public static final EntityModelLayer BUCKET_ARROW_MODEL_LAYER =
      new EntityModelLayer(Identifier.of(FletchablesMod.MOD_ID, "bucket_arrow"), "main");
  public static final EntityModelLayer WEB_ARROW_MODEL_LAYER =
      new EntityModelLayer(Identifier.of(FletchablesMod.MOD_ID, "web_arrow"), "main");

  public static void initialize() {
    EntityModelLayerRegistry.registerModelLayer(
        BUCKET_ARROW_MODEL_LAYER, BucketArrowModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(
        WEB_ARROW_MODEL_LAYER, WebArrowModel::getTexturedModelData);
  }
}
