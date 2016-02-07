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
import skywars.ParseFile;
import skywars.SkyWars;

/**
 *
 * @author Romuald
 */
public class ChestsController {
    
    private static SkyWars plugin;
    
    private final List<ChestItem> chestItemList = Lists.newArrayList();
    private final List<ChestLocate> chestList = Lists.newArrayList();
    private final Random random = new Random();
   
    private List<Location> coffres = new ArrayList<Location>();
    
    private List<ItemStack> stuff = new ArrayList<ItemStack>();
    private int[] lucky_stuff = new int[24];
    
    private List<ItemStack> block = new ArrayList<ItemStack>();
    private int[] lucky_block = new int[11];
    
    private List<ItemStack> magic = new ArrayList<ItemStack>();
    private int[] lucky_magic = new int[7];
    
    private List<Integer> randomLoc = new ArrayList<Integer>();
    
    public ChestsController() {
        plugin = SkyWars.get();
        
        for (int i = 0; i < 27; i++) {
        	randomLoc.add(i);
        }
        
        loadChests();
        loadItems();
        populateChests();
        
    }
    
    public boolean loadItems() {
        chestItemList.clear();
        File itemsFile = new File(SkyWars.get().getDataFolder(), "items.yml");

        if (!itemsFile.exists()) {
        	SkyWars.get().saveResource("items.yml", false);
        }

        if (itemsFile.exists()) {
            FileConfiguration storage = YamlConfiguration.loadConfiguration(itemsFile);

            if (storage.contains("items")) {
                for (String item : storage.getStringList("items")) {
                    List<String> itemData = new LinkedList<>(Arrays.asList(item.split(" ")));

                    int chance = Integer.parseInt(itemData.get(0));
                    itemData.remove(itemData.get(0));
                    
                    ItemStack itemStack = ParseFile.parseItem(itemData);
                    
                    
                    if (itemStack != null) {
                        chestItemList.add(new ChestItem(itemStack, chance));
                    }
                }
            }
        }
        return true;
    }
    
    public boolean loadChests() {
        String map;
        chestList.clear();
        File chestFile = new File(SkyWars.get().getDataFolder(), "chests.yml");

        if (!chestFile.exists()) {
        	SkyWars.get().saveResource("chests.yml", false);
        }

        if (chestFile.exists()) {
            FileConfiguration storage = YamlConfiguration.loadConfiguration(chestFile);
            
            if (storage.contains("chests")) {
                if(storage.contains("chests.map")) {
                    map = storage.getString("chests.map");
                    
                    for (String chest : storage.getStringList("chests.position")) {
                        List<String> location_chest = new LinkedList<>(Arrays.asList(chest.split(" ")));
                        
                        //ParseFile.parseChests(location_chest);
                        //Location locat = ParseFile.parseChests(itemData);

                        chestList.add(new ChestLocate(map, Integer.parseInt(location_chest.get(0)), Integer.parseInt(location_chest.get(1)), Integer.parseInt(location_chest.get(2))));
                    }
                }
            }
        }
        return true;
    }
    
    public void populateChests() {
        
        for(ChestLocate chest : chestList) {
            Inventory inventory = chest.getChest().getBlockInventory();
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
    
    private class ChestLocate {

        private Location loc;
        private String map;
        private int x;
        private int y;
        private int z;

        public ChestLocate(String map, int x, int y, int z) {
            this.map = map;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Chest getChest() {
            loc = new Location(Bukkit.getWorld(map), x, y, z);
            Chest ch = (Chest) loc.getBlock().getState();
            return ch;
        }
    }
    
}
