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
import com.zefu.business.domain.BusMapPunctuation;
import com.zefu.business.service.IBusMapPunctuationService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 地图标点Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/mappunctuation")
public class BusMapPunctuationController extends BaseController
{
    @Autowired
    private IBusMapPunctuationService busMapPunctuationService;

    /**
     * 查询地图标点列表
     */
    @PreAuthorize(hasPermi = "business:mappunctuation:list")
    @GetMapping("/list")
    public TableDataInfo list(BusMapPunctuation busMapPunctuation)
    {
        startPage();
        List<BusMapPunctuation> list = busMapPunctuationService.selectBusMapPunctuationList(busMapPunctuation);
        return getDataTable(list);
    }

    /**
     * 导出地图标点列表
     */
    @PreAuthorize(hasPermi = "business:mappunctuation:export")
    @Log(title = "地图标点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusMapPunctuation busMapPunctuation) throws IOException
    {
        List<BusMapPunctuation> list = busMapPunctuationService.selectBusMapPunctuationList(busMapPunctuation);
        ExcelUtil<BusMapPunctuation> util = new ExcelUtil<BusMapPunctuation>(BusMapPunctuation.class);
        util.exportExcel(response, list, "mappunctuation");
    }

    /**
     * 获取地图标点详细信息
     */
    @PreAuthorize(hasPermi = "business:mappunctuation:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busMapPunctuationService.selectBusMapPunctuationById(id));
    }

    /**
     * 新增地图标点
     */
    @PreAuthorize(hasPermi = "business:mappunctuation:add")
    @Log(title = "地图标点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusMapPunctuation busMapPunctuation)
    {
        return toAjax(busMapPunctuationService.insertBusMapPunctuation(busMapPunctuation));
    }

    /**
     * 修改地图标点
     */
    @PreAuthorize(hasPermi = "business:mappunctuation:edit")
    @Log(title = "地图标点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusMapPunctuation busMapPunctuation)
    {
        return toAjax(busMapPunctuationService.updateBusMapPunctuation(busMapPunctuation));
    }

    /**
     * 删除地图标点
     */
    @PreAuthorize(hasPermi = "business:mappunctuation:remove")
    @Log(title = "地图标点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busMapPunctuationService.deleteBusMapPunctuationByIds(ids));
    }
}
