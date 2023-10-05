package org.mikefonseta;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.AssertJUnit.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Suite {

    private static final String MAIN = "http://localhost:8000/";
    private static WebDriver driver;
    private static final String PATH_DOWNLOAD_BROWSER_FOLDER = "/home/mike/Scaricati/";

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;




    @BeforeAll
    public static void init(){
        System.setProperty("webdriver.chrome.driver","/home/mike/Scrivania/PasswordManager/JavaTests/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");
//        chromeOptions.addArguments("--no-sandbox");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(MAIN);

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    @Order(1)
    public void Register()  {

        driver.get(MAIN);

        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("button")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            if((items.get(i).getAttribute("onclick") != null && items.get(i).getAttribute("onclick").toLowerCase().contains("signup"))
                || (items.get(i).getText() != null && items.get(i).getText().toLowerCase().replaceAll(" ","").contains("signup")))
            {
                items.get(i).click();
                i=-2;
            }
            i++;
        }

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"user\"]")))
        .sendKeys("MikeFonseta");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pwd\"]")))
        .sendKeys("1231231");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pwd1\"]")))
        .sendKeys("1231231");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"email\"]")))
        .sendKeys("m.fonseta@studenti.unina.it");



        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"chk\"]")))
        .click();

        isAlertPresent(driver);

        String chkButton = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"chk\"]")))
                .getAttribute("value")
                .toLowerCase();

        assertEquals("login",chkButton);
    }

    @Test
    @Order(2)
    public void Login() throws InterruptedException {

        driver.get(MAIN);

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"user\"]")))
                .sendKeys("MikeFonseta");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pwd\"]")))
                .sendKeys("1231231");

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"chk\"]")))
                .click();

        Thread.sleep(1000);

        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).getText().toLowerCase().replaceAll(" ","").contains("logout"))
            {
                i=-2;
            }
            i++;
        }

        assertEquals(-1, i);
    }

    @Test
    @Order(3)
    public void AddEntry1()  {

        driver.get(MAIN);

        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).getAttribute("data-target") != null && items.get(i).getAttribute("data-target").toLowerCase().contains("add"))
            {
                items.get(i).click();
                i=-2;
            }
            i++;
        }

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminput\"]")))
                .sendKeys("Google");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminputuser\"]")))
                .sendKeys("m.fonseta@gmail.com");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminputurl\"]")))
                .sendKeys("https://www.google.it");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminputcomment\"]")))
                .sendKeys("Password generata per google");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminputtags\"]")))
                .sendKeys("Password generata");

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newbtn\"]")))
                .click();

        isAlertPresent(driver);

        items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("td")));

        i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).getText().toLowerCase().contains("google"))
            {
                i=-2;
            }
            i++;
        }

        assertEquals(-1, i);
    }

    @Test
    @Order(4)
    public void AddEntry2()  {

        driver.get(MAIN);

        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).getAttribute("data-target") != null && items.get(i).getAttribute("data-target").toLowerCase().contains("add"))
            {
                items.get(i).click();
                i=-2;
            }
            i++;
        }

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminput\"]")))
                .sendKeys("Youtube");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminputuser\"]")))
                .sendKeys("m.fonseta@gmail.com");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminputurl\"]")))
                .sendKeys("https://www.youtube.it");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminputcomment\"]")))
                .sendKeys("Password generata per youtube");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newiteminputtags\"]")))
                .sendKeys("Password generata");

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"newbtn\"]")))
                .click();

        isAlertPresent(driver);
        items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("td")));

        i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).getText().toLowerCase().contains("youtube"))
            {
                i=-2;
            }
            i++;
        }

        assertEquals(-1, i);
    }

    @Test
    @Order(5)
    public void ModifyEntry1()  {

        driver.get(MAIN);

        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("datarow")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).findElement(By.className("namecell")).getText().toLowerCase().contains("youtube"))
            {
                items.get(i).findElement(By.xpath(".//a[@title='Edit']")).click();
                i=-2;
            }
            i++;
        }
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edititeminputcomment\"]")))
                .sendKeys(" per m.fonseta@gmail.com (Youtube)");

        String comment = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edititeminputcomment\"]"))).getText();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"editbtn\"]")))
                .click();

        isAlertPresent(driver);
        items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("datarow")));

        i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).findElement(By.className("namecell")).getText().toLowerCase().contains("youtube"))
            {
                items.get(i).findElement(By.xpath(".//a[@title='Edit']")).click();
                i=-2;
            }
            i++;
        }

        assertEquals(new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edititeminputcomment\"]"))).getText(), comment);

    }

    @Test
    @Order(6)
    public void ModifyEntry2()  {

        driver.get(MAIN);

        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("datarow")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).findElement(By.className("namecell")).getText().toLowerCase().contains("google"))
            {
                items.get(i).findElement(By.xpath(".//a[@title='Edit']")).click();
                i=-2;
            }
            i++;
        }
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edititeminputcomment\"]")))
                .sendKeys(" per m.fonseta@gmail.com (Google)");

        String comment = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edititeminputcomment\"]"))).getText();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"editbtn\"]")))
                .click();

        isAlertPresent(driver);
        items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("datarow")));

        i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).findElement(By.className("namecell")).getText().toLowerCase().contains("google"))
            {
                items.get(i).findElement(By.xpath(".//a[@title='Edit']")).click();
                i=-2;
            }
            i++;
        }

        assertEquals(new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"edititeminputcomment\"]"))).getText(), comment);
    }

    @Test
    @Order(7)
    public void ExportsCSV() throws InterruptedException {
        Thread.sleep(10000);
        driver.get(MAIN);
        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            System.out.println(items.get(i).getText());
            if(items.get(i).getAttribute("innerText").toLowerCase().contains("csv"))
            {
                i=-2;
            }
            i++;
        }

        assertEquals(-1, i);
    }

    @Test
    @Order(8)
    public void DeleteEntry1()  {

        driver.get(MAIN);

        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("datarow")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            WebElement namecell = items.get(i).findElement(By.className("namecell"));
            if(namecell != null && namecell.getText().toLowerCase().contains("google"))
            {
                items.get(i).findElement(By.xpath(".//a[@title='Edit']")).click();
                i=-2;
            }
            i++;
        }

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"delbtn\"]")))
                .click();

        //Alert conferma eliminazione
        isAlertPresent(driver);
        //Alert avvenuta eliminazione
        isAlertPresent(driver);

        items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("datarow")));

        i = 0;
        while(i > -1 && i < items.size())
        {
            WebElement namecell = items.get(i).findElement(By.className("namecell"));
            if(namecell != null && namecell.getText().toLowerCase().contains("google"))
            {
                i=-2;
            }
            i++;
        }

        assertTrue(i != -1);
    }

    @Test
    @Order(9)
    public void DeleteEntry2()  {

        driver.get(MAIN);

        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("datarow")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            WebElement namecell = items.get(i).findElement(By.className("namecell"));
            if(namecell != null && namecell.getText().toLowerCase().contains("youtube"))
            {
                items.get(i).findElement(By.xpath(".//a[@title='Edit']")).click();
                i=-2;
            }
            i++;
        }

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"delbtn\"]")))
                .click();

        //Alert conferma eliminazione
        isAlertPresent(driver);
        //Alert avvenuta eliminazione
        isAlertPresent(driver);

        items = null;

        try{
            items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("datarow")));
        }catch (Exception e)
        {

        }

        assertNull(items);
    }

    @Test
    @Order(10)
    public void ImportCSV() {
        driver.get(MAIN);
        List<WebElement> items = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        int i = 0;
        while(i > -1 && i < items.size())
        {
            if(items.get(i).getAttribute("innerText").toLowerCase().contains("import"))
            {
                i=-2;
            }
            i++;
        }

        assertEquals(-1, i);
    }


    private boolean isAlertPresent(WebDriver driver) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.alertIsPresent()).accept();
            return true;
        } catch (Exception ignored) {
            return  false;
        }
    }

    @AfterAll
    public static void finish() {
        if (driver != null) {
            driver.close();
        }
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}