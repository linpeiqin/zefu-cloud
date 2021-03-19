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
          <el-input v-model.number="data.length" type="number" col="1" onkeyup="value=value.replace(/[^\d]/g,'')">
            <template slot="prepend">最大长度</template>
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
  name: 'TextTree',
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
        identifier: this.data.identifier,
        length: this.data.length
      }
      this.$refs.form.resetFields()
      return ret
    }
  }
}
</script>

<style scoped>
    .el-input{
        width:260px;
    }
</style>
