package com.fletchables.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

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
  }
}
