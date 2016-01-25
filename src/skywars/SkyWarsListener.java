/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import java.util.ArrayList;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

/**
 *
 * @author Romuald
 */
public class SkyWarsListener implements Listener {
    
    ArrayList<Location> al = new ArrayList<Location>();
    ArrayList tab = new ArrayList();
    
    Location choice_class = new Location(Bukkit.getWorld("World"), 34, 101, -1);
    Location plateform = new Location(Bukkit.getWorld("World"), 21, 101, -55);
    int perso_ID;
    int temp;
    int task;
    int seconds = 21;
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        sendTitle(p, ChatColor.GREEN + "Bienvenue", ChatColor.BLUE + p.getName(), 20, 50, 20);
    }
    
    @EventHandler
    public void onSpawnPlayer(PlayerSpawnLocationEvent e) {
        e.setSpawnLocation(new Location(Bukkit.getWorld("World"), 0.5, 101, 0.5));
    }
    
    /*@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        p.sendMessage("X : "+p.getLocation().getBlockX());
        p.sendMessage("Y : "+p.getLocation().getBlockY());
        p.sendMessage("Z : "+p.getLocation().getBlockZ());
        
       /* if(e.getFrom().getBlockX() == 82 && e.getFrom().getBlockY() == 72 && e.getFrom().getBlockZ() == 296) {
            Location test = new Location(p.getWorld(), 80.5, 89, 307.5);
            p.teleport(test);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Archer", ChatColor.RED + "Plaque de pression", 20, 100, 20);
        }*/
    //}
    
    public Location onPlace(Player p) {
        
        al.add(new Location(Bukkit.getWorld("World"), 15, 102, -50));
        al.add(new Location(Bukkit.getWorld("World"), 16, 102, -50));
        al.add(new Location(Bukkit.getWorld("World"), 17, 102, -50));
        al.add(new Location(Bukkit.getWorld("World"), 18, 102, -50));
        al.add(new Location(Bukkit.getWorld("World"), 19, 102, -50));
        al.add(new Location(Bukkit.getWorld("World"), 20, 102, -50));
        al.add(new Location(Bukkit.getWorld("World"), 21, 102, -50));
        al.add(new Location(Bukkit.getWorld("World"), 22, 102, -50));
        
        if(perso_ID < 8) {
            temp = (int)(8*Math.random());
            while(tab.contains(temp)) {
                temp = (int)(8*Math.random());
            }
            perso_ID++;
            tab.add(temp);
            return al.get(temp);
        }
        else {
            return null;
        }
    }
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = (Player)e.getPlayer();
        //Entity test = (Player)e.getPlayer();
        
        if(e.getClickedBlock().getType() == Material.LEVER //Départ en verre
                && e.getClickedBlock().getX() == 27 
                && e.getClickedBlock().getY() == 101 
                && e.getClickedBlock().getZ() == -55-1) {
            
            /*Location start = onPlace(p);
            p.teleport(start);*/
            
            for(Player pls : Bukkit.getOnlinePlayers()) {
                sendTitle(pls, ChatColor.GREEN + "Map "+ ChatColor.BLUE + "Cité", "", 20, 50, 20);
            }
            
            //p.getWorld().setTime(3000);
                    
            Countdown();
            //p.setNoDamageTicks(25*20);
        }
        else if(e.getClickedBlock().getType() == Material.STONE_PLATE 
                && e.getClickedBlock().getX() == 4 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == 0) { //Vers Plateforme des classes
            
            p.teleport(choice_class);
        }
        else if((e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 26 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == -5-1) ||
                (e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 26 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == -6-1)) { //Stuff Tank
            
            p.teleport(plateform);
            
            /* ITEM */
            stuffTank(p);
            /* **** */
            
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Tank", ChatColor.RED + "Prêt à recevoir ?", 20, 50, 20);
        }
        else if((e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 26 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == -2-1) ||
                (e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 26 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == -1-1)) { //Stuff Bourrin
            
            p.teleport(plateform);
            
            /* ITEM */
            stuffBourrin(p);
            /* **** */
            
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Bourrin", ChatColor.RED + "Tape dans le fond chui pas ta mère !", 20, 50, 20);
        }
        else if((e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 26 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == 1) ||
                (e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 26 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == 2)) { //Stuff Archer
            
            p.teleport(plateform);
            
            /* ITEM */
            stuffArcher(p);
            /* **** */
            
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Archer", ChatColor.RED + "Ca va gicler !", 20, 50, 20);
        }
        else if((e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 42 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == 1) ||
                (e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 42 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == 2)) { //Stuff Assassin
            
            p.teleport(plateform);
            
            /* ITEM */
            stuffAssassin(p);
            /* **** */
            
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Assassin", ChatColor.RED + "Prendre par derrière ça fait mal", 20, 50, 20);
        }
        else if((e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 42 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == -1-1) ||
                (e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 42 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == -2-1)) { //Stuff Normal
            
            p.teleport(plateform);
            
            /* ITEM */
            stuffNormal(p);
            /* **** */
            
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Normal", ChatColor.RED + "Pour des gens normaux... Ou presque !", 20, 50, 20);
        }
        else if((e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 42 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == -6) ||
                (e.getClickedBlock().getType() == Material.WOOD_PLATE 
                && e.getClickedBlock().getX() == 42 
                && e.getClickedBlock().getY() == 101
                && e.getClickedBlock().getZ() == -7)) { //Stuff Normal
            
            p.teleport(plateform);
            
            /* ITEM */
            stuffPopo(p);
            /* **** */
            
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Potions", ChatColor.RED + "J'ai glissé sur un truc gluant", 20, 50, 20);
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
            /*Bukkit.broadcastMessage("Liste players = ");
            for(Player pls : Bukkit.getOnlinePlayers()) {
                Bukkit.broadcastMessage(pls.getPlayerListName());
            }*/
            p.teleport(onPlace(p));
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
        ItemStack arc = new ItemStack(Material.BOW);
        arc.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        arc.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
        arc.getItemMeta().spigot().setUnbreakable(true);

        ItemStack fleche = new ItemStack(Material.ARROW);

        ItemStack sign = new ItemStack(Material.SIGN);
        sign.addUnsafeEnchantment(Enchantment.KNOCKBACK, 4);
        sign.getItemMeta().spigot().setUnbreakable(true);

        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        helmet.getItemMeta().spigot().setUnbreakable(true);

        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        chestplate.getItemMeta().spigot().setUnbreakable(true);

        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leggings.getItemMeta().spigot().setUnbreakable(true);

        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.getItemMeta().spigot().setUnbreakable(true);

        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { arc, fleche, sign });//Ajouter l'épée dans l'inventaire
        p.getInventory().setArmorContents(new ItemStack[] { boots, leggings, chestplate, helmet  });//Enfilé l'équipement sur le joueur
        p.updateInventory();//Mettre à jour l'inventaire
    }

    public void stuffBourrin(Player p) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
        sword.getItemMeta().spigot().setUnbreakable(true);

        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        helmet.getItemMeta().spigot().setUnbreakable(true);

        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        chestplate.getItemMeta().spigot().setUnbreakable(true);

        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leggings.getItemMeta().spigot().setUnbreakable(true);

        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        boots.getItemMeta().spigot().setUnbreakable(true);

        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { sword });//Ajouter l'épée dans l'inventaire
        p.getInventory().setArmorContents(new ItemStack[] { boots, leggings, chestplate, helmet });//Enfilé l'équipement sur le joueur
        p.updateInventory();//Mettre à jour l'inventaire
    }

    public void stuffTank(Player p) {
        ItemStack daxe = new ItemStack(Material.WOOD_AXE);
        daxe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        
        ItemStack dhelmet = new ItemStack(Material.DIAMOND_HELMET);
        dhelmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        dhelmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        dhelmet.getItemMeta().spigot().setUnbreakable(true);
        
        ItemStack dchestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        dchestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        dchestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        dchestplate.getItemMeta().spigot().setUnbreakable(true);
        
        ItemStack dleggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        dleggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        dleggings.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        dleggings.getItemMeta().spigot().setUnbreakable(true);
        
        ItemStack dboots = new ItemStack(Material.DIAMOND_BOOTS);
        dboots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        dboots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        dboots.getItemMeta().spigot().setUnbreakable(true);
        
        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { daxe });//Ajouter l'épée dans l'inventaire
        p.getInventory().setArmorContents(new ItemStack[] { dboots, dleggings, dchestplate, dhelmet });//Enfilé l'équipement sur le joueur
        p.updateInventory();//Mettre à jour l'inventaire
    }

    public void stuffNormal(Player p) {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        sword.getItemMeta().spigot().setUnbreakable(true);

        ItemStack arc = new ItemStack(Material.BOW);
        arc.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        arc.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        arc.getItemMeta().spigot().setUnbreakable(true);

        ItemStack fleche = new ItemStack(Material.ARROW);

        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        helmet.getItemMeta().spigot().setUnbreakable(true);

        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        chestplate.getItemMeta().spigot().setUnbreakable(true);

        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leggings.getItemMeta().spigot().setUnbreakable(true);

        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.getItemMeta().spigot().setUnbreakable(true);

        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { sword, arc, fleche });//Ajouter l'épée dans l'inventaire
        p.getInventory().setArmorContents(new ItemStack[] { boots, leggings, chestplate, helmet });//Enfilé l'équipement sur le joueur
        p.updateInventory();//Mettre à jour l'inventaire
    }

    public void stuffAssassin(Player p) {
        ItemStack spade = new ItemStack(Material.DIAMOND_SPADE);
        spade.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
        spade.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        spade.getItemMeta().spigot().setUnbreakable(true);

        ItemStack briquet = new ItemStack(Material.FLINT_AND_STEEL);
        briquet.getItemMeta().spigot().setUnbreakable(true);

        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { spade, briquet });//Ajouter l'épée dans l'inventaire
        //p.getInventory().setArmorContents(new ItemStack[] {});//Enfilé l'équipement sur le joueur
        p.updateInventory();//Mettre à jour l'inventaire
    }
    
    public void stuffPopo(Player p) {
        ItemStack potion = new ItemStack(Material.POTION, 1, (short) 8197);
                
        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { potion });//Ajouter l'épée dans l'inventaire
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