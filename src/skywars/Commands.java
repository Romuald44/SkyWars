/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 *
 * @author Romuald
 */
public class Commands implements CommandExecutor {
    
    InstanceMap instance_skybool;
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        
        if(cmd.getName().equalsIgnoreCase("skywars") && sender instanceof Player) {
            if(args[0].equals("enter")) {
                p.sendMessage("Ok pour la reception");
                //instance_skybool.addPlayers(p);
            }
            else {
                
            }
        }
        return false;
    }
}
