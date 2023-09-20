package ru.netology.sql.pageobject;

import com.codeborne.selenide.Selenide;
import ru.netology.sql.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public void validLogin(DataHelper.AuthUser user) {  //метод ввода валидного логина
        //передаём клиента (пользователя)

        Selenide.$("[data-test-id = login] input").setValue(user.getLogin()); //вводим логин
        Selenide.$("[data-test-id = password] input").setValue(user.getPassword()); //вводим пароль
        Selenide.$("[data-test-id = action-login]").click(); //нажимаем кнопку "Продолжить"
    }
}
