/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.web.persistence;

import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class HazelcastListener {

    private HazelcastInstance hazelcastInstance;

    @PostConstruct
    private void postConstruct() {
        hazelcastInstance = hazelcastInstance();
        final IMap<Object, Object> map = hazelcastInstance.getMap("wbh-catalog");
        System.out.println(map.size());
        final Object o = map.get("12467");
        System.out.println(o);
    }

    @PreDestroy
    private void preDestroy() {
        hazelcastInstance.shutdown();
    }

    private HazelcastInstance hazelcastInstance() {
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
