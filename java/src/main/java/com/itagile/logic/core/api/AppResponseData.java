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

import java.util.Collections;
import java.util.List;

/**
 * Default implementation for {@link AppResponse}.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
public class AppResponseData implements MutableAppResponse {
    /**
     * Determines if this response was successful.
     */
    private boolean ok;

    /**
     * List of messages for this response.
     */
    private List<ServiceMessage> messages;

    @Override
    public final boolean isOk() {
        return ok;
    }

    @Override
    public final void setOk(final boolean ok) {
        this.ok = ok;
    }

    @Override
    public final List<ServiceMessage> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public final void setMessages(final List<ServiceMessage> messages) {
        this.messages = messages;
    }
}
