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
          <span>
            日期类型必须传入长整型unix时间戳(单位:毫秒)
          </span>
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
    getData() {
      const ret = {
        dataType: this.data.dataType,
        identifier: this.data.identifier
      }
      this.$refs.form.resetFields()
      return ret
    }
  }
}
</script>

<style scoped>
    .el-input {
        width: 260px;
    }
</style>
