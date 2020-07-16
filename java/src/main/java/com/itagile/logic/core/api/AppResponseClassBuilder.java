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

import com.itagile.logic.core.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * A builder for creating a generic AppResponse.
 *
 * @param <T> the AppResponse implementation class
 * @author Javier Alcala
 * @since 1.0.0
 */
public class AppResponseClassBuilder<T extends MutableAppResponse> implements AppResponse {
    /**
     * Determines if this response was successful.
     */
    private boolean ok = true;

    /**
     * List of messages for this response.
     */
    private final List<ServiceMessage> messages = new ArrayList<>();

    /**
     * Current class type.
     */
    private Class<T> clazz;

    /**
     * Current mutable instance.
     */
    private T data;

    /**
     * Specific constructor to use.
     */
    private Supplier<T> supplier;

    /**
     * Constructor with specific supplier.
     *
     * @param supplier the specific supplier
     */
    public AppResponseClassBuilder(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Constructor with class type.
     *
     * @param clazz the class type
     */
    protected AppResponseClassBuilder(final Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Static constructor with specific constructor to use.
     *
     * @param supplier the specific constructor to use
     * @param <T> the AppResponse implementation class
     * @return the created object
     */
    public static <T extends MutableAppResponse> AppResponseClassBuilder<T> of(final Supplier<T> supplier) {
        return new AppResponseClassBuilder<>(supplier);
    }

    /**
     * Static constructor with class type.
     *
     * @param clazz the class type
     * @param <T> the AppResponse implementation class
     * @return the created object
     */
    public static <T extends MutableAppResponse> AppResponseClassBuilder<T> of(final Class<T> clazz) {
        return new AppResponseClassBuilder<>(clazz);
    }

    @Override
    public final boolean isOk() {
        return ok;
    }

    @Override
    public final List<ServiceMessage> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    /**
     * Appends an error message and changes the ok state to false.
     *
     * @param type    the type of this message
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     * @return this object
     */
    private AppResponseClassBuilder<T> addMessage(final ServiceMessageType type, final String message,
                                                  final Object... args) {
        if (args.length == 0) {
            messages.add(ServiceMessageData.of(type, message));
        } else {
            messages.add(ServiceMessageData.of(type, TextUtils.format(message, args)));
        }
        if (type == ServiceMessageType.ERROR) {
            ok = false;
        }
        return this;
    }

    /**
     * Appends an error message and changes the ok state to false.
     *
     * @param message the error message to append
     * @return this object
     */
    public AppResponseClassBuilder<T> addError(final String message) {
        return addMessage(ServiceMessageType.ERROR, message);
    }

    /**
     * Appends an error message and changes the ok state to false.
     * The message is formatted using {@link String#format(String, Object...) String.format}.
     *
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     * @return this object
     */
    public AppResponseClassBuilder<T> addError(final String message, final Object... args) {
        return addMessage(ServiceMessageType.ERROR, message, args);
    }

    /**
     * Appends a warning message.
     *
     * @param message the error message to append
     * @return this object
     */
    public AppResponseClassBuilder<T> addWarning(final String message) {
        return addMessage(ServiceMessageType.WARN, message);
    }

    /**
     * Appends a warning message.
     * The message is formatted using {@link String#format(String, Object...) String.format}.
     *
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     * @return this object
     */
    public AppResponseClassBuilder<T> addWarning(final String message, final Object... args) {
        return addMessage(ServiceMessageType.WARN, message, args);
    }

    /**
     * Appends an informative message.
     *
     * @param message the error message to append
     * @return this object
     */
    public AppResponseClassBuilder<T> addInfo(final String message) {
        return addMessage(ServiceMessageType.INFO, message);
    }

    /**
     * Appends an informative message.
     * The message is formatted using {@link String#format(String, Object...) String.format}.
     *
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     * @return this object
     */
    public AppResponseClassBuilder<T> addInfo(final String message, final Object... args) {
        return addMessage(ServiceMessageType.INFO, message, args);
    }

    /**
     * Appends all messages from response object.
     *
     * @param response the service response to append
     * @return this object
     */
    public AppResponseClassBuilder<T> addAll(final AppResponse response) {
        response.getMessages().forEach(message -> addMessage(message.getType(), message.getMessage()));
        return this;
    }

    /**
     * Gets the current mutable instance.
     *
     * @return the current mutable instance
     */
    public T getData() {
        if (data == null) {
            if (supplier == null) {
                try {
                    data = clazz.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new IllegalStateException("Failed to instantiate " + clazz.getName(), e);
                }
            } else {
                data = supplier.get();
            }
        }
        data.setOk(ok);
        data.setMessages(getMessages());
        return data;
    }

    /**
     * Builds an instance and assigns final properties values.
     *
     * @return the new instance
     */
    public T build() {
        final T dto = getData();
        dto.setOk(ok);
        dto.setMessages(this.messages);
        return dto;
    }
}
