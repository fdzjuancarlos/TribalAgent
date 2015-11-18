package MayorAgent;

import InformationHarvester.TribalTown;
import MayorAgent.behaviours.InformationRequest;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import MayorAgent.behaviours.ManejadorInitiator;


public class MayorAgent extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7947898804785496723L;
	public TribalTown currentTown;
	
	protected void setup(){
    	
    	SequentialBehaviour infoCollect = new SequentialBehaviour();
    	


    	
        addBehaviour(new InformationRequest(this, 10000));
    }
	
    
    


}
