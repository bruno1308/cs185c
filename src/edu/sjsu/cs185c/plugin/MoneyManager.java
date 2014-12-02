package edu.sjsu.cs185c.plugin;

import java.util.HashMap;

import org.bukkit.Material;

public class MoneyManager {
	private static HashMap<String, Double> balances = new HashMap<String, Double>();
	static Plugin plugin;
	
	MoneyManager(Plugin p){
		plugin = p;
	}
	
	public static void setBalance(String name, Double bal){
		balances.put(name, bal);
	}
	
	public static Double getBalance(String name){
		return balances.get(name);
	}
	
	public static boolean hasAccount(String name){
		return balances.containsKey(name);
	}
	
	public static HashMap<String,Double> getAllBalances(){
		return balances;
	}
	
	public static Plugin getPlugin(){
		return plugin;
	}
	
	public static void addBalance(String name, Double amount){
		balances.put(name, getBalance(name)+amount);
	}
}
