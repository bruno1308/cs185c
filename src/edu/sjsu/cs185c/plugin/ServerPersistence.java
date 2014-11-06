package edu.sjsu.cs185c.plugin;

public class ServerPersistence {
	
	private static Plugin p = MoneyManager.getPlugin();
	
	public static void saveBalances(){
		for(String name :MoneyManager.getAllBalances().keySet()){
			p.getConfig().set("balance."+name, MoneyManager.getAllBalances().get(name));	
		}
		p.saveConfig();
	}
	
	public static void loadBalances(){
		if(!p.getConfig().contains("balance")) return;
		for(String name :p.getConfig().getConfigurationSection("balance").getKeys(false)){
			//p.getConfig().set("balance."+name, MoneyManager.getAllBalances().get(name));
			MoneyManager.setBalance(name, p.getConfig().getDouble("balance."+name));
		}
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
	
	public static void loadData(){
		loadKarmas();
		loadBalances();
	}
	public static void saveData(){
		saveKarmas();
		saveBalances();
	}
}
