package ru.netology.sql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.data.SQLHelper;
import ru.netology.sql.pageobject.DashboardPage;
import ru.netology.sql.pageobject.LoginPage;
import ru.netology.sql.pageobject.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.sql.data.SQLHelper.cleanAuthCodes;

public class LoginTest {
    LoginPage loginPage;//экземпляр страницы LoginPage

    @AfterAll
    static void tearDownAll() {
        SQLHelper.cleanDatabase();
    }

    ;

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);//обращение к экземпляру открывает страницу
    }

    @Test
    void validUserTest() {

        var authInfo = DataHelper.getValidUser(); //"известного пользователя" поместили в authInfo
        loginPage.validLogin(authInfo); // открываем страницу LoginPage и заполняем поля логина/пароля "известного пользователя"
        var verificationPage = new VerificationPage();
        verificationPage.veryficationPageVisible(); //переходим на страницу VerificationPage, проверяем видимость страницы VerificationPage
        var verificationCode = SQLHelper.getVerificationCode();//значение результата метода обращения к БД за актуальным кодом кладём в переменную
        verificationPage.verifyCode(verificationCode); //заполняем поле кода на странице VeryficationPage
        var dashboardPage = new DashboardPage();
        dashboardPage.DashboardPageVisible(); //проверяем видимость страницы DashboardPage

    }

    @Test
    void invalidCodeTest() {

        var authInfo = DataHelper.getValidUser(); //"известного пользователя" поместили в authInfo
        loginPage.validLogin(authInfo); //применили метод для заполнения на странице LoginPage полей логина/пароля "известного пользователя"
        var verificationPage = new VerificationPage();
        verificationPage.veryficationPageVisible(); //проверяем видимость страницы VerificationPage

        var verificationCode = DataHelper.verificationCode();//рандомный код кладём в переменную
        verificationPage.verifyCode(verificationCode); //заполняем поле кода на странице VeryficationPage рандомным значением
        verificationPage.errorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз."); //проверяем появление сообщения об ошибке
    }

    @Test
    void invalidLoginTest() {

        var authInfo = DataHelper.getRandomUser(); //"рандомного пользователя" поместили в authInfo
        loginPage.validLogin(authInfo); //применили метод для заполнения на странице LoginPage полей логина/пароля "рандомного пользователя"
        loginPage.errorNotificationLoginPage("Ошибка! \nНеверно указан логин или пароль"); //проверяем появление сообщения об ошибке
    }

    @Test
    void EnterThreeInvalidPasswordTest() {
        var userName = DataHelper.getRandomUser();
        loginPage.invalidLogin(userName); //применили метод для заполнения на странице LoginPage полей логина/пароля

        loginPage.errorNotificationLoginPage("Страница аутентификации заблокирована. \nПопробуйте позже.");
        //проверяем появление сообщения о блокировке при 3-х кратном НЕвалидном пароле на странице LoginPage
    }
}

