package eu.artofcoding.bookworm.dls.bestellung;

import eu.artofcoding.bookworm.common.template.Template;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackageClasses = {
        BlistaConfiguration.class,
        Template.class
})
public class AppConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource("/blista.properties"));
        return propertyPlaceholderConfigurer;
    }

}
