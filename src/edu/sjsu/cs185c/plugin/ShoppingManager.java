package edu.sjsu.cs185c.plugin;

import java.util.HashMap;

import org.bukkit.Material;

public class ShoppingManager {
	private static HashMap<Material, Double> prices = new HashMap<Material, Double>();
	
	public static HashMap<Material, Double> getPrices() {
		return prices;
	}

	public static void setPrices(HashMap<Material, Double> prices) {
		ShoppingManager.prices = prices;
	}

	public static void addItem(Material name, Double price){
		prices.put(name, price);
	}
	
	public static Double getPrice(Material name){
		return prices.get(name);
	}
	public static void init(){
		prices.put(Material.GOLD_AXE, 80D);
		prices.put(Material.GOLD_SWORD, 100D);
		prices.put(Material.DIAMOND_AXE, 800D);
		prices.put(Material.DIAMOND_SWORD, 1000D);
		prices.put(Material.WOOD_AXE, 50D);
		prices.put(Material.WOOD_SWORD, 80D);
		prices.put(Material.STONE_AXE, 120D);
		prices.put(Material.STONE_SWORD, 150D);
		prices.put(Material.IRON_AXE, 140D);
		prices.put(Material.IRON_SWORD, 200D);
		prices.put(Material.GOLD_CHESTPLATE, 150D);
		prices.put(Material.GOLD_BOOTS, 200D);
		prices.put(Material.GOLD_LEGGINGS, 200D);
		prices.put(Material.GOLD_HELMET, 200D);
		prices.put(Material.LEATHER_CHESTPLATE, 80D);
		prices.put(Material.LEATHER_BOOTS, 80D);
		prices.put(Material.LEATHER_LEGGINGS, 80D);
		prices.put(Material.LEATHER_HELMET, 80D);
		prices.put(Material.DIAMOND_CHESTPLATE, 1500D);
		prices.put(Material.DIAMOND_BOOTS, 1500D);
		prices.put(Material.DIAMOND_LEGGINGS, 1500D);
		prices.put(Material.DIAMOND_HELMET, 1500D);
		prices.put(Material.IRON_CHESTPLATE, 700D);
		prices.put(Material.IRON_BOOTS, 700D);
		prices.put(Material.IRON_LEGGINGS, 700D);
		prices.put(Material.IRON_HELMET, 700D);
		prices.put(Material.CHAINMAIL_CHESTPLATE, 500D);
		prices.put(Material.CHAINMAIL_BOOTS, 500D);
		prices.put(Material.CHAINMAIL_LEGGINGS, 500D);
		prices.put(Material.CHAINMAIL_HELMET, 500D);
	}


}
