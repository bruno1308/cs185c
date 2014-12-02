package edu.sjsu.cs185c.plugin;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import edu.sjsu.cs185c.util.ErrorHandler;

public class ShoppingCommand implements CommandExecutor {

	public boolean onCommand(CommandSender cs, Command arg1, String arg2,
			String[] args) {
		if(args[0].equalsIgnoreCase("buy")){
			if(args.length != 2) {cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString()); return false;}
			HashMap<Material, Double> prices = ShoppingManager.getPrices();
			Material m = Material.getMaterial(args[1].toUpperCase());
			if(prices.containsKey(m) && m!= null){
				double price = prices.get(m);
				Player sender = (Player)cs;
				if(price <= MoneyManager.getBalance(sender.getName())){
					MoneyManager.setBalance(cs.getName(), MoneyManager.getBalance(cs.getName())-price);
					sender.getInventory().addItem(new ItemStack(m, 1));
					//sender.getInventory().set
					cs.sendMessage(ChatColor.YELLOW+"Bought "+m.toString()+ " for $"+price);
				}else{
					cs.sendMessage(ErrorHandler.NOT_ENOUGH_MONEY.toString()); return false;
				}
			}else{
				cs.sendMessage(ErrorHandler.INEXISTENT_ITEM.toString()); return false;
			}
			
		}else if(args[0].equalsIgnoreCase("sell")){
			if(args.length != 2) {cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString()); return false;}
			HashMap<Material, Double> prices = ShoppingManager.getPrices();
			Material m = Material.getMaterial(args[1].toUpperCase());
			if(prices.containsKey(m) && m!= null){
				double price = prices.get(m)/2D;
				Player sender = (Player)cs;
				ItemStack[] inv = sender.getInventory().getContents();
				for(ItemStack item:inv){
					if(item != null && item.getType().equals(m)){
						//sender.getInventory().removeItem(new ItemStack(m,1));
						//removeInventoryItems(sender.getInventory(),m,1);
						takeOne(sender, item);
						MoneyManager.setBalance(sender.getName(), MoneyManager.getBalance(sender.getName())+price);		
						MoneyManager.setBalance("$server", MoneyManager.getBalance("$server")-price);
						cs.sendMessage(ChatColor.YELLOW+"Sold "+m.toString()+ " for $"+price);
						sender.updateInventory();
						return true;
					}
				}
				cs.sendMessage(ChatColor.RED+ErrorHandler.NOT_HAVE_ITEM.toString());
				return true;
			}else{
				cs.sendMessage(ErrorHandler.INEXISTENT_ITEM.toString()); return false;
			}
			
		}else if(args[0].equalsIgnoreCase("price")){
			if(args.length != 2) {cs.sendMessage(ErrorHandler.WRONG_NUMBER_OF_PARMS.toString()); return false;}
			HashMap<Material, Double> prices = ShoppingManager.getPrices();
			Material m = Material.getMaterial(args[1].toUpperCase());
			if(prices.containsKey(m) && m!= null){
				cs.sendMessage(ChatColor.BLUE+"It costs "+prices.get(m));
			}else{
				cs.sendMessage(ErrorHandler.INEXISTENT_ITEM.toString()); return false;
			}
		}else{
			cs.sendMessage(ErrorHandler.INEXISTENT_COMMAND.toString()); return false;
		}
		return false;
	}
	/*public static void removeInventoryItems(PlayerInventory inv, Material type, int amount) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.remove(is);
                    amount = -newamount;
                    if (amount == 0) break;
                }
            }
        }
    }*/
	
	public void takeOne(Player p, ItemStack i){
		  if(i.getAmount() <=1){
		    p.getInventory().removeItem(i);
		  }
		  if(i.getAmount() > 1){
		    i.setAmount(i.getAmount() - 1);
		  }
		}

}
