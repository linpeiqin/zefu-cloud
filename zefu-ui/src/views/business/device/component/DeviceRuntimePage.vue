<template>
  <div v-if="show">
    <el-col v-for="item in propData" :key="item.identifier" class="range">
      <el-card shadow="always">
        <div>
          <div class="top clearfix">
            <time class="top-text"> {{ item.propName }}</time>
            <el-button type="text" class="button" @click="showDetail(item)">详情</el-button>
            <el-button type="text" class="button" @click="propertySet(item)">下发</el-button>
            <el-button type="text" class="button" @click="propertyGet(item)">读取</el-button>
          </div>

          <div class="content-text">{{ item.value }} {{ item.unit }}</div>
          <div class="bottom clearfix">
            <time class="time">{{ item.arrivedTime }}</time>
          </div>
        </div>
      </el-card>
    </el-col>

    <el-dialog :title="dlgTitle" :visible.sync="detailShow" width="80%">
      <device-item-data
        ref="itemDataPage"
        :show="detailShow"
        :func-type="funcType"
        :device-code="deviceCode"
        :product-code="productCode"
        :up-label="upLabel"
        :down-label="downLabel"
        :down-show="true"
      />
    </el-dialog>

    <el-dialog title="下发指令" :visible.sync="invokeShow">
      <el-form ref="form" label-width="80px">
        <el-form-item label="指令">
          <el-input v-model="downMessage" type="textarea" :rows="10" />
        </el-form-item>
        <el-form-item label="返回结果">
          <el-input
            v-model="response"
            type="textarea"
            :rows="10"
            disabled
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="invoke">下发</el-button>
          <el-button @click="invokeShow = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  deviceRuntimeApi,
  propertySetApi,
  propertyGetApi
} from '@/api/business/device'
import { propsTemplateApi, propDetailSepcApi } from '@/api/business/product'
import deviceItemData from './DeviceItemDataList'
export default {
  name: 'DevDetailPage',
  components: { deviceItemData },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    deviceCode: {
      type: String,
      default: ''
    },
    // eslint-disable-next-line vue/require-default-prop
    productCode: String
  },
  data() {
    return {
      propData: [],
      detailShow: false,
      invokeShow: false,
      funcType: 'PROP',
      dlgTitle: '',
      /** 下发数据*/
      downMessage: {},
      /** 下发的属性具体属性*/
      propertyData: {},
      response: '',
      downLabel: '设置记录',
      upLabel: '上报记录'
    }
  },
  watch: {},
  mounted() {
    this.initData()
  },
  destroyed() {},
  methods: {
    propertySet(item) {
      const param = {
        funcType: 'PROP',
        identifier: item.identifier,
        productCode: this.productCode
      }
      propDetailSepcApi(this.productCode, item.identifier, 'PROP').then(
        (data) => {
          this.propertyData = data.data
          if (this.propertyData.wrType !== 1) {
            this.$message('只读属性无法下发指令')
            return
          }
          propsTemplateApi(param).then((data) => {
            const retValue = data.data
            this.downMessage = JSON.stringify(retValue.demoData, null, 2)
            this.invokeShow = true
          })
        }
      )
    } /** function propertySet ends*/,
    /** 读取属性*/

    propertyGet(item) {
      const param = {
        identifier: item.identifier,
        deviceCode: this.deviceCode,
        timeout: 20
      }
      propertyGetApi(param).then((data) => {
        item.value = data.data
        item.arrivedTime = data.time
        this.$alert(item.value, '返回结果', {
          confirmButtonText: '确定'
        })
      })
    } /** function propertyGet ends*/,
    /** 下发属性*/
    invoke(item) {
      this.command = JSON.parse(this.downMessage)
      const param = {
        identifier: this.propertyData.identifier,
        deviceCode: this.deviceCode,
        productCode: this.productCode,
        command: this.command
      }
      propertySetApi(param).then((data) => {
        this.response = JSON.stringify(data, null, 2)
      })
    },
    showDetail(item) {
      this.detailShow = true
      this.dlgTitle = item.propName
      this.$nextTick(() => {
        this.$refs.itemDataPage.upLabel = '上报记录'
        this.$refs.itemDataPage.downLabel = '设置记录'
        this.$refs.itemDataPage.downShow = true
        this.$refs.itemDataPage.getData(
          item.dataType,
          item.identifier,
          item.unit
        )
        this.$refs.itemDataPage.resetPageNo()
      })
    },
    initData() {
      deviceRuntimeApi(this.productCode, 'PROP', this.deviceCode).then(
        (data) => {
          const retValue = data.data
          this.propData = retValue
        }
      )
    }
  }
}
</script>

<style scoped>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 15px;
  line-height: 12px;
}

.top {
  margin-top: 0px;
  line-height: 12px;
}
.top-text {
  margin-top: 0px;
  line-height: 12px;
  color: #999;
}

.button {
  margin-top: 0px;
  margin-left: 10px;
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
.range {
  margin-top: 8px;
  margin-right: 3px;
  margin-left: 3px;
  width: 24%;
}

.content-text {
  font-size: 18px;
  font-family: "微软雅黑";
  height: 80px;
  overflow: hidden; /* 超出隐藏结合width使用截取采用效果*/
  text-overflow: ellipsis; /* 本功能的主要功臣，超出部分的剪裁方式 */
  -o-text-overflow: ellipsis;
}
</style>
