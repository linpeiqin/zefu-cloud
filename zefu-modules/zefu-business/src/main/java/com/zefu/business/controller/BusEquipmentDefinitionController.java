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
import com.zefu.business.domain.BusEquipmentDefinition;
import com.zefu.business.service.IBusEquipmentDefinitionService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 设备定义Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/equipmentdefinition")
public class BusEquipmentDefinitionController extends BaseController
{
    @Autowired
    private IBusEquipmentDefinitionService busEquipmentDefinitionService;

    /**
     * 查询设备定义列表
     */
    @PreAuthorize(hasPermi = "business:equipmentdefinition:list")
    @GetMapping("/list")
    public TableDataInfo list(BusEquipmentDefinition busEquipmentDefinition)
    {
        startPage();
        List<BusEquipmentDefinition> list = busEquipmentDefinitionService.selectBusEquipmentDefinitionList(busEquipmentDefinition);
        return getDataTable(list);
    }

    /**
     * 导出设备定义列表
     */
    @PreAuthorize(hasPermi = "business:equipmentdefinition:export")
    @Log(title = "设备定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusEquipmentDefinition busEquipmentDefinition) throws IOException
    {
        List<BusEquipmentDefinition> list = busEquipmentDefinitionService.selectBusEquipmentDefinitionList(busEquipmentDefinition);
        ExcelUtil<BusEquipmentDefinition> util = new ExcelUtil<BusEquipmentDefinition>(BusEquipmentDefinition.class);
        util.exportExcel(response, list, "equipmentdefinition");
    }

    /**
     * 获取设备定义详细信息
     */
    @PreAuthorize(hasPermi = "business:equipmentdefinition:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busEquipmentDefinitionService.selectBusEquipmentDefinitionById(id));
    }

    /**
     * 新增设备定义
     */
    @PreAuthorize(hasPermi = "business:equipmentdefinition:add")
    @Log(title = "设备定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusEquipmentDefinition busEquipmentDefinition)
    {
        return toAjax(busEquipmentDefinitionService.insertBusEquipmentDefinition(busEquipmentDefinition));
    }

    /**
     * 修改设备定义
     */
    @PreAuthorize(hasPermi = "business:equipmentdefinition:edit")
    @Log(title = "设备定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusEquipmentDefinition busEquipmentDefinition)
    {
        return toAjax(busEquipmentDefinitionService.updateBusEquipmentDefinition(busEquipmentDefinition));
    }

    /**
     * 删除设备定义
     */
    @PreAuthorize(hasPermi = "business:equipmentdefinition:remove")
    @Log(title = "设备定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busEquipmentDefinitionService.deleteBusEquipmentDefinitionByIds(ids));
    }
}
