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

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    private AppResponseClassBuilder<AppResponseData> getBean() {
        return AppResponseClassBuilder.of(AppResponseData::new);
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

    private void testAdd(AppResponseClassBuilder<AppResponseData> bean, Function<String,
            AppResponseClassBuilder<AppResponseData>> method, boolean ok, ServiceMessageType type) {
        String expected = "message";
        assertSame(bean, method.apply(expected));
        assertEquals(ok, bean.isOk());
        List<ServiceMessage> messages = bean.getMessages();
        assertEquals(1, messages.size());
        ServiceMessage message = messages.get(0);
        assertEquals(expected, message.getMessage());
        assertEquals(type, message.getType());
    }

    private void testAddWithArgs(AppResponseClassBuilder<AppResponseData> bean, BiFunction<String, Object[],
            AppResponseClassBuilder<AppResponseData>> method, boolean ok, ServiceMessageType type) {
        assertSame(bean, method.apply("message {0} {1}", new Object[] {"a", 1}));
        assertEquals(ok, bean.isOk());
        List<ServiceMessage> messages = bean.getMessages();
        assertEquals(1, messages.size());
        ServiceMessage message = messages.get(0);
        assertEquals("message a 1", message.getMessage());
        assertEquals(type, message.getType());
    }

    @Test
    void addError() {
        AppResponseClassBuilder<AppResponseData> bean = getBean();
        testAdd(bean, bean::addError, false, ServiceMessageType.ERROR);
    }

    @Test
    void testAddError() {
        AppResponseClassBuilder<AppResponseData> bean = getBean();
        testAddWithArgs(bean, bean::addError, false, ServiceMessageType.ERROR);
    }

    @Test
    void addWarning() {
        AppResponseClassBuilder<AppResponseData> bean = getBean();
        testAdd(bean, bean::addWarning, true, ServiceMessageType.WARN);
    }

    @Test
    void testAddWarning() {
        AppResponseClassBuilder<AppResponseData> bean = getBean();
        testAddWithArgs(bean, bean::addWarning, true, ServiceMessageType.WARN);
    }

    @Test
    void addInfo() {
        AppResponseClassBuilder<AppResponseData> bean = getBean();
        testAdd(bean, bean::addInfo, true, ServiceMessageType.INFO);
    }

    @Test
    void testAddInfo() {
        AppResponseClassBuilder<AppResponseData> bean = getBean();
        testAddWithArgs(bean, bean::addInfo, true, ServiceMessageType.INFO);
    }

    @Test
    void addAll() {
        String expected = "message";
        AppResponseClassBuilder<AppResponseData> bean1 = getBean();
        bean1.addError(expected);
        AppResponseClassBuilder<AppResponseData> bean2 = getBean();
        assertSame(bean2, bean2.addAll(bean1));
        assertEquals(bean1.isOk(), bean2.isOk());
        List<ServiceMessage> messages = bean2.getMessages();
        assertEquals(1, messages.size());
        ServiceMessage message = messages.get(0);
        assertEquals(expected, message.getMessage());
        assertEquals(ServiceMessageType.ERROR, message.getType());
    }

    private void testData(AppResponseData data) {
        assertThat(data, instanceOf(AppResponseData.class));
        assertTrue(data.isOk());
        assertThat(data.getMessages(), is(empty()));
    }

    @Test
    void getDataWithClass() {
        AppResponseClassBuilder<AppResponseData> bean = AppResponseClassBuilder.of(AppResponseData.class);
        testData(bean.getData());
    }

    private static class AppResponseDataMockConstructorError extends AppResponseData {
        public AppResponseDataMockConstructorError() throws IllegalAccessException {
            throw new IllegalAccessException();
        }
    }

    @Test
    void getDataWithClassError() {
        AppResponseClassBuilder<AppResponseDataMockConstructorError> bean = AppResponseClassBuilder.of(AppResponseDataMockConstructorError.class);
        assertThrows(IllegalStateException.class, bean::getData);
    }

    @Test
    void getDataWithSupplier() {
        AppResponseClassBuilder<AppResponseData> bean = getBean();
        testData(bean.getData());
        testData(bean.getData());
    }

    @Test
    void build() {
        AppResponseClassBuilder<AppResponseData> bean = getBean();
        testData(bean.build());
    }
}