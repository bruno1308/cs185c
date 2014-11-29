package edu.sjsu.cs185c.plugin;

public class Profession {
	private ProfessionType pt;
	private int level;
	private Double wage;
	private int experience;
	
	public Profession(ProfessionType newpt){
		this.pt = newpt;
		level =0;
		experience=0;
		wage=100D;
	}
	public ProfessionType getPt() {
		return pt;
	}
	public void setPt(ProfessionType pt) {
		this.pt = pt;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Double getWage() {
		return wage;
	}
	public void setWage(Double wage) {
		this.wage = wage;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	

}
enum ProfessionType{
	MINER, LUMBERJACK, ASSASSIN, POLICEMAN, HUNTER, BUILDER,
	CRAFTER, UNEMPLOYED
}
