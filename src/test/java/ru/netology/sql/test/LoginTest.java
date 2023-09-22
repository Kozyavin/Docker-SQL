package ru.netology.sql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.data.SQLHelper;
import ru.netology.sql.pageobject.DashboardPage;
import ru.netology.sql.pageobject.LoginPage;
import ru.netology.sql.pageobject.VerificationPage;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest {



    @AfterAll
    static void tearDownAll() {
        SQLHelper.cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999", LoginPage.class);
    }
    LoginPage loginPage = new LoginPage(); //создали экземпляр класса страницы логина/пароля
    VerificationPage verificationPage = new VerificationPage();
    DashboardPage dashboardPage = new DashboardPage();

    @Test
    void validUserTest() {

        var authInfo = DataHelper.getValidUser(); //"известного пользователя" поместили в authInfo
        loginPage.validLogin(authInfo); //применили метод для заполнения на странице LoginPage полей логина/пароля "известного пользователя"
        verificationPage.veryficationPageVisible(); //проверяем видимость страницы VerificationPage

        var verificationCode = SQLHelper.getVerificationCode();//значение результата метода обращения к БД за актуальным кодом кладём в переменную
        verificationPage.verifyCode(verificationCode); //заполняем поле кода на странице VeryficationPage
        dashboardPage.DashboardPageVisible(); //проверяем видимость страницы DashboardPage

    }

    @Test
    void invalidLoginTest() {

        var authInfo = DataHelper.getRandomUser(); //"рандомного пользователя" поместили в authInfo
        loginPage.validLogin(authInfo); //применили метод для заполнения на странице LoginPage полей логина/пароля "рандомного пользователя"
        verificationPage.errorNotification("Ошибка! \nНеверно указан логин или пароль"); //проверяем появление сообщения об ошибке
    }

    @Test
    void invalidCodeTest() {

        var authInfo = DataHelper.getValidUser(); //"известного пользователя" поместили в authInfo
        loginPage.validLogin(authInfo); //применили метод для заполнения на странице LoginPage полей логина/пароля "известного пользователя"
        verificationPage.veryficationPageVisible(); //проверяем видимость страницы VerificationPage

        var verificationCode = DataHelper.verificationCode();//рандомный код кладём в переменную
        verificationPage.verifyCode(verificationCode); //заполняем поле кода на странице VeryficationPage рандомным значением
        verificationPage.errorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз."); //проверяем появление сообщения об ошибке
    }

    @Test
    void EnterThreeInvalidPasswordTest() {
        var userName = DataHelper.getInvalidUser();
        loginPage.invalidLogin(userName); //применили метод для заполнения на странице LoginPage полей логина/пароля

        loginPage.errorNotificationLoginPage("Страница аутентификации заблокирована. \nПопробуйте позже.");
        //проверяем появление сообщения о блокировке при 3-х кратном НЕвалидном пароле на странице LoginPage
    }
}

