<template>
  <div>
    <el-form ref="form">
      <div>
        <el-dialog
          title="新增元素"
          :visible.sync="dlgShow"
          :modal="false"
          :close-on-click-modal="false"
          :show-close="false"
          :close-on-press-escape="false"
        >
          <byte-tree ref="treeData" :show="byteShow"/>
          <div slot="footer" class="dialog-footer">
            <el-button @click="addItemCancel">取 消</el-button>
            <el-button type="primary" @click="saveItem">确 定</el-button>
          </div>
        </el-dialog>
        <el-table :data="identifiers" style="width: 300px">
          <el-table-column prop="identifier" label="标识符"/>
          <el-table-column prop="dataType" label="数据类型"/>
          <el-table-column fixed="right" label="操作" width="120">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click.native.prevent="deleteRow(scope.$index, identifiers)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button type="primary" @click="addItem">添加参数</el-button>
        <el-button type="primary" @click="showParams">显示参数</el-button>
      </div>
    </el-form>
  </div>

</template>

<script>
export default {
  name: 'StructTree',
  props: {
    funcType: {
      type: String,
      default: 'notservicce'
    }
  },
  data() {
    return {
      dataType: 'struct',
      dlgShow: false,
      byteShow: true,
      identifiers: []
    }
  },
  created() {
  },
  methods: {
    showParams() {
      const ret = this.identifiers
      console.log(JSON.stringify(ret))
      alert(JSON.stringify(ret));
    },
    deleteRow(index, data) {
      data.splice(index, 1)
    },
    addItem() {
      this.dlgShow = true
      this.$refs.treeData.reset()
    },
    addItemCancel() {
      this.dlgShow = false
    },
    saveItem() {
      const ret = this.$refs.treeData.getData()
      console.log(JSON.stringify(ret))
      this.dlgShow = false
      this.identifiers.push(ret)
    },
    getData() {
      return {
        dataType: 'struct',
        data: this.identifiers
      }
    },
    visibleReset() {

    },
    onDataTypeChange(dataType) {
      this.visibleReset()
    }
  }
}
</script>
