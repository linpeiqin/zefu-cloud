<template>
  <div v-if="show">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          @click="createProp"
          v-hasPermi="['business:func:add']"
        >新增属性
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          @click="dataTemplateShow"
          v-hasPermi="['business:func:edit']"
        >数据模板
        </el-button>
      </el-col>
    </el-row>
    <el-table
      :data="propData"
      border
      class="table"
      header-cell-class-name="table-header"
    >
      <el-table-column v-if="false" prop="id" label="ID"/>
      <el-table-column prop="identifier" label="属性标识" width="200" align="center"/>
      <el-table-column prop="funcName" label="属性名称" align="center"/>
      <el-table-column prop="dataType" label="数据类型" align="center"/>
      <el-table-column prop="attr" label="属性" :formatter="formatAttr" align="center"/>
      <el-table-column prop="wrType" label="只读" :formatter="formatWRType" align="center"/>
      <el-table-column prop="funcStatus" label="状态" :formatter="formatFuncStatus" align="center"/>
      <el-table-column prop="funcDesc" label="描述" align="center"/>
      <el-table-column prop="createTime" label="创建时间" align="center"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="editPropHandle(scope.$index, scope.row)"
          >编辑
          </el-button>
          <el-button
            type="text"
            class="red"
            @click="delPropHandle(scope.$index, scope.row)"
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
      @pagination="initProps"
    />
    <el-dialog title="新增属性" :visible.sync="dialogAddPropVisible">
      <el-form ref="propForm" :model="propForm" :rules="propFormRules">
        <el-form-item label="属性名称" prop="funcName">
          <el-input v-model="propForm.funcName" label-width="50px"/>
        </el-form-item>
        <byte-tree ref="byteCreateForm" :show="createByteShow"/>
        <el-form-item label="读写类型" prop="wrType">
          <el-radio-group v-model="propForm.wrType">
            <el-radio label="1">读写</el-radio>
            <el-radio label="0">只读</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="funcDesc">
          <el-input v-model="propForm.funcDesc" placeholder="请输入描述" type="textarea" rows="5"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onCreateCancel">取 消</el-button>
        <el-button type="primary" @click="onCreateProp">确 定</el-button>
      </div>
    </el-dialog><!--新增属性结束-->
    <el-dialog title="编辑属性" :visible.sync="dialogEditPropVisible">
      <el-form ref="propEditForm" :model="propEditForm" :rules="propFormRules">
        <el-form-item label="属性名称" prop="funcName">
          <el-input v-model="propEditForm.funcName" label-width="50px"/>
        </el-form-item>
        <byte-retrieve-tree ref="retrieveTree" :show="true" :form-data="propEditForm"/>
        <el-form-item label="读写类型" prop="wrType">
          <el-radio-group v-model="propEditForm.wrType">
            <el-radio :label="1">读写</el-radio>
            <el-radio :label="0">只读</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="funcDesc">
          <el-input v-model="propEditForm.funcDesc" placeholder="请输入描述" type="textarea" rows="5"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editCancelPropHandle">取 消</el-button>
        <el-button type="primary" @click="onEditSubmit">确 定</el-button>
      </div>
    </el-dialog><!--编辑属性结束-->
    <el-dialog title="数据模版" :visible.sync="templateShow">
      请求数据格式 <br>
      <json-viewer
        :value="templateRequest"
        :expand-depth="5"
        copyable
        boxed
        sort
      />
      数据说明模版<br>
      <json-viewer
        :value="templateData"
        :expand-depth="5"
        copyable
        boxed
        sort
      />
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
  name: 'PropMgrPage',
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
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 遮罩层
      loading: true,
      templateData: {},
      templateRequest: {},
      templateShow: false,
      editByteShow: true,
      createByteShow: true,
      propData: [],
      total: 0,
      delPropdialogVisible: false,
      dialogEditPropVisible: false,
      formLabelWidth: '120px',
      propEditForm: {
        funcName: '',
        identifier: '',
        funcDesc: '',
        attr: null,
        wrType: null,
        dataType: null,
        funcType: 'PROP'
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productCode: this.productCode,
        status: null,
        funcType: 'PROP'
      },
      propForm: {
        funcName: '',
        identifier: '',
        funcDesc: '',
        attr: null,
        wrType: null,
        funcType: 'PROP'
      },
      propFormRules: {
        funcName: [{required: true, message: '请输入名称', trigger: 'blur'}],
        identifier: [{required: true, message: '请选输入标识符', trigger: 'blur'}],
        dataType: [{required: true, message: '请选择数据类型', trigger: 'change'}],
        funcDesc: [{max: 160, message: '描述最多160个字符', trigger: 'blur'}]
      },
      dialogAddPropVisible: false
    }
  },
  created() {
    this.initProps()
  },
  methods: {
    dataTemplateShow() {
      this.templateShow = true
    },

    release(index, row) {
      const id = row.id
      this.$confirm('确定要发布吗？', '提示', {
        type: 'warning'
      }).then(() => {
        propsReleaseApi(id).then(response => {
          this.$message.success('发布成功')
          this.initProps()
        }).catch(error => {
          this.$message.error(error);
        })
      }).catch(() => {
      })
    },
    createProp() {
      this.dialogAddPropVisible = true
    },
    formatWRType(row, column) {
      if (row.wrType == '1') {
        return '否'
      } else if (row.wrType == '0') {
        return '是'
      }
      return '-'
    },
    formatFuncStatus(row, column) {
      if (row.funcStatus == '1') {
        return '已发布'
      } else if (row.funcStatus == '0') {
        return '草稿'
      }
      return '-'
    },
    editPropHandle(index, row) {
      this.dialogEditPropVisible = true
      const id = row.id
      propDetailApi(id).then(data => {
        const retValue = data.data
        this.propEditForm = retValue
      })/** ajax end*/
    },
    delPropHandle(index, row) {
      const id = row.id
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        propDelApi(id).then(response => {
          this.$message.success('删除成功')
          this.initProps()
        }).catch(error => {
          this.$message.error(error);
        })
      }).catch(() => {
      })
    },
    initProps() {
      productFuncList(this.queryParams).then(data => {
        this.propData = data.rows;
        this.total = data.total;
        this.loading = false;
      })

      const param = {
        productCode: this.productCode,
        funcType: 'PROP'
      }
      propsTemplateApi(param).then(data => {
        const retValue = data.data || null;
        if (retValue != null){
          this.templateRequest = retValue.demoData;
          this.templateData = retValue.template
        }
      })
      this.$emit('initProps')
    },
    editCancelPropHandle() {
      this.dialogEditPropVisible = false
      this.$refs['propEditForm'].resetFields()
    },
    onEditSubmit() {
      this.$refs.propEditForm.validate(valid => {
        if (valid) {
          this.propEditForm.productCode = this.productCode
          const para = this.propEditForm
          let attrObj
          this.$nextTick(() => {
            attrObj = this.$refs.retrieveTree.getData()
          })
          para.attr = JSON.stringify(attrObj)
          propsEditApi(para).then(data => {
            this.$message.success('提交成功！')
            this.$refs['propEditForm'].resetFields()
            this.dialogEditPropVisible = false
            this.initProps()
          })/** end ajax*/
        }/** valid true*/ else {
          this.$message.success('校验失败！')
        }/** valid false*/
      })
    },

    onCreateCancel() {
      this.$refs['propForm'].resetFields()
      this.dialogAddPropVisible = false
    },
    onCreateProp() {
      const attr = this.$refs.byteCreateForm.getData()
      const attrObj = JSON.stringify(attr)
      this.propForm.productCode = this.productCode
      const para = this.propForm
      para.attr = attrObj
      para.identifier = attr.identifier
      this.$refs.propForm.validate(valid => {
        if (valid) {
          propsCreateApi(para).then(data => {
            const retValue = data.data
            const {msg, code} = data
            console.log(retValue)
            this.$message.success('提交成功！')
            this.$refs['propForm'].resetFields()
            this.dialogAddPropVisible = false
            this.initProps()
            this.$refs.byteCreateForm.reset()
          })/** end ajax*/
        }/** valid true*/ else {
          this.$message.success('校验失败！')
        }/** valid false*/
      })
    }/** func onCreateProp ends*/
  }
}
</script>

<style scoped>

</style>
