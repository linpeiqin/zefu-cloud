package com.zefu.common.core.exception.business;

import com.zefu.common.core.exception.BaseException;

/**
 * 产品功能异常
 * 
 * @author ruoyi|linking
 */
public class ProductFuncException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public ProductFuncException(String msg)
    {
        super("product-func", null,null,msg);
    }
}
