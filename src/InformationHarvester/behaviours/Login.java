package InformationHarvester.behaviours;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import InformationHarvester.TribalTown;

public class Login extends DriverUser{


	public Login(WebDriver newDriver, TribalTown town) {
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