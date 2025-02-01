package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.items.EnderArrowItem;
import com.fletchables.items.ExplosiveArrowItem;
import com.fletchables.items.JumpArrowItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
  public static final Item EXPLOSIVE_ARROW =
      register(new ExplosiveArrowItem(new Item.Settings()), "explosive_arrow");
  public static final Item ENDER_ARROW =
      register(new EnderArrowItem(new Item.Settings()), "ender_arrow");
  public static final Item JUMP_ARROW =
      register(new JumpArrowItem(new Item.Settings()), "jump_arrow");

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
        .register(
            content ->
                content.addAfter(Items.SPECTRAL_ARROW, EXPLOSIVE_ARROW, ENDER_ARROW, JUMP_ARROW));

    DispenserBlock.registerProjectileBehavior(EXPLOSIVE_ARROW);
    DispenserBlock.registerProjectileBehavior(ENDER_ARROW);
    DispenserBlock.registerProjectileBehavior(JUMP_ARROW);
  }

  private static Item register(Item item, String id) {
    Identifier identifier = Identifier.of(FletchablesMod.MOD_ID, id);

    return Registry.register(Registries.ITEM, identifier, item);
  }
}
