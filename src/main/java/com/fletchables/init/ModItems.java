package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.items.*;
import java.util.function.Function;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
  public static final Item EXPLOSIVE_ARROW =
      register(ExplosiveArrowItem::new, new Item.Settings(), "explosive_arrow");
  public static final Item ENDER_ARROW =
      register(EnderArrowItem::new, new Item.Settings(), "ender_arrow");
  public static final Item JUMP_ARROW =
      register(JumpArrowItem::new, new Item.Settings(), "jump_arrow");
  public static final Item TWISTING_ARROW =
      register(TwistingArrowItem::new, new Item.Settings(), "twisting_arrow");
  public static final Item CHORUS_ARROW =
      register(ChorusArrowItem::new, new Item.Settings(), "chorus_arrow");
  public static final Item WATER_ARROW =
      register(
          (settings) ->
              new BlockPlacingArrowItem(settings, ModEntityTypes.WATER_ARROW_ENTITY, Blocks.WATER),
          new Item.Settings(),
          "water_arrow");
  public static final Item LAVA_ARROW =
      register(
          (settings) ->
              new BlockPlacingArrowItem(settings, ModEntityTypes.LAVA_ARROW_ENTITY, Blocks.LAVA),
          new Item.Settings(),
          "lava_arrow");
  public static final Item WEB_ARROW =
      register(
          (settings) ->
              new BlockPlacingArrowItem(settings, ModEntityTypes.WEB_ARROW_ENTITY, Blocks.COBWEB),
          new Item.Settings(),
          "web_arrow");
  public static final Item SUB_ARROW =
      register(SubArrowItem::new, new Item.Settings(), "sub_arrow");

  public static void initialize() {}

  private static Item register(
      Function<Item.Settings, Item> itemFunction, Item.Settings settings, String id) {
    Identifier identifier = Identifier.of(FletchablesMod.MOD_ID, id);
    settings = settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier));
    Item item = itemFunction.apply(settings);

    return Registry.register(Registries.ITEM, identifier, item);
  }
}
