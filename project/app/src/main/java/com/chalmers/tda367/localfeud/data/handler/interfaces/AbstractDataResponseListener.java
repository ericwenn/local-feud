package com.chalmers.tda367.localfeud.data.handler.interfaces;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ericwenn on 5/12/16.
 */
public abstract class AbstractDataResponseListener<D> implements DataResponseListener<D> {


    public Type getType() {

        Class<?> c = this.getClass();

        Type t = c.getGenericSuperclass();

        ParameterizedType parameterized = (ParameterizedType) t;

        Log.d("HEJHEJHEJ", parameterized.getActualTypeArguments()[0].toString());
        return parameterized.getActualTypeArguments()[0];

    }
}
