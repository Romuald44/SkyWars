public static Location spawn = new Location(Bukkit.getWorld("monde2"), -314.5, 124, -235.5); // Coordonné du centre de ton spawn 

public static boolean isCloseToSpawn(Location loc) {
	return isCloseToSpawn(loc, 23); // radius de 23 ! 
} 

public static boolean isCloseToSpawn(Location loc, int radius) 
{ 
	if(loc == null) {
		return false;
	}
	int x = Math.abs(loc.getBlockX() - spawn.getBlockX());
	 int y = Math.abs(loc.getBlockY() - spawn.getBlockY());
	  int z = Math.abs(loc.getBlockZ() - spawn.getBlockZ());
	   return (x < radius) && (y < 70) && (z < radius); 
} 