package com.evilcodes.citycraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.PlayerAPI;
import com.evilcodes.citycraft.handlers.MessagesHandler;
import com.evilcodes.citycraft.utils.MySQLCore;
import com.evilcodes.citycraft.utils.WorldGuard;

public class CreateCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 3) {
			if (cs instanceof Player) {
				final Player player = (Player) cs;
				//Check for space between cities
				if (WorldGuard.rangeOkay(player.getLocation())) {
					final String cityname = args[1];
					final int Lenght = CityCraft.config.getInt("Lenght");
					final String citytag = args[2];
					final String owner = player.getName();
					if (!PlayerAPI.isInCity(player.getName())) {
						if (citytag.length() <= Lenght) {
							final String nameq = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
							final String tagq = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `citytag` = '" + citytag + "';";
							if (!MySQLCore.mysqlExists(nameq)) {
								if (!MySQLCore.mysqlExists(tagq)) {
									//Create protected Region (with WG)
									final String regionname = citytag + "-" + cityname;
									if (WorldGuard.createRegion(regionname, player)) {
										//MySQL Code & Set to Owner
										final String query = "INSERT INTO `" + CityCraft.dbprefix + "cities" + "` (`cityname`,`citytag`,`basex`,`basey`,`basez`,`worldname`,`owner`,`members`,`boughtslots`) VALUES"
												+ " ('" + cityname + "', '" + citytag + "', '" + player.getLocation().getBlockX() + "', '" + player.getLocation().getBlockY() + "', '" + player.getLocation().getBlockZ() + "', '" + player.getWorld().getName() + "', '" + owner + "', '" + owner + "', 0)";												
										CityCraft.core.insert(query);
										//Update MySQL
										final String playerquery = "UPDATE `" + CityCraft.dbprefix + "players" + "` SET `cityname` = '" + cityname + "' WHERE `playername` = '" + player.getName() + "'";
										CityCraft.core.update(playerquery);
										player.sendMessage(MessagesHandler.convert("Create.Created").replace("%cityname%", cityname).replace("%citytag%", citytag));
									} else {
										player.sendMessage(MessagesHandler.convert("Create.RegionExist").replace("%name%", cityname));
									}
								} else {
									player.sendMessage(MessagesHandler.convert("Create.TagExist").replace("%tag%", citytag));
								}
							} else {
								player.sendMessage(MessagesHandler.convert("Create.CityExist").replace("%name%", cityname));
							}
						} else {
							player.sendMessage(MessagesHandler.convert("Create.TooLongTag").replace("%tag%", citytag).replace("%lenght%", Lenght + ""));
						}
					} else {
						player.sendMessage(MessagesHandler.convert("Create.InCity"));
					}
				} else {
					player.sendMessage(MessagesHandler.convert("Create.NoSpace"));
				}
			} else {
				cs.sendMessage(MessagesHandler.convert("NoPlayer"));
			}
		} else {
			cs.sendMessage(MessagesHandler.convert("Create.Usage"));
		}
	}

}
