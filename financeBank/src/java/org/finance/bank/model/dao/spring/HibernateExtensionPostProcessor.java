package org.finance.bank.model.dao.spring;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <p>Adds Hibernate persistent class definitions to an existing Spring Session Factory bean, possibly defined
 * within a seperate Spring configuration file in a seperate jar file.
 * 
 * <p>As an example consider the following Spring bean configuration:
 * 
 * <pre>
 * &lt;bean class=&quot;dao.spring.HibernateExtensionPostProcessor&quot;&gt;
 *     &lt;property name=&quot;mappingResources&quot;&gt;
 *         &lt;list&gt;
 *             &lt;value&gt;bean/Foo.hbm.xml&lt;/value&gt;
 *         &lt;/list&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;annotatedClasses&quot;&gt;
 *         &lt;list&gt;
 *             &lt;value&gt;bean.Bar&lt;/value&gt;
 *         &lt;/list&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * <p>The snippet will add two persistent classes to an existing Session Factory bean called &quot;sessionFactory&quot;.
 */
public class HibernateExtensionPostProcessor implements BeanFactoryPostProcessor {

    private String sessionFactoryBeanName = "sessionFactory";
    private List mappingResources;
    private List annotatedClasses;
    private List configLocations;
    private Properties hibernateProperties;

    /**
     * Adds the annotated classes and the mapping resources to the existing Session Factory configuration.
     * @param configurableListableBeanFactory the good ol' bean factory
     */
    @SuppressWarnings("unchecked")
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) {
        if (configurableListableBeanFactory.containsBean(sessionFactoryBeanName)) {
            BeanDefinition sessionFactoryBeanDefinition =
                    configurableListableBeanFactory.getBeanDefinition(sessionFactoryBeanName);
            MutablePropertyValues propertyValues = sessionFactoryBeanDefinition.getPropertyValues();
            if (mappingResources != null) {
                PropertyValue propertyValue = propertyValues.getPropertyValue("mappingResources");
                if (propertyValue == null) {
                    propertyValue = new PropertyValue("mappingResources", new ArrayList());
                    propertyValues.addPropertyValue(propertyValue);
                }
                List existingMappingResources = (List) propertyValue.getValue();
                existingMappingResources.addAll(mappingResources);
            }
            if (annotatedClasses != null) {
                PropertyValue propertyValue = propertyValues.getPropertyValue("annotatedClasses");
                if (propertyValue == null) {
                    propertyValue = new PropertyValue("annotatedClasses", new ArrayList());
                    propertyValues.addPropertyValue(propertyValue);
                }
                List existingMappingResources = (List) propertyValue.getValue();
                existingMappingResources.addAll(annotatedClasses);
            }

            if (configLocations != null) {
                PropertyValue propertyValue = propertyValues.getPropertyValue("configLocations");
                if (propertyValue == null) {
                    propertyValue = new PropertyValue("configLocations", new ArrayList());
                    propertyValues.addPropertyValue(propertyValue);
                }
                List existingConfigLocations = (List) propertyValue.getValue();
                existingConfigLocations.addAll(configLocations);
            }
            if (hibernateProperties != null) {
                PropertyValue propertyValue = propertyValues.getPropertyValue("hibernateProperties");
                if (propertyValue == null) {
                    propertyValue = new PropertyValue("hibernateProperties", new Properties());
                    propertyValues.addPropertyValue(propertyValue);
                }
                Properties existingHibernateProperties = (Properties) propertyValue.getValue();
                existingHibernateProperties.putAll(hibernateProperties);
            }
        } else {
            throw new NoSuchBeanDefinitionException("No bean named [" + sessionFactoryBeanName
                    + "] exists within the bean factory. "
                    + "Cannot post process session factory to add Hibernate resource definitions.");
        }
    }

    /**
     * Set the name of the SessionFactory bean. By default this post processor looks for a
     * bean of name &quot;sessionFactory&quot;
     *
     * @param sessionFactoryBeanName The name of the session factory bean.
     */
    public void setSessionFactoryBeanName(String sessionFactoryBeanName) {
        this.sessionFactoryBeanName = sessionFactoryBeanName;
    }

    /**
     * Set the list of mapping resources (.hbm.xml files) to be added to the session factory.
     *
     * @param mappingResources The list of mapping resources.
     */
    public void setMappingResources(List mappingResources) {
        this.mappingResources = mappingResources;
    }

    /**
     * The list of annotated classes to add to the session factory.
     *
     * @param annotatedClasses The list of annotated classes that need to be added.
     */
    public void setAnnotatedClasses(List annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
    }

    /**
     * The list of configuration locations (i.e. classpath:hibernate.cfg.xml) to add to the session factory
     *
     * @param configLocations The list of configuration locations that need to be added.
     */
    public void setConfigLocations(List configLocations) {
        this.configLocations = configLocations;
    }

    /**
     * Hibernate properties to add to the session factory.
     *
     * @param hibernateProperties The list of additional properties.
     */
    public void setHibernateProperties(Properties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }
}
