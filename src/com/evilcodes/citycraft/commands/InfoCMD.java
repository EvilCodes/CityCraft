package com.evilcodes.citycraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.PlayerAPI;
import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;
import com.evilcodes.citycraft.utils.MySQLCore;

public class InfoCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (cs instanceof Player) {
				final Player player = (Player) cs;
				if (PlayerAPI.isInCity(player.getName())) {
					final String cityname = DBHandler.getcity(cs.getName());
					DBHandler.cityInfo(cityname, cs);
				} else {
					player.sendMessage(MessagesHandler.convert("Info.InNoCity"));
				}
			} else {
				cs.sendMessage(MessagesHandler.convert("NoPlayer"));
			}
		} else if (args.length == 2) {
			final String cityname = args[1];
			final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
			if (MySQLCore.mysqlExists(query)) {
				DBHandler.cityInfo(cityname, cs);
			} else {
				cs.sendMessage(MessagesHandler.convert("Info.NoCity").replace("%name%", cityname));
			}
		} else {
			cs.sendMessage(MessagesHandler.convert("Info.Usage"));
		}
	}

}
