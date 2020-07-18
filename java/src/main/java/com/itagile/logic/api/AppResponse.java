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

import java.util.List;

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
     * Determines if this response was successful.
     *
     * @return true if no errors where found.
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Sets this indicator's if this response was successful.
     *
     * @param ok true if this response was successful
     */
    public void setOk(final boolean ok) {
        this.ok = ok;
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
     * Sets the list of messages for this response.
     *
     * @param messages list of messages for this response
     */
    public void setMessages(final List<ServiceMessage> messages) {
        this.messages = messages;
    }
}
