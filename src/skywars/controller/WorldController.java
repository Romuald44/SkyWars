/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import skywars.SkyWars;

/**
 *
 * @author Romuald
 */
public class WorldController {
    
    private static SkyWars plugin;
    
    public WorldController() {
        plugin = SkyWars.get();
    }
    
    public boolean loadWorld(String worldName){
        String isLobby = worldName.substring(0, Math.min(worldName.length(), 5));
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generateStructures(false);
        /*worldCreator.generator(new ChunkGenerator() {
            @Override
        	public List<BlockPopulator> getDefaultPopulators(World world) {
                return Arrays.asList(new BlockPopulator[0]);
            }
            
            @Override
            public boolean canSpawn(World world, int x, int z) {
                return true;
            }
            
            @Override
            public byte[] generate(World world, Random random, int x, int z) {
                return new byte[32768];
            }
    
            @Override
            public Location getFixedSpawnLocation(World world, Random random) {
                return new Location(world, 0.0D, 64.0D, 0.0D);
            }
        });*/
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
        world.setKeepSpawnInMemory(false);
        world.setTicksPerAnimalSpawns(1);
        world.setTicksPerMonsterSpawns(1);
        
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
    
}
