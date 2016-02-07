/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars.listener;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skywars.SkyWars;
import skywars.controller.GameController;

/**
 *
 * @author Romuald
 */
public class PlayerListener implements Listener {
       
    //private String[][] players_sky = new String[8][2];
    private static SkyWars plugin;
    private GameController gc;
    
    public PlayerListener() {
        plugin = SkyWars.get();
        gc = SkyWars.get().getGC();
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.getWorld().getName().equals("SkyBool1")) {
            ItemStack door = new ItemStack(Material.IRON_DOOR);
            ItemMeta meta = (ItemMeta) door.getItemMeta();
            meta.setDisplayName("Return to spawn");
            door.setItemMeta(meta);
            p.getInventory().setItem(8, door);
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(p.getWorld().getName().equals("SkyBool1")) {
            gc.removePlayers(p);
        }
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        
        if(p.getWorld().getName().equals("SkyBool1")) {
            if((p.getLocation().getBlockX() == -116 && p.getLocation().getBlockY() == 100 && p.getLocation().getBlockZ() == 285) ||
               (p.getLocation().getBlockX() == -107 && p.getLocation().getBlockY() == 100 && p.getLocation().getBlockZ() == 294) ||
               (p.getLocation().getBlockX() == -116 && p.getLocation().getBlockY() == 100 && p.getLocation().getBlockZ() == 303) ||
               (p.getLocation().getBlockX() == -125 && p.getLocation().getBlockY() == 100 && p.getLocation().getBlockZ() == 294)) {
                Location lava1 = new Location(Bukkit.getWorld("SkyBool1"), -118, 110, 294);
                Location lava2 = new Location(Bukkit.getWorld("SkyBool1"), -114, 110, 294);
                Location lava3 = new Location(Bukkit.getWorld("SkyBool1"), -116, 110, 292);
                Location lava4 = new Location(Bukkit.getWorld("SkyBool1"), -116, 110, 296);
                lava1.getBlock().setType(Material.LAVA);
                lava2.getBlock().setType(Material.LAVA);
                lava3.getBlock().setType(Material.LAVA);
                lava4.getBlock().setType(Material.LAVA);
            }
        }
    }
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        
        if(p.getWorld().getName().equals("world")) {
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.SIGN) {
                    Sign s = (Sign) e.getClickedBlock().getState();
                    if(s.getLine(1).equalsIgnoreCase(ChatColor.RED+"SkyBool")) {
                        onSignJoinable(s, p);
                    }
                }
            }
        }
    }
    
    public void onSignJoinable(Sign s, Player p) {
        if(gc.getStateGame() == false) {
            s.setLine(2, ChatColor.RED+"Disponible");
            gc.addPlayers(p);
        }
        else {
            s.setLine(2, ChatColor.RED+"Indisponible");
        }
        s.setLine(3, ChatColor.BLUE+"§l"+gc.getNbPlayers()+" / 8");
        s.update();
        /*s.setLine(0, ChatColor.BLUE+"§lSkyWars");
            s.setLine(1, ChatColor.RED+"SkyBool");//instance_skybool.getPlayers()
            s.setLine(2, ChatColor.RED+"Disponible");
            s.setLine(3, ChatColor.BLUE+"§l0 / 8");
            s.update();*/
    }
    
    /*@EventHandler
    public void PlayerDamageReceive(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player damaged = (Player) e.getEntity();
             
            if(damaged.getWorld().getName().equals("SkyBool1")) {
                if((damaged.getHealth()-e.getDamage()) <= 0) {
                    
                    //Killed
                    e.setCancelled(true);
                    damaged.setGameMode(GameMode.SPECTATOR);
                    damaged.teleport(gc.locAlea());
                    players_sky = gc.Players_Sky();
                    
                    Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                            @Override
                            public void run() {
                                if(gc.getWinner()==1) {
                                    sendTitle(Bukkit.getPlayer(gc.getNameWinner()), ChatColor.GOLD + "Winner", ChatColor.RED + "Tu leur a mis cher !", 20, 100, 20);

                                    Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                                        @Override
                                        public void run() {
                                            for(Player pls : Bukkit.getOnlinePlayers()) {
                                                pls.setGameMode(GameMode.SURVIVAL);
                                                pls.setHealth(20);
                                                pls.setFoodLevel(20);
                                                pls.teleport(choice_skywars);
                                            }
                                            Bukkit.dispatchCommand(damaged, "skybool reload");
                                            gc.resetPlayers();
                                        }
                                    }, 150);
                                }
                            }
                        }, 40);
                    
                    for(int i=0; i<8; i++) {
                        if(players_sky[i][0].equals(damaged.getPlayer().getName())) {
                            players_sky[i][1] = "0";
                        }
                        if(players_sky[i][1] == "1") {
                            gc.setWinner();
                            gc.setNameWinner(players_sky[i][0]);
                        }
                    }
                }
            }
        }
    }
    */
    
    @EventHandler
    public void autoRespawn(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.SPECTATOR);
        event.setRespawnLocation(gc.locAlea());
        
        /*Bukkit.getScheduler().runTaskLater(SkyWars.get(), new Runnable() {
            @Override
            public void run() {
                if(gc.getWinner()==1) {
                    sendTitle(Bukkit.getPlayer(gc.getNameWinner()), ChatColor.GOLD + "Winner", ChatColor.RED + "Tu leur a mis cher !", 20, 100, 20);

                    Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                        @Override
                        public void run() {
                            for(Player pls : Bukkit.getOnlinePlayers()) {
                                celebrate();
                                pls.setGameMode(GameMode.SURVIVAL);
                                pls.setHealth(20);
                                pls.setFoodLevel(20);
                                pls.teleport(choice_skywars);
                            }
                            Bukkit.dispatchCommand(player, "skybool reload");
                            gc.resetPlayers();
                        }
                    }, 150);
                }
            }
        }, 40);*/
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = event.getEntity().getPlayer();
            gc.deathPlayer(player);
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player p = event.getPlayer();
        if(!gc.getStart() && p.getWorld().getName().equals("SkyBool1")) {
            event.setCancelled(true);
        }
    }
    
    public void GameOver(Player p) {
        p.sendMessage("ah que coucou");
    }
    
    public void hologramme(Player p) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        
        sb.append("PVP");
        sb1.append("SkyWars");
        String name_pvp = sb.toString();
        String name_skw = sb1.toString();
        
        ArmorStand pvp = Bukkit.getWorld(p.getWorld().getName()).spawn(new Location(Bukkit.getWorld("World"), -11.5, 102, 12.5), ArmorStand.class);
        ArmorStand skw = Bukkit.getWorld(p.getWorld().getName()).spawn(new Location(Bukkit.getWorld("World"), -11.5, 102, -11.5), ArmorStand.class);
        pvp.setVisible(false);
        pvp.setCustomName(name_pvp);
        pvp.setCustomNameVisible(true);
        
        skw.setVisible(false);
        skw.setCustomName(name_skw);
        skw.setCustomNameVisible(true);
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