/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.agoncal.sample.arquilian.consul;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.UUID;

import static com.orbitz.consul.model.agent.Registration.RegCheck.http;

@Singleton
@Startup
public class ConsulManagementService {

    private final Logger log = LoggerFactory.getLogger(ConsulManagementService.class);

    // Number Resource
    private static final String NUMBER_RESOURCE_NAME = "CONSUL_NUMBER_RESOURCE";
    private String numberResourceHost = "http://localhost";
    private Integer numberResourcePort = 8080;
    // Consul
    @Inject
    private Consul consul;
    private AgentClient agentClient;

    @PostConstruct
    protected void registerNumberResource() {

        final Config config = ConfigProvider.getConfig();
        config.getOptionalValue("NUMBER_API_HOST", String.class).ifPresent(host -> numberResourceHost = host);
        config.getOptionalValue("NUMBER_API_PORT", Integer.class).ifPresent(port -> numberResourcePort = port);

        final String healthURL = numberResourceHost + ":" + numberResourcePort + "/sampleArquilianConsul/api/numbers/health";

        log.info("Number resource host and port " + numberResourceHost + ":" + numberResourcePort);
        log.info("Number resource health URL " + healthURL);

        agentClient = consul.agentClient();

        final ImmutableRegistration registration =
            ImmutableRegistration.builder()
                                 .id(UUID.randomUUID().toString())
                                 .name(NUMBER_RESOURCE_NAME)
                                 .address(numberResourceHost)
                                 .port(numberResourcePort)
                                 .check(http(healthURL, 5))
                                 .build();
        agentClient.register(registration);

        log.info(NUMBER_RESOURCE_NAME + " is registered in consul on " + numberResourceHost + ":" + numberResourcePort);
    }

    @PreDestroy
    protected void unregisterNumberResource() {

        agentClient.deregister(NUMBER_RESOURCE_NAME);

        log.info(NUMBER_RESOURCE_NAME + " is un-registered from consul");
    }
}
