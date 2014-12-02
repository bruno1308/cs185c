package edu.sjsu.cs185c.plugin;

import java.util.HashMap;

import org.bukkit.Material;

public class ProfessionManager {
	private static HashMap<String, Profession> professions = new HashMap<String, Profession>();
	static Plugin plugin;
	
	public static HashMap<String, Profession> getAllProfessions(){
		return professions;
	}
	
	public static Profession getProfessionByName(String name){
		return professions.get(name);
	}
	public static Profession setProfession(String name, Profession p){
		return professions.put(name, p);
	}
	public static boolean hasProfession(String name){
		return professions.containsKey(name);
	}
}