package MayorAgent.behaviours;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

public class InformationRequest extends WakerBehaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1139040594848704784L;

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
		
    	ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        msg.setContent("build iron");
        id.setLocalName("bob");
        msg.addReceiver(id);
        myAgent.addBehaviour(new ManejadorInitiator(myAgent, msg));
	}
	

}