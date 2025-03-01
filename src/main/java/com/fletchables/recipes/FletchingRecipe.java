package com.fletchables.recipes;

import com.fletchables.init.ModRecipeSerializers;
import com.fletchables.init.ModRecipeTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FletchingRecipe implements Recipe<CraftingRecipeInput> {
  private final List<Ingredient> recipeItems;
  private final ItemStack output;
  @Nullable private IngredientPlacement ingredientPlacement;

  public FletchingRecipe(List<Ingredient> recipeItems, ItemStack output) {
    this.recipeItems = recipeItems;
    this.output = output;
  }

  @Override
  public boolean matches(CraftingRecipeInput input, World world) {
    if (input.getWidth() * input.getHeight() != this.recipeItems.size()) {
      return false;
    }

    for (int index = 0; index < this.recipeItems.size(); index++) {
      Ingredient ingredient = recipeItems.get(index);

      if (!ingredient.test(input.getStackInSlot(index))) {
        return false;
      }
    }

    return true;
  }

  @Override
  public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
    return this.output.copy();
  }

  @Override
  public RecipeSerializer<? extends Recipe<CraftingRecipeInput>> getSerializer() {
    return ModRecipeSerializers.FLETCHING_SERIALIZER;
  }

  @Override
  public RecipeType<? extends Recipe<CraftingRecipeInput>> getType() {
    return ModRecipeTypes.FLETCHING;
  }

  @Override
  public IngredientPlacement getIngredientPlacement() {
    if (this.ingredientPlacement == null) {
      this.ingredientPlacement =
          IngredientPlacement.forMultipleSlots(
              this.recipeItems.stream().map(Optional::ofNullable).toList());
    }

    return this.ingredientPlacement;
  }

  @Override
  public RecipeBookCategory getRecipeBookCategory() {
    return null;
  }

  public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
    return DefaultedList.ofSize(input.size(), ItemStack.EMPTY);
  }

  public ItemStack output() {
    return this.output;
  }

  public List<Ingredient> recipeItems() {
    return this.recipeItems;
  }

  public static class Serializer implements RecipeSerializer<FletchingRecipe> {
    public static final MapCodec<FletchingRecipe> CODEC =
        RecordCodecBuilder.mapCodec(
            (instance) ->
                instance
                    .group(
                        Ingredient.CODEC
                            .listOf()
                            .fieldOf("recipeItems")
                            .forGetter(FletchingRecipe::recipeItems),
                        ItemStack.CODEC.fieldOf("output").forGetter(FletchingRecipe::output))
                    .apply(instance, FletchingRecipe::new));

    public static final PacketCodec<RegistryByteBuf, FletchingRecipe> PACKET_CODEC =
        PacketCodec.ofStatic(Serializer::write, Serializer::read);

    public Serializer() {}

    @Override
    public MapCodec<FletchingRecipe> codec() {
      return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, FletchingRecipe> packetCodec() {
      return PACKET_CODEC;
    }

    private static FletchingRecipe read(RegistryByteBuf buf) {
      int ingredientCount = buf.readVarInt();
      List<Ingredient> recipeItems =
          DefaultedList.ofSize(ingredientCount, Ingredient.ofItem(Items.AIR));
      recipeItems.replaceAll((ingredient) -> Ingredient.PACKET_CODEC.decode(buf));
      ItemStack output = ItemStack.PACKET_CODEC.decode(buf);

      return new FletchingRecipe(recipeItems, output);
    }

    private static void write(RegistryByteBuf buf, FletchingRecipe recipe) {
      buf.writeVarInt(recipe.recipeItems.size());

      for (Ingredient ingredient : recipe.recipeItems()) {
        Ingredient.PACKET_CODEC.encode(buf, ingredient);
      }

      ItemStack.PACKET_CODEC.encode(buf, recipe.output());
    }
  }
}
