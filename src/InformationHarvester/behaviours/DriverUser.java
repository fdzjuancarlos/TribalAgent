package InformationHarvester.behaviours;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import InformationHarvester.Building;
import InformationHarvester.TribalTown;
import jade.core.behaviours.OneShotBehaviour;

public abstract class DriverUser extends OneShotBehaviour{
	protected WebDriver driver;
	protected TribalTown currentTown;

	protected DriverUser(WebDriver newDriver, TribalTown town){
		driver = newDriver;
		currentTown = town;
	}

	public int onEnd(){
		sleep(1);
		return 0;
	}

	protected void sleep(long time){
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void upgradeBuilding(String name){
		Building toUpgrade = currentTown.get(name);
		String commonXpath = ".//*[@id='main_buildlink_" + name + "_" + String.valueOf((toUpgrade.getLevel()+1))+"']";
		System.out.println(commonXpath);
		//.//*[@id='main_buildlink_farm_3']
		clickButtonFromXpath(commonXpath);
		
	}

	protected void enterBuilding(String name){
		String commonXpath = ".//*[@id='main_buildrow_"+name+"']";
		Building main = new Building();
		main.setLevel(levelFromXPath(commonXpath+"/td[1]/span"));
		main.setNextLevelWood(intFromXpath(commonXpath+"/td[2]"));
		main.setNextLevelClay(intFromXpath(commonXpath+"/td[3]"));
		main.setNextLevelIron(intFromXpath(commonXpath+"/td[4]"));
		main.setNextLevelFarm(intFromXpath(commonXpath+"/td[6]"));
		main.setName(name);
		currentTown.put(name, main);
	}
	
	protected void backMainMenu(){
		clickButtonFromXpath(".//*[@id='menu_row']/td[2]/a");
		sleep(2);
	}

	protected void clickButtonFromXpath(String text){
		WebElement element = driver.findElement(By.xpath(text));
		System.out.println(element.getText());
		sleep(1);
		element.click();
		sleep(1);
	}

	protected int extractLevel(String text){
		try{
			return Integer.valueOf(text.split(" ")[1]);
		}catch(NumberFormatException e){
			return 0;
		}
	}

	protected String stringFromXpath(String text){
		WebElement element = driver.findElement(By.xpath(text));
		return element.getText();
	}

	protected int intFromXpath(String text){
		try{
			return Integer.valueOf(stringFromXpath(text));
		}catch(NumberFormatException e){
			return 0;
		}
	}

	protected int levelFromXPath(String text){
		return extractLevel(stringFromXpath(text));
	}
	protected void enterBuildingsMenu(){
		clickButtonFromXpath(".//*[@id='l_main']/a/div");
		sleep(2);
	}
}