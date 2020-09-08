package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Selenide.$;

public class CheckboxesPage {
    private final SelenideElement checkBox1 = $("input[type=checkbox]:nth-child(1)");
    private final SelenideElement checkBox2 = $("input[type=checkbox]:nth-child(3)");

    public CheckboxesPage selectCheckbox1(boolean state) {
        checkBox1.setSelected(state);

        if(checkBox1.isSelected()) {
            checkBox1.should(checked);
            System.out.println("Checkbox 1 is checked");
        } else {
            checkBox1.shouldNot(checked);
            System.out.println("Checkbox 1 is unchecked");
        }
        return this;
    }

    public CheckboxesPage selectCheckbox2(boolean state) {
        checkBox2.setSelected(state);

        if(checkBox2.isSelected()) {
            checkBox2.should(checked);
            System.out.println("Checkbox 2 is checked");
        } else {
            checkBox2.shouldNot(checked);
            System.out.println("Checkbox 2 is unchecked");
        }
        return this;
    }

}

