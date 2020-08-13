<template>
  <div>
    <BannerDetail></BannerDetail>
    <FeatureListDetail></FeatureListDetail>
  </div>
</template>

<script>
  // @ is an alias to /src
  import BannerDetail from '@/components/homePageDetail/BannerDetail.vue'
  import FeatureListDetail from '@/components/homePageDetail/FeatureListDetail.vue'

  export default {
    name: 'home',
    components: {
      BannerDetail,
      FeatureListDetail
    }
  }
  
</script>
<style>
  .navbar-submenu-menu {
    margin-top: -5px;
    left: initial !important;
    right: 0;
  }

  .el-menu--horizontal > .navbar-submenu.is-active .el-submenu__title {
    border-bottom: 0;
  }

  .el-submenu.is-active .el-submenu__title {
    border-bottom: 0;
  }
</style>