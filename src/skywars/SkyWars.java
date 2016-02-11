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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
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
    private static Location spawn_start = new Location(Bukkit.getWorld("World"), 0.5, 101, 0.5);
    private static Location choice_class = new Location(Bukkit.getWorld("World"), 500.5, 101, 500.5);
    private static Location choice_skywars = new Location(Bukkit.getWorld("World"), -498.5, 103, -501.5);
    
    private WorldController wc;
    private GameController gc;
    private ChestsController cc;
    //private ScoreBoard sb;
    
    //Méthode d'activation
    @Override
    public void onEnable() {
        instance = this;
        wc = new WorldController();
        gc = new GameController();
        cc = new ChestsController();
        //sb = new ScoreBoard();
        
        //Message en vert
        console.sendMessage("§aSkyWars actif!");
        
        this.getCommand("joueurs").setExecutor(new Commands());
        this.getCommand("skywars").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
    }
    
    //Méthode de désactivation
    @Override
    public void onDisable() {
        //Message en rouge
        console.sendMessage("§aSkyWars desactive");
        wc.unloadWorld("SkyBool1");
        wc.deleteWorld(new File("SkyBool1"));
    }
    
    public static SkyWars get() {
        return instance;
    }
    
    public WorldController getWC() {
        return wc;
    }
    
    public GameController getGC() {
        return gc;
    }
    
    public ChestsController getCC() {
        return cc;
    }
    
    /*public ScoreBoard getSB() {
        return sb;
    }*/
    
    public Location getSpawn() {
        return spawn_start;
    }
    
    public Location getLobbyPVP() {
        return choice_class;
    }
    
    public Location getLobbySW() {
        return choice_skywars;
    }
    
    public void reset() {
        wc = new WorldController();
        gc = new GameController();
        cc = new ChestsController();
        
        this.getCommand("joueurs").setExecutor(new Commands());
        this.getCommand("skywars").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
    }
}