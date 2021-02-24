<template>
  <div>
    <svg-icon icon-class="question" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'ZeFuDoc',
  data() {
    return {
      url: 'http://doc.zefu.vip/zefu-vue'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>