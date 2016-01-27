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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author Romuald
 */
public class InstanceMap implements Listener {
    
    ArrayList<Location> loc_start = new ArrayList<Location>();
    ArrayList tab_nb_temp = new ArrayList();
    String[][] players_sky = new String[8][2];
    
    int perso_ID;
    int temp;
    int winner;
    int kill_total=0;
    int player_total=1;
    int task;
    int seconds = 21;
    String name_winner;
    private World instance_map;
    private int instance_players;
    
    public InstanceMap(World imap) {
        instance_map = imap;
        instance_map.setAutoSave(false);
    }
    
    public String getName() {
        return instance_map.getName();
    }
    
    public int getPlayers() {
        return instance_players;
    }
    
    public void showPlayers() {
        for(int i=0; i<8; i++) {
            Bukkit.broadcastMessage("Joueurs : "+players_sky[i][0]+" nb : "+players_sky[i][1]);
        }
    }
    
    public void addPlayers(Player p) {
        boolean decl = true;
        for(int i=0; i<8; i++) {
            if(players_sky[i][0] == null && decl) {
                this.players_sky[i][0] = p.getName();
                this.players_sky[i][1] = "1";
                decl=false;
            }
        }
        this.instance_players++;
    }
    
    public void removePlayers(Player p) {
        for(int i=0; i<8; i++) {
            if(players_sky[i][0] == p.getName()) {
                this.players_sky[i][0] = null;
                this.players_sky[i][1] = null;
            }
        }
        this.instance_players--;
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        
        for(int i=0; i<8; i++) {
            if(players_sky[i][0] == e.getEntity().getPlayer().getName()) {
                this.players_sky[i][1] = "0";
            }
            if(players_sky[i][1] == "1") {
                winner++;
                name_winner = players_sky[i][0];
            }
        }
        if(winner==1) {
            sendTitle(Bukkit.getPlayer(name_winner), ChatColor.GOLD + "Winner", ChatColor.RED + "Tu leur a mis cher !", 20, 50, 20);
            SkyWarsListener back = new SkyWarsListener();
            
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                @Override
                public void run() {
                    e.getEntity().getPlayer().setGameMode(GameMode.SURVIVAL);
                    e.getEntity().getPlayer().teleport(new Location(Bukkit.getWorld("World"), -500.5, 101, -500.5));
                    back.instance_skybool = new InstanceMap(Bukkit.getWorld("SkyBool"));
                }
            }, 200);
        }
        else {
            e.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);
        }
        setScoreboard(e.getEntity().getPlayer());
    }
    
    public void populateChest(Player p) {
        Location loc = new Location(Bukkit.getWorld("World"), -74, 101, 329);
        loc.getBlock().setType(Material.CHEST);
	Chest chest = (Chest) loc.getBlock().getState();
	Inventory inv = chest.getInventory();
        inv.addItem(tab_stuff());
    }
    
    public ItemStack[] tab_stuff() {
        ItemStack[] alea = new ItemStack[26];
        alea[0] = new ItemStack(Material.LEATHER_HELMET);
        
        return alea;
    }
    
    public void setScoreboard(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("Liste", "Joueur");
        objective.setDisplayName("Liste Joueurs");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        for(int i=0; i<8; i++) {
            if(players_sky[i][1] == "1") {
                Score score = objective.getScore(ChatColor.GREEN+p.getName());
                score.setScore(1);
            }
            else {
                Score score = objective.getScore(ChatColor.RED+p.getName());
                score.setScore(0);
            }
            p.setScoreboard(board);
        }
    }
    
    public Location onSpawnAlea(Player p) {
        
        /**** SKYWARS ****/
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -165.5, 104, 294.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -151.5, 105, 329.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -115.5, 104, 344.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -79.5, 104, 330.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -65.5, 104, 294.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -79.5, 104, 258.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -115.5, 104, 244.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -151.5, 104, 258.5));
        /* **** */
        
        if(perso_ID < 8) {
            temp = (int)(8*Math.random());
            while(tab_nb_temp.contains(temp)) {
                temp = (int)(8*Math.random());
            }
            perso_ID++;
            tab_nb_temp.add(temp);
            return loc_start.get(temp);
        }
        else {
            return null;
        }
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
    
    public void starting() {
        Location first = new Location(Bukkit.getWorld("SkyBool"), -165.5, 103, 294.5);
        Location second = new Location(Bukkit.getWorld("SkyBool"), -151.5, 104, 329.5);
        Location third = new Location(Bukkit.getWorld("SkyBool"), -115.5, 103, 344.5);
        Location fourth = new Location(Bukkit.getWorld("SkyBool"), -79.5, 103, 330.5);
        Location fifth = new Location(Bukkit.getWorld("SkyBool"), -65.5, 103, 294.5);
        Location sixth = new Location(Bukkit.getWorld("SkyBool"), -79.5, 103, 258.5);
        Location seve = new Location(Bukkit.getWorld("SkyBool"), -115.5, 103, 244.5);
        Location eight = new Location(Bukkit.getWorld("SkyBool"), -151.5, 103, 258.5);
        first.getBlock().breakNaturally();
        second.getBlock().breakNaturally();
        third.getBlock().breakNaturally();
        fourth.getBlock().breakNaturally();
        fifth.getBlock().breakNaturally();
        sixth.getBlock().breakNaturally();
        seve.getBlock().breakNaturally();
        eight.getBlock().breakNaturally();
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
}
