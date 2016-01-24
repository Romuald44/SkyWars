/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Romuald
 */
public class SkyWarsListener implements Listener {
    
    int place1,place2=0;
    int task;
    int seconds = 21;
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        sendTitle(p, ChatColor.GREEN + "Bienvenue", ChatColor.BLUE + p.getName(), 20, 50, 20);
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
    
    public Location onPlace(Player p) {
        int i=0;
        ArrayList al = new ArrayList();
        al.add(new Location(Bukkit.getWorld("World"), 80.5, 77, 299.5));
        al.add(new Location(Bukkit.getWorld("World"), 84.5, 77, 299.5));
            
        while(Bukkit.getOnlinePlayers() != p) {
            //essai.add(pls);
            //Player test = pls.getPlayer();//(Player)essai.get(0);
            /*test.sendMessage("X : "+test.getLocation().getX());
            test.sendMessage("Y : "+test.getLocation().getY());
            test.sendMessage("Z : "+test.getLocation().getZ());*/
            
            /*if(test.getLocation().getBlockX() == 80 && test.getLocation().getBlockY() == 77 && test.getLocation().getBlockZ() == 299) {
                test.sendMessage("Emplacement 1 occupé");
                place1++;
            }
            else if(test.getLocation().getBlockX() == 84 && test.getLocation().getBlockY() == 77 && test.getLocation().getBlockZ() == 299) {
                test.sendMessage("Emplacement 2 occupé");
                place2++;
            }*/
            i++;
            return (Location) al.get(i-1);
        }
        return null;
    }
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = (Player)e.getPlayer();
        //Entity test = (Player)e.getPlayer();
        
        if(e.getClickedBlock().getType() == Material.LEVER //Départ en verre
                && e.getClickedBlock().getX() == 83 
                && e.getClickedBlock().getY() == 89 
                && e.getClickedBlock().getZ() == 307) {
            
            Location start = onPlace(p);
            p.teleport(start);
            
            sendTitle(p, ChatColor.GREEN + "Map "+ ChatColor.BLUE + "Forêt", "", 20, 50, 20);
            p.getWorld().setTime(3000);
                    
            //Countdown();
            p.setNoDamageTicks(25*20);
        }
        else if(e.getClickedBlock().getType() == Material.LEVER //Départ en verre
                && e.getClickedBlock().getX() == 80 
                && e.getClickedBlock().getY() == 89 
                && e.getClickedBlock().getZ() == 304) {
            
            Location start = onPlace(p);
            p.teleport(start);
            
            sendTitle(p, ChatColor.GREEN + "Map "+ ChatColor.BLUE + "Forêt", "", 20, 50, 20);
            p.getWorld().setTime(3000);
                    
            //Countdown();
            p.setNoDamageTicks(25*20);
        }
        else if(e.getClickedBlock().getType() == Material.STONE_PLATE 
                && e.getClickedBlock().getX() == 82 
                && e.getClickedBlock().getY() == 72
                && e.getClickedBlock().getZ() == 296) { //Vers Plateforme
            
            Location plateform = new Location(p.getWorld(), 80.5, 89, 307.5);
            p.teleport(plateform);
            
            /* ITEM */
            stuffArcher(p);
            //hologramme(p);
            /* **** */
            
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Archer", ChatColor.RED + "Plaque de pression", 20, 50, 20);
        }
    }
    
    public void hologramme(Player p) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Plateforme");
        String name = sb.toString();
        
        ArmorStand as = Bukkit.getWorld(p.getWorld().getName()).spawn(p.getLocation(), ArmorStand.class);
        as.setVisible(false);
        as.setCustomName(name);
        as.setCustomNameVisible(true);
    }
    
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {//On récupère la commande tapée par l'utilisateur
        
        Player p = e.getPlayer();// On récupère le joueur.
        World test = p.getWorld();

        if(e.getMessage().equalsIgnoreCase("/heal")) {
            p.setHealth(20.0);
            //p.sendTitle("Heal", "Récupération du total de vie");
            sendTitle(p, "Heal", ChatColor.RED + "Récupération du total de vie", 20, 80, 20);
        }
        else if(e.getMessage().equalsIgnoreCase("/list")) {
            p.sendMessage(test.getPlayers().toString());
            p.sendMessage("Liste players = "+p.getPlayerListName());
        }
        else if(e.getMessage().equalsIgnoreCase("/meteo")) {
            p.sendMessage("Heure = "+test.getTime());
            p.sendMessage("Orage = "+test.hasStorm());
            test.setTime(3000);
        }
    }
    
    public void onChangeWeather(WeatherChangeEvent e) {
        if(e.getWorld().hasStorm()) {
            e.getWorld().setThundering(false);
            e.getWorld().setStorm(false);
            e.getWorld().setWeatherDuration(1000000);
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
        seconds = 21;
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
            
            @Override
            public void run() {
                seconds--;
                
                if(seconds == 0) {
                    Bukkit.getScheduler().cancelTask(task);
                    for(Player pls : Bukkit.getOnlinePlayers()) {
                        sendTitle(pls, ChatColor.RED + "GANG BANG !", "", 10, 20, 10);
                    }
                    starting();
                }
                if((seconds == 20) || (seconds > 0 && seconds <= 10)) {
                    for(Player pls : Bukkit.getOnlinePlayers()) {
                        sendTitle(pls, ChatColor.RED + Integer.toString(seconds), "", 10, 20, 10);
                    }
                    
                    //Bukkit.broadcastMessage("Début de la partie dans "+ChatColor.RED+seconds+" secondes");
                }
            }
        }, 20, 20);
    }
    
    public void stuffArcher(Player p) {
        ItemStack dsword = new ItemStack(Material.DIAMOND_SWORD);
        dsword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemStack ironhelmet = new ItemStack(Material.IRON_HELMET);
        ironhelmet.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemStack ironchestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ironchestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemStack ironleggings = new ItemStack(Material.IRON_LEGGINGS);
        ironleggings.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemStack ironboots = new ItemStack(Material.IRON_BOOTS);
        ironboots.addUnsafeEnchantment(Enchantment.DURABILITY, 3);

        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { dsword });//Ajouter l'épée dans l'inventaire
        p.getInventory().setArmorContents(new ItemStack[] { ironboots, ironleggings, ironchestplate, ironhelmet });//Enfilé l'équipement sur le joueur
        p.updateInventory();//Mettre à jour l'inventaire
    }
    
    public void starting() {
        Location first = new Location(Bukkit.getWorld("world"), 80.5, 76, 299.5);
        first.getBlock().breakNaturally();
    }
    
    /*public void flag(Player p, Team t) {
        if(t.getName().equals("red")) {
            ItemStack redFlag = new ItemStack(Material.BANNER, 1);
            BannerMeta redMeta = (BannerMeta) redFlag.getItemMeta();
            redMeta.setBaseColor(DyeColor.RED);
            redMeta.setDisplayName("§cRouge");
            redFlag.setItemMeta(redMeta);
            p.getInventory().setHelmet(redFlag);
        } else if(t.getName().equals("blue")) {
            ItemStack blueFlag = new ItemStack(Material.BANNER, 1);
            BannerMeta blueMeta = (BannerMeta) blueFlag.getItemMeta();
            blueMeta.setBaseColor(DyeColor.LIGHT_BLUE);
            blueMeta.setDisplayName("§9Bleu");
            blueFlag.setItemMeta(blueMeta);
            p.getInventory().setHelmet(blueFlag);
        }
    }*/
}