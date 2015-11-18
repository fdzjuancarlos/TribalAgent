package InformationHarvester.behaviours;

import org.openqa.selenium.WebDriver;

import InformationHarvester.TribalTown;

public class MakeBuilding extends DriverUser {
	
	String toBuild;

	protected MakeBuilding(WebDriver newDriver, TribalTown town, String name) {
		super(newDriver, town);
		toBuild = name;
	}

	@Override
	public void action() {
		enterBuildingsMenu();
		upgradeBuilding(toBuild);
		backMainMenu();
	}

}
