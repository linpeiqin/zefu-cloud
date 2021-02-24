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
import com.zefu.business.domain.BusCommandOption;
import com.zefu.business.service.IBusCommandOptionService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 命令设置Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/commandoption")
public class BusCommandOptionController extends BaseController
{
    @Autowired
    private IBusCommandOptionService busCommandOptionService;

    /**
     * 查询命令设置列表
     */
    @PreAuthorize(hasPermi = "business:commandoption:list")
    @GetMapping("/list")
    public TableDataInfo list(BusCommandOption busCommandOption)
    {
        startPage();
        List<BusCommandOption> list = busCommandOptionService.selectBusCommandOptionList(busCommandOption);
        return getDataTable(list);
    }

    /**
     * 导出命令设置列表
     */
    @PreAuthorize(hasPermi = "business:commandoption:export")
    @Log(title = "命令设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusCommandOption busCommandOption) throws IOException
    {
        List<BusCommandOption> list = busCommandOptionService.selectBusCommandOptionList(busCommandOption);
        ExcelUtil<BusCommandOption> util = new ExcelUtil<BusCommandOption>(BusCommandOption.class);
        util.exportExcel(response, list, "commandoption");
    }

    /**
     * 获取命令设置详细信息
     */
    @PreAuthorize(hasPermi = "business:commandoption:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busCommandOptionService.selectBusCommandOptionById(id));
    }

    /**
     * 新增命令设置
     */
    @PreAuthorize(hasPermi = "business:commandoption:add")
    @Log(title = "命令设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusCommandOption busCommandOption)
    {
        return toAjax(busCommandOptionService.insertBusCommandOption(busCommandOption));
    }

    /**
     * 修改命令设置
     */
    @PreAuthorize(hasPermi = "business:commandoption:edit")
    @Log(title = "命令设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusCommandOption busCommandOption)
    {
        return toAjax(busCommandOptionService.updateBusCommandOption(busCommandOption));
    }

    /**
     * 删除命令设置
     */
    @PreAuthorize(hasPermi = "business:commandoption:remove")
    @Log(title = "命令设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busCommandOptionService.deleteBusCommandOptionByIds(ids));
    }
}
