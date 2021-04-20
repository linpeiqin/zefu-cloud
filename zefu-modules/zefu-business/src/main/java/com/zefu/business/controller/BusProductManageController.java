package com.zefu.business.controller;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;


import com.zefu.common.base.domain.dto.response.ProtocolItemResDto;
import com.zefu.common.core.domain.R;
import com.zefu.common.core.utils.SecurityUtils;
import com.zefu.common.base.domain.bo.TopicDescBo;
import com.zefu.common.base.domain.dto.response.DataTypeItemResDto;
import com.zefu.common.base.metadata.MetaType;
import com.zefu.common.base.metadata.MetaUnit;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zefu.common.log.annotation.Log;
import com.zefu.common.log.enums.BusinessType;
import com.zefu.common.security.annotation.PreAuthorize;
import com.zefu.business.domain.BusProductManage;
import com.zefu.business.service.IBusProductManageService;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.page.TableDataInfo;

/**
 * 产品管理Controller
 * 
 * @author linking
 * @date 2021-03-11
 */
@RestController
@RequestMapping("/product")
public class BusProductManageController extends BaseController
{
    @Autowired
    private IBusProductManageService busProductManageService;

    /**
     * 查询产品管理列表
     */
    @PreAuthorize(hasPermi = "business:product:list")
    @GetMapping("/list")
    public TableDataInfo list(BusProductManage busProductManage)
    {
        startPage();
        List<BusProductManage> list = busProductManageService.selectBusProductManageList(busProductManage);
        return getDataTable(list);
    }

    /**
     * 导出产品管理列表
     */
    @PreAuthorize(hasPermi = "business:product:export")
    @Log(title = "产品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusProductManage busProductManage) throws IOException
    {
        List<BusProductManage> list = busProductManageService.selectBusProductManageList(busProductManage);
        ExcelUtil<BusProductManage> util = new ExcelUtil<BusProductManage>(BusProductManage.class);
        util.exportExcel(response, list, "product");
    }
    /**
     * 状态修改
     */
    @PreAuthorize(hasPermi = "business:product:edit")
    @Log(title = "产品管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody BusProductManage busProductManage)
    {
        busProductManage.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(busProductManageService.updateProductStatus(busProductManage));
    }
    /**
     * 获取产品管理详细信息
     */
    @PreAuthorize(hasPermi = "business:product:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busProductManageService.selectBusProductManageById(id));
    }

    /**
     * 新增产品管理
     */
    @PreAuthorize(hasPermi = "business:product:add")
    @Log(title = "产品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusProductManage busProductManage)
    {
        return toAjax(busProductManageService.insertBusProductManage(busProductManage));
    }
    @PreAuthorize(hasPermi = "business:product:edit")
    @Log(title = "产品管理", businessType = BusinessType.OTHER)
    @GetMapping(value = "/dataTypes")
    public AjaxResult dataTypeList(){
        List<DataTypeItemResDto> result = new ArrayList<>();
        for(MetaType item: MetaType.values()){
            DataTypeItemResDto dto = new DataTypeItemResDto(item.getCode(), item.getMsg(), item.getMsg());
            result.add(dto);
        }
        return AjaxResult.success(result);
    }

    @PreAuthorize(hasPermi = "business:product:query")
    @GetMapping(value = "/protocols")
    @Log(title = "获取支持协议列表",businessType = BusinessType.OTHER)
    public AjaxResult protocols(){
        List<ProtocolItemResDto> protocolItemResDtos = busProductManageService.listProtocol();
        return AjaxResult.success(protocolItemResDtos);
    }

    @PreAuthorize(hasPermi = "business:product:edit")
    @GetMapping(value = "/units")
    @Log(title = "获取计量单位列表",businessType = BusinessType.OTHER)
    public AjaxResult units(){
        List<DataTypeItemResDto> result = new ArrayList<>();
        for(MetaUnit item: MetaUnit.values()){
            DataTypeItemResDto dto = new DataTypeItemResDto(item.getCode(), item.getName(), item.getCode() +"|"+item.getMsg());
            result.add(dto);
        }
        return AjaxResult.success(result);
    }
    /**
     * 修改产品管理
     */
    @PreAuthorize(hasPermi = "business:product:edit")
    @Log(title = "产品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusProductManage busProductManage)
    {
        return toAjax(busProductManageService.updateBusProductManage(busProductManage));
    }

    /**
     * 删除产品管理
     */
    @PreAuthorize(hasPermi = "business:product:remove")
    @Log(title = "产品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busProductManageService.deleteBusProductManageByIds(ids));
    }

    /**
     * topic列表
     */
    @PreAuthorize(hasPermi = "business:product:edit")
    @Log(title = "产品管理", businessType = BusinessType.OTHER)
    @GetMapping("/topics")
    public AjaxResult  topics(){
        List<TopicDescBo> list = new ArrayList<TopicDescBo>();
        list.add(TopicDescBo.build("属性上报", "up/设备编码/prop", null));
        list.add(TopicDescBo.build("事件上报", "up/设备编码/event", null));
        list.add(TopicDescBo.build("下行数据返回结果上报", "up/设备编码/message/reply" , "设备上报下行数据返回结果(服务&属性设置)"));
        list.add(TopicDescBo.build("调用设备服务", "down/设备编码/service/invoke", null));
        list.add(TopicDescBo.build("设备属性设置", "down/设备编码/property/set", "设备属性设置"));
        return AjaxResult.success(list);
    }
    @GetMapping("/queryProtocolCodeByCode")
    public R<String> queryProtocolCodeByCode(String code){
        String protocolCode = this.busProductManageService.queryByCode(code).getProtocolCode();
        if (protocolCode == null) return R.fail();
        return R.ok(protocolCode);
    }
}
