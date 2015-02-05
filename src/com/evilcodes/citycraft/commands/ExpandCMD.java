package com.evilcodes.citycraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.handlers.MessagesHandler;

public class ExpandCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			final Player player = (Player) cs;
			player.sendMessage("§cNot implemented yet!");
		} else {
			cs.sendMessage(MessagesHandler.convert("NoPlayer"));
		}
	}

}
