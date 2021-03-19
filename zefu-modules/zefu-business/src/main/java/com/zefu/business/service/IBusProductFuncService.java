package com.zefu.business.service;

import com.zefu.business.domain.BusProductFunc;
import com.zefu.business.domain.dto.request.ProductFuncItemResDto;
import com.zefu.business.domain.dto.request.prop.TemplateReqDto;
import com.zefu.business.domain.dto.response.TemplateResDto;
import com.zefu.business.enums.ProductFuncTypeEnum;

import java.util.List;

/**
 * 产品功能Service接口
 * 
 * @author linking
 * @date 2021-03-12
 */
public interface IBusProductFuncService 
{

    /**
     * 查询产品功能
     * 
     * @param id 产品功能ID
     * @return 产品功能
     */
    public BusProductFunc selectBusProductFuncById(Long id);

    /**
     * 查询产品功能列表
     * 
     * @param busProductFunc 产品功能
     * @return 产品功能集合
     */
    public List<BusProductFunc> selectBusProductFuncList(BusProductFunc busProductFunc);

    /**
     * 新增产品功能
     * 
     * @param busProductFunc 产品功能
     * @return 结果
     */
    public int insertBusProductFunc(BusProductFunc busProductFunc);

    /**
     * 修改产品功能
     * 
     * @param busProductFunc 产品功能
     * @return 结果
     */
    public int updateBusProductFunc(BusProductFunc busProductFunc);

    /**
     * 批量删除产品功能
     * 
     * @param ids 需要删除的产品功能ID
     * @return 结果
     */
    public int deleteBusProductFuncByIds(Long[] ids);

    /**
     * 删除产品功能信息
     * 
     * @param id 产品功能ID
     * @return 结果
     */
    public int deleteBusProductFuncById(Long id);

    List<ProductFuncItemResDto> ListFuncByProductCode(String productCode, Long funcStatus,
                                                      ProductFuncTypeEnum typeEnum);

    /**
     * 根据标识符查询物模型
     * @param productCode String
     * @param typeEnum ProductFuncTypeEnum
     * @param  identifier String
     * @return   ProductFuncItemResDto
     * */
    ProductFuncItemResDto queryFunc(String productCode, ProductFuncTypeEnum typeEnum, String identifier);

    public TemplateResDto template(TemplateReqDto reqDto);

    void releaseProp(Long id);
}
