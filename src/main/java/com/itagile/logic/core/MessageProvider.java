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

import com.itagile.logic.api.ServiceMessage;
import com.itagile.logic.api.ServiceMessageType;

/**
 * Contract for customizing ServiceMessage properties, transforming message from code, and resolving and applying
 * current locale.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
public interface MessageProvider {
    /**
     * Creates a new instance of ServiceMessage. This type can be used to customize ServiceMessage properties like
     * translating message from code to a the real text and resolving and applying current locale.
     *
     * @param type    the type of this message
     * @param message the error message to append
     * @param args    arguments referenced by the format specifiers in the format string
     * @return the new ServiceMessage instance
     */
    ServiceMessage getMessage(ServiceMessageType type, String message, Object... args);
}
