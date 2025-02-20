package com.fletchables.entities;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TwistingArrowEntity extends PersistentProjectileEntity {
  public TwistingArrowEntity(EntityType<? extends TwistingArrowEntity> entityType, World world) {
    super(entityType, world);
  }

  public TwistingArrowEntity(
      double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
    super(ModEntityTypes.TWISTING_ARROW_ENTITY, x, y, z, world, stack, weapon);
  }

  public TwistingArrowEntity(
      LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
    super(ModEntityTypes.TWISTING_ARROW_ENTITY, owner, world, stack, shotFrom);
  }

  @Override
  protected ItemStack getDefaultItemStack() {
    return new ItemStack(ModItems.TWISTING_ARROW);
  }

  @Override
  protected void onEntityHit(EntityHitResult entityHitResult) {
    super.onEntityHit(entityHitResult);
    Entity entity = entityHitResult.getEntity();

    if (!entity.getEntityWorld().isClient()) {
      entity.rotate(entity.getYaw() + 180, entity.getPitch());
      entity.playSound(SoundEvent.of(Identifier.ofVanilla("entity.enderman.teleport")), 1.0f, 2.0f);
    }
  }
}
