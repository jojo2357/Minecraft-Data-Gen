package com.github.jojo2357.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

//WIP please ignore
public class ShapedRecipe {
    private ShapedRecipe(ShapedRecipeBuilder builder){
        JsonObject createdJson = new JsonObject();
        if (builder.shaped)
            createdJson.addProperty("type", "minecraft:crafting_shaped");
        else
            createdJson.addProperty("type", "minecraft:crafting_shapeless");
        createdJson.add("recipe", createRecipe(builder));
    }

    private JsonArray createRecipe(ShapedRecipeBuilder builder) {
        JsonArray out = new JsonArray();

        return out;
    }

    public static class ShapedRecipeBuilder{
        private final String[] items = new String[9];
        private Boolean shaped;

        private ShapedRecipeBuilder(){
            for (String str : items)
                str = "";
        }

        public static ShapedRecipeBuilder newInstance(){
            return new ShapedRecipeBuilder();
        }

        public ShapedRecipeBuilder withItemIn(String itemResolveable, int...locations){
            for (int i : locations)
                items[i] = itemResolveable;
            return this;
        }

        public ShapedRecipeBuilder withEmptySpots(int...locations){
            for (int i : locations)
                items[i] = " ";
            return this;
        }

        public ShapedRecipeBuilder shaped(boolean shaped){
            this.shaped = shaped;
            return this;
        }

        public void build(){
            new ShapedRecipe(this);
        }
    }
}
