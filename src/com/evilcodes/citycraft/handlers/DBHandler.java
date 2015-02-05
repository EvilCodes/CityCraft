package com.evilcodes.citycraft.handlers;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.bukkit.command.CommandSender;

import com.evilcodes.citycraft.CityCraft;
import com.evilcodes.citycraft.api.PlayerAPI;
import com.evilcodes.citycraft.utils.MySQLCore;

public class DBHandler {

	//incity getcity boughtslots owner members rank
	
	public static boolean incity(String playername) {
		if (PlayerAPI.isInCity(playername)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getcity(String playername) {
		final String ifquery = "SELECT * FROM `" + CityCraft.dbprefix + "players" + "` WHERE `playername` = '" + playername + "';";
		String cityname = "";
		if (MySQLCore.mysqlExists(ifquery)) {
			final String query = "SELECT * FROM `" + CityCraft.dbprefix + "players" + "` WHERE `playername` = '" + playername + "';";
			ResultSet res = CityCraft.core.select(query);
		    if (res != null) {
			      try {
			        while (res.next()) {
			          try {
			        	  if (res.getString("cityname") != null || res.getString("cityname") != "" || res.getString("cityname") != " ") {
			        		  cityname = res.getString("cityname");
			        	  }
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
		return cityname;
	}
	
	public static int boughtslots(String cityname) {
		final String ifquery = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
		int boughtslots = 0;
		if (MySQLCore.mysqlExists(ifquery)) {
			final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "'";
			ResultSet res = CityCraft.core.select(query);
		    if (res != null) {
			      try {
			        while (res.next()) {
			          try {
			        	  boughtslots = res.getInt("boughtslots");
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
		return boughtslots;
	}
	
	public static String owner(String cityname) {
		final String ifquery = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
		String owner = "Niemand";
		if (MySQLCore.mysqlExists(ifquery)) {
			final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "'";
			ResultSet res = CityCraft.core.select(query);
		    if (res != null) {
			      try {
			        while (res.next()) {
			          try {
			        	  owner = res.getString("owner");
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
		return owner;
	}
	
	public static String[] getMemberArray(String cityname) {
		final String ifquery = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
		String[] members = null;
		if (MySQLCore.mysqlExists(ifquery)) {
			final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "'";
			ResultSet res = CityCraft.core.select(query);
		    if (res != null) {
			      try {
			        while (res.next()) {
			          try {
			        	  final String memberlist = res.getString("members").replace(", ", ":");
			        	  members = memberlist.split(":");
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
		return members;
	}
	
	public static String getMemberString(String cityname) {
		String memberlist = null;
		final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
		final ResultSet res = CityCraft.core.select(query);
		if (res != null) {
			try {
				while (res.next()) {
					memberlist = res.getString("members");
					return memberlist;
				}
		      } catch (SQLException ex) {
		    	  LogHandler.err(String.format("An Error occurred: %s", new Object[] { Integer.valueOf(ex.getErrorCode()) }));
		    	  LogHandler.stackerr(ex);
		      }
		}
		return memberlist;
	}
	
	public static String getMemberString1(String cityname) {
		//final String ifquery = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
		String members = "";
		//if (MySQLCore.mysqlExists(ifquery)) {
			final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "'";
			ResultSet res = CityCraft.core.select(query);
		    if (res != null) {
			      try {
			        while (res.next()) {
			          try {
			        	  final String memberlist = res.getString("members");
			        	  System.out.println(memberlist);
			        	  return memberlist;
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
		//}
		return members;
	}
	
	public static String rank(String playername) {
		final String ifquery = "SELECT * FROM `" + CityCraft.dbprefix + "players" + "` WHERE `playername` = '" + playername + "';";
		String rank = "";
		if (MySQLCore.mysqlExists(ifquery)) {
			final String query = "SELECT * FROM `" + CityCraft.dbprefix + "players" + "` WHERE `playername` = '" + playername + "';";
			ResultSet res = CityCraft.core.select(query);
		    if (res != null) {
			      try {
			        while (res.next()) {
			          try {
			        	  rank = res.getString("rank");
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
		return rank;
	}

	public static void cityInfo(String cityname, CommandSender cs) {
		  String StatsMessage = "";
		  try {
				Scanner inputFile = new Scanner(CityCraft.infoFile);
			      while (inputFile.hasNextLine()) {
			    	  StatsMessage = inputFile.nextLine();
			    	  StatsMessage = modifymsg(StatsMessage, cs);
				      cs.sendMessage(StatsMessage);
			      }
		  } catch (FileNotFoundException e) {
			  LogHandler.err("FileNotFound: cityinfo.txt! ");
			  LogHandler.stackerr(e);
		  }
	}

	  private static String modifymsg(String line, CommandSender player) {
		  	final String cityname = DBHandler.getcity(player.getName());
			final String query = "SELECT * FROM `" + CityCraft.dbprefix + "cities" + "` WHERE `cityname` = '" + cityname + "';";
			ResultSet res = CityCraft.core.select(query);
		    if (res != null) {
			      try {
			        while (res.next()) {
			          try {
			        	  final String tag = res.getString("citytag");
			        	  final int basex = res.getInt("basex");
			        	  final int basey = res.getInt("basey");
			        	  final int basez = res.getInt("basez");
			        	  final String base = basex + " " + basey + " " + basez;
			        	  final String owner = res.getString("owner");
			        	  final String members = res.getString("members");
			        	  final int boutghtslots = res.getInt("boughtslots");
			        	  //name, tag, owner, baseloc, members, boughtslots
			    		  String[] Variables = { "&", "%name%", "%tag%", "%owner%", "%baseloc%", "%members%", "%boughtslots%" };
			    		  String[] Modified = { "�", cityname, tag, owner, base, members, boutghtslots + "" };    
			    	    for (int x = 0; x < Variables.length; x++) {
			    	      CharSequence cChk = null;

			    	      cChk = Variables[x];

			    	      if (line.contains(cChk)) {
			    	        line = line.replace(cChk, Modified[x].toString());
			    	      }

			    	    }
			    	    return line;
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
			return line;
	  }
	
}
