package InformationHarvester.behaviours;

import java.util.StringTokenizer;

import org.openqa.selenium.WebDriver;

import InformationHarvester.TribalTown;
import InformationHarvester.HarvesterAgent;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class ManejadorResponder extends AchieveREResponder
{
    public ManejadorResponder(Agent a,MessageTemplate mt) {
        super(a,mt);
    }

    protected ACLMessage handleRequest(ACLMessage request)throws NotUnderstoodException, RefuseException
    {
        StringTokenizer st=new StringTokenizer(request.getContent());
        String contenido=st.nextToken();
        System.out.println("HANDLE REQUEST "+ request.getContent() + " -- " +contenido);
        if(contenido.equalsIgnoreCase("build")){
            TribalTown town = ((HarvesterAgent)myAgent).currentTown;
            String building = st.nextToken();
            if (town.get(building) != null && town.getOnConstructionBuildings() < 2)
            {
                ACLMessage agree = request.createReply();
                agree.setPerformative(ACLMessage.AGREE);
                WebDriver driver = ((HarvesterAgent)myAgent).driver;
                System.out.println("UPGRADE ACCEPTED "+ building);
                myAgent.addBehaviour(new MakeBuilding(driver,town,building));
                return agree;
            }
            else
            {
                throw new RefuseException("What building?");
            }
        }
        else throw new NotUnderstoodException("Not Understood, USAGE [build <buildingName>]");
    }

    protected ACLMessage prepareResultNotification(ACLMessage request,ACLMessage response) throws FailureException
    {
        if (Math.random() > 0.2) {
//            System.out.println("Hospital "+getLocalName()+": Han vuelto de atender el accidente.");
            ACLMessage inform = request.createReply();
            inform.setPerformative(ACLMessage.INFORM);
            return inform;
        }
        else
        {
//            System.out.println("Hospital "+getLocalName()+": Han hecho todo lo posible, lo sentimos.");
            throw new FailureException("Han hecho todo lo posible");
        }
    }
}
