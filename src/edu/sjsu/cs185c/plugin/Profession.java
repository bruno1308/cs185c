package edu.sjsu.cs185c.plugin;

public class Profession {
	private ProfessionType pt;
	private int level;
	private Double wage;
	private Double experience;
	private final int EXP_FACTOR =1;
	private int drop_rate;
	
	public int getDrop_rate() {
		return drop_rate;
	}
	public void setDrop_rate(int drop_rate) {
		this.drop_rate = drop_rate;
	}
	public Profession(ProfessionType newpt){
		this.pt = newpt;
		level =0;
		experience=0D;
		drop_rate=0;
		wage=100D;
		if(newpt == ProfessionType.MINER){
			drop_rate=10;
		}else if(newpt == ProfessionType.UNEMPLOYED){
			wage = 0D;
		}
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
	public Double getExperience() {
		return experience;
	}
	public void setExperience(Double experience) {
		this.experience = experience;
	}
	public void addExperience(double exp) {
		this.experience+=exp;
	}
	public int calculateLevel(){
		int new_level;
		new_level = (int)(EXP_FACTOR * Math.sqrt(experience));
		return new_level;
	}
	public int checkUpLevel(){
		int level_now = this.level;
		int level_next = calculateLevel();
		if(level_now!=level_next){
			this.level = level_next;
			this.drop_rate = (level_next + 10)+((level_next/10)*5);
			if(this.drop_rate>=100) this.drop_rate = 100;
			wage+=100;
		}
		return level_next -level_now;
	}
	

}
enum ProfessionType{
	MINER, LUMBERJACK, ASSASSIN, POLICEMAN, HUNTER, BUILDER,
	CRAFTER, UNEMPLOYED
}
