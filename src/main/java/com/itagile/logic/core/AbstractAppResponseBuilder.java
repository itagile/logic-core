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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for AppResponse builder implementations.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
public class AbstractAppResponseBuilder implements ResponseBuilder {
    /**
     * Determines if this response was successful.
     */
    private boolean ok = true;

    /**
     * List of messages for this response.
     */
    private final List<ServiceMessage> messages = new ArrayList<>();

    /**
     * The service for custom ServiceMessage instantiation.
     */
    private MessageProvider messageProvider;

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
     * Sets the service for custom ServiceMessage instantiation.
     *
     * @param messageProvider the service for custom ServiceMessage instantiation
     */
    protected void setMessageProvider(final MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    /**
     * Appends a message and changes the ok state to false if the type is ERROR.
     *
     * @param type    the type of this message
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     * @return this object
     */
    private ResponseBuilder addMessage(final ServiceMessageType type, final String message,
                            final Object... args) {
        final ServiceMessage dto;
        if (messageProvider == null) {
            if (args.length == 0) {
                dto = ServiceMessage.of(type, message);
            } else {
                dto = ServiceMessage.of(type, TextUtils.format(message, args));
            }
        } else {
            dto = messageProvider.getMessage(type, message, args);
        }
        messages.add(dto);
        if (dto.getType() == ServiceMessageType.ERROR) {
            ok = false;
        }
        return this;
    }

    @Override
    public final ResponseBuilder addError(final String message) {
        return addMessage(ServiceMessageType.ERROR, message);
    }

    @Override
    public final ResponseBuilder addError(final String message, final Object... args) {
    	return addMessage(ServiceMessageType.ERROR, message, args);
    }

    @Override
    public final ResponseBuilder addWarning(final String message) {
    	return addMessage(ServiceMessageType.WARN, message);
    }

    @Override
    public final ResponseBuilder addWarning(final String message, final Object... args) {
    	return addMessage(ServiceMessageType.WARN, message, args);
    }

    @Override
    public final ResponseBuilder addInfo(final String message) {
    	return addMessage(ServiceMessageType.INFO, message);
    }

    @Override
    public final ResponseBuilder addInfo(final String message, final Object... args) {
    	return addMessage(ServiceMessageType.INFO, message, args);
    }

    @Override
    public final ResponseBuilder addAll(final AppResponse response) {
    	return addAll(response.getMessages());
    }

    @Override
    public final ResponseBuilder addAll(final List<ServiceMessage> messages) {
        messages.forEach(message -> addMessage(message.getType(), message.getMessage()));
        return this;
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
