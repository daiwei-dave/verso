package verso.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;

import verso.config.Environment;
import verso.session.VSessionFactory;

public class VSessionFactoryBean implements FactoryBean<VSessionFactory> {

    private DataSource dataSource;
    private VSessionFactory sessionFactory;
    
    @Override
    public VSessionFactory getObject() throws Exception {
        if (sessionFactory != null) return sessionFactory;
        sessionFactory = new VSessionFactory();
        Environment config = new Environment();
        config.setDataSource(dataSource);
        
        sessionFactory.setConfig(config);
        return sessionFactory;
    }

    @Override
    public Class<?> getObjectType() {
        return VSessionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
