<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备源ID" prop="equipmentSelfId">
        <el-input
          v-model="queryParams.equipmentSelfId"
          placeholder="请输入设备源ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="命令号" prop="commandNo">
        <el-input
          v-model="queryParams.commandNo"
          placeholder="请输入命令号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="起始地址" prop="startAddress">
        <el-input
          v-model="queryParams.startAddress"
          placeholder="请输入起始地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据长度" prop="dataLength">
        <el-input
          v-model="queryParams.dataLength"
          placeholder="请输入数据长度"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模组ID" prop="moduleId">
        <el-input
          v-model="queryParams.moduleId"
          placeholder="请输入模组ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发送串" prop="sendBuff">
        <el-input
          v-model="queryParams.sendBuff"
          placeholder="请输入发送串"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option label="请选择字典生成" value="" />
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
          v-hasPermi="['business:commandoption:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:commandoption:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:commandoption:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:commandoption:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commandoptionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="设备源ID" align="center" prop="equipmentSelfId" />
      <el-table-column label="命令号" align="center" prop="commandNo" />
      <el-table-column label="起始地址" align="center" prop="startAddress" />
      <el-table-column label="数据长度" align="center" prop="dataLength" />
      <el-table-column label="校验码" align="center" prop="crc" />
      <el-table-column label="模组ID" align="center" prop="moduleId" />
      <el-table-column label="发送串" align="center" prop="sendBuff" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注信息" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:commandoption:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:commandoption:remove']"
          >删除</el-button>
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

    <!-- 添加或修改命令设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备源ID" prop="equipmentSelfId">
          <el-input v-model="form.equipmentSelfId" placeholder="请输入设备源ID" />
        </el-form-item>
        <el-form-item label="命令号" prop="commandNo">
          <el-input v-model="form.commandNo" placeholder="请输入命令号" />
        </el-form-item>
        <el-form-item label="起始地址" prop="startAddress">
          <el-input v-model="form.startAddress" placeholder="请输入起始地址" />
        </el-form-item>
        <el-form-item label="数据长度" prop="dataLength">
          <el-input v-model="form.dataLength" placeholder="请输入数据长度" />
        </el-form-item>
        <el-form-item label="校验码" prop="crc">
          <el-input v-model="form.crc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="模组ID" prop="moduleId">
          <el-input v-model="form.moduleId" placeholder="请输入模组ID" />
        </el-form-item>
        <el-form-item label="发送串" prop="sendBuff">
          <el-input v-model="form.sendBuff" placeholder="请输入发送串" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCommandoption, getCommandoption, delCommandoption, addCommandoption, updateCommandoption } from "@/api/business/commandoption";

export default {
  name: "Commandoption",
  components: {
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
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 命令设置表格数据
      commandoptionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentSelfId: null,
        commandNo: null,
        startAddress: null,
        dataLength: null,
        crc: null,
        moduleId: null,
        sendBuff: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询命令设置列表 */
    getList() {
      this.loading = true;
      listCommandoption(this.queryParams).then(response => {
        this.commandoptionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        equipmentSelfId: null,
        commandNo: null,
        startAddress: null,
        dataLength: null,
        crc: null,
        moduleId: null,
        sendBuff: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加命令设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCommandoption(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改命令设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCommandoption(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCommandoption(this.form).then(response => {
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
      this.$confirm('是否确认删除命令设置编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCommandoption(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/commandoption/export', {
        ...this.queryParams
      }, `business_commandoption.xlsx`)
    }
  }
};
</script>
