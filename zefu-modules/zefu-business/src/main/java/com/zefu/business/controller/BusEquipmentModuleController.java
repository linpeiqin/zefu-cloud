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
import com.zefu.business.domain.BusEquipmentModule;
import com.zefu.business.service.IBusEquipmentModuleService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 设备模组关联Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/equipmentmodule")
public class BusEquipmentModuleController extends BaseController
{
    @Autowired
    private IBusEquipmentModuleService busEquipmentModuleService;

    /**
     * 查询设备模组关联列表
     */
    @PreAuthorize(hasPermi = "business:equipmentmodule:list")
    @GetMapping("/list")
    public TableDataInfo list(BusEquipmentModule busEquipmentModule)
    {
        startPage();
        List<BusEquipmentModule> list = busEquipmentModuleService.selectBusEquipmentModuleList(busEquipmentModule);
        return getDataTable(list);
    }

    /**
     * 导出设备模组关联列表
     */
    @PreAuthorize(hasPermi = "business:equipmentmodule:export")
    @Log(title = "设备模组关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusEquipmentModule busEquipmentModule) throws IOException
    {
        List<BusEquipmentModule> list = busEquipmentModuleService.selectBusEquipmentModuleList(busEquipmentModule);
        ExcelUtil<BusEquipmentModule> util = new ExcelUtil<BusEquipmentModule>(BusEquipmentModule.class);
        util.exportExcel(response, list, "equipmentmodule");
    }

    /**
     * 获取设备模组关联详细信息
     */
    @PreAuthorize(hasPermi = "business:equipmentmodule:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busEquipmentModuleService.selectBusEquipmentModuleById(id));
    }

    /**
     * 新增设备模组关联
     */
    @PreAuthorize(hasPermi = "business:equipmentmodule:add")
    @Log(title = "设备模组关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusEquipmentModule busEquipmentModule)
    {
        return toAjax(busEquipmentModuleService.insertBusEquipmentModule(busEquipmentModule));
    }

    /**
     * 修改设备模组关联
     */
    @PreAuthorize(hasPermi = "business:equipmentmodule:edit")
    @Log(title = "设备模组关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusEquipmentModule busEquipmentModule)
    {
        return toAjax(busEquipmentModuleService.updateBusEquipmentModule(busEquipmentModule));
    }

    /**
     * 删除设备模组关联
     */
    @PreAuthorize(hasPermi = "business:equipmentmodule:remove")
    @Log(title = "设备模组关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busEquipmentModuleService.deleteBusEquipmentModuleByIds(ids));
    }
}
