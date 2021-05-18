<template>
  <div>
    <el-dialog
      title="编辑元素"
      :visible.sync="show"
      :modal="false"
      :close-on-click-modal="false"
      :show-close="false"
      :close-on-press-escape="false"
    >
      <el-form ref="form" :model="data">
        <el-form-item label="">
          <el-input v-model="data.identifier" :disabled="true">
            <template slot="prepend">标识符</template>
          </el-input>
        </el-form-item>
        <el-form-item label="计量单位" prop="unit">
          <el-select v-model="data.unit" prop="unit" clearable placeholder="请选择">
            <el-option
              v-for="item in unitList"
              :key="item.code"
              :label="item.desc"
              :value="item.code"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {productUnitsApi} from '@/api/business/product'

export default {
  name: 'NumberTree',
  props: {
    data: { type: Object, default: null },
    show: Boolean
  },
  data() {
    return {
      unitList: [],
      currRow: null
    }
  },
  created() {
    this.initUnitsList()
  },
  methods: {
    editRow(index, row) {

    },
    getCurrRow() {

    },
    getData() {
      return this.data
    },
    cancel() {
      this.$emit('close')
    },
    save() {
      this.$emit('save')
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
    .el-input {
        width: 260px;
    }
</style>
