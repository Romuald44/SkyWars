/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars.controller;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import skywars.SkyWars;

/**
 *
 * @author Romuald
 */
public class ChestsController {
    
    private SkyWars plugin;
    private final List<ChestItem> chestItemList = Lists.newArrayList();
    private final Random random = new Random();
   
    private List<Location> coffres = new ArrayList<Location>(40);
    //private String[][] stuff = new String[24][3];
    private List<String[]> stuff = new ArrayList<String[]>(11);
    
    private List<Integer> randomLoc = new ArrayList<Integer>();
    private List<Integer> randomDLoc = new ArrayList<Integer>();
    
    public ChestsController() {
        plugin = SkyWars.get();
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -74, 101, 329));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -80, 101, 324));
        String[] test = {"85", "wood", "6"};
        stuff.add(test);
                
        85 wood 6
- 50 wood 12 
- 100 cobblestone 6
- 75 cobblestone 12
- 50 snow_ball 3
- 50 egg 2
- 1 ender_pearl 1
- 50 lava_bucket 1
- 25 lava_bucket 2
- 50 water_bucket 1
- 25 water_bucket 2
        /*ul = new ItemUtils();
        load();
        for (int i = 0; i < 27; i++) {
        	randomLoc.add(i);
        }
        for (int i = 0; i < 54; i++) {
        	randomDLoc.add(i);
        }*/
    }
    
    /*public void load() {
        chestItemList.clear();
        File chestFile = new File(SkyWars.get().getDataFolder(), "chest.yml");

        if (!chestFile.exists()) {
        	SkyWars.get().saveResource("chest.yml", false);
        }

        if (chestFile.exists()) {
            FileConfiguration storage = YamlConfiguration.loadConfiguration(chestFile);

            if (storage.contains("items")) {
                for (String item : storage.getStringList("items")) {
                    List<String> itemData = new LinkedList<String>(Arrays.asList(item.split(" ")));

                    int chance = Integer.parseInt(itemData.get(0));
                    itemData.remove(itemData.get(0));
                    
                    ItemStack itemStack = ul.parseItem(itemData);
                    
                    
                    if (itemStack != null) {
                        chestItemList.add(new ChestItem(itemStack, chance));
                    }
                }
            }
        }
    }*/

    public void populateChest(Chest chest) {
    	
        Inventory inventory = chest.getBlockInventory();
            inventory.clear();
        int added = 0;
        Collections.shuffle(randomLoc);

        for (ChestItem chestItem : chestItemList) {
            if (random.nextInt(100) + 1 <= chestItem.getChance()) {
                inventory.setItem(randomLoc.get(added), chestItem.getItem());
                if (added++ >= inventory.getSize()-1) {
                    break;
                }
            }
        }
    }
    
    private class ChestItem {

        private ItemStack item;
        private int chance;

        public ChestItem(ItemStack item, int chance) {
            this.item = item;
            this.chance = chance;
        }

        public ItemStack getItem() {
            return item;
        }

        public int getChance() {
            return chance;
        }
    }
    
}
