package com.evilcodes.citycraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.CityAPI;
import com.evilcodes.citycraft.api.PlayerAPI;
import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;

public class KickCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 2 || args.length == 3) {
			if (cs instanceof Player) {
				if (args.length == 2) {
					final Player player = (Player) cs;
					final String kick = args[1];
					final String city = DBHandler.getcity(player.getName());
					if (PlayerAPI.isOwner(player.getName())) {
						if (DBHandler.getcity(kick).equalsIgnoreCase(city)) {
							if (Bukkit.getOfflinePlayer(kick).hasPlayedBefore()) {
								final String members = DBHandler.getMemberString(city);
								final String newmembers = members.replace(", " + kick, "");
								final String query = "UPDATE `" + CityCraft.dbprefix + "cities" + "` SET `members` = '" + newmembers + "' WHERE `cityname` = '" + city + "'";;
								CityCraft.core.update(query);
								
								final String playerquery = "UPDATE `" + CityCraft.dbprefix + "players" + "` SET `cityname` = '' WHERE `playername` = '" + kick + "'";
								CityCraft.core.update(playerquery);
								final Player kickedplayer = Bukkit.getPlayerExact(kick);
								if (kickedplayer.isOnline()) {
									kickedplayer.sendMessage(MessagesHandler.convert("Kick.GotKicked").replace("%name%", player.getName()));
								}
								CityAPI.broadcast(city, MessagesHandler.convert("Kick.KickBroadcast").replace("%name%", player.getName()).replace("%kick%", kick));
								player.sendMessage(MessagesHandler.convert("Kick.Kicked").replace("%name%", kick));
							} else {
								player.sendMessage(MessagesHandler.convert("Kick.UnknownPlayer"));
							}
						} else {
							player.sendMessage(MessagesHandler.convert("Kick.NotInCity"));
						}
					} else {
						player.sendMessage(MessagesHandler.convert("Kick.NoOwner"));
					}
				}
				if (args.length == 3) {
					final Player player = (Player) cs;
					if (player.isOp()) {
						consoleKick(cs, cmd, label, args);
					} else {
						player.sendMessage(MessagesHandler.noPermission("Kick as OP"));
					}
				}
			} else {
				if (args.length == 3) {
					consoleKick(cs, cmd, label, args);
				} else {
					cs.sendMessage(MessagesHandler.convert("Kick.CSUsage"));
				}
			}
		} else {
			cs.sendMessage(MessagesHandler.convert("Kick.Usage"));
		}
	}
	
	private static void consoleKick(CommandSender cs, Command cmd, String label, String[] args) {
		final String city = args[1];
		final String kick = args[2];
		if (CityAPI.cityExists(city)) {
			if (PlayerAPI.isInCity(kick, city)) {
				final OfflinePlayer kickplayer = Bukkit.getOfflinePlayer(kick);
				if (kickplayer.hasPlayedBefore()) {
					final String members = DBHandler.getMemberString(city);
					final String newmembers = members.replace(", " + kick, "");
					final String query = "UPDATE `" + CityCraft.dbprefix + "cities" + "` SET `members` = '" + newmembers + "' WHERE `cityname` = '" + city + "'";;
					CityCraft.core.update(query);
					final Player kickedplayer = Bukkit.getPlayerExact(kick);
					if (kickedplayer.isOnline()) {
						kickedplayer.sendMessage(MessagesHandler.convert("Kick.GotKicked").replace("%name%", cs.getName()));
					}
					CityAPI.broadcast(city, MessagesHandler.convert("Kick.KickBroadcast").replace("%name%", cs.getName()).replace("%kick%", kick));
				} else {
					cs.sendMessage(MessagesHandler.convert("Kick.UnknownPlayer"));
				}
			} else {
				cs.sendMessage(MessagesHandler.convert("Kick.CSPlayerNotInCity"));
			}
		} else {
			cs.sendMessage(MessagesHandler.convert("Kick.CSUnknownCity"));
		}
	}

}
