package test.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import static org.junit.Assert.*;
//import java.util.regex.Pattern;

public class Test2 {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://9.42.46.211:2221/");
		selenium.start();
	}

	@Test
	public void testTest1() throws Exception {
		selenium.open("/");
		selenium.type("id=taskName", "testuser5");
		selenium.click("css=button.bx--btn.bx--btn--primary");
		selenium.type("id=taskName", "testtask1");
		selenium.click("css=button.bx--btn.bx--btn--primary");
		selenium.click("id=cloudChoice:DONT_MIND");
		selenium.click("//fieldset[@id='cloudChoice']/div[2]/div/div[2]/label/span");
		selenium.click("id=moveDataToCloud:DONT_MIND");
		selenium.click("//fieldset[@id='moveDataToCloud']/div[2]/div/div[2]/label/span");
		selenium.click("id=useFlexibleEnv:NO");
		selenium.click("//fieldset[@id='useFlexibleEnv']/div[2]/div/div[3]/label/span");
		selenium.click("id=savePreferenceButton");
		selenium.click("css=body > a");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
