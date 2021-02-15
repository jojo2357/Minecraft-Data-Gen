package com.github.jojo2357;

import com.github.jojo2357.blocks.BlockModel;
import com.github.jojo2357.blocks.VaryingBlockModel;
import com.github.jojo2357.items.ItemModel;
import com.github.jojo2357.loottable.LootTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static final String modid = "mosaic_blocks";

    public static void main(String[] args) throws IOException {
        //doLootTableStuff();
        /*Scanner kb = new Scanner(System.in);
        //String texture_name = "loltest";
        //BlockModel.BlockModelBuilder.newInstance().withParent("minecraft:block/cube_all").allTexts(modid + ":blocks/" + texture_name).build();
        BlockModel.BlockModelTemplate.refreshStats();
        System.out.println("Please choose a template");
        for (BlockModel.BlockModelTemplate key : BlockModel.BlockModelTemplate.allTemplates){
            //System.out.println("%" + BlockModel.BlockModelTemplate.longestName + "s" +  key.parent_id + ": ");
            //System.out.print(key.parent_id.length() + " (" + BlockModel.BlockModelTemplate.longestName + ")");
            System.out.printf(("%" + (BlockModel.BlockModelTemplate.longestName + 2) + "s"), key.parent_id + ": ");
            for (String kee : key.textureKeys)
                System.out.print(kee + ", ");
            System.out.println();
        }
        BlockModel.BlockModelTemplate template = getTemplate(kb);
        createBlockModel(kb, template);
        kb.close();*/
        ArrayList<File> imagesToRun = getRunFiles();
        for (File file : imagesToRun){
            FileWriter fw = new FileWriter("./generated/" + file.getName().split("\\.")[0] + ".json");
            //fw.write(VaryingBlockModel.VaryingBlockModelBuilder.newInstance().addMapping("facing", new ArrayList<>(Arrays.asList("north", "south", "east", "west"))).blockFor(file.getName().substring(file.getName().indexOf('_')).split("\\.")[0]).addMapping("color", new ArrayList<>(Arrays.asList("red", "white", "yellow", "purple", "pink", "orange", "magenta", "lime", "light_gray", "light_blue", "green", "gray", "cyan", "brown", "blue", "black"))).build());
            //fw.write(BlockModel.BlockModelBuilder.newInstance().withParent("block/cube_all").setTexture("all", modid + ":blocks/" + file.getName().split("\\.")[0]).build());
            fw.write(ItemModel.ItemModelBuilder.newInstance().withParent(modid + ":" + "block/" + file.getName().split("\\.")[0]).build());
            fw.close();
        }
    }

    private static void doLootTableStuff() throws IOException {
        for (File fyle : getRunFiles()) {
            LootTable.LootTableBuilder bilder = LootTable.LootTableBuilder.newInstance();
            LootTable.Pool defaultPool = new LootTable.Pool();
            defaultPool.addCondition("minecraft:survives_explosion");
            defaultPool.addEntry(modid + ":" + fyle.getName().split("\\.")[0]);
            defaultPool.setRolls(1);
            FileWriter fw = new FileWriter("./generated/" + fyle.getName().split("\\.")[0] + ".json");
            //fw.write(VaryingBlockModel.VaryingBlockModelBuilder.newInstance().addMapping("facing", new ArrayList<>(Arrays.asList("north", "south", "east", "west"))).blockFor(file.getName().substring(file.getName().indexOf('_')).split("\\.")[0]).addMapping("color", new ArrayList<>(Arrays.asList("red", "white", "yellow", "purple", "pink", "orange", "magenta", "lime", "light_gray", "light_blue", "green", "gray", "cyan", "brown", "blue", "black"))).build());
            fw.write(bilder.addPool(defaultPool).setType("minecraft:block").build());
            fw.close();
        }
    }



    private static void createBlockModel(Scanner kb, BlockModel.BlockModelTemplate template){
        HashMap<String, String> selections = new HashMap<>();
        for (String kee : template.textureKeys)
            selections.put(kee, "Missing_texture");
        System.out.println("Please choose a face to change, press enter to generate");
        for (String kee : template.textureKeys)
            System.out.println(kee + ": " + selections.get(kee));
        while (true){
            if (kb.hasNextLine()){
                String kbIn = kb.nextLine();
                if (kbIn.equals("")) break;
                for (String key : template.textureKeys)
                    if (key.equals(kbIn)) {
                        System.out.print("Name a texture for " + key + ": ");
                        String response = getResponse(kb);
                        selections.put(key, response);
                        break;
                    }
                System.out.println("Please choose a face to change, press enter to generate");
                for (String kee : template.textureKeys)
                    System.out.println(kee + ": " + selections.get(kee));
            }
        }
        BlockModel.BlockModelBuilder bilder = BlockModel.BlockModelBuilder.newInstance().withParent(template.parent_id);
        for (String thing : selections.keySet())
            bilder.setTexture(thing, selections.get(thing));
        bilder.build();
    }

    private static String getResponse(Scanner kb){
        while (true){
            if (kb.hasNextLine()){
                return kb.nextLine();
            }
        }
    }

    private static BlockModel.BlockModelTemplate getTemplate(Scanner kb){
        while (true){
            if (kb.hasNextLine()) {
                String searchFor = kb.nextLine();
                BlockModel.BlockModelTemplate template = null;
                for (BlockModel.BlockModelTemplate temp : BlockModel.BlockModelTemplate.allTemplates){
                    if (temp.parent_id.equals(searchFor)){
                        template = temp;
                    }
                }
                if (template != null){
                    System.out.println("You chose parent " + template.parent_id);
                    return template;
                }
                System.out.print("Sorry could not find " + searchFor + " in the list of templates, please enter something else: ");
            }
        }
    }

    private static ArrayList<File> getRunFiles() {
        File root = new File("src/main/resources/ilja_assets/block/block");
        System.out.println(root.isDirectory());
        System.out.println(root.getAbsolutePath());
        System.out.println(root.exists());
        ArrayList<File> out = new ArrayList<>(Arrays.asList(root.listFiles()));
        out.removeIf(file -> !file.getName().contains("white_"));
        return out;
    }
}
