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

import com.orbitz.consul.Consul;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ConsulProducer {

    private final Logger log = LoggerFactory.getLogger(ConsulProducer.class);

    // Consul
    private String consulHost = "http://localDUMMYhost";
    private Integer consulPort = 9999;

    @Produces
    protected Consul produceConsul() {

        final Config config = ConfigProvider.getConfig();
        config.getOptionalValue("CONSUL_HOST", String.class).ifPresent(host -> consulHost = host);
        config.getOptionalValue("CONSUL_PORT", Integer.class).ifPresent(port -> consulPort = port);

        log.info("Consul host and port " + consulHost + ":" + consulPort);
        Consul consul = Consul.builder().withUrl(consulHost + ":" + consulPort).build();

        return consul;
    }
}
