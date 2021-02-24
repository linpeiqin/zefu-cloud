<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['business:mapdefinition:add']"
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
          v-hasPermi="['business:mapdefinition:edit']"
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
          v-hasPermi="['business:mapdefinition:remove']"
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
          v-hasPermi="['business:mapdefinition:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mapdefinitionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="id"/>
      <el-table-column label="名称" align="center" prop="name"/>
      <el-table-column label="样式" align="center" prop="pattern"/>
      <el-table-column label="风格" align="center" prop="style"/>
      <el-table-column label="级别" align="center" prop="level"/>
      <el-table-column label="控制器" align="center" prop="controller"/>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat"/>
      <el-table-column label="备注信息" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:mapdefinition:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:mapdefinition:remove']"
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

    <!-- 添加或修改地图定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" fullscreen append-to-body>
      <el-row :gutter="22">
        <el-col :span="20">
          <baidu-map class="bm-view" :center="center" :zoom="form.level"  :scroll-wheel-zoom="true" >
            <bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
            <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
            <bm-map-type :map-types="form.style" anchor="BMAP_ANCHOR_TOP_LEFT"></bm-map-type>
            <bm-overview-map anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :isOpen="false"></bm-overview-map>
            <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
            <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
            <bm-panorama anchor="BMAP_ANCHOR_BOTTOM_LEFT" offset="-200"></bm-panorama>
          </baidu-map>
        </el-col>
        <el-col :span="4">
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入名称"/>
            </el-form-item>
            <el-form-item label="样式" prop="pattern">
              <el-input v-model="form.pattern" placeholder="请输入样式"/>
            </el-form-item>
            <el-form-item label="风格类型" prop="style">
              <el-select v-model="form.style" placeholder="风格类型" clearable size="small">
                <el-option
                  v-for="dict in styleOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="级别" prop="level">
              <el-input v-model="form.level" placeholder="请输入级别"/>
            </el-form-item>
            <el-form-item label="控制器" prop="controller">
              <el-input v-model="form.controller" placeholder="请输入控制器"/>
            </el-form-item>
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
            <el-form-item label="描述" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入描述"
                        :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="preview">预览</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listMapdefinition,
  getMapdefinition,
  delMapdefinition,
  addMapdefinition,
  updateMapdefinition
} from "@/api/business/mapdefinition";

export default {
  name: "Mapdefinition",
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
      // 地图定义表格数据
      mapdefinitionList: [],
      // 状态字典
      statusOptions: [],
      //地图类型字典
      styleOptions:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        pattern: null,
        style: null,
        level: null,
        controller: null,
        status: null,
      },
      // 表单参数
      form: {
        name: "",
        pattern: "9260a25a95016dc8c9e64c5d62af26c7",
        style: "",
        level: 18,
        controller: "",
        status: 0
      },
      // 表单校验
      rules: {},
      center: {lng: 116.404, lat: 39.915},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("bus_map_type").then(response => {
      this.styleOptions = response.data;
    });
  },
  methods: {
    /** 查询地图定义列表 */
    getList() {
      this.loading = true;
      listMapdefinition(this.queryParams).then(response => {
        this.mapdefinitionList = response.rows;
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
        pattern: null,
        style: null,
        level: null,
        controller: null,
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
      this.open = true;
      this.title = "添加地图定义";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMapdefinition(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改地图定义";
      });
    },
    /** 预览按钮 */
    preview(){
      this.$refs["form"].validate(valid => {
        if (valid) {

        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMapdefinition(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMapdefinition(this.form).then(response => {
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
      this.$confirm('是否确认删除地图定义编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delMapdefinition(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/mapdefinition/export', {
        ...this.queryParams
      }, `business_mapdefinition.xlsx`)
    },
  }
};
</script>
<style lang="scss" scoped>
.bm-view {
  width: 100%;
  height: 500px;
}
</style>
