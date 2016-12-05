package verso;

import javax.sql.DataSource;

public class MyTest {
    
    public static void main(String args[]) throws Exception {
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/webserver?useUnicode=true&characterEncoding=utf8";
        DataSource data = new VDataSource(driverClassName, url, "root", "123456");
        new VSession(data).select("select * from article where id=1", null);
    }
}
