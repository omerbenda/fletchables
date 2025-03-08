package com.fletchables.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockPlacingArrowEntity extends PersistentProjectileEntity {
  private static final String SHOULD_PLACE_NBT = "shouldPlace";

  private final Block blockToPlace;

  private boolean shouldPlace = true;

  public BlockPlacingArrowEntity(
      EntityType<? extends BlockPlacingArrowEntity> entityType, World world, Block blockToPlace) {
    super(entityType, world);
    this.blockToPlace = blockToPlace;
  }

  public BlockPlacingArrowEntity(
      EntityType<? extends BlockPlacingArrowEntity> entityType,
      double x,
      double y,
      double z,
      World world,
      ItemStack stack,
      @Nullable ItemStack weapon,
      Block blockToPlace) {
    super(entityType, x, y, z, world, stack, weapon);
    this.blockToPlace = blockToPlace;
  }

  public BlockPlacingArrowEntity(
      EntityType<? extends BlockPlacingArrowEntity> entityType,
      LivingEntity owner,
      World world,
      ItemStack stack,
      @Nullable ItemStack shotFrom,
      Block blockToPlace) {
    super(entityType, owner, world, stack, shotFrom);
    this.blockToPlace = blockToPlace;
  }

  @Override
  protected ItemStack getDefaultItemStack() {
    return new ItemStack(Items.ARROW);
  }

  @Override
  protected ItemStack asItemStack() {
    return new ItemStack(Items.ARROW);
  }

  @Override
  protected void onBlockHit(BlockHitResult blockHitResult) {
    super.onBlockHit(blockHitResult);

    if (this.shouldPlace && !this.getEntityWorld().isClient()) {
      this.setBlockAtPos(this.getBlockPos());
      this.shouldPlace = false;
    }
  }

  private void setBlockAtPos(BlockPos pos) {
    this.getEntityWorld().setBlockState(pos, this.blockToPlace.getDefaultState());
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);

    this.shouldPlace = nbt.getBoolean(SHOULD_PLACE_NBT);
  }

  @Override
  public NbtCompound writeNbt(NbtCompound nbt) {
    nbt.putBoolean(SHOULD_PLACE_NBT, this.shouldPlace);

    return super.writeNbt(nbt);
  }
}
