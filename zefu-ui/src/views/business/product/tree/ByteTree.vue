<template>
  <div v-if="show">
    <el-form>
      <el-form-item label="标识符" prop="identifier">
      <el-input v-model="identifier" v-if="identifierShow">
        <template slot="prepend">标识符</template>
      </el-input>
      </el-form-item>
      <el-form-item label="数据类型" prop="dataType">
        <el-select
          v-model="dataType"
          clearable
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
        <component :is="myComponent" ref="treeData" :data-type="dataType" />
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import numberTree from "./NumberTree";
import textTree from "./TextTree";
import boolTree from "./BoolTree";
import structTree from "./StructTree";
import dateTree from "./DateTree";
import { productDataTypeApi } from "@/api/business/product";

export default {
  name: "ByteTree",
  components: {
    numberTree,
    textTree,
    boolTree,
    structTree,
    dateTree,
  },
  props: {
    show: {
      type: Boolean,
      default: false,
    },
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
      identifier: "",
      dataTypeList: [],
      dataType: "",
      identifierShow: true,
    };
  },
  watch: {
    funcType: {
      handler(newValue, oldValue) {
        console.log("newValue:" + newValue + "  old:" + oldValue);
        if (newValue === "SERVICE") {
          this.identifierShow = false;
        } else {
          this.identifierShow = true;
        }
      },
      // 代表在wacth里声明了funcType这个方法之后立即先去执行handler方法
      immediate: true,
    },
  } /**ends watch*/,
  created() {
    this.initDataTypeList();
  },
  methods: {
    submit() {
      const ret = this.$refs.treeData.getData();
      ret.identifier = this.identifier;
      return ret;
    },
    getData() {
      if (undefined == this.$refs.treeData) {
        return null;
      }
      const ret = this.$refs.treeData.getData();
      ret.identifier = this.identifier;
      return ret;
    },
    reset() {
      this.myComponent = null;
      this.dataType = "";
      this.identifier = "";
    },
    onDataTypeChange(dataType) {
      if (this.numArr.includes(dataType)) {
        this.myComponent = numberTree;
      } else if (this.dateArr.includes(dataType)) {
        this.myComponent = dateTree;
      } else if (this.boolArr.includes(dataType)) {
        this.myComponent = boolTree;
      } else if (this.textArr.includes(dataType)) {
        this.myComponent = textTree;
      } else if (this.structArr.includes(dataType)) {
        this.myComponent = structTree;
      }
    } /** onDataTypeChange*/,

    initDataTypeList() {
      productDataTypeApi().then((data) => {
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
