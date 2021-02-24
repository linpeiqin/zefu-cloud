import request from '@/utils/request'

// 查询地图标点列表
export function listMappunctuation(query) {
  return request({
    url: '/business/mappunctuation/list',
    method: 'get',
    params: query
  })
}

// 查询地图标点详细
export function getMappunctuation(id) {
  return request({
    url: '/business/mappunctuation/' + id,
    method: 'get'
  })
}

// 新增地图标点
export function addMappunctuation(data) {
  return request({
    url: '/business/mappunctuation',
    method: 'post',
    data: data
  })
}

// 修改地图标点
export function updateMappunctuation(data) {
  return request({
    url: '/business/mappunctuation',
    method: 'put',
    data: data
  })
}

// 删除地图标点
export function delMappunctuation(id) {
  return request({
    url: '/business/mappunctuation/' + id,
    method: 'delete'
  })
}
