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
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import skywars.ScoreBoard;
import skywars.SkyWars;

/**
 *
 * @author Romuald
 */
public class GameController {
    
    private static SkyWars plugin;
    private WorldController wc;
    //private ScoreBoard sb;
    
    private int nbPlayers = 0;
    private List<Player> players = new ArrayList<Player>();
    private List<Player> deathplayers = new ArrayList<Player>();
    private ArrayList tab_nb_temp = new ArrayList();
    private List<Location> loc_start = Lists.newArrayList();
    
    private boolean startgame = false;
    private String name_winner;
    private int winner=0;
    private int task;
    private int seconds = 21;
    
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard board = manager.getNewScoreboard();
    private Objective o = board.registerNewObjective("Nom", "morts");
    
    public GameController() {
        plugin = SkyWars.get();
        wc = SkyWars.get().getWC();
        //sb = SkyWars.get().getSB();
        
        loadSpawn();
    }
    
    public final void loadSpawn() {
        String map;
        loc_start.clear();
        File spawnFile = new File(SkyWars.get().getDataFolder(), "spawn_SkyBool1.yml");

        if (!spawnFile.exists()) {
        	SkyWars.get().saveResource("spawn_SkyBool1.yml", false);
        }

        if (spawnFile.exists()) {
            FileConfiguration storage = YamlConfiguration.loadConfiguration(spawnFile);
            
            if (storage.contains("spawn")) {
                if(storage.contains("spawn.map")) {
                    map = storage.getString("spawn.map");
                    
                    if(map.equalsIgnoreCase("SkyBool1")) {
                        for (String chest : storage.getStringList("spawn.position")) {
                            List<String> spawn = new LinkedList<>(Arrays.asList(chest.split(" ")));

                            loc_start.add(new Location(Bukkit.getWorld(map), Double.parseDouble(spawn.get(0)), Double.parseDouble(spawn.get(1)), Double.parseDouble(spawn.get(2))));
                        }
                    }
                }
            }
        }
    }
    
    public void showplayers() {
        for(Player test : players) {
            Bukkit.getPlayer("EpicSaxGuy").sendMessage("Players : "+test.getName());
        }
    }
    
    public void addPlayers(Player p) {
        if(!startgame) {
            players.add(p);
            ScoreBoard();
            this.nbPlayers++;
            
            p.setGameMode(GameMode.ADVENTURE);//Mettre le joueur en survie
            p.getInventory().clear();//Vider l'inventaire
            p.getInventory().setArmorContents(null);//A poil !
            
            p.teleport(onSpawnAlea(p));
            
            for(Player pls : players) {
                pls.sendMessage(ChatColor.GOLD+p.getName()+ChatColor.AQUA+" à rejoint la partie");
            }
            
            if(nbPlayers >= 2) {//&& seconds >= 20
                //shutCount();
                Countdown();
            }
            else {
                shutCount();
            }
        }
    }
    
    public void removePlayers(Player p) {
        players.remove(p);//Suppression automatique du joueurs dans la list (pas besoin de le supprimer)
        ScoreBoard();
        this.nbPlayers--;
        
        for(Player pls : players) {
            pls.sendMessage(ChatColor.GOLD+p.getName()+ChatColor.AQUA+" à quitté la partie");
        }
        
        if(nbPlayers <= 2) {
            shutCount();
        }
    }
    
    public void deathPlayer(Player p, Player killer) {
        ScoreZero(p);
        players.remove(p);
        
        for(Player pls : players) {
            if(killer == null) {
                pls.sendMessage(ChatColor.GOLD+p.getName()+ChatColor.AQUA+" c'est fait enculé par le vide");
            }
            else {
                pls.sendMessage(ChatColor.GOLD+p.getName()+ChatColor.AQUA+" c'est fait enculé par "+ChatColor.GOLD+killer.getName());
            }
        }
        showplayers();
        
        if(players.size() == 1) {
            players.get(0).setHealth(20);
            sendTitle(players.get(0), ChatColor.GOLD + "Winner", ChatColor.RED + "Tu leur a mis cher !", 20, 100, 20);

            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                @Override
                public void run() {
                    for(Player pls : players) {
                        pls.setGameMode(GameMode.SURVIVAL);
                        pls.setHealth(20);
                        pls.setFoodLevel(20);
                        pls.teleport(new Location(Bukkit.getWorld("World"), -498.5, 103, -501.5));
                    }
                    board.clearSlot(DisplaySlot.SIDEBAR);
                    players.clear();
                    SkyWars.get().reset();
                }
            }, 150);
        }
    }
    
    public int getWinner() {
        return winner;
    }
    
    public String getNameWinner() {
        return name_winner;
    }
    
    public boolean getStateGame() {
        return startgame;
    }
    
    public void setNameWinner(String n_win) {
        this.name_winner = n_win;
    }
    
    public void setWinner(int nb) {
        this.winner = nb;
    }
    
    public int getNbPlayers() {
        return nbPlayers;
    }
    
    public Location locAlea() {
        return loc_start.get((int)(8*Math.random()));
    }
    
    public Location onSpawnAlea(Player p) {
        if(nbPlayers < 8) {
            int temp = (int)(8*Math.random());
            while(tab_nb_temp.contains(temp)) {
                temp = (int)(8*Math.random());
            }
            tab_nb_temp.add(temp);
            return loc_start.get(temp);
        }
        else {
            return null;
        }
    }
    
    public void ScoreBoard()
    {
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        for(Player online : players) {
            Score score = o.getScore(online.getName());
            score.setScore(1);
            
            online.setScoreboard(board);
        }
    }
    
    public void ScoreZero(Player p)
    {
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        for(Player online : players) {
            if(online.getName().equalsIgnoreCase(p.getName())) {
                Score score = o.getScore(online.getName());
                score.setScore(0);
            }
            
            online.setScoreboard(board);
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
                    for(Player pls : players) {
                        pls.setGameMode(GameMode.SURVIVAL);
                        pls.playSound(pls.getLocation(), Sound.EXPLODE, 10, 360);//Beep de départ
                        sendTitle(pls, ChatColor.RED + "GANG BANG !", "", 10, 20, 10);
                        startgame = true;
                    }
                    starting();
                    deathplayers = players;
                }
                else if((seconds == 20)) {
                    for(Player pls : players) {
                        pls.sendMessage("Début de la partie dans "+ChatColor.RED+seconds+" secondes");
                    }
                }
                else if(seconds > 0 && seconds <= 10) {
                    for(Player pls : players) {
                        if(seconds <= 3) {
                            pls.playSound(pls.getLocation(), Sound.CLICK, 10, 360);
                        }
                        sendTitle(pls, ChatColor.RED + Integer.toString(seconds), "", 10, 20, 10);
                    }
                }
            }
        }, 20, 20);
    }
    
    public void shutCount() {
        Bukkit.getScheduler().cancelTask(task);
    }
    
    public void starting() {
        Location first = new Location(Bukkit.getWorld("SkyBool1"), -165.5, 103, 294.5);
        Location second = new Location(Bukkit.getWorld("SkyBool1"), -151.5, 104, 329.5);
        Location third = new Location(Bukkit.getWorld("SkyBool1"), -115.5, 103, 344.5);
        Location fourth = new Location(Bukkit.getWorld("SkyBool1"), -79.5, 103, 330.5);
        Location fifth = new Location(Bukkit.getWorld("SkyBool1"), -65.5, 103, 294.5);
        Location sixth = new Location(Bukkit.getWorld("SkyBool1"), -79.5, 103, 258.5);
        Location seve = new Location(Bukkit.getWorld("SkyBool1"), -115.5, 103, 244.5);
        Location eight = new Location(Bukkit.getWorld("SkyBool1"), -151.5, 103, 258.5);
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
