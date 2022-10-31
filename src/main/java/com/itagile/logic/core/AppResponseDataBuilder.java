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

import com.itagile.logic.api.AppResponse;

import java.util.function.Supplier;

/**
 * A builder for creating a generic AppResponse. This builder is special for objects inheriting from AppResponse.
 *
 * @param <T> the AppResponse implementation class
 * @author Javier Alcala
 * @since 1.0.0
 */
public class AppResponseDataBuilder<T extends AppResponse> extends AbstractAppResponseBuilder {
    /**
     * Current class type.
     */
    private Class<T> clazz;

    /**
     * Current mutable instance.
     */
    private T data;

    /**
     * Specific constructor to use.
     */
    private Supplier<T> supplier;

    /**
     * Constructor with specific supplier.
     *
     * @param supplier the specific supplier
     */
    public AppResponseDataBuilder(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Constructor with class type.
     *
     * @param clazz the class type
     */
    protected AppResponseDataBuilder(final Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Static factory method with specific constructor to use.
     *
     * @param supplier the specific constructor to use
     * @param <T>      the AppResponse implementation class
     * @return the created object
     */
    public static <T extends AppResponse> AppResponseDataBuilder<T> of(final Supplier<T> supplier) {
        return new AppResponseDataBuilder<>(supplier);
    }

    /**
     * Static factory method with class type.
     *
     * @param clazz the class type
     * @param <T>   the AppResponse implementation class
     * @return the created object
     */
    public static <T extends AppResponse> AppResponseDataBuilder<T> of(final Class<T> clazz) {
        return new AppResponseDataBuilder<>(clazz);
    }

    /**
     * Sets the service for custom ServiceMessage instantiation.
     *
     * @param messageProvider the service for custom ServiceMessage instantiation
     * @return this object
     */
    public AppResponseDataBuilder<T> withMessageProvider(final MessageProvider messageProvider) {
        setMessageProvider(messageProvider);
        return this;
    }

    /**
     * Builds an instance and assigns ok value and messages. Subsequent calls to this method will return the same
     * instance. The aforementioned is to ease setting other properties of the response.
     *
     * @return the new instance
     */
    public T build() {
        if (data == null) {
            if (supplier == null) {
                try {
                    data = clazz.newInstance();
                } catch (final IllegalAccessException | InstantiationException e) {
                    throw new IllegalStateException("Failed to instantiate " + clazz.getName(), e);
                }
            } else {
                data = supplier.get();
            }
        }
        data.setMessages(getMessages());
        return data;
    }
}
