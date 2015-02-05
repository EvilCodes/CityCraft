package com.evilcodes.citycraft.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.CityAPI;
import com.evilcodes.citycraft.handlers.LogHandler;
import com.evilcodes.citycraft.handlers.MessagesHandler;

public class ListCMD {
	
	public static ArrayList<String> citynames = new ArrayList<String>();
	public static ArrayList<String> citytags = new ArrayList<String>();
	public static ArrayList<Integer> citymembers = new ArrayList<Integer>();
	
	public static void execute(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs.hasPermission("city.list")) {
				final String query =  "SELECT * FROM `" + CityCraft.dbprefix + "cities"  + "`";
				ResultSet res = CityCraft.core.select(query);
			    if (res != null) {
				      try {
				        while (res.next()) {
				        	final String cityname = res.getString("cityname");
				        	citynames.add(cityname);
				        	final String citytag = res.getString("citytag");
				        	citytags.add(citytag);
				        	final String[] citymemberarray = CityAPI.getMemberArray(cityname);
				        	int citymemberint = citymemberarray.length;
				        	if (citymemberarray.length == 0 || citymemberarray == null) {
				        		citymemberint = 0;
				        	}
				        	citymembers.add(citymemberint);
				        }
				        cs.sendMessage(MessagesHandler.convert("List.Header"));
				        cs.sendMessage("");
				        for (int i = 0; ; i++) {
				        	final String cityname = citynames.get(i);
				        	final String citytag = citytags.get(i);
				        	final int members = citymembers.get(i);
				        	cs.sendMessage(MessagesHandler.convert("List.Index").replace("%cityname%", cityname).replace("%citytag%", citytag).replace("%members%", members + ""));
				        	if (i == citynames.size() - 1) {
				        		break;
				        	}
				        }
				        cs.sendMessage("");
				        cs.sendMessage(MessagesHandler.convert("List.Footer"));
				        citynames.clear();
				        citytags.clear();
				        citymembers.clear();
				        
				      } catch (SQLException ex) {
			        	  LogHandler.err("Could not reach MySQLDatabase!");
			        	  LogHandler.err(String.format("An Error occurred: %s", new Object[] { Integer.valueOf(ex.getErrorCode()) }));
			        	  LogHandler.stackerr(ex);
				      }
			    } else {
			    	cs.sendMessage("Could not reach MySQL Database :(");
			    }
		} else {
			cs.sendMessage(MessagesHandler.noPermission("city.list"));
		}
	}

}
