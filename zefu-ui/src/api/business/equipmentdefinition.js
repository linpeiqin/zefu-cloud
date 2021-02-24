import request from '@/utils/request'

// 查询设备定义列表
export function listEquipmentdefinition(query) {
  return request({
    url: '/business/equipmentdefinition/list',
    method: 'get',
    params: query
  })
}

// 查询设备定义详细
export function getEquipmentdefinition(id) {
  return request({
    url: '/business/equipmentdefinition/' + id,
    method: 'get'
  })
}

// 新增设备定义
export function addEquipmentdefinition(data) {
  return request({
    url: '/business/equipmentdefinition',
    method: 'post',
    data: data
  })
}

// 修改设备定义
export function updateEquipmentdefinition(data) {
  return request({
    url: '/business/equipmentdefinition',
    method: 'put',
    data: data
  })
}

// 删除设备定义
export function delEquipmentdefinition(id) {
  return request({
    url: '/business/equipmentdefinition/' + id,
    method: 'delete'
  })
}
