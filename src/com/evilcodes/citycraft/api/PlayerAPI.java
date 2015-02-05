package com.evilcodes.citycraft.api;

import com.evilcodes.citycraft.handlers.DBHandler;
import com.evilcodes.citycraft.utils.WorldGuard;

public class PlayerAPI {
	
	/**
	 * @param playername The Playername
	 * @return String
	 */
	public static String getCity(String playername) {
		final String city = DBHandler.getcity(playername);
		return city;
	}
	
	/**
	 * @param playername The Playername
	 * @return String
	 */
	public static String getTag(String playername) {
		final String city = DBHandler.getcity(playername);
		final String tag = CityAPI.getTag(city);
		return tag;
	}
	
	/**
	 * @param playername The Playername
	 * @return String
	 */
	public static String getRank(String playername) {
		final String rank = DBHandler.rank(playername);
		return rank;
	}
	
    /**
	* Is Owner of his city
	* Returns Boolean
    *  
	* Checking by connecting to MySQL Table "players" and "cities"
	* @param playername  The playername
	*/
	public static Boolean isOwner(String playername) {
		final String city = DBHandler.getcity(playername);
		final String owner = DBHandler.owner(city);
		if (owner.equalsIgnoreCase(playername) || playername.equalsIgnoreCase(owner)) {
			return true;
		} else {
			return false;
		}
	}
	
    /**
	* Is Owner of another city
	* <p>
	* Returns Boolean
    * <p>
	* Checking by connecting to MySQL Table "players" and "cities"
	* @param playername  The playername
	* @param cityname  The cityname
	*/
	public static Boolean isOwner(String playername, String cityname) {
		final String city = cityname;
		final String owner = DBHandler.owner(city);
		if (owner.equalsIgnoreCase(playername) || playername.equalsIgnoreCase(owner)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether a player is in a specified city
	 * @param playername The Playername
	 * @param city The Cityname
	 * @return Boolean
	 */
	public static Boolean isInCity(String playername, String city) {
		final String cityname = DBHandler.getcity(playername);
		if (cityname.equalsIgnoreCase(city) || city.equalsIgnoreCase(cityname)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether a player is in a city
	 * @param playername The Playername
	 * @return Boolean
	 */
	public static Boolean isInCity(String playername) {
		final String cityname = DBHandler.getcity(playername);
		if (cityname.equalsIgnoreCase("") || cityname.equalsIgnoreCase(" ") || cityname == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Checks whether a player is in the cityregion
	 * <p>
	 * Maybe needed for a protectionchecker and checking whether a player could build there
	 * @param playername The Playername
	 * @return Boolean true, if he is - false, if he isn't
	 */
	public static Boolean isInHisCityRegion(String playername) {
		if (WorldGuard.isInHisCityRegion(playername)) {
			return true;
		} else {
			return false;
		}
	}
	
}
