const global = {}
global.install = function(Vue) {

  Vue.prototype.formatNodeType = function(row) {
    if (row.nodeType === 'DIRECT') {
      return '直连'
    } else if (row.nodeType === 'SUB') {
      return '子设备'
    } else if (row.nodeType === 'GATEWAY') {
      return '网关设备'
    }
    return '错误类型'
  }

  Vue.prototype.formatTimeBC = function(row, column, value) {
    // eslint-disable-next-line eqeqeq
    if (value == '1980-01-01 00:00:00') {
      return '/'
    }
    return value
  }

  Vue.prototype.writeGwDeviceCode = function(gwDeviceCode) {
    localStorage.setItem('gwDeviceCode', gwDeviceCode)
  }

  Vue.prototype.readGwDeviceCode = function() {
    return localStorage.getItem('gwDeviceCode')
  }

  Vue.prototype.writeNodeType = function(nodetype) {
    localStorage.setItem('nodetype', nodetype)
  }

  Vue.prototype.readNodeType = function() {
    return localStorage.getItem('nodetype')
  }

  Vue.prototype.numArr = ['int', 'float', 'double', 'long']
  Vue.prototype.boolArr = ['boolean']
  Vue.prototype.dateArr = ['date']
  Vue.prototype.textArr = ['text']
  Vue.prototype.structArr = ['struct']

  Vue.prototype.formatAttr = function(row) {
    if (row.dataType == 'date') { return 'UTC unix时间戳 单位毫秒' }
    if (row.attr == '') return ''
    const attrObject = JSON.parse(row.attr)
    const dataType = row.dataType
    let result = {}
    if (Vue.prototype.numArr.includes(dataType)) {
      result = {
        '最大值': attrObject.max,
        '最小值': attrObject.min,
        '单位': attrObject.unit
      }
    } else if (Vue.prototype.boolArr.includes(dataType)) {
      result = {
        'false - ': attrObject.bool0,
        'true -': attrObject.bool1
      }
    } else if (Vue.prototype.textArr.includes(dataType)) {
      result = {
        '最大长度': attrObject.length
      }
    } else if (Vue.prototype.structArr.includes(dataType)) {
      return '结构体'
    }

    const retStr = JSON.stringify(result)

    return retStr
  },

  Vue.prototype.LOGIN_SUCCESS = false
  /** 服务调用选中服务时组装输入参数*/
  Vue.prototype.buildInputParam = function(data) {
    const ret = {}
    if (data == null || undefined === data) {
      return ret
    }
    const dataType = data.dataType
    if (Vue.prototype.numArr.includes(dataType)) {
      ret[data.identifier] = 0
    } if (Vue.prototype.dateArr.includes(dataType)) {
      ret[data.identifier] = new Date().getTime()
    } else if (Vue.prototype.boolArr.includes(dataType)) {
      ret[data.identifier] = false
    } else if (Vue.prototype.textArr.includes(dataType)) {
      ret[data.identifier] = ''
    } else if (Vue.prototype.structArr.includes(dataType)) {
      // ret[data.identifier]
      const list = data.data
      const inner = []
      let innerItem
      for (let i = 0; i < list.length; i++) {
        const item = list[i]
        innerItem = Vue.prototype.buildInputParam(item)
        inner.push(innerItem)
      }
      ret[data.identifier] = inner
    }
    return ret
  }/** function buildInputParam success ends*/

  /** 模版演示数据组装*/
  Vue.prototype.buildTemplateDemoData = function(data) {
    const temlateResut = {}
    for (let i = 0; i < data.length; i++) {
      const item = data[i]

      if (this.boolArr.includes(item.dataType)) {
        temlateResut[item.identifier] = false
      } else if (this.textArr.includes(item.dataType)) {
        temlateResut[item.identifier] = '示例数据'
      } else if (this.dateArr.includes(item.dataType)) {
        temlateResut[item.identifier] = (new Date()).getTime()
      } else if (this.numArr.includes(item.dataType)) {
        temlateResut[item.identifier] = 0
      } else if (this.structArr.includes(item.dataType)) {
        const listData = item.attrMap.data
        temlateResut[item.identifier] = Vue.prototype.buildTemplateDemoData(listData)
      }
    }
    return temlateResut
  }/** ends function buildTemplateDemoData*/
  /** 服务调用模版演示数据组装*/
  Vue.prototype.buildTemplateServiceDemoData = function(data) {
    const temlateResut = {}
    for (let i = 0; i < data.length; i++) {
      const item = data[i]
      /** 说明是服务调用的参数*/
      try {
        const input = JSON.parse(item.inputParam)

        temlateResut[item.identifier] = Vue.prototype.buildInputDemoData(input)
      } catch (e) {
        temlateResut[item.identifier] = {}
      }
    }
    return temlateResut
  }/** ends function buildTemplateServiceDemoData*/
  /** 服务调用模版入参演示数据组装*/
  Vue.prototype.buildInputDemoData = function(item) {
    let result = {}

    if (this.boolArr.includes(item.dataType)) {
      result = false
    } else if (this.textArr.includes(item.dataType)) {
      result = '示例数据'
    } else if (this.dateArr.includes(item.dataType)) {
      result = (new Date()).getTime()
    } else if (this.numArr.includes(item.dataType)) {
      result = 0
    } else if (this.structArr.includes(item.dataType)) {
      const listData = item.data
      for (let i = 0; i < listData.length; i++) {
        const listItem = listData[i]
        result[listItem.identifier] = Vue.prototype.buildInputDemoData(listItem)
      }
    }

    return result
  }/** ends function buildTemplateServiceDemoData*/
  /** 模版数据组装-只是物模型数据*/
  Vue.prototype.buildTemplateData = function(data) {
    const template = {}
    for (let i = 0; i < data.length; i++) {
      const item = data[i]
      const jsonItem = {}

      jsonItem['decsription'] = item.propDesc
      jsonItem['name'] = item.propName
      template[item.identifier] = jsonItem
      if (item.funcType === 'SERVICE') {
        if (item.inputParam !== '') {
          jsonItem['inputParam'] = JSON.parse(item.inputParam)
        }
      } else {
        jsonItem['attr'] = item.attrMap
      }
    }
    return template
  }/** ends function buildTemplateData*/
}/** global install ends*/

export default global
