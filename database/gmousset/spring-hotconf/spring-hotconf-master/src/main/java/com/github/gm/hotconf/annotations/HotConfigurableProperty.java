/**
 * 
 */
package com.github.gm.hotconf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a class field as configurable. Works on Spring instantiated beans.
 * 
 * @author Gwendal Mousset
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HotConfigurableProperty {
    /** property name. */
    String value() default "";
}
