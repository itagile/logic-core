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

/**
 * Abstract base class for AppResponse builder implementations.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
public class AbstractAppResponseBuilder {
    /**
     * Determines if this response was successful.
     */
    private boolean ok = true;

    /**
     * List of messages for this response.
     */
    private final List<ServiceMessage> messages = new ArrayList<>();

    /**
     * Determines if this response was successful.
     *
     * @return true if no errors where found.
     */
    public final boolean isOk() {
        return ok;
    }

    /**
     * Returns the list of messages for this response.
     * The list returned is immutable.
     *
     * @return the list of messages
     */
    public final List<ServiceMessage> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    /**
     * Appends an error message and changes the ok state to false.
     *
     * @param type    the type of this message
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     */
    private void addMessage(final ServiceMessageType type, final String message,
                                                  final Object... args) {
        if (args.length == 0) {
            messages.add(ServiceMessage.of(type, message));
        } else {
            messages.add(ServiceMessage.of(type, TextUtils.format(message, args)));
        }
        if (type == ServiceMessageType.ERROR) {
            ok = false;
        }
    }

    /**
     * Appends an error message and changes the ok state to false.
     *
     * @param message the error message to append
     */
    public void addError(final String message) {
        addMessage(ServiceMessageType.ERROR, message);
    }

    /**
     * Appends an error message and changes the ok state to false.
     * The message is formatted using {@link String#format(String, Object...) String.format}.
     *
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     */
    public void addError(final String message, final Object... args) {
        addMessage(ServiceMessageType.ERROR, message, args);
    }

    /**
     * Appends a warning message.
     *
     * @param message the error message to append
     */
    public void addWarning(final String message) {
        addMessage(ServiceMessageType.WARN, message);
    }

    /**
     * Appends a warning message.
     * The message is formatted using {@link String#format(String, Object...) String.format}.
     *
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     */
    public void addWarning(final String message, final Object... args) {
        addMessage(ServiceMessageType.WARN, message, args);
    }

    /**
     * Appends an informative message.
     *
     * @param message the error message to append
     */
    public void addInfo(final String message) {
        addMessage(ServiceMessageType.INFO, message);
    }

    /**
     * Appends an informative message.
     * The message is formatted using {@link String#format(String, Object...) String.format}.
     *
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     */
    public void addInfo(final String message, final Object... args) {
        addMessage(ServiceMessageType.INFO, message, args);
    }

    /**
     * Appends all messages from response object.
     *
     * @param response the service response to append
     */
    public void addAll(final AppResponse response) {
        addAll(response.getMessages());
    }

    /**
     * Appends all messages from list.
     *
     * @param messages the list of messages
     */
    public void addAll(final List<ServiceMessage> messages) {
        messages.forEach(message -> addMessage(message.getType(), message.getMessage()));
    }

    /**
     * Assigns final properties values.
     *
     * @param dto AppResponse instance to assign values
     * @param <T> the AppResponse implementation class
     * @return the same dto instance
     */
    protected <T extends AppResponse> T setProperties(final T dto) {
        dto.setOk(ok);
        dto.setMessages(getMessages());
        return dto;
    }
}
