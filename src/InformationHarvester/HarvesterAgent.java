package InformationHarvester;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;
import jade.proto.SubscriptionResponder;
import jade.proto.SubscriptionResponder.SubscriptionManager;
import jade.proto.SubscriptionResponder.Subscription;
import jade.lang.acl.MessageTemplate;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;

public class HarvesterAgent extends Agent {

	TribalTown currentTown;
	private Set<Subscription> suscripciones = new HashSet<Subscription>();


	// Inicialización del agente
	protected void setup(){
		currentTown = new TribalTown();

		SequentialBehaviour infoCollect = new SequentialBehaviour();
		WebDriver driver = new FirefoxDriver();




		MessageTemplate template = SubscriptionResponder.createMessageTemplate(ACLMessage.SUBSCRIBE);


		SubscriptionManager gestor = new SubscriptionManager() {

			public boolean register(Subscription suscripcion) {
				suscripciones.add(suscripcion);
				return true;
			}

			public boolean deregister(Subscription suscripcion) {
				suscripciones.remove(suscripcion);
				return true;
			}
		};
		
		this.addBehaviour(new HacerSuscripcion(this, template, gestor));

		infoCollect.addSubBehaviour(new Login(driver, currentTown));
		infoCollect.addSubBehaviour(new InfoHarvest(driver, currentTown));
		addBehaviour(infoCollect);
	}

	public TribalTown getTown(){
		return currentTown;
	}

	private abstract class DriverUser extends OneShotBehaviour{
		protected WebDriver driver;
		protected TribalTown currentTown;

		DriverUser(WebDriver newDriver, TribalTown town){
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
	}

	private class Login extends DriverUser{


		Login(WebDriver newDriver, TribalTown town) {
			super(newDriver, town);
		}

		public void onStart()
		{
			System.out.println("Esto se hace cada vez que se inicia el comportamiento");
		}

		public void action(){


			driver.get("https://www.guerrastribales.es/");
			sleep(4);

			WebElement loginText = driver.findElement(By.xpath(".//*[@id='user']"));
			WebElement passwdText = driver.findElement(By.xpath(".//*[@id='password']"));
			WebElement loginButton = driver.findElement(By.xpath(".//*[@id='js_login_button']/a/span[2]"));


			loginText.sendKeys("Postmortem1337");
			passwdText.sendKeys("multiagentes5");

			sleep(3);

			loginButton.click();

			for(int i=0; i<5 ; i++){
				try{
					loginButton = driver.findElement(By.xpath(".//*[@id='active_server']/div/a/span"));
					break;
				}catch(NoSuchElementException e){
					sleep(1);
				}
			}


			loginButton.click();


		}

	}

	private class InfoHarvest extends DriverUser{

		InfoHarvest(WebDriver newDriver, TribalTown town) {
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
		
		private void backMainMenu(){
			clickButtonFromXpath(".//*[@id='menu_row']/td[2]/a");
			sleep(2);
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
				for (Subscription suscripcion:HarvesterAgent.this.suscripciones) {
					suscripcion.notify(mensaje);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void enterBuildingsMenu(){
			clickButtonFromXpath(".//*[@id='l_main']/a/div");
			sleep(2);
		}

		private void upgradeBuilding(String name){
			Building toUpgrade = currentTown.get(name);
			String commonXpath = ".//*[@id='main_buildlink_" + name + "_" + String.valueOf((toUpgrade.getLevel()+1))+"']";
			clickButtonFromXpath(commonXpath);
		}

		private void enterBuilding(String name){
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

		private void clickButtonFromXpath(String text){
			WebElement element = driver.findElement(By.xpath(text));
			element.click();
		}

		private int extractLevel(String text){
			try{
				return Integer.valueOf(text.split(" ")[1]);
			}catch(NumberFormatException e){
				return 0;
			}
		}

		private String stringFromXpath(String text){
			WebElement element = driver.findElement(By.xpath(text));
			return element.getText();
		}

		private int intFromXpath(String text){
			try{
				return Integer.valueOf(stringFromXpath(text));
			}catch(NumberFormatException e){
				return 0;
			}
		}

		private int levelFromXPath(String text){
			return extractLevel(stringFromXpath(text));
		}


	}

	private class HacerSuscripcion extends SubscriptionResponder {
		private Subscription suscripcion;

		public HacerSuscripcion(Agent agente, MessageTemplate plantilla, SubscriptionManager gestor) {
			super(agente, plantilla, gestor);
		}

		//Método que maneja la suscripcion

		protected ACLMessage handleSubscription(ACLMessage propuesta)
				throws NotUnderstoodException {
			System.out.printf("%s: SUSCRIBE recibido de %s.\n",
					HarvesterAgent.this.getLocalName(), propuesta.getSender().getLocalName());
			System.out.printf("%s: La propuesta es: %s.\n",
					HarvesterAgent.this.getLocalName(), propuesta.getContent());

			//Crea la suscripcion
			this.suscripcion = this.createSubscription(propuesta);

			try {
				//El SubscriptionManager registra la suscripcion
				this.mySubscriptionManager.register(suscripcion);
			} catch (Exception e) {
				System.out.println(HarvesterAgent.this.getLocalName() + ": Error en el registro de la suscripción.");
			}

			//Acepta la propuesta y la envía
			ACLMessage agree = propuesta.createReply();
			agree.setPerformative(ACLMessage.AGREE);
			return agree;
		}

		//Maneja la cancelación de la suscripcion

		protected ACLMessage handleCancel(ACLMessage cancelacion) {
			System.out.printf("%s: CANCEL recibido de %s.\n",
					HarvesterAgent.this.getLocalName(), cancelacion.getSender().getLocalName());

			try {
				//El SubscriptionManager elimina del registro la suscripcion
				this.mySubscriptionManager.deregister(this.suscripcion);
			} catch (Exception e) {
				System.out.println(HarvesterAgent.this.getLocalName() + ": Error en el desregistro de la suscripción.");
			}

			//Acepta la cancelación y responde
			ACLMessage cancela = cancelacion.createReply();
			cancela.setPerformative(ACLMessage.INFORM);
			return cancela;
		}
	}


}
