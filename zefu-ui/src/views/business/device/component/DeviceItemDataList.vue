<template>
  <div>

      起<el-date-picker
          v-model="startDate"
          type="datetime"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择日期时间"
        />
        止<el-date-picker
          v-model="endDate"
          type="datetime"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择日期时间"
        />
        <el-button type="primary" @click="search">搜索</el-button>
      <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane :label="upLabel" name="up"> </el-tab-pane>
      <el-tab-pane :label="downLabel" name="down" v-if="downShow"> </el-tab-pane>
    </el-tabs>

         <el-table
          :data="tableData"
          border
          class="table"
          header-cell-class-name="table-header"

        >

          <el-table-column prop="timestamp" label="时间" align="center" width="200"/>
           <el-table-column prop="messageId" label="下发ID" align="center"
            v-if="funcType == 'SERVICE' || (funcType == 'PROP' && activeName == 'down')"
          />
          <el-table-column prop="request" label="值" align="center" :formatter="formatValue" />

          <el-table-column prop="status" label="设备响应状态" align="center" :formatter="formatStatus"
            v-if="funcType == 'SERVICE' || (funcType == 'PROP' && activeName == 'down')"
          />
          <el-table-column prop="replyMessage" label="设备响应" align="center" :formatter="formatReply"
            v-if="funcType == 'SERVICE' || (funcType == 'PROP' && activeName == 'down')"
          />
           <el-table-column prop="replyTime" label="响应时间" align="center"
            v-if="funcType == 'SERVICE' || (funcType == 'PROP' && activeName == 'down')"
            width="200"
          />

          <el-table-column label="操作" width="200" align="center">
            <template slot-scope="scope">
              <el-button
                type="text"
                @click="viewDetail(scope.$index, scope.row)"
                >查看
              </el-button>
              <el-button
                type="text"
                 v-if="funcType == 'SERVICE' || (funcType == 'PROP' && activeName == 'down')"
                @click="viewResponseDetail(scope.$index, scope.row)"
                >响应详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <div class="pagination">
          <el-pagination
            background
            layout="total, sizes, prev, pager, next"
            :current-page="query.pageNo"
            :page-size="query.limit"
            :page-sizes="[10, 20, 30, 50]"
            :total="total"
            @current-change="handlePageChange"
          />
        </div>
        <el-dialog title="" :visible.sync="contentShow" :modal="false">
          <pre>{{ content }}</pre>
        </el-dialog>
        <el-dialog title="" :visible.sync="responseShow" :modal="false">
          <pre>{{ replyMessage }}</pre>
        </el-dialog>


  </div>
</template>

<script>
import {deviceRtListApi, deviceSetListApi} from '@/api/business/device';

export default {
  name: "DeviceItemDataList",
  props: {
    funcType: String,
    deviceCode: String,
    productCode: String,
    show: Boolean,
    dlgName: String,
    upLabel:String,
    downLabel:String,
    downShow:true
  },
  data() {
    return {
      endDate: null,
      startDate: null,
      contentShow: false,
      tableData: [],
      content: "",
      dataType: "",
      identifier: "",
      unit: "",
      responseShow:false,
      replyMessage:'',
      query: {
        paramData: {
          productCode: this.productCode,
          deviceCode: this.deviceCode,
          funcType: this.funcType,
          startDate: this.startDate,
          endDate: this.endDate,
          identifier: this.identifier,
          dataType: this.dataType,
        },
        pageNo: 1,
        limit: 10,
      },
      total: 0,
      activeName:"up"
    };
  },
  methods: {
     search() {
      this.query.paramData.endDate = this.endDate;
      this.query.paramData.startDate = this.startDate;
      this.tableData = [];
      if('up' == this.activeName){
          deviceRtListApi(this.query).then((data) => {
            const retValue = data.data;
            this.tableData = retValue.resultData;
            this.total = retValue.total;
          });
      }else if('down' == this.activeName){
          deviceSetListApi(this.query).then((data) => {
            const retValue = data.data;
            this.tableData = retValue.resultData;
            this.total = retValue.total;
          });
      }

    },

    formatStatus(row, column){
       let value = row.status;
       if(200 == value){
         return "成功"
       }else if(201 == value){
          return "失败"
       }else  if(201 == value){
         return "未知错误"
       }else{
         return "暂未回执"
       }
    },
    formatReply(row, column){
      let value = "" + row.replyMessage;
      if (value.length > 30) {
        value = value.substr(0, 30) + "...";
      }
      return value
    },
    handleClick(item){
        this.search()
    },
    handlePageChange(val) {
      this.$set(this.query, "pageNo", val);
      this.getData(this.dataType, this.identifier, this.unit);
    },
    viewDetail(index, row) {
      this.content = row.request;
      this.contentShow = true;
      this.startDate = null;
      this.endDate = null;
    },
    viewResponseDetail(index, row){
      this.responseShow = true
      this.replyMessage = row.replyMessage
    },
    formatValue(row, column) {
      const dataType = this.dataType;
      let value = row.request;
      if (this.structArr.includes(dataType)) {
        value = JSON.stringify(row.request);
      }
      value = "" + value;
      if (value.length > 30) {
        value = value.substr(0, 30) + "...";
      }
      value = this.unit == null ? value : value + this.unit;
      return value;
    },
    /** 重置分页到第一页*/
    resetPageNo() {
      this.$set(this.query, "pageNo", 1);
    },
    getData(dataType, identifier, unit) {
      this.tableData = [];
      this.query.paramData.identifier = identifier;
      this.identifier = identifier;
      this.dataType = dataType;
      this.unit = unit;
      this.query.paramData.dataType = dataType;
      this.activeName = 'up'
      deviceRtListApi(this.query).then((data) => {
        const retValue = data.data;
        const { msg, code } = data;
        if (code !== 200) {
          this.$notify.error({
            title: "错误",
            message: msg,
          });
        } else {
          this.tableData = retValue.resultData;
          this.total = retValue.total;
        }
      });
    },
  },
};
</script>
