<template>
  <div>
    <el-col v-for="item in gatewayList" :key="item.deviceCode" class="range">
      <el-card shadow="always">
        <div>
          <div class="top clearfix">
            <time class="top-text"> {{ item.deviceName||item.deviceCode }}</time>
            <el-button type="text" class="button" @click="addSubShow(item)">添加</el-button>
            <el-button type="text" class="button" @click="removeSubShow(item)">移除</el-button>
          </div>

          <el-row :gutter="24" style="margin-top:10px">
            <el-col :span="12">
              <el-card shadow="always">
                总数:{{ item.deviceTotal }}
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card shadow="always">
                在线:{{ item.deviceActive }}
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-card>
    </el-col>
    <el-col>
      <div class="pagination">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next"
          :current-page="query.pageNo"
          :page-size="query.limit"
          :page-sizes="pageSizes"
          :total="total"
          @current-change="handlePageChange"
          @size-change="sizeChange"
        />
      </div>
    </el-col>

    <sub-add ref="subAdd" :show="subAddShow" :gw-device-code="gwDeviceCode" :product-list="productSubList" @hidden="subAddShow = false" />
    <sub-remove ref="subRemove" :show="subRemoveShow" :gw-device-code="gwDeviceCode" :product-list="productSubList" @hidden="subRemoveShow = false" />
  </div>
</template>

<script>
import {deviceSubApi} from '@/api/business/device'
import {productQueryApi} from '@/api/business/product'
import subAdd from './component/SubAdd'
import subRemove from './component/SubRemove'

export default {
  components: { subAdd, subRemove },
  data() {
    return {
      gatewayList: [],
      /** 搜索参数*/
      search: {
        productCode: null,
        activeStatus: null,
        nodeType: 'GATEWAY'
      },
      query: {
        paramData: {},
        pageNo: 1,
        limit: 12
      },
      total: 0,
      pageSizes: [12, 24, 36, 48, 60, 72, 84, 96, 108],
      subAddShow: false,
      subRemoveShow: false,
      /** 网关子设备*/
      productSubList: [],
      gwDeviceCode: ''
    }
  },
  created() {
    this.init()
    this.getProductSubList()
  },
  methods: {
    init() {
      this.query.paramData = this.search
      deviceSubApi(this.query).then(data => {
        this.gatewayList = data.data.resultData
        this.total = data.data.total
      })
    },
    getProductSubList() {
      const para = {
        nodeType: 'SUB'
      }
      productQueryApi(para).then((data) => {
        this.productSubList = data.data
      })
    },
    /** 添加子设备*/
    addSubShow(item) {
      this.subAddShow = true
      this.gwDeviceCode = item.deviceCode
      this.$nextTick(() => {
        this.$refs.subAdd.init()
      })
    },
    removeSubShow(item) {
      this.subRemoveShow = true
      this.gwDeviceCode = item.deviceCode
      this.$nextTick(() => {
        this.$refs.subRemove.init()
      })
    },
    handlePageChange(val) {
      this.$set(this.query, 'pageNo', val)
      this.init()
    },
    sizeChange(val) {
      this.$set(this.query, 'limit', val)
      this.init()
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
  margin-top: 10px;
  margin-right: 10px;
  margin-left: 10px;
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
