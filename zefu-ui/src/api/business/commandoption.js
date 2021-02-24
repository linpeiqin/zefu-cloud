import request from '@/utils/request'

// 查询命令设置列表
export function listCommandoption(query) {
  return request({
    url: '/business/commandoption/list',
    method: 'get',
    params: query
  })
}

// 查询命令设置详细
export function getCommandoption(id) {
  return request({
    url: '/business/commandoption/' + id,
    method: 'get'
  })
}

// 新增命令设置
export function addCommandoption(data) {
  return request({
    url: '/business/commandoption',
    method: 'post',
    data: data
  })
}

// 修改命令设置
export function updateCommandoption(data) {
  return request({
    url: '/business/commandoption',
    method: 'put',
    data: data
  })
}

// 删除命令设置
export function delCommandoption(id) {
  return request({
    url: '/business/commandoption/' + id,
    method: 'delete'
  })
}
