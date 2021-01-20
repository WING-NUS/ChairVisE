<template>
  <div>
    <BannerDetail/>
    <QuickStartDetail/>
  </div>
</template>

<script>
// @ is an alias to /src
import QuickStartDetail from '@/components/homepage/QuickStartDetail'
import BannerDetail from '@/components/homepage/BannerDetail'
export default {
  name: 'Home',
  data () {
    return {
      isLogin: window.sessionStorage.getItem('token')
    }
  },
  components: {
    BannerDetail,
    QuickStartDetail
  },
  methods: {}
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
