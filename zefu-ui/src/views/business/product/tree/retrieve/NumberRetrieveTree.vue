<template>
  <div>
    <el-form ref="form" :model="formData">

      <el-form-item label="计量单位" prop="unit">
        <el-select v-model="formData.unit" prop="unit" clearable placeholder="请选择">
          <el-option
            v-for="item in unitList"
            :key="item.code"
            :label="item.desc"
            :value="item.code"
          />
        </el-select>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {productUnitsApi} from '@/api/business/product'

export default {
  name: 'NumberTree',
  props: {
    formData: { type: Object, default: null }
  },
  data() {
    return {
      unitList: []
    }
  },
  created() {
    this.initUnitsList()
  },
  methods: {
    getData() {
      const ret = {
        dataType: this.formData.dataType,
        identifier: this.formData.identifier,
        unit: this.formData.unit
      }
      this.$refs.form.resetFields()
      return ret
    },
    setDataType(type) {
      this.dataType = type
    },
    initUnitsList() {
      productUnitsApi({}).then(data => {
        const retValue = data.data
        this.unitList = retValue
      })
    }
  }
}
</script>

<style scoped>
    .el-input{
        width:260px;
    }
</style>
