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

package org.apache.shenyu.springboot.plugin.websocket;

import org.apache.shenyu.common.enums.PluginEnum;
import org.apache.shenyu.plugin.api.ShenyuPlugin;
import org.apache.shenyu.plugin.base.handler.PluginDataHandler;
import org.apache.shenyu.plugin.websocket.WebSocketPlugin;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.server.WebSocketService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test case for {@link WebSocketPluginConfiguration}.
 */
public class WebSocketPluginConfigurationTest {

    @Test
    public void testWebSocketPlugin() {
        new ApplicationContextRunner()
            .withConfiguration(
                AutoConfigurations.of(
                        WebSocketPluginConfiguration.class
                ))
            .withBean(DefaultServerCodecConfigurer.class)
            .withPropertyValues("debug=true")
            .run(
                context -> {
                    assertThat(context).hasSingleBean(PluginDataHandler.class);
                    assertThat(context).hasSingleBean(WebSocketPlugin.class);
                    assertThat(context).hasSingleBean(ReactorNettyWebSocketClient.class);
                    assertThat(context).hasSingleBean(WebSocketService.class);
                    ShenyuPlugin plugin = context.getBean("webSocketPlugin", ShenyuPlugin.class);
                    assertThat(plugin.named()).isEqualTo(PluginEnum.WEB_SOCKET.getName());
                }
            );
    }
}
