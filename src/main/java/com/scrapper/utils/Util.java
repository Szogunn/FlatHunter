package com.scrapper.utils;

import java.util.Collection;

public class Util {
    public static final String EMPTY = "";

    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }
}
