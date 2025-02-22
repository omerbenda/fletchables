package com.fletchables.blocks;

import com.fletchables.entities.JumpTntEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class JumpTntBlock extends Block {
  public static final MapCodec<JumpTntBlock> CODEC = createCodec(JumpTntBlock::new);
  public static final BooleanProperty UNSTABLE = Properties.UNSTABLE;

  public JumpTntBlock(AbstractBlock.Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState().with(UNSTABLE, false));
  }

  @Override
  protected MapCodec<? extends Block> getCodec() {
    return CODEC;
  }

  @Override
  protected void onBlockAdded(
      BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
    if (!oldState.isOf(state.getBlock()) && world.isReceivingRedstonePower(pos)) {
      primeTnt(world, pos);
      world.removeBlock(pos, false);
    }
  }

  @Override
  protected void neighborUpdate(
      BlockState state,
      World world,
      BlockPos pos,
      Block sourceBlock,
      @Nullable WireOrientation wireOrientation,
      boolean notify) {
    if (world.isReceivingRedstonePower(pos)) {
      primeTnt(world, pos);
      world.removeBlock(pos, false);
    }
  }

  @Override
  public void onDestroyedByExplosion(ServerWorld world, BlockPos pos, Explosion explosion) {
    JumpTntEntity jumpTntEntity =
        new JumpTntEntity(
            world,
            (double) pos.getX() + 0.5,
            pos.getY(),
            (double) pos.getZ() + 0.5,
            explosion.getCausingEntity());
    int fuse = jumpTntEntity.getFuse();
    jumpTntEntity.setFuse((short) (world.random.nextInt(fuse / 4) + fuse / 8));
    world.spawnEntity(jumpTntEntity);
  }

  public static void primeTnt(World world, BlockPos pos) {
    primeTnt(world, pos, null);
  }

  private static void primeTnt(World world, BlockPos pos, @Nullable LivingEntity igniter) {
    if (!world.isClient()) {
      JumpTntEntity jumpTntEntity =
          new JumpTntEntity(
              world, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, igniter);
      world.spawnEntity(jumpTntEntity);
      world.playSound(
          (PlayerEntity) null,
          jumpTntEntity.getX(),
          jumpTntEntity.getY(),
          jumpTntEntity.getZ(),
          SoundEvents.ENTITY_TNT_PRIMED,
          SoundCategory.BLOCKS,
          1.0F,
          1.0F);
      world.emitGameEvent(igniter, GameEvent.PRIME_FUSE, pos);
    }
  }

  @Override
  protected ActionResult onUseWithItem(
      ItemStack stack,
      BlockState state,
      World world,
      BlockPos pos,
      PlayerEntity player,
      Hand hand,
      BlockHitResult hit) {
    if (!stack.isOf(Items.FLINT_AND_STEEL) && !stack.isOf(Items.FIRE_CHARGE)) {
      return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    } else {
      primeTnt(world, pos, player);
      world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
      Item item = stack.getItem();

      if (stack.isOf(Items.FLINT_AND_STEEL)) {
        stack.damage(1, player, LivingEntity.getSlotForHand(hand));
      } else {
        stack.decrementUnlessCreative(1, player);
      }

      player.incrementStat(Stats.USED.getOrCreateStat(item));

      return ActionResult.SUCCESS;
    }
  }

  @Override
  protected void onProjectileHit(
      World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
    if (world instanceof ServerWorld serverWorld) {
      BlockPos blockPos = hit.getBlockPos();
      Entity entity = projectile.getOwner();

      if (projectile.isOnFire() && projectile.canModifyAt(serverWorld, blockPos)) {
        primeTnt(world, blockPos, entity instanceof LivingEntity ? (LivingEntity) entity : null);
        world.removeBlock(blockPos, false);
      }
    }
  }

  @Override
  public boolean shouldDropItemsOnExplosion(Explosion explosion) {
    return false;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(UNSTABLE);
  }
}
