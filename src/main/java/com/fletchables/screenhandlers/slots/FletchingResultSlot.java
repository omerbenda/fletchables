package com.fletchables.screenhandlers.slots;

import com.fletchables.init.ModRecipeTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class FletchingResultSlot extends Slot {
  private final RecipeInputInventory input;
  private final PlayerEntity player;
  private int amount;

  public FletchingResultSlot(
      PlayerEntity player,
      RecipeInputInventory input,
      Inventory inventory,
      int index,
      int x,
      int y) {
    super(inventory, index, x, y);
    this.player = player;
    this.input = input;
  }

  public boolean canInsert(ItemStack stack) {
    return false;
  }

  public ItemStack takeStack(int amount) {
    if (this.hasStack()) {
      this.amount += Math.min(amount, this.getStack().getCount());
    }

    return super.takeStack(amount);
  }

  protected void onCrafted(ItemStack stack, int amount) {
    this.amount += amount;
    this.onCrafted(stack);
  }

  protected void onTake(int amount) {
    this.amount += amount;
  }

  protected void onCrafted(ItemStack stack) {
    if (this.amount > 0) {
      stack.onCraftByPlayer(this.player.getWorld(), this.player, this.amount);
    }

    this.amount = 0;
  }

  public void onTakeItem(PlayerEntity player, ItemStack stack) {
    this.onCrafted(stack);
    CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
    CraftingRecipeInput craftingRecipeInput = positioned.input();
    int left = positioned.left();
    int top = positioned.top();
    DefaultedList<ItemStack> defaultedList =
        this.getRecipeRemainders(craftingRecipeInput, player.getWorld());

    for (int y = 0; y < craftingRecipeInput.getHeight(); y++) {
      for (int x = 0; x < craftingRecipeInput.getWidth(); x++) {
        int m = x + left + (y + top) * this.input.getWidth();
        ItemStack itemStack = this.input.getStack(m);
        ItemStack itemStack2 = defaultedList.get(x + y * craftingRecipeInput.getWidth());

        if (!itemStack.isEmpty()) {
          this.input.removeStack(m, 1);
          itemStack = this.input.getStack(m);
        }

        if (!itemStack2.isEmpty()) {
          if (itemStack.isEmpty()) {
            this.input.setStack(m, itemStack2);
          } else if (ItemStack.areItemsAndComponentsEqual(itemStack, itemStack2)) {
            itemStack2.increment(itemStack.getCount());
            this.input.setStack(m, itemStack2);
          } else if (!this.player.getInventory().insertStack(itemStack2)) {
            this.player.dropItem(itemStack2, false);
          }
        }
      }
    }
  }

  private DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input, World world) {
    if (world instanceof ServerWorld serverWorld) {
      return serverWorld
          .getRecipeManager()
          .getFirstMatch(ModRecipeTypes.FLETCHING, input, serverWorld)
          .map((recipe) -> recipe.value().getRecipeRemainders(input))
          .orElseGet(() -> copyInput(input));
    } else {
      return CraftingRecipe.collectRecipeRemainders(input);
    }
  }

  private static DefaultedList<ItemStack> copyInput(CraftingRecipeInput input) {
    DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);

    for (int i = 0; i < defaultedList.size(); ++i) {
      defaultedList.set(i, input.getStackInSlot(i));
    }

    return defaultedList;
  }

  public boolean disablesDynamicDisplay() {
    return true;
  }
}
