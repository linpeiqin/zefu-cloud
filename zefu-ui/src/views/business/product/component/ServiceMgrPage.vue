<template>
  <div v-if="show">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          @click="createService"
          v-hasPermi="['business:func:add']"
        >新增服务
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          v-hasPermi="['business:func:edit']"
        >数据模板
        </el-button>
      </el-col>
    </el-row>
    <!--          @click="dataTemplateShow"-->
    <el-table
      :data="serviceData"
      border
      class="table"
      header-cell-class-name="table-header"
    >
      <el-table-column v-if="false" prop="id" label="ID"/>
      <el-table-column prop="identifier" label="服务标识" width="200" align="center"/>
      <el-table-column prop="funcName" label="服务名称" align="center"/>

      <el-table-column prop="async" label="异步" :formatter="formatAsync" align="center"/>

      <el-table-column prop="funcStatus" label="状态" :formatter="formatFuncStatus" align="center"/>
      <el-table-column prop="funcDesc" label="描述" align="center"/>
      <el-table-column prop="createTime" label="创建时间" align="center"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="editServiceHandle(scope.$index, scope.row)"
          >编辑
          </el-button>
          <el-button
            type="text"
            class="red"
            @click="delServiceHandle(scope.$index, scope.row)"
          >删除
          </el-button>
          <el-button
            type="text"
            class="red"
            @click="release(scope.$index, scope.row)"
          >发布
          </el-button>
        </template>
      </el-table-column>
    </el-table><!--属性表单结束-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="initServices"
    />
    <el-dialog title="新增服务" :visible.sync="dlgAddVisible">
      <el-form ref="form" :model="form" :rules="formRules">
        <el-form-item label="属性名称" prop="funcName">
          <el-input v-model="form.funcName" label-width="50px"/>
        </el-form-item>
        <el-form-item label="标识符" prop="identifier">
          <el-input v-model="form.identifier" label-width="50px"/>
        </el-form-item>
        <el-form-item label="输入参数" prop="inputParam"><br>
          <byte-tree ref="byteInputForm" :show="createByteShow" :funcType="funcType"/>
        </el-form-item>
        <!-- <el-form-item label="输出参数" prop="outputParam"><br>
          <byte-tree ref="byteOutputForm" :show="createByteShow"  />
        </el-form-item> -->
        <!-- <el-form-item label="异步" prop="async">
          <el-radio-group v-model="form.async">
            <el-radio :label="1">异步</el-radio>
            <el-radio :label="0">同步</el-radio>
          </el-radio-group>
        </el-form-item> -->
        <el-form-item label="描述" prop="funcDesc">
          <el-input v-model="form.funcDesc" placeholder="请输入描述" type="textarea" rows="5"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onCreateCancel">取 消</el-button>
        <el-button type="primary" @click="onCreate">确 定</el-button>
      </div>
    </el-dialog><!--新增属性结束-->
    <el-dialog title="编辑服务" :visible.sync="dlgEditVisible">
      <el-form ref="editForm" :model="editForm" :rules="formRules">
        <el-form-item label="属性名称" prop="funcName">
          <el-input v-model="editForm.funcName" label-width="50px"/>
        </el-form-item>

        <el-form-item label="输入参数" prop="inputParam"><br>
          <byte-retrieve-tree ref="inputRetrieveTree" :funcType="funcType" :form-data="editForm.input"
                              :show="createByteShow"/>
        </el-form-item>
        <!-- <el-form-item label="输出参数" prop="outputParam"><br>
          <byte-retrieve-tree ref="outputRetrieveTree"    :form-data="editForm.output"  :show="createByteShow"/>
        </el-form-item> -->
        <!-- <el-form-item label="异步" prop="async">
          <el-radio-group v-model="editForm.async">
            <el-radio :label="1">异步</el-radio>
            <el-radio :label="0">同步</el-radio>
          </el-radio-group>
        </el-form-item>
        -->
        <el-form-item label="描述" prop="funcDesc">
          <el-input v-model="editForm.funcDesc" placeholder="请输入描述" type="textarea" rows="5"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onEditCancel">取 消</el-button>
        <el-button type="primary" @click="onEdit">确 定</el-button>
      </div>
    </el-dialog><!--新增属性结束-->
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
  propDelApi,
  propDetailApi,
  propsCreateApi,
  propsEditApi,
  propsReleaseApi,
  propsTemplateApi
} from '@/api/business/product'


export default {
  name: 'ServiceMgrPage',


  props: {
    show: {
      type: Boolean,
      default: false
    },
    productCode: String
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
      templateData: {},
      templateRequest: {},
      templateShow: false,
      dlgEditVisible: false,
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productCode: this.productCode,
        status: null,
        funcType: 'SERVICE'
      },
      editForm: {
        funcType: 'SERVICE'
      },
      funcType: 'SERVICE',
      serviceData: [],
      dlgAddVisible: false,
      createByteShow: true,
      form: {
        funcType: 'SERVICE'
      },
      formRules: {
        funcName: [{required: true, message: '请输入名称', trigger: 'blur'}],
        identifier: [{required: true, message: '请选输入标识符', trigger: 'blur'}],
        dataType: [{required: true, message: '请选择数据类型', trigger: 'change'}],
        funcDesc: [{max: 160, message: '描述最多160个字符', trigger: 'blur'}]
      }
    }
  },
  methods: {
    release(index, row) {
      let id = row.id
      this.$confirm('确定要发布吗？', '提示', {
        type: 'warning'
      }).then(() => {
        propsReleaseApi(id).then(response => {
          console.log(response)
          this.$message.success('发布成功')
          this.initServices()
        }).catch(error => {
          reject(error)
        })
      }).catch(() => {
      })
    },
    formatFuncStatus(row, column) {
      if (row.funcStatus == '1') {
        return '已发布'
      } else if (row.funcStatus == '0') {
        return '草稿'
      }
      return '-'
    },
    onEditCancel() {
      this.$refs['editForm'].resetFields()
      this.dlgEditVisible = false
    },
    onEdit() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.editForm.productCode = this.productCode
          const para = this.editForm
          const inputParam = this.$refs.inputRetrieveTree.getData()
          para.inputParam = JSON.stringify(inputParam)
          propsEditApi(para).then(data => {
            const retValue = data.data
            const {msg, code} = data
            console.log(retValue)
            this.$message.success('提交成功！')
            this.$refs['editForm'].resetFields()
            this.dlgEditVisible = false
            this.initServices()
          })/** end ajax*/
        }/** valid true*/ else {
          this.$message.success('校验失败！')
        }/** valid false*/
      })
    },

    formatAsync(row, column, value) {
      if (value == 0) {
        return '否'
      } else {
        return '是'
      }
    },
    createService() {
      this.dlgAddVisible = true
    },
    editServiceHandle(index, row) {
      const id = row.id
     /* const param = {id, id}*/
      propDetailApi(id).then(data => {
        const retValue = data.data
        const {msg, code} = data.data
        console.log(retValue)
        this.editForm = retValue
        if (null != retValue.inputParam) {
          this.editForm.input = this.processParam(retValue.inputParam)
          this.editForm.input.attrMap = this.processParam(retValue.inputParam)
        }
        this.dlgEditVisible = true
      })/** ajax end*/
    },
    /** 服务端返回的出入参进行处理*/
    processParam(param) {
      const result = {
        identifier: '',
        dataType: ''
      }
      try {
        return JSON.parse(param)
      } catch (e) {
        return result
      }
    },
    delServiceHandle(index, row) {
      const param = {id: row.id}
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        propDelApi(row.id).then(data => {
          const retValue = data.data
          console.log(retValue)
          this.initServices()
        })/** ajax end*/
      }).catch(() => {
      })
    },
    onCreateCancel() {
      this.$refs['form'].resetFields()
      this.dlgAddVisible = false
    },
    onCreate() {
      this.form.productCode = this.productCode
      const para = this.form

      if (null != this.$refs.byteInputForm.getData()) {
        para.inputParam = JSON.stringify(this.$refs.byteInputForm.getData())
      }

      this.$refs['form'].validate(valid => {
        if (valid) {
          propsCreateApi(para).then(data => {
            const retValue = data.data
            const {msg, code} = data
            this.$message.success('提交成功！')
            this.$refs['form'].resetFields()
            this.dlgAddVisible = false
            this.initServices()
            this.$refs.byteInputForm.reset()
          })/** end ajax*/
        }/** valid true*/ else {
          this.$message.success('校验失败！')
        }/** valid false*/
      })
    }, /** func onCreateProp ends*/
    initServices() {
      productFuncList(this.queryParams).then(data => {
        this.serviceData = data.rows;
        this.total = data.total;
        this.loading = false;
      })
      let param = {
        productCode:this.productCode,
        funcType:'SERVICE'
      }
     propsTemplateApi(param).then(data => {
       const retValue = data.data || null;
       if (retValue != null){
         this.templateRequest = retValue.demoData;
         this.templateData = retValue.template
       }
      })

    }

  }
}
</script>

<style scoped>
.el-input {
  width: 260px;
}
</style>
