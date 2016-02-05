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
   
    private List<Location> coffres = new ArrayList<Location>();
    
    private List<ItemStack> stuff = new ArrayList<ItemStack>();
    private int[] lucky_stuff = new int[24];
    
    private List<ItemStack> block = new ArrayList<ItemStack>();
    private int[] lucky_block = new int[11];
    
    private List<ItemStack> magic = new ArrayList<ItemStack>();
    private int[] lucky_magic = new int[7];
    
    private List<Integer> randomLoc = new ArrayList<Integer>();
    private List<Integer> randomDLoc = new ArrayList<Integer>();
    
    public ChestsController() {
        plugin = SkyWars.get();
        
        loadcoffres();
        loadstuff();
        loadblock();
        loadmagic();
        
        for (int i = 0; i < 27; i++) {
        	randomLoc.add(i);
        }
        
        for(int i = 0; i < 24; i++) {
            chestItemList.add(new ChestItem(stuff.get(i), lucky_stuff[i]));
        }
        for(int i = 0; i < 11; i++) {
            chestItemList.add(new ChestItem(block.get(i), lucky_block[i]));
        }
        for(int i = 0; i < 7; i++) {
            chestItemList.add(new ChestItem(magic.get(i), lucky_magic[i]));
        }
        
        for(int e = 0; e < 41; e++) {
            testpopulate(coffres.get(e));
        }
        
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
    
    public void loadcoffres() {
        //Bulles 1
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -74, 101, 329));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -80, 101, 324));
        
        //Bulles 2
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -116, 101, 338));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -110, 101, 343));
        
        //Bulles 3
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -152, 102, 323));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -146, 102, 328));
        
        //Bulles 4
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -166, 101, 288));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -160, 101, 293));
        
        //Bulles 5
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -152, 101, 252));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -146, 101, 257));
        
        //Bulles 6
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -116, 101, 238));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -110, 101, 243));
        
        //Bulles 7
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -80, 101, 252));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -74, 101, 257));
        
        //Bulles 8
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -66, 101, 288));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -60, 101, 293));
        
        //Inter
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -87, 102, 306));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -87, 102, 282));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -104, 102, 265));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -128, 102, 265));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -145, 102, 282));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -145, 102, 306));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -128, 102, 323));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -104, 102, 323));
        
        //Temple
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -120, 100, 292));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -119, 100, 291));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -118, 100, 290));
        
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -120, 100, 296));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -119, 100, 297));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -118, 100, 298));
        
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -114, 100, 290));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -113, 100, 291));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -112, 100, 292));
        
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -112, 100, 296));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -113, 100, 297));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -114, 100, 298));
        
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -116, 101, 293));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -116, 101, 295));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -115, 101, 294));
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -117, 101, 294));
        
        coffres.add(new Location(Bukkit.getWorld("SkyBool1"), -116, 102, 294));
        
    }
    
    public void loadstuff() {
        
        for(int i = 0; i < 24; i++) {
            if(i<4) {
                lucky_stuff[i] = 15;//Stuff Leather
            }
            else if(i>=4 && i<8) {
                lucky_stuff[i] = 10;//Stuff Gold
            }
            else if(i>=8 && i<12) {
                lucky_stuff[i] = 7;//Stuff Chainmail
            }
            else if(i>=12 && i<16) {
                lucky_stuff[i] = 5;//Stuff Iron
            }
            else if(i>=16 && i<20) {
                lucky_stuff[i] = 1;//Stuff Diamond
            }
            else if(i==20) {
                lucky_stuff[i] = 30;//Wood_SWORD
            }
            else if(i==21) {
                lucky_stuff[i] = 25;//Stone_SWORD
            }
            else if(i==22) {
                lucky_stuff[i] = 3;//Iron_SWORD
            }
            else if(i==23) {
                lucky_stuff[i] = 1;//Diamond_SWORD
            }
        }
        
        
        ItemStack leather_helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemStack leather_chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemStack leather_leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemStack leather_boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        
        stuff.add(leather_helmet);
        stuff.add(leather_chestplate);
        stuff.add(leather_leggings);
        stuff.add(leather_boots);
        
        ItemStack gold_helmet = new ItemStack(Material.GOLD_HELMET, 1);
        ItemStack gold_chestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        ItemStack gold_leggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
        ItemStack gold_boots = new ItemStack(Material.GOLD_BOOTS, 1);
        
        stuff.add(gold_helmet);
        stuff.add(gold_chestplate);
        stuff.add(gold_leggings);
        stuff.add(gold_boots);
        
        ItemStack chainmail_helmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        ItemStack chainmail_chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ItemStack chainmail_leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        ItemStack chainmail_boots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
        
        stuff.add(chainmail_helmet);
        stuff.add(chainmail_chestplate);
        stuff.add(chainmail_leggings);
        stuff.add(chainmail_boots);
        
        ItemStack iron_helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack iron_chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack iron_leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack iron_boots = new ItemStack(Material.IRON_BOOTS, 1);
        
        stuff.add(iron_helmet);
        stuff.add(iron_chestplate);
        stuff.add(iron_leggings);
        stuff.add(iron_boots);
        
        ItemStack diamond_helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemStack diamond_chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemStack diamond_leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemStack diamond_boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        
        stuff.add(diamond_helmet);
        stuff.add(diamond_chestplate);
        stuff.add(diamond_leggings);
        stuff.add(diamond_boots);
        
        ItemStack wood_sword = new ItemStack(Material.WOOD_SWORD, 1);
        ItemStack stone_sword = new ItemStack(Material.STONE_SWORD, 1);
        ItemStack iron_sword = new ItemStack(Material.IRON_SWORD, 1);
        ItemStack diamond_sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        
        stuff.add(wood_sword);
        stuff.add(stone_sword);
        stuff.add(iron_sword);
        stuff.add(diamond_sword);
    }
    
    public void loadblock() {
        
        lucky_block[0] = 85;
        lucky_block[1] = 50;
        lucky_block[2] = 100;
        lucky_block[3] = 75;

        lucky_block[4] = 50;
        lucky_block[5] = 50;
        lucky_block[6] = 3;

        lucky_block[7] = 50;
        lucky_block[8] = 25;
        lucky_block[9] = 50;
        lucky_block[10] = 25;
        
        
        ItemStack wood6 = new ItemStack(Material.WOOD, 6);
        ItemStack wood12 = new ItemStack(Material.WOOD, 12);
        ItemStack cobble6 = new ItemStack(Material.COBBLESTONE, 6);
        ItemStack cobble12 = new ItemStack(Material.COBBLESTONE, 12);

        ItemStack snow_ball = new ItemStack(Material.SNOW_BALL, 16);
        ItemStack egg = new ItemStack(Material.EGG, 16);
        ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL, 1);

        ItemStack lavabucket1 = new ItemStack(Material.LAVA_BUCKET, 1);
        ItemStack lavabucket2 = new ItemStack(Material.LAVA_BUCKET, 2);
        ItemStack waterbucket1 = new ItemStack(Material.WATER_BUCKET, 1);
        ItemStack waterbucket2 = new ItemStack(Material.WATER_BUCKET, 2);
        
        block.add(wood6);
        block.add(wood12);
        block.add(cobble6);
        block.add(cobble12);

        block.add(snow_ball);
        block.add(egg);
        block.add(enderpearl);

        block.add(lavabucket1);
        block.add(lavabucket2);
        block.add(waterbucket1);
        block.add(waterbucket2);
    }
    
    public void loadmagic() {
        
        lucky_magic[0] = 90;
        lucky_magic[1] = 50;
        lucky_magic[2] = 25;

        lucky_magic[3] = 1;
        lucky_magic[4] = 50;
        lucky_magic[5] = 25;
        lucky_magic[6] = 1;
        
        
        ItemStack apple = new ItemStack(Material.APPLE, 1);
        ItemStack goldenapple1 = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemStack goldenapple2 = new ItemStack(Material.GOLDEN_APPLE, 2);

        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemStack arrow1 = new ItemStack(Material.ARROW, 2);
        ItemStack arrow2 = new ItemStack(Material.ARROW, 4);
        ItemStack arrow3 = new ItemStack(Material.ARROW, 12);
        
        magic.add(apple);
        magic.add(goldenapple1);
        magic.add(goldenapple2);

        magic.add(bow);
        magic.add(arrow1);
        magic.add(arrow2);
        magic.add(arrow3);
    }
    
    public void testpopulate(Location chest) {
        Chest ch = (Chest) chest.getBlock().getState();
        Inventory inventory = ch.getBlockInventory();
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
