package MayorAgent.behaviours;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class ManejadorInitiator extends AchieveREInitiator
{
    public ManejadorInitiator(Agent a,ACLMessage msg) {
        super(a,msg);
    }

    protected void handleAgree(ACLMessage agree)
    {
    	DebugLog(myAgent.getLocalName()+": Han acepetado mi propuesta" );
    }

    protected void handleRefuse(ACLMessage refuse)
    {
    	DebugLog(myAgent.getLocalName()+": Han rechazado mi propuesta" );
    }

    protected void handleNotUnderstood(ACLMessage notUnderstood)
    {
    	DebugLog(myAgent.getLocalName()+": No entendieron mi propuesta" );
    }

    protected void handleInform(ACLMessage inform)
    {
    	DebugLog(myAgent.getLocalName()+": Han cumplido mi propuesta" );
    }

    protected void handleFailure(ACLMessage fallo)
    {
    	DebugLog(myAgent.getLocalName()+": No pudieron cumplir mi propuesta tras aceptarla" );
    }
    
    protected void DebugLog(String string){
    	System.out.println(string);
    }
}
