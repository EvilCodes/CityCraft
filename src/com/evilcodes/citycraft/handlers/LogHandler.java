package com.evilcodes.citycraft.handlers;

import org.bukkit.Bukkit;

import com.evilcodes.citycraft.CityCraft;

public class LogHandler {

	public static void log(String msg) {
		Bukkit.getLogger().info(CityCraft.prefix + msg);
	}
	
	public static void err(String msg) {
		Bukkit.getLogger().severe(CityCraft.prefix + msg);
	}
	
	public static void stackerr(Exception msg) {
		if (CityCraft.debug) {
			Bukkit.getLogger().severe(CityCraft.prefix + msg);
		}
	}
}
