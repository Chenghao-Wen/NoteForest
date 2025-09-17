package com.noteforest.utils;

/**
 * ThreadLocal utility class
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    // Provide ThreadLocal object
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    // Get value by key
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }
	
    // Store key-value pair
    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }


    // Clear ThreadLocal to prevent memory leaks
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
