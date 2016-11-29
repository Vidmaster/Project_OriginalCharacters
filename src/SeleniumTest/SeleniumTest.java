package SeleniumTest;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.chrome.driver",  "chromedriver");
    driver = new ChromeDriver();
    baseUrl = "http://vidmaster.ddns.net:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
  @Test// Test user registration
  public void originalCharacterTest01() throws Exception {
    driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Register")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password1");
    driver.findElement(By.id("confirmPassword")).clear();
    driver.findElement(By.id("confirmPassword")).sendKeys("password1");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("demouser25@me.com");
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("I am a test user!! I like writing stories.");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
    assertEquals("User created successfully!", closeAlertAndGetItsText());
    Thread.sleep(5000);
  }
    
  @Test // test same user registration
  public void originalCharacterTest02() throws Exception {
	  driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Register")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password1");
    driver.findElement(By.id("confirmPassword")).clear();
    driver.findElement(By.id("confirmPassword")).sendKeys("password1");
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("demouser@me.com");
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("I'm trying to register again because that's cool!!");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(8000); // pause to show unable to create same user with email
  }
    
  @Test // test login
  public void originalCharacterTest03() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(6000); // pause to see invalid
    
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password1");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000); // pause to show login
  }
    
    
  @Test // test editing user profile
  public void originalCharacterTest04() throws Exception {
    driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password1");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
    
    
    driver.findElement(By.linkText("Edit Profile")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("I am a test user!! I like writing stories.\n\nHere is more info about me!!!!!!");
    Thread.sleep(5000); // pause to see new text
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }
      
  @Test // test changing user password
  public void originalCharacterTest05() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password1");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
    
    
    driver.findElement(By.linkText("Update Password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password1");
    driver.findElement(By.id("newPassword")).clear();
    driver.findElement(By.id("newPassword")).sendKeys("password2");
    driver.findElement(By.id("confirmPassword")).clear();
    driver.findElement(By.id("confirmPassword")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }
    
  @Test // test adding new character
  public void originalCharacterTest06() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
    
    
    driver.findElement(By.linkText("New Character")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("Michael Barr");
    driver.findElement(By.id("appearance")).clear();
    driver.findElement(By.id("appearance")).sendKeys("Tall pale and handsome!");
    driver.findElement(By.id("personality")).clear();
    driver.findElement(By.id("personality")).sendKeys("Loud and obnoxious");
    driver.findElement(By.id("notes")).clear();
    driver.findElement(By.id("notes")).sendKeys("Like jeans and his cowboy boots.");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }
    
  @Test // test editting character
  public void originalCharacterTest07() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
	  
    driver.findElement(By.linkText("Michael Barr")).click();
    driver.findElement(By.linkText("Edit Character")).click();
    driver.findElement(By.id("notes")).clear();
    driver.findElement(By.id("notes")).sendKeys("Used to be at the top of command but has been exhiled by the high order.");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }
    
  @Test // test adding new story
  public void originalCharacterTest08() throws Exception {
    driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
    
    
    driver.findElement(By.linkText("New Story")).click();
    Thread.sleep(1000);
    driver.findElement(By.id("title")).clear();
    driver.findElement(By.id("title")).sendKeys("The Gray Town in Toberville");
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("This is a story of how Michael was at the top and fell to the bottom");
    driver.findElement(By.id("genre")).clear();
    driver.findElement(By.id("genre")).sendKeys("Drama");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }
    
  @Test // test editing a story
  public void originalCharacterTest09() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
	  
    driver.findElement(By.linkText("The Gray Town in Toberville")).click();
    Thread.sleep(2000);
    driver.findElement(By.linkText("Edit Story Details")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("This is a story of how Michael was the king but got knocked down to the bottom.");
    Thread.sleep(5000);;
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }
      
  @Test // test adding contribution to story
  public void originalCharacterTest10() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
	  
    driver.findElement(By.linkText("The Gray Town in Toberville")).click();
    Thread.sleep(2000);
    driver.findElement(By.linkText("Write A Contribution")).click();
    driver.findElement(By.id("title")).clear();
    driver.findElement(By.id("title")).sendKeys("Chapter 1: The Mansion");
    driver.findElement(By.id("body")).clear();
    driver.findElement(By.id("body")).sendKeys("Michael sat in his mansion drinking his wine and tearing the meat savagly off the turkey leg with the bone.....");
    driver.findElement(By.cssSelector("div.tags.focused")).click();
    driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
    driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("m");
    driver.findElement(By.xpath("//tags-input[@id='tags']/div/auto-complete/div/ul/li[2]/ti-autocomplete-match/ng-include/div/b")).click();
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }
    
  @Test // test adding another contribution to story
  public void originalCharacterTest11() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
	  
    driver.findElement(By.linkText("The Gray Town in Toberville")).click();
    Thread.sleep(2000);
    driver.findElement(By.linkText("Write A Contribution")).click();
    driver.findElement(By.id("title")).clear();
    driver.findElement(By.id("title")).sendKeys("Chapter 2: Knocked Out");
    driver.findElement(By.id("body")).clear();
    driver.findElement(By.id("body")).sendKeys("The guards entered the room, and with disbelief grabbed Michael....");
    driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
    driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("michael");
    driver.findElement(By.xpath("//tags-input[@id='tags']/div/auto-complete/div/ul/li/ti-autocomplete-match/ng-include/div")).click();
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }
    
  @Test // test adding final contribution to story
  public void originalCharacterTest12() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
	  
    driver.findElement(By.linkText("The Gray Town in Toberville")).click();
    Thread.sleep(2000);
    driver.findElement(By.linkText("Write A Contribution")).click();
    driver.findElement(By.id("title")).clear();
    driver.findElement(By.id("title")).sendKeys("Chapter 3: The trash Chute");
    driver.findElement(By.id("body")).clear();
    driver.findElement(By.id("body")).sendKeys("Noise clinking as Michael could here the compactors getting closer and closer as he slipped down the chute....");
    driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
    driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("Michael");
    driver.findElement(By.xpath("//tags-input[@id='tags']/div/auto-complete/div/ul/li/ti-autocomplete-match/ng-include/div")).click();
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }
  
  @Test // test editing a contribution
	public void originalCharacterTest13() throws Exception {
  	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
    
  	driver.findElement(By.linkText("Edit")).click();
    driver.findElement(By.id("body")).clear();
    driver.findElement(By.id("body")).sendKeys("it was clunky, and noisy, you would have thought the military would have better planes by now....\n\n\nMore story here...");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
  }
  
  @Test // test deleting a story
  public void originalCharacterTest14() throws Exception {
	  driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
	  
    driver.findElement(By.linkText("The Gray Town in Toberville")).click();
    Thread.sleep(2000);
    driver.findElement(By.cssSelector("button.btn.btn-danger")).click();
    Thread.sleep(5000);
    assertTrue(closeAlertAndGetItsText().matches("^Are you SURE you want to delete this story[\\s\\S]$"));
    Thread.sleep(5000);
    assertTrue(closeAlertAndGetItsText().matches("^Are you REALLY SURE[\\s\\S] There is no going back!$"));
    Thread.sleep(5000);
  }
    
  @Test // test joining a story
  public void originalCharacterTest15() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
    driver.findElement(By.id("searchText")).clear();
    driver.findElement(By.id("searchText")).sendKeys("john");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
    driver.findElement(By.linkText("John Returns Home")).click();
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
    driver.findElement(By.linkText("John Returns Home")).click();
    Thread.sleep(5000);
  }
    
  @Test // Test writing contribution of joined story
  public void originalCharacterTest16() throws Exception {
	driver.get(baseUrl + "/#!/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("demouser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("password2");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
	  
    
    driver.findElement(By.linkText("John Returns Home")).click();
    Thread.sleep(2000);
    driver.findElement(By.linkText("Write A Contribution")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
    driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("john");
    driver.findElement(By.cssSelector("b.ng-binding")).click();
    driver.findElement(By.id("title")).clear();
    driver.findElement(By.id("title")).sendKeys("Chapter 6: A New Home");
    driver.findElement(By.id("body")).clear();
    driver.findElement(By.id("body")).sendKeys("As John came across a small hutch he entered with no one insight and what seemed to have been an abandonded home...");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    Thread.sleep(5000);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
