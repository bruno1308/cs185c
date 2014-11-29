package edu.sjsu.cs185c.plugin;

import java.util.HashMap;

public class KarmaManager {
	private static HashMap<String, Integer> karmas = new HashMap<String, Integer>();
	static Plugin plugin;
	
	KarmaManager(Plugin p){
		plugin = p;
	}
	
	public static void setKarma(String name, Integer bal){
		karmas.put(name, bal);
	}
	
	public static int getKarma(String name){
		return karmas.get(name);
	}
	
	public static boolean hasKarma(String name){
		return karmas.containsKey(name);
	}
	
	public static HashMap<String,Integer> getAllKarmas(){
		return karmas;
	}
	
	public static Plugin getPlugin(){
		return plugin;
	}
	
	public static void addKarma(String name, Integer amount){
		karmas.put(name,getKarma(name)+amount);
	}

}
