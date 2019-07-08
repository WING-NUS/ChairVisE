<template>
  <el-container v-loading.fullscreen.lock="isAppLoading">
    <el-header>
      <menu-bar></menu-bar>
    </el-header>
    <el-main>
      <router-view/>
    </el-main>
    <el-footer></el-footer>
  </el-container>
</template>

<script>
  import MenuBar from '@/components/MenuBar.vue'

  export default {
    watch: {
      '$route'() {
        this.$store.dispatch('getAuthInfo');
      },
      'isFetchUserInfoError'() {
        if (!this.isFetchUserInfoError) {
          return
        }
        this.$notify.error({
          title: 'Auth request fail',
          message: this.$store.state.userInfo.apiErrorMsg,
          duration: 0
        });
      }
    },
    components: {
      'menu-bar': MenuBar,
    },
    computed: {
      isAppLoading() {
        return this.$store.state.isPageLoading
      },
      isFetchUserInfoError() {
        return this.$store.state.userInfo.isApiError
      }
    },
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Montserrat:300,400,500');

  body {
    font-family: 'Montserrat', sans-serif;
  }
</style>

