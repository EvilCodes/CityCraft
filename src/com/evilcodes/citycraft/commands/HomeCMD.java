package com.evilcodes.citycraft.commands;

import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.PlayerAPI;
import com.evilcodes.citycraft.handlers.LogHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;

public class HomeCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			final Player player = (Player) cs;
			if (args.length == 1) {
				if (PlayerAPI.isInCity(player.getName())) {
					//TP to own cityhome
					player.getTargetBlock(null, 100);
					final String cityname = PlayerAPI.getCity(player.getName());
					final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
					final ResultSet res = CityCraft.core.select(query);
				    if (res != null) {
					      try {
					    	  while(res.next()) {
					    		  final int x = res.getInt("basex");
					    		  final int y = res.getInt("basey");
					    		  final int z = res.getInt("basez");
					    		  final String worldstring = res.getString("worldname");
					    		  final World world = Bukkit.getWorld(worldstring);
					    		  final Location loc = new Location(world, x, y, z);
					    		  player.teleport(loc);
					    	  }
						  } catch (Exception ex) {
							  LogHandler.err("Could not reach MySQLDatabase!");
							  LogHandler.stackerr(ex);
						  }
					} else {
					      LogHandler.err("Resultset is null! (Maybe a wrong query)");
					}
				} else {
					player.sendMessage(MessagesHandler.convert("Home.NoCity"));
				}
			}
			if (args.length == 2) {
				//TP to other cityhome
				if (player.hasPermission("city.home.others")) {
					final String cityname = args[1];
					final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
					final ResultSet res = CityCraft.core.select(query);
				    if (res != null) {
					      try {
					    	  while(res.next()) {
					    		  final int x = res.getInt("basex");
					    		  final int y = res.getInt("basey");
					    		  final int z = res.getInt("basez");
					    		  final String worldstring = res.getString("worldname");
					    		  final World world = Bukkit.getWorld(worldstring);
					    		  final Location loc = new Location(world, x, y, z);
					    		  player.teleport(loc);
					    	  }
						  } catch (Exception ex) {
							  LogHandler.err("Could not reach MySQLDatabase!");
							  LogHandler.stackerr(ex);
						  }
					} else {
					      LogHandler.err("Resultset is null! (Maybe a wrong query)");
					}
				} else {
					player.sendMessage(MessagesHandler.noPermission("city.home.others"));
				}
			}
		}
	}

}
