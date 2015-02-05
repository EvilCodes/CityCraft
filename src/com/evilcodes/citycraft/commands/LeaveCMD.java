package com.evilcodes.citycraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.CityAPI;
import com.evilcodes.citycraft.api.PlayerAPI;
import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.handlers.LogHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;

public class LeaveCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {		
		if (cs instanceof Player) {
			Player player = (Player) cs;
			if (!PlayerAPI.isOwner(player.getName())) {
				if (PlayerAPI.isInCity(player.getName())) {
					final String cityname = DBHandler.getcity(player.getName());
					final String members = DBHandler.getMemberString(cityname);
					if (members.contains(player.getName())) {
						CityAPI.broadcast(cityname, MessagesHandler.convert("Leave.Broadcast").replace("%name%", player.getName()));
						
						final String newmembers = members.replace( player.getName() + ", ", "");
						final String query = "UPDATE `" + CityCraft.dbprefix + "cities" + "` SET `members` = '" + newmembers + "' WHERE `cityname` = '" + cityname + "'";
						CityCraft.core.update(query);
						
						final String playerquery = "UPDATE `" + CityCraft.dbprefix + "players" + "` SET `cityname` = '' WHERE `playername` = '" + player.getName() + "'";
						final String rankquery = "UPDATE `" + CityCraft.dbprefix + "players" + "` SET `rank` = '' WHERE `playername` = '" + player.getName() + "'";
						CityCraft.core.update(playerquery);
						CityCraft.core.update(rankquery);
						player.sendMessage(MessagesHandler.convert("Leave.Left"));
					} else {
						LogHandler.err(player.getName() + " couldnt leave his city, because of an MySQL-Error!");
					}
				} else {
					player.sendMessage(MessagesHandler.convert("Leave.NoCity"));
				}
			} else {
				player.sendMessage(MessagesHandler.convert("Leave.IsOwner"));
			}
		} else {
			cs.sendMessage(MessagesHandler.convert("NoPlayer"));
		}
	}
	 
}
