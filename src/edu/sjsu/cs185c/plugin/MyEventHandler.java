package edu.sjsu.cs185c.plugin;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import edu.sjsu.cs185c.util.MathUtil;


public class MyEventHandler {
	public final static double MIN_DIST = 5D;
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
    	checkAssassin(event);
		
	}
	
	
	private static void checkAssassin(EntityDeathEvent event) {
		Entity dead = event.getEntity();
    	Player  p = event.getEntity().getKiller();
    	Profession prof = ProfessionManager.getProfessionByName(p.getName());
    	//TODO MONSTERMANAGER with different xp for each monster
    	if(prof.getPt() == ProfessionType.ASSASSIN){
    		afterExp(prof, 5D, p);
    		if(dead instanceof Player)
    			afterExp(prof,5D,p);
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
    			if(my_list.get(ids).getType() == Type.DESTROY_BLOCK && Material.valueOf(my_list.get(ids).getEntity_target()) == b.getType()){
    				saveCode(p,ids,my_list);            				
    			}
    		}
    	}
		minerBreakingBlock(e);
		
	}

	public static void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
    	if(p == null) return;
    	HashMap<Integer,Achievement> my_list = AchievementManager.getAllAchievements();
    	if(my_list!= null){
    		for(int ids:my_list.keySet()){
    			if(my_list.get(ids).getType() == Type.VISIT){
    				HashMap<String,List<AchievementStatus>> ach_list = AchievementManager.getAllAchievementsStatus();
    				if(ach_list!=null){
    					Double px=p.getLocation().getX();
    					Double py=p.getLocation().getY();
    					Double pz=p.getLocation().getZ();
    					Double achx=my_list.get(ids).getX();
    					Double achy=my_list.get(ids).getY();
    					Double achz=my_list.get(ids).getZ();
    					Double dist = 0D;
    					try{
    						dist =MathUtil.getDistance(px,py,pz,achx,achy,achz);
    					}catch(Exception ex){
    						dist =MIN_DIST*2;
    					}
    					if(dist<= MIN_DIST) {
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
	
	public static void minerBreakingBlock(BlockBreakEvent e){
		Block b = e.getBlock();
		Location loc = b.getLocation();
    	Player p = e.getPlayer();
    	Material m = b.getType();
    	System.out.println("Name is: "+m.toString());
    	Profession player_prof = ProfessionManager.getProfessionByName(p.getName());
    	if(player_prof.getPt()==ProfessionType.MINER && m.isBlock()){
    		int rand = MathUtil.randInt(1, 100);
    		if(player_prof.getDrop_rate() > rand){
    			b.getWorld().dropItem(loc, new ItemStack(b.getType(), 1));
    			p.sendMessage(ChatColor.YELLOW+"Double drop!");
    		}
    		try{
    			double exp = BlockValues.valueOf(m.toString()).getValue();
    			afterExp(player_prof,exp,p);
    		}catch(Exception ex){
    			System.out.println("Block yet not supported");
    		}
    	}else{
    		return;
    	}
		
	}

	public static void onCraftItem(CraftItemEvent e) {
		Recipe r = e.getRecipe();
        ItemStack item = r.getResult();
        Material m = item.getType();
        HumanEntity p = e.getWhoClicked();
        
        if(isWeapon(m.toString()) || isChestPlate(m.toString()) || isLeggings(m.toString()) || isBoots(m.toString())){
			if(ProfessionManager.getProfessionByName(p.getName()).getPt() != ProfessionType.CRAFTER){
        	e.getInventory().setResult(new ItemStack(Material.LOG, 1));
			}else{
				crafterCrafting(e);
			}
		}
	}
	
	public static void crafterCrafting(CraftItemEvent e){
		Player p = (Player)e.getWhoClicked();
		Profession player_prof = ProfessionManager.getProfessionByName(p.getName());
		afterExp(player_prof,1D,p);
		
	}
	
	public static boolean isWeapon(String test) {

	    for (WeaponType c : WeaponType.values()) {
	        if (c.name().equals(test)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public static void afterExp(Profession p, Double exp, Player player){
		p.addExperience(exp);
		player.sendMessage(ChatColor.WHITE+"+"+Double.toString(exp)+" exp");
		for(int i=p.checkUpLevel(); i!=0; --i){
			player.sendMessage(ChatColor.BLUE+"Level up!");
		}
		
	}
	public static boolean isChestPlate(String test) {

	    for (ChestPlateType c : ChestPlateType.values()) {
	        if (c.name().equals(test)) {
	            return true;
	        }
	    }

	    return false;
	}
	public static boolean isBoots(String test) {

	    for (BootsType c : BootsType.values()) {
	        if (c.name().equals(test)) {
	            return true;
	        }
	    }

	    return false;
	}
	public static boolean isLeggings(String test) {

	    for (LeggingsType c : LeggingsType.values()) {
	        if (c.name().equals(test)) {
	            return true;
	        }
	    }

	    return false;
	}

	public static void onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		Profession prof = ProfessionManager.getProfessionByName(p.getName());
		if(prof.getPt() == ProfessionType.BUILDER){
			//TODO add blockmanager to add different xp values
			afterExp(prof, 0.5, p);
		}
		
	}

}




