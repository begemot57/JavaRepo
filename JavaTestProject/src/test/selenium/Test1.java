package test.selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test1 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://9.42.46.211:2221/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void test1() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.id("taskName")).clear();
    driver.findElement(By.id("taskName")).sendKeys("testuser5");
    driver.findElement(By.cssSelector("button.bx--btn.bx--btn--primary")).click();
    driver.findElement(By.id("taskName")).clear();
    driver.findElement(By.id("taskName")).sendKeys("testtask1");
    driver.findElement(By.cssSelector("button.bx--btn.bx--btn--primary")).click();
    driver.findElement(By.id("cloudChoice:DONT_MIND")).click();
    driver.findElement(By.xpath("//fieldset[@id='cloudChoice']/div[2]/div/div[2]/label/span")).click();
    driver.findElement(By.id("moveDataToCloud:DONT_MIND")).click();
    driver.findElement(By.xpath("//fieldset[@id='moveDataToCloud']/div[2]/div/div[2]/label/span")).click();
    driver.findElement(By.id("useFlexibleEnv:NO")).click();
    driver.findElement(By.xpath("//fieldset[@id='useFlexibleEnv']/div[2]/div/div[3]/label/span")).click();
    driver.findElement(By.id("savePreferenceButton")).click();
    driver.findElement(By.cssSelector("body > a")).click();
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

