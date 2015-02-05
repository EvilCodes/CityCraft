package com.evilcodes.citycraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.evilcodes.citycraft.api.CityAPI;
import com.evilcodes.citycraft.api.PlayerAPI;

public class ChatEvent implements Listener {
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		final String message = e.getMessage();
		final String city = PlayerAPI.getCity(e.getPlayer().getName());
		final String tag = CityAPI.getTag(city);
		final String rank = PlayerAPI.getRank(e.getPlayer().getName());
		final String pprefix = "&f";
		String prefix = "&6[&a%tag%&6] &6[&a%rank%&6] &6<%pprefix%%name%&6>";
		prefix = prefix.replace("&", "§").replace("%tag%", tag).replace("%rank%", rank).replace("%pprefix%", pprefix).replace("%name%", e.getPlayer().getName()).replace("&", "§");
		//final String format = prefix + message;
		e.setFormat(prefix + " §f" + message);
		e.setMessage(message);
	}
	
}
