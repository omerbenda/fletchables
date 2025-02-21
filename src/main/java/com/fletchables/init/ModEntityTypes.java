package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntityTypes {
  public static final EntityType<ExplosiveArrowEntity> EXPLOSIVE_ARROW_ENTITY =
      register(
          EntityType.Builder.<ExplosiveArrowEntity>create(
                  ExplosiveArrowEntity::new, SpawnGroup.MISC)
              .dimensions(0.5F, 0.5F)
              .eyeHeight(0.13F)
              .maxTrackingRange(4)
              .trackingTickInterval(20),
          "explosive_arrow");
  public static final EntityType<EnderArrowEntity> ENDER_ARROW_ENTITY =
      register(
          EntityType.Builder.<EnderArrowEntity>create(EnderArrowEntity::new, SpawnGroup.MISC)
              .dimensions(0.5F, 0.5F)
              .eyeHeight(0.13F)
              .maxTrackingRange(4)
              .trackingTickInterval(20),
          "ender_arrow");
  public static final EntityType<JumpArrowEntity> JUMP_ARROW_ENTITY =
      register(
          EntityType.Builder.<JumpArrowEntity>create(JumpArrowEntity::new, SpawnGroup.MISC)
              .dimensions(0.5F, 0.5F)
              .eyeHeight(0.13F)
              .maxTrackingRange(4)
              .trackingTickInterval(20),
          "jump_arrow");
  public static final EntityType<TwistingArrowEntity> TWISTING_ARROW_ENTITY =
      register(
          EntityType.Builder.<TwistingArrowEntity>create(TwistingArrowEntity::new, SpawnGroup.MISC)
              .dimensions(0.5F, 0.5F)
              .eyeHeight(0.13F)
              .maxTrackingRange(4)
              .trackingTickInterval(20),
          "twisting_arrow");
  public static final EntityType<ChorusArrowEntity> CHORUS_ARROW_ENTITY =
      register(
          EntityType.Builder.<ChorusArrowEntity>create(ChorusArrowEntity::new, SpawnGroup.MISC)
              .dimensions(0.5F, 0.5F)
              .eyeHeight(0.13F)
              .maxTrackingRange(4)
              .trackingTickInterval(20),
          "chorus_arrow");

  public static void initialize() {}

  private static <T extends Entity> EntityType<T> register(
      EntityType.Builder<T> builder, String id) {
    Identifier identifier = Identifier.of(FletchablesMod.MOD_ID, id);

    return Registry.register(
        Registries.ENTITY_TYPE,
        identifier,
        builder.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier)));
  }
}
