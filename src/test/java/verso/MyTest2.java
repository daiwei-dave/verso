package verso;

import verso.session.Session;
import verso.session.SessionFactory;
import verso.session.VSessionFactory;

public class MyTest2 {
    
    public static void main(String args[]) throws Exception {
        SessionFactory factory = new VSessionFactory("config.xml");
        Session session = factory.openSession();
        session.exec("select * from article where id<30");
    }
}