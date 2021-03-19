<template>
  <div>
    <el-form ref="form" :model="formData">
      
      <el-form-item label="">
        <el-input v-model.number="formData.length" type="number" col="1" onkeyup="value=value.replace(/[^\d]/g,'')">
          <template slot="prepend">最大长度</template>
        </el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'TextTree',
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
        identifier: this.formData.identifier,
        length: this.formData.length
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
