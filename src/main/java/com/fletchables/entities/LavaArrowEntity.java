package com.fletchables.entities;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LavaArrowEntity extends PersistentProjectileEntity {
  public LavaArrowEntity(EntityType<? extends LavaArrowEntity> entityType, World world) {
    super(entityType, world);
  }

  public LavaArrowEntity(
      double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
    super(ModEntityTypes.LAVA_ARROW_ENTITY, x, y, z, world, stack, weapon);
  }

  public LavaArrowEntity(
      LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
    super(ModEntityTypes.LAVA_ARROW_ENTITY, owner, world, stack, shotFrom);
  }

  @Override
  protected ItemStack getDefaultItemStack() {
    return new ItemStack(ModItems.LAVA_ARROW);
  }

  @Override
  protected void onCollision(HitResult hitResult) {
    super.onCollision(hitResult);

    Vec3d hitPos = hitResult.getPos();
    BlockPos hitBlockPos = new BlockPos((int)hitPos.x, (int)hitPos.y, (int)hitPos.z);
    this.getEntityWorld().setBlockState(hitBlockPos, Blocks.LAVA.getDefaultState());
    this.remove(RemovalReason.DISCARDED);
  }
}
