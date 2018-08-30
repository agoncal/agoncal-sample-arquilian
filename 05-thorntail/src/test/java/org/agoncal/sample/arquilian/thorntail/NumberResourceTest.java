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
package org.agoncal.sample.arquilian.thorntail;

import io.restassured.response.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@RunAsClient
@DefaultDeployment
public class NumberResourceTest {

//    @BeforeClass
//    public static void setup() throws Exception {
//        RestAssured.baseURI = "http://localhost:8080/sampleArquilianThorntail";
//    }

    @Test
    public void generateBookNumber() throws Exception {
        Response response =
                get("http://localhost:8080/api/numbers/book")
                        .then()
                        .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.asString()).startsWith("BK-");
    }
}
