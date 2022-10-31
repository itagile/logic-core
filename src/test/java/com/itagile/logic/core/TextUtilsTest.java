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

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Javier Alcala
 * @since 1.0.0
 */
class TextUtilsTest {

    @Test
    void formatNoArgs() {
        final String expected = "message";
        assertEquals(expected, TextUtils.format(expected));
    }

    @Test
    void formatWithArgs() {
        assertEquals("message 2020-12-01",
                TextUtils.format("message {0}",
                        LocalDate.of(2020, 12, 1)));
    }

    @Test
    void formatLocale() {
        assertEquals("message December", TextUtils.format(Locale.ENGLISH, "message {0,Date,MMMM}",
                Date.valueOf(LocalDate.of(2020, 12, 1))));
    }
}