<template>
  <div>
    <el-form ref="form" :model="formData">
     
      <el-form-item label="">
        <el-input v-model="formData.bool1" placeholder="如:开">
          <template slot="prepend">true - </template>
        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input v-model="formData.bool0" placeholder="如:关">
          <template slot="prepend">false - </template>
        </el-input>
      </el-form-item>
    </el-form>
    <!--        <el-button @click.native="showData">显示数据</el-button>-->
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
    showData() {
      alert(JSON.stringify(this.getData()))
    },
    getData() {
      const ret = {
        dataType: this.formData.dataType,
        identifier: this.formData.identifier,
        bool0: this.formData.bool0,
        bool1: this.formData.bool1
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
