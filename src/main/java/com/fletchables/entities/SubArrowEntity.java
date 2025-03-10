package com.fletchables.entities;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SubArrowEntity extends PersistentProjectileEntity {
  private static final float WATER_ACCELERATION = 1.3F;

  public SubArrowEntity(EntityType<? extends SubArrowEntity> entityType, World world) {
    super(entityType, world);
  }

  public SubArrowEntity(
      double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
    super(ModEntityTypes.SUB_ARROW_ENTITY, x, y, z, world, stack, weapon);
  }

  public SubArrowEntity(
      LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
    super(ModEntityTypes.SUB_ARROW_ENTITY, owner, world, stack, shotFrom);
  }

  @Override
  protected ItemStack getDefaultItemStack() {
    return new ItemStack(ModItems.SUB_ARROW);
  }

  @Override
  protected float getDragInWater() {
    return WATER_ACCELERATION;
  }
}
