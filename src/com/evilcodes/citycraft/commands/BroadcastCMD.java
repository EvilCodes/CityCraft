package com.evilcodes.citycraft.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.api.CityAPI;
import com.evilcodes.citycraft.api.PlayerAPI;
import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;

public class BroadcastCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			cs.sendMessage(MessagesHandler.convert("Broadcast.Usage"));
		}
		if (args.length >= 2) {
			if (cs instanceof Player) {
				final Player player = (Player) cs;
				if (DBHandler.incity(player.getName())) {
					if (PlayerAPI.isOwner(player.getName())) {
						final String broadcastmessage1 = Arrays.toString(args).replace(", ", " ").replace("[", "").replace("]", "").replace("broadcast", "").replace("&", "§");
						final String city = DBHandler.getcity(player.getName());
						final String broadcastmessage = MessagesHandler.convert("Broadcast.Prefix").replace("%cityname%", city) + broadcastmessage1;
						CityAPI.broadcast(city, broadcastmessage);
					} else {
						player.sendMessage(MessagesHandler.convert("Broadcast.NotOwner"));
					}
				} else {
					player.sendMessage(MessagesHandler.convert("Broadcast.NotInCity"));
				}
			} else {
				cs.sendMessage(MessagesHandler.convert("NoPlayer"));
			}
		}
	}	
}
