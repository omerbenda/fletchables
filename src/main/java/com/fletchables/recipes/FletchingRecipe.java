package com.fletchables.recipes;

import com.fletchables.init.ModRecipeSerializers;
import com.fletchables.init.ModRecipeTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class FletchingRecipe implements Recipe<CraftingRecipeInput> {
  private final ItemStack output;
  private final List<Ingredient> recipeItems;

  public FletchingRecipe(ItemStack output, List<Ingredient> recipeItems) {
    this.output = output;
    this.recipeItems = recipeItems;
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
    return this.getResult(lookup).copy();
  }

  @Override
  public boolean fits(int width, int height) {
    return true;
  }

  @Override
  public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
    return this.output;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return ModRecipeSerializers.FLETCHING_SERIALIZER;
  }

  @Override
  public RecipeType<?> getType() {
    return ModRecipeTypes.FLETCHING;
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
                        ItemStack.CODEC.fieldOf("output").forGetter(FletchingRecipe::output),
                        Ingredient.ALLOW_EMPTY_CODEC
                            .listOf()
                            .fieldOf("recipeItems")
                            .forGetter(FletchingRecipe::recipeItems))
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
      ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
      int ingredientCount = buf.readVarInt();
      List<Ingredient> recipeItems = DefaultedList.ofSize(ingredientCount, Ingredient.EMPTY);
      recipeItems.replaceAll((ingredient) -> Ingredient.PACKET_CODEC.decode(buf));

      return new FletchingRecipe(output, recipeItems);
    }

    private static void write(RegistryByteBuf buf, FletchingRecipe recipe) {
      ItemStack.PACKET_CODEC.encode(buf, recipe.output());
      buf.writeVarInt(recipe.recipeItems.size());

      for (Ingredient ingredient : recipe.recipeItems()) {
        Ingredient.PACKET_CODEC.encode(buf, ingredient);
      }
    }
  }
}
