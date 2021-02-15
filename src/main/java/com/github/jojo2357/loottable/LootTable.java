package com.github.jojo2357.loottable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class LootTable {
    public final String json;
    private LootTable(LootTableBuilder bilder){
        JsonObject out = new JsonObject();
        out.addProperty("type", bilder.type);
        JsonArray pools = new JsonArray();
        for (Pool pool : bilder.pools){
            JsonObject thisPool = new JsonObject();
            thisPool.addProperty("rolls", pool.rolls);
            JsonArray entries = new JsonArray();
            for (String entry : pool.entries){
                JsonObject entryObject = new JsonObject();
                entryObject.addProperty("type", "minecraft:item");
                entryObject.addProperty("name", entry);
                entries.add(entryObject);
            }
            thisPool.add("entries", entries);
            JsonArray conditions = new JsonArray();
            for (String condition : pool.conditions){
                JsonObject considitonObject = new JsonObject();
                considitonObject.addProperty("condition", condition);
                conditions.add(considitonObject);
            }
            thisPool.add("conditions", conditions);
            pools.add(thisPool);
        }
        out.add("pools", pools);
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        System.out.println(gson.toJson(out));
        json = gson.toJson(out);
    }

    public static class LootTableBuilder{
        private String type;
        private ArrayList<Pool> pools = new ArrayList<>();

        public static LootTableBuilder newInstance(){
            return new LootTableBuilder();
        }

        public LootTableBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public LootTableBuilder addPool(Pool poolin){
            pools.add(poolin);
            return this;
        }

        public String build() {
            return new LootTable(this).json;
        }
    }

    public static class Pool{
        public int rolls = 1;
        public ArrayList<String> entries = new ArrayList<>();
        public ArrayList<String> conditions = new ArrayList<>();

        public void setRolls(int rolls) {
            this.rolls = rolls;
        }

        public void addEntry(String name){
            entries.add(name);
        }
        
        public void addCondition(String condition){
            conditions.add(condition);
        }
    }
}
