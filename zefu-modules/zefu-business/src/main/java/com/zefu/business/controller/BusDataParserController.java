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
import com.zefu.business.domain.BusDataParser;
import com.zefu.business.service.IBusDataParserService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 数据解析Controller
 * 
 * @author linking
 * @date 2021-02-08
 */
@RestController
@RequestMapping("/dataparser")
public class BusDataParserController extends BaseController
{
    @Autowired
    private IBusDataParserService busDataParserService;

    /**
     * 查询数据解析列表
     */
    @PreAuthorize(hasPermi = "business:dataparser:list")
    @GetMapping("/list")
    public TableDataInfo list(BusDataParser busDataParser)
    {
        startPage();
        List<BusDataParser> list = busDataParserService.selectBusDataParserList(busDataParser);
        return getDataTable(list);
    }

    /**
     * 导出数据解析列表
     */
    @PreAuthorize(hasPermi = "business:dataparser:export")
    @Log(title = "数据解析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusDataParser busDataParser) throws IOException
    {
        List<BusDataParser> list = busDataParserService.selectBusDataParserList(busDataParser);
        ExcelUtil<BusDataParser> util = new ExcelUtil<BusDataParser>(BusDataParser.class);
        util.exportExcel(response, list, "dataparser");
    }

    /**
     * 获取数据解析详细信息
     */
    @PreAuthorize(hasPermi = "business:dataparser:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busDataParserService.selectBusDataParserById(id));
    }

    /**
     * 新增数据解析
     */
    @PreAuthorize(hasPermi = "business:dataparser:add")
    @Log(title = "数据解析", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusDataParser busDataParser)
    {
        return toAjax(busDataParserService.insertBusDataParser(busDataParser));
    }

    /**
     * 修改数据解析
     */
    @PreAuthorize(hasPermi = "business:dataparser:edit")
    @Log(title = "数据解析", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusDataParser busDataParser)
    {
        return toAjax(busDataParserService.updateBusDataParser(busDataParser));
    }

    /**
     * 删除数据解析
     */
    @PreAuthorize(hasPermi = "business:dataparser:remove")
    @Log(title = "数据解析", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busDataParserService.deleteBusDataParserByIds(ids));
    }
}
