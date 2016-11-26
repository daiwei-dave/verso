package verso.spring;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import verso.annotation.Column;
import verso.annotation.Table;
import verso.config.Environment;
import verso.mapper.MappedResult;
import verso.mapper.impl.MappedBeanResult;
import verso.session.VSessionFactory;

/**
 * 类似{@code DaoScanner}
 */
public class PojoScanner extends ClassPathBeanDefinitionScanner {

    private VSessionFactory sessionFactory;
    
    public PojoScanner(BeanDefinitionRegistry registry) {
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
                String anno = Table.class.getCanonicalName();
                return metadataReader.getAnnotationMetadata().hasAnnotation(anno);
            }
        });
    }
    
    @Override
	public int scan(String... basePackages) {
        Environment config = sessionFactory.getConfig();
        int count = 0;
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                Class<?> clazz;
                try {
                    clazz = Class.forName(candidate.getBeanClassName());
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException("Class not found");
                }
                String key = clazz.getAnnotation(Table.class).value();
                
                MappedBeanResult mppedResult = new MappedBeanResult(clazz);
                for (Field field : clazz.getDeclaredFields()) {
                    Column column = field.getAnnotation(Column.class);
                    String name = column==null ? field.getName() : column.value();
                    mppedResult.put(name, field);
                }
                config.putResult(key, mppedResult);
                count++;
            }
        }
        return count;
    }
}
