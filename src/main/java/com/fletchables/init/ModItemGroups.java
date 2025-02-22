package com.fletchables.init;

import com.fletchables.entities.JumpTntEntity;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ModItemGroups {
  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
        .register(
            content ->
                content.addAfter(
                    Items.SPECTRAL_ARROW,
                    ModItems.EXPLOSIVE_ARROW,
                    ModItems.ENDER_ARROW,
                    ModItems.JUMP_ARROW,
                    ModItems.TWISTING_ARROW,
                    ModItems.CHORUS_ARROW));

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE)
        .register(content -> content.addAfter(Blocks.TNT, ModBlocks.JUMP_TNT_BLOCK));

    DispenserBlock.registerProjectileBehavior(ModItems.EXPLOSIVE_ARROW);
    DispenserBlock.registerProjectileBehavior(ModItems.ENDER_ARROW);
    DispenserBlock.registerProjectileBehavior(ModItems.JUMP_ARROW);
    DispenserBlock.registerProjectileBehavior(ModItems.TWISTING_ARROW);
    DispenserBlock.registerProjectileBehavior(ModItems.CHORUS_ARROW);
    DispenserBlock.registerBehavior(
        ModBlocks.JUMP_TNT_BLOCK,
        new ItemDispenserBehavior() {
          protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            World world = pointer.world();
            BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
            JumpTntEntity tntEntity =
                new JumpTntEntity(
                    world,
                    (double) blockPos.getX() + 0.5,
                    blockPos.getY(),
                    (double) blockPos.getZ() + 0.5,
                    (LivingEntity) null);
            world.spawnEntity(tntEntity);
            world.playSound(
                (PlayerEntity) null,
                tntEntity.getX(),
                tntEntity.getY(),
                tntEntity.getZ(),
                SoundEvents.ENTITY_TNT_PRIMED,
                SoundCategory.BLOCKS,
                1.0F,
                1.0F);
            world.emitGameEvent((Entity) null, GameEvent.ENTITY_PLACE, blockPos);
            stack.decrement(1);

            return stack;
          }
        });
  }
}
