<template>
  <div>
    <el-form ref="form">
      <el-form-item label="">
        <!--                <el-input  v-model="identifier">-->
        <!--                    <template slot="prepend">标识符</template>-->
        <!--                </el-input>-->
      </el-form-item>
      <el-form-item label="">
        <el-input v-model="bool1" placeholder="如:开">
          <template slot="prepend">true - </template>
        </el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input v-model="bool0" placeholder="如:关">
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
  },
  data() {
    return {
      bool0: '',
      bool1: ''
    }
  },
  methods: {
    showData() {
      alert(JSON.stringify(this.getData()))
    },
    getData() {
      const ret = {
        dataType: 'boolean',
        bool0: this.bool0,
        bool1: this.bool1
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
