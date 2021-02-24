<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备编号" prop="equipmentNo">
        <el-input
          v-model="queryParams.equipmentNo"
          placeholder="请输入设备编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备ID" prop="equipmentId">
        <el-input
          v-model="queryParams.equipmentId"
          placeholder="请输入设备ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备位置" prop="equipmentPosition">
        <el-input
          v-model="queryParams.equipmentPosition"
          placeholder="请输入设备位置"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类别" prop="equipmentType">
        <el-select v-model="queryParams.equipmentType" placeholder="请选择设备类别" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="实体ID" prop="entityId">
        <el-input
          v-model="queryParams.entityId"
          placeholder="请输入实体ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场景ID" prop="sceneId">
        <el-input
          v-model="queryParams.sceneId"
          placeholder="请输入场景ID"
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
          v-hasPermi="['business:entityequipment:add']"
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
          v-hasPermi="['business:entityequipment:edit']"
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
          v-hasPermi="['business:entityequipment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:entityequipment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="entityequipmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="设备名称" align="center" prop="equipmentName" />
      <el-table-column label="设备编号" align="center" prop="equipmentNo" />
      <el-table-column label="设备ID" align="center" prop="equipmentId" />
      <el-table-column label="设备位置" align="center" prop="equipmentPosition" />
      <el-table-column label="设备类别" align="center" prop="equipmentType" />
      <el-table-column label="实体ID" align="center" prop="entityId" />
      <el-table-column label="场景ID" align="center" prop="sceneId" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注信息" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:entityequipment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:entityequipment:remove']"
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

    <!-- 添加或修改实体设备关联对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备编号" prop="equipmentNo">
          <el-input v-model="form.equipmentNo" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="设备ID" prop="equipmentId">
          <el-input v-model="form.equipmentId" placeholder="请输入设备ID" />
        </el-form-item>
        <el-form-item label="设备位置" prop="equipmentPosition">
          <el-input v-model="form.equipmentPosition" placeholder="请输入设备位置" />
        </el-form-item>
        <el-form-item label="设备类别" prop="equipmentType">
          <el-select v-model="form.equipmentType" placeholder="请选择设备类别">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="实体ID" prop="entityId">
          <el-input v-model="form.entityId" placeholder="请输入实体ID" />
        </el-form-item>
        <el-form-item label="场景ID" prop="sceneId">
          <el-input v-model="form.sceneId" placeholder="请输入场景ID" />
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
import { listEntityequipment, getEntityequipment, delEntityequipment, addEntityequipment, updateEntityequipment } from "@/api/business/entityequipment";

export default {
  name: "Entityequipment",
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
      // 实体设备关联表格数据
      entityequipmentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentName: null,
        equipmentNo: null,
        equipmentId: null,
        equipmentPosition: null,
        equipmentType: null,
        entityId: null,
        sceneId: null,
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
    /** 查询实体设备关联列表 */
    getList() {
      this.loading = true;
      listEntityequipment(this.queryParams).then(response => {
        this.entityequipmentList = response.rows;
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
        equipmentName: null,
        equipmentNo: null,
        equipmentId: null,
        equipmentPosition: null,
        equipmentType: null,
        entityId: null,
        sceneId: null,
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
      this.title = "添加实体设备关联";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEntityequipment(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改实体设备关联";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEntityequipment(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEntityequipment(this.form).then(response => {
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
      this.$confirm('是否确认删除实体设备关联编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delEntityequipment(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/entityequipment/export', {
        ...this.queryParams
      }, `business_entityequipment.xlsx`)
    }
  }
};
</script>