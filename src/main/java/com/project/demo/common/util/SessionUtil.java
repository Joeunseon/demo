package com.project.demo.common.util;

import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    @SuppressWarnings("unchecked")
    public static <T> Set<T> getSessionSet(HttpSession session, String key, Class<T> type) {
        Object obj = session.getAttribute(key);
        if ( obj instanceof Set<?> ) {
            return (Set<T>) obj;
        }
        return new HashSet<>();
    }
}
