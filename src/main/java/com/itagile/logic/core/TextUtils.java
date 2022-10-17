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

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Miscellaneous text utility methods.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
public final class TextUtils {

    /**
     * Hides default constructor to disallow instantiating.
     */
    private TextUtils() {
        super();
    }

    /**
     * Creates a MessageFormat with the given pattern and uses it to format the given arguments.
     * <p>
     * This implementation uses {@link java.text.MessageFormat#format(String, Object...) MessageFormat.format}
     * managing java.time classes.
     *
     * @param pattern the pattern string
     * @param args    arguments referenced by the format specifiers in the format string
     * @return this object
     * @see java.text.MessageFormat
     */
    public static String format(final String pattern, final Object... args) {
        return format(null, pattern, args);
    }

    /**
     * Creates a MessageFormat with the given locale and pattern and uses it to format the given arguments.
     * <p>
     * This implementation uses {@link java.text.MessageFormat#format(String, Object...) MessageFormat.format}
     * managing java.time classes.
     *
     * @param locale  the locale to use
     * @param pattern the error pattern to append
     * @param args    arguments referenced by the format specifiers in the format string
     * @return this object
     * @see java.text.MessageFormat
     */
    public static String format(final Locale locale, final String pattern, final Object... args) {
        final String text;
        if (args.length == 0) {
            text = pattern;
        } else {
            final MessageFormat messageFormat;
            if (locale == null) {
                messageFormat = new MessageFormat(pattern);
            } else {
                messageFormat = new MessageFormat(pattern, locale);
            }
            text = messageFormat.format(args);
        }
        return text;
    }

}
