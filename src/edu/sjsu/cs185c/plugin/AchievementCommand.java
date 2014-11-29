package edu.sjsu.cs185c.plugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import edu.sjsu.cs185c.util.ErrorHandler;

public class AchievementCommand implements CommandExecutor {

	public boolean onCommand(CommandSender cs, Command arg1, String arg2,
			String[] args) {
		if(args[0].equalsIgnoreCase("destroy")){
			try{
				if(args.length != 5)cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString());
				String target = args[1].toUpperCase();
				Type target_type = validTarget(target);
				int times = Integer.parseInt(args[2]);
				RewardType rt = RewardType.valueOf(args[3].toUpperCase());
				double ra = Double.parseDouble(args[4]);
				if(target_type != Type.INVALID){
					Achievement ach = new Achievement(target_type,target, times, rt, ra);
					AchievementManager.addAchievement(ach);
					cs.sendMessage(ChatColor.GREEN+"Achievement created successfully");
					return true;
				}else{
					cs.sendMessage(ErrorHandler.INVALID_TARGET.toString());
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
				cs.sendMessage(ErrorHandler.WRONG_COMMAND_USAGE.toString());
				return false;
			}
		}else if(args[0].equalsIgnoreCase("visit")){
			Double x,y,z;
			try{
				if(args.length != 6)cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString());
			x = Double.parseDouble(args[1]);
			y = Double.parseDouble(args[2]);
			z = Double.parseDouble(args[3]);
			RewardType rt = RewardType.valueOf(args[4].toUpperCase());
			double ra = Double.parseDouble(args[5]);
			Achievement ach = new Achievement(Type.VISIT,x,y,z,rt,ra);
			AchievementManager.addAchievement(ach);
			cs.sendMessage(ChatColor.GREEN+"Achievement created successfully");
			return true;
			}catch(Exception e){
				cs.sendMessage(ErrorHandler.INVALID_LOCATION.toString());
				return false;
			}
			
		}
		return false;
	}
	
	public Type validTarget(String target){
		Material m = Material.getMaterial(target);
		if(m!= null) return Type.DESTROY_BLOCK;
		Player p = Bukkit.getPlayer(target);			
		if(p != null) return Type.DESTROY_PLAYER;
		if(isMonster(target)) return Type.DESTROY_MONSTER;
		return Type.INVALID;
	}

	static public boolean isMonster(String aName) {
		EntityType[] aFruits = EntityType.values();
	    for (EntityType aFruit : aFruits)
	        if (aFruit.toString().equals(aName))
	            return true;
	    return false;
	}
}
