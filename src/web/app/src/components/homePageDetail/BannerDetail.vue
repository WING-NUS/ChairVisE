<template>
  <div>
    <zoom-center-transition :duration="500">      
      <el-row>
        <div v-show="show">
          <el-col :span="6">
            <img alt="Vue logo" src="@/assets/chair2.png"/>
          </el-col>
          <el-col :span="18">
            <h2> ChairVisE -- The Conference Data Visualisation Management System </h2>
          </el-col>
        </div>
      </el-row>
    </zoom-center-transition>
  </div>
</template>

<script>
  import {ZoomCenterTransition} from 'vue2-transitions'
  export default {
    name: 'BannerDetail',
    data: () => ({
      show: false,
    }),
    props: {
      msg: String
    },
    mounted() {
      this.loadBanner();
    },
    methods: {
      loadBanner() {
        this.show = true;
      }
    },
    components: {
      ZoomCenterTransition
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  img {
    width: 250px;
    height: 300px;
    text-align: center;
    display: block;
    margin: 0 auto;
  }
  .el-row {    
    background: #1e9fc4;
    padding: 32px;
  }
</style>
