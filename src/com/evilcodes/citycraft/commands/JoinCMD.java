package com.evilcodes.citycraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.CityAPI;
import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;

public class JoinCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			Player player = (Player) cs;
			if (args.length == 2) {
				if (!DBHandler.incity(player.getName())) {
					final String city = args[1];
					final String playerquery = "UPDATE `" + CityCraft.dbprefix + "players" + "` SET `cityname` = '" + city + "' WHERE `playername` = '" + player.getName() + "'";
					CityCraft.core.update(playerquery);
					final String members = DBHandler.getMemberString(city);
					final String memberquery = "UPDATE `" + CityCraft.dbprefix + "cities" + "` SET `members` = '" + members + ", " + player.getName() + "' WHERE `cityname` = '" + city + "'";
					CityCraft.core.update(memberquery);
					CityAPI.broadcast(city, MessagesHandler.convert("Join.Broadcast").replace("%name%", player.getName()));
					player.sendMessage(MessagesHandler.convert("Join.Joined").replace("%name%", city));
				} else {
					player.sendMessage(MessagesHandler.convert("Join.InCity"));
				}
			} else {
				player.sendMessage(MessagesHandler.convert("Join.Usage"));
			}
		} else {
			cs.sendMessage(MessagesHandler.convert("NoPlayer"));
		}
	}

}
