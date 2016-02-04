/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import java.io.File;
import skywars.controller.WorldController;
import skywars.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import skywars.controller.ChestsController;
import skywars.controller.GameController;
/**
 *
 * @author Romuald
 */
public class SkyWars extends JavaPlugin {

    //Console de bukkit
    private static ConsoleCommandSender console = Bukkit.getConsoleSender();
    
    private static SkyWars instance;
    private Location spawn_start = new Location(Bukkit.getWorld("World"), 0.5, 101, 0.5);
    private Location choice_class = new Location(Bukkit.getWorld("World"), 500.5, 101, 500.5);
    private Location choice_skywars = new Location(Bukkit.getWorld("World"), -498.5, 103, -501.5);
    private Location plateform = new Location(Bukkit.getWorld("World"), 21, 101, -55);
    
    private static WorldController wc;
    private static GameController gc;
    private static ChestsController cc;
    
    //Méthode d'activation
    @Override
    public void onEnable() {
        instance = this;
        wc = new WorldController();
        gc = new GameController();
        cc = new ChestsController();
        
        Location test = new Location(Bukkit.getWorld("SkyBool1"), -74, 101, 329);
        Chest ch = (Chest) test.getBlock().getState();
        Inventory inv = ch.getInventory();
        
        inv.setItem(0, new ItemStack(Material.COBBLESTONE));
        //cc.populateChest(test);
        
        //Message en vert
        console.sendMessage("§aSkyWars actif!");
        
        this.getCommand("joueurs").setExecutor(new Commands());
        this.getCommand("skywars").setExecutor(new Commands());
        this.getCommand("skybool").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
    }
    
    //Méthode de désactivation
    @Override
    public void onDisable() {
        //Message en rouge
        console.sendMessage("§aSkyWars desactive");
    }
    
    /*public static InstanceMap getIM() {
        return instance.instance_skybool;
    }*/
    
    public static SkyWars get() {
        return instance;
    }
    
    public static WorldController getWC() {
        return wc;
    }
    
    public static GameController getGC() {
        return gc;
    }
    
    public static ChestsController getCC() {
        return cc;
    }
}