<template>
  <div>
    <zoom-center-transition :duration="500">
      <el-row>
        <div v-show="show">
          <el-col :span="5">
            <img alt="Vue logo" src="@/assets/images/chair2.png"/>
          </el-col>
          <el-col :span="19">
            <h2>ChairVisE V4.0 -- The Conference Data Visualisation Management System</h2>
          </el-col>
        </div>
      </el-row>
    </zoom-center-transition>
  </div>
</template>

<script>
import { ZoomCenterTransition } from 'vue2-transitions'

export default {
  name: 'BannerDetail',
  data: () => ({
    show: false
  }),
  props: {
    msg: String
  },
  mounted () {
    this.loadBanner()
  },
  methods: {
    loadBanner () {
      this.show = true
    }
  },
  components: {
    ZoomCenterTransition
  }
}
</script>

<style scoped>
img {
  width: 180px;
  height: 200px;
  text-align: center;
  display: block;
  margin: 0 auto;
}
.el-row {
  background: #1e9fc4;
  padding: 20px;
}
h2 {
  margin-top: 60px;
}
</style>
