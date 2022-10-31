/*
 * Copyright (c) 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itagile.logic.api;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Javier Alcala
 * @since 1.0.0
 */
class AppResponseTest {

    @Test
    void getMessages() {
        final List<ServiceMessage> messages = Arrays.asList(
                ServiceMessage.of(ServiceMessageType.ERROR, "Error 1"),
                ServiceMessage.of(ServiceMessageType.WARN, "Warning 1"),
                ServiceMessage.of(ServiceMessageType.INFO, "Info 1")
        );
        final String expected = "Error 1,Warning 1,Info 1";
        final AppResponse dto = new AppResponse(messages);
        final String actual = dto.getMessages(",");
        assertEquals(expected, actual);
    }

    @Test
    void setMessagesOk() {
        final List<ServiceMessage> messages = Arrays.asList(
                ServiceMessage.of(ServiceMessageType.WARN, "Warning 1"),
                ServiceMessage.of(ServiceMessageType.INFO, "Info 1")
        );
        final AppResponse dto = new AppResponse();
        dto.setMessages(messages);
        assertTrue(dto.isOk());
    }

    @Test
    void setMessagesError() {
        final List<ServiceMessage> messages = Arrays.asList(
                ServiceMessage.of(ServiceMessageType.ERROR, "Error 1")
        );
        final AppResponse dto = new AppResponse();
        dto.setMessages(messages);
        assertFalse(dto.isOk());
    }
}