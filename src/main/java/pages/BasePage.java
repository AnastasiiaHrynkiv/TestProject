package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BasePage {
    private final SelenideElement header = $("h3");

    public void verifyPageTitle (String name) {
        header.should(Condition.exactText(name));
    }
}
