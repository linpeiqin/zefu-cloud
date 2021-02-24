import request from '@/utils/request'

// 查询数据解析列表
export function listDataparser(query) {
  return request({
    url: '/business/dataparser/list',
    method: 'get',
    params: query
  })
}

// 查询数据解析详细
export function getDataparser(id) {
  return request({
    url: '/business/dataparser/' + id,
    method: 'get'
  })
}

// 新增数据解析
export function addDataparser(data) {
  return request({
    url: '/business/dataparser',
    method: 'post',
    data: data
  })
}

// 修改数据解析
export function updateDataparser(data) {
  return request({
    url: '/business/dataparser',
    method: 'put',
    data: data
  })
}

// 删除数据解析
export function delDataparser(id) {
  return request({
    url: '/business/dataparser/' + id,
    method: 'delete'
  })
}
