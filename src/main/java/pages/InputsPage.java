package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;

public class InputsPage {
    private final SelenideElement inputsField = $("input[type=number]");


    public InputsPage setValue (String value) {
        inputsField.setValue(value);
        inputsField.should(value(value));
        return this;
    }

    public void setValueByArrows(Keys arrows, int x) {
        int i;
        for (i = 0; i < x; i++) {
            inputsField.sendKeys(arrows);
        }
        String value = Integer.toString(x);
        inputsField.should(value(value));
    }
}
