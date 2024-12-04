/**
 * 
 */
package com.github.gm.hotconf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Gwendal Mousset
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HotConfigurationHookBefore {
    /** Property name. */
    String value();

    /** Invocation priority. */
    int priority() default 0;
}
