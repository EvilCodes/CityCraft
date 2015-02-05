package com.evilcodes.citycraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.utils.WorldGuard;

public class CheckRangeCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			final Player player = (Player) cs;
				if (WorldGuard.rangeOkay(player.getLocation())) {
					player.sendMessage("True");
				} else {
					player.sendMessage("False");
				}
		}
		
	}

}
