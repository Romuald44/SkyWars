/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars.controller;

import java.util.ArrayList;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import skywars.SkyWars;

/**
 *
 * @author Romuald
 */
public class GameController {
    
    private static SkyWars plugin;
    private static WorldController wc;
    
    private int nbPlayers = 0;
    private ArrayList<Player> players = new ArrayList<Player>();
    private String[][] players_sky = new String[8][2];
    private ArrayList tab_nb_temp = new ArrayList();
    private ArrayList<Location> loc_start = new ArrayList<Location>();
    private boolean startgame = false;
    private String name_winner;
    private World instance_map;
    private int winner=0;
    int task;
    int seconds = 21;
    
    public GameController() {
        plugin = SkyWars.get();
        wc = SkyWars.get().getWC();
        
        loc_start.add(new Location(Bukkit.getWorld("SkyBool1"), -165.5, 104, 294.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool1"), -151.5, 105, 329.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool1"), -115.5, 104, 344.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool1"), -79.5, 104, 330.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool1"), -65.5, 104, 294.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool1"), -79.5, 104, 258.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool1"), -115.5, 104, 244.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool1"), -151.5, 104, 258.5));
    }
    
    public void resetPlayers() {
        for(int i=0; i<nbPlayers; i++) {
            players_sky[i][0]=null;
            players_sky[i][1]=null;
        }
    }
    
    public void showPlayers() {
        for(int i=0; i<8; i++) {
            Bukkit.broadcastMessage("Joueurs : "+players_sky[i][0]+" nb : "+players_sky[i][1]);
        }
    }
    
    public void addPlayers(Player p) {
        if(!startgame) {
            players.add(p);
            this.nbPlayers++;
            
            p.setGameMode(GameMode.SURVIVAL);//Mettre le joueur en survie
            p.getInventory().clear();//Vider l'inventaire
            p.getInventory().setArmorContents(null);//A poil !
            //setScore(p, 1);
            boolean decl = true;
            for(int i=0; i<8; i++) {
                if(players_sky[i][0] == null && decl) {
                    this.players_sky[i][0] = p.getName();
                    this.players_sky[i][1] = "1";
                    decl=false;
                }
            }
            //setBoard(p, players, "Liste Joueurs");
            
            p.teleport(onSpawnAlea(p));
            
            for(Player pls : players) {
                pls.sendMessage(ChatColor.GOLD+p.getName()+ChatColor.AQUA+" à rejoint la partie");
            }
            
            if(nbPlayers >= 1) {
                shutCount();
                Countdown();
            }
            else {
                shutCount();
            }
        }
    }
    
    public void removePlayers(Player p) {
        players.remove(p);
        this.nbPlayers--;
        
        for(int i=0; i<8; i++) {
            if(players_sky[i][0] == p.getName()) {
                this.players_sky[i][0] = null;
                this.players_sky[i][1] = null;
            }
        }
        for(Player pls : players) {
            pls.sendMessage(ChatColor.GOLD+p.getName()+ChatColor.AQUA+" à quitté la partie");
        }
        //setBoard(p, players, "Liste Joueurs");
        
        if(nbPlayers >= 1) {
            shutCount();
            Countdown();
        }
        else {
            shutCount();
        }
    }
    
    public void deathPlayer(Player p) {
        players.remove(p);
        
        for(int i=0; i<8; i++) {
            if(players_sky[i][0].equals(p.getName())) {
                players_sky[i][1] = "0";
            }
        }
        
        if(players.size() == 1) {
            sendTitle(players.get(0), ChatColor.GOLD + "Winner", ChatColor.RED + "Tu leur a mis cher !", 20, 100, 20);

            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                @Override
                public void run() {
                    for(Player pls : Bukkit.getOnlinePlayers()) {
                        pls.setGameMode(GameMode.SURVIVAL);
                        pls.setHealth(20);
                        pls.setFoodLevel(20);
                        pls.teleport(new Location(Bukkit.getWorld("World"), -498.5, 103, -501.5));
                    }
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
    
    public boolean getStart() {
        return startgame;
    }
    
    public String[][] Players_Sky() {
        return players_sky;
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
    
    public void Countdown() {
        seconds = 21;
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
            
            @Override
            public void run() {
                seconds--;
                
                if(seconds == 0) {
                    Bukkit.getScheduler().cancelTask(task);
                    for(Player pls : players) {
                        pls.playSound(pls.getLocation(), Sound.EXPLODE, 10, 360);//Beep de départ
                        sendTitle(pls, ChatColor.RED + "GANG BANG !", "", 10, 20, 10);
                        startgame = true;
                    }
                    starting();
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
