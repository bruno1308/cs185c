package edu.sjsu.cs185c.plugin;

public enum BlockValues {
	 IRON_BLOCK(3),
    GOLD_BLOCK(8),
    DIAMOND_BLOCK(15),
    STONE(1),
    GRASS(0.5),
    DIRT(0.5),
    COBBLESTONE(0.8),
    WOOD(0.6),
    LOG(0.8);

    private final double value;

    private BlockValues(final double newValue) {
        value = newValue;
    }

    public double getValue() { return value; }

}

enum WeaponType{
	IRON_SWORD, IRON_AXE, WOOD_SWORD, WOOD_AXE, STONE_SWORD,STONE_AXE, GOLD_SWORD, GOLD_AXE,
	DIAMOND_SWORD, DIAMOND_AXE
}
enum ChestPlateType{
	LEATHER_CHESTPLATE, CHAINMAIL_CHESTPLATE, IRON_CHESTPLATE, GOLD_CHESTPLATE, DIAMOND_CHESTPLATE
}
enum HelmetType{
	LEATHER_HELMET, CHAINMAIL_HELMET, IRON_HELMET, GOLD_HELMET, DIAMOND_HELMET
}
enum LeggingsType{
	LEATHER_LEGGINGS, CHAINMAIL_LEGGINGS, IRON_LEGGINGS, GOLD_LEGGINGS, DIAMOND_LEGGINGS
}
enum BootsType{
	LEATHER_BOOTS, CHAINMAIL_BOOTS, IRON_BOOTS, GOLD_BOOTS, DIAMOND_BOOTS
}
