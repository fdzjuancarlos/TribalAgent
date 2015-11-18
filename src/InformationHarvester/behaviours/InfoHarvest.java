package InformationHarvester.behaviours;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import InformationHarvester.HarvesterAgent;
import InformationHarvester.TribalTown;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionResponder.Subscription;

public class InfoHarvest extends DriverUser{

	public InfoHarvest(WebDriver newDriver, TribalTown town) {
		super(newDriver, town);
	}

	public void onStart()
	{
	}

	public void action(){



		//RESOURCES
		checkResources();

		//BUILDINGS
		enterBuildingsMenu();

		enterBuilding("main");
		enterBuilding("statue");
		enterBuilding("wood");
		enterBuilding("stone");
		enterBuilding("iron");
		enterBuilding("farm");
		enterBuilding("storage");
		enterBuilding("hide");
		
		updateSubscribers();
		
		backMainMenu();




	}
	
	public int onEnd(){
		myAgent.addBehaviour(new InfoHarvest(driver,currentTown));
		System.out.println("NUEVO CICLO");
		return 0;
	}
	

	
	private void checkResources(){
		WebElement woodText = driver.findElement(By.xpath(".//*[@id='wood']"));
		WebElement clayText = driver.findElement(By.xpath(".//*[@id='stone']"));
		WebElement ironText = driver.findElement(By.xpath(".//*[@id='iron']"));
		WebElement maxStorage =  driver.findElement(By.xpath(".//*[@id='storage']"));

		WebElement currentFarm =  driver.findElement(By.xpath(".//*[@id='pop_current_label']"));
		WebElement maxFarm =  driver.findElement(By.xpath(".//*[@id='pop_max_label']"));


		currentTown.setWood(Integer.valueOf(woodText.getText()));
		currentTown.setStone(Integer.valueOf(clayText.getText()));
		currentTown.setIron(Integer.valueOf(ironText.getText()));
		currentTown.setMaxStorage(Integer.valueOf(maxStorage.getText()));

		currentTown.setCurrentFarm(Integer.valueOf(currentFarm.getText()));
		currentTown.setMaxFarm(Integer.valueOf(maxFarm.getText()));
	}
	
	private void updateSubscribers(){
		ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
		try {
			mensaje.setContentObject(currentTown);
			for (Subscription suscripcion:((HarvesterAgent)myAgent).suscripciones) {
				suscripcion.notify(mensaje);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	


}