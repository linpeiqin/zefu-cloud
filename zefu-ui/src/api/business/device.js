import request from '@/utils/request'

// 查询设备管理列表
export function listDevice(query) {
  return request({
    url: '/business/device/list',
    method: 'get',
    params: query
  })
}

// 查询设备管理详细
export function getDevice(id) {
  return request({
    url: '/business/device/' + id,
    method: 'get'
  })
}

// 状态修改
export function changeDeviceStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/business/device/changeStatus',
    method: 'put',
    data: data
  })
}

// 新增设备管理
export function addDevice(data) {
  return request({
    url: '/business/device',
    method: 'post',
    data: data
  })
}

// 修改设备管理
export function updateDevice(data) {
  return request({
    url: '/business/device',
    method: 'put',
    data: data
  })
}

// 删除设备管理
export function delDevice(id) {
  return request({
    url: '/business/device/' + id,
    method: 'delete'
  })
}

export function deviceSearchApi(data) {
  return request({
    url: '/business/device/search',
    method: 'post',
    data
  })
}
export function deviceSubApi(data) {
  return request({
    url: '/business/device/gw/sub/dev',
    method: 'post',
    data
  })
}
export function deviceGwMapApi(data) {
  return request({
    url: '/business/device/map',
    method: 'post',
    data
  })
}

export function deviceBatchChgApi(data) {
  return request({
    url: '/business/device/status/change',
    method: 'post',
    data
  })
}

export function deviceBatchAddApi(data) {
  return request({
    url: '/business/device/batchadd',
    method: 'post',
    data
  })
}

export function deviceRuntimeApi(productCode, type, deviceCode) {
  return request({
    url: '/business/device/runtime',
    method: 'get',
    params: { productCode, type, deviceCode }
  })
}

export function deviceRtListApi(data) {
  return request({
    url: '/business/device/runtime/item',
    method: 'post',
    data
  })
}

export function deviceSetListApi(data) {
  return request({
    url: '/business/device/set/item',
    method: 'post',
    data
  })
}

export function deviceInfoApi(deviceCode) {
  return request({
    url: '/business/device/info',
    method: 'get',
    params: { deviceCode }
  })
}

export function deviceCreateApi(data) {
  return request({
    url: '/business/device/create',
    method: 'post',
    data
  })
}

export function deviceUpdateApi(data) {
  return request({
    url: '/business/device/update',
    method: 'post',
    data
  })
}

export function deviceImportApi(data) {
  return request({
    url: '/business/device/import/file',
    method: 'post',
    data
  })
}

export function serviceInvokeApi(data) {
  return request({
    url: '/business/device/service/invoke',
    method: 'post',
    data
  })
}
export function propertySetApi(data) {
  return request({
    url: '/business/device/property/set',
    method: 'post',
    data
  })
}

export function propertyGetApi(data) {
  return request({
    url: '/business/device/property/get',
    method: 'post',
    timeout: 20000,
    data
  })
}

