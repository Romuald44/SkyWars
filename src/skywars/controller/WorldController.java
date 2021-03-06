/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import skywars.SkyWars;

/**
 *
 * @author Romuald
 */
public class WorldController {
    
    private static SkyWars plugin;
    
    public WorldController() {
        plugin = SkyWars.get();
        newInstance();
    }
    
    public boolean loadWorld(String worldName){
        String isLobby = worldName.substring(0, Math.min(worldName.length(), 5));
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generateStructures(false);
	World world = worldCreator.createWorld();
        world.setDifficulty(Difficulty.NORMAL);
        world.setSpawnFlags(true, true);
        if (isLobby.equalsIgnoreCase("lobby")) {
        	world.setPVP(false);
        } else {
        	world.setPVP(true);
        }
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(Integer.MAX_VALUE);
        world.setAutoSave(false);
        world.setTime(1000);
        world.setKeepSpawnInMemory(false);
        world.setTicksPerAnimalSpawns(0);
        world.setTicksPerMonsterSpawns(0);
        
        world.setGameRuleValue("keepInventory ", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("showDeathMessages", "false");
        
        boolean loaded = false;
        for(World w: plugin.getServer().getWorlds()) {
            if(w.getName().equals(world.getName())) {
                loaded = true;
                break;
            }
        }
            return loaded;
	}
    
    public void copyWorld(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                    target.mkdirs();
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {

        }
    }
    
    public void unloadWorld(String w) {
        World world = SkyWars.get().getServer().getWorld(w);
        if(world != null) {
            SkyWars.get().getServer().unloadWorld(world, true);
        }
    }
    
    public void deleteWorld(String name) {
        unloadWorld(name);
        File target = new File (SkyWars.get().getServer().getWorldContainer().getAbsolutePath(), name);
        deleteWorld(target);
    }

    public boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    public void newInstance() {
        unloadWorld("SkyBool1");
        deleteWorld("SkyBool1");
        copyWorld(new File("SkyBool"), new File("SkyBool1"));
        loadWorld("SkyBool1");
        
        unloadWorld("TempleRun1");
        deleteWorld("TempleRun1");
        copyWorld(new File("TempleRun"), new File("TempleRun1"));
        loadWorld("TempleRun1");
        
        unloadWorld("Prairie1");
        deleteWorld("Prairie1");
        copyWorld(new File("Prairie"), new File("Prairie1"));
        loadWorld("Prairie1");
        
        unloadWorld("NetherWars1");
        deleteWorld("NetherWars1");
        copyWorld(new File("NetherWars"), new File("NetherWars1"));
        loadWorld("NetherWars1");
    }
}
