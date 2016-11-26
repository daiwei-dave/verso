package verso.spring;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import verso.session.VSessionFactory;

public class DaoScanner extends ClassPathBeanDefinitionScanner {

    private VSessionFactory sessionFactory;
    
    public DaoScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void setSessionFactory(VSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void registerFilters() {
        this.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                    throws IOException {
                return true;
            }
        });
    }
    
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
    }
    
    /**
     * 具体参见super.doScan(basePackages)中调用findCandidateComponents来找到所有包下需要的组件
     * 查找过程中会判断满足includeFilter才能放过，因此在registerFilters()函数中新增filter，将所有类放过
     * 另外还会判断读入的类isCandidateComponent，原来判断其非接口、非抽象类，改为判断必须为接口才放过
     */
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition def = (GenericBeanDefinition) holder.getBeanDefinition();
            MutablePropertyValues pv = def.getPropertyValues();
            pv.add("clazz", def.getBeanClassName());
            pv.add("sessionFactory", sessionFactory);
            def.setBeanClass(DaoFactoryBean.class);
            def.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
        return beanDefinitions;
    }
}
