package com.evilcodes.citycraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.handlers.LogHandler;
import com.evilcodes.citycraft.utils.MySQLCore;

public class JoinEvent implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player player = e.getPlayer();
		final String query = "SELECT * FROM `" + CityCraft.dbprefix + "players" + "` WHERE `playername` = '" + player.getName() + "';";
		if (!MySQLCore.mysqlExists(query)) {
			final String insertquery = "INSERT INTO `" + CityCraft.dbprefix + "players` (`playername` ,`cityname` ,`rank`) VALUES ('" + player.getName() + "',  '',  '')";
			CityCraft.core.insert(insertquery);
			LogHandler.log("Player " + player.getName() + " successfull inserted into database!" );
			//"INSERT INTO `" + CityCraft.dbprefix + "cities" + "` (`cityname`,`citytag`,`basex`,`basey`,`basez`,`worldname`,`owner`,`members`,`boughtslots`) VALUES"
			//+ " ('" + cityname + "', '" + citytag + "', '" + player.getLocation().getBlockX() + "', '" + player.getLocation().getBlockY() + "', '" + player.getLocation().getBlockZ() + "', '" + player.getWorld().getName() + "', '" + owner + "', '" + owner + "', 0)";												
		}
	}
	
}
