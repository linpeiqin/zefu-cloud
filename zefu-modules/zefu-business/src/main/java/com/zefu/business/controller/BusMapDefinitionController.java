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
import com.zefu.business.domain.BusMapDefinition;
import com.zefu.business.service.IBusMapDefinitionService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 地图定义Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/mapdefinition")
public class BusMapDefinitionController extends BaseController
{
    @Autowired
    private IBusMapDefinitionService busMapDefinitionService;

    /**
     * 查询地图定义列表
     */
    @PreAuthorize(hasPermi = "business:mapdefinition:list")
    @GetMapping("/list")
    public TableDataInfo list(BusMapDefinition busMapDefinition)
    {
        startPage();
        List<BusMapDefinition> list = busMapDefinitionService.selectBusMapDefinitionList(busMapDefinition);
        return getDataTable(list);
    }

    /**
     * 导出地图定义列表
     */
    @PreAuthorize(hasPermi = "business:mapdefinition:export")
    @Log(title = "地图定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusMapDefinition busMapDefinition) throws IOException
    {
        List<BusMapDefinition> list = busMapDefinitionService.selectBusMapDefinitionList(busMapDefinition);
        ExcelUtil<BusMapDefinition> util = new ExcelUtil<BusMapDefinition>(BusMapDefinition.class);
        util.exportExcel(response, list, "mapdefinition");
    }

    /**
     * 获取地图定义详细信息
     */
    @PreAuthorize(hasPermi = "business:mapdefinition:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busMapDefinitionService.selectBusMapDefinitionById(id));
    }

    /**
     * 新增地图定义
     */
    @PreAuthorize(hasPermi = "business:mapdefinition:add")
    @Log(title = "地图定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusMapDefinition busMapDefinition)
    {
        return toAjax(busMapDefinitionService.insertBusMapDefinition(busMapDefinition));
    }

    /**
     * 修改地图定义
     */
    @PreAuthorize(hasPermi = "business:mapdefinition:edit")
    @Log(title = "地图定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusMapDefinition busMapDefinition)
    {
        return toAjax(busMapDefinitionService.updateBusMapDefinition(busMapDefinition));
    }

    /**
     * 删除地图定义
     */
    @PreAuthorize(hasPermi = "business:mapdefinition:remove")
    @Log(title = "地图定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busMapDefinitionService.deleteBusMapDefinitionByIds(ids));
    }
}
