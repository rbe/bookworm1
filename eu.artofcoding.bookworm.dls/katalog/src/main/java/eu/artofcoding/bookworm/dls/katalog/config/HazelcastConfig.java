/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.katalog.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.BlockingQueue;

@Configuration
public class HazelcastConfig extends WebMvcConfigurerAdapter {

    @Bean
    public TaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setThreadNamePrefix("BookwormExecutor-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    @Scope("singleton")
    public HazelcastInstance hazelcastInstance() {
        final Config config = new Config(this.getClass().getPackage().getName());
        final NetworkConfig networkConfig = new NetworkConfig()
                .setPublicAddress("127.0.0.1")
                .setInterfaces(new InterfacesConfig().setEnabled(true).addInterface("127.0.0.1"));
        config.setNetworkConfig(networkConfig);
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    @Autowired
    public BlockingQueue<String> hazelcastBlockingQueue(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getQueue("my-distributed-queue");
    }

}
