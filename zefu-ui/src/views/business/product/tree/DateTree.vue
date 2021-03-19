<template>
  <div>
    <el-form ref="form">
      <el-form-item label="">
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
  props: {},
  data() {
    return {
    }
  },
  methods: {
    getData() {
      const ret = {
        dataType: 'date'
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
