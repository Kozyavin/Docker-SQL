package ru.netology.sql.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.sql.data.DataHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();//переменная класса QueryRunner, используется в библиотеке db

    private SQLHelper(){ //пустой контейнер
    }

    private static Connection getConn() throws SQLException { //метод, возвращающий подключение
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app","app","pass");
    }
    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode(){
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1"; //запрос на получение послейней записи колонки кодов (по дате)
        var conn = getConn(); //в переменную conn кладём подключение в БД
        var code = runner.query(conn,codeSQL,new ScalarHandler<String>()); //query- метод запросов. Передаём поключение, запрос, new ScalarHandler<String>() - хотим получить значение одного поля
        return new DataHelper.VerificationCode(code);
    }
    @SneakyThrows
    public static void cleanDatabase(){ //метод очистки БД
        var connection = getConn();
        runner.execute(connection,"DELETE FROM auth_codes"); //важна очерёдность
        runner.execute(connection,"DELETE FROM card_transactions");
        runner.execute(connection,"DELETE FROM cards");
        runner.execute(connection,"DELETE FROM users");
    }

}
