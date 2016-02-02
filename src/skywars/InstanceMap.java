/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 *
 * @author Romuald
 */
public class InstanceMap implements Listener {
    
    ArrayList<Location> loc_start = new ArrayList<Location>();
    ArrayList tab_nb_temp = new ArrayList();
    String[][] players_sky = new String[8][2];
    
    private Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
    private Scoreboard sbBuffer = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective = sb.registerNewObjective("players", "dummy");
    private Objective objectiveBuffer = sbBuffer.registerNewObjective("playerBuffer", "dummy");
  
    public HashMap<Player, Integer> scoreMap = new HashMap<>();
    
    ArrayList<Player> players = new ArrayList<Player>();
   
    int perso_ID;
    int temp;
    public int winner=0;
    int kill_total=0;
    int player_total=1;
    int task;
    int seconds = 21;
    public String name_winner;
    private World instance_map;
    private int instance_players=0;
    
    public InstanceMap(World imap) {
        instance_map = imap;
        
        imap.setAutoSave(false);
        imap.setThundering(false);
        imap.setStorm(false);
        imap.setTicksPerAnimalSpawns(1);
        imap.setTicksPerMonsterSpawns(1);
        imap.setGameRuleValue("doMobSpawning", "false");
        imap.setGameRuleValue("mobGriefing", "false");
        imap.setGameRuleValue("doFireTick", "false");
        
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -165.5, 104, 294.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -151.5, 105, 329.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -115.5, 104, 344.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -79.5, 104, 330.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -65.5, 104, 294.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -79.5, 104, 258.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -115.5, 104, 244.5));
        loc_start.add(new Location(Bukkit.getWorld("SkyBool"), -151.5, 104, 258.5));
        //chestPopulate();
    }
    
    public String getNameWorld() {
        return instance_map.getName();
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
    
    public void resetPlayers() {
        for(int i=0; i<8; i++) {
            players_sky[i][0]=null;
            players_sky[i][1]=null;
        }
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
        p.teleport(onSpawnAlea(p));
        players.add(p);
        setScore(p, 1);
        boolean decl = true;
        for(int i=0; i<8; i++) {
            if(players_sky[i][0] == null && decl) {
                this.players_sky[i][0] = p.getName();
                this.players_sky[i][1] = "1";
                decl=false;
            }
        }
        setBoard(p, players, "Liste Joueurs");
        this.instance_players++;
    }
    
    public void removePlayers(Player p) {
        players.remove(p);
        for(int i=0; i<8; i++) {
            if(players_sky[i][0] == p.getName()) {
                this.players_sky[i][0] = null;
                this.players_sky[i][1] = null;
            }
        }
        setBoard(p, players, "Liste Joueurs");
        this.instance_players--;
    }
    
    public void chestPopulate() {
        Location loc1 = new Location(Bukkit.getWorld("SkyBool"), -74, 101, 329);
        //Location loc1_1 = new Location(Bukkit.getWorld("SkyBool"), -80, 101, 324);
        
        loc1.getBlock().setType(Material.CHEST);
	Chest chest = (Chest) loc1.getBlock().getState();
	Inventory inv = chest.getInventory();
        inv.addItem(aleaStuff());
    }
    
    public Material[] tab_stuff() {
        Material[] alea = new Material[22];
        alea[0] =  Material.LEATHER_HELMET;
        alea[1] =  Material.LEATHER_CHESTPLATE;
        alea[2] =  Material.LEATHER_LEGGINGS;
        alea[3] =  Material.LEATHER_BOOTS;
        
        alea[4] =  Material.GOLD_HELMET;
        alea[5] =  Material.GOLD_CHESTPLATE;
        alea[6] =  Material.GOLD_LEGGINGS;
        alea[7] =  Material.GOLD_BOOTS;
        
        alea[8] =  Material.CHAINMAIL_HELMET;
        alea[9] =  Material.CHAINMAIL_CHESTPLATE;
        alea[10] =  Material.CHAINMAIL_LEGGINGS;
        alea[11] =  Material.CHAINMAIL_BOOTS;
        
        alea[12] =  Material.IRON_HELMET;
        alea[13] =  Material.IRON_CHESTPLATE;
        alea[14] =  Material.IRON_LEGGINGS;
        alea[15] =  Material.IRON_BOOTS;
        
        alea[16] = Material.DIAMOND_HELMET;
        alea[17] = Material.DIAMOND_CHESTPLATE;
        alea[18] = Material.DIAMOND_LEGGINGS;
        alea[19] = Material.DIAMOND_BOOTS;
        
        alea[20] = Material.STONE_SWORD;
        alea[21] = Material.IRON_SWORD;
        
        return alea;
    }
    
    public ItemStack[] aleaStuff() {
        Material[] stuff = tab_stuff();
        
        ItemStack[] aleastuff = new ItemStack[3];
        
        for(int i=0; i<4; i++) {
            aleastuff[i] = new ItemStack(stuff[(int)(21*Math.random())]);
            Bukkit.getPlayer("EpicSaxGuy").sendMessage("Stuff : "+aleastuff[i].getType().name());
        }
        
        return aleastuff;
    }
    
    public ItemStack[] tab_magic() {
        ItemStack[] alea = new ItemStack[26];
        alea[0] = new ItemStack(Material.GOLDEN_APPLE);
        alea[1] = new ItemStack(Material.ARROW);
        
        return alea;
    }
    
    public ItemStack[] tab_block() {
        ItemStack[] alea = new ItemStack[26];
        alea[0] = new ItemStack(Material.COBBLESTONE);
        alea[1] = new ItemStack(Material.WOOD);
        alea[2] = new ItemStack(Material.ICE);
        alea[3] = new ItemStack(Material.GLASS);
        alea[4] = new ItemStack(Material.FISHING_ROD);
        
        return alea;
    }
    
    public void setBoard(Player p, List<Player> playerList, String displayName){
      objective.setDisplaySlot(DisplaySlot.SIDEBAR);
      objective.setDisplayName(displayName);
      p.setScoreboard(sb);
      for(Player player : playerList){
        player.setScoreboard(sb);
      }
    }
  
    public void setScore(Player p, int score){
      Score s = objective.getScore(p.getName());
      Score s2 = objectiveBuffer.getScore(p.getName());
      int oldScore = scoreMap.containsKey(p) ? scoreMap.get(p) : 0;
      scoreMap.put(p, oldScore + score);

      s2.setScore(scoreMap.get(p));
      removeLowestScore(objectiveBuffer, 1);
      swap(objective, objectiveBuffer);

      s.setScore(scoreMap.get(p));
      removeLowestScore(objective, 1);
      swap(objectiveBuffer, objective);
    }
  
    public void swap(Objective ob1, Objective ob2){
      ob2.setDisplayName(ob1.getDisplayName());
      ob2.setDisplaySlot(ob1.getDisplaySlot());
    }
  
    public void removeLowestScore(Objective objective, int maxScoreboardEntries){
        if(objective.getScoreboard().getEntries().size() <= maxScoreboardEntries)
          return;

        int min = 0;
        Set<String> player = objective.getScoreboard().getEntries();
        List<Integer> scoreValue = new ArrayList<>();
        List<String> scorePlayer = new ArrayList<>();
        for(String p : player){
          scorePlayer.add(objective.getScore(p).getEntry());
          scoreValue.add(objective.getScore(p).getScore());
        }
        try{
          min = scoreValue.indexOf(Collections.min(scoreValue));
        } catch (NoSuchElementException nsee){
          System.out.println("No such element!");
        }

        objective.getScoreboard().resetScores(scorePlayer.get(min));
    }
    
    public Location locAlea() {
        return loc_start.get((int)(8*Math.random()));
    }
    
    public Location onSpawnAlea(Player p) {
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
