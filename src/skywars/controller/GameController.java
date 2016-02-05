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
    private int nbPlayers = 0;
    private int maxPlayers = 8;
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
            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().clear();
            //setScore(p, 1);
            boolean decl = true;
            for(int i=0; i<maxPlayers; i++) {
                if(players_sky[i][0] == null && decl) {
                    this.players_sky[i][0] = p.getName();
                    this.players_sky[i][1] = "1";
                    decl=false;
                }
            }
            //setBoard(p, players, "Liste Joueurs");
            this.maxPlayers--;
            this.nbPlayers++;
            p.teleport(onSpawnAlea(p));
            if(nbPlayers >= 3) {
                Countdown();
            }
            else {
                shutCount();
            }
        }
    }
    
    public void removePlayers(Player p) {
        players.remove(p);
        for(int i=0; i<8; i++) {
            if(players_sky[i][0] == p.getName()) {
                this.players_sky[i][0] = null;
                this.players_sky[i][1] = null;
            }
        }
        //setBoard(p, players, "Liste Joueurs");
        this.maxPlayers++;
        this.nbPlayers--;
    }
    
    public int getWinner() {
        return winner;
    }
    
    public String getNameWinner() {
        return name_winner;
    }
    
    public void setNameWinner(String n_win) {
        this.name_winner = n_win;
    }
    
    public void setWinner() {
        this.winner=0;
        this.winner++;
    }
    
    public boolean getStart() {
        return startgame;
    }
    
    public String[][] Players_Sky() {
        return players_sky;
    }
    
    public int getPlayers() {
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
                    for(Player pls : Bukkit.getOnlinePlayers()) {
                        sendTitle(pls, ChatColor.RED + "GANG BANG !", "", 10, 20, 10);
                        startgame = true;
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
