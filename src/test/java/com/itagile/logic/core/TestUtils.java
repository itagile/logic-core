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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;

/**
 * Convenience utilities for common operations with tests.
 *
 * @author Javier Alcala
 * @since 1.0.0
 */
public class TestUtils {

    /**
     * Serializes the object to Json representation.
     *
     * @param src the object to serialize
     * @return Json representation of {@code src}
     */
    public static String toJson(final Object src) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(src);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException("Json serialization error", e);
        }
    }

    /**
     * Deserializes the object from Json representation.
     *
     * @param json  the string to deserialize
     * @param clazz the class of T
     * @param <T>   the type of the desired object
     * @return an object of type T from the string
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (final IOException e) {
            throw new IllegalStateException("Json deserialization error", e);
        }
    }

    /**
     * Assert that {@code expected} and {@code actual} are equal using
     * {@link org.hamcrest.Matchers#samePropertyValuesAs} for each item.
     *
     * @param expected the expected list of values
     * @param actual   the actual list of values
     * @param <T>      the type of objects in list
     */
    public static <T> void assertListEquals(final List<T> expected, final List<T> actual) {
        assertThat("Incorrect size", actual, hasSize(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            final T actualItem = actual.get(i);
            final T expectedItem = expected.get(i);
            assertThat("Items in index " + i + " aren't equal", actualItem, samePropertyValuesAs(expectedItem));
        }
    }

}
