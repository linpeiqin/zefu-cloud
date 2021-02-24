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
import com.zefu.business.domain.BusSceneDefinition;
import com.zefu.business.service.IBusSceneDefinitionService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 场景定义Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/scenedefinition")
public class BusSceneDefinitionController extends BaseController
{
    @Autowired
    private IBusSceneDefinitionService busSceneDefinitionService;

    /**
     * 查询场景定义列表
     */
    @PreAuthorize(hasPermi = "business:scenedefinition:list")
    @GetMapping("/list")
    public TableDataInfo list(BusSceneDefinition busSceneDefinition)
    {
        startPage();
        List<BusSceneDefinition> list = busSceneDefinitionService.selectBusSceneDefinitionList(busSceneDefinition);
        return getDataTable(list);
    }

    /**
     * 导出场景定义列表
     */
    @PreAuthorize(hasPermi = "business:scenedefinition:export")
    @Log(title = "场景定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusSceneDefinition busSceneDefinition) throws IOException
    {
        List<BusSceneDefinition> list = busSceneDefinitionService.selectBusSceneDefinitionList(busSceneDefinition);
        ExcelUtil<BusSceneDefinition> util = new ExcelUtil<BusSceneDefinition>(BusSceneDefinition.class);
        util.exportExcel(response, list, "scenedefinition");
    }

    /**
     * 获取场景定义详细信息
     */
    @PreAuthorize(hasPermi = "business:scenedefinition:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busSceneDefinitionService.selectBusSceneDefinitionById(id));
    }

    /**
     * 新增场景定义
     */
    @PreAuthorize(hasPermi = "business:scenedefinition:add")
    @Log(title = "场景定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusSceneDefinition busSceneDefinition)
    {
        return toAjax(busSceneDefinitionService.insertBusSceneDefinition(busSceneDefinition));
    }

    /**
     * 修改场景定义
     */
    @PreAuthorize(hasPermi = "business:scenedefinition:edit")
    @Log(title = "场景定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusSceneDefinition busSceneDefinition)
    {
        return toAjax(busSceneDefinitionService.updateBusSceneDefinition(busSceneDefinition));
    }

    /**
     * 删除场景定义
     */
    @PreAuthorize(hasPermi = "business:scenedefinition:remove")
    @Log(title = "场景定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busSceneDefinitionService.deleteBusSceneDefinitionByIds(ids));
    }
}
