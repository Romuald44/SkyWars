/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Romuald
 */
public class SkyWarsListener implements Listener {
    
    //List<Player> = new List<Player>;
    int task;
    int seconds = 21;
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        sendTitle(p, ChatColor.GREEN + "Bienvenue", ChatColor.BLUE + p.getName(), 20, 100, 20);
    }
    
    /*for(Player pls : Bukkit.getOnlinePlayers()) {
        pls.teleport(test);
    }*/
    /*@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        
        if(e.getFrom().getBlockX() == 82 && e.getFrom().getBlockY() == 72 && e.getFrom().getBlockZ() == 296) {
            Location test = new Location(p.getWorld(), 80.5, 89, 307.5);
            p.teleport(test);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Archer", ChatColor.RED + "Plaque de pression", 20, 100, 20);
        }
    }*/
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = (Player)e.getPlayer();

        if(e.getClickedBlock().getType() == Material.LEVER //Départ en verre
                && e.getClickedBlock().getX() == 83 
                && e.getClickedBlock().getY() == 89 
                && e.getClickedBlock().getZ() == 307) {
            
            Location start = new Location(p.getWorld(), 80.5, 77, 299.5);
            p.teleport(start);
            sendTitle(p, ChatColor.GREEN + "Map "+ ChatColor.BLUE + "Forêt", "", 20, 100, 20);
        }
        else if(e.getClickedBlock().getType() == Material.STONE_PLATE 
                && e.getClickedBlock().getX() == 82 
                && e.getClickedBlock().getY() == 72
                && e.getClickedBlock().getZ() == 296) { //Vers Plateforme
            
            Location plateform = new Location(p.getWorld(), 80.5, 89, 307.5);
            p.teleport(plateform);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Archer", ChatColor.RED + "Plaque de pression", 20, 100, 20);
        }
    }
    
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {//On récupère la commande tapée par l'utilisateur
        
        Player p = e.getPlayer();// On récupère le joueur.
        World test = p.getWorld();

        if(e.getMessage().equalsIgnoreCase("/heal")) {
            p.setHealth(20.0);
            //p.sendTitle("Heal", "Récupération du total de vie");
            sendTitle(p, "Heal", ChatColor.RED + "Récupération du total de vie", 20, 100, 20);
        }
        else if(e.getMessage().equalsIgnoreCase("/list")) {
            p.sendMessage(test.getPlayers().toString());
            p.sendMessage("Liste players = "+p.getPlayerListName());
        }
        else if(e.getMessage().equalsIgnoreCase("/count")) {
            Countdown();
            p.sendMessage("Count");
        }
    }
    
    public void sendTitle(Player p, String title, String subTitle, int fadeIn, int duration, int fadeOut)
    {
            CraftPlayer craftplayer = (CraftPlayer)p;
            PlayerConnection connection = craftplayer.getHandle().playerConnection;
            IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + title + "'}");
            IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + subTitle + "'}");
            PacketPlayOutTitle timesPacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleJSON, fadeIn, duration, fadeOut);
            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON);
            PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
            connection.sendPacket(timesPacket);
            connection.sendPacket(titlePacket);
            connection.sendPacket(subtitlePacket);
    }
    
    public void Countdown() {
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
            
            @Override
            public void run() {
                seconds--;
                
                if(seconds == 0) {
                    Bukkit.getScheduler().cancelTask(task);
                    starting();
                }
                if((seconds == 20) || (seconds <= 10)) {
                    Bukkit.broadcastMessage("Début de la partie dans "+ChatColor.RED+seconds+" secondes");
                }
            }
        }, 20, 20);
    }
    
    public void starting() {
        Location first = new Location(Bukkit.getWorld("world"), 80.5, 76, 299.5);
        first.getBlock().breakNaturally();
    }
}