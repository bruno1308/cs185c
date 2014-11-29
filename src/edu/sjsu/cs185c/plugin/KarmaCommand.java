package edu.sjsu.cs185c.plugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import edu.sjsu.cs185c.util.ErrorHandler;
import edu.sjsu.cs185c.util.MapUtil;

public class KarmaCommand implements CommandExecutor{

	public boolean onCommand(CommandSender cs, Command arg1, String arg2,
			String[] args) {
		if(args[0].equalsIgnoreCase("ranking")){
			try{
				if(args.length != 1)cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString());
				HashMap<String,Integer> all =KarmaManager.getAllKarmas();
				Map<String, Integer> sorted = MapUtil.sortByValue(all);
				int cont=sorted.size();
				for(String key: sorted.keySet()){
					String msg = Integer.toString(cont)+": "+key+" "+sorted.get(key);
					cs.sendMessage(ChatColor.BLUE+msg);
					cont--;
				}
				return true;
			}catch(Exception e){
				e.printStackTrace();
				cs.sendMessage(ChatColor.RED+"Command error: "+e.toString());
				return false;
			}
		}
		if(args.length <2){
			if(args.length != 5)cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString());
			cs.sendMessage(ChatColor.GREEN+"Usage: /karma <add/remove/set/get> <player> (<amount>)?");
		}
		if(KarmaManager.hasKarma(args[1])){
		if(args[0].equalsIgnoreCase("add")){
			if(!cs.hasPermission("economy.karma.add")) {cs.sendMessage(ErrorHandler.PERMISSION_DENIED.toString()); return false;}
				try{
					int amount = Integer.parseInt(args[2]);
					KarmaManager.setKarma(args[1], (KarmaManager.getKarma(args[1])+amount));
					cs.sendMessage(ChatColor.GREEN+"Add karma complete");
					return true;
				}catch(Exception e){
					cs.sendMessage(ErrorHandler.INVALID_NUMBER.toString());
					return false;
				}
			
			
		}else if(args[0].equalsIgnoreCase("remove")){
			if(!cs.hasPermission("economy.karma.remove")) {cs.sendMessage(ErrorHandler.PERMISSION_DENIED.toString()); return false;}
				try{
					int amount = Integer.parseInt(args[2]);;
					KarmaManager.setKarma(args[1], (KarmaManager.getKarma(args[1])-amount));
					cs.sendMessage(ChatColor.GREEN+"Remove karma complete");
					return true;
				}catch(Exception e){
					cs.sendMessage(ErrorHandler.INVALID_NUMBER.toString());
					
					return false;
				}
			}
			
		else if(args[0].equalsIgnoreCase("set")){
			if(!cs.hasPermission("economy.karma.set")) {cs.sendMessage(ErrorHandler.PERMISSION_DENIED.toString()); return false;}
				try{
					int amount = Integer.parseInt(args[2]);;
					KarmaManager.setKarma(args[1], amount);
					cs.sendMessage(ChatColor.GREEN+"Karma set successfully");
					return true;
				}catch(Exception e){
					cs.sendMessage(ErrorHandler.INVALID_NUMBER.toString());
					return false;
				}
			}else if(args[0].equalsIgnoreCase("get")){
				try{
					int bal=KarmaManager.getKarma(args[1]);
					cs.sendMessage(ChatColor.GREEN+"Karma: "+Integer.toString(bal));
					return true;
				}catch(Exception e){
					cs.sendMessage(ErrorHandler.INEXISTENT_PLAYER.toString());
					return false;
				}
			}
			
		else{
			cs.sendMessage(ErrorHandler.INEXISTENT_COMMAND.toString());
			return false;
		}
		}else{
			cs.sendMessage(ErrorHandler.INEXISTENT_PLAYER.toString());
			return false;
		}
		
	}

}
