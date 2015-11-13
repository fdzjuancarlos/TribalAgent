package InformationHarvester;

import jade.core.Agent;
import jade.core.behaviours.*;
 
public class HarvesterAgent extends Agent {
 
    // Inicialización del agente
    protected void setup(){
        addBehaviour(new MiComportamiento());
    }
 
    private class MiComportamiento extends Behaviour{
 
        public void onStart()
        {
            System.out.println("Esto se hace cada vez que se inicia el comportamiento");
        }
 
        public void action(){
 
            System.out.println("Hola a todos.");
 
            block(40000);
            System.out.println("Despues de 1 segundo");
        }
 
        public boolean done(){
            return true;
        }
    }
}
