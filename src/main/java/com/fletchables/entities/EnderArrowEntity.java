package com.fletchables.entities;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnderArrowEntity extends PersistentProjectileEntity {
  public EnderArrowEntity(EntityType<? extends EnderArrowEntity> entityType, World world) {
    super(entityType, world);
  }

  public EnderArrowEntity(
      double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
    super(ModEntityTypes.ENDER_ARROW_ENTITY, x, y, z, world, stack, weapon);
  }

  public EnderArrowEntity(
      LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
    super(ModEntityTypes.ENDER_ARROW_ENTITY, owner, world, stack, shotFrom);
  }

  @Override
  protected ItemStack getDefaultItemStack() {
    return new ItemStack(ModItems.ENDER_ARROW);
  }

  @Override
  protected void onHit(LivingEntity target) {
    super.onHit(target);
    this.teleportOwner();
  }

  @Override
  protected void onBlockHit(BlockHitResult blockHitResult) {
    super.onBlockHit(blockHitResult);
    this.teleportOwner();
  }

  private void teleportOwner() {
    Entity owner = this.getOwner();
    World arrowWorld = this.getWorld();

    if (arrowWorld instanceof ServerWorld serverWorld
        && owner != null
        && owner.getWorld().getRegistryKey().equals(arrowWorld.getRegistryKey())) {
      owner.teleportTo(
          new TeleportTarget(
              serverWorld,
              this.getPos(),
              owner.getVelocity(),
              owner.getYaw(),
              owner.getPitch(),
              TeleportTarget.NO_OP));
    }
  }

  @Override
  protected ItemStack asItemStack() {
    return super.asItemStack().withItem(Items.ARROW);
  }
}
