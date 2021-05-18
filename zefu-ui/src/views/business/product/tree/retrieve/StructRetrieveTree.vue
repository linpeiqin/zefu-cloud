<template>
  <div>
    <el-form ref="form" :model="formData">


      <div>
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
        <el-table
          :data="tableData"
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
                @click.native.prevent="deleteRow(scope.$index, tableData)"
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
        <el-button type="primary" @click="showData">显示参数</el-button>

        <component
          :is="myComponent"
          ref="treeData"
          :data="editData"
          :show="dlgEditShow"
          @close="dlgEditShow = false"
          @save="getTreeData"
          @edit="editDlg"
        />

      </div>
    </el-form>
  </div>

</template>

<script>
import editStructTree from '../edit/EditStructTree'
import boolEditTree from '../edit/BoolEditTree'
import numberEditTree from '../edit/NumberEditTree'
import textEditTree from '../edit/TextEditTree'
import dateEditTree from '../edit/DateEditTree'

export default {
  name: 'StructTree',
  components: { editStructTree, boolEditTree, numberEditTree, textEditTree, dateEditTree },
  props: {
    dataType: { type: String, default: 'struct' },
    identifier: { type: String, default: '' },
    formData: Object
  },
  data() {
    return {
      dlgShow: false,
      byteShow: true,
      identifiers: [],
      childTable: [],
      tableData: [],
      dlgEditShow: false,
      editData: [],
      myComponent: null
    }
  },
  created() {
    this.tableData = this.formData.attrMap.data//, this.childTable;
  },
  updated() {
    this.tableData = this.formData.attrMap.data//, this.childTable;
    console.log('STRUCT UPDATE')
  },
  methods: {
    showData() {
      alert(JSON.stringify(this.tableData))
    },
    getTreeData() {
      const ret = this.$refs.treeData.getData()
      this.dlgEditShow = false

      if (this.structArr.includes(ret.dataType)) {
        return
      }
    },
    deleteRow(index, rows) {
      rows.splice(index, 1)
    },
    //* *最外面的edit,只可能列出struct类型*//
    editRow(index, row) {
      this.myComponent = editStructTree
      this.editData = row.data
      this.dlgEditShow = true
    },
    /** 子组件点击编辑按钮*/
    editDlg() {
      this.editData = this.$refs.treeData.getCurrRow()
      const row = this.editData
      this.dlgEditShow = true
      if (this.structArr.includes(row.dataType)) {
        this.myComponent = editStructTree
      } else if (this.textArr.includes(row.dataType)) {
        this.myComponent = textEditTree
      } else if (this.numArr.includes(row.dataType)) {
        this.myComponent = numberEditTree
      } else if (this.boolArr.includes(row.dataType)) {
        this.myComponent = boolEditTree
      } else if (this.dateArr.includes(row.dataType)) {
        this.myComponent = dateEditTree
      }
    },
    addItem() {
      this.dlgShow = true
    },
    addItemCancel() {
      this.dlgShow = false
    },
    saveItem() {
      const ret = this.$refs.treeData.getData()
      this.tableData.push(ret)
      console.log(JSON.stringify(ret))
      this.dlgShow = false
    },
    getData() {
      return {
        dataType: this.formData.dataType,
        identifier: this.formData.identifier,
        data: this.tableData
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

<style scoped>
    .el-input{
        width:260px;
    }
</style>
