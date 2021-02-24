import request from '@/utils/request'

// 查询场景实体关联列表
export function listSceneentity(query) {
  return request({
    url: '/business/sceneentity/list',
    method: 'get',
    params: query
  })
}

// 查询场景实体关联详细
export function getSceneentity(id) {
  return request({
    url: '/business/sceneentity/' + id,
    method: 'get'
  })
}

// 新增场景实体关联
export function addSceneentity(data) {
  return request({
    url: '/business/sceneentity',
    method: 'post',
    data: data
  })
}

// 修改场景实体关联
export function updateSceneentity(data) {
  return request({
    url: '/business/sceneentity',
    method: 'put',
    data: data
  })
}

// 删除场景实体关联
export function delSceneentity(id) {
  return request({
    url: '/business/sceneentity/' + id,
    method: 'delete'
  })
}
