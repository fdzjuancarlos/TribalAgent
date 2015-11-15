package InformationHarvester;

import java.util.HashMap;
import java.util.Map;

public class TribalTown {
	
	private int wood;
	private int stone;
	private int iron;
	private int maxStorage;
	
	private int currentFarm;
	private int maxFarm;
	
	private Map<String,Building> buildings;
	
	TribalTown(){
		buildings = new HashMap<String,Building>();
		wood = 0;
		stone = 0;
		iron = 0;
		maxStorage = 0;
		currentFarm = 0; maxFarm = 0;
	}
	
//	public void setBuilding(String name, Building build){
//		buildings.put(name, build);
//	}
	
//	public Building getBuilding(String name, Building build){
//		buildings.put(name, build);
//	}


	public int getWood() {
		return wood;
	}


	public void setWood(int wood) {
		this.wood = wood;
	}


	public int getStone() {
		return stone;
	}


	public void setStone(int stone) {
		this.stone = stone;
	}


	public int getIron() {
		return iron;
	}


	public void setIron(int iron) {
		this.iron = iron;
	}


	public int getMaxStorage() {
		return maxStorage;
	}


	public void setMaxStorage(int maxStorage) {
		this.maxStorage = maxStorage;
	}


	public int getCurrentFarm() {
		return currentFarm;
	}


	public void setCurrentFarm(int currentFarm) {
		this.currentFarm = currentFarm;
	}


	public int getMaxFarm() {
		return maxFarm;
	}


	public void setMaxFarm(int maxFarm) {
		this.maxFarm = maxFarm;
	}
	
	@Override
	public String toString(){
		return "Resources: \n" + "Wood " + wood +" Stone " + stone + " Iron "+ iron;
	}

	public Building get(Object key) {
		return buildings.get(key);
	}

	public Building put(String key, Building value) {
		return buildings.put(key, value);
	}

}
