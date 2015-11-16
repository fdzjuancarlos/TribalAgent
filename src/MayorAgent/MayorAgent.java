package MayorAgent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import InformationHarvester.HarvesterAgent;
import InformationHarvester.TribalTown;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.SubscriptionInitiator;

public class MayorAgent extends Agent {
	
	TribalTown currentTown;
	
	protected void setup(){
    	
    	SequentialBehaviour infoCollect = new SequentialBehaviour();

    	
        addBehaviour(new InformationRequest(this, 5000));
    }
	
    private class InformationRequest extends WakerBehaviour{

		public InformationRequest(Agent a, long timeout) {
			super(a, timeout);
		}
		
		public void onWake(){
	        ACLMessage mensaje = new ACLMessage(ACLMessage.SUBSCRIBE);
	        mensaje.setProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
	 
	        AID id = new AID();
	        id.setLocalName("bob");
	        mensaje.addReceiver(id);
	        System.out.println("GONNA SUSSCRIIIBE");
			myAgent.addBehaviour(new TownSubscription(myAgent, mensaje));
		}
		
    
    }
    
    private class TownSubscription extends SubscriptionInitiator {
        private int suscripciones = 0;
 
        public TownSubscription(Agent agente, ACLMessage mensaje) {
            super(agente, mensaje);
        }
 
        //Maneja la respuesta en caso que acepte: AGREE
 
        protected void handleAgree(ACLMessage inform) {
            System.out.println(MayorAgent.this.getLocalName() + ": Solicitud aceptada.");
        }
 
        // Maneja la respuesta en caso que rechace: REFUSE
 
        protected void handleRefuse(ACLMessage inform) {
            System.out.println(MayorAgent.this.getLocalName() + ": Solicitud rechazada.");
        }
 
        //Maneja la informacion enviada: INFORM
 
        protected void handleInform(ACLMessage inform) {
            try {
				currentTown = (TribalTown) inform.getContentObject();
				System.out.println(currentTown.getIron() + "DE IROOOOOOON ");
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
        }
 
        //Maneja la respuesta en caso de fallo: FAILURE
 
        protected void handleFailure(ACLMessage failure) {
            //Se comprueba si el fallo viene del AMS o de otro agente.
            if (failure.getSender().equals(myAgent.getAMS())) {
                System.out.println(MayorAgent.this.getLocalName() + ": El destinatario no existe.");
            } else {
                System.out.printf("%s: El agente %s falló al intentar realizar la acción solicitada.\n",
                		MayorAgent.this.getLocalName(), failure.getSender().getName());
            }
        }
 
        public void cancellationCompleted(AID agente) {
            //Creamos una plantilla para solo recibir los mensajes del agente que va a cancelar la suscripción
            MessageTemplate template = MessageTemplate.MatchSender(agente);
            ACLMessage msg = blockingReceive(template);
 
            //Comprobamos que tipo de mensaje llegó: INFORM o FAILURE
            if (msg.getPerformative() == ACLMessage.INFORM)
                System.out.printf("%s : Suscripcion cancelada con el agente %s.\n",
                		MayorAgent.this.getLocalName(), agente.getLocalName());
            else
                System.out.printf("%s: Se ha producido un fallo en la cancelación con el agente %s.\n",
                		MayorAgent.this.getLocalName(), agente.getLocalName());
        }
    }


}
