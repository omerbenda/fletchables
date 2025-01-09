package com.fletchables.entity;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ExplosiveArrowEntity extends PersistentProjectileEntity {
  public ExplosiveArrowEntity(EntityType<? extends ExplosiveArrowEntity> entityType, World world) {
    super(entityType, world);
  }

  public ExplosiveArrowEntity(
      double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
    super(ModEntityTypes.EXPLOSIVE_ARROW_ENTITY, x, y, z, world, stack, weapon);
  }

  public ExplosiveArrowEntity(
      LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
    super(ModEntityTypes.EXPLOSIVE_ARROW_ENTITY, owner, world, stack, shotFrom);
  }

  @Override
  protected ItemStack getDefaultItemStack() {
    return new ItemStack(ModItems.EXPLOSIVE_ARROW);
  }
}
