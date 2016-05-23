package com.chalmers.tda367.localfeud.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-04-12
 */
public class GsonHandler
{
    private final Gson gson;
    private static GsonHandler instance = null;

    private GsonHandler() {
        gson = new Gson();
    }

    public synchronized static GsonHandler getInstance() {
        if (instance == null) {
            instance = new GsonHandler();
        }
        return instance;
    }

    public <E> E fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
