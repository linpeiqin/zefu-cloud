<template>
  <div class="app-container">
    <el-form :model="search" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备编码" prop="deviceCode">
        <el-input
          v-model="search.deviceCode"
          placeholder="请输入设备编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="search.deviceName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品编码" prop="productCode">
        <el-select
          v-model="search.productCode"
          clearable
          placeholder="请选择产品"
        >
          <el-option
            v-for="item in productList"
            :key="item.productCode"
            :label="item.productName"
            :value="item.productCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="在线状态" prop="activeStatus">
        <el-select v-model="search.activeStatus" placeholder="请选择0:离线 1:在线" clearable size="small">
          <el-option
            v-for="dict in activeStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="节点类型" prop="nodeType">
        <el-select v-model="search.nodeType" placeholder="请选择节点类型" clearable size="small">
          <el-option
            v-for="dict in nodeTypeOptions"
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
          v-hasPermi="['business:device:add']"
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
          v-hasPermi="['business:device:edit']"
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
          v-hasPermi="['business:device:remove']"
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
          v-hasPermi="['business:device:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deviceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="设备编码" align="center" prop="deviceCode"/>
      <el-table-column label="设备名称" align="center" prop="deviceName"/>
      <el-table-column label="所属产品" align="center" prop="productName"  />
      <el-table-column label="网关编码" align="center" prop="gwDevCode"/>
      <el-table-column label="产品编码" align="center" prop="productCode"/>
      <el-table-column v-if="false" label="设备秘钥" align="center" prop="deviceSecret"/>
      <el-table-column label="节点类型" align="center" prop="nodeTypeName" :formatter="nodeTypeFormat"/>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enableStatus"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="最后上线时间" align="center" prop="lastOnlineTime" :formatter="formatTime"/>
      <el-table-column label="是否在线" align="center" prop="activeStatus" :formatter="activeFormat"/>
      <el-table-column label="创建时间" align="center" prop="createTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:device:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="viewDetail(scope.row)"
            v-hasPermi="['business:device:edit']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:device:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="genSign(scope.row)"
            v-hasPermi="['business:device:edit']"
          >签名
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="query.pageNo"
      :limit.sync="query.limit"
      @pagination="getList"
    />

    <!-- 添加或修改设备管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备编码" prop="deviceCode">
          <el-input v-model="form.deviceCode"/>
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="form.deviceName"/>
        </el-form-item>
        <el-form-item label="设备密钥" prop="deviceSecret">
          <el-input v-model="form.deviceSecret"/>
        </el-form-item>
        <el-form-item label="所属产品" prop="productCode">
          <el-select
            v-model="form.productCode"
            prop="productCode"
            clearable
            placeholder="请选择"
            @change="$forceUpdate()"
          >
            <el-option
              v-for="item in productList"
              :key="item.productCode"
              :label="item.productName"
              :value="item.productCode"
            />
          </el-select>
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
import {
  addDevice,
  changeDeviceStatus,
  delDevice,
  deviceSearchApi,
  getDevice,
  updateDevice
} from "@/api/business/device";
import {listProduct} from '@/api/business/product';

export default {
  name: "Device",
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
      // 节点类型字典
      nodeTypeOptions: [],
      // 状态字典
      enableStatusOptions: [],
      activeStatusOptions:[],
      // 总条数
      total: 0,
      // 设备管理表格数据
      deviceList: [],
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      productCode: this.$route.query.code,
      /** 搜索参数*/
      search: {
        productCode: null,
        enableStatus: null,
        activeStatus: null
      },
      // 查询参数
      query: {
        paramData: {
          productCode: this.$route.query.code
        },
        pageNo: 1,
        limit: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productCode: [
          {required: true, max: 60, message: '请选择产品', trigger: 'blur'}
        ],
        deviceCode: [
          {
            required: true,
            max: 60,
            message: '请输入设备编码',
            trigger: 'blur'
          }
        ],
        deviceName: [
          {
            required: true,
            max: 60,
            message: '请输入设备名称',
            trigger: 'blur'
          }
        ],
        deviceSecret: [
          {
            required: true,
            max: 60,
            message: '请输入设备密钥',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  watch: {
    $route: {
      handler() {
        this.productCode = this.$route.query.code
        this.query = {
          paramData: {
            productCode: this.$route.query.code
          },
          pageNo: 1,
          limit: 10
        }
        this.getList()
      },
      deep: true
    }
  },
  created() {
    this.getList();
    this.getDicts("bus_node_type").then(response => {
      this.nodeTypeOptions = response.data;
    });
    this.getDicts("sys_normal_disable").then(response => {
      this.enableStatusOptions = response.data;
    });
    this.getDicts("bus_device_status").then(response => {
      this.activeStatusOptions = response.data;
    });
    this.getProductList();
  },
  methods: {
    /** 查询设备管理列表 */
    getList() {
      const para = this.query
      const paramData = {
        productCode: this.$route.query.code
      }
      para.paramData = paramData
      deviceSearchApi(para).then((data) => {
        const retValue = data.data
        this.deviceList = retValue.resultData
        this.total = retValue.total
        this.loading = false;
      })
    },
    getProductList() {
      listProduct({}).then((data) => {
        this.productList = data.rows
      })
    },

    genSign(row) {
      const userName = row.deviceCode + '|' + new Date().getTime()
      const password = this.$md5(userName + '|' + row.deviceSecret)
      let msg = 'clientId:' + row.deviceCode
      msg = msg + '<br/>userName:' + userName
      msg = msg + '<br/>password:' + password
      this.$alert(msg, 'MQTT登录信息', {
        dangerouslyUseHTMLString: true
      })
    },
    // 节点类型字典翻译
    nodeTypeFormat(row, column) {
      return this.selectDictLabel(this.nodeTypeOptions, row.nodeType);
    },
    activeFormat(row,column) {
      return  this.selectDictLabel(this.activeStatusOptions, row.activeStatus);
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
        deviceCode: null,
        deviceName: null,
        gwDevCode: null,
        productCode: null,
        delFlag: null,
        activeStatus: 0,
        lastOnlineTime: null,
        deviceSecret: null,
        firmwareVersion: null,
        devHost: null,
        devPort: null,
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
      const para = this.query
      const paramData = this.search
      para.paramData = paramData
      deviceSearchApi(para).then((data) => {
        const retValue = data.data
        this.deviceList = retValue.resultData
        this.total = retValue.total
        this.loading = false;
      })
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
      this.title = "添加设备管理";
    },
    viewDetail(row) {
      const id = row.id || this.ids[0];
      const param = {
        productCode: row.productCode,
        deviceCode: row.deviceCode
      }
      this.$router.push({path: '/device/detail/' + id, query: param})
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDevice(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDevice(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDevice(this.form).then(response => {
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
      let text = row.enableStatus === "0" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.deviceName + '"设备吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return changeDeviceStatus(row.id, row.enableStatus);
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function () {
        row.enableStatus = row.enableStatus === "0" ? "1" : "0";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除设备管理编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDevice(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/device/export', {
        ...this.query
      }, `business_device.xlsx`)
    }
  }
};
</script>
