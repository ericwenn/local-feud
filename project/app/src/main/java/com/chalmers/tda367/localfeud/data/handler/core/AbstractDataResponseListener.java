package com.chalmers.tda367.localfeud.data.handler.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ericwenn on 5/12/16.
 */
public abstract class AbstractDataResponseListener<D> implements DataResponseListener<D> {


    /**
     * Returns the generic type D
     * @return Type generic type
     */
    public Type getType() {

        Class<?> c = this.getClass();

        Type t = c.getGenericSuperclass();

        ParameterizedType parameterized = (ParameterizedType) t;

        return parameterized.getActualTypeArguments()[0];

    }
}
