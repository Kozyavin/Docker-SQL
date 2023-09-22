package ru.netology.sql.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Keys;
import ru.netology.sql.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public void errorNotificationLoginPage(String expectedText){ //проверка на появление сообщения об ошибке
        $("[data-test-id = error-notification] .notification__content").shouldHave(Condition.exactText(expectedText)).shouldBe(Condition.visible);
    }

    public void validLogin(DataHelper.AuthUser user) {  //метод ввода валидного логина
        //передаём клиента (пользователя)

        Selenide.$("[data-test-id = login] input").setValue(user.getLogin()); //вводим логин
        Selenide.$("[data-test-id = password] input").setValue(user.getPassword()); //вводим пароль
        Selenide.$("[data-test-id = action-login]").click(); //нажимаем кнопку "Продолжить"
    }
    public void invalidLogin(DataHelper.InvalidUserLogin user) {  //метод ввода НЕ валидного пароля, при сохранении одинакового логина
        //проверка появления сообщения о блокировке при трёхкратном вводе НЕ валидного пароля

        $("[data-test-id = login] input").setValue(user.getInvlogin()); //вводим логин

        for(int i = 0; i < 4 ; i++ ) {
            $("[data-test-id='password'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);//очистка поля пароль
            $("[data-test-id='password'] input").setValue(user.getInvpassword());
            $("[data-test-id = action-login]").click(); //нажимаем кнопку "Продолжить"
        }
    }
}
