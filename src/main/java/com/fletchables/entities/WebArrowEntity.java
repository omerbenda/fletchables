package com.fletchables.entities;

import com.fletchables.init.ModEntityTypes;
import net.minecraft.block.Blocks;
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

public class WebArrowEntity extends PersistentProjectileEntity {
  private static final String SHOULD_PLACE_NBT = "shouldPlace";

  private boolean shouldPlace = true;

  public WebArrowEntity(EntityType<? extends WebArrowEntity> entityType, World world) {
    super(entityType, world);
  }

  public WebArrowEntity(
      double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
    super(ModEntityTypes.WEB_ARROW_ENTITY, x, y, z, world, stack, weapon);
  }

  public WebArrowEntity(
      LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
    super(ModEntityTypes.WEB_ARROW_ENTITY, owner, world, stack, shotFrom);
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
    this.getEntityWorld().setBlockState(pos, Blocks.COBWEB.getDefaultState());
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
