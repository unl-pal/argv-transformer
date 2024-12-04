/**
 * 
 */
package com.github.gm.hotconf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gwendal Mousset
 *
 */
public final class HotConfigurableHooks {

    /** Class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HotConfigurableHooks.class);

    /** Hooks After by property. */
    private Map<String, List<HookInfo>> hooksBefore;

    /** Hooks After by property. */
    private Map<String, List<HookInfo>> hooksAfter;

    /**
     * Representation of a hook method.
     */
    private final class HookInfo {
        /** Owner bean. */
        private Object bean;

        /** Called method. */
        private Method method;

        /** Hook priority. */
        private int priority;
    }
}
