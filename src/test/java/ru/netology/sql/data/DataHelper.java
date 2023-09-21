package ru.netology.sql.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    static Faker faker = new Faker(new Locale("en"));

    public  DataHelper(){}//пустой контейнер
    @Value
    public static class AuthUser{//объект -юзер
        String login;
        String password;
    }
    @Value
    public static class VerificationCode{//объект -код
        String code;
    }

    private static String getRandomLogin() {//генерация логина
        return faker.name().username();
    }

    private static String getRandomPassword() {//генерация пароля
        return faker.internet().password();
    }

    public static AuthUser getRandomUser() { //рандомный пользователь
        return new AuthUser(getRandomLogin(), getRandomPassword());
    }

    public static AuthUser getValidUser() { //известный пользователь
        return new AuthUser("vasya", "qwerty123");
    }

    public static VerificationCode verificationCode() {//генерация рандомного кода
        return new VerificationCode(faker.numerify("######"));
    }
}



