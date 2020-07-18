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

package com.itagile.logic.core.api;

import com.itagile.logic.core.TestUtils;
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
 * @author Javier Alcala
 * @since 1.0.0
 */
class AppResponseClassBuilderTest {

    private AppResponseClassBuilder<AppResponse> getBean() {
        return AppResponseClassBuilder.of(AppResponse::new);
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

    private void testAdd(AppResponseClassBuilder<AppResponse> bean, Consumer<String> method, boolean ok,
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

    private void testAddWithArgs(AppResponseClassBuilder<AppResponse> bean, BiConsumer<String, Object[]> method,
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
        AppResponseClassBuilder<AppResponse> bean = getBean();
        testAdd(bean, bean::addError, false, ServiceMessageType.ERROR);
    }

    @Test
    void testAddError() {
        AppResponseClassBuilder<AppResponse> bean = getBean();
        testAddWithArgs(bean, bean::addError, false, ServiceMessageType.ERROR);
    }

    @Test
    void addWarning() {
        AppResponseClassBuilder<AppResponse> bean = getBean();
        testAdd(bean, bean::addWarning, true, ServiceMessageType.WARN);
    }

    @Test
    void testAddWarning() {
        AppResponseClassBuilder<AppResponse> bean = getBean();
        testAddWithArgs(bean, bean::addWarning, true, ServiceMessageType.WARN);
    }

    @Test
    void addInfo() {
        AppResponseClassBuilder<AppResponse> bean = getBean();
        testAdd(bean, bean::addInfo, true, ServiceMessageType.INFO);
    }

    @Test
    void testAddInfo() {
        AppResponseClassBuilder<AppResponse> bean = getBean();
        testAddWithArgs(bean, bean::addInfo, true, ServiceMessageType.INFO);
    }

    @Test
    void addAll() {
        String expected = "message";
        AppResponseClassBuilder<AppResponse> bean1 = getBean();
        bean1.addError(expected);
        AppResponse dto1 = bean1.build();
        AppResponseClassBuilder<AppResponse> bean2 = getBean();
        bean2.addAll(dto1);
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
    void getDataWithClass() {
        AppResponseClassBuilder<AppResponse> bean = AppResponseClassBuilder.of(AppResponse.class);
        testData(bean.getData());
    }

    private static class AppResponseMockConstructorError extends AppResponse {
        public AppResponseMockConstructorError() throws IllegalAccessException {
            throw new IllegalAccessException();
        }
    }

    @Test
    void getDataWithClassError() {
        AppResponseClassBuilder<AppResponseMockConstructorError> bean =
                AppResponseClassBuilder.of(AppResponseMockConstructorError.class);
        assertThrows(IllegalStateException.class, bean::getData);
    }

    @Test
    void getDataWithSupplier() {
        AppResponseClassBuilder<AppResponse> bean = getBean();
        testData(bean.getData());
        testData(bean.getData());
    }

    @Test
    void build() {
        AppResponseClassBuilder<AppResponse> bean = getBean();
        testData(bean.build());
    }

    @Test
    void testJsonSerializationOk() {
        AppResponseClassBuilder<AppResponse> bean = getBean();
        AppResponse expected = bean.build();
        String json = TestUtils.toJson(expected);
        AppResponse actual = TestUtils.fromJson(json, AppResponse.class);
        assertEquals(expected.isOk(), actual.isOk());
        TestUtils.assertListEquals(expected.getMessages(), actual.getMessages());
    }

    @Test
    void testJsonSerializationWithMessages() {
        AppResponseClassBuilder<AppResponse> bean = getBean();
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