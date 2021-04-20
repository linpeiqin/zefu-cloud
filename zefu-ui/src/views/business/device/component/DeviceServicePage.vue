<template>
  <div v-if="show">
    <el-col v-for="item in serviceData" :key="item.identifier" class="range">
      <el-card shadow="always">
        <div>
          <div class="top clearfix">
            <time class="top-text"> {{ item.propName }}</time>
            <el-button type="text" class="button" @click="showDetail(item)">详情</el-button>
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
        :upLabel="upLabel"
        :downShow=false
      />
    </el-dialog>

  </div>
</template>

<script>
import { deviceRuntimeApi } from '@/api/business/device'
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
      dlgName: '详情',
      serviceData: [],
      detailShow: false,
      tableData: [],
      funcType: 'SERVICE',
      dlgTitle: '',
      upLabel: '调用记录'
    }
  },
  watch: {

  },
  mounted() {
    this.initData()
  },
  destroyed() {

  },
  methods: {

    showDetail(item) {
      this.detailShow = true
      this.dlgTitle = item.propName
      this.$nextTick(() => {
        this.$refs.itemDataPage.upLabel = '调用记录'
        this.$refs.itemDataPage.downShow = false
        this.$refs.itemDataPage.getData(item.dataType, item.identifier, item.unit)
        this.$refs.itemDataPage.resetPageNo()
      })

    },

    initData() {
      deviceRuntimeApi(this.productCode, 'SERVICE', this.deviceCode).then(data => {
        const retValue = data.data
        this.serviceData = retValue
      })
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
    clear: both
  }
  .range{
    margin-top:8px;
    margin-right:3px;
    margin-left:3px;
    width: 24%;
  }

  .content-text{
    font-size: 18px;
    font-family:'微软雅黑';
    height: 80px;
    overflow: hidden; /* 超出隐藏结合width使用截取采用效果*/
    text-overflow: ellipsis; /* 本功能的主要功臣，超出部分的剪裁方式 */
    -o-text-overflow: ellipsis;
  }
</style>
