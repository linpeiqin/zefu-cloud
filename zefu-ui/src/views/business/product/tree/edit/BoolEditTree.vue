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
        <el-form-item label="">
          <el-input v-model="data.bool1" placeholder="如:开">
            <template slot="prepend">true - </template>
          </el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="data.bool0" placeholder="如:关">
            <template slot="prepend">false - </template>
          </el-input>
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
export default {
  name: 'BoolTree',
  props: {
    data: Object,
    show: Boolean
  },
  data() {
    return {
      currRow: null
    }
  },
  methods: {
    getData() {
      return this.data
    },
    cancel() {
      this.$emit('close')
    },
    save() {
      this.$emit('save')
    },
    showData() {
      alert(JSON.stringify(this.getData()))
    },
    getData() {
      const ret = {
        dataType: this.data.dataType,
        identifier: this.data.identifier,
        bool0: this.data.bool0,
        bool1: this.data.bool1
      }
      this.$refs.form.resetFields()
      return ret
    },
    editRow(index, row) {
      this.currRow = row
      this.$emit('edit')
    },
    getCurrRow() {
      // alert(JSON.stringify(this.currRow.data));
      return this.currRow
    }
  }
}
</script>

<style scoped>
    .el-input{
        width:260px;
    }
</style>
