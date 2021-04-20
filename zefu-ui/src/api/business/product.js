import request from '@/utils/request'

// 查询产品管理列表
export function listProduct(query) {
  return request({
    url: '/business/product/list',
    method: 'get',
    params: query
  })
}

// 查询产品管理详细
export function getProduct(id) {
  return request({
    url: '/business/product/' + id,
    method: 'get'
  })
}

// 新增产品管理
export function addProduct(data) {
  return request({
    url: '/business/product',
    method: 'post',
    data: data
  })
}

// 修改产品管理
export function updateProduct(data) {
  return request({
    url: '/business/product',
    method: 'put',
    data: data
  })
}

// 删除产品管理
export function delProduct(id) {
  return request({
    url: '/business/product/' + id,
    method: 'delete'
  })
}
// 状态修改
export function changeProductStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/business/product/changeStatus',
    method: 'put',
    data: data
  })
}


export function productUpdateApi(data) {
  return request({
    url: '/business/product/update',
    method: 'post',
    data
  })
}

export function protocolListApi(data) {
  return request({
    url: '/business/product/protocols',
    method: 'get',
    params: { }
  })
}

export function productDetailApi(code) {
  return request({
    url: '/business/product/detail',
    method: 'get',
    params: { code }
  })
}


export function productPropsApi(productCode, funcType) {
  return request({
    url: '/business/func/list',
    method: 'get',
    params: { productCode, funcType }
  })
}
export function productFuncList(query) {
  return request({
    url: '/business/func/list',
    method: 'get',
    params: query
  })
}

export function propDelApi(id) {
  return request({
    url: '/business/func/' + id,
    method: 'delete'
  })
}

// 查询产品功能详细
export function propDetailApi(id) {
  return request({
    url: '/business/func/' + id,
    method: 'get'
  })
}
export function propsCreateApi(data) {
  return request({
    url: '/business/func',
    method: 'post',
    data: data
  })
}
export function propsEditApi(data) {
  return request({
    url: '/business/func',
    method: 'put',
    data: data
  })
}

export function propsReleaseApi(id) {
  return request({
    url: '/business/func/release',
    method: 'get',
    params: { id }
  })
}
export function productTopicApi(productCode) {
  return request({
    url: '/business/product/topics',
    method: 'get',
    params: { productCode }
  })
}

export function productDataTypeApi() {
  return request({
    url: '/business/product/dataTypes',
    method: 'get',
    params: { }
  })
}
export function productUnitsApi() {
  return request({
    url: '/business/product/units',
    method: 'get',
    params: { }
  })
}

export function propsTemplateApi(data) {
  return request({
    url: '/business/func/template',
    method: 'post',
    data
  })
}
export function propDetailSepcApi(productCode, identifier, funcType) {
  return request({
    url: '/business/func/detail/identifier',
    method: 'get',
    params: { productCode, identifier, funcType}
  })
}
export function productQueryApi(query) {
  return request({
    url: '/business/product/list',
    method: 'get',
    params: query
  })
}
