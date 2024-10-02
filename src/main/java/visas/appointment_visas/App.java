package visas.appointment_visas;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.checkerframework.common.util.report.qual.ReportCall;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
    	
    	WebDriverManager.chromedriver().setup();
    	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://ais.usvisa-info.com/es-co/niv/users/sign_in");
		
		WebElement email = driver.findElement(By.id("user_email"));
		WebElement password = driver.findElement(By.id("user_password"));
		WebElement accept_Policy = driver.findElement(By.xpath("//div[contains(@class, 'icheckbox')]"));
		WebElement login_button = driver.findElement(By.xpath("//input[@name='commit']"));
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		//Proceso de login		
		email.sendKeys("cortizp9@gmail.com");
		password.sendKeys("Master.123");
		accept_Policy.click();
		
		Assert.assertTrue(driver.findElement(By.id("policy_confirmed")).isSelected());
		
		login_button.click();
		
		//espera a que cambie la pagina
		
		Thread.sleep(5000);
		
		//click en continuar
		WebElement continue_buttonElement = driver.findElement(By.xpath("//a[text()='Continuar']/.."));
		continue_buttonElement.click();
		
		//espera a que aparezcan opciones
		Thread.sleep(5000);
		List<WebElement> acordion = driver.findElements(By.className("accordion-item"));
		
		//recorre las opciones y da click en Reprogramar
		for ( WebElement i : acordion) {
			if(i.getText().contains("Reprogramar")) {
				i.click();
			}
		}
		
		//espera a que cambie la pagina
		Thread.sleep(1000);
		
		//click de nuevo en Reprogramar
		WebElement reprogramarBoton = driver.findElement(By.xpath("//a[text()='Reprogramar cita']"));
		reprogramarBoton.click();
		
		//espera a que apareszca el calendario
		Thread.sleep(2000);
		WebElement calendarioElement = driver.findElement(By.id("appointments_consulate_appointment_date"));
		Thread.sleep(2000);
		calendarioElement.click();
		
		boolean dayFound=false;
		
		
		
		while(dayFound == false) {
		    // Find all the days in the current month
		    List<WebElement> availableDays = driver.findElements(By.xpath("//span[@class='ui-state-default']/.."));
		    WebElement nextElement = driver.findElement(By.xpath("//a[@title='Next']"));
		    
		    for (WebElement i : availableDays) {
		        // Get the class attribute of the day element
		        String dayClass = i.getAttribute("class");
		        System.out.println(dayClass);
		        // Check if the day is selectable (i.e., it doesn't contain "ui-state-disabled")
		        if (dayClass.contains("ui-state-disabled")) {
		            System.out.println("Not available day: " + i.getText());
		            System.out.println("Class: " + dayClass);
		        }
		        else if (dayClass.contains("undefined")){
		        	System.out.println("Available day: " + i.getText());
		        	i.click();
		            dayFound = true;
		            break;
		        }
		    }

		    // If no day was found in the current month, move to the next month
		    if (dayFound == false) {
		    	nextElement.click();
		        System.out.println("Moved to the next month");
		    }
		}
		
		
    }
}
