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

import java.util.Locale;

/**
 * @author Javier Alcala
 * @since 1.0.0
 */
public interface MessageProvider {

    /**
     * Resolves the message or returns the code if doesn't exists.
     *
     * @param message the message or code to look up.
     * @param locale  the locale in which to do the lookup
     * @return the resolved message (never {@code null})
     */
    String getMessage(String message, Locale locale);

}
