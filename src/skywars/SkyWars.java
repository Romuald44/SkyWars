/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
/**
 *
 * @author Romuald
 */
public class SkyWars extends JavaPlugin {

    //Console de bukkit
    private static ConsoleCommandSender console = Bukkit.getConsoleSender();
    
    //Méthode d'activation
    @Override
    public void onEnable() {
        //Message en vert
        console.sendMessage("§aSkyWars actif!");
        
        Listener l = new SkyWarsListener();//On crée une instance de notre classe qui implémente Listener
        PluginManager pm = getServer().getPluginManager();//On récupère le PluginManager du serveur
        pm.registerEvents(l, this);//On enregistre notre instance de Listener et notre plugin auprès du PluginManager
    }
    
    //Méthode de désactivation
    @Override
    public void onDisable() {
        //Message en rouge
        console.sendMessage("§aSkyWars desactive");
    }
    
    public static ConsoleCommandSender getConsole() {
        return console;
    }
}