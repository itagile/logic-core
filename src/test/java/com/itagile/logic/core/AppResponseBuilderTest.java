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
        AppResponseBuilder bean = new AppResponseBuilder();
        AppResponse actual = bean.build();
        assertTrue(actual.isOk());
        assertThat(actual.getMessages(), is(empty()));
    }

    @Test
    void buildWithErrors() {
        List<ServiceMessage> expected = Arrays.asList(
                ServiceMessage.of(ServiceMessageType.ERROR, "Error 1"),
                ServiceMessage.of(ServiceMessageType.WARN, "Warning 1"),
                ServiceMessage.of(ServiceMessageType.INFO, "Info 1")
        );
        AppResponseBuilder bean = new AppResponseBuilder();
        ResponseBuilder chainError = bean.addError(expected.get(0).getMessage());
        assertEquals(bean, chainError);
        ResponseBuilder chainWarn = bean.addWarning(expected.get(1).getMessage());
        assertEquals(bean, chainWarn);
        ResponseBuilder chainInfo = bean.addInfo(expected.get(2).getMessage());
        assertEquals(bean, chainInfo);
        AppResponse actual = bean.build();
        assertFalse(actual.isOk());
        TestUtils.assertListEquals(expected, actual.getMessages());
    }

    @Test
    void withMessageProvider() {
        final String expected = "message";
        AppResponseBuilder bean =
                new AppResponseBuilder().withMessageProvider((type, message, args) -> ServiceMessage.of(type,
                        expected));
        bean.addError("other");
        List<ServiceMessage> messages = bean.getMessages();
        ServiceMessage message = messages.get(0);
        assertEquals(expected, message.getMessage());
        assertEquals(ServiceMessageType.ERROR, message.getType());
    }

    @Test
    void testJsonSerializationOk() {
        AppResponseBuilder bean = new AppResponseBuilder();
        AppResponse expected = bean.build();
        String json = TestUtils.toJson(expected);
        AppResponse actual = TestUtils.fromJson(json, AppResponse.class);
        assertEquals(expected.isOk(), actual.isOk());
        TestUtils.assertListEquals(expected.getMessages(), actual.getMessages());
    }

    @Test
    void testJsonSerializationWithMessages() {
        AppResponseBuilder bean = new AppResponseBuilder();
        bean.addError("Error 1");
        bean.addWarning("Warning 1");
        bean.addInfo("Info 1");
        AppResponse expected = bean.build();
        String json = TestUtils.toJson(expected);
        AppResponse actual = TestUtils.fromJson(json, AppResponse.class);
        assertEquals(expected.isOk(), actual.isOk());
        TestUtils.assertListEquals(expected.getMessages(), actual.getMessages());
    }

}