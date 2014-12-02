package edu.sjsu.cs185c.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_7_R3.AchievementList;

public class ServerPersistence {
	
	private static Plugin p = MoneyManager.getPlugin();
	
	public static void saveBalances(){
		for(String name :MoneyManager.getAllBalances().keySet()){
			p.getConfig().set("balance."+name, MoneyManager.getAllBalances().get(name));	
		}
		p.getConfig().set("balance.$server", MoneyManager.getAllBalances().get("$server"));
		p.saveConfig();
	}
	
	public static void loadBalances(){
		if(!p.getConfig().contains("balance")) return;
		for(String name :p.getConfig().getConfigurationSection("balance").getKeys(false)){
			MoneyManager.setBalance(name, p.getConfig().getDouble("balance."+name));
		}
		MoneyManager.setBalance("$server", p.getConfig().getDouble("balance.$server"));
	}
	public static void saveKarmas(){
		for(String name :KarmaManager.getAllKarmas().keySet()){
			p.getConfig().set("karma."+name, KarmaManager.getAllKarmas().get(name));		
		}
		p.saveConfig();
	}
	
	public static void loadKarmas(){
		if(!p.getConfig().contains("karma")) return;
		for(String name :p.getConfig().getConfigurationSection("karma").getKeys(false)){
			//p.getConfig().set("karma."+name, KarmaManager.getAllKarmas().get(name));
			KarmaManager.setKarma(name, p.getConfig().getInt("karma."+name));
		}
	}
	
	public static void saveAchievements(){
		HashMap<Integer,Achievement> my_list = AchievementManager.getAllAchievements();
		for(int id : my_list.keySet()){
			Achievement ach = my_list.get(id);
			p.getConfig().set("achievement."+id+".type",ach.getType().toString());	
			if(ach.getType() == Type.DESTROY_MONSTER ||ach.getType() == Type.DESTROY_BLOCK || ach.getType() == Type.DESTROY_PLAYER ){
				p.getConfig().set("achievement."+id+".target", ach.getEntity_target().toUpperCase());
				p.getConfig().set("achievement."+id+".amount", ach.getAmount());
				p.getConfig().set("achievement."+id+".reward_type", ach.getReward_type().toString());
				p.getConfig().set("achievement."+id+".reward_amount", ach.getReward_amount());
			}else if(ach.getType()==Type.VISIT){
				p.getConfig().set("achievement."+id+".x", ach.getX());
				p.getConfig().set("achievement."+id+".y", ach.getY());
				p.getConfig().set("achievement."+id+".z", ach.getZ());
				p.getConfig().set("achievement."+id+".reward_type", ach.getReward_type().toString());
				p.getConfig().set("achievement."+id+".reward_amount", ach.getReward_amount());
			}else if(ach.getType()==Type.COLLECT){
				
			}
		}
		p.getConfig().set("achievement_count", AchievementManager.count);
		p.saveConfig();
	}
	public static void loadAchievements(){
		if(!p.getConfig().contains("achievement")) return;
	
		for(String id :p.getConfig().getConfigurationSection("achievement").getKeys(false)){
			int id_integer = Integer.parseInt(id);
			Type type = Type.valueOf(p.getConfig().getString("achievement."+id+".type").toUpperCase());
			if(type == Type.DESTROY_BLOCK || type == Type.DESTROY_MONSTER || type == Type.DESTROY_PLAYER){
				String dt = p.getConfig().getString("achievement."+id+".target").toUpperCase();
				int amount = p.getConfig().getInt("achievement."+id+".amount");
				RewardType rt = RewardType.valueOf(p.getConfig().getString("achievement."+id+".reward_type").toUpperCase());
				double rw_amount = p.getConfig().getDouble("achievement."+id+".reward_amount");
				Achievement ach = new Achievement(type, dt, amount, rt, rw_amount);
				AchievementManager.setAchievement(id_integer, ach);
				
			}else if(type==Type.VISIT){
				Double x = p.getConfig().getDouble("achievement."+id+".x");
				Double y = p.getConfig().getDouble("achievement."+id+".y");
				Double z = p.getConfig().getDouble("achievement."+id+".z");
				RewardType rt = RewardType.valueOf(p.getConfig().getString("achievement."+id+".reward_type").toUpperCase());
				double rw_amount = p.getConfig().getDouble("achievement."+id+".reward_amount");
				Achievement ach = new Achievement(type, x, y, z, rt, rw_amount);
				AchievementManager.setAchievement(id_integer, ach);
				
			}else if(type==Type.COLLECT){
				
			}
		}
		int count = p.getConfig().getInt("achievement_count");
		AchievementManager.count=count;
	}
	
	public static void saveAchievementsStatus(){
		HashMap<String,List<AchievementStatus>> my_list = AchievementManager.getAllAchievementsStatus();
		for(String player : my_list.keySet()){
			List<AchievementStatus> status_list = my_list.get(player);
			for(AchievementStatus e:status_list){
				int ach_id = e.getAch_id();
				int progress = e.getProgress();
				boolean done = e.getDone();
				p.getConfig().set("achievement_status."+player+"."+ach_id+".progress",progress);
				p.getConfig().set("achievement_status."+player+"."+ach_id+".done",String.valueOf(done));
				
		}
	}
		p.saveConfig();
}

	public static void loadAchievementsStatus(){
		if(!p.getConfig().contains("achievement_status")) return;
	
		for(String player :p.getConfig().getConfigurationSection("achievement_status").getKeys(false)){
			List<AchievementStatus> as_list = new ArrayList<AchievementStatus>();
			for(String ach_id: p.getConfig().getConfigurationSection("achievement_status."+player).getKeys(false)){
				
				AchievementStatus as =new AchievementStatus();
				String is_done = p.getConfig().getString("achievement_status."+player+"."+ach_id+".done");
				as.setAch_id(Integer.parseInt(ach_id));
				as.setDone(Boolean.valueOf(is_done));
				as.setProgress(p.getConfig().getInt("achievement_status."+player+"."+ach_id+".progress"));
				as_list.add(as);
			}
			AchievementManager.setPlayerAchList(player, as_list);
		}
	}
	
	public static void saveProfessions(){
		HashMap<String, Profession> my_list = ProfessionManager.getAllProfessions();
		for(String player : my_list.keySet()){
			//System.out.println("Persisting player: "+player);
			Profession pr = my_list.get(player);
			Double exp = pr.getExperience();
			int level = pr.getLevel();
			Double wage = pr.getWage();
			ProfessionType pt = pr.getPt();
			int drop_rate = pr.getDrop_rate();
			p.getConfig().set("profession."+player+".type",pt.toString().toUpperCase());
			p.getConfig().set("profession."+player+".level",level);
			p.getConfig().set("profession."+player+".exp",exp);
			p.getConfig().set("profession."+player+".wage",wage);
			p.getConfig().set("profession."+player+".drop_rate",drop_rate);
		}
		p.saveConfig();
	}
	
	public static void loadProfessions(){
		if(!p.getConfig().contains("profession")) return;
		for(String name :p.getConfig().getConfigurationSection("profession").getKeys(false)){
			ProfessionType pt;
			
			pt = ProfessionType.valueOf(p.getConfig().getString("profession."+name+".type"));
			Profession prof = new Profession(pt);
			prof.setExperience(p.getConfig().getDouble("profession."+name+".exp"));
			prof.setLevel(p.getConfig().getInt("profession."+name+".level"));
			prof.setWage(p.getConfig().getDouble("profession."+name+".wage"));
			prof.setDrop_rate(p.getConfig().getInt("profession."+name+".drop_rate"));
			prof.setPt(pt);
			ProfessionManager.setProfession(name, prof);
		}
	}
	
	
	public static void loadData(){
		loadKarmas();
		loadBalances();
		loadAchievements();
		loadAchievementsStatus();
		loadProfessions();
		System.out.println("Loading data");
		
	}
	public static void saveData(){
		saveKarmas();
		saveBalances();
		saveAchievements();
		saveAchievementsStatus();
		saveProfessions();
		System.out.println("Saving data");
	}
}
