package com.fletchables.init;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.fletchables.FletchablesMod.MOD_ID;

public class ModItems {
  public static final Item TEST_ITEM = register(new Item(new Item.Settings()), "test_item");

  public static void initialize() {}

  private static Item register(Item item, String id) {
    Identifier identifier = Identifier.of(MOD_ID, id);

    return Registry.register(Registries.ITEM, identifier, item);
  }
}
