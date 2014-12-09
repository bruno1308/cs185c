package edu.sjsu.cs185c.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import edu.sjsu.cs185c.util.ErrorHandler;

public class ProfessionCommand implements CommandExecutor{

	public boolean onCommand(CommandSender cs, Command arg1, String arg2,
			String[] args) {
		
			if(args[0].equalsIgnoreCase("change")){
				if(args.length !=2) {cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString()); return false;}
				try{
					if(!cs.hasPermission("economy.profession.change")) {cs.sendMessage(ErrorHandler.PERMISSION_DENIED.toString());return false;}
					ProfessionType new_pt = ProfessionType.valueOf(args[1].toUpperCase());
					Profession new_prof = new Profession(new_pt);
					ProfessionManager.setProfession(cs.getName(), new_prof);
					cs.sendMessage(ChatColor.GREEN+"Profession changed successfully to "+args[1]);
					return true;
				}catch(IllegalArgumentException ie){
					cs.sendMessage(ErrorHandler.INEXISTENT_PROFESSION.toString());
					}	
			}
			else if(args[0].equalsIgnoreCase("list")){
				if(args.length !=1) {cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString()); return false;}
				ProfessionType types[] = ProfessionType.values();
				cs.sendMessage(ChatColor.BLUE+"List of professions: ");
				for(ProfessionType pt :types){
					cs.sendMessage(ChatColor.GREEN+pt.toString());
				}
				return true;
			}else if(args[0].equalsIgnoreCase("get")){
				if(args.length !=2) {cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString()); return false;}
				if(!MoneyManager.hasAccount(args[1])){cs.sendMessage(ErrorHandler.INEXISTENT_PLAYER.toString()); return false;}
				ProfessionType type = ProfessionManager.getProfessionByName(args[1]).getPt();
				cs.sendMessage(ChatColor.GREEN+args[1]+" is a/an "+type.toString());
				return true;
			}else if(args[0].equalsIgnoreCase("level")){
				if(args.length !=2) {cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString()); return false;}
				if(!MoneyManager.hasAccount(args[1])){cs.sendMessage(ErrorHandler.INEXISTENT_PLAYER.toString()); return false;}
				int level = ProfessionManager.getProfessionByName(args[1]).getLevel();
				cs.sendMessage(ChatColor.GREEN+args[1]+" is level "+Integer.toString(level));
				return true;
			} else {cs.sendMessage(ErrorHandler.INEXISTENT_COMMAND.toString()); return false;}
		return false;
	}

}
