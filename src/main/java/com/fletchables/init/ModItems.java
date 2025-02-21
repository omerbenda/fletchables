package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.items.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

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

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
        .register(
            content ->
                content.addAfter(
                    Items.SPECTRAL_ARROW,
                    EXPLOSIVE_ARROW,
                    ENDER_ARROW,
                    JUMP_ARROW,
                    TWISTING_ARROW,
                    CHORUS_ARROW));

    DispenserBlock.registerProjectileBehavior(EXPLOSIVE_ARROW);
    DispenserBlock.registerProjectileBehavior(ENDER_ARROW);
    DispenserBlock.registerProjectileBehavior(JUMP_ARROW);
    DispenserBlock.registerProjectileBehavior(TWISTING_ARROW);
    DispenserBlock.registerProjectileBehavior(CHORUS_ARROW);
  }

  private static Item register(
      Function<Item.Settings, Item> itemFunction, Item.Settings settings, String id) {
    Identifier identifier = Identifier.of(FletchablesMod.MOD_ID, id);
    settings = settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier));
    Item item = itemFunction.apply(settings);

    return Registry.register(Registries.ITEM, identifier, item);
  }
}
