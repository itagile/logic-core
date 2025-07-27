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

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.itagile.logic.api.AppResponse;
import com.itagile.logic.api.ServiceMessage;

/**
 * Contract for AppResponse builder implementations.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
public interface ResponseBuilder {

    /**
     * Default static factory method.
     *
     * @return the created object
     */
    static AppResponseBuilder of() {
        return new AppResponseBuilder();
    }

    /**
     * Static factory method with specific constructor to use.
     *
     * @param supplier the specific constructor to use
     * @param <T> the AppResponse implementation class
     * @return the created object
     */
    static <T extends AppResponse> AppResponseDataBuilder<T> of(final Supplier<T> supplier) {
        return new AppResponseDataBuilder<>(supplier);
    }

    /**
     * Static factory method with class type.
     *
     * @param clazz the class type
     * @param <T> the AppResponse implementation class
     * @return the created object
     */
    static <T extends AppResponse> AppResponseDataBuilder<T> of(final Class<T> clazz) {
        return new AppResponseDataBuilder<>(clazz);
    }

    /**
     * Determines if this response was successful.
     *
     * @return true if no errors where found.
     */
    boolean isOk();

    /**
     * Returns the list of messages for this response. The list returned is immutable.
     *
     * @return the list of messages
     */
    List<ServiceMessage> getMessages();

    /**
     * Returns the String joining all the messages from this response.
     *
     * @param delimiter the delimiter to be used between each message
     * @return the String joining all the messages
     */
    default String getMessages(final CharSequence delimiter) {
        return getMessages().stream().map(ServiceMessage::getMessage)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * Appends an error message and changes the ok state to false.
     *
     * @param message the error message to append
     * @return this object
     */
    ResponseBuilder addError(String message);

    /**
     * Appends an error message and changes the ok state to false. The message is formatted using
     * {@link java.text.MessageFormat#format(String, Object...) MessageFormat.format}.
     *
     * @param message the error message to append
     * @param args arguments referenced by the format specifiers in the format string
     * @return this object
     */
    ResponseBuilder addError(String message, Object... args);

    /**
     * Appends a warning message.
     *
     * @param message the error message to append
     * @return this object
     */
    ResponseBuilder addWarning(String message);

    /**
     * Appends a warning message. The message is formatted using
     * {@link java.text.MessageFormat#format(String, Object...) MessageFormat.format}.
     *
     * @param message the error message to append
     * @param args arguments referenced by the format specifiers in the format string
     * @return this object
     */
    ResponseBuilder addWarning(String message, Object... args);

    /**
     * Appends an informative message.
     *
     * @param message the error message to append
     * @return this object
     */
    ResponseBuilder addInfo(String message);

    /**
     * Appends an informative message. The message is formatted using
     * {@link java.text.MessageFormat#format(String, Object...) MessageFormat.format}.
     *
     * @param message the error message to append
     * @param args arguments referenced by the format specifiers in the format string
     * @return this object
     */
    ResponseBuilder addInfo(String message, Object... args);

    /**
     * Appends all messages from response object.
     *
     * @param response the service response to append
     * @return this object
     */
    ResponseBuilder addAll(AppResponse response);

    /**
     * Appends all messages from list.
     *
     * @param messages the list of messages
     * @return this object
     */
    ResponseBuilder addAll(Collection<ServiceMessage> messages);

    /**
     * Sets code for last added message.
     *
     * @param code code to set
     * @return this object
     */
    ResponseBuilder withCode(String code);

    /**
     * Determines if this response has a message with the code specified.
     *
     * @return true if has a message with the code specified
     */
    boolean hasCode(String code);

}
