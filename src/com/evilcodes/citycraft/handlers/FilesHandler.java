package com.evilcodes.citycraft.handlers;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import com.evilcodes.citycraft.CityCraft;

public class FilesHandler {
		
	public static void CheckForConfigs() {
		CityCraft.configFile = new File(CityCraft.instance.getDataFolder(), "config.yml");
		CityCraft.messagesFile = new File(CityCraft.instance.getDataFolder(), "messages.yml");
		CityCraft.infoFile = new File(CityCraft.instance.getDataFolder(), "cityinfo.txt");
		
		if (!CityCraft.configFile.exists()) {
			LogHandler.log("No Configfile found, creating...");
			setConfigs();
		}
		if (!CityCraft.messagesFile.exists()) {
			LogHandler.log("No Messagesfile found, creating...");
			setConfigs();
		}
		if (!CityCraft.infoFile.exists()) {
			LogHandler.log("No Infofile found, creating...");
			setConfigs();
		}
		LoadFiles();
	}
	
	public static void SaveFiles() {
	    try {
	    	CityCraft.config.save(CityCraft.configFile);
	    	CityCraft.messages.save(CityCraft.messagesFile);
	    }
	    catch (Exception e) {
	    	LogHandler.err("Could not save configfiles!");
	    	LogHandler.stackerr(e);
	    }
	}
	
	public static void LoadFiles() {
	   	try {
	   	   if (CityCraft.config == null) {
	   	    CityCraft.configFile = new File(CityCraft.instance.getDataFolder(), "config.yml");
	   	    }
	   		CityCraft.config = YamlConfiguration.loadConfiguration(CityCraft.configFile);
	   		
		   	if (CityCraft.messages == null) {
			    CityCraft.messagesFile = new File(CityCraft.instance.getDataFolder(), "messages.yml");
			}
	   		CityCraft.messages = YamlConfiguration.loadConfiguration(CityCraft.messagesFile);
		} catch (Exception e) {
	    	LogHandler.err("Could not load configfiles!");
	    	LogHandler.stackerr(e);
		}
	}
		
	public static void setConfigs() {

		CityCraft.config = YamlConfiguration.loadConfiguration(CityCraft.configFile);
	    if (!CityCraft.configFile.exists()) {      
	         CityCraft.instance.saveResource("config.yml", false);
	     }
	    
		CityCraft.messages = YamlConfiguration.loadConfiguration(CityCraft.messagesFile);
	    if (!CityCraft.messagesFile.exists()) {      
	         CityCraft.instance.saveResource("messages.yml", false);
	     }
	    
	    if (!CityCraft.infoFile.exists()) {      
	         CityCraft.instance.saveResource("cityinfo.txt", false);
	     }
	}
		
	
}

