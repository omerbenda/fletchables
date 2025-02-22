package com.fletchables.entities;

import com.fletchables.init.ModBlocks;
import com.fletchables.init.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

public class JumpTntEntity extends Entity implements Ownable {
  private static final TrackedData<Integer> FUSE =
      DataTracker.registerData(JumpTntEntity.class, TrackedDataHandlerRegistry.INTEGER);
  private static final TrackedData<BlockState> BLOCK_STATE =
      DataTracker.registerData(JumpTntEntity.class, TrackedDataHandlerRegistry.BLOCK_STATE);

  private static final int DEFAULT_FUSE = 80;
  public static final float EXPLOSION_POWER = 5.0F;
  public static final float EXPLOSION_KNOCKBACK = 5.0F;
  private static final String BLOCK_STATE_NBT_KEY = "block_state";
  public static final String FUSE_NBT_KEY = "fuse";
  @Nullable private LivingEntity causingEntity;

  public JumpTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
    this(ModEntityTypes.JUMP_TNT_ENTITY, world);
    this.setPosition(x, y, z);
    double d = world.random.nextDouble() * 6.2831854820251465;
    this.setVelocity(-Math.sin(d) * 0.02, 0.20000000298023224, -Math.cos(d) * 0.02);
    this.setFuse(80);
    this.prevX = x;
    this.prevY = y;
    this.prevZ = z;
    this.causingEntity = igniter;
  }

  public JumpTntEntity(EntityType<? extends JumpTntEntity> entityType, World world) {
    super(entityType, world);
    this.intersectionChecked = true;
  }

  @Override
  protected void initDataTracker(DataTracker.Builder builder) {
    builder.add(FUSE, DEFAULT_FUSE);
    builder.add(BLOCK_STATE, ModBlocks.JUMP_TNT_BLOCK.getDefaultState());
  }

  @Override
  protected Entity.MoveEffect getMoveEffect() {
    return MoveEffect.NONE;
  }

  @Override
  public boolean canHit() {
    return !this.isRemoved();
  }

  @Override
  protected double getGravity() {
    return 0.04;
  }

  @Override
  public void tick() {
    this.tickPortalTeleportation();
    this.applyGravity();
    this.move(MovementType.SELF, this.getVelocity());
    this.tickBlockCollision();
    this.setVelocity(this.getVelocity().multiply(0.98));
    if (this.isOnGround()) {
      this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
    }

    int i = this.getFuse() - 1;
    this.setFuse(i);
    if (i <= 0) {
      this.discard();
      if (!this.getWorld().isClient) {
        this.explode();
      }
    } else {
      this.updateWaterState();
      if (this.getWorld().isClient()) {
        this.getWorld()
            .addParticle(
                ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
      }
    }
  }

  private void explode() {
    this.getWorld()
        .createExplosion(
            this,
            Explosion.createDamageSource(this.getWorld(), this),
            getExplosionBehavior(),
            this.getX(),
            this.getBodyY(0.0625),
            this.getZ(),
            EXPLOSION_POWER,
            false,
            World.ExplosionSourceType.TNT);
  }

  @Override
  protected void writeCustomDataToNbt(NbtCompound nbt) {
    nbt.putShort(FUSE_NBT_KEY, (short) this.getFuse());
    nbt.put(BLOCK_STATE_NBT_KEY, NbtHelper.fromBlockState(this.getBlockState()));
  }

  @Override
  protected void readCustomDataFromNbt(NbtCompound nbt) {
    this.setFuse(nbt.getShort(FUSE_NBT_KEY));

    if (nbt.contains(BLOCK_STATE_NBT_KEY, 10)) {
      this.setBlockState(
          NbtHelper.toBlockState(
              this.getWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK),
              nbt.getCompound(BLOCK_STATE_NBT_KEY)));
    }
  }

  @Override
  @Nullable
  public LivingEntity getOwner() {
    return this.causingEntity;
  }

  @Override
  public void copyFrom(Entity original) {
    super.copyFrom(original);

    if (original instanceof JumpTntEntity jumpTntEntity) {
      this.causingEntity = jumpTntEntity.causingEntity;
    }
  }

  public void setFuse(int fuse) {
    this.dataTracker.set(FUSE, fuse);
  }

  public int getFuse() {
    return this.dataTracker.get(FUSE);
  }

  public void setBlockState(BlockState state) {
    this.dataTracker.set(BLOCK_STATE, state);
  }

  public BlockState getBlockState() {
    return this.dataTracker.get(BLOCK_STATE);
  }

  @Override
  public final boolean damage(ServerWorld world, DamageSource source, float amount) {
    return false;
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
