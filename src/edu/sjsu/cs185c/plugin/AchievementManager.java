package edu.sjsu.cs185c.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AchievementManager {
	static Plugin plugin;
	static int count=0;
	private static HashMap<Integer,Achievement> achievements = new HashMap<Integer,Achievement>();
	private static HashMap<String, List<AchievementStatus>> player_ach = new HashMap<String, List<AchievementStatus>>();
	AchievementManager(Plugin p){
		plugin = p;
	}
	
	
	public static void addAchtoPlayer(int id, String name){
		AchievementStatus as = new AchievementStatus();
		as.setAch_id(id);
		if(player_ach.get(name)!= null){
			System.out.println("Player already has list, just add this achiev.: ");
			player_ach.get(name).add(as);
		}
		else{
			List<AchievementStatus> my_list= new ArrayList<AchievementStatus>();
			my_list.add(as);
			System.out.println("Creating new AchievementStatus list for "+name+ " with the ach id: "+id);
			player_ach.put(name, my_list);
		}
	}
	
	public static HashMap<Integer,Achievement> getAllAchievements(){
		return achievements;
	}
	public static HashMap<String,List<AchievementStatus>> getAllAchievementsStatus(){
		return player_ach;
	}
	
	public static void setPlayerAchList(String name, List<AchievementStatus> list){
		player_ach.put(name, list);
	}
	
	public static void removeAchievement(int id){
		achievements.remove(id);
	}
	
	public static Achievement addAchievement(Achievement ach){
		Player[] players_online = Bukkit.getOnlinePlayers();
		if(players_online.length== 0) System.out.println("NO PLAYERS ONLINE");
		else{
			for(Player p: players_online){
				AchievementStatus as = new AchievementStatus();
				as.setAch_id(count);
				addAchtoPlayer(count, p.getName());
			}
		}
		return achievements.put(count++,ach);
		
	}
	public static Achievement setAchievement(int id,Achievement ach){
		return achievements.put(id,ach);
		
	}
	
	public static void loadAchivementstoPlayers(){
		for(Player p: plugin.getServer().getOnlinePlayers()){
			List<AchievementStatus> my_list = new ArrayList<AchievementStatus>();
			for(Integer key: achievements.keySet()){
				AchievementStatus as = new AchievementStatus();
				as.setAch_id(key);
				my_list.add(as);
			}
			
			player_ach.put(p.getDisplayName(), my_list);
		}
	}
	public static void onPlayerLogin(Player p){
		List<AchievementStatus> player_achlist= new ArrayList<AchievementStatus>();
		player_achlist = player_ach.get(p.getDisplayName());
		Set<Integer> ach_have = new HashSet<Integer>();
		if(player_achlist != null ){
			for(AchievementStatus as:player_achlist){
				System.out.println("Player has number "+as.getAch_id());
				ach_have.add(as.getAch_id());
			}
		}
		for(Integer id:achievements.keySet()){
			System.out.println("Checking if player has ach id: "+id);
			if(!ach_have.contains(id)){
				addAchtoPlayer(id,p.getDisplayName());
				System.out.println("Adding number "+id+ "to "+p.getDisplayName());
			}
		}	
		
	}
	



	public static void finishAchievement(Player p, int ids) {
		String name = p.getName();
		Achievement ach = achievements.get(ids);
		if(ach.getReward_type() == RewardType.MONEY){
			MoneyManager.addBalance(name, ach.getReward_amount());
		}else if(ach.getReward_type() == RewardType.KARMA){
			Double amount = ach.getReward_amount();
			Integer int_amount =amount.intValue();
			KarmaManager.addKarma(name, int_amount);
		}
		p.sendMessage(ChatColor.YELLOW+"Achievement number "+ids+" completed! You received "+ach.getReward_amount()+" "+ach.getReward_type().toString());
		
	}

	

}
