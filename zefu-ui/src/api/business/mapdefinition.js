import request from '@/utils/request'

// 查询地图定义列表
export function listMapdefinition(query) {
  return request({
    url: '/business/mapdefinition/list',
    method: 'get',
    params: query
  })
}

// 查询地图定义详细
export function getMapdefinition(id) {
  return request({
    url: '/business/mapdefinition/' + id,
    method: 'get'
  })
}

// 新增地图定义
export function addMapdefinition(data) {
  return request({
    url: '/business/mapdefinition',
    method: 'post',
    data: data
  })
}

// 修改地图定义
export function updateMapdefinition(data) {
  return request({
    url: '/business/mapdefinition',
    method: 'put',
    data: data
  })
}

// 删除地图定义
export function delMapdefinition(id) {
  return request({
    url: '/business/mapdefinition/' + id,
    method: 'delete'
  })
}
