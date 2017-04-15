/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.application;

import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
class HazelcastConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setThreadNamePrefix("bookwormCatalogExecutor-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    @Scope("singleton")
    public HazelcastInstance hazelcastInstance() {
        final Config config = new Config(this.getClass().getPackage().getName());
        final InterfacesConfig interfaces =
                new InterfacesConfig()
                        .setEnabled(true)
                        .addInterface("127.0.0.1");
        final NetworkConfig networkConfig =
                new NetworkConfig()
                        .setPublicAddress("127.0.0.1")
                        .setInterfaces(interfaces);
        networkConfig.getJoin()
                .getMulticastConfig()
                .setEnabled(false);
        networkConfig.getJoin()
                .getTcpIpConfig()
                .setEnabled(true)
                .addMember("127.0.0.1");
        config.setNetworkConfig(networkConfig);
        final ManagementCenterConfig managementCenterConfig =
                new ManagementCenterConfig()
                        .setEnabled(true)
                        .setUrl("http://localhost:8080/mancenter");
        config.setManagementCenterConfig(managementCenterConfig);
        return Hazelcast.newHazelcastInstance(config);
    }

}
