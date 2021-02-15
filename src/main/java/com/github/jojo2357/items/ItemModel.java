package com.github.jojo2357.items;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ItemModel {
    private String json;

    public ItemModel(ItemModelBuilder builder) {
        JsonObject out = new JsonObject();
        out.addProperty("parent", builder.parent);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(out));
        json = gson.toJson(out);
    }

    public static class ItemModelBuilder {
        private String parent;

        private ItemModelBuilder(){

        }

        public static ItemModelBuilder newInstance(){
            return new ItemModelBuilder();
        }

        public ItemModelBuilder withParent(String parent){
            this.parent = parent;
            return this;
        }

        public String build(){
            return new ItemModel(this).json;
        }
    }
}
