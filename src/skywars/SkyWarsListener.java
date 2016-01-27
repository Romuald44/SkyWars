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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

/**
 *
 * @author Romuald
 */
public class SkyWarsListener implements Listener {
    
    WorldCreator skybool = new WorldCreator("SkyBool");
    WorldCreator skybaal = new WorldCreator("SkyBaal");
    InstanceMap instance_skybool = new InstanceMap(Bukkit.getWorld("SkyBool"));
    
    Location spawn_start = new Location(Bukkit.getWorld("World"), 0.5, 101, 0.5);
    Location choice_class = new Location(Bukkit.getWorld("World"), 500.5, 101, 500.5);
    Location choice_skywars = new Location(Bukkit.getWorld("World"), -500.5, 101, -500.5);
    Location plateform = new Location(Bukkit.getWorld("World"), 21, 101, -55);
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
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
           (p.getLocation().getBlockZ() == 12) ||
                (p.getLocation().getBlockX() == -12) &&
           (p.getLocation().getBlockY() == 103) &&
           (p.getLocation().getBlockZ() == 12) ||
                (p.getLocation().getBlockX() == -12) &&
           (p.getLocation().getBlockY() == 104) &&
           (p.getLocation().getBlockZ() == 12)) {
            
            p.teleport(choice_class);
            sendTitle(p, ChatColor.GOLD + "Mode PVP", ChatColor.RED + "Choississez votre classe", 20, 50, 20);
        }
        if((p.getLocation().getBlockX() == -12) &&
           (p.getLocation().getBlockY() == 102) &&
           (p.getLocation().getBlockZ() == -12) ||
                (p.getLocation().getBlockX() == -12) &&
           (p.getLocation().getBlockY() == 103) &&
           (p.getLocation().getBlockZ() == -12) ||
                (p.getLocation().getBlockX() == -12) &&
           (p.getLocation().getBlockY() == 104) &&
           (p.getLocation().getBlockZ() == -12)) {
            
            p.teleport(choice_skywars);
            sendTitle(p, ChatColor.GOLD + "Mode SkyWars", "", 20, 50, 20);
        }
        else if((p.getLocation().getBlockX() == 501) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 489) || (p.getLocation().getBlockX() == 500) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 489) || (p.getLocation().getBlockX() == 499) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 489) ||
                (p.getLocation().getBlockX() == 501) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 489) || (p.getLocation().getBlockX() == 500) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 489) || (p.getLocation().getBlockX() == 499) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 489) ||
                (p.getLocation().getBlockX() == 501) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 489) || (p.getLocation().getBlockX() == 500) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 489) || (p.getLocation().getBlockX() == 499) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 489)) {
            
            p.teleport(plateform);
            stuffArcher(p);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Archer", ChatColor.RED + "Ca va gicler !", 20, 50, 20);
        }
        else if((p.getLocation().getBlockX() == 509) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 491) ||
                (p.getLocation().getBlockX() == 509) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 491) ||
                (p.getLocation().getBlockX() == 509) &&
           (p.getLocation().getBlockY() == 101) &&
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
           (p.getLocation().getBlockZ() == 501) ||
                (p.getLocation().getBlockX() == 511) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 499) || (p.getLocation().getBlockX() == 511) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 500) || (p.getLocation().getBlockX() == 511) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 501) ||
                (p.getLocation().getBlockX() == 511) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 499) || (p.getLocation().getBlockX() == 511) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 500) || (p.getLocation().getBlockX() == 511) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 501)) {
            
            p.teleport(plateform);
            stuffAssassin(p);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Assassin", ChatColor.RED + "Prendre par derrière ça fait mal", 20, 50, 20);
        }
        else if((p.getLocation().getBlockX() == 509) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 509) ||
                (p.getLocation().getBlockX() == 509) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 509) ||
                (p.getLocation().getBlockX() == 509) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 509)) {
            
            p.teleport(plateform);
            stuffTank(p);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Tank", ChatColor.RED + "Prêt à recevoir ?", 20, 50, 20);
        }
        else if((p.getLocation().getBlockX() == 489) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 499) || (p.getLocation().getBlockX() == 489) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 500) || (p.getLocation().getBlockX() == 489) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 501) ||
                (p.getLocation().getBlockX() == 489) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 499) || (p.getLocation().getBlockX() == 489) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 500) || (p.getLocation().getBlockX() == 489) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 501) ||
                (p.getLocation().getBlockX() == 489) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 499) || (p.getLocation().getBlockX() == 489) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 500) || (p.getLocation().getBlockX() == 489) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 501)) {
            
            p.teleport(plateform);
            stuffNormal(p);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Normal", ChatColor.RED + "Pour des gens normaux... Ou presque !", 20, 50, 20);
        }
        else if((p.getLocation().getBlockX() == 491) &&
           (p.getLocation().getBlockY() == 99) &&
           (p.getLocation().getBlockZ() == 491) ||
                (p.getLocation().getBlockX() == 491) &&
           (p.getLocation().getBlockY() == 100) &&
           (p.getLocation().getBlockZ() == 491) ||
                (p.getLocation().getBlockX() == 491) &&
           (p.getLocation().getBlockY() == 101) &&
           (p.getLocation().getBlockZ() == 491)) {
            
            p.teleport(plateform);
            stuffPopo(p);
            sendTitle(p, ChatColor.GREEN + "Classe "+ ChatColor.BLUE + "Potions", ChatColor.RED + "La drogue c mal ! mvoyez", 20, 50, 20);
        }
    }
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        
        /*p.sendMessage("X : "+e.getClickedBlock().getX());
        p.sendMessage("Y : "+e.getClickedBlock().getY());
        p.sendMessage("Z : "+e.getClickedBlock().getZ());*/
        
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getClickedBlock().getX() == -500 
                && e.getClickedBlock().getY() == 101 
                && e.getClickedBlock().getZ() == -495) {
                
                skybool.createWorld();
                
                if(instance_skybool.getPlayers() <8) {
                    if(instance_skybool.getPlayers() >= 2) {
                        instance_skybool.Countdown();
                    }
                    p.sendMessage(instance_skybool.getName());
                    instance_skybool.addPlayers(p);
                    p.sendMessage(""+instance_skybool.getPlayers());
                    sendTitle(p, ChatColor.GREEN + "Map "+ ChatColor.BLUE + "Cité", "", 20, 50, 20);
                    p.teleport(instance_skybool.onSpawnAlea(p));
                }
            }
        }
    }
    
    /*@EventHandler
    public void onSignChange(SignChangeEvent e) {
        if(e.getLine(0).equalsIgnoreCase("SkyBool")) {
            e.setLine(0, ChatColor.RED+"SkyBool");
            //e.setLine(1, "Ta gueule");
        }
    }*/
    
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {//On récupère la commande tapée par l'utilisateur
        
        Player p = e.getPlayer();// On récupère le joueur.
        World monde = p.getWorld();

        if(e.getMessage().equalsIgnoreCase("/heal")) {
            p.setHealth(20.0);
            //p.sendTitle("Heal", "Récupération du total de vie");
            sendTitle(p, "Heal", ChatColor.RED + "Récupération du total de vie", 20, 80, 20);
        }
        else if(e.getMessage().equalsIgnoreCase("/skybool")) {
            skybool.createWorld();
            p.teleport(new Location(Bukkit.getWorld("SkyBool"), -110, 101, 297));//.getServer()
        }
        else if(e.getMessage().equalsIgnoreCase("/hub")) {
            p.teleport(spawn_start);
            instance_skybool.removePlayers(p);
        }
        else if(e.getMessage().equalsIgnoreCase("/pvp")) {
            p.teleport(choice_class);
        }
        else if(e.getMessage().equalsIgnoreCase("/skywars")) {
            p.teleport(choice_skywars);
        }
        else if(e.getMessage().equalsIgnoreCase("/joueurs")) {
            instance_skybool.showPlayers();
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