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

package com.itagile.logic.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO representing a standard application response with messages.
 *
 * <p>If there are any errors, the response is considered to be not Ok. Messages are classified as errors, warnings
 * or, informative.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
public class AppResponse {
    /**
     * Determines if this response was successful.
     */
    private boolean ok;

    /**
     * List of messages for this response.
     */
    private List<ServiceMessage> messages;

    /**
     * Empty constructor.
     */
    public AppResponse() {
        this.messages = Collections.emptyList();
        this.ok = true;
    }

    /**
     * Constructor with messages.
     *
     * @param messages the list of messages for this response
     */
    public AppResponse(final Collection<ServiceMessage> messages) {
        this.setMessages(messages);
    }

    /**
     * Determines if this response was successful.
     *
     * @return true if no errors where found.
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Returns the list of messages for this response.
     *
     * @return the list of messages
     */
    public List<ServiceMessage> getMessages() {
        return messages;
    }

    /**
     * Returns the String joining all the messages from this response.
     *
     * @param delimiter the delimiter to be used between each message
     * @return the String joining all the messages
     */
    public String getMessages(final CharSequence delimiter) {
        return messages.stream().map(ServiceMessage::getMessage).collect(Collectors.joining(delimiter));
    }

    /**
     * Sets the list of messages for this response.
     *
     * @param messages the list of messages for this response
     */
    public void setMessages(final Collection<ServiceMessage> messages) {
        this.messages = Collections.unmodifiableList(new ArrayList<>(messages));
        this.ok = messages.stream().noneMatch(x -> x.getType() == ServiceMessageType.ERROR);
    }
}
