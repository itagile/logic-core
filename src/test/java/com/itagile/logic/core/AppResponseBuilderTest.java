/*
 * Copyright (c) 2020 the original author or authors.
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

package com.itagile.logic.core;

import com.itagile.logic.api.AppResponse;
import com.itagile.logic.api.ServiceMessage;
import com.itagile.logic.api.ServiceMessageType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.*;

/**
 * AppResponseBuilder tests.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
class AppResponseBuilderTest {

    @Test
    void buildOk() {
        final AppResponseBuilder bean = new AppResponseBuilder();
        final AppResponse actual = bean.build();
        assertTrue(actual.isOk());
        assertThat(actual.getMessages(), is(empty()));
    }

    @Test
    void buildWithErrors() {
        final List<ServiceMessage> expected = Arrays.asList(
                ServiceMessage.of(ServiceMessageType.ERROR, "Error 1"),
                ServiceMessage.of(ServiceMessageType.WARN, "Warning 1"),
                ServiceMessage.of(ServiceMessageType.INFO, "Info 1")
        );
        final AppResponseBuilder bean = new AppResponseBuilder();
        final ResponseBuilder chainError = bean.addError(expected.get(0).getMessage());
        assertEquals(bean, chainError);
        final ResponseBuilder chainWarn = bean.addWarning(expected.get(1).getMessage());
        assertEquals(bean, chainWarn);
        final ResponseBuilder chainInfo = bean.addInfo(expected.get(2).getMessage());
        assertEquals(bean, chainInfo);
        final AppResponse actual = bean.build();
        assertFalse(actual.isOk());
        TestUtils.assertListEquals(expected, actual.getMessages());
    }

    @Test
    void getMessages() {
        final List<ServiceMessage> messages = Arrays.asList(
                ServiceMessage.of(ServiceMessageType.ERROR, "Error 1"),
                ServiceMessage.of(ServiceMessageType.WARN, "Warning 1"),
                ServiceMessage.of(ServiceMessageType.INFO, "Info 1")
        );
        final AppResponseBuilder bean = new AppResponseBuilder();
        bean.addAll(messages);
        final String expected = "Error 1,Warning 1,Info 1";
        final String actual = bean.getMessages(",");
        assertEquals(expected, actual);
    }

    @Test
    void withMessageProvider() {
        final String expected = "message";
        final AppResponseBuilder bean =
                new AppResponseBuilder().withMessageProvider((type, message, args) -> ServiceMessage.of(type,
                        expected));
        bean.addError("other");
        final List<ServiceMessage> messages = bean.getMessages();
        final ServiceMessage message = messages.get(0);
        assertEquals(expected, message.getMessage());
        assertEquals(ServiceMessageType.ERROR, message.getType());
    }

    @Test
    void testJsonSerializationOk() {
        final AppResponseBuilder bean = new AppResponseBuilder();
        final AppResponse expected = bean.build();
        final String json = TestUtils.toJson(expected);
        final AppResponse actual = TestUtils.fromJson(json, AppResponse.class);
        assertEquals(expected.isOk(), actual.isOk());
        TestUtils.assertListEquals(expected.getMessages(), actual.getMessages());
    }

    @Test
    void testJsonSerializationWithMessages() {
        final AppResponseBuilder bean = new AppResponseBuilder();
        bean.addError("Error 1");
        bean.addWarning("Warning 1");
        bean.addInfo("Info 1");
        final AppResponse expected = bean.build();
        final String json = TestUtils.toJson(expected);
        final AppResponse actual = TestUtils.fromJson(json, AppResponse.class);
        assertEquals(expected.isOk(), actual.isOk());
        TestUtils.assertListEquals(expected.getMessages(), actual.getMessages());
    }

}