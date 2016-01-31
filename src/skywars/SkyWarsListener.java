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
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 *
 * @author Romuald
 */
public class SkyWarsListener implements Listener {
   
    Location spawn_start = new Location(Bukkit.getWorld("World"), 0.5, 101, 0.5);
    Location choice_class = new Location(Bukkit.getWorld("World"), 500.5, 101, 500.5);
    Location choice_skywars = new Location(Bukkit.getWorld("World"), -498.5, 103, -501.5);
    Location plateform = new Location(Bukkit.getWorld("World"), 21, 101, -55);
    
    InstanceMap instance_skybool;
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(ChatColor.RED+"SkyWars");
        p.sendMessage("Développement par : "+ChatColor.GREEN+"EpicSaxGuy, MrTwixo, Guitou388");
    }
    
    @EventHandler
    public void PlayerDamageReceive(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player damaged = (Player) e.getEntity();
             
            if(damaged.getWorld().getName() == "SkyBool") {
                if((damaged.getHealth()-e.getDamage()) <= 0) {
                    
                    //Killed
                    e.setCancelled(true);
                    damaged.setGameMode(GameMode.SPECTATOR);
                    damaged.teleport(instance_skybool.loc_start.get((int)(8*Math.random())));
                    
                    Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                            @Override
                            public void run() {
                                if(instance_skybool.getWinner()==1) {
                                    sendTitle(Bukkit.getPlayer(instance_skybool.getNameWinner()), ChatColor.GOLD + "Winner", ChatColor.RED + "Tu leur a mis cher !", 20, 100, 20);
                                    //SkyWarsListener back = new SkyWarsListener();

                                    Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                                        @Override
                                        public void run() {
                                            for(Player pls : Bukkit.getOnlinePlayers()) {
                                                pls.setGameMode(GameMode.SURVIVAL);
                                                pls.setHealth(20);
                                                pls.setFoodLevel(20);
                                                pls.teleport(choice_skywars);
                                                instance_skybool.resetPlayers();
                                            }
                                            //back.instance_skybool = new InstanceMap(Bukkit.getWorld("SkyBool"));
                                        }
                                    }, 150);
                                }
                            }
                        }, 40);
                    
                    for(int i=0; i<8; i++) {
                        if(instance_skybool.players_sky[i][0].equals(damaged.getPlayer().getName())) {
                            instance_skybool.players_sky[i][1] = "0";
                        }
                        if(instance_skybool.players_sky[i][1] == "1") {
                            instance_skybool.setWinner();
                            instance_skybool.setNameWinner(instance_skybool.players_sky[i][0]);
                        }
                    }
                    
                    //GameOver(damaged);
                    
                    /*if(instance_skybool.getWinner()==1) {
                        sendTitle(Bukkit.getPlayer(instance_skybool.getNameWinner()), ChatColor.GOLD + "Winner", ChatColor.RED + "Tu leur a mis cher !", 20, 50, 20);
                        //SkyWarsListener back = new SkyWarsListener();

                        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("SkyWars"), new Runnable() {
                            @Override
                            public void run() {
                                for(Player pls : Bukkit.getOnlinePlayers()) {
                                    pls.setGameMode(GameMode.SURVIVAL);
                                    pls.teleport(choice_skywars);
                                    instance_skybool.resetPlayers();
                                }
                                //back.instance_skybool = new InstanceMap(Bukkit.getWorld("SkyBool"));
                            }
                        }, 200);
                    }*/
                    
                }
            }
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