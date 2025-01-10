package com.fletchables.events;

import com.fletchables.screenhandlers.FletchingScreenHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEvents {
  public static ActionResult interact(
      PlayerEntity playerEntity, World world, Hand hand, BlockHitResult hitResult) {
    BlockPos pos = hitResult.getBlockPos();

    if (!world.getBlockState(pos).isOf(Blocks.FLETCHING_TABLE)) {
      return ActionResult.PASS;
    }

    if (playerEntity.isSneaking()
        && (!playerEntity.getMainHandStack().isEmpty()
            || !playerEntity.getOffHandStack().isEmpty())) {
      return ActionResult.PASS;
    }

    playerEntity.openHandledScreen(getFletchingTableScreen(world, pos));

    return ActionResult.SUCCESS;
  }

  private static SimpleNamedScreenHandlerFactory getFletchingTableScreen(
      World world, BlockPos pos) {
    return new SimpleNamedScreenHandlerFactory(
        (syncId, inventory, player) ->
            new FletchingScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)),
        Text.literal("placeholder"));
  }
}
