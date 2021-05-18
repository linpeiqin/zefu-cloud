<template>
  <div class="app-container">
    <!--查询列表-->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消息协议" prop="protocolCode">
        <el-input
          v-model="queryParams.protocolCode"
          placeholder="请输入消息协议"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="节点类型" prop="nodeType">
        <el-select v-model="queryParams.nodeType" placeholder="请选择节点类型" clearable size="small">
          <el-option
            v-for="dict in nodeTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
    <!--头部操作列表（按钮）-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:product:add']"
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
          v-hasPermi="['business:product:edit']"
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
          v-hasPermi="['business:product:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:product:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--数据列表-->
    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品编码" align="center" prop="productCode" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="传输协议" align="center" prop="transportList" />
      <el-table-column label="消息协议" align="center" prop="protocolCode" />
      <el-table-column label="节点类型" align="center" prop="nodeType" :formatter="nodeTypeFormat" />
      <el-table-column label="图标" align="center" prop="logUrl" >
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.logUrl" />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:product:edit']"
          >修改</el-button>
          <el-button
            type="text"
            size="small"
            icon="el-icon-edit"
            @click="handleEditTable(scope.row)"
            v-hasPermi="['business:product:edit']"
          >物模型</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:product:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改产品管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-row :gutter="15">

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-col :span="24">
        <el-form-item label="产品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入产品名称" />
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="传输协议" prop="transportList">
          <el-radio-group v-model="form.transportList">
            <el-radio label="MQTT" />
            <!-- <el-checkbox label="TCP" /> -->
            <el-radio label="UDP" />
            <!-- <el-checkbox label="HTTP" /> -->
          </el-radio-group>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="数据协议" prop="protocolCode">
          <el-select v-model="form.protocolCode" placeholder="请选择">
            <el-option
              v-for="item in protocols"
              :key="item.code"
              :label="item.name"
              :value="item.code"
            />
          </el-select>
        </el-form-item>
        </el-col>
        <el-col :span="24">
        <el-form-item label="节点类型" >
          <el-radio-group v-model="form.nodeType">
            <el-radio
              v-for="dict in nodeTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="图标" >
            <el-popover
              placement="bottom-start"
              width="460"
              trigger="click"
              @show="$refs['iconSelect'].reset()"
            >
              <IconSelect ref="iconSelect" @selected="selected" />
              <el-input slot="reference" v-model="form.logUrl" placeholder="点击选择图标" readonly>
                <svg-icon
                  v-if="form.logUrl"
                  slot="prefix"
                  :icon-class="form.logUrl"
                  class="el-input__icon"
                  style="height: 32px;width: 16px;"
                />
                <i v-else slot="prefix" class="el-icon-search el-input__icon" />
              </el-input>
            </el-popover>
          </el-form-item>
        </el-col>
        <el-col :span="24">
        <el-form-item label="描述" prop="productDesc">
          <el-input v-model="form.productDesc" placeholder="请输入描述" />
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
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
  addProduct,
  changeProductStatus,
  delProduct,
  getProduct,
  listProduct,
  protocolListApi,
  updateProduct
} from "@/api/business/product";
import IconSelect from "@/components/IconSelect";

export default {
  name: "Product",
  components: {
    IconSelect
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
      // 唯一标识符
      uniqueId: "",
      // 总条数
      total: 0,
      // 产品管理表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 节点类型字典
      nodeTypeOptions: [],
      // 状态字典
      statusOptions: [],
      //协议列表
      protocols: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productCode: null,
        productName: null,
        protocolCode: null,
        nodeType: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        transportList: [{ required: true, message: '传输协议', trigger: 'change' }],
        productName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        productDesc: [{ required: true, max: 160, message: '请输入产品描述', trigger: 'blur' }],
        nodeType: [
          { required: true, message: "节点类型不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("bus_node_type").then(response => {
      this.nodeTypeOptions = response.data;
    });
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getData();
  },
  activated() {
    const time = this.$route.query.t;
    if (time != null && time != this.uniqueId) {
      this.uniqueId = time;
      this.resetQuery();
    }
  },
  methods: {
    getData() {
      protocolListApi({}).then(data => {
        const retValue = data.data
        console.log(retValue)
        this.protocols = retValue
      })
    },
    // 选择图标
    selected(name) {
      this.form.logUrl = name;
    },
    /** 查询产品管理列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 节点类型字典翻译
    nodeTypeFormat(row, column) {
      return this.selectDictLabel(this.nodeTypeOptions, row.nodeType);
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
        productCode: null,
        productName: null,
        transportList: null,
        protocolCode: null,
        nodeType: "0",
        productDesc: null,
        logUrl: null,
        productStatus: 0,
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
      this.title = "添加产品管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProduct(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品管理";
      });
    },
    /** 物模型按钮操作 */
    handleEditTable(row) {
      const id = row.id || this.ids[0];
      this.$router.push({path:"/product/edit/" + id,query:{code:row.productCode}});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProduct(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProduct(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.productName + '"产品吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return changeProductStatus(row.id, row.status);
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除产品管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delProduct(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/product/export', {
        ...this.queryParams
      }, `business_product.xlsx`)
    }
  }
};
</script>
