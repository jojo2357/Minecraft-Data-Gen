package com.github.jojo2357;

import com.github.jojo2357.blocks.BlockModel;
import com.github.jojo2357.blocks.VaryingBlockModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static final String modid = "mosaic_blocks";

    public static void main(String[] args) throws IOException {
        //String texture_name = "loltest";
        //BlockModel.BlockModelBuilder.newInstance().withParent("minecraft:block/cube_all").allTexts(modid + ":blocks/" + texture_name).build();
        ArrayList<File> imagesToRun = getRunFiles();
        for (File file : imagesToRun){
            FileWriter fw = new FileWriter("./generated/" + file.getName().split("\\.")[0] + ".txt");
            //fw.write(VaryingBlockModel.VaryingBlockModelBuilder.newInstance().addMapping("facing", new ArrayList<>(Arrays.asList("north", "south", "east", "west"))).blockFor(file.getName().substring(file.getName().indexOf('_')).split("\\.")[0]).addMapping("color", new ArrayList<>(Arrays.asList("red", "white", "yellow", "purple", "pink", "orange", "magenta", "lime", "light_gray", "light_blue", "green", "gray", "cyan", "brown", "blue", "black"))).build());
            fw.write(BlockModel.BlockModelBuilder.newInstance().withParent("block/cube_all").setTexture("all", modid + ":blocks/" + file.getName().split("\\.")[0]).build());
            fw.close();
        }
    }

    private static ArrayList<File> getRunFiles(){
        File root = new File("src/main/resources/ilja_assets/block/block");
        System.out.println(root.isDirectory());
        System.out.println(root.getAbsolutePath());
        System.out.println(root.exists());
        ArrayList<File> out = new ArrayList<>(Arrays.asList(root.listFiles()));
        out.removeIf(file -> !file.getName().contains("white_"));
        return out;
    }
}
