package ru.netology.sql.pageobject;

import com.codeborne.selenide.Condition;
import ru.netology.sql.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    public void veryficationPageVisible(){ //проверка на загрузку/отображение страницы
        $("[data-test-id=code] input").shouldBe(visible);
    }
    public void ErrorNotification(String expectedText){ //проверка на появление сообщения об ошибке
        $("[data-test-id = error-notification] .notification__content").shouldHave(Condition.exactText(expectedText)).shouldBe(Condition.visible);
    }
    public void verifyCode(DataHelper.VerificationCode verificationCode){

        $("[data-test-id = code] input").setValue(verificationCode.getCode());//вводим код
        $("[data-test-id = action-verify] .button__content").click();//нажимаем кнопку
    }
    /*public void verify (String verificationCode){
        $("[data-test-id = code] input").setValue(verificationCode);
        $("[data-test-id = action-verify] input").click();
    }*/
    /*public DashboardPage validCode(String verificationCode){
        return verifyCode(verificationCode);
    }*/
}
