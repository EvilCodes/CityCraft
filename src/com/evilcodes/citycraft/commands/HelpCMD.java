package com.evilcodes.citycraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.evilcodes.citycraft.handlers.MessagesHandler;

public class HelpCMD {

	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 0 || args.length == 1) {
			cs.sendMessage(MessagesHandler.convert("Help.Header"));
				cs.sendMessage("§b/city help spieler §6Sehe alle Befehle für Spieler");
				cs.sendMessage("§b/city help buergermeister §6Sehe alle Befehle für den Bürgermeister");
				cs.sendMessage("§b/city help admin §6Sehe alle Befehle für Admins");
				cs.sendMessage("§b/city help info §6Sehe alle Infos über das Plugin");
			cs.sendMessage(MessagesHandler.convert("Help.Footer"));
		}
		
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("spieler")) {
				cs.sendMessage(MessagesHandler.convert("Help.Header"));
					cs.sendMessage("§bcreate §6Erstelle eine Stadt");
					cs.sendMessage("§bjoin §6Trete einer Stadt bei");
					cs.sendMessage("§bleave §6Verlasse eine Stadt");
					cs.sendMessage("§binvite §6Lade einen Freund in deine Stadt ein");
					cs.sendMessage("§blist §6Erstelle eine Stadt");
					cs.sendMessage("§bhome §6Teleportiere dich zu deiner Stadt!");
				cs.sendMessage(MessagesHandler.convert("Help.Footer"));
			}
			
			if (args[1].equalsIgnoreCase("buergermeister")) {
				cs.sendMessage(MessagesHandler.convert("Help.Header"));
					cs.sendMessage("§bbroadcast §6Schreibe eine Nachricht an alle Mitglieder deiner Stadt");
					cs.sendMessage("§bexpand §6Vergrössere deine Stadt");
					cs.sendMessage("§bgiverank §6Gebe einem Spieler in deiner Stadt einen Rang");
					cs.sendMessage("§bkick §6Kicke einen Spieler aus deiner Stadt");
				cs.sendMessage(MessagesHandler.convert("Help.Footer"));
			}
			
			if (args[1].equalsIgnoreCase("admin")) {
				cs.sendMessage(MessagesHandler.convert("Help.Header"));
					cs.sendMessage("§bdel §6Lösche eine Stadt");
					cs.sendMessage("§breload §6Lade Konfigurationseinstellungen neu");
				cs.sendMessage(MessagesHandler.convert("Help.Footer"));
			}
			
			if (args[1].equalsIgnoreCase("info")) {
				cs.sendMessage(MessagesHandler.convert("Help.InfoHeader"));
					cs.sendMessage("§6§lCityCraft §r§3[§6privat§3]");
					cs.sendMessage("§3Plugin erstellt von §6§lEvilCodes §3[§6evil_dr3am§3]");
					cs.sendMessage("§3Website: §o http://evilcodes.tk/");
					cs.sendMessage("§3Kontaktemail: §o evilcodes.dev@gmail.com");
				cs.sendMessage(MessagesHandler.convert("Help.InfoFooter"));
			}
		}
	}

}
