package edu.sjsu.cs185c.plugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.sjsu.cs185c.util.ErrorHandler;
import edu.sjsu.cs185c.util.MapUtil;

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
				cs.sendMessage(ErrorHandler.WRONG_COMMAND_USAGE.toString());
				return false;
			}
		}else{
		if(!MoneyManager.hasAccount(args[1])) {cs.sendMessage(ErrorHandler.INEXISTENT_PLAYER.toString()); return false;}
		
		if(args[0].equalsIgnoreCase("add")){
				if(!cs.hasPermission("economy.money.add")) {cs.sendMessage(ErrorHandler.PERMISSION_DENIED.toString()); return false;}
				try{
					Double amount = Double.parseDouble(args[2]);
					cs.sendMessage(ChatColor.GREEN+"Add balance complete: "+Double.toString(amount) +" + " + Double.toString(MoneyManager.getBalance(args[1])));
					MoneyManager.setBalance(args[1], (MoneyManager.getBalance(args[1])+amount));
					return true;
				}catch(Exception e){
					cs.sendMessage(ErrorHandler.INVALID_NUMBER.toString());
					return false;
				}
			
			
		}else if(args[0].equalsIgnoreCase("remove")){
			if(!cs.hasPermission("economy.money.remove")) {cs.sendMessage(ErrorHandler.PERMISSION_DENIED.toString()); return false;}
				try{
					Double amount = Double.parseDouble(args[2]);
					MoneyManager.setBalance(args[1],MoneyManager.getBalance(args[1])-amount);
					cs.sendMessage(ChatColor.GREEN+"Remove balance complete");
					return true;
				}catch(Exception e){
					cs.sendMessage(ErrorHandler.INVALID_NUMBER.toString());
					
					return false;
				}
			}
			
		else if(args[0].equalsIgnoreCase("set")){
			if(!cs.hasPermission("economy.money.set")) {cs.sendMessage(ErrorHandler.PERMISSION_DENIED.toString()); return false;}
				try{
					Double amount = Double.parseDouble(args[2]);
					MoneyManager.setBalance(args[1], amount);
					cs.sendMessage(ChatColor.GREEN+"Set balance complete");
					return true;
				}catch(Exception e){
					cs.sendMessage(ErrorHandler.INVALID_NUMBER.toString());
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
		}else if(args[0].equalsIgnoreCase("transfer")){
			try{
				Player me = (Player)cs;
				String receiver=args[1];
				Double my_bal = MoneyManager.getBalance(me.getName());
				Double amount= Double.parseDouble(args[2]);
				if(my_bal >= amount){
					MoneyManager.setBalance(me.getName(), my_bal-amount);
					MoneyManager.setBalance(receiver, MoneyManager.getBalance(receiver) + amount);		
					cs.sendMessage(ChatColor.GREEN+"Transferred: "+Double.toString(amount)+" to "+receiver);
					return true;
				}else{
					cs.sendMessage(ErrorHandler.NOT_ENOUGH_MONEY.toString());
					return false;
				}
				
			}catch(Exception e){
				return false;
			}
		}
		else{
			cs.sendMessage(ErrorHandler.INEXISTENT_COMMAND.toString());
			return false;
		}
		}		
		
	}
	
	

}
