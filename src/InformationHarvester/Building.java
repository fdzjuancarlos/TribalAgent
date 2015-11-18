package InformationHarvester;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Building implements Serializable {
	
	private String name;
	private int level=0;
	
	private int nextLevelWood;
	private int nextLevelClay;
	private int nextLevelIron;
	private int nextLevelFarm;
	
	private long nextLevelTime;
	
	public Building() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNextLevelWood() {
		return nextLevelWood;
	}

	public void setNextLevelWood(int nextLevelWood) {
		this.nextLevelWood = nextLevelWood;
	}

	public int getNextLevelClay() {
		return nextLevelClay;
	}

	public void setNextLevelClay(int nextLevelClay) {
		this.nextLevelClay = nextLevelClay;
	}

	public int getNextLevelIron() {
		return nextLevelIron;
	}

	public void setNextLevelIron(int nextLevelIron) {
		this.nextLevelIron = nextLevelIron;
	}

	public int getNextLevelFarm() {
		return nextLevelFarm;
	}

	public void setNextLevelFarm(int nextLevelFarm) {
		this.nextLevelFarm = nextLevelFarm;
	}

	public long getNextLevelTime() {
		return nextLevelTime;
	}

	public void setNextLevelTime(long nextLevelTime) {
		this.nextLevelTime = nextLevelTime;
	}
	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException {
			    // perform the default serialization for all non-transient, non-static
			    // fields
			    aOutputStream.defaultWriteObject();
			}
	
	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException {
			    // always perform the default de-serialization first
			    aInputStream.defaultReadObject();
			}

}
