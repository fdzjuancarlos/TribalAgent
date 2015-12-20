package MayorAgent.behaviours;

import java.util.Collection;
import java.util.Map;

import InformationHarvester.Building;
import InformationHarvester.TribalTown;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

public class DecisionMaking extends OneShotBehaviour {
	
	TribalTown currentTown;
	
	public DecisionMaking(Agent agent, TribalTown town){
		super(agent);
		currentTown = town;
	}

	@Override
	public void action() {
		if(currentTown.getOnConstructionBuildings() < 2){
			Map<String,Building> buildings = currentTown.getBuildings();
			Collection<Building> array = buildings.values();
			Building toBuild = null;
			int cheaper = Integer.MAX_VALUE;
			for (Building building : array) {
				int cost = building.getNextLevelClay() + building.getNextLevelIron() +
						   building.getNextLevelWood();
				boolean canBeBuilded = currentTown.getCurrentFarm()+building.getNextLevelFarm() < currentTown.getMaxFarm();
				if(cost < cheaper && canBeBuilded){
					cheaper = cost;
					toBuild = building;
				}
			}
			System.out.println("Mayor: Going to request build " + toBuild.getName());
			
	    	ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
	        msg.setContent("build " + toBuild.getName());
	        AID id = new AID();
	        id.setLocalName("bob");
	        msg.addReceiver(id);
	        myAgent.addBehaviour(new ManejadorInitiator(myAgent, msg));
		}
		
	}

}
