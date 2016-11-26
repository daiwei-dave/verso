package verso.spring;

import static org.springframework.util.Assert.notNull;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import verso.session.VSessionFactory;

public class BaseConfigurer implements BeanDefinitionRegistryPostProcessor {

    private String daoPackage;
    private String pojoPackage;
    private VSessionFactory sessionFactory;

    public void setDaoPackage(String basePackage) {
        this.daoPackage = basePackage;
    }
    public void setPojoPackage(String basePackage) {
        this.pojoPackage = basePackage;
    }
    public void setSessionFactory(VSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        notNull(this.daoPackage, "Property 'daoPackage' is required");
        notNull(this.sessionFactory, "Property 'sessionFactory' is required");
        DaoScanner scanner = new DaoScanner(registry);
        scanner.setSessionFactory(this.sessionFactory);
        scanner.registerFilters();
        scanner.scan(daoPackage);
        
        PojoScanner pojoScanner = new PojoScanner(registry);
        pojoScanner.setSessionFactory(sessionFactory);
        pojoScanner.registerFilters();
        pojoScanner.scan(pojoPackage);
    }

}
