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
    public static String modid = "mosaic_blocks";

    public static void main(String[] args) throws IOException {
        deMetaMenuStuff();
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
        /*ArrayList<File> imagesToRun = getRunFiles();
        for (File file : imagesToRun){
            FileWriter fw = new FileWriter("./generated/" + file.getName().split("\\.")[0] + ".json");
            //fw.write(VaryingBlockModel.VaryingBlockModelBuilder.newInstance().addMapping("facing", new ArrayList<>(Arrays.asList("north", "south", "east", "west"))).blockFor(file.getName().substring(file.getName().indexOf('_')).split("\\.")[0]).addMapping("color", new ArrayList<>(Arrays.asList("red", "white", "yellow", "purple", "pink", "orange", "magenta", "lime", "light_gray", "light_blue", "green", "gray", "cyan", "brown", "blue", "black"))).build());
            fw.write(BlockModel.BlockModelBuilder.newInstance().withParent("block/cube_all").setTexture("all", modid + ":block/" + file.getName().split("\\.")[0]).build());
            //fw.write(ItemModel.ItemModelBuilder.newInstance().withParent(modid + ":" + "block/" + file.getName().split("\\.")[0]).build());
            fw.close();
        }*/
    }

    private static void deMetaMenuStuff() throws IOException {
        Scanner kb = new Scanner(System.in);
        System.out.println("Welcome to jojo's baller datagen!");
        System.out.println("Github: https://github.com/jojo2357/Minecraft-Data-Gen");
        System.out.print("Please enter your modid: ");
        modid = getResponse(kb);
        System.out.println("Datagenning for " + modid);
        while (true) {
            System.out.println("\nPlease enter one of the following numbers (enter to exit):");
            System.out.println("1) Loot tabling");
            System.out.println("2) Block modeling");
            System.out.println("3) Block stating");
            System.out.println("4) Item modeling");
            String response = getResponse(kb);
            if (response.equals(""))
                break;
            switch (response.charAt(0)) {
                case '1' -> doSpecialLootTableStuff(kb);
                case '2' -> createBlockModel(kb, getTemplate(kb));
                case '3' -> doComplicatedBlockStating(kb);
                case '4' -> doItemStuff(kb);
                default -> System.out.println("Invalid choice");
            }
        }
        kb.close();
    }

    private static void doSpecialLootTableStuff(Scanner kb) throws IOException {
        System.out.print("enter the relative directory of your image assets: ");
        String workingDir = getResponse(kb);
        for (File fyle : getRunFiles(workingDir))
            System.out.println(fyle.getName());
        System.out.println("Thats all the files i could find ^^^");
        System.out.println("This part is kinda complicated, and needs improvement. Enter a string that every file to be generated has (ie. if all the files that you want item models generated for have \"white_\" then enter that.");
        String filter = getResponse(kb);
        String userIn;
        LootTable.LootTableBuilder bilder = LootTable.LootTableBuilder.newInstance();
        LootTable.Pool currentPool = new LootTable.Pool();
        System.out.println("Please enter a table type (ie. minecraft:block or minecraft:entity)");
        bilder.setType(getResponse(kb));
        do {
            System.out.println("Current pool: \n" + currentPool.toString());
            System.out.println("\nWhat would you like to add?");
            System.out.println("1) new pool");
            System.out.println("2) new entry");
            System.out.println("3) new condition");
            userIn = getResponse(kb);
            if (userIn.length() == 0) {
                bilder.addPool(currentPool);
                break;
            }
            switch (userIn.charAt(0)){
                case '1':
                    bilder.addPool(currentPool);
                    currentPool = new LootTable.Pool();
                    break;
                case '2':
                    System.out.print("Enter fully qualified entry (to drop itself, enter nothing): ");
                    currentPool.addEntry(getResponse(kb));
                    break;
                case '3':
                    System.out.print("Enter fully qualified condition: ");
                    currentPool.addCondition(getResponse(kb));
                    break;
            }
        } while (true);
        for (File fyle : getRunFiles(workingDir, filter)) {
            FileWriter fw = new FileWriter("./generated/" + fyle.getName().split("\\.")[0] + ".json");
            fw.write(bilder.build(modid + ":" + fyle.getName().split("\\.")[0]));
            fw.close();
        }
    }

    private static void doItemStuff(Scanner kb) throws IOException {
        System.out.print("enter the relative directory of your image assets: ");
        String workingDir = getResponse(kb);
        for (File fyle : getRunFiles(workingDir))
            System.out.println(fyle.getName());
        System.out.println("Thats all the files i could find ^^^");
        System.out.println("This part is kinda complicated, and needs improvement. Enter a string that every file to be generated has (ie. if all the files that you want item models generated for have \"white_\" then enter that.");
        String filter = getResponse(kb);
        for (File fyle : getRunFiles(workingDir, filter)) {
            FileWriter fw = new FileWriter("./generated/" + fyle.getName().split("\\.")[0] + ".json");
            fw.write(ItemModel.ItemModelBuilder.newInstance().withParent(modid + ":" + "block/" + fyle.getName().split("\\.")[0]).build());
            fw.close();
        }
    }

    private static void doComplicatedBlockStating(Scanner kb) {
        System.out.println("This is a stub i dunno how to reasonably automate it, sorry");
        getRunFiles();
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
        ArrayList<File> out = new ArrayList<>(Arrays.asList(root.listFiles()));
        //out.removeIf(file -> !file.getName().contains("white_"));
        return out;
    }

    private static ArrayList<File> getRunFiles(String directory) {
        File root = new File(directory);
        ArrayList<File> out = new ArrayList<>(Arrays.asList(root.listFiles()));
        //out.removeIf(file -> !file.getName().contains("white_"));
        return out;
    }

    private static ArrayList<File> getRunFiles(String directory, String filter) {
        /*System.out.println(root.isDirectory());
        System.out.println(root.getAbsolutePath());
        System.out.println(root.exists());*/
        ArrayList<File> out = getRunFiles(directory);
        if (!filter.equals(""))
            out.removeIf(file -> !file.getName().contains(filter));
        return out;
    }
}
