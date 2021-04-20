/* eslint-disable vue/require-default-prop */
<template>
  <div v-if="show">
    <div>
      设备功能:
      <el-select
        v-model="value"
        clearable
        placeholder="请选择"
        @change="handleSelect"
      >
        <el-option
          v-for="item in options"
          :key="item.identifier"
          :label="item.funcName"
          :value="item.identifier"
        />
      </el-select>
    </div>

    <div class="form-group">
      <label for="" style="float: left">参数：</label>
      <el-input
        v-model="runParam"
        type="textarea"
        :rows="10"
        placeholder="请输入内容"
      />
    </div>

    <div>
      <el-button type="primary" @click="sendMsg">运行</el-button>
    </div>
    调用结果：<el-input
      v-model="runResult"
      type="textarea"
      :rows="10"
      disabled
      placeholder=""
    />
  </div>
</template>

<script>
import { productPropsApi, propsTemplateApi } from '@/api/business/product'
import { serviceInvokeApi } from '@/api/business/device'

export default {
  name: 'ServiceInvoke',
  props: {
    show: {
      type: Boolean,
      default: false
    },
    // eslint-disable-next-line vue/require-default-prop
    deviceCode: String,
    // eslint-disable-next-line vue/require-default-prop
    productCode: String
  },
  data() {
    return {
      templateRequest: {},
      templateData: {},
      options: [],
      value: null,
      loading: false,
      runParam: '',
      /** 要发送给服务端的命令实体，其实runParam就是command的字符串*/
      command: {},
      runResult: '',
      identifier: ''
    }
  },
  mounted() {
    this.initData()
  },
  created() {},
  methods: {
    sendMsg() {
      this.command = JSON.parse(this.runParam)
      const param = {
        identifier: this.identifier,
        deviceCode: this.deviceCode,
        productCode: this.productCode,
        command: this.command
      }
      serviceInvokeApi(param).then((data) => {
        this.runResult = JSON.stringify(data, null, 2)
        this.$alert('返回messageId为:' + data.data, 'deivice:'+this.deviceCode, {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: '物模型中查看回执结果'
            })
          }
        })
      })
    },
    initData() {
      productPropsApi(this.productCode, 'SERVICE').then((data) => {
        const retValue = data.rows
        this.options = retValue
      })

      const param = {
        productCode: this.productCode,
        funcType: 'SERVICE'
      }
      propsTemplateApi(param).then((data) => {
        const retValue = data.data
        this.templateRequest = retValue.demoData
        this.templateData = retValue.template
      })
    },
    handleSelect(item) {
      this.command = {}
      this.command[item] = this.templateRequest[item]
      this.runParam = JSON.stringify(this.command, null, 2)
      this.identifier = item
    }
  }
}
</script>

<style scoped>
</style>
