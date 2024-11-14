/**
 * 
 */
package com.github.gm.hotconf;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.gm.hotconf.types.AcceptedFieldTypes;

/**
 * Central class:
 * <ul>
 * <li>This object contains all configurable properties information.</li>
 * <li>This object allows property value modification.</li>
 * </ul>
 * 
 * @author Gwendal Mousset
 */
public final class HotConfigurableProperties {

    

    /** Class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HotConfigurableProperties.class);

    /**
     * Configurable hooks.
     */
    @Autowired
    private HotConfigurableHooks confHooks;

    /**
     * Property map. The key is the declared property name.
     */
    private Map<String, PropertyInfo> properties;

    /**
     * Property representation. Info : - Owner - Field
     */
    private final class PropertyInfo {
        /** Owner bean. */
        private Object bean;

        /** Field. */
        private Field field;
    }
}
