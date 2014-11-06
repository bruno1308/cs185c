package edu.sjsu.cs185c.plugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MoneyCommand implements CommandExecutor {

	public boolean onCommand(CommandSender cs, Command arg1, String arg2,
			String[] args) {
		if(args[0].equalsIgnoreCase("ranking")){
			try{
				HashMap<String,Double> all =MoneyManager.getAllBalances();
				Map<String, Double> sorted = MapUtil.sortByValue(all);
				int cont=sorted.size();
				for(String key: sorted.keySet()){
					String msg = Integer.toString(cont)+": "+key+" "+sorted.get(key)+"$";
					cs.sendMessage(ChatColor.BLUE+msg);
					cont --;
				}
				return true;
			}catch(Exception e){
				//e.printStackTrace();
				cs.sendMessage(ChatColor.RED+"Command error: "+e.toString());
				return false;
			}
		}
		if(args.length < 2){
			cs.sendMessage(ChatColor.RED+"Wrong command usage");
			cs.sendMessage(ChatColor.GREEN+"Usage: /econ <add/remove/set/get> <player> <amount>");
			return false;
		}
		if(MoneyManager.hasAccount(args[1])){
		if(args[0].equalsIgnoreCase("add")){
			
				try{
					Double amount = Double.parseDouble(args[2]);
					cs.sendMessage(ChatColor.GREEN+"Add balance complete: "+Double.toString(amount) +" + " + Double.toString(MoneyManager.getBalance(args[1])));
					MoneyManager.setBalance(args[1], (MoneyManager.getBalance(args[1])+amount));
					return true;
				}catch(Exception e){
					cs.sendMessage(ChatColor.RED+"Wrong amount value");
					return false;
				}
			
			
		}else if(args[0].equalsIgnoreCase("remove")){
			
				try{
					Double amount = Double.parseDouble(args[2]);
					MoneyManager.setBalance(args[1],MoneyManager.getBalance(args[1])-amount);
					cs.sendMessage(ChatColor.GREEN+"Remove balance complete");
					return true;
				}catch(Exception e){
					cs.sendMessage(ChatColor.RED+"Wrong amount value");
					
					return false;
				}
			}
			
		else if(args[0].equalsIgnoreCase("set")){
			
				try{
					Double amount = Double.parseDouble(args[2]);
					MoneyManager.setBalance(args[1], amount);
					cs.sendMessage(ChatColor.GREEN+"Set balance complete");
					return true;
				}catch(Exception e){
					cs.sendMessage(ChatColor.RED+"Wrong amount value");
					return false;
				}
			}
		else if(args[0].equalsIgnoreCase("get")){
			try{
				Double bal=MoneyManager.getBalance(args[1]);
				cs.sendMessage(ChatColor.GREEN+"Balance: "+Double.toString(bal));
				return true;
			}catch(Exception e){
				return false;
			}
		}
		else{
			cs.sendMessage(ChatColor.RED+"Wrong command usage");
			return false;
		}
		}else{
			cs.sendMessage(ChatColor.RED+"Player doesn't have an account");
			return false;
		}
		
		
		
	}

}
