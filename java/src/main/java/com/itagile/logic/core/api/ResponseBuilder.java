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

import java.util.List;

/**
 * @author Javier Alcala
 * @since 1.0.0
 */
public interface ResponseBuilder {
    /**
     * Determines if this response was successful.
     *
     * @return true if no errors where found.
     */
    boolean isOk();

    /**
     * Returns the list of messages for this response.
     * The list returned is immutable.
     *
     * @return the list of messages
     */
    List<ServiceMessage> getMessages();

    /**
     * Appends an error message and changes the ok state to false.
     *
     * @param message the error message to append
     */
    void addError(String message);

    /**
     * Appends an error message and changes the ok state to false.
     * The message is formatted using {@link String#format(String, Object...) String.format}.
     *
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     */
    void addError(String message, Object... args);

    /**
     * Appends a warning message.
     *
     * @param message the error message to append
     */
    void addWarning(String message);

    /**
     * Appends a warning message.
     * The message is formatted using {@link String#format(String, Object...) String.format}.
     *
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     */
    void addWarning(String message, Object... args);

    /**
     * Appends an informative message.
     *
     * @param message the error message to append
     */
    void addInfo(String message);

    /**
     * Appends an informative message.
     * The message is formatted using {@link String#format(String, Object...) String.format}.
     *
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     */
    void addInfo(String message, Object... args);

    /**
     * Appends all messages from response object.
     *
     * @param response the service response to append
     */
    void addAll(AppResponse response);

    /**
     * Appends all messages from list.
     *
     * @param messages the list of messages
     */
    void addAll(List<ServiceMessage> messages);
}
