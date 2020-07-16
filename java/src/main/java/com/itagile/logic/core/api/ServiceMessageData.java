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

/**
 * Default implementation for {@link ServiceMessage}.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
public class ServiceMessageData implements ServiceMessage {
    /**
     * Type of this message.
     */
    private ServiceMessageType type;

    /**
     * Text of this message.
     */
    private String message;

    /**
     * Static constructor using fields.
     * @param type type of this message
     * @param message text of this message
     * @return the created object
     */
    public static ServiceMessageData of(final ServiceMessageType type, final String message) {
        final ServiceMessageData data = new ServiceMessageData();
        data.setType(type);
        data.setMessage(message);
        return data;
    }

    @Override
    public final ServiceMessageType getType() {
        return type;
    }

    /**
     * Sets the type of this message.
     * @param type the type of this message
     */
    public void setType(final ServiceMessageType type) {
        this.type = type;
    }

    @Override
    public final String getMessage() {
        return message;
    }

    /**
     * Sets the text of this message.
     * @param message the text of this message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
