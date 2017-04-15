/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.service;

import com.hazelcast.config.Config;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.BlockingQueue;

@Configuration
@ComponentScan("eu.artofcoding.bookworm.dls.bestellung.v03.orderstatus")
@EnableAsync
public class OrderTestAppConfig {

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
        final QueueConfig queueConfig = config.getQueueConfig(this.getClass().getPackage().getName());
        queueConfig.setName("my-distributed-queue")
                .setBackupCount(1)
                .setMaxSize(0)
                .setStatisticsEnabled(true);
        queueConfig.getQueueStoreConfig()
                .setEnabled(true)
                .setClassName("com.hazelcast.QueueStoreImpl")
                .setProperty("binary", "false");
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    @Autowired
    public BlockingQueue<String> hazelcastBlockingQueue(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getQueue("my-distributed-queue");
    }

}
