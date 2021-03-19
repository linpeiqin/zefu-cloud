<template>
  <div>
    <el-form ref="form">
      <el-form-item label="" />
      <el-form-item label="">
        <el-input v-model.number="length" type="number" col="1" onkeyup="value=value.replace(/[^\d]/g,'')">
          <template slot="prepend">最大长度</template>
        </el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'NumberTree',
  data() {
    return {
      identifier: '',
      length: null
    }
  },
  methods: {
    getData() {
      const ret = {
        dataType: 'text',
        length: this.length
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
