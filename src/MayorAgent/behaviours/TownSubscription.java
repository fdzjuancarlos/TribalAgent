package MayorAgent.behaviours;

import InformationHarvester.TribalTown;
import MayorAgent.MayorAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.SubscriptionInitiator;

public class TownSubscription extends SubscriptionInitiator {
    private int suscripciones = 0;
    MayorAgent mayor;

    public TownSubscription(Agent agente, ACLMessage mensaje) {
        super(agente, mensaje);
        mayor = (MayorAgent)myAgent;
    }

    //AGREE

    protected void handleAgree(ACLMessage inform) {
        System.out.println(myAgent.getLocalName() + ": Solicitud aceptada.");
    }

    // Maneja la respuesta en caso que rechace: REFUSE

    protected void handleRefuse(ACLMessage inform) {
        System.out.println(myAgent.getLocalName() + ": Solicitud rechazada.");
    }

    //Maneja la informacion enviada: INFORM

    protected void handleInform(ACLMessage inform) {
        try {
			mayor.currentTown = (TribalTown) inform.getContentObject();
			System.out.println("Mayor: Información obtenida, meditando situación...");
			DecisionMaking decision = new DecisionMaking(myAgent, mayor.currentTown);
			myAgent.addBehaviour(decision);
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
    }

    //Maneja la respuesta en caso de fallo: FAILURE

    protected void handleFailure(ACLMessage failure) {
        //Se comprueba si el fallo viene del AMS o de otro agente.
        if (failure.getSender().equals(myAgent.getAMS())) {
            System.out.println(myAgent.getLocalName() + ": El destinatario no existe.");
        } else {
            System.out.printf("%s: El agente %s falló al intentar realizar la acción solicitada.\n",
            		myAgent.getLocalName(), failure.getSender().getName());
        }
    }

//    public void cancellationCompleted(AID agente) {
//        //Creamos una plantilla para solo recibir los mensajes del agente que va a cancelar la suscripción
//        MessageTemplate template = MessageTemplate.MatchSender(agente);
////        ACLMessage msg = blockingReceive(template);
//
//        //Comprobamos que tipo de mensaje llegó: INFORM o FAILURE
//        if (msg.getPerformative() == ACLMessage.INFORM)
//            System.out.printf("%s : Suscripcion cancelada con el agente %s.\n",
//            		myAgent.getLocalName(), agente.getLocalName());
//        else
//            System.out.printf("%s: Se ha producido un fallo en la cancelación con el agente %s.\n",
//            		myAgent.getLocalName(), agente.getLocalName());
//    }
}
