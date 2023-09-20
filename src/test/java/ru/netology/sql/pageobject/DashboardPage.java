package ru.netology.sql.pageobject;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;



public class DashboardPage {

    public void DashboardPageVisible() {    //метод на проверку видимости (загрузки) окна Dashboard при создании экземпляра
        $(".heading").shouldHave(exactText(" Личный кабинет"));
    }
}
