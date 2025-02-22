package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.blocks.JumpTntBlock;
import java.util.function.Function;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
  public static Block JUMP_TNT_BLOCK =
      register(
          JumpTntBlock::new,
          AbstractBlock.Settings.create()
              .mapColor(MapColor.ORANGE)
              .breakInstantly()
              .sounds(BlockSoundGroup.GRASS)
              .burnable()
              .solidBlock(Blocks::never),
          "jump_tnt");

  public static void initialize() {}

  private static Block register(
      Function<Block.Settings, Block> blockFunction, AbstractBlock.Settings settings, String id) {
    Identifier identifier = Identifier.of(FletchablesMod.MOD_ID, id);
    settings = settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier));
    Block block = blockFunction.apply(settings);
    BlockItem blockItem =
        new BlockItem(
            block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier)));

    Registry.register(Registries.ITEM, identifier, blockItem);

    return Registry.register(Registries.BLOCK, identifier, block);
  }
}
