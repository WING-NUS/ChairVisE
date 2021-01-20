<template>
  <el-container id="app">
    <el-header style="padding: 0;">
      <menu-bar style="position: fixed; width: 100vw; z-index: 1;"/>
    </el-header>
    <transition name="fade">
      <router-view/>
    </transition>
  </el-container>
</template>

<script>

import MenuBar from '@/components/MenuBar'

export default {
  name: 'App',
  components: {
    'menu-bar': MenuBar
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Montserrat:300,400,500');
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 50px;
}

body {
  font-family: 'Montserrat', sans-serif;
  font-weight: 450;
  color: #202020;
  margin: 0;
}

.fade-enter-active, .fade-leave-active {
  transition-property: opacity;
  transition-duration: .25s;
}

.fade-enter-active {
  transition-delay: .5s;
}

.fade-enter, .fade-leave-active {
  opacity: 0
}
</style>
