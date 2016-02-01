/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
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
    
    WorldCreator wc = new WorldCreator("SkyBool");

    //WorldCreator cp_skybool = new WorldCreator("SkyBool").copy(skybool);
    private InstanceMap instance_skybool;
    
    //Méthode d'activation
    @Override
    public void onEnable() {
        instance = this;
        //Bukkit.getServer().createWorld(wc);
        instance_skybool = new InstanceMap(wc.createWorld());
        //Message en vert
        console.sendMessage("§aSkyWars actif!");
        
        this.getCommand("skywars").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
    }
    
    //Méthode de désactivation
    @Override
    public void onDisable() {
        //Message en rouge
        console.sendMessage("§aSkyWars desactive");
    }
    
    public static InstanceMap getIM() {
        return instance.instance_skybool;
    }
}