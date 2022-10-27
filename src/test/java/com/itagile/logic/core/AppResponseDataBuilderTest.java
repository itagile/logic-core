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

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.*;

/**
 * AppResponseDataBuilder tests.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
class AppResponseDataBuilderTest {

    private AppResponseDataBuilder<AppResponse> getBean() {
        return AppResponseDataBuilder.of(AppResponse::new);
    }

    @Test
    void isOk() {
        assertTrue(getBean().isOk());
    }

    @Test
    void getMessages() {
        //noinspection ConstantConditions
        assertThrows(UnsupportedOperationException.class, () -> getBean().getMessages().add(null));
    }

    private void testAdd(AppResponseDataBuilder<?> bean, Consumer<String> method, boolean ok,
                         ServiceMessageType type) {
        String expected = "message";
        method.accept(expected);
        assertEquals(ok, bean.isOk());
        List<ServiceMessage> messages = bean.getMessages();
        assertEquals(1, messages.size());
        ServiceMessage message = messages.get(0);
        assertEquals(expected, message.getMessage());
        assertEquals(type, message.getType());
    }

    private void testAddWithArgs(AppResponseDataBuilder<?> bean, BiConsumer<String, Object[]> method,
                                 boolean ok, ServiceMessageType type) {
        method.accept("message {0} {1}", new Object[]{"a", 1});
        assertEquals(ok, bean.isOk());
        List<ServiceMessage> messages = bean.getMessages();
        assertEquals(1, messages.size());
        ServiceMessage message = messages.get(0);
        assertEquals("message a 1", message.getMessage());
        assertEquals(type, message.getType());
    }

    @Test
    void addError() {
        AppResponseDataBuilder<AppResponse> bean = getBean();
        testAdd(bean, bean::addError, false, ServiceMessageType.ERROR);
    }

    @Test
    void testAddError() {
        AppResponseDataBuilder<AppResponse> bean = getBean();
        testAddWithArgs(bean, bean::addError, false, ServiceMessageType.ERROR);
    }

    @Test
    void addWarning() {
        AppResponseDataBuilder<AppResponse> bean = getBean();
        testAdd(bean, bean::addWarning, true, ServiceMessageType.WARN);
    }

    @Test
    void testAddWarning() {
        AppResponseDataBuilder<AppResponse> bean = getBean();
        testAddWithArgs(bean, bean::addWarning, true, ServiceMessageType.WARN);
    }

    @Test
    void addInfo() {
        AppResponseDataBuilder<AppResponse> bean = getBean();
        testAdd(bean, bean::addInfo, true, ServiceMessageType.INFO);
    }

    @Test
    void testAddInfo() {
        AppResponseDataBuilder<AppResponse> bean = getBean();
        testAddWithArgs(bean, bean::addInfo, true, ServiceMessageType.INFO);
    }

    @Test
    void addAll() {
        String expected = "message";
        AppResponseDataBuilder<AppResponse> bean1 = getBean();
        bean1.addError(expected);
        AppResponse dto1 = bean1.build();
        AppResponseDataBuilder<AppResponse> bean2 = getBean();
        ResponseBuilder chain = bean2.addAll(dto1);
        assertEquals(bean2, chain);
        AppResponse dto2 = bean2.build();
        assertEquals(dto1.isOk(), dto2.isOk());
        List<ServiceMessage> messages = dto2.getMessages();
        assertEquals(1, messages.size());
        ServiceMessage message = messages.get(0);
        assertEquals(expected, message.getMessage());
        assertEquals(ServiceMessageType.ERROR, message.getType());
    }

    private void testData(AppResponse data) {
        assertThat(data, instanceOf(AppResponse.class));
        assertTrue(data.isOk());
        assertThat(data.getMessages(), is(empty()));
    }

    @Test
    void buildWithClass() {
        AppResponseDataBuilder<AppResponse> bean = AppResponseDataBuilder.of(AppResponse.class);
        testData(bean.build());
    }

    private static class AppResponseMockConstructorError extends AppResponse {
        public AppResponseMockConstructorError() throws IllegalAccessException {
            throw new IllegalAccessException();
        }
    }

    @Test
    void buildWithClassError() {
        AppResponseDataBuilder<AppResponseMockConstructorError> bean =
                AppResponseDataBuilder.of(AppResponseMockConstructorError.class);
        assertThrows(IllegalStateException.class, bean::build);
    }

    @Test
    void buildWithSupplier() {
        AppResponseDataBuilder<AppResponse> bean = getBean();
        AppResponse bean1 = bean.build();
        testData(bean1);
        AppResponse bean2 = bean.build();
        testData(bean2);
        assertSame(bean1, bean2);
    }

    @Test
    void withMessageProvider() {
        final String expected = "message";
        AppResponseDataBuilder<AppResponse> bean =
                getBean().withMessageProvider((type, message, args) -> ServiceMessage.of(type, expected));
        bean.addError("other");
        List<ServiceMessage> messages = bean.getMessages();
        ServiceMessage message = messages.get(0);
        assertEquals(expected, message.getMessage());
        assertEquals(ServiceMessageType.ERROR, message.getType());
    }

    @Test
    void testJsonSerializationOk() {
        AppResponseDataBuilder<AppResponse> bean = getBean();
        AppResponse expected = bean.build();
        String json = TestUtils.toJson(expected);
        AppResponse actual = TestUtils.fromJson(json, AppResponse.class);
        assertEquals(expected.isOk(), actual.isOk());
        TestUtils.assertListEquals(expected.getMessages(), actual.getMessages());
    }

    @Test
    void testJsonSerializationWithMessages() {
        AppResponseDataBuilder<AppResponse> bean = getBean();
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