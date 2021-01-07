<template>
  <el-main>
    <div class="page">
      <div class="feature-list" v-if="!isLogin">
        <h1>Here's what you can do</h1>
        <el-row type="flex" :gutter="16" align="middle" justify="center">
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">
                <img src="@/assets/images/upload.png"/>
                <p> Upload your conference data </p>
              </el-card>
            </el-col>
          </zoom-x-transition>
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">
                <img src="@/assets/images/presentation.png"/>
                <p> Generate various visualisation </p>
              </el-card>
            </el-col>
          </zoom-x-transition>
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">
                <img src="@/assets/images/collaboration.png"/>
                <p> Create and share presentation with others </p>
              </el-card>
            </el-col>
          </zoom-x-transition>
        </el-row>
      </div>
      <div class="options-list" v-if="isLogin">
        <h1>Quick Start</h1>
        <el-row type="flex" :gutter="16" align="middle" justify="center">
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">
                <h3>Step 1</h3>
                <img src="@/assets/images/upload.png"/>
                <el-button type="primary" class="button" @click="importData">
                  Import Data
                </el-button>
              </el-card>
            </el-col>
          </zoom-x-transition>
          <zoom-x-transition :duration="500" :delay="600">
            <el-col :sm="24" :md="8" :lg="8" :xl="8" v-show="show">
              <el-card shadow="hover" class="feature-card">
                <h3>Step 2</h3>
                <img src="@/assets/images/stadistics.png"/>
                <el-button type="primary" class="button" @click="visualize">
                  Visualise Data
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
import { ZoomXTransition } from 'vue2-transitions'

export default {
  name: 'QuickStartDetail',
  data: () => ({
    show: false
  }),
  props: {
    msg: String
  },
  computed: {
    isLogin () {
      return window.sessionStorage.getItem('token')
    }
  },
  mounted () {
    this.loadFeatures()
  },
  methods: {
    visualize () {
      this.$router.push('/conferenceList')
    },
    importData () {
      this.$router.push('/import')
    },
    loadFeatures () {
      this.show = true
    },
    conference () {
      // this.$router.replace('/conference')
    }
  },
  components: {
    ZoomXTransition
  }
}
</script>

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
  padding-left: 20px;
  padding-right: 20px;
  padding-top: 0;
}

.options-list .el-button {
  width: 100%;
}

h1 {
  margin-top: 0;
}

h3 {
  text-align: left;
  margin: 0;
  color: #535353;
}
</style>
