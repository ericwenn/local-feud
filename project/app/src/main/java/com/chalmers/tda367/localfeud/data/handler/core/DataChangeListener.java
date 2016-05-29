package com.chalmers.tda367.localfeud.data.handler.core;

/**
 *  A interface that listens for changes on T.
 */
public interface DataChangeListener<T> {
    void onChange( T oldValue, T newValue );
}
