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

import java.util.function.Supplier;

/**
 * A builder for creating a generic AppResponse. This builder is special for objects inheriting from AppResponse.
 *
 * @param <T> the AppResponse implementation class
 * @author Javier Alcala
 * @since 1.0.0
 */
public class AppResponseClassBuilder<T extends AppResponse> extends AbstractAppResponseBuilder {
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
    public AppResponseClassBuilder(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Constructor with class type.
     *
     * @param clazz the class type
     */
    protected AppResponseClassBuilder(final Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Static constructor with specific constructor to use.
     *
     * @param supplier the specific constructor to use
     * @param <T>      the AppResponse implementation class
     * @return the created object
     */
    public static <T extends AppResponse> AppResponseClassBuilder<T> of(final Supplier<T> supplier) {
        return new AppResponseClassBuilder<>(supplier);
    }

    /**
     * Static constructor with class type.
     *
     * @param clazz the class type
     * @param <T>   the AppResponse implementation class
     * @return the created object
     */
    public static <T extends AppResponse> AppResponseClassBuilder<T> of(final Class<T> clazz) {
        return new AppResponseClassBuilder<>(clazz);
    }

    /**
     * Gets the current mutable instance.
     *
     * @return the current mutable instance
     */
    public T getData() {
        if (data == null) {
            if (supplier == null) {
                try {
                    data = clazz.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new IllegalStateException("Failed to instantiate " + clazz.getName(), e);
                }
            } else {
                data = supplier.get();
            }
        }
        return setProperties(data);
    }

    /**
     * Builds an instance and assigns final properties values.
     *
     * @return the new instance
     */
    public T build() {
        return getData();
    }
}
