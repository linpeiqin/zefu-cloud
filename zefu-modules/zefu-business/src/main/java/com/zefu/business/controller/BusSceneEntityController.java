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
import com.zefu.business.domain.BusSceneEntity;
import com.zefu.business.service.IBusSceneEntityService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 场景实体关联Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/sceneentity")
public class BusSceneEntityController extends BaseController
{
    @Autowired
    private IBusSceneEntityService busSceneEntityService;

    /**
     * 查询场景实体关联列表
     */
    @PreAuthorize(hasPermi = "business:sceneentity:list")
    @GetMapping("/list")
    public TableDataInfo list(BusSceneEntity busSceneEntity)
    {
        startPage();
        List<BusSceneEntity> list = busSceneEntityService.selectBusSceneEntityList(busSceneEntity);
        return getDataTable(list);
    }

    /**
     * 导出场景实体关联列表
     */
    @PreAuthorize(hasPermi = "business:sceneentity:export")
    @Log(title = "场景实体关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusSceneEntity busSceneEntity) throws IOException
    {
        List<BusSceneEntity> list = busSceneEntityService.selectBusSceneEntityList(busSceneEntity);
        ExcelUtil<BusSceneEntity> util = new ExcelUtil<BusSceneEntity>(BusSceneEntity.class);
        util.exportExcel(response, list, "sceneentity");
    }

    /**
     * 获取场景实体关联详细信息
     */
    @PreAuthorize(hasPermi = "business:sceneentity:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busSceneEntityService.selectBusSceneEntityById(id));
    }

    /**
     * 新增场景实体关联
     */
    @PreAuthorize(hasPermi = "business:sceneentity:add")
    @Log(title = "场景实体关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusSceneEntity busSceneEntity)
    {
        return toAjax(busSceneEntityService.insertBusSceneEntity(busSceneEntity));
    }

    /**
     * 修改场景实体关联
     */
    @PreAuthorize(hasPermi = "business:sceneentity:edit")
    @Log(title = "场景实体关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusSceneEntity busSceneEntity)
    {
        return toAjax(busSceneEntityService.updateBusSceneEntity(busSceneEntity));
    }

    /**
     * 删除场景实体关联
     */
    @PreAuthorize(hasPermi = "business:sceneentity:remove")
    @Log(title = "场景实体关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busSceneEntityService.deleteBusSceneEntityByIds(ids));
    }
}
