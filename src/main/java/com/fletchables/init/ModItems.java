package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.items.EnderArrowItem;
import com.fletchables.items.ExplosiveArrowItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
  public static final Item EXPLOSIVE_ARROW =
      register(new ExplosiveArrowItem(new Item.Settings()), "explosive_arrow");
  public static final Item ENDER_ARROW =
      register(new EnderArrowItem(new Item.Settings()), "ender_arrow");

  public static void initialize() {}

  private static Item register(Item item, String id) {
    Identifier identifier = Identifier.of(FletchablesMod.MOD_ID, id);

    return Registry.register(Registries.ITEM, identifier, item);
  }
}
