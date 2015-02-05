package com.evilcodes.citycraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.CityAPI;
import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;

public class GiveRankCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			final Player player = (Player) cs;
			if (args.length == 3) {
				final String rank = args[2];
				if (rank.length() <= 10) {
					final String modifiedplayername = args[1];
					final Player modifiedplayer = Bukkit.getPlayerExact(modifiedplayername);
					final String cityofplayer = DBHandler.getcity(modifiedplayername);
					final String owner = CityAPI.getOwner(cityofplayer);
					final String playername = player.getName();
					if (owner.equalsIgnoreCase(playername) || playername.equalsIgnoreCase(owner)) {
						final String query = "UPDATE `" + CityCraft.dbprefix + "players" + "` SET `rank` = '" + rank + "' WHERE `playername` = '" + modifiedplayername + "'";
						CityCraft.core.update(query);
						modifiedplayer.sendMessage(MessagesHandler.convert("GiveRank.NewRank").replace("%rank%", rank.replace("&", "§")));
						player.sendMessage(MessagesHandler.convert("GiveRank.Ranked").replace("%rank%", rank.replace("&", "§")).replace("%name%", modifiedplayername));
					} else {
						player.sendMessage(MessagesHandler.convert("GiveRank.Owner"));
					}
				} else {
					player.sendMessage(MessagesHandler.convert("GiveRank.TooLong"));
				}
			} else {
				cs.sendMessage(MessagesHandler.convert("GiveRank.Usage"));
			}
		} else {
			cs.sendMessage(MessagesHandler.convert("NoPlayer"));
		}
	}

}
