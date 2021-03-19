<template>
  <div v-if="show" />
</template>

<script>
export default {
  name: 'ByteTree',
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {

    }
  },
  mounted() {

  },
  updated() {

  },
  methods: {
    getData() {
      return {}
    }

  }
}
</script>

<style scoped>
    .el-input{
        width:260px;
    }
</style>
