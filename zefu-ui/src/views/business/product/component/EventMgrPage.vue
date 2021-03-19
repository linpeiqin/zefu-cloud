<template>
  <div v-if="show">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          @click="createEvent"
          v-hasPermi="['business:func:add']"
        >新增事件</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          @click="templateShow = true"
          v-hasPermi="['business:func:edit']"
        >数据模板</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="eventData"
      border
      class="table"
      header-cell-class-name="table-header"
    >
      <el-table-column v-if="false" prop="id" label="ID" />
      <el-table-column prop="identifier" label="事件标识" width="200" align="center" />
      <el-table-column prop="funcName" label="名称" align="center" />
      <el-table-column prop="dataType" label="数据类型" align="center" />
      <el-table-column prop="attr" label="属性" :formatter="formatAttr" align="center" />
      <el-table-column prop="eventType" label="事件级别" :formatter="formatEventType" align="center" />
       <el-table-column prop="funcStatus" label="状态" :formatter="formatFuncStatus" align="center" />
      <el-table-column prop="funcDesc" label="描述" align="center" />
      <el-table-column prop="createTime" label="创建时间" align="center" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="editEventHandle(scope.$index, scope.row)"
          >编辑</el-button>
          <el-button
            type="text"
            class="red"
            @click="delPropHandle(scope.$index, scope.row)"
          >删除</el-button>
           <el-button
            type="text"
            class="red"
            @click="release(scope.$index, scope.row)"
          >发布</el-button>
        </template>
      </el-table-column>
    </el-table><!--新增事件表单结束-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="initEvents"
    />
    <el-dialog title="新增事件" :visible.sync="dialogEventVisible">
      <el-form ref="eventForm" :model="eventForm" :rules="eventFormRules">
        <el-form-item label="事件名称" prop="funcName">
          <el-input v-model="eventForm.funcName" label-width="50px" />
        </el-form-item>
        <el-form-item label="事件类型" prop="eventType">
          <el-radio-group v-model="eventForm.eventType">
            <el-radio label="INFO">通知</el-radio>
            <el-radio label="WARN">警告</el-radio>
            <el-radio label="FAULT">故障</el-radio>
          </el-radio-group>
        </el-form-item>
        <byte-tree ref="byteCreateForm" :show="createByteShow" />
        <el-form-item label="描述" prop="funcDesc">
          <el-input v-model="eventForm.funcDesc" placeholder="请输入描述" type="textarea" rows="5" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onCreateEventCancel">取 消</el-button>
        <el-button type="primary" @click="onEventSave">确 定</el-button>
      </div>
    </el-dialog><!--新增事件结束-->

    <el-dialog title="编辑事件" :visible.sync="dialogEditEventVisible">
      <el-form ref="eventEditForm" :model="eventEditForm" :rules="eventFormRules">
        <el-form-item label="事件名称" prop="funcName">
          <el-input v-model="eventEditForm.funcName" label-width="50px" />
        </el-form-item>
        <byte-retrieve-tree ref="retrieveTree" :show="true" :form-data="eventEditForm" />
        <el-form-item label="事件类型" prop="eventType">
          <el-radio-group v-model="eventEditForm.eventType">
            <el-radio label="INFO">通知</el-radio>
            <el-radio label="WARN">警告</el-radio>
            <el-radio label="FAULT">故障</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="描述" prop="funcDesc">
          <el-input v-model="eventEditForm.funcDesc" placeholder="请输入描述" type="textarea" rows="5" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onEditEventCancel">取 消</el-button>
        <el-button type="primary" @click="onEditEvent">确 定</el-button>
      </div>
    </el-dialog><!--编辑事件结束-->
    <el-dialog
      title="提示"
      :visible.sync="delEventDlgVisible"
      width="30%"
    >
      <span>您将永久性删除该属性，请确认！！！</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click.native="delEventDlgVisible = false">取 消</el-button>
        <el-button type="primary" @click.native="delProp">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="数据模版" :visible.sync="templateShow">
      请求数据格式<br/>
       <json-viewer
        :value="templateRequest"
        :expand-depth=5
        copyable
        boxed
        sort></json-viewer>
      数据说明模版<br/>
      <json-viewer
        :value="templateData"
        :expand-depth=5
        copyable
        boxed
        sort></json-viewer>


    </el-dialog><!--数据模版结束-->
  </div>
</template>

<script>

import {
  productFuncList,
  productPropsApi, propDelApi,
  propDetailApi, propsCreateApi,
  propsEditApi, propsReleaseApi, propsTemplateApi
} from '@/api/business/product'

export default {
  name: 'EventMgrPage',
  props: {
    show: {
      type: Boolean,
      default: true
    },
    productCode: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      templateRequest:{},
      templateData:{},
      templateShow:false,
      createByteShow: true,
      eventData: null,
      delEventDlgVisible: false,
      retData: null,
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productCode: this.productCode,
        status: null,
        funcType: 'EVENT'
      },
      dialogEventVisible: false,
      dialogEditEventVisible: false,
      eventForm: {
        funcType: 'EVENT'
      },
      delPropParams: {},
      eventFormRules: {},
      eventEditForm: {
        funcType: 'EVENT'
      },
      eventEditFormRules: {}
    }
  },
  created() {
    this.initEvents()
  },
  methods: {
    dataTemplateShow(){
        this.templateShow = true;
    },
    release(index, row){
      let id = row.id
      this.$confirm('确定要发布吗？', '提示', {
        type: 'warning'
      }).then(() => {
        propsReleaseApi(id).then(response => {
          console.log(response)
          this.$message.success('发布成功')
          this.initEvents()
        }).catch(error => {
          reject(error)
        })
      }).catch(() => {})
    },
    initEvents() {
      productFuncList(this.queryParams).then(data => {
        this.eventData = data.rows;
        this.total = data.total;
        this.loading = false;
      })
       let param = {
        productCode:this.productCode,
        funcType:'EVENT'
      }
      propsTemplateApi(param).then(data => {
        const retValue = data.data || null;
        if (retValue != null){
          this.templateRequest = retValue.demoData;
          this.templateData = retValue.template
        }
      })
    },
    editEventHandle(index, row) {
      this.dialogEditEventVisible = true
      const id = row.id
      propDetailApi(id).then(data => {
        const retValue = data.data
        console.log(retValue)
        this.eventEditForm = retValue
      })/** ajax end*/
    },
    createEvent() {
      this.dialogEventVisible = true

    },
    formatFuncStatus(row, column) {
      if (row.funcStatus == '1') {
        return '已发布'
      } else if (row.funcStatus == '0') {
        return '草稿'
      }
      return '-'
    },
    formatEventType(row, column) {
      if (row.eventType == 'INFO') {
        return '通知'
      } else if (row.eventType == 'WARN') {
        return '警告'
      } else if (row.eventType == 'FAULT') {
        return '故障'
      }
      return '-'
    },
    onCreateEventCancel() {
      this.dialogEventVisible = false
    },
    onEventSave() {
      const attr = this.$refs.byteCreateForm.getData()
      const attrObj = JSON.stringify(attr)
      this.eventForm.productCode = this.productCode
      const para = this.eventForm
      para.attr = attrObj
      para.identifier = attr.identifier
      this.$refs.eventForm.validate(valid => {
        if (valid) {
          propsCreateApi(para).then(data => {
            const retValue = data.data
            const { msg, code } = data
            console.log(retValue)
            this.$message.success('提交成功！')
            this.$refs['eventForm'].resetFields()
            this.dialogEventVisible = false
            this.initEvents()
           this.$refs.byteCreateForm.reset()
          })/** end ajax*/
        }/** valid true*/ else {
          this.$message.success('校验失败！')
        }/** valid false*/
      })
    }, /** func onCreateProp ends*/
    delPropHandle(index, row) {
      const id = row.id
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        const id = row.id
        propDelApi(id).then(response => {
          console.log(response)
          this.$message.success('删除成功')
          this.initEvents()
        }).catch(error => {
          reject(error)
        })
      }).catch(() => {})
    },
    onEditEventCancel() {
      this.dialogEditEventVisible = false
    },
    onEditEvent() {
      this.$refs.eventEditForm.validate(valid => {
        if (valid) {
          this.eventEditForm.productCode = this.productCode
          const para = this.eventEditForm
          const attrObj = this.$refs.retrieveTree.getData()
          para.attr = JSON.stringify(attrObj)
          propsEditApi(para).then(data => {
            const retValue = data.data
            const { msg, code } = data
            console.log(retValue)
            this.$message.success('提交成功！')
            this.$refs['eventEditForm'].resetFields()
            this.dialogEditEventVisible = false
            this.initEvents()
          })/** end ajax*/
        }/** valid true*/ else {
          this.$message.success('校验失败！')
        }/** valid false*/
      })
    }
  }
}
</script>

<style scoped>
    .el-input{
        width:260px;
    }
</style>
