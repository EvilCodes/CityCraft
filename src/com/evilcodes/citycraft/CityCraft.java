package com.evilcodes.citycraft;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.evilcodes.citycraft.commands.CityCommandExecutor;
import com.evilcodes.citycraft.handlers.FilesHandler;
import com.evilcodes.citycraft.handlers.LogHandler;
import com.evilcodes.citycraft.listeners.ChatEvent;
import com.evilcodes.citycraft.listeners.JoinEvent;
import com.evilcodes.citycraft.utils.DBCore;
import com.evilcodes.citycraft.utils.MySQLCore;


public class CityCraft extends JavaPlugin {
	
	public static CityCraft instance;
	public static String prefix = "[CityCraft] ";
	public static DBCore core;
	public static boolean debug = false;
	public static File configFile;
	public static FileConfiguration config;
	public static File messagesFile;
	public static FileConfiguration messages;
	public static File infoFile;
	
	public static String dbhost;
	public static String dbuser;
	public static String dbpass;
	public static String dbname;
	public static String dbprefix;
	
	//TODO ChatNachricht bei Kick
	//TODO Invite mit falschem Namen -> Error
	//TODO Join request beim Bürgermeister
	//TODO Broadcast
	//TODO List
	//TODO Home
	
	public void onEnable() {
		instance = this;
		LogHandler.log("Starting!");
		
		//TODO Check whether WorldGuard & WorldEdit are loaded, if not, disable the plugin!
		
		FilesHandler.CheckForConfigs();
		//Load Commands & Listeners
		setDB();
		CityCraft.core = new MySQLCore(CityCraft.dbhost, CityCraft.dbname, CityCraft.dbuser, CityCraft.dbpass);
		if (!core.checkConnection()) {
			LogHandler.err("Could not connect to Database, shutting down!");
			LogHandler.err("Please edit your config.yml (plugins/CityCraft/config.yml) and reload your server!");
			Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("CityCraft"));
			return;
		}
		CheckTables();
		
		this.getCommand("city").setExecutor(new CityCommandExecutor());
		Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
	}
	
	public void onDisable() {
		
	}
	
	public void setDB() {
		CityCraft.dbhost = config.getString("Database.Host");
		CityCraft.dbuser = config.getString("Database.Username");
		CityCraft.dbpass = config.getString("Database.Password");
		CityCraft.dbname = config.getString("Database.Databasename");
		CityCraft.dbprefix = config.getString("Database.Tableprefix");
		
		CityCraft.debug = config.getBoolean("Debug");
	}
		
	public void CheckTables() {	
			if (!CityCraft.core.existsTable(dbprefix + "cities").booleanValue()) {
				LogHandler.log("Databasetable " + "cities" + " does not exist!");
				LogHandler.log("Creating " + "cities" + " in " + CityCraft.dbname);
					//Tablestruktur: "ID,cityname,citytag,basex,basey,basez,worldname,owner,members,boughtslots"
					final String query = "CREATE TABLE IF NOT EXISTS `" + CityCraft.dbprefix + "cities" + "` (`ID` int(10) NOT NULL AUTO_INCREMENT, `cityname` varchar(42) NOT NULL, `citytag` varchar(42) NOT NULL, `basex` int(8) NOT NULL DEFAULT '0', `basey` int(8) NOT NULL DEFAULT '0', `basez` int(8) NOT NULL DEFAULT '0', `worldname` varchar(42) NOT NULL, `owner` varchar(42) NOT NULL, `members` varchar(42) NOT NULL, `boughtslots` int(8) NOT NULL DEFAULT '0', PRIMARY KEY (`ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Cities in CityCraft' AUTO_INCREMENT=1 ;";
					CityCraft.core.execute(query);
			}
			if (!CityCraft.core.existsTable(dbprefix + "players").booleanValue()) {
				LogHandler.log("Databasetable " + "players" + " does not exist!");
				LogHandler.log("Creating " + "players" + " in " + CityCraft.dbname);
					//Tablestruktur: "ID,playername,cityname,rank"
					final String query = "CREATE TABLE IF NOT EXISTS `" + CityCraft.dbprefix + "players" + "` (`ID` int(10) NOT NULL AUTO_INCREMENT, `playername` varchar(42) NOT NULL, `cityname` varchar(42) NOT NULL, `rank` varchar(42) NOT NULL, PRIMARY KEY (`ID`)) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Players in CityCraft' AUTO_INCREMENT=1 ;";
					CityCraft.core.execute(query);
				//More ifs, if there are more tables in the arraylist
			}
	  }

}
