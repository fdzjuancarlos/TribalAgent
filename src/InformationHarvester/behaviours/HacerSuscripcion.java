package InformationHarvester.behaviours;

import InformationHarvester.HarvesterAgent;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionResponder;
import jade.proto.SubscriptionResponder.Subscription;
import jade.proto.SubscriptionResponder.SubscriptionManager;

public class HacerSuscripcion extends SubscriptionResponder {
	private Subscription suscripcion;

	public HacerSuscripcion(Agent agente, MessageTemplate plantilla, SubscriptionManager gestor) {
		super(agente, plantilla, gestor);
	}

	//Método que maneja la suscripcion

	protected ACLMessage handleSubscription(ACLMessage propuesta)
			throws NotUnderstoodException {
		System.out.printf("%s: SUSCRIBE recibido de %s.\n",
				myAgent.getLocalName(), propuesta.getSender().getLocalName());
		System.out.printf("%s: La propuesta es: %s.\n",
				myAgent.getLocalName(), propuesta.getContent());

		//Crea la suscripcion
		this.suscripcion = this.createSubscription(propuesta);

		try {
			//El SubscriptionManager registra la suscripcion
			this.mySubscriptionManager.register(suscripcion);
		} catch (Exception e) {
			System.out.println(myAgent.getLocalName() + ": Error en el registro de la suscripción.");
		}

		//Acepta la propuesta y la envía
		ACLMessage agree = propuesta.createReply();
		agree.setPerformative(ACLMessage.AGREE);
		return agree;
	}

	//Maneja la cancelación de la suscripcion

	protected ACLMessage handleCancel(ACLMessage cancelacion) {
		System.out.printf("%s: CANCEL recibido de %s.\n",
				myAgent.getLocalName(), cancelacion.getSender().getLocalName());

		try {
			//El SubscriptionManager elimina del registro la suscripcion
			this.mySubscriptionManager.deregister(this.suscripcion);
		} catch (Exception e) {
			System.out.println(myAgent.getLocalName() + ": Error en el desregistro de la suscripción.");
		}

		//Acepta la cancelación y responde
		ACLMessage cancela = cancelacion.createReply();
		cancela.setPerformative(ACLMessage.INFORM);
		return cancela;
	}
}