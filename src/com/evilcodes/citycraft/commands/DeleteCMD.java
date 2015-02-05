package com.evilcodes.citycraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.CityAPI;
import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;
import com.evilcodes.citycraft.utils.WorldGuard;

public class DeleteCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 2) {
			final String cityname = args[1];
			if (CityAPI.cityExists(cityname)) {
				//Delete members prefix and cityname from playerstable
				final String[] members = DBHandler.getMemberArray(cityname);
				for (String memberstring : members) {
					final String citynamequery = "UPDATE `" + CityCraft.dbprefix + "players" + "` SET `cityname` = '' WHERE `playername` = '" + memberstring + "'";
					final String rankquery = "UPDATE `" + CityCraft.dbprefix + "players" + "` SET `rank` = '' WHERE `playername` = '" + memberstring + "'";
					CityCraft.core.update(citynamequery);
					CityCraft.core.update(rankquery);
					final Player member = Bukkit.getPlayerExact(memberstring);
					member.sendMessage(MessagesHandler.convert("Delete.Broadcast").replace("%name%", cs.getName()));
					final String citytag = CityAPI.getTag(cityname);
					final String regionname = citytag + "-" + cityname;
					WorldGuard.deleteRegion(regionname);
				}				
				//Delete from citiestable
				final String query = "DELETE FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
				CityCraft.core.delete(query);
				cs.sendMessage(MessagesHandler.convert("Delete.Deleted").replace("%name%", cityname));
			} else {
				cs.sendMessage(MessagesHandler.convert("Delete.CityDoesntExist").replace("%name%", cityname));
			}
		} else {
			cs.sendMessage(MessagesHandler.convert("Delete.Usage"));
		}
	}

}
