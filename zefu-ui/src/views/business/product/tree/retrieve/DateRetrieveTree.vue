<template>
  <div>
    <el-form ref="form" :model="formData">
      <el-form-item label="">
        <!-- <el-input v-model="formData.identifier" :disabled="true">
          <template slot="prepend">标识符</template>

        </el-input> -->
        <span>
          日期类型必须传入长整型unix时间戳(单位:毫秒)
        </span>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'BoolTree',
  props: {
    formData: Object
  },
  data() {
    return {

    }
  },
  methods: {
    getData() {
      const ret = {
        dataType: this.formData.dataType,
        identifier: this.formData.identifier
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
