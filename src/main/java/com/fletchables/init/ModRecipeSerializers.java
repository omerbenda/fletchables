package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.recipes.FletchingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializers {
  public static RecipeSerializer<FletchingRecipe> FLETCHING_SERIALIZER =
      register(new FletchingRecipe.Serializer(), "fletching");

  public static void initialize() {}

  public static <T extends Recipe<?>> RecipeSerializer<T> register(
      RecipeSerializer<T> serializer, String id) {
    Identifier identifier = Identifier.of(FletchablesMod.MOD_ID, id);

    return Registry.register(Registries.RECIPE_SERIALIZER, identifier, serializer);
  }
}
