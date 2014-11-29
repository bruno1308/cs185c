package edu.sjsu.cs185c.plugin;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import edu.sjsu.cs185c.util.MapUtil;
import edu.sjsu.cs185c.util.MathUtil;

public class MyEventHandler {
	public static void onPlayerDeath(PlayerDeathEvent e){
		if(!(e.getEntity().getKiller() instanceof Player))return;
        String killers_name = e.getEntity().getKiller().getName();
        String dead_name = e.getEntity().getName();
        if (KarmaManager.hasKarma(killers_name) && KarmaManager.hasKarma(dead_name)){
        	int killers_karma = KarmaManager.getKarma(killers_name);
        	int dead_karma = KarmaManager.getKarma(dead_name);
        	if(dead_karma<0){
        		if(dead_karma < killers_karma)
        		killers_karma++;
        		else
        		killers_karma--;
        	}else{
        		if(dead_karma < killers_karma)
            		killers_karma--;
            		else
            		killers_karma-=3;
        	}
            
            }
		
	}

	public static void onEntityDeath(EntityDeathEvent event) {
		Entity dead = event.getEntity();
    	Player  p = event.getEntity().getKiller();
    	if(p == null) return;
    	HashMap<Integer,Achievement> my_list = AchievementManager.getAllAchievements();
    	if(my_list!= null){
    		for(int ids:my_list.keySet()){
    			if(my_list.get(ids).getType() == Type.DESTROY_MONSTER ){
    			if(dead.getType() == EntityType.valueOf(my_list.get(ids).getEntity_target())){
    				saveCode(p,ids,my_list);                    		
        		}
    			}else if(my_list.get(ids).getType() == Type.DESTROY_PLAYER && dead instanceof Player){
    				Player dead_player = (Player)dead;
    				if(dead_player.getName() == my_list.get(ids).getEntity_target()){
    					saveCode(p,ids,my_list); 
        				
    				}
    				
    			}
    		}
    		
    	}
		
	}
	
	
	public static void saveCode(Player p,int ids,HashMap<Integer,Achievement> my_list ){
		HashMap<String,List<AchievementStatus>> ach_list = AchievementManager.getAllAchievementsStatus();
		if(ach_list!=null){
			List<AchievementStatus> player_list = ach_list.get(p.getName());
			for(AchievementStatus as: player_list){
				if(as.getDone() != true && as.getAch_id() == ids){		
					as.updateProgress(1);
					System.out.println("Updating progress, total: " +as.getProgress());
					if(as.getProgress() >= my_list.get(ids).getAmount()){
						as.setDone(true);
						AchievementManager.finishAchievement(p, ids);
					}
				}
			}
			
		}
	}

	public static void onBlockDestroy(BlockBreakEvent e) {
		Block b = e.getBlock();
    	Player p = e.getPlayer();
    	HashMap<Integer,Achievement> my_list = AchievementManager.getAllAchievements();
    	if(my_list!= null){
    		for(int ids:my_list.keySet()){
    			/*System.out.println("Destroyed block: "+b.getType());
    			System.out.println("Converting "+Material.valueOf(my_list.get(ids).getEntity_target()));
    			System.out.println("I had "+my_list.get(ids).getEntity_target());*/
    			if(my_list.get(ids).getType() == Type.DESTROY_BLOCK && Material.valueOf(my_list.get(ids).getEntity_target()) == b.getType()){
    				saveCode(p,ids,my_list);            				
    			}
    		}
    	}
		
	}

	public static void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
    	if(p == null) return;
    	HashMap<Integer,Achievement> my_list = AchievementManager.getAllAchievements();
    	if(my_list!= null){
    		for(int ids:my_list.keySet()){
    			if(my_list.get(ids).getType() == Type.VISIT){
    				HashMap<String,List<AchievementStatus>> ach_list = AchievementManager.getAllAchievementsStatus();
    				if(MathUtil.getDistance(p.getLocation().getX(),p.getLocation().getY(),  p.getLocation().getZ(),
    						my_list.get(ids).getX(), my_list.get(ids).getY(),my_list.get(ids).getZ())<=5.0) {
        				if(ach_list!=null){
        					List<AchievementStatus> player_list = ach_list.get(p.getName());
        					for(AchievementStatus as: player_list){
        						if(as.getDone() != true && as.getAch_id() == ids){
        							as.setDone(true);
    								AchievementManager.finishAchievement(p, ids);
        						}
        					}
        				}
    				}
    			}
    		}
    	}
		
	}

	public static void onPlayerJoin(PlayerJoinEvent event) {
		 
         if(!MoneyManager.hasAccount(event.getPlayer().getName())){
         	MoneyManager.setBalance(event.getPlayer().getName(), 1000.0);
         } if(!KarmaManager.hasKarma(event.getPlayer().getName())){
         	KarmaManager.setKarma(event.getPlayer().getName(), 0);
         } if(!ProfessionManager.hasProfession(event.getPlayer().getName())){
         	ProfessionManager.setProfession(event.getPlayer().getName(), new Profession(ProfessionType.UNEMPLOYED));
         	event.getPlayer().sendMessage(ChatColor.BLUE+"You don't have a profession yet, type /prof change (Profession_name) to get one");
         }
         AchievementManager.onPlayerLogin(event.getPlayer());
		
	}

	
}
