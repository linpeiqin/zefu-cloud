package com.zefu.common.core.utils.bus;

import org.springframework.beans.BeanUtils;

public class ObjectCopyUtil {

    /**
     * 对象属性拷贝 <br>
     * 将源对象的属性拷贝到目标对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
            BeanUtils.copyProperties(source, target);
    }
}
