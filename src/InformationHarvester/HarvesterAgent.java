package InformationHarvester;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import jade.core.Agent;
import jade.core.behaviours.*;
 
public class HarvesterAgent extends Agent {
	
	TribalTown currentTown;
 
    // Inicialización del agente
    protected void setup(){
    	currentTown = new TribalTown();
    	
    	SequentialBehaviour infoCollect = new SequentialBehaviour();
        WebDriver driver = new FirefoxDriver();

    	infoCollect.addSubBehaviour(new Login(driver, currentTown));
    	infoCollect.addSubBehaviour(new InfoHarvest(driver, currentTown));

        addBehaviour(infoCollect);
    }
    
    public TribalTown getTown(){
    	return currentTown;
    }
 
    private abstract class DriverUser extends OneShotBehaviour{
    	protected WebDriver driver;
    	protected TribalTown currentTown;
    	
    	DriverUser(WebDriver newDriver, TribalTown town){
    		driver = newDriver;
    		currentTown = town;
    	}
    	
    	public int onEnd(){
    		sleep(1);
    		return 0;
    	}
    	
        protected void sleep(long time){
        	try {
				Thread.sleep(time*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    private class Login extends DriverUser{

    	
        Login(WebDriver newDriver, TribalTown town) {
			super(newDriver, town);
		}

		public void onStart()
        {
            System.out.println("Esto se hace cada vez que se inicia el comportamiento");
        }
 
        public void action(){
 
            
            driver.get("https://www.guerrastribales.es/");
            sleep(4);
            
            WebElement loginText = driver.findElement(By.xpath(".//*[@id='user']"));
            WebElement passwdText = driver.findElement(By.xpath(".//*[@id='password']"));
            WebElement loginButton = driver.findElement(By.xpath(".//*[@id='js_login_button']/a/span[2]"));

            
            loginText.sendKeys("Postmortem1337");
            passwdText.sendKeys("multiagentes5");
            
            sleep(3);
            
            loginButton.click();
            
            for(int i=0; i<5 ; i++){
            	try{
            		loginButton = driver.findElement(By.xpath(".//*[@id='active_server']/div/a/span"));
            		break;
            	}catch(NoSuchElementException e){
            		sleep(1);
            	}
            }
            
            
            loginButton.click();
            
            
        }
 
    }
    
    private class InfoHarvest extends DriverUser{
    	
        InfoHarvest(WebDriver newDriver, TribalTown town) {
			super(newDriver, town);
		}

		public void onStart()
        {
        }
 
        public void action(){
        	
        	
            
            //RESOURCES
            WebElement woodText = driver.findElement(By.xpath(".//*[@id='wood']"));
            WebElement clayText = driver.findElement(By.xpath(".//*[@id='stone']"));
            WebElement ironText = driver.findElement(By.xpath(".//*[@id='iron']"));
            WebElement maxStorage =  driver.findElement(By.xpath(".//*[@id='storage']"));
            
            WebElement currentFarm =  driver.findElement(By.xpath(".//*[@id='pop_current_label']"));
            WebElement maxFarm =  driver.findElement(By.xpath(".//*[@id='pop_max_label']"));

            
            currentTown.setWood(Integer.valueOf(woodText.getText()));
            currentTown.setStone(Integer.valueOf(clayText.getText()));
            currentTown.setIron(Integer.valueOf(ironText.getText()));
            currentTown.setMaxStorage(Integer.valueOf(maxStorage.getText()));
            
            currentTown.setCurrentFarm(Integer.valueOf(currentFarm.getText()));
            currentTown.setMaxFarm(Integer.valueOf(maxFarm.getText()));
            
            //BUILDINGS
            WebElement goBuildings = driver.findElement(By.xpath(".//*[@id='l_main']/a/div"));
            goBuildings.click();
            sleep(2);
            
            enterBuilding("main");
            enterBuilding("statue");
            enterBuilding("wood");
            enterBuilding("stone");
            enterBuilding("iron");
            enterBuilding("farm");
            enterBuilding("storage");
            enterBuilding("hide");

        }
        
        private void enterBuilding(String name){
        	String commonXpath = ".//*[@id='main_buildrow_"+name+"']";
            Building main = new Building();
            main.setLevel(levelFromXPath(commonXpath+"/td[1]/span"));
            main.setNextLevelWood(intFromXpath(commonXpath+"/td[2]"));
            main.setNextLevelClay(intFromXpath(commonXpath+"/td[3]"));
            main.setNextLevelIron(intFromXpath(commonXpath+"/td[4]"));
            main.setNextLevelFarm(intFromXpath(commonXpath+"/td[6]"));
            currentTown.put(name, main);
        }
        
        private int extractLevel(String text){
        	try{
        		return Integer.valueOf(text.split(" ")[1]);
        	}catch(NumberFormatException e){
        		return 0;
        	}
        }
        
        private String stringFromXpath(String text){
        	WebElement element = driver.findElement(By.xpath(text));
        	return element.getText();
        }
        
        private int intFromXpath(String text){
        	try{
        		return Integer.valueOf(stringFromXpath(text));
        	}catch(NumberFormatException e){
        		return 0;
        	}
        }
        
        private int levelFromXPath(String text){
        	return extractLevel(stringFromXpath(text));
        }
 
    
    }
}
