<template>
  <div>
    <el-dialog
      title="添加子设备"
      :visible.sync="show"
      width="60%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :before-close="cancel"
    >
      <el-row :gutter="24" style="margin-top: 5px; margin-bottom: 5px">
        <el-col :span="24">
          <el-card shadow="always">
            <el-select
              v-model="query.paramData.productCode"
              clearable
              style="width: 160px; margin-right: 55px"
              placeholder="请选择产品"
            >
              <el-option
                v-for="item in productList"
                :key="item.productCode"
                :label="item.productName"
                :value="item.productCode"
              />
            </el-select>
            <el-input v-model="query.paramData.deviceCode" clearable placeholder="设备编码" />
            <el-input v-model="query.paramData.deviceName" clearable placeholder="设备名称" />
            <el-button
              type="primary"
              icon="el-icon-search"
              @click="handleSearch"
            >搜索</el-button>
          </el-card>
        </el-col>
      </el-row>
      <div class="container">
        <div class="form-box">
          <el-table
            ref="multipleTable"
            :data="tableData"
            border
            class="table"
            header-cell-class-name="table-header"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column prop="deviceCode" label="设备编码" width="160" align="center" />
            <el-table-column prop="deviceName" width="200" label="设备名称" align="center" />
            <el-table-column prop="productName" width="200" label="所属产品" align="center" />
            <el-table-column v-if="false" prop="productCode" label="所属产品编码" />
            <el-table-column label="设备状态" width="80px" prop="enableStatus" :formatter="formatStatus" align="center" />
            <el-table-column width="80px" prop="activeStatus" label="是否在线" :formatter="formatActive" align="center" />
            <el-table-column prop="createTime" width="200" label="创建时间" align="center" />
          </el-table>
          <div class="pagination">
            <el-pagination
              background
              layout="total, sizes, prev, pager, next"
              :current-page="query.pageNo"
              :page-size="query.limit"
              :total="total"
              :page-sizes="pageSizes"
              @current-change="handlePageChange"
              @size-change="sizeChange"
            />
          </div>
          <el-button type="primary" @click="add">确认</el-button>
          <el-button @click="cancel">取消</el-button>

        </div>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import {
  deviceSearchApi, deviceGwMapApi
} from '@/api/business/device'

export default {
  props: {
    show: {
      type: Boolean,
      default: false
    },
    productList: {
      type: Array,
      default: function() {
        return []
      }
    },
    gwDeviceCode: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      tableData: [],
      query: {
        paramData: {
          nodeType: 'SUB',
          productCode: null,
          gwDevCode: '',
          subDevQuery: true
        },
        pageNo: 1,
        limit: 10
      },
      total: 0,
      pageSizes: [10, 20, 30, 50, 80, 100, 150],
      selectedDevices: []
    }
  },
  methods: {
    add() {
      this.selectedDevices = []
      for (let i = 0; i < this.multipleSelection.length; i++) {
        this.selectedDevices.push(this.multipleSelection[i].deviceCode)
      }
      const param = {
        gwDeviceCode: this.gwDeviceCode,
        devices: this.selectedDevices
      }
      // alert(JSON.stringify(this.selectedDevices, null, 2))
      deviceGwMapApi(param).then((data) => {
        this.init()
        this.$message('成功')
      })
    },
    cancel() {
      this.$emit('hidden')
    },
    formatActive(value) {
      return value.activeStatus === 1 ? '在线' : '离线'
    },
    formatStatus(value) {
      return value.enableStatus === 1 ? '激活' : '未激活'
    },
    init() {
      const para = this.query
      deviceSearchApi(para).then((data) => {
        this.tableData = data.rows
        this.total = data.total
      })
    },
    handleSearch() {
      const para = this.query
      deviceSearchApi(para).then((data) => {
        this.tableData = data.rows
        this.total = data.total
      })
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      if (this.multipleSelection.length > 0) {
        this.isSelected = true
      }
    },
    sizeChange(val) {
      this.$set(this.query, 'limit', val)
      this.init()
    },
    handlePageChange(val) {
      this.$set(this.query, 'pageNo', val)
      this.init()
    }
  }
}
</script>

<style>
  .el-input{
        width:210px;
    }
</style>
