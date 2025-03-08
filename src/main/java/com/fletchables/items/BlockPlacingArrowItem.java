package com.fletchables.items;

import com.fletchables.entities.BlockPlacingArrowEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
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

public class BlockPlacingArrowItem extends ArrowItem {
  private final EntityType<? extends BlockPlacingArrowEntity> entityType;
  private final Block blockToPlace;

  public BlockPlacingArrowItem(
      Item.Settings settings,
      EntityType<? extends BlockPlacingArrowEntity> entityType,
      Block blockToPlace) {
    super(settings);
    this.entityType = entityType;
    this.blockToPlace = blockToPlace;
  }

  @Override
  public PersistentProjectileEntity createArrow(
      World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
    return new BlockPlacingArrowEntity(
        this.entityType, shooter, world, stack.copyWithCount(1), shotFrom, this.blockToPlace);
  }

  @Override
  public ProjectileEntity createEntity(
      World world, Position pos, ItemStack stack, Direction direction) {
    BlockPlacingArrowEntity blockPlacingArrowEntity =
        new BlockPlacingArrowEntity(
            this.entityType,
            pos.getX(),
            pos.getY(),
            pos.getZ(),
            world,
            stack.copyWithCount(1),
            (ItemStack) null,
            this.blockToPlace);
    blockPlacingArrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;

    return blockPlacingArrowEntity;
  }
}
