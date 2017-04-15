/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.repository;

import com.hazelcast.core.HazelcastInstance;
import eu.artofcoding.bookworm.catalog.etl.domain.AghNummer;
import eu.artofcoding.bookworm.catalog.etl.domain.Hoerbuch;
import eu.artofcoding.bookworm.catalog.etl.domain.Titelnummer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
class RepositoryHazelcastConfig {

    @Bean
    @Autowired
    Map<Titelnummer, Hoerbuch> wbhCatalog(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("wbh-catalog");
    }

    @Bean
    @Autowired
    Set<AghNummer> blistaCatalog(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getSet("blista-catalog");
    }

}
