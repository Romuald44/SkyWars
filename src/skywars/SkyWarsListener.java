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
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

/**
 *
 * @author Romuald
 */
public class SkyWarsListener implements Listener {
    
    ArrayList<Location> al = new ArrayList<Location>();
    ArrayList tab = new ArrayList();
    String[][] players_sky = new String[8][2];
    
    WorldCreator w = new WorldCreator("SkyBool");
    
    Location spawn_start = new Location(Bukkit.getWorld("World"), 0.5, 101, 0.5);
    Location choice_class = new Location(Bukkit.getWorld("World"), 500.5, 101, 500.5);
    Location plateform = new Location(Bukkit.getWorld("World"), 21, 101, -55);
    int perso_ID;
    int kill_total=0;
    int player_total=1;
    int temp;
    int task;
    int seconds = 21;
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        
        Joueurs test = new Joueurs(p.getName().toString());
        p.sendMessage(test.getName());
        
        int i=0;
        for(Player pls : Bukkit.getOnlinePlayers()) {
            players_sky[i][0]=pls.getName();
            players_sky[i][1]="1";
            i++;
        }
        
        sendTitle(p, ChatColor.GREEN + "Bienvenue", ChatColor.BLUE + p.getName(), 20, 50, 20);
    }
    
    @EventHandler
    public void onSpawnPlayer(PlayerSpawnLocationEvent e) {
        e.setSpawnLocation(spawn_start);
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        /*p.sendMessage("X : "+p.getLocation().getBlockX());
        p.sendMessage("Y : "+p.getLocation().getBlockY());
        p.sendMessage("Z : "+p.getLocation().getBlockZ());*/
        
        if((p.getLocation().getBlockX() == -12) &&
           (p.getLocation().getBlockY() == 102) &&
           (p.getLocation().getBlockZ() == 12)) {
            p.teleport(choice_class);
            sendTitle(p, ChatColor.GOLD + "Mode PVP", ChatColor.RED + "Choississez votre classe", 20, 50, 20);
        }
        else if((p.getLocation().getBlockX() == 501) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 489) || (p.getLocation().getBlockX() == 500) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 489) || (p.getLocation().getBlockX() == 499) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 489)) {
            
            p.teleport(plateform);
            stuffArcher(p);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Archer", ChatColor.RED + "Ca va gicler !", 20, 50, 20);
        }
        else if((p.getLocation().getBlockX() == 509) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 491)) {
            
            p.teleport(plateform);
            stuffBourrin(p);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Bourrin", ChatColor.RED + "Tape dans le fond chui pas ta mère !", 20, 50, 20);
        }
        else if((p.getLocation().getBlockX() == 511) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 499) || (p.getLocation().getBlockX() == 511) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 500) || (p.getLocation().getBlockX() == 511) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 501)) {
            
            p.teleport(plateform);
            stuffAssassin(p);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Assassin", ChatColor.RED + "Prendre par derrière ça fait mal", 20, 50, 20);
        }
    }
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        
        if(action == Action.PHYSICAL) {
            if((e.getClickedBlock().getType() == Material.WOOD_PLATE 
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
                    && e.getClickedBlock().getZ() == -7)) { //Stuff Popo

                p.teleport(plateform);

                /* ITEM */
                stuffPopo(p);
                /* **** */

                sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Potions", ChatColor.RED + "J'ai glissé sur un truc gluant", 20, 50, 20);
            }
        }
        else if(action == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock().getType() == Material.LEVER //Départ
                && e.getClickedBlock().getX() == 27 
                && e.getClickedBlock().getY() == 101 
                && e.getClickedBlock().getZ() == -55-1) {

                /*Location start = onPlace(p);
                p.teleport(start);*/
                w.createWorld();
                for(Player pls : Bukkit.getOnlinePlayers()) {
                    sendTitle(pls, ChatColor.GREEN + "Map "+ ChatColor.BLUE + "Cité", "", 20, 50, 20);
                    pls.teleport(onSpawnAlea(pls));
                }

                //p.getWorld().setTime(3000);

                //Countdown();
                //p.setNoDamageTicks(25*20);
            }
        }
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
        else if(e.getMessage().equalsIgnoreCase("/skybool")) {
            w.createWorld();
            p.teleport(new Location(Bukkit.getWorld("SkyBool"), -110, 101, 297));//.getServer()
        }
        else if(e.getMessage().equalsIgnoreCase("/hub")) {
            p.teleport(spawn_start);//.getServer()
        }
        else if(e.getMessage().equalsIgnoreCase("/pvp")) {
            p.teleport(choice_class);//.getServer()
        }
        else if(e.getMessage().equalsIgnoreCase("/joueurs")) {
            for(int i=0; i<players_sky.length; i++) {
                p.sendMessage("Joueur : "+players_sky[i][0]+" nb : "+players_sky[i][1]);
            }
        }
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(kill_total == player_total-1) {
            sendTitle(e.getEntity().getPlayer(), ChatColor.GOLD + "Winner", ChatColor.RED + "Tu leur a mis cher !", 20, 50, 20);
            e.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);
            
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                @Override
                public void run() {
                    player_total=0;
                    e.getEntity().getPlayer().setGameMode(GameMode.SURVIVAL);
                    e.getEntity().getPlayer().teleport(new Location(Bukkit.getWorld("World"), 21, 101, -55));
                }
            }, 200);
        }
        for(int i=0; i<players_sky.length; i++) {
            if(players_sky[i][0].equals(e.getEntity().getName())) {
                players_sky[i][1]="0";
            } 
        }
        kill_total++;
        setScoreboard(e.getEntity().getPlayer());
    }
    
    public void setScoreboard(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("Liste", "Joueur");
        objective.setDisplayName("Liste Joueurs");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        for(Player pls : Bukkit.getOnlinePlayers()) {
            if(pls == p) {
                Score score = objective.getScore(ChatColor.RED+pls.getName());
                score.setScore(0);
            }
            else {
                Score score = objective.getScore(ChatColor.GREEN+pls.getName());
                score.setScore(1);
            }
            pls.setScoreboard(board);
        }
    }
    
    public void hologramme(Player p) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        
        sb.append("PVP");
        sb1.append("SkyWars");
        String name_pvp = sb.toString();
        String name_skw = sb1.toString();
        
        ArmorStand pvp = Bukkit.getWorld(p.getWorld().getName()).spawn(new Location(Bukkit.getWorld("World"), -10.5, 102, 11.5), ArmorStand.class);
        ArmorStand skw = Bukkit.getWorld(p.getWorld().getName()).spawn(new Location(Bukkit.getWorld("World"), -11.5, 102, -11.5), ArmorStand.class);
        pvp.setVisible(false);
        pvp.setCustomName(name_pvp);
        pvp.setCustomNameVisible(true);
        
        skw.setVisible(false);
        skw.setCustomName(name_skw);
        skw.setCustomNameVisible(true);
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
        
        p.removePotionEffect(PotionEffectType.ABSORPTION);
        p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.removePotionEffect(PotionEffectType.SPEED);
        p.removePotionEffect(PotionEffectType.JUMP);
        
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 18000, 0));
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
        
        p.removePotionEffect(PotionEffectType.ABSORPTION);
        p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.removePotionEffect(PotionEffectType.SPEED);
        p.removePotionEffect(PotionEffectType.JUMP);
        
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 18000, 0));
        p.updateInventory();//Mettre à jour l'inventaire
    }

    public void stuffTank(Player p) {
        ItemStack daxe = new ItemStack(Material.WOOD_AXE);
        daxe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        
        ItemStack dhelmet = new ItemStack(Material.DIAMOND_HELMET);
        dhelmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        dhelmet.getItemMeta().spigot().setUnbreakable(true);
        
        ItemStack dchestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        dchestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        dchestplate.getItemMeta().spigot().setUnbreakable(true);
        
        ItemStack dleggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        dleggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        dleggings.getItemMeta().spigot().setUnbreakable(true);
        
        ItemStack dboots = new ItemStack(Material.DIAMOND_BOOTS);
        dboots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        dboots.getItemMeta().spigot().setUnbreakable(true);
        
        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { daxe });//Ajouter l'épée dans l'inventaire
        p.getInventory().setArmorContents(new ItemStack[] { dboots, dleggings, dchestplate, dhelmet });//Enfilé l'équipement sur le joueur
        
        p.removePotionEffect(PotionEffectType.ABSORPTION);
        p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.removePotionEffect(PotionEffectType.SPEED);
        p.removePotionEffect(PotionEffectType.JUMP);
        
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 18000, 0));
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
        
        p.removePotionEffect(PotionEffectType.ABSORPTION);
        p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.removePotionEffect(PotionEffectType.SPEED);
        p.removePotionEffect(PotionEffectType.JUMP);
        
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 18000, 0));
        p.updateInventory();//Mettre à jour l'inventaire
    }

    public void stuffAssassin(Player p) {
        ItemStack armor = new ItemStack(Material.AIR);
        
        ItemStack spade = new ItemStack(Material.DIAMOND_SPADE);
        spade.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
        spade.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        spade.getItemMeta().spigot().setUnbreakable(true);

        ItemStack briquet = new ItemStack(Material.FLINT_AND_STEEL);
        briquet.getItemMeta().spigot().setUnbreakable(true);

        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { spade, briquet });//Ajouter l'épée dans l'inventaire
        p.getInventory().setArmorContents(new ItemStack[] { armor, armor, armor, armor });//Enfilé l'équipement sur le joueur
        
        p.removePotionEffect(PotionEffectType.ABSORPTION);
        p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.removePotionEffect(PotionEffectType.SPEED);
        p.removePotionEffect(PotionEffectType.JUMP);
        
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 18000, 0));
        p.updateInventory();//Mettre à jour l'inventaire
    }
    
    public void stuffPopo(Player p) {
        ItemStack armor = new ItemStack(Material.AIR);
        ItemStack poison = new ItemStack(Material.POTION, 1, (short) 16452);
        ItemStack damage = new ItemStack(Material.POTION, 1, (short) 8196);
                
        p.getInventory().clear();//Vider l'inventaire
        p.getInventory().addItem(new ItemStack[] { poison, });//Ajouter l'épée dans l'inventaire
        p.getInventory().setArmorContents(new ItemStack[] { armor, armor, armor, armor });//Enfilé l'équipement sur le joueur
        
        p.removePotionEffect(PotionEffectType.ABSORPTION);
        p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.removePotionEffect(PotionEffectType.SPEED);
        p.removePotionEffect(PotionEffectType.JUMP);
        
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 18000, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 18000, 0));
        p.updateInventory();//Mettre à jour l'inventaire
    }
    
    public void starting() {
        Location first = new Location(Bukkit.getWorld("world"), 80.5, 76, 299.5);
        first.getBlock().breakNaturally();
    }
    
    public Location onSpawnAlea(Player p) {
        
        /*al.add(new Location(Bukkit.getWorld("World"), 1976, 70, 2038));
        al.add(new Location(Bukkit.getWorld("World"), 1993, 68, 2022));
        al.add(new Location(Bukkit.getWorld("World"), 1997, 60, 1973));
        al.add(new Location(Bukkit.getWorld("World"), 2010, 63, 2009));
        al.add(new Location(Bukkit.getWorld("World"), 2035, 71, 1981));
        al.add(new Location(Bukkit.getWorld("World"), 2020, 65, 1990));
        al.add(new Location(Bukkit.getWorld("World"), 2000, 69, 1965));
        al.add(new Location(Bukkit.getWorld("World"), 2030, 75, 2002));*/
        
        /**** SKYWARS ****/
        al.add(new Location(Bukkit.getWorld("SkyBool"), -165.5, 104, 294.5));
        al.add(new Location(Bukkit.getWorld("SkyBool"), -151.5, 105, 329.5));
        al.add(new Location(Bukkit.getWorld("SkyBool"), -115.5, 104, 344.5));
        al.add(new Location(Bukkit.getWorld("SkyBool"), -79.5, 104, 330.5));
        al.add(new Location(Bukkit.getWorld("SkyBool"), -65.5, 104, 294.5));
        al.add(new Location(Bukkit.getWorld("SkyBool"), -79.5, 104, 258.5));
        al.add(new Location(Bukkit.getWorld("SkyBool"), -115.5, 104, 244.5));
        al.add(new Location(Bukkit.getWorld("SkyBool"), -150.5, 104, 258.5));
        /* **** */
        
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