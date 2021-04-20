package com.zefu.common.base.annotations;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProtocolAnnotation {
    String protocolCode() default "";
    String desc() default "";
    String name() default "";
}

