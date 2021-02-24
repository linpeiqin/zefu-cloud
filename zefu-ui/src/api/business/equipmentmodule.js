import request from '@/utils/request'

// 查询设备模组关联列表
export function listEquipmentmodule(query) {
  return request({
    url: '/business/equipmentmodule/list',
    method: 'get',
    params: query
  })
}

// 查询设备模组关联详细
export function getEquipmentmodule(id) {
  return request({
    url: '/business/equipmentmodule/' + id,
    method: 'get'
  })
}

// 新增设备模组关联
export function addEquipmentmodule(data) {
  return request({
    url: '/business/equipmentmodule',
    method: 'post',
    data: data
  })
}

// 修改设备模组关联
export function updateEquipmentmodule(data) {
  return request({
    url: '/business/equipmentmodule',
    method: 'put',
    data: data
  })
}

// 删除设备模组关联
export function delEquipmentmodule(id) {
  return request({
    url: '/business/equipmentmodule/' + id,
    method: 'delete'
  })
}
