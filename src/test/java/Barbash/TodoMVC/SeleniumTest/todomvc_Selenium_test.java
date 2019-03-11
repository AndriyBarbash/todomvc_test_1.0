package Barbash.TodoMVC.SeleniumTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class todomvc_Selenium_test {

    private WebDriver driver;
    private WebElement testElement;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void test_todomvc_E2E() {

        openTodoMVC();
        add();
        assertTaskList();
        editTask();
        assertEdited();
        removeTask();
        toggle();
        assertItemsLeft();
        filterCompleted();
        filterActive();
        filterAll();
        toggleAll();
        clearCompleted();
        assertNoTasks();

    }

    private void openTodoMVC() {
        driver.get("http://todomvc.com/examples/emberjs/");

    }

    private void add() {

        driver.findElement(By.id("new-todo")).sendKeys("Record A");
        driver.findElement(By.id("new-todo")).sendKeys(Keys.ENTER);

        driver.findElement(By.id("new-todo")).sendKeys("Record B");
        driver.findElement(By.id("new-todo")).sendKeys(Keys.ENTER);

        driver.findElement(By.id("new-todo")).sendKeys("Record C");
        driver.findElement(By.id("new-todo")).sendKeys(Keys.ENTER);
    }

    private void assertTaskList() {

        testElement = driver.findElement(By.xpath("//*[@id=\"todo-list\"]"));
        int liCount = testElement.findElements(By.tagName("li")).size();
        Assert.assertEquals(liCount, 3);

    }

    private void editTask() {
        new Actions(driver).doubleClick(driver.findElement(By.xpath("//li[last()]/div/label"))).perform();
        driver.findElement(By.xpath("//li[last()]/input")).sendKeys(" edited");
        driver.findElement(By.xpath("//li[last()]/input")).sendKeys(Keys.ENTER);

    }

    private void assertEdited() {
        testElement = driver.findElement(By.xpath("//li[last()]/div/label"));
        String taskValue = testElement.getText();
        Assert.assertEquals("Record C edited", taskValue);

    }

    private void removeTask() {
        new Actions(driver).moveToElement(driver.findElement(By.xpath("//li[last()]/div/button"))).click().build().perform();

    }

    private void toggle() {
        new Actions(driver).moveToElement(driver.findElement(By.xpath("//li[1]/div/input"))).click().build().perform();
        new Actions(driver).moveToElement(driver.findElement(By.xpath("//li[1]/div/input"))).click().build().perform();
        new Actions(driver).moveToElement(driver.findElement(By.xpath("//li[2]/div/input"))).click().build().perform();
    }

    private void assertItemsLeft() {
        testElement = driver.findElement(By.cssSelector("#todo-count > strong:nth-child(1)"));
        String taskCounter = testElement.getAttribute("innerText");
        Assert.assertEquals("1", taskCounter);
    }

    private void filterCompleted() {
        new Actions(driver).moveToElement(driver.findElement(By.linkText("Completed"))).click().build().perform();
    }

    private void filterActive() {
        new Actions(driver).moveToElement(driver.findElement(By.linkText("Active"))).click().build().perform();
    }

    private void filterAll() {
        new Actions(driver).moveToElement(driver.findElement(By.linkText("All"))).click().build().perform();
    }

    private void toggleAll() {
        new Actions(driver).moveToElement(driver.findElement(By.id("toggle-all"))).click().build().perform();
    }

    private void clearCompleted() {
        new Actions(driver).moveToElement(driver.findElement(By.id("clear-completed"))).click().build().perform();

    }

    private void assertNoTasks() {
        Assert.assertEquals(0, driver.findElements(By.xpath("//*[@id=\"main\"]")).size());

    }

}
