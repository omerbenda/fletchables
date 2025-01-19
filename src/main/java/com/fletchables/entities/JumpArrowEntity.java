package com.fletchables.entities;

import com.fletchables.init.ModEntityTypes;
import com.fletchables.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

public class JumpArrowEntity extends PersistentProjectileEntity {
  public static final float EXPLOSION_POWER = 5.0F;
  public static final float EXPLOSION_KNOCKBACK = 5.0F;

  public JumpArrowEntity(EntityType<? extends JumpArrowEntity> entityType, World world) {
    super(entityType, world);
  }

  public JumpArrowEntity(
      double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
    super(ModEntityTypes.JUMP_ARROW_ENTITY, x, y, z, world, stack, weapon);
  }

  public JumpArrowEntity(
      LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
    super(ModEntityTypes.JUMP_ARROW_ENTITY, owner, world, stack, shotFrom);
  }

  @Override
  protected ItemStack getDefaultItemStack() {
    return new ItemStack(ModItems.JUMP_ARROW);
  }

  @Override
  protected void onHit(LivingEntity target) {
    super.onHit(target);
    this.explode();
  }

  @Override
  protected void onBlockHit(BlockHitResult blockHitResult) {
    super.onBlockHit(blockHitResult);
    this.explode();
  }

  private void explode() {
    World world = this.getEntityWorld();

    world.createExplosion(
        this,
        Explosion.createDamageSource(world, this),
        getExplosionBehavior(),
        this.getX(),
        this.getY(),
        this.getZ(),
        EXPLOSION_POWER,
        false,
        World.ExplosionSourceType.TNT);

    this.discard();
  }

  protected ExplosionBehavior getExplosionBehavior() {
    return new EntityExplosionBehavior(this) {
      @Override
      public boolean canDestroyBlock(
          Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
        return false;
      }

      @Override
      public boolean shouldDamage(Explosion explosion, Entity entity) {
        return false;
      }

      @Override
      public float getKnockbackModifier(Entity entity) {
        return EXPLOSION_KNOCKBACK;
      }
    };
  }
}
