package org.Tests;

import org.Helpers.AssertHelper;
import org.Helpers.StringHelper;
import org.Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.commons.lang3.StringUtils;

@Test
public class LoginTests extends BaseTest {

    protected static final String defaultLoginValue = "Логин";
    protected static final String defaultPasswordValue = "Пароль";
    protected static final String defaultNotAuthorizedMessage = "Введены неверные данные";
    protected static final String defaultInvalidCharactersMessage = "Введены неверные данные";
    protected static final String defaultTooLongLineMessage = "Введены неверные данные";
    protected static final String defaultPasswordHidingSymbol = "*";

    protected LoginPage loginPage;

    @BeforeMethod
    public void BeforeTest() {
        loginPage = new LoginPage();
    }
    @Test(dataProvider = "validCredentials", description = "User should be able to login with correct credentials")
    public void successfulLoginTest(String login, String password) {
        logger.info("Checking that we can log in with valid credentials");
        Assert.assertTrue(loginPage.tryLogin(login, password), "Failed to login to Alpha App");
    }

    @Test(dataProvider = "invalidCredentials", description = "User should not be able to login with incorrect credentials")
    public void unsuccessfulLoginTest(String login, String password, String errorMessage) {
        logger.info("Checking that we can't log in with invalid credentials");
        AssertHelper.AssertMultiple(
                () -> Assert.assertFalse(loginPage.tryLogin(login, password), "Successfully logged in Alpha App"),
                () -> Assert.assertEquals(loginPage.getError(), errorMessage, "Wrong error message appeared"));
    }

    @Test(dataProvider = "illegalCredentials", description = "User should not be able to login with illegal characters in credentials")
    public void illegalCharactersLoginTest(String login, String password, String errorMessage) {
        logger.info("Checking that we can't log in with illegal characters in credentials");
        AssertHelper.AssertMultiple(
                () -> Assert.assertFalse(loginPage.tryLogin(login, password), "Successfully logged in Alpha App"),
                () -> Assert.assertEquals(loginPage.getError(), errorMessage, "Wrong error message appeared"));
    }

    @Test(dataProvider = "longCredentials", description = "Credentials should be cut of if the length is more than cut off length (default: 50)")
    public void longCredentialsTest(String login, String password, String errorMessage, int cutOffLength) {
        logger.info("Checking that long credentials are cut off");
        String expectedLogin = login.equals("")
                ? defaultLoginValue
                : StringHelper.substring(login, 0, cutOffLength);
        String expectedPassword = password.equals("")
                ? defaultPasswordValue
                : StringHelper.substring(password, 0, cutOffLength);
        AssertHelper.AssertMultiple(
                () -> Assert.assertFalse(loginPage.tryLogin(login, password), "Successfully logged in Alpha App"),
                () -> Assert.assertEquals(loginPage.getLogin(), expectedLogin, "Login did not cut off"),
                () -> Assert.assertEquals(loginPage.getPassword(), expectedPassword, "Password did not cut off"),
                () -> Assert.assertEquals(loginPage.getError(), errorMessage, "Wrong error message appeared"));
    }

    @Test(dataProvider = "passwords", description = "Password should be hidden with special character (default: '*') while show password toggle is not checked")
    public void showPasswordTest(String password) {
        logger.info("Checking that we can hide and show password with toggle");
        loginPage.setPassword(password);
        String hiddenPassword = password.equals("")
                ? defaultPasswordValue
                : StringUtils.repeat(defaultPasswordHidingSymbol, password.length());
        String actualPassword = password.equals("")
                ? defaultPasswordValue
                : password;
        if(loginPage.isPasswordVisible()) {
            loginPage.toggleShowPassword();
        }
        AssertHelper.AssertMultiple(
                () -> Assert.assertEquals(loginPage.getPassword(), hiddenPassword, "Password is not hidden"),
                () -> loginPage.toggleShowPassword(),
                () -> Assert.assertEquals(loginPage.getPassword(), actualPassword, "Password is not visible when show password is checked"));
    }

    @DataProvider(name = "validCredentials")
    public Object[][] createValidCredentials() {
        return new Object[][] {
                new Object[] {"Login", "Password"}
        };
    }

    @DataProvider(name = "passwords")
    public Object[][] createPasswords() {
        return new Object[][] {
                new Object[] {""},
                new Object[] {" "},
                new Object[] {"*****"},
                new Object[] {"qwerty"},
        };
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] createInvalidCredentials() {
        return new Object[][] {
                new Object[] {"__--''" , "qwerty!@#$%^&*()", defaultNotAuthorizedMessage},
                new Object[] {"Login" , "NotOurPassword", defaultNotAuthorizedMessage},
                new Object[] {"NotOurLogin", "Password", defaultNotAuthorizedMessage},
                new Object[] {"" , "Password", defaultNotAuthorizedMessage},
                new Object[] {"Login", "", defaultNotAuthorizedMessage},
                new Object[] {"Wrong", "Credentials", defaultNotAuthorizedMessage},
                new Object[] {"", "", defaultNotAuthorizedMessage},
                new Object[] {" ", " ", defaultNotAuthorizedMessage},
        };
    }

    @DataProvider(name = "illegalCredentials")
    public Object[][] createIllegalCredentials() {
        return new Object[][] {
                new Object[] {"Login!" , "Password", defaultInvalidCharactersMessage},
                new Object[] {"!Login!" , "Password", defaultInvalidCharactersMessage},
                new Object[] {"@$;£" , "Password", defaultInvalidCharactersMessage},
        };
    }

    @DataProvider(name = "longCredentials")
    public Object[][] createLongCredentials() {
        String longValue = "ThisIsVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryLongValue"; //length: 50+
        String notLongEnoughValue = "ThisValueIsPrettyLongButNotLongEnoughToBreakLimit"; //length: 50
        int cutOffLength = 50;
        return new Object[][] {
                new Object[] {longValue, longValue, defaultTooLongLineMessage, cutOffLength},
                new Object[] {"", longValue, defaultTooLongLineMessage, cutOffLength},
                new Object[] {longValue, "", defaultTooLongLineMessage, cutOffLength},
                new Object[] {notLongEnoughValue, "", defaultNotAuthorizedMessage, cutOffLength},
                new Object[] {"", notLongEnoughValue, defaultNotAuthorizedMessage, cutOffLength},
        };
    }

}
