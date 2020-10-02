package tests;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.CheckboxesPage;
import pages.FormAuthenticationPage;
import pages.InputsPage;

import java.io.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TestHerokuApp {

    private final static String APP_URL = "http://the-internet.herokuapp.com";
    private final BasePage basePage = new BasePage();

    @Test
    public void formAuthentication() {
        open(APP_URL + "/login");

        FormAuthenticationPage formAuthenticationPage = new FormAuthenticationPage();

        formAuthenticationPage.loginSuccessful("tomsmith", "SuperSecretPassword!");
        back();
        formAuthenticationPage
                .loginInvalidUsername("tom","SuperSecretPassword!")
                .loginInvalidPassword("tomsmith","Password!")
                .loginMissedUsername("SuperSecretPassword!")
                .loginMissedPassword("tomsmith");
    }

    @Test
    public void checkBox() {
        open(APP_URL + "/checkboxes");
        basePage.verifyPageTitle("Checkboxes");

        CheckboxesPage checkboxesPage = new CheckboxesPage();

        checkboxesPage
                .selectCheckbox1(true)
                .selectCheckbox2(false);
    }

    @Test
    public void inputsNumbers() {
        open(APP_URL + "/inputs");

        InputsPage inputsPage = new InputsPage();

        inputsPage.setValue("40");
        refresh();
        inputsPage.setValueByArrows(Keys.ARROW_UP, 38);
    }

    @Test
    public void addElement() {
        open(APP_URL + "/add_remove_elements/");
        basePage.verifyPageTitle("Add/Remove Elements");
        $("button[onclick='addElement()']").click();
        $(".added-manually").should(visible);
    }

    @Test
    public void deleteElement() {
        open(APP_URL + "/add_remove_elements/");
        basePage.verifyPageTitle("Add/Remove Elements");
        $("button[onclick='addElement()']").click();
        $(".added-manually").click();
        $(".added-manually").should(disappear);
    }

    /*@Test
    public void contextMenu() {
        open(APP_URL + "/context_menu");
        basePage.verifyPageTitle("Context Menu");
        $("#hot-spot").contextClick();
        switchTo().alert().accept();
        refresh();
    }*/

    @Test
    public void basicAuth() {
        open(APP_URL + "/basic_auth",
                "",
                "admin",
                "admin");
        $("div > p").should(text("Congratulations! You must have the proper credentials."));
        basePage.verifyPageTitle("Basic Auth");
     }

    @Test
    public void digestAuth() {
        open(APP_URL + "/digest_auth",
                "",
                "admin",
                "admin");
        $("div > p").should(text("Congratulations! You must have the proper credentials."));
        basePage.verifyPageTitle("Digest Auth");
    }


    @Test
    public void dropdown() {
        open(APP_URL + "/dropdown");
        basePage.verifyPageTitle("Dropdown List");
        $("#dropdown").selectOption("Option 1");
        $("option:nth-child(2)").shouldBe(selected);
        refresh();

        open(APP_URL + "/dropdown");
        $("#dropdown").selectOption(2);
        $("option:nth-child(3)").shouldBe(selected);
    }

    @Test
    public void download() throws IOException {
        open(APP_URL + "/download");
        basePage.verifyPageTitle("File Downloader");
        File file = $("a:nth-child(2)").download();

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("file doesn't exist");
        }
    }

    @Test
    public void upload() {
        open(APP_URL + "/upload");
        basePage.verifyPageTitle("File Uploader");
        File file = new File("C:/Users/ahryn/Desktop/Samples/logo5.png");
        $("#file-upload").uploadFile(file);
        $("#file-submit").click();
        $("#uploaded-files").should(text("logo5.png"));
    }

    @Test
    public void forgotPassword() {
        open(APP_URL + "/forgot_password");
        $("#email").setValue("HrynkivAnastasiia@gmail.com");
        $("#form_submit").click();
        $(new Selectors.ByText("Your e-mail's been sent!")).should(appear);
    }

    @Test
    public void horizontalSlider() {
        open(APP_URL + "/horizontal_slider");
        basePage.verifyPageTitle("Horizontal Slider");
        SelenideElement slider = $("input[type='range']");
        actions().dragAndDropBy(slider, 7, 0).perform();
        $("div>#range").should(text("3"));
    }

    @Test
    public void hovers() {
        open(APP_URL + "/hovers");
        basePage.verifyPageTitle("Hovers");
        $("div:nth-child(3)").hover();
        $(new Selectors.ByText("name: user1")).should(appear);
        $("div:nth-child(4)").hover();
        $(new Selectors.ByText("name: user2")).should(appear);
        $("div:nth-child(5)").hover();
        $(new Selectors.ByText("name: user3")).should(appear);
    }

    @Test
    public void alerts() {
        open(APP_URL + "/javascript_alerts");
        basePage.verifyPageTitle("JavaScript Alerts");
        $("button[onclick='jsAlert()']").click();
        switchTo().alert().accept();
        $(new Selectors.ByText("You successfuly clicked an alert")).should(appear);
        $("button[onclick='jsConfirm()']").click();
        confirm("I am a JS Confirm");
        $(new Selectors.ByText("You clicked: Ok")).should(appear);
        $("button[onclick='jsConfirm()']").click();
        dismiss("I am a JS Confirm");
        $(new Selectors.ByText("You clicked: Cancel")).should(appear);
        $("button[onclick='jsPrompt()']").click();
        prompt("Hello");
        $(new Selectors.ByText("You entered: Hello")).should(appear);
    }

    @Test
    public void newWindowOpen() {
        open(APP_URL + "/windows");
        basePage.verifyPageTitle("Opening a new window");
        $("#content > div > a").click();
        switchTo().window(1);
        $("h3").should(text("New Window"));
        closeWindow();
    }

    @Test
    public void switchToIFrame() {
        open(APP_URL + "/iframe");
        basePage.verifyPageTitle("An iFrame containing the TinyMCE WYSIWYG Editor");
        switchTo().frame("mce_0_ifr");
        $("#tinymce").setValue("Nastia");
        $("#tinymce").should(text("Nastia"));
        switchTo().defaultContent();
    }

    @Test
    public void switchToNestedFrames() {
        open(APP_URL + "/nested_frames");
        switchTo().frame("frame-top");
        switchTo().frame("frame-left");
        $("body").should(text("LEFT"));
        switchTo().defaultContent();

        switchTo().frame("frame-top");
        switchTo().frame("frame-middle");
        $("#content").should(text("MIDDLE"));
        switchTo().defaultContent();

        switchTo().frame("frame-top");
        switchTo().frame("frame-right");
        $("body").should(text("RIGHT"));
        switchTo().defaultContent();

        switchTo().frame("frame-bottom");
        $("body").should(text("BOTTOM"));
        switchTo().defaultContent();
    }

    @Test
    public void notificationMessage() {
        open(APP_URL + "/notification_message_rendered");
        basePage.verifyPageTitle("Notification Message");
        $(".example > p > a").click();
        $("#flash").should(appear).should(or("notificationText", text("Action unsuccesful, please try again"), text("Action successful")));
    }

    @Test
    public void brokenImg() {
        open(APP_URL + "/broken_images");
        basePage.verifyPageTitle("Broken Images");
        $("img:nth-child(2)").shouldNot(image);
        $("img:nth-child(3)").shouldNot(image);
        $("img:nth-child(4)").should(image);
    }

    @Test
    public void addModalWindow() {
        open(APP_URL + "/entry_ad");
        $(".modal-title>h3").should(text("This is a modal window"));
        $(".modal-footer>p").click();
        switchTo().defaultContent();
        basePage.verifyPageTitle("Entry Ad");
    }

    @Test
    public void keyPresses() {
        open(APP_URL + "/key_presses");
        basePage.verifyPageTitle("Key Presses");
        $("#target").sendKeys(Keys.SPACE);
        $("#result").should(text("You entered: SPACE"));
        $("#target").sendKeys(Keys.ARROW_UP);
        $("#result").should(text("You entered: UP"));
        $("#target").sendKeys(Keys.ARROW_DOWN);
        $("#result").should(text("You entered: DOWN"));
        $("#target").sendKeys(Keys.TAB);
        $("#result").should(text("You entered: TAB"));
    }

    @Test
    public void notificationMessage1() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        open(APP_URL + "/notification_message_rendered");
        basePage.verifyPageTitle("Notification Message");
        $(".example > p > a").click();
        $("#flasha").should(appear).should(or("notificationText", text("Action unsuccesful, please try again"), text("Action successful")));
    }

    @Test
    public void brokenImg1() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        open(APP_URL + "/broken_images");
        basePage.verifyPageTitle("Broken Images");
        $("img:nth-child(2)").should(image);
        $("img:nth-child(3)").should(image);
        $("img:nth-child(4)").should(image);
    }

    @Test
    public void keyPresses1() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        open(APP_URL + "/key_presses");
        basePage.verifyPageTitle("Key Presses");
        $("#target").sendKeys(Keys.SPACE);
        $("#result").should(text("You entered: ENTER"));
        $("#target").sendKeys(Keys.ARROW_UP);
        $("#result").should(text("You entered: UP"));
        $("#target").sendKeys(Keys.ARROW_DOWN);
        $("#result").should(text("You entered: DOWN"));
        $("#target").sendKeys(Keys.TAB);
        $("#result").should(text("You entered: TAB"));
    }

}