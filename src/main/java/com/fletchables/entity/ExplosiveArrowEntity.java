package com.fletchables.entity;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ExplosiveArrowEntity extends PersistentProjectileEntity {
  public static final float EXPLOSION_POWER = 4.0F;

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

  @Override
  protected void onHit(LivingEntity target) {
    super.onHit(target);
    explode();
  }

  @Override
  protected void onBlockHit(BlockHitResult blockHitResult) {
    super.onBlockHit(blockHitResult);
    explode();
  }

  private void explode() {
    World world = this.getEntityWorld();

    world.createExplosion(
        this,
        Explosion.createDamageSource(world, this),
        null,
        this.getX(),
        this.getY(),
        this.getZ(),
        EXPLOSION_POWER,
        false,
        World.ExplosionSourceType.TNT);

    this.discard();
  }
}
