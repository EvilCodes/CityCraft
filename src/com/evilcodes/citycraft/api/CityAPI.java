package com.evilcodes.citycraft.api;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.handlers.LogHandler;
import com.evilcodes.citycraft.utils.MySQLCore;

public class CityAPI {
	
	/**
	 * 
	 * @param city The Cityname
	 * @return String (short) 
	 */
	public static String getTag(String city) {
		final String ifquery = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + city + "';";
		String tag = "";
		if (MySQLCore.mysqlExists(ifquery)) {
			final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + city + "'";
			ResultSet res = CityCraft.core.select(query);
		    if (res != null) {
			      try {
			        while (res.next()) {
			          try {
			        	  tag = res.getString("citytag");
			          } catch (Exception ex) {
			        	  LogHandler.err("Could not reach MySQLDatabase!");
			        	  LogHandler.stackerr(ex);
			          }
			        }
			      } catch (SQLException ex) {
			    	  LogHandler.err(String.format("An Error occurred: %s", new Object[] { Integer.valueOf(ex.getErrorCode()) }));
			    	  LogHandler.stackerr(ex);
			      }
		    }
		}
		return tag;
	}
	
	/**
	 * Broadcasts a message to all members of a specified city
	 * @param city The Cityname
	 * @param msg The Broadcastmessage
	 */
	public static void broadcast(String city, String msg) {
		final String[] members = DBHandler.getMemberArray(city);
		for (String memberstring : members) {
			final OfflinePlayer offmember = Bukkit.getOfflinePlayer(memberstring);
			if (offmember.hasPlayedBefore()) {
				if (offmember.isOnline()) {
					final Player member = Bukkit.getPlayerExact(memberstring);
					member.sendMessage(msg.replace("&", "§"));
				}
			}
		}
	}
	
	public static Location getBaseLoc(String cityname) {
		final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
		ResultSet res = CityCraft.core.select(query);
		Location loc = null;
	    if (res != null) {
		      try {
		        while (res.next()) {
		          try {
		        	  final int x = res.getInt("basex");
		        	  final int y = res.getInt("basey");
		        	  final int z = res.getInt("basez");
		        	  final World world = Bukkit.getWorld(res.getString("worldname"));
		        	  loc = new Location(world, x, y, z);
		          } catch (Exception ex) {
		        	  LogHandler.err("Could not reach MySQLDatabase!");
		        	  LogHandler.stackerr(ex);
		          }
		        }
		      }
		      catch (SQLException ex)
		      {
	        	  LogHandler.err("Could not reach MySQLDatabase!");
	        	  LogHandler.err(String.format("An Error occurred: %s", new Object[] { Integer.valueOf(ex.getErrorCode()) }));
	        	  LogHandler.stackerr(ex);
		      }
		    }
		return loc;
	}
	
	/**
	 * Checks whether a city exists
	 * @param city The Cityname
	 * @return Boolean
	 */
	public static Boolean cityExists(String city) {
		final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + city + "';";
		if (MySQLCore.mysqlExists(query)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Get the owner of a city
	 * @param city The Cityname
	 * @return String (Playername)
	 */
	public static String getOwner(String city) {
		final String owner = DBHandler.owner(city);
		return owner;
	}
	
	/**
	 * Get the Stringarray of a memberlist from a specified city
	 * @param city The Cityname
	 * @return String[] (members)
	 */
	public static String[] getMemberArray(String city) {
		return DBHandler.getMemberArray(city);
	}

}
