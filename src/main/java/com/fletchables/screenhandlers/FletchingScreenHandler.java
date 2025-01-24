package com.fletchables.screenhandlers;

import com.fletchables.init.ModRecipeTypes;
import com.fletchables.init.ModScreenHandlers;
import com.fletchables.recipes.FletchingRecipe;
import com.fletchables.screenhandlers.slots.FletchingResultSlot;
import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class FletchingScreenHandler extends ScreenHandler {
  private final Inventory inventory;
  private final ScreenHandlerContext context;
  private final RecipeInputInventory input;
  private final CraftingResultInventory result;
  private final PlayerEntity player;

  public FletchingScreenHandler(int syncId, PlayerInventory inventory) {
    this(syncId, inventory, ScreenHandlerContext.EMPTY);
  }

  public FletchingScreenHandler(
      int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
    super(ModScreenHandlers.FLETCHING, syncId);

    this.inventory = inventory;
    this.context = context;
    this.input = new CraftingInventory(this, 4, 1);
    this.result = new CraftingResultInventory();
    this.player = inventory.player;

    this.addInventorySlots();
    this.addCraftingSlots();
    this.addSlot(new FletchingResultSlot(inventory.player, this.input, this.result, 0, 147, 35));
  }

  @Override
  public void onContentChanged(Inventory inventory) {
    this.context.run((world, pos) -> updateResult(this, world, player, input, result));
  }

  protected static void updateResult(
      ScreenHandler handler,
      World world,
      PlayerEntity player,
      RecipeInputInventory input,
      CraftingResultInventory result) {
    if (!world.isClient()) {
      CraftingRecipeInput recipeInput = input.createRecipeInput();
      ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
      ItemStack resultItemStack = ItemStack.EMPTY;
      Optional<RecipeEntry<FletchingRecipe>> optionalRecipe =
          world
              .getServer()
              .getRecipeManager()
              .getFirstMatch(ModRecipeTypes.FLETCHING, recipeInput, world);

      if (optionalRecipe.isPresent()) {
        RecipeEntry<FletchingRecipe> recipeEntry = optionalRecipe.get();
        FletchingRecipe recipe = recipeEntry.value();

        if (result.shouldCraftRecipe(world, serverPlayer, recipeEntry)) {
          ItemStack itemStack = recipe.craft(recipeInput, world.getRegistryManager());

          if (itemStack.isItemEnabled(world.getEnabledFeatures())) {
            resultItemStack = itemStack;
          }
        }
      }

      result.setStack(0, resultItemStack);
      handler.setPreviousTrackedSlot(0, resultItemStack);
      serverPlayer.networkHandler.sendPacket(
          new ScreenHandlerSlotUpdateS2CPacket(
              handler.syncId, handler.nextRevision(), 0, resultItemStack));
    }
  }

  @Override
  public ItemStack quickMove(PlayerEntity player, int slot) {
    return null;
  }

  @Override
  public boolean canUse(PlayerEntity player) {
    return ScreenHandler.canUse(this.context, player, Blocks.FLETCHING_TABLE);
  }

  @Override
  public void onClosed(PlayerEntity player) {
    super.onClosed(player);
    this.context.run((world, pos) -> this.dropInventory(player, this.input));
  }

  private void addInventorySlots() {
    for (int x = 0; x < 9; x++) {
      this.addSlot(new Slot(this.inventory, x, 8 + x * 18, 142));
    }

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 9; x++) {
        this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
      }
    }
  }

  private void addCraftingSlots() {
    this.addSlot(new Slot(this.input, 0, 9, 53));
    this.addSlot(new Slot(this.input, 1, 27, 35));
    this.addSlot(new Slot(this.input, 2, 45, 17));
    this.addSlot(new Slot(this.input, 3, 94, 35));
  }
}
