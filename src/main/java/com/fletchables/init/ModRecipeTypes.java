package com.fletchables.init;

import com.fletchables.FletchablesMod;
import com.fletchables.recipes.FletchingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeTypes {
  public static final RecipeType<FletchingRecipe> FLETCHING = register("fletching");

  public static void initialize() {}

  public static <T extends Recipe<?>> RecipeType<T> register(String id) {
    Identifier identifier = Identifier.of(FletchablesMod.MOD_ID, id);

    return Registry.register(
        Registries.RECIPE_TYPE,
        identifier,
        new RecipeType<T>() {
          @Override
          public String toString() {
            return id;
          }
        });
  }
}
