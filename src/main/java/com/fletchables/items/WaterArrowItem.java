package com.fletchables.items;

import com.fletchables.entities.WaterArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WaterArrowItem extends ArrowItem {
  public WaterArrowItem(Item.Settings settings) {
    super(settings);
  }

  @Override
  public PersistentProjectileEntity createArrow(
      World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
    return new WaterArrowEntity(shooter, world, stack.copyWithCount(1), shotFrom);
  }

  @Override
  public ProjectileEntity createEntity(
      World world, Position pos, ItemStack stack, Direction direction) {
    WaterArrowEntity waterArrowEntity =
        new WaterArrowEntity(
            pos.getX(), pos.getY(), pos.getZ(), world, stack.copyWithCount(1), (ItemStack) null);
    waterArrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;

    return waterArrowEntity;
  }
}
