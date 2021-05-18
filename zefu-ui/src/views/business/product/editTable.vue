<template>
  <el-card>
      <el-tabs v-model="activeModelName"  @tab-click="handleModelClick">
        <el-tab-pane label="属性" name="propModel">
         <prop-mgr-page ref="propMgrPage" :show="showPropPage" :product-code="productCode" />
        </el-tab-pane>
        <el-tab-pane label="事件" name="eventModel">
          <event-mgr-page ref="eventMgrPage" :show="showEventPage" :product-code="productCode" />
        </el-tab-pane>
        <el-tab-pane label="服务" name="serviceModel">
          <service-mgr-page ref="serviceMgrPage" :show="showServicePage" :product-code="productCode" />
        </el-tab-pane>
        <el-tab-pane label="TOPIC" name="topicInfo">
          <topic-detail :product-code="productCode" />
        </el-tab-pane>
      </el-tabs>
    <el-form label-width="100px">
      <el-form-item style="text-align: center;margin-left:-100px;margin-top:10px;">
        <el-button @click="close()">返回</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import serviceMgrPage from './component/ServiceMgrPage.vue'
import propMgrPage from './component/PropMgrPage.vue'
import eventMgrPage from './component/EventMgrPage.vue'
import topicDetail from './component/TopicDetail'

export default {
  name: "ProductEdit",
  components: {
    serviceMgrPage,
    propMgrPage,
    eventMgrPage,
    topicDetail
  },
  data() {
    return {
      form:{},
      activeModelName: 'propModel',
      showPropPage: true,
      showEventPage: true,
      showServicePage: true,
      showProductDetailPage: true,
      productCode: this.$route.query.code
    };
  },
  created() {

  },
  methods: {
    handleModelClick(tab, event) {
      if (tab.name == 'eventModel') {
        this.$refs.eventMgrPage.initEvents()
      } else if (tab.name == 'serviceModel') {
        this.$refs.serviceMgrPage.initServices()
      } else if (tab.name == 'propModel') {
        this.$refs.propMgrPage.initProps()
      }
    },
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/business/product", query: { t: Date.now()}})
    }
  }
};
</script>
