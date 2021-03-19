package com.zefu.business.controller;

import com.zefu.business.domain.BusProductFunc;
import com.zefu.business.service.IBusProductFuncService;
import com.zefu.business.domain.dto.request.prop.TemplateReqDto;
import com.zefu.business.domain.dto.response.TemplateResDto;
import com.zefu.common.core.exception.business.ProductFuncException;
import com.zefu.common.core.utils.SecurityUtils;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.web.page.TableDataInfo;
import com.zefu.common.log.annotation.Log;
import com.zefu.common.log.enums.BusinessType;
import com.zefu.common.security.annotation.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 产品功能Controller
 * 
 * @author linking
 * @date 2021-03-12
 */
@RestController
@RequestMapping("/func")
public class BusProductFuncController extends BaseController
{
    @Autowired
    private IBusProductFuncService busProductFuncService;

    /**
     * 查询产品功能列表
     */
    @PreAuthorize(hasPermi = "business:func:list")
    @GetMapping("/list")
    public TableDataInfo list(BusProductFunc busProductFunc)
    {
        startPage();
        List<BusProductFunc> list = busProductFuncService.selectBusProductFuncList(busProductFunc);
        return getDataTable(list);
    }

    /**
     * 导出产品功能列表
     */
    @PreAuthorize(hasPermi = "business:func:export")
    @Log(title = "产品功能", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusProductFunc busProductFunc) throws IOException
    {
        List<BusProductFunc> list = busProductFuncService.selectBusProductFuncList(busProductFunc);
        ExcelUtil<BusProductFunc> util = new ExcelUtil<BusProductFunc>(BusProductFunc.class);
        util.exportExcel(response, list, "func");
    }

    /**
     * 获取产品功能详细信息
     */
    @PreAuthorize(hasPermi = "business:func:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busProductFuncService.selectBusProductFuncById(id));
    }

    /**
     * 新增产品功能
     */
    @PreAuthorize(hasPermi = "business:func:add")
    @Log(title = "产品功能", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusProductFunc busProductFunc)
    {
        return toAjax(busProductFuncService.insertBusProductFunc(busProductFunc));
    }

    /**
     * 修改产品功能
     */
    @PreAuthorize(hasPermi = "business:func:edit")
    @Log(title = "产品功能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusProductFunc busProductFunc)
    {
        return toAjax(busProductFuncService.updateBusProductFunc(busProductFunc));
    }

    /**
     * 删除产品功能
     */
    @PreAuthorize(hasPermi = "business:func:remove")
    @Log(title = "产品功能", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busProductFuncService.deleteBusProductFuncByIds(ids));
    }

    @PreAuthorize(hasPermi = "business:func:edit")
    @PostMapping(value = "template")
    @Log(title = "产品功能", businessType = BusinessType.UPDATE)
    public AjaxResult  template(@RequestBody TemplateReqDto reqDto){
        if(StringUtils.isEmpty(reqDto.getIdentifier()) && StringUtils.isEmpty(reqDto.getProductCode())){
            logger.warn("产品编码和标识符不能同时为空");
            throw new ProductFuncException("产品编码和标识符不能同时为空");
        }
        TemplateResDto resDto = busProductFuncService.template(reqDto);
        return   AjaxResult.success(resDto);
    }


    @PreAuthorize(hasPermi = "business:func:edit")
    @GetMapping(value = "/release")
    @Log(title = "产品功能", businessType = BusinessType.UPDATE)
    public AjaxResult  propRelease(Long id){
        busProductFuncService.releaseProp(id);
        return AjaxResult.success();
    }
}
