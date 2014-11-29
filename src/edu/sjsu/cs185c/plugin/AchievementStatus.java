package edu.sjsu.cs185c.plugin;

public class AchievementStatus {
	int ach_id;
	public int getAch_id() {
		return ach_id;
	}
	public void setAch_id(int ach_id) {
		this.ach_id = ach_id;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
	public void updateProgress(int work){
		this.progress +=work;
	}
	int progress=0;
	Boolean done=false;
}
