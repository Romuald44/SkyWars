/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import java.io.File;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import skywars.controller.WorldController;


/**
 *
 * @author Romuald
 */
public class Commands implements CommandExecutor {
    
    InstanceMap instance_skybool;
    WorldController wc;
    
    public Commands() {
        wc = SkyWars.getWC();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        
        if(cmd.getName().equalsIgnoreCase("skywars") && sender instanceof Player) {
            if(args[0].equals("enter")) {
                p.sendMessage("Ok pour la reception");
                instance_skybool.addPlayers(p);
                sendTitle(p, ChatColor.GREEN+"Map"+ChatColor.BLUE+" SkyBool", "", 20, 50, 20);
            }
        }
        if(cmd.getName().equalsIgnoreCase("skybool") && sender instanceof Player) {
            if(args[0].equals("reload")) {
                wc.unloadWorld("SkyBool1");
                wc.deleteWorld(new File("SkyBool1"));
                wc.copyWorld(new File("SkyBool"), new File("SkyBool1"));
                wc.loadWorld("SkyBool1");
            }
            else if(args[0].equals("enter")) {
                p.teleport(new Location(Bukkit.getWorld("SkyBool1"), -165.5, 104, 294.5));
                sendTitle(p, ChatColor.GREEN+"Map"+ChatColor.BLUE+" SkyBool", "", 20, 50, 20);
            }
        }
        return false;
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
