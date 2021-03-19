<template>
  <div>
    <el-table
      :data="tableData"
      border
      class="table"
      header-cell-class-name="table-header"
    >
      <el-table-column prop="name" label="名称" align="center" />
      <el-table-column prop="topic" label="topic" align="center" />
      <el-table-column prop="desc" label="描述" align="center" />

    </el-table>
  </div>
</template>

<script>
import { productTopicApi } from '@/api/business/product'

export default {
  name: 'TopicDetail',
  props: {
    productCode: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      tableData: []
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      const param = { productCode: this.productCode }
      productTopicApi(this.productCode).then(data => {
        const retValue = data.data
        this.tableData = retValue
      })/** ajax end*/
    }
  }
}
</script>

<style scoped>

</style>
