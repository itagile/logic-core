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
 * Mutability methods for a standard application response with messages.
 *
 * @author Javier Alcala
 * @since 1.0.0
 * @see AppResponse
 */
public interface MutableAppResponse extends AppResponse {
    /**
     * Sets this indicator's if this response was successful.
     * @param ok true if this response was successful
     */
    void setOk(boolean ok);

    /**
     * Sets the list of messages for this response.
     * @param messages list of messages for this response
     */
    void setMessages(List<ServiceMessage> messages);
}
