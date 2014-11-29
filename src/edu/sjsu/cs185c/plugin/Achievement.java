package edu.sjsu.cs185c.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Achievement {

	private double reward_amount;
	private int amount;
	private RewardType reward_type;
	private Type type;
	private String entity_target;
	private Double x,y,z;

	public Achievement(Type t, String target, int times, RewardType rt, double rw_amount){
		type =t;
		entity_target = target;
		amount = times;
		reward_type = rt;
		reward_amount = rw_amount;
	}
	public Achievement(Type t, Double x, Double y, Double z, RewardType rt, double rw_amount){
		type =t;
		amount = 1;
		reward_type = rt;
		reward_amount = rw_amount;
		this.x =x;
		this.y=y;
		this.z=z;
		
	}

	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public Double getZ() {
		return z;
	}
	public void setZ(Double z) {
		this.z = z;
	}
	public double getReward_amount() {
		return reward_amount;
	}

	public void setReward_amount(double reward_amount) {
		this.reward_amount = reward_amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public RewardType getReward_type() {
		return reward_type;
	}

	public void setReward_type(RewardType reward_type) {
		this.reward_type = reward_type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	public String getEntity_target() {
		return entity_target;
	}
	public void setEntity_target(String entity_target) {
		this.entity_target = entity_target;
	}




}

enum RewardType {
    MONEY, KARMA
}


enum Type{
	DESTROY_PLAYER, DESTROY_MONSTER, DESTROY_BLOCK, VISIT, COLLECT, INVALID
}


