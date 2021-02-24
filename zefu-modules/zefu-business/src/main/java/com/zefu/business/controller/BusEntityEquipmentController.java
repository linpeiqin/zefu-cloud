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
import com.zefu.business.domain.BusEntityEquipment;
import com.zefu.business.service.IBusEntityEquipmentService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 实体设备关联Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/entityequipment")
public class BusEntityEquipmentController extends BaseController
{
    @Autowired
    private IBusEntityEquipmentService busEntityEquipmentService;

    /**
     * 查询实体设备关联列表
     */
    @PreAuthorize(hasPermi = "business:entityequipment:list")
    @GetMapping("/list")
    public TableDataInfo list(BusEntityEquipment busEntityEquipment)
    {
        startPage();
        List<BusEntityEquipment> list = busEntityEquipmentService.selectBusEntityEquipmentList(busEntityEquipment);
        return getDataTable(list);
    }

    /**
     * 导出实体设备关联列表
     */
    @PreAuthorize(hasPermi = "business:entityequipment:export")
    @Log(title = "实体设备关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusEntityEquipment busEntityEquipment) throws IOException
    {
        List<BusEntityEquipment> list = busEntityEquipmentService.selectBusEntityEquipmentList(busEntityEquipment);
        ExcelUtil<BusEntityEquipment> util = new ExcelUtil<BusEntityEquipment>(BusEntityEquipment.class);
        util.exportExcel(response, list, "entityequipment");
    }

    /**
     * 获取实体设备关联详细信息
     */
    @PreAuthorize(hasPermi = "business:entityequipment:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busEntityEquipmentService.selectBusEntityEquipmentById(id));
    }

    /**
     * 新增实体设备关联
     */
    @PreAuthorize(hasPermi = "business:entityequipment:add")
    @Log(title = "实体设备关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusEntityEquipment busEntityEquipment)
    {
        return toAjax(busEntityEquipmentService.insertBusEntityEquipment(busEntityEquipment));
    }

    /**
     * 修改实体设备关联
     */
    @PreAuthorize(hasPermi = "business:entityequipment:edit")
    @Log(title = "实体设备关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusEntityEquipment busEntityEquipment)
    {
        return toAjax(busEntityEquipmentService.updateBusEntityEquipment(busEntityEquipment));
    }

    /**
     * 删除实体设备关联
     */
    @PreAuthorize(hasPermi = "business:entityequipment:remove")
    @Log(title = "实体设备关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busEntityEquipmentService.deleteBusEntityEquipmentByIds(ids));
    }
}
