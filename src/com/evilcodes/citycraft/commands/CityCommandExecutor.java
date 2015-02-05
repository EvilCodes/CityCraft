package com.evilcodes.citycraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.evilcodes.citycraft.handlers.FilesHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;
import com.evilcodes.citycraft.handlers.PermissionsHandler;

public class CityCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		
		if (args.length == 0) {
			HelpCMD.execute(cs, cmd, label, args);
		} 
		
		if (args.length >= 1) {
			
			//User commands
			
			if (args[0].equalsIgnoreCase("info")) {
				InfoCMD.execute(cs, cmd, label, args);
			} else if (args[0].equalsIgnoreCase("help")) {
				HelpCMD.execute(cs, cmd, label, args);
			} else if (args[0].equalsIgnoreCase("range")) {
				CheckRangeCMD.execute(cs, cmd, label, args);
			} else if (args[0].equalsIgnoreCase("create")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.create")) {
					CreateCMD.execute(cs, cmd, label, args);
				}
			} else if (args[0].equalsIgnoreCase("join")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.join")) {
					JoinCMD.execute(cs, cmd, label, args);
				}
			} else if (args[0].equalsIgnoreCase("list")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.list")) {
					ListCMD.execute(cs, cmd, label, args);
				}
			} else if (args[0].equalsIgnoreCase("home")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.home")) {
					HomeCMD.execute(cs, cmd, label, args);
				}
			} else if (args[0].equalsIgnoreCase("leave")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.leave")) {
					LeaveCMD.execute(cs, cmd, label, args);
				}
			} else if (args[0].equalsIgnoreCase("invite")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.invite")) {
					InviteCMD.execute(cs, cmd, label, args);
				}
			//Bürgermeister Commands
			} else if (args[0].equalsIgnoreCase("broadcast")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.broadcast")) {
					BroadcastCMD.execute(cs, cmd, label, args);
				}
			} else if (args[0].equalsIgnoreCase("kick")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.kick")) {
					KickCMD.execute(cs, cmd, label, args);
				}
			} else if (args[0].equalsIgnoreCase("expand")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.expand")) {
					ExpandCMD.execute(cs, cmd, label, args);
				}
			} else if (args[0].equalsIgnoreCase("giverank")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.giverank")) {
					GiveRankCMD.execute(cs, cmd, label, args);
				}
			//OP Commands
			} else if (args[0].equalsIgnoreCase("del")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.del")) {
					DeleteCMD.execute(cs, cmd, label, args);
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (PermissionsHandler.hasPermission(cs, "citycraft.reload")) {
					//FilesHandler.SaveFiles();
					FilesHandler.LoadFiles();
					cs.sendMessage(MessagesHandler.convert("Reloaded"));
				}
			} else {
				HelpCMD.execute(cs, cmd, label, args);
			}
			
		}
		
		//Langweiliges return Statement!
		return true;
	}	
}
