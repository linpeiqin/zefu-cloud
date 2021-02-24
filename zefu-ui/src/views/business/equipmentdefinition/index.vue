<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择设备类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="设备序列" prop="serial">
        <el-input
          v-model="queryParams.serial"
          placeholder="请输入设备序列"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通讯协议ID" prop="communicationAgreementId">
        <el-input
          v-model="queryParams.communicationAgreementId"
          placeholder="请输入通讯协议ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备本身ID" prop="selfId">
        <el-input
          v-model="queryParams.selfId"
          placeholder="请输入设备本身ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="协议名称" prop="agreementName">
        <el-input
          v-model="queryParams.agreementName"
          placeholder="请输入协议名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产厂商" prop="manufactor">
        <el-input
          v-model="queryParams.manufactor"
          placeholder="请输入生产厂商"
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
          v-hasPermi="['business:equipmentdefinition:add']"
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
          v-hasPermi="['business:equipmentdefinition:edit']"
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
          v-hasPermi="['business:equipmentdefinition:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:equipmentdefinition:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentdefinitionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="设备名称" align="center" prop="name" />
      <el-table-column label="设备类型" align="center" prop="type" />
      <el-table-column label="设备序列" align="center" prop="serial" />
      <el-table-column label="通讯协议ID" align="center" prop="communicationAgreementId" />
      <el-table-column label="设备本身ID" align="center" prop="selfId" />
      <el-table-column label="协议名称" align="center" prop="agreementName" />
      <el-table-column label="生产厂商" align="center" prop="manufactor" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注信息" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:equipmentdefinition:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:equipmentdefinition:remove']"
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

    <!-- 添加或修改设备定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择设备类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="设备序列" prop="serial">
          <el-input v-model="form.serial" placeholder="请输入设备序列" />
        </el-form-item>
        <el-form-item label="通讯协议ID" prop="communicationAgreementId">
          <el-input v-model="form.communicationAgreementId" placeholder="请输入通讯协议ID" />
        </el-form-item>
        <el-form-item label="设备本身ID" prop="selfId">
          <el-input v-model="form.selfId" placeholder="请输入设备本身ID" />
        </el-form-item>
        <el-form-item label="协议名称" prop="agreementName">
          <el-input v-model="form.agreementName" placeholder="请输入协议名称" />
        </el-form-item>
        <el-form-item label="生产厂商" prop="manufactor">
          <el-input v-model="form.manufactor" placeholder="请输入生产厂商" />
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
import { listEquipmentdefinition, getEquipmentdefinition, delEquipmentdefinition, addEquipmentdefinition, updateEquipmentdefinition } from "@/api/business/equipmentdefinition";

export default {
  name: "Equipmentdefinition",
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
      // 设备定义表格数据
      equipmentdefinitionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        serial: null,
        communicationAgreementId: null,
        selfId: null,
        agreementName: null,
        manufactor: null,
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
    /** 查询设备定义列表 */
    getList() {
      this.loading = true;
      listEquipmentdefinition(this.queryParams).then(response => {
        this.equipmentdefinitionList = response.rows;
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
        name: null,
        type: null,
        serial: null,
        communicationAgreementId: null,
        selfId: null,
        agreementName: null,
        manufactor: null,
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
      this.title = "添加设备定义";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEquipmentdefinition(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备定义";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEquipmentdefinition(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEquipmentdefinition(this.form).then(response => {
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
      this.$confirm('是否确认删除设备定义编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delEquipmentdefinition(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/equipmentdefinition/export', {
        ...this.queryParams
      }, `business_equipmentdefinition.xlsx`)
    }
  }
};
</script>