package dev.online.monitor.method.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface Audit {

    String message() default "";
}
