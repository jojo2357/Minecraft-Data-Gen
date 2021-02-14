package com.github.jojo2357.blocks;

import com.github.jojo2357.Main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

//WIP please ignore
public class VaryingBlockModel {
    private String json;

    private VaryingBlockModel(VaryingBlockModelBuilder builder){
        JsonObject out = new JsonObject();
        JsonObject variants = new JsonObject();
        for (int i = 0; i < builder.totalMapSize(); i++){
            int j = i;
            String thing = "";
            JsonObject otherThing = new JsonObject();
            for (String key : builder.variableMappings.keySet()) {
                thing += key + "=" +  builder.variableMappings.get(key).get(j % builder.variableMappings.get(key).size()) + ",";
                if (key.equals("facing"))
                    otherThing.addProperty("y", directionToRotation(builder.variableMappings.get(key).get(j % builder.variableMappings.get(key).size())));
                if (key.equals("color"))
                    otherThing.addProperty("model", Main.modid + ":block/" + builder.variableMappings.get(key).get(j % builder.variableMappings.get(key).size()) + builder.host);
                j /= builder.variableMappings.get(key).size();
            }
            thing = thing.substring(0, thing.length() - 1);
            variants.add(thing, otherThing);
        }
        out.add("variants", variants);
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        System.out.println(gson.toJson(out));
        json = gson.toJson(out);
    }

    private int directionToRotation(String in){
        if (in.equals("north"))
            return 0;
        if (in.equals("east"))
            return 90;
        if (in.equals("south"))
            return 180;
        return 270;
    }

    public static class VaryingBlockModelBuilder{
        HashMap<String, ArrayList<String>> variableMappings;
        String host;

        private VaryingBlockModelBuilder(){
            variableMappings = new HashMap<>();
        }

        public static VaryingBlockModelBuilder newInstance(){
            return new VaryingBlockModelBuilder();
        }

        public VaryingBlockModelBuilder addMapping(String key, ArrayList<String> mapping){
            variableMappings.put(key, mapping);
            return this;
        }

        public VaryingBlockModelBuilder blockFor(String block){
            host = block;
            return this;
        }

        public String build(){
            return new VaryingBlockModel(this).json;
        }

        private int totalMapSize(){
            int out = 1;
            for (ArrayList<String> map : variableMappings.values())
                out *= map.size();
            return out;
        }
    }
}
