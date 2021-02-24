import request from '@/utils/request'

// 查询实体设备关联列表
export function listEntityequipment(query) {
  return request({
    url: '/business/entityequipment/list',
    method: 'get',
    params: query
  })
}

// 查询实体设备关联详细
export function getEntityequipment(id) {
  return request({
    url: '/business/entityequipment/' + id,
    method: 'get'
  })
}

// 新增实体设备关联
export function addEntityequipment(data) {
  return request({
    url: '/business/entityequipment',
    method: 'post',
    data: data
  })
}

// 修改实体设备关联
export function updateEntityequipment(data) {
  return request({
    url: '/business/entityequipment',
    method: 'put',
    data: data
  })
}

// 删除实体设备关联
export function delEntityequipment(id) {
  return request({
    url: '/business/entityequipment/' + id,
    method: 'delete'
  })
}
