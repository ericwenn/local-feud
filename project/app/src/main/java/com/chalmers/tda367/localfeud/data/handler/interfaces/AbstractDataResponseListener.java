package com.chalmers.tda367.localfeud.data.handler.interfaces;

import java.lang.reflect.Type;

/**
 * Created by ericwenn on 5/12/16.
 */
public abstract class AbstractDataResponseListener<D> implements DataResponseListener<D> {


    public Type getType() {

        Class<?> c = this.getClass();
        return c.getGenericSuperclass();
    }
}
