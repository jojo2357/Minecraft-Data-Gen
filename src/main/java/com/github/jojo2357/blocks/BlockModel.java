package com.github.jojo2357.blocks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BlockModel {
    private String json;

    public BlockModel(BlockModelBuilder builder) {
        JsonObject out = new JsonObject();
        out.addProperty("parent", builder.parent.parent_id);
        JsonObject textures = new JsonObject();
        for (String key : builder.texture_name.keySet())
            textures.addProperty(key, builder.texture_name.get(key));
        out.add("textures", textures);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(out));
        json = gson.toJson(out);
    }

    public static class BlockModelBuilder{
        private BlockModelTemplate parent;
        private final HashMap<String, String> texture_name = new HashMap<>();

        private BlockModelBuilder(){

        }

        public static BlockModelBuilder newInstance(){
            return new BlockModelBuilder();
        }

        public BlockModelBuilder withParent(String parent){
            for (BlockModelTemplate template : BlockModelTemplate.allTemplates)
                if (template.parent_id.equals(parent)) {
                    this.parent = template;
                    texture_name.clear();
                    for (String key : template.textureKeys)
                        texture_name.put(key, "missing_text");
                }
            return this;
        }

        public BlockModelBuilder setTexture(String textureKey, String textureResolveable){
            if (texture_name.containsKey(textureKey))
                texture_name.put(textureKey, textureResolveable);
            return this;
        }

        public String build(){
            return new BlockModel(this).json;
        }
    }

    public static class BlockModelTemplate {
        public static ArrayList<BlockModelTemplate> allTemplates = new ArrayList<>(Arrays.asList(
                new BlockModelTemplate("block/cube_all", "all"),
                new BlockModelTemplate("block/button", "texture"),
                new BlockModelTemplate("block/button_inventory", "texture"),
                new BlockModelTemplate("block/button_pressed", "texture"),
                new BlockModelTemplate("block/door_bottom", "bottom", "top"),
                new BlockModelTemplate("block/door_bottom_rh", "bottom", "top"),
                new BlockModelTemplate("block/door_top", "bottom", "top"),
                new BlockModelTemplate("block/door_top_rh", "bottom", "top"),
                new BlockModelTemplate("block/template_fence_gate", "texture"),
                new BlockModelTemplate("block/template_fence_gate_open", "texture"),
                new BlockModelTemplate("block/template_fence_gate_wall", "texture"),
                new BlockModelTemplate("block/template_fence_gate_wall_open", "texture"),
                new BlockModelTemplate("block/fence_inventory", "texture"),
                new BlockModelTemplate("block/fence_post", "texture"),
                new BlockModelTemplate("block/fence_side", "texture"),
                new BlockModelTemplate("block/leaves", "all"),
                new BlockModelTemplate("block/cube_column", "end", "side"),
                new BlockModelTemplate("block/pressure_plate_up", "texture"),
                new BlockModelTemplate("block/pressure_plate_down", "texture"),
                new BlockModelTemplate("block/cross", "cross"),
                new BlockModelTemplate("block/slab", "bottom", "top", "side"),
                new BlockModelTemplate("block/slab_top", "bottom", "top", "side"),
                new BlockModelTemplate("block/stairs", "bottom", "top", "side"),
                new BlockModelTemplate("block/inner_stairs", "bottom", "top", "side"),
                new BlockModelTemplate("block/outer_stairs", "bottom", "top", "side"),
                new BlockModelTemplate("block/template_orientable_trapdoor_bottom", "texture"),
                new BlockModelTemplate("block/template_orientable_trapdoor_open", "texture"),
                new BlockModelTemplate("block/template_orientable_trapdoor_top", "texture"),
                new BlockModelTemplate("block/rail_flat", "rail"),
                new BlockModelTemplate("block/template_rail_raised_ne", "rail"),
                new BlockModelTemplate("block/template_rail_raised_sw", "rail"),
                new BlockModelTemplate("block/block", "particle", "body", "top"),
                new BlockModelTemplate("block/stem_fruit", "stem", "upperstem"),
                new BlockModelTemplate("block/tinted_cross", "cross"),
                new BlockModelTemplate("block/cube_bottom_top", "bottom", "top", "side"),
                new BlockModelTemplate("block/orientable_with_bottom", "particle", "bottom", "top", "side", "front"),
                new BlockModelTemplate("block/crop", "crop"),
                new BlockModelTemplate("block/template_glazed_terracotta", "particle", "pattern"),
                new BlockModelTemplate("block/template_glass_pane_noside", "pane"),
                new BlockModelTemplate("block/template_glass_pane_noside_alt", "pane"),
                new BlockModelTemplate("block/template_glass_pane_post", "edge", "pane"),
                new BlockModelTemplate("block/template_glass_pane_side", "edge", "pane"),
                new BlockModelTemplate("block/template_glass_pane_side_alt", "edge", "pane"),
                new BlockModelTemplate("block/orientable", "top", "front", "side"),
                new BlockModelTemplate("block/carpet", "particle", "wool"),
                new BlockModelTemplate("block/coral_fan", "fan"),
                new BlockModelTemplate("block/coral_wall_fan", "fan"),
                new BlockModelTemplate("block/cube_directional", "particle", "down", "up", "north", "east", "south", "west"),
                new BlockModelTemplate("block/anvil", "top"),
                new BlockModelTemplate("block/cube", "particle", "down", "up", "north", "east", "south", "west"),
                new BlockModelTemplate("block/cube_mirrored", "particle", "down", "up", "north", "east", "south", "west"),
                new BlockModelTemplate("block/orientable", "top", "front", "side"),
                new BlockModelTemplate("block/orientable_vertical", "front", "side"),
                new BlockModelTemplate("block/template_farmland", "particle", "dirt", "farmland"),
                new BlockModelTemplate("block/fire_up", "particle", "fire"),
                new BlockModelTemplate("block/fire_up_alt", "particle", "fire"),
                new BlockModelTemplate("block/four_turtle_eggs", "all")
        ));

        public String parent_id;
        public ArrayList<String> textureKeys;

        public BlockModelTemplate(){
            allTemplates.add(this);
        }

        public BlockModelTemplate(String parent, String...textures){
            parent_id = parent;
            textureKeys = new ArrayList<>(Arrays.asList(textures));
        }

        public BlockModelTemplate addTexture(String key) {
            boolean dupe = false;
            for (String textureKey : textureKeys) {
                if (textureKey.equals(key)) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe)
                textureKeys.add(key);
            return this;
        }
    }
}
