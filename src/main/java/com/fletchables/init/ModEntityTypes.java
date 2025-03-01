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
  public static final EntityType<WaterArrowEntity> WATER_ARROW_ENTITY =
      register(
          EntityType.Builder.<WaterArrowEntity>create(WaterArrowEntity::new, SpawnGroup.MISC)
              .dimensions(0.5F, 0.5F)
              .eyeHeight(0.13F)
              .maxTrackingRange(4)
              .trackingTickInterval(20),
          "water_arrow");
  public static final EntityType<LavaArrowEntity> LAVA_ARROW_ENTITY =
      register(
          EntityType.Builder.<LavaArrowEntity>create(LavaArrowEntity::new, SpawnGroup.MISC)
              .dimensions(0.5F, 0.5F)
              .eyeHeight(0.13F)
              .maxTrackingRange(4)
              .trackingTickInterval(20),
          "lava_arrow");
  public static final EntityType<JumpTntEntity> JUMP_TNT_ENTITY =
      register(
          EntityType.Builder.<JumpTntEntity>create(JumpTntEntity::new, SpawnGroup.MISC)
              .dropsNothing()
              .makeFireImmune()
              .dimensions(0.98F, 0.98F)
              .eyeHeight(0.15F)
              .maxTrackingRange(10)
              .trackingTickInterval(10),
          "jump_tnt");

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
