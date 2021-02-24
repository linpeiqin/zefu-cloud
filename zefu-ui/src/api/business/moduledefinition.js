import request from '@/utils/request'

// 查询模组定义列表
export function listModuledefinition(query) {
  return request({
    url: '/business/moduledefinition/list',
    method: 'get',
    params: query
  })
}

// 查询模组定义详细
export function getModuledefinition(id) {
  return request({
    url: '/business/moduledefinition/' + id,
    method: 'get'
  })
}

// 新增模组定义
export function addModuledefinition(data) {
  return request({
    url: '/business/moduledefinition',
    method: 'post',
    data: data
  })
}

// 修改模组定义
export function updateModuledefinition(data) {
  return request({
    url: '/business/moduledefinition',
    method: 'put',
    data: data
  })
}

// 删除模组定义
export function delModuledefinition(id) {
  return request({
    url: '/business/moduledefinition/' + id,
    method: 'delete'
  })
}
