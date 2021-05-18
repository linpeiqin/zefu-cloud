<template>
  <div v-if="show">
    <el-form ref="form" :model="formData">
      <el-form-item label="">
        <el-input
          v-model="formData.identifier"
          :disabled="true"
          v-if="identifierShow"
        >
          <template slot="prepend">标识符</template>
        </el-input>
      </el-form-item>
      <el-form-item label="数据类型" prop="dataType">
        <el-select
          v-model="formData.dataType"
          clearable
          :disabled="true"
          placeholder="请选择"
          @change="onDataTypeChange"
        >
          <el-option
            v-for="item in dataTypeList"
            :key="item.code"
            :label="item.desc"
            :value="item.code"
          />
        </el-select>
        <component :is="myComponent" ref="treeData" :form-data="formData" />
      </el-form-item>
    </el-form>
    <!--        <el-button @click.native="submit">提交</el-button>-->
  </div>
</template>

<script>
import numberRetrieveTree from "./NumberRetrieveTree";
import textRetrieveTree from "./TextRetrieveTree";
import boolRetrieveTree from "./BoolRetrieveTree";
import structRetrieveTree from "./StructRetrieveTree";
import dateRetrieveTree from "./DateRetrieveTree";
import emptyRetrieveTree from "./EmptyRetrieveTree";
import {productDataTypeApi} from "@/api/business/product";

export default {
  name: "ByteTree",
  components: {
    numberRetrieveTree,
    textRetrieveTree,
    boolRetrieveTree,
    structRetrieveTree,
    dateRetrieveTree,
    emptyRetrieveTree,
  },
  props: {
    show: {
      type: Boolean,
      default: false,
    },
    formData: { type: Object, default: "" },
    funcType: {
      type: String,
      default: "notservicce",
    },
  },
  data() {
    return {
      myComponent: null,
      unit: "",
      unitList: [],

      dataTypeList: [],
      identifierShow:true
    };
  },
  created() {
     if(this.funcType === "SERVICE"){
        this.identifierShow = false
    }
    this.initDataTypeList();
    this.onDataTypeChange(this.formData.dataType);
    console.log("LONG CREATED");
  },
  mounted() {
    this.onDataTypeChange(this.formData.dataType);
  },
  updated() {
    this.onDataTypeChange(this.formData.dataType);
    console.log("LONG UPDATED");
  },
  methods: {
    submit() {
      const ret = this.$refs.treeData.getData();
      return ret;
    },
    getData() {
      const ret = this.$refs.treeData.getData();
      return ret;
    },
    componentsReset() {},
    reset() {
      this.myComponent = null;
      this.dataType = "";
      this.identifier = "";
    },
    onDataTypeChange(dataType) {
      if (this.numArr.includes(dataType)) {
        this.myComponent = numberRetrieveTree;
      } else if (this.dateArr.includes(dataType)) {
        this.myComponent = dateRetrieveTree;
      } else if (this.boolArr.includes(dataType)) {
        this.myComponent = boolRetrieveTree;
      } else if (this.textArr.includes(dataType)) {
        this.myComponent = textRetrieveTree;
      } else if (this.structArr.includes(dataType)) {
        this.myComponent = structRetrieveTree;
      } else {
        this.myComponent = emptyRetrieveTree;
      }
    } /** onDataTypeChange*/,

    initDataTypeList() {
      productDataTypeApi({}).then((data) => {
        const retValue = data.data;
        this.dataTypeList = retValue;
      });
    },
  },
};
</script>

<style scoped>
.el-input {
  width: 260px;
}
</style>
