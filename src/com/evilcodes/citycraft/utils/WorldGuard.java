package com.evilcodes.citycraft.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.CityAPI;
import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.handlers.LogHandler;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldGuard {

	public static WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	    return (WorldGuardPlugin) plugin;
	}	
	
	public static Boolean isInHisCityRegion(String playername) {
		final String cityname = DBHandler.getcity(playername);
		final Player player = Bukkit.getPlayerExact(playername);
		final Location loc = player.getLocation();
		//ABC-Alphabet
		final String cityregion = CityAPI.getTag(cityname) + "-" + cityname;
		final ApplicableRegionSet ar = WGBukkit.getRegionManager(loc.getWorld()).getApplicableRegions(loc);
	    final Iterator<ProtectedRegion> prs = ar.iterator();
	    while (prs.hasNext()) {
	        ProtectedRegion pr = prs.next();
	        if (pr.getId().equalsIgnoreCase(cityregion)) {
	        	return true;
	        } else {
	         	return false;
	        }
	    }
		return false;
	}
	
	public static boolean createRegion(String regionname, Player player) {
		final WorldGuardPlugin wg = WorldGuard.getWorldGuard();
		final Location loc = player.getLocation();
		//North - Right
		final Double xnr = player.getLocation().getX() + 10.00;
		final Double znr = player.getLocation().getZ() - 10.00;
		final BlockVector2D nr = new BlockVector2D(xnr, znr);
		//North - Left
		final Double xnl = player.getLocation().getX() - 10.00;
		final Double znl = player.getLocation().getZ() - 10.00;
		final BlockVector2D nl = new BlockVector2D(xnl, znl);
		//South - Right
		final Double xsr = player.getLocation().getX() + 10.00;
		final Double zsr = player.getLocation().getZ() + 10.00;
		final BlockVector2D sr = new BlockVector2D(xsr, zsr);
		//South - Left
		final Double xsl = player.getLocation().getX() - 10.00;
		final Double zsl = player.getLocation().getZ() + 10.00;
		final BlockVector2D sl = new BlockVector2D(xsl, zsl);
		
		final List<BlockVector2D> points = new ArrayList<BlockVector2D>();
		points.add(nr);
		points.add(nl);
		points.add(sr);
		points.add(sl);
		
		final ProtectedRegion prg = new ProtectedPolygonalRegion(regionname, points, 1, 256);
		final DefaultDomain owners = new DefaultDomain();
		owners.addPlayer(player.getName());
		prg.setOwners(owners);
		final StringFlag regionmessage = DefaultFlag.GREET_MESSAGE;
		try {
			prg.setFlag(regionmessage, regionmessage.parseInput(WorldGuard.getWorldGuard(), Bukkit.getConsoleSender(), "§bWillkommen in der Stadt §6" + regionname));
		} catch (InvalidFlagFormat e1) {
			e1.printStackTrace();
		}
		//Cuboid (Not working) -    new ProtectedCuboidRegion(regionname, pt1, pt2);
		
		if (!wg.getGlobalRegionManager().get(loc.getWorld()).hasRegion(regionname)) {
			try {
				//create Region
				wg.getRegionManager(loc.getWorld()).addRegion(prg);
				wg.getRegionManager(loc.getWorld()).save();
			} catch (Exception e) {
				LogHandler.err("Could not save the new region " + regionname);
				LogHandler.stackerr(e);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean inRegion(Location loc) {
		final WorldGuardPlugin wg = WorldGuard.getWorldGuard();
		final RegionManager rm = wg.getRegionManager(loc.getWorld());
		if (rm.getApplicableRegions(loc) == null || rm.getApplicableRegions(loc).size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static Boolean rangeOkay(Location loc) {
		final int space = CityCraft.config.getInt("Space");
		
		int y = 50;
		//int z = (space / 2) - space;
		/*
		while (x < space) {
			x++;
			final Location locationx = new Location(loc.getWorld(), x, y, z);
			if (inRegion(locationx)) {
				return false;
			}
			while (y < space) {
				y++;
				final Location locationy = new Location(loc.getWorld(), x, y, z);
				if (inRegion(locationy)) {
					return false;
				}
			}
		}
		*/
		/*
		for (int x = (space / 2) - space; x == space; x++) {
			final Location locationx = new Location(loc.getWorld(), loc.getX() + x, y, loc.getZ() + z);
			if (inRegion(locationx)) {
				return false;
			} else {
				for (;z == space; z++) {
					final Location locationy = new Location(loc.getWorld(), loc.getX() + x, y, loc.getZ() + z);
					if (inRegion(locationy)) {
						return false;
					}
				}
			}
		}
		*/
		
		for (int x = (space / 2) - space; ; x++) {
			if (x >= (space / 2)) {
				break;
			}
			for (int z = (space / 2) - space; ; y++) {
				if (z >= (space / 2)) {
					break;
				}
				final Location location = new Location(loc.getWorld(), loc.getX() + x, y, loc.getZ() + z);
				if (inRegion(location)) {
					return false;
				}
			}
		}
		return true;
	}

	public static void deleteRegion(String regionname) {
		final World world = CityAPI.getBaseLoc(regionname).getWorld();
		final WorldGuardPlugin wg = WorldGuard.getWorldGuard();
		final RegionManager regm = wg.getGlobalRegionManager().get(world);
		final ProtectedRegion region = regm.getRegionExact(regionname);
		regm.removeRegion(region.getId());
		try {
			regm.save();
		} catch (Exception e) {
			LogHandler.err("Could not save regions " + regionname);
			LogHandler.stackerr(e);
		}
	}
}