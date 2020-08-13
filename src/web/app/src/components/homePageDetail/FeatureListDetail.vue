<template>
  <el-main>
    <div class="page">
      <div class="feature-list" v-if="!isLogin">
        <h1> Here's what you can do</h1>
        <el-row type="flex" :gutter="16" align="middle" justify="center">
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">                                
                <img src="@/assets/upload.png"/>
                <p> Upload all your conference data </p>
              </el-card> 
            </el-col>
          </zoom-x-transition>
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">
                <img src="@/assets/collaboration.png"/>
                <p> Create and share presentation with others </p>
              </el-card> 
            </el-col>
          </zoom-x-transition>
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">                
                <img src="@/assets/presentation.png"/>
                <p> Generate various visualisation </p>
              </el-card> 
            </el-col>
          </zoom-x-transition>
        </el-row>
      </div>
      <div class="options-list" v-if="isLogin">
        <h1> Exploring Chairvise </h1>
        <el-row type="flex" :gutter="16" align="middle" justify="center">
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">
                <img src="@/assets/upload.png"/>             
                <el-button type="primary" class="button" @click="importData">
                  Import Data
                </el-button>
              </el-card>
            </el-col>
          </zoom-x-transition>
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">
                <img src="@/assets/stadistics.png"/>
                <el-button type="primary" class="button" @click="analyze">
                  Analyse Data 
                </el-button>
              </el-card>
            </el-col>
          </zoom-x-transition>
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">
                <img src="@/assets/schedule.png"/>
                <el-button type="primary" class="button" @click="conference">
                  Track Conference
                </el-button>
              </el-card>
            </el-col>
          </zoom-x-transition>
        </el-row>
      </div>
    </div>
  </el-main>
</template>

<script>
  import {ZoomXTransition} from 'vue2-transitions'

  export default {
    name: 'FeatureListDetail',
    data: () => ({
      show: false,
    }),
    props: {
      msg: String
    },
    computed: {
      isLogin() {
        return this.$store.state.userInfo.isLogin
      }
    },
    mounted() {
      this.loadFeatures();
    },
    methods: {
      analyze() {
        this.$router.push("/analyze");
      },
      importData() {
        this.$router.push("/importData");
      },
      loadFeatures() {
        this.show = true;
      },
      conference() {
        this.$router.replace("/conference");
      }
    },
    components: {
      ZoomXTransition
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  i {
    font-size: 4rem;    
    margin-bottom: 1.5rem;
  }

  .el-card {
    margin: 0px;
    text-align: center;
  }

  .el-button--text {
    color: black;
  }

  .page {
    padding: 20px;
  }

  .options-list .el-button {
    width: 100%
  }
</style>
