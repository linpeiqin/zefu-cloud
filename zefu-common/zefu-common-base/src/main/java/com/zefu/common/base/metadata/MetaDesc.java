package com.zefu.common.base.metadata;


import com.zefu.common.es.config.enums.EsMetaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaDesc {
    String code() default "";
    String name() default "";
    String desc() default "";
    EsMetaType type() default EsMetaType.STRING;
}
