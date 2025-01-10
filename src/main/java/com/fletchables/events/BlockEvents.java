package com.fletchables.events;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class BlockEvents {
  public static ActionResult interact(
      PlayerEntity playerEntity, World world, Hand hand, BlockHitResult hitResult) {
    if (!world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.FLETCHING_TABLE)) {
      return ActionResult.PASS;
    }

    if (playerEntity.isSneaking()
        && (!playerEntity.getMainHandStack().isEmpty()
            || !playerEntity.getOffHandStack().isEmpty())) {
      return ActionResult.PASS;
    }

    playerEntity.sendMessage(Text.literal("interacted with fletching table"));

    return ActionResult.SUCCESS;
  }
}
