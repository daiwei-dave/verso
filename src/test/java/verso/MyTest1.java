package verso;

import javax.sql.DataSource;

import verso.session.Session;
import verso.session.VSession;

public class MyTest1 {
    private static String driverClassName = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/webserver?useUnicode=true&characterEncoding=utf8";

    public static void main(String args[]) throws Exception {
        DataSource data = new VDataSource(driverClassName, url, "root", "123456");
        Session session = new VSession(data);
        session.exec("select * from article where id=1");
    }
}
