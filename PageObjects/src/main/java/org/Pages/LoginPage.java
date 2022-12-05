package org.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class LoginPage extends BasePage {

    @FindBy(how = How.ID, id = "com.alfabank.qapp:id/etUsername")
    private WebElement login;
    @FindBy(how = How.ID, id = "com.alfabank.qapp:id/etPassword")
    private WebElement password;
    @FindBy(how = How.ID, id = "com.alfabank.qapp:id/text_input_end_icon")
    private WebElement showPasword;
    @FindBy(how = How.ID, id = "com.alfabank.qapp:id/btnConfirm")
    private WebElement submit;
    @FindBy(how = How.ID, id = "com.alfabank.qapp:id/tvError")
    private WebElement error;
    @FindBy(how = How.ID, id = "com.alfabank.qapp:id/tvTitle")
    private WebElement pageTitle;

    /**
     * Trying to login to application with given credentials
     * @param username Username to use
     * @param pass Password to use
     * @return True if login successful, false if there are any error
     */
    public boolean tryLogin(String username, String pass) {
        logger.info(String.format("Trying to login with username '%s' and password '%s'", username, pass));
        login.sendKeys(username);
        password.sendKeys(pass);
        submit.click();
        boolean isErrorAppeared = false;
        boolean isLoginSuccessful = false;
        do {
            isErrorAppeared = isErrorAppeared();
            isLoginSuccessful = isLoginSuccessful();
            waitForUpdate();
        } while(!isErrorAppeared && !isLoginSuccessful);
        return !isErrorAppeared || isLoginSuccessful;
    }

    public String getLogin() {
        return login.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getError() {
        return error.getText();
    }

    public void toggleShowPassword() {
        showPasword.click();
    }

    public void setPassword(String pass) {
        password.sendKeys(pass);
    }
    public boolean isPasswordVisible() {
        return showPasword.isSelected();
    }

    /**
     * Checking if error message exists. Sometimes it throws
     * {@code StaleElementReferenceException} when page reloads
     * after {@code findElements()} method but before
     * {@code getText()} method executes. In this case we assume
     * error is not appeared
     * @return true if there is error on page, false if now
     */
    protected boolean isErrorAppeared() {
        List<WebElement> errors = driver.findElements(By.id("com.alfabank.qapp:id/tvError"));
        try {
            return errors.size() == 1 && !errors.get(0).getText().isEmpty();
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Checks if 'Submit' button exists. If not, we are logged in
     * @return True if no submit button exists, false in other case.
     */
    protected boolean isLoginSuccessful() {
        return driver.findElements(By.id("com.alfabank.qapp:id/btnConfirm")).size() == 0;
    }

    @Override
    protected void waitForPageLoad() {
        wait.until((wd) -> driver.findElements(By.id("com.alfabank.qapp:id/tvTitle")).size() > 0);
    }
}
