<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="场景名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入场景名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场景类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择场景类型" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:scenedefinition:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:scenedefinition:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:scenedefinition:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:scenedefinition:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="scenedefinitionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="场景名称" align="center" prop="name"/>
      <el-table-column label="场景类型" align="center" prop="type"/>
      <el-table-column label="主机地址" align="center" prop="macCode"/>
      <el-table-column label="父场景" align="center" prop="parentId"/>
      <el-table-column label="主机IP" align="center" prop="masterIp"/>
      <el-table-column label="关联场景" align="center" prop="relationId"/>
      <el-table-column label="场景地址" align="center" prop="address"/>
      <el-table-column label="管理员" align="center" prop="adminName"/>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:scenedefinition:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:scenedefinition:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改场景定义对话框 -->
    <el-dialog :title="title" :visible.sync="open">
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" size="medium" label-width="80px"
                 label-position="left">
          <el-col :span="12">
            <el-form-item label="场景名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入场景名称" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="场景类型" prop="type">
<!--              <el-select v-model="form.type" placeholder="请选择下拉场景类型" clearable
                         :style="{width: '100%'}">
                <el-option v-for="(item, index) in typeOptions" :key="index" :label="item.label"
                           :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>-->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主机地址" prop="macCode">
              <el-input v-model="form.macCode" placeholder="请输入主机地址" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主机IP" prop="masterIp">
              <el-input v-model="form.masterIp" placeholder="请输入主机IP" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="父场景" prop="parentId">
              <el-input v-model="form.parentId" placeholder="请输入父场景" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="关联场景" prop="relationId">
              <el-input v-model="form.relationId" placeholder="请输入关联场景" clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="场景地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入场景地址" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="管理员" prop="adminId">
              <el-input v-model="form.adminId" placeholder="请输入场景管理员" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入描述"
                        :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listScenedefinition,
  getScenedefinition,
  delScenedefinition,
  addScenedefinition,
  updateScenedefinition
} from "@/api/business/scenedefinition";

export default {
  name: "Scenedefinition",
  components: {},
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
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 场景定义表格数据
      scenedefinitionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        macCode: null,
        parentId: null,
        masterIp: null,
        relationId: null,
        address: null,
        adminId: null,
        adminName: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询场景定义列表 */
    getList() {
      this.loading = true;
      listScenedefinition(this.queryParams).then(response => {
        this.scenedefinitionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        type: null,
        macCode: null,
        parentId: null,
        masterIp: null,
        relationId: null,
        address: null,
        adminId: null,
        adminName: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加场景定义";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getScenedefinition(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改场景定义";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateScenedefinition(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addScenedefinition(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除场景定义编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delScenedefinition(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/scenedefinition/export', {
        ...this.queryParams
      }, `business_scenedefinition.xlsx`)
    }
  }
};
</script>
