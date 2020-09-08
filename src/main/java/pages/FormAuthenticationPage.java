package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class FormAuthenticationPage {
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $(".fa-sign-in");
    private final SelenideElement messageFlash = $("#flash");

    public FormAuthenticationPage loginSuccessful(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        messageFlash.should(text("You logged into a secure area!"));
        return this;
    }

    public FormAuthenticationPage loginInvalidUsername(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        messageFlash.should(text("Your username is invalid!"));
        return this;
    }

    public FormAuthenticationPage loginInvalidPassword(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        messageFlash.should(text("Your password is invalid!"));
        return this;
    }

    public FormAuthenticationPage loginMissedUsername(String password) {
        passwordInput.setValue(password);
        loginButton.click();
        messageFlash.should(text("Your username is invalid!"));
        return this;
    }

    public FormAuthenticationPage loginMissedPassword(String username) {
        usernameInput.setValue(username);
        loginButton.click();
        messageFlash.should(text("Your password is invalid!"));
        return this;
    }
}
