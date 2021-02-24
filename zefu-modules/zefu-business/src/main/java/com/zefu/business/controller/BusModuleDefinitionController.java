package com.zefu.business.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zefu.common.log.annotation.Log;
import com.zefu.common.log.enums.BusinessType;
import com.zefu.common.security.annotation.PreAuthorize;
import com.zefu.business.domain.BusModuleDefinition;
import com.zefu.business.service.IBusModuleDefinitionService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 模组定义Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/moduledefinition")
public class BusModuleDefinitionController extends BaseController
{
    @Autowired
    private IBusModuleDefinitionService busModuleDefinitionService;

    /**
     * 查询模组定义列表
     */
    @PreAuthorize(hasPermi = "business:moduledefinition:list")
    @GetMapping("/list")
    public TableDataInfo list(BusModuleDefinition busModuleDefinition)
    {
        startPage();
        List<BusModuleDefinition> list = busModuleDefinitionService.selectBusModuleDefinitionList(busModuleDefinition);
        return getDataTable(list);
    }

    /**
     * 导出模组定义列表
     */
    @PreAuthorize(hasPermi = "business:moduledefinition:export")
    @Log(title = "模组定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusModuleDefinition busModuleDefinition) throws IOException
    {
        List<BusModuleDefinition> list = busModuleDefinitionService.selectBusModuleDefinitionList(busModuleDefinition);
        ExcelUtil<BusModuleDefinition> util = new ExcelUtil<BusModuleDefinition>(BusModuleDefinition.class);
        util.exportExcel(response, list, "moduledefinition");
    }

    /**
     * 获取模组定义详细信息
     */
    @PreAuthorize(hasPermi = "business:moduledefinition:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busModuleDefinitionService.selectBusModuleDefinitionById(id));
    }

    /**
     * 新增模组定义
     */
    @PreAuthorize(hasPermi = "business:moduledefinition:add")
    @Log(title = "模组定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusModuleDefinition busModuleDefinition)
    {
        return toAjax(busModuleDefinitionService.insertBusModuleDefinition(busModuleDefinition));
    }

    /**
     * 修改模组定义
     */
    @PreAuthorize(hasPermi = "business:moduledefinition:edit")
    @Log(title = "模组定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusModuleDefinition busModuleDefinition)
    {
        return toAjax(busModuleDefinitionService.updateBusModuleDefinition(busModuleDefinition));
    }

    /**
     * 删除模组定义
     */
    @PreAuthorize(hasPermi = "business:moduledefinition:remove")
    @Log(title = "模组定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busModuleDefinitionService.deleteBusModuleDefinitionByIds(ids));
    }
}
