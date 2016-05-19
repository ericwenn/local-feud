package com.chalmers.tda367.localfeud.data.handler.interfaces;

/**
 * Created by ericwenn on 5/18/16.
 */
public interface DataChangeListener<T> {
    void onChange( T oldValue, T newValue );
}
