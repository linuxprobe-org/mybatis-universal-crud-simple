package org.linuxprobe.crud.core.annoatation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * class object save to blob
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface BlobHandler {
}
