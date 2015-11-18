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

import InformationHarvester.behaviours.DriverUser;
import InformationHarvester.behaviours.HacerSuscripcion;
import InformationHarvester.behaviours.InfoHarvest;
import InformationHarvester.behaviours.Login;
import InformationHarvester.behaviours.ManejadorResponder;
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

	public TribalTown currentTown;
	public Set<Subscription> suscripciones = new HashSet<Subscription>();
	public WebDriver driver;


	// Inicialización del agente
	protected void setup(){
		currentTown = new TribalTown();

		SequentialBehaviour infoCollect = new SequentialBehaviour();
		driver = new FirefoxDriver();




		MessageTemplate template = SubscriptionResponder.createMessageTemplate(ACLMessage.SUBSCRIBE);
        MessageTemplate protocolo = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        MessageTemplate performativa = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        MessageTemplate plantilla = MessageTemplate.and(protocolo, performativa);


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
		this.addBehaviour(new ManejadorResponder(this,plantilla));

		infoCollect.addSubBehaviour(new Login(driver, currentTown));
		infoCollect.addSubBehaviour(new InfoHarvest(driver, currentTown));
		addBehaviour(infoCollect);
	}

	public TribalTown getTown(){
		return currentTown;
	}

	

	

	

	


}
