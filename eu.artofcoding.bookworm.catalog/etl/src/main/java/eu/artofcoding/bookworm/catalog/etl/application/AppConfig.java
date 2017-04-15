/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.application;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import({HazelcastConfig.class})
@ComponentScan("eu.artofcoding.bookworm.catalog.etl")
@EnableScheduling
class AppConfig {

    @Bean
    static PropertyPlaceholderConfigurer bookwormProperties() {
        final PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocations(new ClassPathResource("/conf/wbh.properties"),
                new ClassPathResource("/conf/blista.properties"));
        return propertyPlaceholderConfigurer;
    }


}
