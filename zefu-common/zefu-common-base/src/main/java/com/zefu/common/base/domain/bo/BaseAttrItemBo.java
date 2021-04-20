package com.zefu.common.base.domain.bo;

import lombok.Data;

import java.util.List;


@Data
public class BaseAttrItemBo {
    /**标识符*/
    String               identifier;
    /**数据类型*/
    String               dataType;
    /**boolean类型为false时显示的文案*/
    String              bool0;
    /**boolean类型为true时显示的文案*/
    String              bool1;
    Long                 length;
    String               unit;
    /**如果数据类型是结构体的话*/
    List<BaseAttrItemBo> data;
}
