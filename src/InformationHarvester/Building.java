package InformationHarvester;

public class Building {
	
	private String name;
	private int level=0;
	
	private int nextLevelWood;
	private int nextLevelClay;
	private int nextLevelIron;
	private int nextLevelFarm;
	
	private long nextLevelTime;
	
	Building() {
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


}
