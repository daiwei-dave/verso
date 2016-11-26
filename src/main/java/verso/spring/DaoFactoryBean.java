package verso.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.dao.support.DaoSupport;

import static org.springframework.util.Assert.notNull;
import verso.config.Environment;
import verso.session.VSession;
import verso.session.VSessionFactory;

public class DaoFactoryBean<T> extends DaoSupport implements FactoryBean<T> {

    private Class<T> clazz;
    private VSession session;
    
    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    public void setSessionFactory(VSessionFactory factory) {
        session = factory.getSession();
    }
    
    @Override
    public T getObject() throws Exception {
        return session.getDao(clazz);
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        notNull(session, "Property 'sessionFactory' are required");
        // TODO
    }

}
