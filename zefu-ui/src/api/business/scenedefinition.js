import request from '@/utils/request'

// 查询场景定义列表
export function listScenedefinition(query) {
  return request({
    url: '/business/scenedefinition/list',
    method: 'get',
    params: query
  })
}

// 查询场景定义详细
export function getScenedefinition(id) {
  return request({
    url: '/business/scenedefinition/' + id,
    method: 'get'
  })
}

// 新增场景定义
export function addScenedefinition(data) {
  return request({
    url: '/business/scenedefinition',
    method: 'post',
    data: data
  })
}

// 修改场景定义
export function updateScenedefinition(data) {
  return request({
    url: '/business/scenedefinition',
    method: 'put',
    data: data
  })
}

// 删除场景定义
export function delScenedefinition(id) {
  return request({
    url: '/business/scenedefinition/' + id,
    method: 'delete'
  })
}
