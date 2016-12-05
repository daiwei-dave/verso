package verso;

import javax.sql.DataSource;

public class MyTest {
    private static String driverClassName = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/webserver?useUnicode=true&characterEncoding=utf8";

    public static void main(String args[]) throws Exception {
        DataSource data = new VDataSource(driverClassName, url, "root", "123456");
        new VSession(data).exec("select * from article where id=1");
    }
}
