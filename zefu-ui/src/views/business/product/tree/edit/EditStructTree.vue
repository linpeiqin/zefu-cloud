<template>
  <div>
    <el-dialog
      title="编辑元素"
      :visible.sync="show"
      :modal="false"
      :close-on-click-modal="false"
      :show-close="false"
      :close-on-press-escape="false"
    >

      <el-table
        :data="data"
        style="width: 300px"
        row-key="identifier"
      >
        <el-table-column
          prop="identifier"
          label="标识符"
        />
        <el-table-column
          prop="dataType"
          label="数据类型"
        />
        <el-table-column
          fixed="right"
          label="操作"
          width="120"
        >
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small"
              @click.native.prevent="deleteRow(scope.$index, data)"
            >
              删除
            </el-button>
            <el-button
              type="text"
              size="small"
              @click.native.prevent="editRow(scope.$index, scope.row)"
            >
              编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button type="primary" @click="addItem">添加参数</el-button>
      <el-dialog
        title="新增元素E"
        :visible.sync="dlgShow"
        :modal="false"
        :close-on-click-modal="false"
        :show-close="false"
        :close-on-press-escape="false"
      >

        <byte-tree ref="treeData" :show="byteShow" />
        <div slot="footer" class="dialog-footer">
          <el-button @click="addItemCancel">取 消</el-button>
          <el-button type="primary" @click="saveItem">确 定</el-button>
        </div>
      </el-dialog>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'EditStructTree',
  props: {
    data: Array,
    show: Boolean
  },
  data() {
    return {
      currRow: null,
      dlgShow: false,
      byteShow: true
    }
  },
  methods: {
    cancel() {
      this.$emit('close')
    },
    addItemCancel() {
      this.dlgShow = false
       
    },
    saveItem() {
      const ret = this.$refs.treeData.getData()
      console.log(JSON.stringify(ret))
      this.dlgShow = false
      this.data.push(ret)
      this.$refs.treeData.reset()
    },
    save() {
      this.$emit('save')
    },
    addItem() {
      this.dlgShow = true
       
    },
    editRow(index, row) {
      this.currRow = row
      this.$emit('edit')
    },
    deleteRow(index, data) {
      data.splice(index, 1)
    },
    getCurrRow() {
      // alert(JSON.stringify(this.currRow.data));
      if (this.structArr.includes(this.currRow.dataType)) {
        return this.currRow.data
      } else {
        return this.currRow
      }
    },
    getData() {
      return this.data
    }
  }
}
</script>

<style scoped>

</style>
