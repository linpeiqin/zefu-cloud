package com.zefu.business.controller;

import com.zefu.business.domain.BusDeviceManage;
import com.zefu.business.domain.BusProductManage;
import com.zefu.business.service.IBusDeviceManageService;
import com.zefu.business.service.IPropGetService;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.enums.BatchOpEnum;
import com.zefu.common.core.domain.R;
import com.zefu.common.core.utils.SecurityUtils;
import com.zefu.common.core.utils.poi.ExcelUtil;
import com.zefu.common.core.web.controller.BaseController;
import com.zefu.common.core.web.domain.AjaxResult;
import com.zefu.common.core.web.page.TableDataInfo;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.dto.PageReqDto;
import com.zefu.common.base.domain.dto.PageResDto;
import com.zefu.common.base.domain.dto.request.DevQueryReqDto;
import com.zefu.common.base.domain.dto.request.device.*;
import com.zefu.common.base.domain.dto.response.device.DeviceRtHistoryResDto;
import com.zefu.common.base.domain.dto.response.device.DeviceRtResDto;
import com.zefu.common.base.domain.dto.response.device.GwDevicePageResDto;
import com.zefu.common.base.domain.gateway.direct.response.PropertyGetResponse;
import com.zefu.common.base.domain.gateway.mq.PropertySetMessageBo;
import com.zefu.common.base.domain.gateway.mq.ServiceInvokeMessageBo;
import com.zefu.common.base.enums.DeviceStateEnum;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.base.exception.GException;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.utils.bus.IdGenerate;
import com.zefu.common.log.annotation.Log;
import com.zefu.common.log.enums.BusinessType;
import com.zefu.common.security.annotation.PreAuthorize;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备管理Controller
 * 
 * @author linking
 * @date 2021-03-19
 */
@RestController
@RequestMapping("/device")
public class BusDeviceManageController extends BaseController
{
    @Autowired
    private IBusDeviceManageService busDeviceManageService;
    @Autowired
    IPropGetService propGetService;
    /**
     * 查询设备管理列表
     */
    @PreAuthorize(hasPermi = "business:device:list")
    @GetMapping("/list")
    public TableDataInfo list(BusDeviceManage busDeviceManage)
    {
        startPage();
        List<BusDeviceManage> list = busDeviceManageService.selectBusDeviceManageList(busDeviceManage);
        return getDataTable(list);
    }

    @PreAuthorize(hasPermi = "business:device:list")
    @PostMapping("/search")
    @Log(title = "分页查询设备列表", businessType = BusinessType.OTHER)
    public AjaxResult search(@RequestBody PageReqDto<DevQueryReqDto> searchPage) {
        PageResDto<DevicePageResDto> resDto = busDeviceManageService.queryByPage(searchPage);
        return AjaxResult.success(resDto);
    }

    /**
     * 导出设备管理列表
     */
    @PreAuthorize(hasPermi = "business:device:export")
    @Log(title = "设备管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusDeviceManage busDeviceManage) throws IOException
    {
        List<BusDeviceManage> list = busDeviceManageService.selectBusDeviceManageList(busDeviceManage);
        ExcelUtil<BusDeviceManage> util = new ExcelUtil<BusDeviceManage>(BusDeviceManage.class);
        util.exportExcel(response, list, "device");
    }

    /**
     * 获取设备管理详细信息
     */
    @PreAuthorize(hasPermi = "business:device:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busDeviceManageService.selectBusDeviceManageById(id));
    }

    /**
     * 新增设备管理
     */
    @PreAuthorize(hasPermi = "business:device:add")
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusDeviceManage busDeviceManage)
    {
        return toAjax(busDeviceManageService.insertBusDeviceManage(busDeviceManage));
    }
    /**
     * 状态修改
     */
    @PreAuthorize(hasPermi = "business:device:edit")
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody BusDeviceManage busDeviceManage)
    {
        busDeviceManage.setUpdateBy(SecurityUtils.getUsername());
        busDeviceManage.setUpdateTime(new Date());
        return toAjax(busDeviceManageService.updateDeviceStatus(busDeviceManage));
    }
    /**
     * 修改设备管理
     */
    @PreAuthorize(hasPermi = "business:device:edit")
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusDeviceManage busDeviceManage)
    {
        return toAjax(busDeviceManageService.updateBusDeviceManage(busDeviceManage));
    }

    /**
     * 删除设备管理
     */
    @PreAuthorize(hasPermi = "business:device:remove")
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busDeviceManageService.deleteBusDeviceManageByIds(ids));
    }
    @PreAuthorize(hasPermi = "business:device:query")
    @Log(title = "设备管理", businessType = BusinessType.OTHER)
    @GetMapping(value = "/info")
    public AjaxResult info(String deviceCode) {
        if (StringUtils.isEmpty(deviceCode)) {
            return AjaxResult.error(ErrorEnum.INVALID_PARAM.getMsg());
        }
        DevicePageResDto devicePageResDto = busDeviceManageService.selectBusDeviceManageByDeviceCode(deviceCode);
        return AjaxResult.success(devicePageResDto);
    }
    @PreAuthorize(hasPermi = "business:device:query")
    @GetMapping(value = "/runtime")
    @Log(title = "设备运行状态", businessType = BusinessType.OTHER)
    public AjaxResult runtime(String deviceCode, String productCode, String type) {
        if (StringUtils.isEmpty(deviceCode) || StringUtils.isEmpty(productCode)) {
            return AjaxResult.error(ErrorEnum.INVALID_PARAM.getMsg());
        }
        ProductFuncTypeEnum productFuncTypeEnum = ProductFuncTypeEnum.explain(type);
        if (null == productFuncTypeEnum) {
            productFuncTypeEnum = ProductFuncTypeEnum.PROP;
        }
        List<DeviceRtResDto> result = busDeviceManageService.queryRtByDevCode(deviceCode, productCode, productFuncTypeEnum);
        return AjaxResult.success(result);
    }
    @PreAuthorize(hasPermi = "business:device:query")
    @PostMapping(value = "/runtime/item")
    @Log(title = "查看设备具体属性运行详情列表", businessType = BusinessType.OTHER)
    public AjaxResult rtItem(@RequestBody PageReqDto<DeviceRtItemReqDto> searchPage) {
        PageResDto<DeviceRtHistoryResDto> pageResult = busDeviceManageService.searchRtItem(searchPage);
        return AjaxResult.success(pageResult);
    }
    @PreAuthorize(hasPermi = "business:device:query")
    @PostMapping(value = "/set/item")
    @Log(title = "查看设备属性设置历史", businessType = BusinessType.OTHER)
    public AjaxResult
    setItem(@RequestBody PageReqDto<DeviceRtItemReqDto> searchPage) {
        PageResDto<DeviceRtHistoryResDto> pageResult = busDeviceManageService.searchSetItem(searchPage);
        return AjaxResult.success(pageResult);
    }
   @PreAuthorize(hasPermi = "business:device:query")
   @PostMapping(value = "/property/set")
   @Log(title = "属性设置", businessType = BusinessType.OTHER)
   public AjaxResult propertySet(@Valid @RequestBody PropertySetReqDto reqDto) {
       PropertySetMessageBo bo = new PropertySetMessageBo();
       bo.setIdentifier(reqDto.getIdentifier());
       bo.setDeviceCode(reqDto.getDeviceCode());
       DevicePageResDto devicePageResDto = busDeviceManageService.selectBusDeviceManageByDeviceCode(reqDto.getDeviceCode());
       bo.setProductCode(devicePageResDto.getProductCode());
       bo.setCommand(reqDto.getCommand());
       bo.setMessageId(IdGenerate.genId());
       busDeviceManageService.send(Constants.REDIS_CHANNEL.PROP_SET,bo);
       return AjaxResult.success();
   }
    @PreAuthorize(hasPermi = "business:device:query")
    @PostMapping(value = "/gw/sub/dev")
    @Log(title = "网关设备列表", businessType = BusinessType.OTHER)
    public AjaxResult gwSubDevice(@RequestBody PageReqDto<DevQueryReqDto> searchPage) {
        PageResDto<DevicePageResDto> resDto = busDeviceManageService.queryByPage(searchPage);
        PageResDto<GwDevicePageResDto> result = new PageResDto<>();
        List<GwDevicePageResDto> list = new ArrayList<>();
        for(DevicePageResDto device: resDto.getResultData()){
            GwDevicePageResDto item = new GwDevicePageResDto();
            long total = busDeviceManageService.countByGwDevice(device.getDeviceCode(), DeviceStateEnum.TOTAL);
            long active = busDeviceManageService.countByGwDevice(device.getDeviceCode(), DeviceStateEnum.ACTIVE);
            BeanUtils.copyProperties(device, item);
            item.setDeviceTotal(total);
            item.setDeviceActive(active);
            list.add(item);
        }
        result.setResultData(list);
        return AjaxResult.success(result);
    }
    @PreAuthorize(hasPermi = "business:device:query")
    @PostMapping(value = "/property/get")
    @Log(title = "属性读取", businessType = BusinessType.OTHER)
    public AjaxResult propertyGet(@Valid @RequestBody PropertyGetReqDto reqDto) {
        String deviceCode = reqDto.getDeviceCode();
        String identifier = reqDto.getIdentifier();
        Long timeout = reqDto.getTimeout();
        PropertyGetResponse propertyGetResponse = propGetService.fetchProperty(deviceCode, identifier, timeout);
        Object value = propertyGetResponse.getValue();
        return null == value ? AjaxResult.error(ErrorEnum.TIMEOUT.getMsg()) : AjaxResult.success(propertyGetResponse.getValue());
    }

    @PreAuthorize(hasPermi = "business:device:edit")
    @PostMapping(value = "/map")
    @Log(title = "子设备关联到网关设备", businessType = BusinessType.OTHER)
    public AjaxResult map(@RequestBody GatewayMapReqDto dto) {
        if(!dto.getRemoveAction()){
            DevicePageResDto gwDevice = busDeviceManageService.queryByDevCode(dto.getGwDeviceCode());
            if(null == gwDevice){
                throw GException.genException(ErrorEnum.RESOURCE_NOT_EXISTS, "网关设备不存在");
            }
        }else{
            dto.setGwDeviceCode("");
        }
        busDeviceManageService.mapGateway(dto);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "business:device:edit")
    @PostMapping(value = "/service/invoke")
    @Log(title = "下发指令", businessType = BusinessType.OTHER)
    public AjaxResult invoke(@Valid @RequestBody InvokeReqDto reqDto) {
        ServiceInvokeMessageBo bo = new ServiceInvokeMessageBo();
        String messageId = IdGenerate.genId();
        bo.setIdentifier(reqDto.getIdentifier());
        bo.setMessageId(messageId);
        bo.setDeviceCode(reqDto.getDeviceCode());
        bo.setProductCode(reqDto.getProductCode());
        bo.setCommand(reqDto.getCommand());
        busDeviceManageService.send(Constants.REDIS_CHANNEL.SERVICE_INVOKE,bo);
        return AjaxResult.success("下发成功",messageId);
    }

    //以下为开放接口
    @PostMapping("/activeDevice")
    public R<Boolean> activeDevice(DeviceActiveMqBo bo){
        this.busDeviceManageService.activeDevice(bo);
        return R.ok();
    }
    @PostMapping("/batchChangeStatusByCode")
    public R<Boolean> batchChangeStatusByCode(List<String> devices, BatchOpEnum batchOpEnum){
        this.busDeviceManageService.batchChangeStatusByCode(devices,batchOpEnum);
        return R.ok();
    }
    @GetMapping("/queryByDevCode")
    public R<DevicePageResDto> queryByDevCode(String devCode){
        return R.ok(this.busDeviceManageService.queryByDevCode(devCode));
    }


}
