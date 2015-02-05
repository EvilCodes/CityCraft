package com.evilcodes.citycraft.commands;

import java.util.HashMap;

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

public class InviteCMD {
	
	public static HashMap<Player, String> invitedplayers = new HashMap<Player, String>();
	public static HashMap<String, String> invitedcities = new HashMap<String, String>();

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			Player player = (Player) cs;
			
			/*
			//ZOMBIE
			Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
			zombie.setCustomName(ChatColor.GREEN + "ZOMBIE");
			zombie.setCustomNameVisible(true);
			ItemStack item = new ItemStack(Material.IRON_SWORD);
			zombie.getEquipment().setItemInHand(item);
			zombie.setCanPickupItems(false);
			zombie.setHealth(500.00);
			zombie.setMaxHealth(500.00);
			//ZOMBIE
			*/
			
			
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("accept")) {
					if (invitedcities.containsKey(player.getName())) {
						final String whoinviteds = invitedplayers.get(player).intern();
						final Player whoinvited = Bukkit.getPlayerExact(whoinviteds);
						final String city = invitedcities.get(player.getName()).intern();
						final String playerquery = "UPDATE `" + CityCraft.dbprefix + "players" + "` SET `cityname` = '" + city + "' WHERE `playername` = '" + player.getName() + "'";
						CityCraft.core.update(playerquery);
						final String members = DBHandler.getMemberString(city);
						final String memberquery = "UPDATE `" + CityCraft.dbprefix + "cities" + "` SET `members` = '" + members + ", " + player.getName() + "' WHERE `cityname` = '" + city + "'";
						CityCraft.core.update(memberquery);
						whoinvited.sendMessage(MessagesHandler.convert("Invite.Accepted").replace("%name%", player.getName()).replace("%city%", city));
						invitedcities.remove(player.getName());
						invitedplayers.remove(player);
						player.sendMessage(MessagesHandler.convert("Invite.Accept").replace("%city%", city));
						CityAPI.broadcast(city, MessagesHandler.convert("Join.Joined").replace("%name%", player.getName()));
					} else {
						player.sendMessage(MessagesHandler.convert("Invite.NoRequest"));
					}
				} else if (args[1].equalsIgnoreCase("deny")) {
					if (invitedcities.containsKey(player.getName())) {
						final String whoinviteds = invitedplayers.get(player).intern();
						final Player whoinvited = Bukkit.getPlayerExact(whoinviteds);
						whoinvited.sendMessage(MessagesHandler.convert("Invite.Denied").replace("%name%", player.getName()));
						invitedcities.remove(player.getName());
						invitedplayers.remove(player);
					} else {
						player.sendMessage(MessagesHandler.convert("Invite.NoRequest"));
					}
				} else {
				//Check if player is in a city
					if (PlayerAPI.isInCity(player.getName())) {
						//Check if invited player is online
						final OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
						if (op.hasPlayedBefore()) {
							final Player invited = Bukkit.getPlayerExact(args[1]);
							if (invited.isOnline()) {
								//Check if invited player is not in a city
								if (!PlayerAPI.isInCity(invited.getName())) {
									//Send request
									if (invitedcities.containsKey(invited.getName())) {
										invitedcities.remove(invited.getName());									
									}
									if (invitedplayers.containsKey(invited.getName())) {
										invitedplayers.remove(invited.getName());									
									}
									final String city = DBHandler.getcity(player.getName());
									invitedcities.put(invited.getName(), city);
									invitedplayers.put(invited, player.getName());
									player.sendMessage(MessagesHandler.convert("Invite.Sent").replace("%name%", invited.getName()));
									invited.sendMessage(MessagesHandler.convert("Invite.Request").replace("%name%", player.getName()).replace("%city%", city));
								} else {
									player.sendMessage(MessagesHandler.convert("Invite.InCity").replace("%name%", invited.getName()));
								}
							} else {
								player.sendMessage(MessagesHandler.convert("Invite.NotFound").replace("%name%", args[1]));
							}
						} else {
							player.sendMessage(MessagesHandler.convert("Invite.NotFound").replace("%name%", args[1]));
						}
					} else {
						player.sendMessage(MessagesHandler.convert("Invite.InNoCity"));
					}
				}
			} else {
				player.sendMessage(MessagesHandler.convert("Invite.Usage"));
			}
		} else {
			cs.sendMessage(MessagesHandler.convert("NoPlayer"));
		}
	}

}
