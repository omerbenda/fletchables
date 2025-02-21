package com.fletchables.entities;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ChorusArrowEntity extends PersistentProjectileEntity {
  public ChorusArrowEntity(EntityType<? extends ChorusArrowEntity> entityType, World world) {
    super(entityType, world);
  }

  public ChorusArrowEntity(
      double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
    super(ModEntityTypes.CHORUS_ARROW_ENTITY, x, y, z, world, stack, weapon);
  }

  public ChorusArrowEntity(
      LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
    super(ModEntityTypes.CHORUS_ARROW_ENTITY, owner, world, stack, shotFrom);
  }

  @Override
  protected ItemStack getDefaultItemStack() {
    return new ItemStack(ModItems.TWISTING_ARROW);
  }

  @Override
  protected void onEntityHit(EntityHitResult entityHitResult) {
    super.onEntityHit(entityHitResult);

    if (entityHitResult.getEntity() instanceof LivingEntity livingEntityHit) {
      teleport(livingEntityHit, 16);
    }
  }

  private static boolean teleport(LivingEntity target, double diameter) {
    World world = target.getEntityWorld();
    boolean bl = false;

    for (int i = 0; i < 16; ++i) {
      double d = target.getX() + (target.getRandom().nextDouble() - 0.5) * diameter;
      double e =
          MathHelper.clamp(
              target.getY() + (target.getRandom().nextDouble() - 0.5) * diameter,
              world.getBottomY(),
              world.getBottomY() + ((ServerWorld) world).getLogicalHeight() - 1);
      double f = target.getZ() + (target.getRandom().nextDouble() - 0.5) * diameter;
      if (target.hasVehicle()) {
        target.stopRiding();
      }

      Vec3d vec3d = target.getPos();
      if (target.teleport(d, e, f, true)) {
        world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(target));
        SoundCategory soundCategory;
        SoundEvent soundEvent;
        if (target instanceof FoxEntity) {
          soundEvent = SoundEvents.ENTITY_FOX_TELEPORT;
          soundCategory = SoundCategory.NEUTRAL;
        } else {
          soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
          soundCategory = SoundCategory.PLAYERS;
        }

        world.playSound(
            null, target.getX(), target.getY(), target.getZ(), soundEvent, soundCategory);
        target.onLanding();
        bl = true;
        break;
      }
    }

    if (bl && target instanceof PlayerEntity playerEntity) {
      playerEntity.clearCurrentExplosion();
    }

    return bl;
  }
}
